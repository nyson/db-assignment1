/*
 * @(#)Pgm1.java        1.0 2013-01-30
 *
 * Funktionalitet
 * Betala räkningar om förfallodatum nåtts (betaldatum är dagens datum) och om
 * det finns tillräckligt med pengar på kontot. När en betalning gjorts så
 * skall betalningen tas bort från bevakning och endast kunna hittas i
 * transaktionsloggen.
 *
 */


import java.io.*;
import java.util.*;

/**   
 * I denna klass skall...
 * @version      1.0 30 Jan 2013
 * @author       xxx, xxx, xxx
 */
public class Pgm1 {
    public static void main(String[] args) {
    	Metoder m;
        System.out.println("\n*** Wera's betalservice - Pgm1 ***");
        System.out.println
        	("\n* Detta program utfor alla annu ej utforda transaktioner *");
        System.out.println
        	("Utför utgångna transaktion mindre an en vecka gamla...");
        
        try {
        	m = buildMetoder();        	
        } catch (IOException e) {
        	System.out.println("Kunde inte hitta en eller flera av de angivna"
        			+ " filerna, var god försök igen. \n" 
        			+ "Working dir: " + new File("").getAbsolutePath() + "\n"
        			+ e);
        	return;
        }
        
        Calendar c = Calendar.getInstance();
        c.add(Calendar.WEEK_OF_YEAR, -1);
        Date lastWeek = c.getTime();

        m.executeAllTransactionsBetween(lastWeek, new Date());
        /*        
        try {
        	m.saveChanges();
        } catch (IOException e) {
        	System.out.println("Kunde inte spara ändringar: " + e.getMessage());
        }
       
        */
    }
    
    /**
     * Creates a method object for handling bank business
     * @return a freshly baked Metoder object
     * @throws FileNotFoundException
     */
    private static Metoder buildMetoder() 
    	throws FileNotFoundException, IOException{
    	BufferedReader in 
    		= new BufferedReader(new InputStreamReader (System.in));
    	String temp, accounts, log, survey;
    	
    	String defaultAccountsFile = "utf8data/_Konton.txt";
    	String defaultLogFile = "utf8data/_GjordaTransaktioner.txt";
    	String defaultSurveillanceFile = "utf8data/_Bevakning.txt";
    	
    	System.out.println("Var god och mata in dina filer, eller tryck bara "
    			+ "enter för att ange den förvalda inställningen.");
    	
    	System.out.print("Kontofil ("+defaultAccountsFile+"): ");
    	temp = in.readLine();
    	if(temp.trim().length() > 0) 
    		accounts = temp;
    	else {
    		System.out.println("Använder '"+defaultAccountsFile+"'");
    		accounts = defaultAccountsFile;
    	}
    	
    	System.out.print("Logfil ("+defaultLogFile+"): ");
    	temp = in.readLine();
    	if(temp.trim().length() > 0)
    		log = temp;
    	else {
    		System.out.println("Använder '"+defaultLogFile+"'");
    		log = defaultLogFile;
    	}
    	
    	System.out.print("Bevakningsfil ("+defaultSurveillanceFile+"): ");
    	temp = in.readLine();
    	if(temp.trim().length() > 0)
    		survey = temp;
    	else {
    		System.out.println("Använder '"+defaultSurveillanceFile+"'");
    		survey = defaultSurveillanceFile;
    	}

    	in.close();
    	return new Metoder(accounts, log, survey);
    } 
}