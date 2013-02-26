/*
 * @(#)Pgm2.java        1.0 2013-01-30
 *
Lista konton/ betalningsmottagare samt tillgängligt saldo. 
Om man anger ett konto nummer så ska information endast visas om det kontonumret.
Om man inte anger ett konto nummer så skall information om alla kontonummer visas.
Lägga till konto/betalningsmottagare
När man lägger in ett nytt kontonummer så skall kontroll ske så att det inte redan finns ett konto med det numret. Valfritt är att lägga in en funktion som slumpar fram ett kontonummer.
Ett kontonummer får inte bestå av fler än 12 tecken inklusive max/minst ett bindestreck.
Sätta in pengar på konto.
Ta ut pengar från konto.
Lägga in överföringar och räkningar. Se klasserna Transaktion för information om vad som skall matas in för en räkning/överföring.
En minnesantackning för en betalning/överföring får vara max 15 tecken.
Det ska gå att välja att skicka antingen ett OCR-nummer eller ett meddelande till mottagaren av betalningen/överföringen.
Ett meddelande/OCR-nummer får vara minst ett tecken och max 15 tecken. (Om endast siffror har angivits så antas det vara ett OCR-nummer annars antas det vara ett meddelande till betalningsmottagaren).
Notera att OCR-numrets kontrollsiffra skall kontrolleras. I
 *
 */

import java.io.*;
import java.text.*;
import java.util.*;

/**   
 * Pgm2 presenterar en meny for foljande tjanster:
 * Lista konton, satt in/ta ut pengar, skapa konto och registrera transaktioner.
 * @version      1.0 30 Jan 2013
 * @author       xxx, xxx, xxx
 */
public class Pgm2 {

	private static Scanner tbScanner = new Scanner(System.in);
	private static Metoder m;

	/**
	 *  Mainmetod med while-loop for Pgm2's val
	 */
	public static void main(String[] args){
        System.out.println("-= Pgm2, Konto- och transaktionshantering =-\n");
        
		try {
			m = Metoder.buildMetoder();
		} catch (IOException e) {
        	System.out.println("Kunde inte hitta en eller flera av de angivna"
        			+ " filerna, var god fors�k igen. \n" 
        			+ "Working dir: " + new File("").getAbsolutePath() + "\n"
        			+ e.getMessage());
        	return;
		}		

		String huvudMeny =
			"========================================\n" +
		    "== Meny ================================\n" +
		    "========================================\n" +
			"1. Lista konto        4. Ta ut pengar\n" +
			"2. Skapa nytt konto   5. Registrera ny transaktion\n" +
			"3. S�tt in pengar     0. Avsluta\n" +
			"Gor ditt val: ";

		boolean avsluta = false;
		while(!avsluta) {
			System.out.print(huvudMeny);
			switch (tbScanner.nextLine()){
			case "1": 
				listaKonton(); 
				break;
				
			case "2": 
				skapaKonto(); 
				break;
				
			case "3": 
				sattInPengar(); 
				break;
				
			case "4": 
				taUtPengar(); 
				break;
				
			case "5": 
				registreraTransaktion(); 
				break;
				
			case "0": 
				System.out.println("Avslutar programmet!");
				avsluta = true; 
				break;
				
			default: 
				System.out.println("Forsok igen! (0-5)"); 
				break;
			}
		}
		try {
			m.saveChanges();
		} catch (IOException e) {
			System.out.println("Kunde inte spara till fil!\n" + e.getMessage());
		}
		System.out.print("Avslutar. Tack och hej.");
	}

	/**        
	 * visar information om ett konto
	 */
	private static void listaKonton() {
		System.out.print
			("Ange kontonummer eller enter for att se alla konton: ");
		String accountNumber = tbScanner.nextLine();
		
		if (accountNumber.trim().length() > 0) {  // om ett konto skall visas?
			try {
				Konto k = m.findAccount(accountNumber);

				System.out.println(Metoder.accountToString(k));
			} catch (NoSuchFieldException e){
				System.out.println("Kontot hittades inte!");
			}
			tbScanner.nextLine();

		} else { 
			System.out.println("Visar alla kontonummer: ");
			Konto[] accounts = m.getAccounts();
			for(Konto k : accounts) {
				if(k == null)
					break;

				System.out.println(Metoder.accountToString(k));
			}
			tbScanner.nextLine();    
		}
	}

	/**        
	 * Lagger till Konto
	 */
	private static void skapaKonto() {
		String number, name, owner;
		double amount;
		String val;
		String account;
		Konto k;
		Random Numb = new Random();

		do { // forsok tills ett nytt kontonr hittats
		System.out.print("Ange kontonummer (enter = slumpmassigt) :");

		val = tbScanner.nextLine();
			if (val.length() > 0) { // om enter slumpa fram kontonr
				number = val;
				System.out.println("sparar " + number);
			} else {
				int x = Numb.nextInt(10000);
				int y = Numb.nextInt(10000000);
				number = x + "-" + y;
				System.out.println("slumpar.. " + number);
			}
			if(m.accountExists(number))
				System.out.print("Konto existerar. ");
		} while(m.accountExists(number));
		
		System.out.print("Skriv in kontonamn: ");
		name = tbScanner.nextLine();

		System.out.print("Skriv in Agarens namn: ");
		owner = tbScanner.nextLine();
	
		System.out.print("Skriv in saldo: ");
		amount = Double.parseDouble(tbScanner.nextLine());
		
		k = new Konto(number, amount, name, owner);

		m.addAccount(k);
		
		System.out.println("Ditt nya konto ar:\n"
			+ Metoder.accountToString(k));
	}

	/**        
	 * Satta in pengar pa konto.
	 */
	private static void sattInPengar() {
		System.out.println("Valkommen till pengainsattningen!");
		System.out.print("Skriv in kontonummer: ");
		
		try {
			Konto k = m.findAccount(tbScanner.nextLine());
			System.out.println("Konto: " + Metoder.accountToString(k));
			System.out.print
				("Vanligen skriv i hur mycket pengar ni vill satta in: ");
			
			double amount = tbScanner.nextDouble();
			
			k.depositAmount(amount);
			
			System.out.println("Du har nu satt in " + amount + " pengar pa "
				+ "ditt konto: \n" + Metoder.accountToString(k));
			
		} catch (NoSuchFieldException e){
			System.out.println("Finns inget konto med det numret!");
			return;
		}
		
	}

	/**        
	 * Ta ut pengar från konto.
	 */
	private static void taUtPengar() {
		System.out.println("Välkommen till pengauttagningen!");
		System.out.print("Skriv in kontonummer: ");
		try {
			Konto k = m.findAccount(tbScanner.nextLine());
			
			System.out.println("Konto: " + Metoder.accountToString(k));
			System.out.print
				("Vänligen skriv i hur mycket pengar ni vill ta ut: ");
			
			double amount = Double.parseDouble(tbScanner.nextLine());
			
			k.withdraw(amount);
			
			System.out.println("Du har tagit ut " + amount + " pengar på "
				+ "ditt konto: \n" + Metoder.accountToString(k));
			
		} catch (NoSuchFieldException e){
			System.out.println("Finns inget konto med det numret!");
			return;
		}
	}

	/**        
	 * Lägga in överföringar och räkningar.
	 */
	private static void registreraTransaktion() {
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd");
		Konto source, destination;
		Date due;
		double amount;
		String ocr, not;
		Transaktion t;
		
		System.out.println("Välkommen att skapa en transaktion!");

		
		try {
			System.out.print("Skriv i avsändarens kontonummer: ");
			source = m.findAccount(tbScanner.nextLine());
			System.out.print("Skriv i mottagarens kontonummer: ");
			destination = m.findAccount(tbScanner.nextLine());
				
		} catch (NoSuchFieldException e) {
			System.out.println("Det finns inget konto med det numret!");
			return;
		}
		
		System.out.print("Skriv vilket datum, i formatet yyyyMMdd, du vill att"
				+ " transaktionen ska genomföras: ");
		
		try {
			due = dFormat.parse(tbScanner.nextLine());
		} catch (ParseException e) {
			System.out.println("Datumet du angav var i ogiltigt format!");
			return;
		}

		System.out.print("Skriv i mängd pengar att överföra: ");
		try {
			amount = Double.parseDouble(tbScanner.nextLine());
		} catch(NumberFormatException e) {
			System.out.println("Det där är inte ett giltigt tal.");
			return;
		}
		
		System.out.print("Skriv i ett OCR eller ett medelande ");
		ocr = tbScanner.nextLine();
		
		if(ocr.length() > 15 || ocr.length() <= 1 || !m.validOcr(ocr)) {
			System.out.println("Din inmatning registreades som ett Medelande!");
			
		}
		
		System.out.println("Lägg till en notering eller lämna fältet blankt: ");
		not = tbScanner.nextLine();
		
		if(not.trim().length() > 0)
			t = new Transaktion(due, source.getAccountNumber(), 
					destination.getAccountNumber(), amount, ocr);
		else
			t = new Transaktion(due, source.getAccountNumber(), 
					destination.getAccountNumber(), amount, ocr, not);
		
		
		m.addTransaction(t);
		System.out.println("Transaktion tillagd!\n" + t);
		
		// Se klasserna Transaktion för information om vad som skall matas in för en räkning/överföring.
		// En minnesantackning för en betalning/överföring får vara max 15 tecken.
		// Det ska gå att välja att skicka antingen ett OCR-nummer eller ett meddelande till mottagaren av betalningen/överföringen.
		// Ett meddelande/OCR-nummer får vara minst ett tecken och max 15 tecken. (Om endast siffror har angivits så antas det vara ett OCR-nummer annars antas det vara ett meddelande till betalningsmottagaren).
		// Notera att OCR-numrets kontrollsiffra skall kontrolleras.
	}
}
