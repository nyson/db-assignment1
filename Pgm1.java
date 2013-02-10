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
import java.util.Date;

/**   
 * I denna klass skall...
 * @version      1.0 30 Jan 2013
 * @author       xxx, xxx, xxx
 */
public class Pgm1 {
       
    /**          
	 * privat variabel
     */
    private int variabel;

    /**        
     * main  ...
     */
    
    public static void main(String[] args) {

        System.out.println("\n*** Wera's betalservice - Pgm1 ***");
        System.out.println
        	("\n* Detta program utfor alla annu ej utforda transaktioner *");
        System.out.println
        	("Utför utgångna transaktion mindre an en vecka gamla...");
        
    }
   
}