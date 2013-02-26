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

	private static Scanner tbScanner;
	private static Metoder m;

	/**
	 *  Mainmetod med while-loop för Pgm2's val
	 */
	public static void main(String[] args){
		System.out.println("\n*** Wera's betalservice - Pgm2 ***"
				+ "Detta program låter dig lista "
				+ "konton och göra andra bankärenden!");


		try {
			m = Metoder.buildMetoder();
		} catch (IOException e) {
			System.out.println("Kunde inte öppna angivna filer.");
			return;
		}		

		tbScanner = new Scanner(System.in);

		System.out.println("\n* Detta program gor foljande: *");


		String huvudMeny =
			"1. Lista konto        4. Ta ut pengar\n" +
			"2. Skapa nytt konto   5. Registrera ny transaktion\n" +
			"3. Satt in pengar     0. Avsluta\n" +
			"Gör ditt val: ";

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
			Konto k = m.findAccount(accountNumber);

			if (k != null) {
				System.out.println(Metoder.accountToString(k));
			} else {
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
	 * Lägger till Konto
	 */
	private static void skapaKonto() {
		String number, name, owner;
		double amount;
		Konto k;
		
		System.out.println("Välkommen att skapa ett nytt konto!");
		System.out.print("Skriv in kontonummer: ");
		number = tbScanner.nextLine();

		System.out.print("Skriv in kontonamn: ");
		name = tbScanner.nextLine();

		System.out.print("Skriv in ägarens namn: ");
		owner = tbScanner.nextLine();
	
		System.out.print("Skriv in saldo: ");
		amount = tbScanner.nextDouble();
		
		k = new Konto(number, amount, name, owner);
		m.addAccount(k);
		
		System.out.println("Ditt nya konto är inlagt!\n"
			+ "Det här är ditt nya konto: "
			+ Metoder.accountToString(k));
	}

	/**        
	 * Sätta in pengar på konto.
	 */
	private static void sattInPengar() {
		System.out.println("Välkommen till pengainsättningen!");
		System.out.print("Skriv in kontonummer: ");
		Konto k = m.findAccount(tbScanner.nextLine());
		
		if(k == null) {
			System.out.println("Finns inget konto med det numret!");
			return;
		}
		
		System.out.println("Konto: " + Metoder.accountToString(k));
		System.out.print
			("Vänligen skriv i hur mycket pengar ni vill sätta in: ");
		
		double amount = tbScanner.nextDouble();
		
		k.depositAmount(amount);
		
		System.out.println("Du har nu satt in " + amount + " pengar på "
			+ "ditt konto: \n" + Metoder.accountToString(k));
	}

	/**        
	 * Ta ut pengar från konto.
	 */
	private static void taUtPengar() {
		System.out.println("Välkommen till pengauttagningen!");
		System.out.print("Skriv in kontonummer: ");
		Konto k = m.findAccount(tbScanner.nextLine());
		
		if(k == null) {
			System.out.println("Finns inget konto med det numret!");
			return;
		}
		
		System.out.println("Konto: " + Metoder.accountToString(k));
		System.out.print
			("Vänligen skriv i hur mycket pengar ni vill ta ut: ");
		
		double amount = Double.parseDouble(tbScanner.nextLine());
		
		k.depositAmount(amount);
		
		System.out.println("Du har tagit ut " + amount + " pengar på "
			+ "ditt konto: \n" + Metoder.accountToString(k));
		
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

		System.out.print("Skriv i avsändarens kontonummer: ");
		source = m.findAccount(tbScanner.nextLine());
		
		if(source == null) {
			System.out.println("Det finns inget konto med det numret!");
			return;
		}
			
		System.out.print("Skriv i mottagarens kontonummer: ");
		destination = m.findAccount(tbScanner.nextLine());
		
		if(destination == null) {
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
		
		System.out.print("Skriv i ett OCR: ");
		ocr = tbScanner.nextLine();
		
		if(ocr.length() > 15 || ocr.length() <= 1 || !m.validOcr(ocr)) {
			System.out.println("Ditt inmatade OCR var ogiltigt!");
			return;
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