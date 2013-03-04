/**
 *
 * Maintains accounts. Here we can list our accounts and any info connected to 
 * them, make new accounts or create transactions.
 * 
 * @author Jonathan Skårstedt
 * @author Oskar Pålsgård
 * @author Magnus duberg
 * 
 * @version 1.0
 */

import java.io.*;
import java.text.*;
import java.util.*;

public class Pgm2 {

	private static Scanner tbScanner = new Scanner(System.in);
	private static Metoder m;

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
			"3. Sätt in pengar     0. Avsluta\n" +
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
	 * Shows information of a given account or, if no account is given, all
	 * accounts.
	 */
	private static void listaKonton() {
		System.out.print
			("Ange kontonummer eller enter for att se alla konton: ");
		String accountNumber = tbScanner.nextLine();
		
		if (accountNumber.trim().length() > 0) {
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
	 * Add new account to our bank. 
	 */
	private static void skapaKonto() {
		String number, name, owner;
		double amount;
		String val;
		Konto k;
		Random Numb = new Random();

		do {
		System.out.print
			("Ange kontonummer eller en tom rad för slumpat kontonummer :");

		val = tbScanner.nextLine();
			if (val.length() > 0) {
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
	 * Deposits money into an account.
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
	 * Withdraws money into an account.
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
	 * Registers transaction. 
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

		System.out.print("Ange summa: ");
		try {
			amount = Double.parseDouble(tbScanner.nextLine().replace(",","."));
		} catch(NumberFormatException e) {
			System.out.println("Det där är inte ett giltigt tal.");
			return;
		}
		
		System.out.print("Skriv i ett OCR eller ett medelande :");
		ocr = tbScanner.nextLine();
		
		if(!m.validOcr(ocr)) {
			System.out.println("Din inmatning registreades som ett meddelande.");
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
		
	}
}
