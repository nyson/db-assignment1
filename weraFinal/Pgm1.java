package weraFinal;
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
        } catch (FileNotFoundException e) {
        	System.out.println("Kunde inte hitta en eller flera av de angivna"
        			+ "filerna, var god försök igen. \n" + e.getMessage());
        	return;
        }
        
        Date lastWeek = new Date(); 
        m.executeTransactionsAfter(lastWeek);
        m.saveChanges();
    }
    
    /**
     * Creates a method object for handling bank business
     * @return a freshly baked Metoder object
     * @throws FileNotFoundException
     */
    private static Metoder buildMetoder() throws FileNotFoundException{
    	Scanner in = new Scanner(System.in);
    	String temp, accounts, log, survey;
    	
    	String defaultAccountsFile = "./utf8data/_Konto.txt";
    	String defaultLogFile = "./utf8data/_GjordaTransaktioner.txt";
    	String defaultSurveillanceFile = "./utf8data/_Bevakning.txt";
    	
    	System.out.println("Var god och mata in dina filer, eller tryck bara "
    			+ "enter för att ange den förvalda inställningen.");
    	
    	System.out.print("Kontofil ("+defaultAccountsFile+"): ");
    	temp = in.next();
    	if(temp.trim().length() > 0)
    		accounts = temp;
    	else
    		accounts = defaultAccountsFile;
    	
    	System.out.print("Logfil ("+defaultLogFile+"): ");
    	temp = in.next();
    	if(temp.trim().length() > 0)
    		log = temp;
    	else
    		log = defaultAccountsFile;
    	
    	System.out.print("Bevakningsfil ("+defaultSurveillanceFile+"): ");
    	temp = in.next();
    	if(temp.trim().length() > 0)
    		survey = temp;
    	else
    		survey = defaultAccountsFile;
    	
    	
    	
    	    	
    	in.close();
    	return new Metoder(accounts, log, survey);
    } 
}