/*
 * @(#)Pgm1.java        1.0 2013-01-30
 *
 * Funktionalitet
 * Betala rÃ¤kningar om fÃ¶rfallodatum nÃ¥tts (betaldatum Ã¤r dagens datum) och om
 * det finns tillrÃ¤ckligt med pengar pÃ¥ kontot. NÃ¤r en betalning gjorts sÃ¥
 * skall betalningen tas bort frÃ¥n bevakning och endast kunna hittas i
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
        System.out.println("\n-= Pgm 1, Betalar rakningar =-");
        System.out.println("Utfor transaktioner i bevakningsfilen mindre");
        System.out.println("an en vecka gamla om det finns tackning.\n");

        // Ladda datafilerna
        try {
        	m = Metoder.buildMetoder();
        } catch (IOException e) {
        	System.out.println("Fel vid läsning av någon av de angivna"
        			+ " datafilerna, var god försök igen. \n" 
        			+ "Sökväg: " + new File("").getAbsolutePath() + "\n"
        			+ "Undantag: " + e.getMessage());
        	return;
        }

        // Skapa en Date en vecka tillbaka i tiden
        Calendar c = Calendar.getInstance();
        c.add(Calendar.WEEK_OF_YEAR, -1);
        Date lastWeek = c.getTime();

        // Utför alla transaktioner en vecka tillbaka fram till idag
        try {
        	m.executeAllTransactionsBetween(lastWeek, new Date());
        } catch (Exception e) {
        	System.out.println("Kunde inte utföra transaktionerna:\n" + e.getMessage());
        }
        
        // Sparar alla ändringar
        try {
        	m.saveChanges();        	
        } catch (IOException e) {
        	System.out.println("Kunde inte spara Ã¤ndringar:\n" + e.getMessage());
        }
        // Programmet slutar
        System.out.println("Finished.");
    }
}