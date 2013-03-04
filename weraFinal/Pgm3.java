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

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        System.out.println("-= Pgm3, Hanterar gamla transaktioner =-\n");
        
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
                	System.out.println("Avslutar applikationen");
                	break;
                	
                default: 
                	System.out.println("Forsok igen! (0-2)"); 
                	break;
            }
        }
    }


    /** 
     * Moves and archives old, non-executed transactions into an archive file.
     * 
     * Makes sure the file to archive to doesn't exist, and shows amount of 
     * archived transactions
     */
    private static void arkiveraGamlaTransaktioner(){
    	File archive;

    	System.out.print
    		("Mata in ett nytt filnamn att arkivera till: ");
    	archive = new File(tbScanner.nextLine());
    	
    	while(archive.exists()){
    		System.out.print
    			("Filen existerar redan; var god mata in ett nytt filnamn: ");
    		archive = new File(tbScanner.nextLine());
    	}    	
    	tbScanner.reset();
    	System.out.println("Sparar till: " + archive);
    	
     // Hämtar datum en vecka tillbaka
    	Calendar c = Calendar.getInstance();
        c.add(Calendar.WEEK_OF_YEAR, -1);
        
     // Försöker arkivera till den nya filen 
        try {
        	int antalArkiveradeTransaktioner;
        	antalArkiveradeTransaktioner = m.archiveTransactions(c.getTime(), archive);
        	System.out.println("Arkiverade " + antalArkiveradeTransaktioner + " transaktioner");
        } catch (IOException e) {
        	System.out.println("Kunde inte skriva till arkivfilen!");
        }        
    }//slut på arkiveraGamlaTransaktioner

    
    /** 
     * Lists made transactions, searchable by either Date or Account number.
     */
    private static void listaTransaktioner(){
    	ArrayList<GjordTransaktion> logs 
    		= new ArrayList<GjordTransaktion>();
        System.out.print("Vill du lista transaktioner efter\n" +
        				 "1. kontonummer  2. datum\nAnge ditt val: ");
        String datumEllerKonto ="konto";
        
        switch (tbScanner.nextLine()){
        case "1" :
        	datumEllerKonto = "konto";
        	
        	break;
        case "2" :
        	datumEllerKonto = "datum";
        	break;
        case "0" :
        	System.out.println("Avbryter.");
        	return;
        default :
        	System.out.println("Fel inmatning. Avbryter.");
        	return;
        }

        if (datumEllerKonto == "konto"){ 
		    System.out.print
		    	("Ange det konto vars transaktioner du vill visa: ");
		    String svar; 
		    while (true){
		    	svar = tbScanner.nextLine();
		    	if (m.accountExists(svar)){
		    		System.out.println
		    			("Kontot hittades, söker i transaktionsloggen...");
		    		break;
		    		
		    	} else { 
		    		System.out.print("Inte ett giltigt konto. Forsok igen: ");
		    	}
		    }
		    
		    logs = m.getLogsByAccountNumber(svar);
		    
    	}
        else if (datumEllerKonto == "datum"){ // Försöker läsa in ett datum
		    System.out.print("Ange det datum transaktionen(erna) genomforts: (yyyyMMdd)");
	        SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd");
		    Date svar; 
		    String temp;
		    while (true){ // loopar tills ett datum ar inmatat
		    	try {// testa om datum
		    		temp = tbScanner.nextLine();
		    		svar = dFormat.parse(temp);
		    	}catch (ParseException | NumberFormatException e) { // om inte ett datum.. 
		    		System.out.println("Använd formatet yyyyMMdd!");
		    		continue; //börja om loop
		    	}
		    	break; // annars avbryt loop;
		    }
		    // listar transaktioner under ett visst datum
		    System.out.println("Forsoker lista transaktioner under ett visst datum..");
		    logs = m.getLogsAfter(svar);
        } else {
        	System.out.println("Detta alternativ finns inte.");        	
        }
        
        if(logs.isEmpty()) {
        	System.out.println("Hittade inga rader!");
        	
        } else {
        	for(GjordTransaktion log : logs) {
        		System.out.println(log.toFileString());        		
        	}       	
        }
        	
        	
    }
}
