/** Pgm3.java
 * 
 * Program for archiving and listing transactions
 * 
 * @author Jonathan Skårstedt
 * @author Oskar Pålsgård
 * @author Magnus Duberg
 * 
 * Version 1.0
 */

import java.util.*;
import java.io.*;

public class Pgm3{
	private static Metoder m;
    private static Scanner tbScanner = new Scanner(System.in);

    /**
     * Main method
     * 
     * Generally flows into two methods, to either archive old transactions or
     * to list logged ones.
     * 
     * @param args NA: Command line arguments
     */
	public static void main(String[] args) {
        System.out.println("\n-= Pgm3, Hanterar gamla transaktioner =-\n");
        
		try {
			m = Metoder.buildMetoder();
		} catch (IOException e) {
			System.out.println("Kunde inte öppna angivna filer: " 
					+ e.getMessage());
			return;
		}        
        
        String huvudMeny =
        "========================================\n" +
        "== Meny ================================\n" +
        "========================================\n" +
        "1. Arkivera forfallna transaktioner\n" +
        "2. Lista alla utforda transaktioner\n" +
        "0. Avsluta\n" +
        "Ange ditt val: ";

        boolean avsluta = false; 
        while(!avsluta) { // 
            System.out.print(huvudMeny);
            switch (tbScanner.nextLine()){
                case "1": 
                	arkiveraGamlaTransaktioner(); 
                	break;
                	
                case "2": 
                	listaTransaktioner(); 
                	break;
                	
                case "0": 
                	avsluta = true;
                	break;
                	
                default: 
                	System.out.println("Forsok igen! (0-2)"); 
                	break;
            }
        }
        // Sparar alla ändringar
        try {
        	m.saveChanges();
        }catch(IOException e){
        	System.out.print("Fel vid sparandet. Exception:" + e);
        }
        // Avslutar programmet
        System.out.println("Avslutar.");
    }


    /** 
     * Moves and archives old, non-executed transactions into an archive file.
     * 
     * Makes sure the file to archive to doesn't exist, and shows amount of 
     * archived transactions
     */
    private static void arkiveraGamlaTransaktioner(){
    	File newArchiveFile = null;

		System.out.print ("Mata in ett nytt filnamn att arkivera till: ");
		Boolean unikFil = false;
		do {
			String newFileName = tbScanner.nextLine();
    		if (new File("datafiler/"+newFileName).exists()){
    				System.out.print("Filen finns redan. Mata in ett nytt filnamn: ");
    		}else{
    			newArchiveFile = new File(newFileName);
    			unikFil = true;
    		}
    	}while(!unikFil);	
    	tbScanner.reset();
		    	
     // Hämtar datum en vecka tillbaka
    	Calendar c = Calendar.getInstance();
        c.add(Calendar.WEEK_OF_YEAR, -1);
        
     // Försöker arkivera till den nya filen 
    	System.out.println("Sparar till: " + newArchiveFile);
        try {
        	int antalArkiveradeTransaktioner;
        	antalArkiveradeTransaktioner = m.archiveTransactions(c.getTime(), newArchiveFile);
        	System.out.println("Arkiverade " + antalArkiveradeTransaktioner + " transaktioner");
        } catch (IOException e) {
        	System.out.println("Kunde inte skriva till arkivfilen!");
        }        
    }//slut på arkiveraGamlaTransaktioner

    
    /** 
     * Lists made transactions, sorted by either Date or Account number.
     */
    private static void listaTransaktioner(){
    	System.out.print("Vill du lista transaktioner sorterat efter\n" +
    			"1. kontonummer  2. datum\n0. Avbryt    Ange ditt val: ");
        do {
	        switch (tbScanner.nextLine()){ //Förlåt, String endast java 7+
	        case "1" : m.printLogs(m.getLogsSortedByAccountNumber()); return;
	        case "2" : m.printLogs(m.getLogsSortedByDate()); return;
	        case "0" : return;        	
	        default  : System.out.println("Fel inmatning. Försök igen");}
	    }while (true);
    }
}
