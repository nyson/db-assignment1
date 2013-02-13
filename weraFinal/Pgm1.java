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
        	m = Metoder.buildMetoder();        	
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

        
        
       
        try {
        	m.executeAllTransactionsBetween(lastWeek, new Date());
        	m.saveChanges();
        	
        } catch (IOException e) {
        	System.out.println("Kunde inte spara ändringar: " + e.getMessage());
        }
       

    }
    
}