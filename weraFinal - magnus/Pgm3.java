import java.util.*;
import java.io.*;

public class Pgm3{
	private static Metoder m;
    private static Scanner tbScanner = new Scanner(System.in);

	public static void main(String[] args) {
        System.out.println("\n*** Wera's betalservice - Pgm3 ***");
        System.out.println("*** Detta program listar transaktioner ***");

		try {
			m = Metoder.buildMetoder();
		} catch (IOException e) {
			System.out.println("Kunde inte öppna angivna filer.");
			return;
		}        
        
        String huvudMeny =
        "1. Arkivera forfallna transaktioner\n" +
        "2. Lista alla utforda transaktioner\n" +
        "0. Avsluta\n" +
        "Pgm3:>";

        boolean avsluta = false; // Huvudmenyn är igång sålänge 0
        while(!avsluta) {
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
                	break; // Fel inmatning
            }
        }
    }


    /** Flytta (gallra) gamla obetalda räkningar (förföll för en vecka sedan men
    *  som ej är betalda) till nytt filnamn (filen får inte finnas som man
    *  skriver in vilket filnamn man vill spara till).
    */
    private static void arkiveraGamlaTransaktioner(){
    	File archive;
    	System.out.println("Välkommen till arkiveraren!");
    	System.out.print
    		("Vänligen mata in ett nytt filnamn att arkivera till: ");
    	archive = new File(tbScanner.nextLine());
    	
    	while(archive.exists()){
    		System.out.print
    			("Filen existerar redan; var god mata in ett nytt filnamn: ");
    		archive = new File(tbScanner.nextLine());
    	}
    	
    	tbScanner.reset();        
        System.out.println("Sparar till: " + archive);

        Calendar c = Calendar.getInstance();
        c.add(Calendar.WEEK_OF_YEAR, -1);
        
        try {
        	m.archiveTransactions(c.getTime(), archive); 
        } catch (IOException e) {
        	System.out.println("Kunde inte skriva till arkivfilen!");
        }
        
    }


    /** Lista alla genomförda transaktioner. För detta ändamål skall klassen
    *  GjordTransaktion skapas och den skall ärva Transaktion. I klassen
    *  GjordTransaktion sparas transaktionsnoteringen och transaktionsdatum.
    *  I klassen Transaktion sparas önskat betaldatum samt betalningsnotering
    *  tillsammans med övriga variabler. 
    */
    /** Listningen på genomförda transaktioner skall kunna sökas fram baserat på:
    *	* Kontonummer
    *	* Transaktionsdatum
    */
    private static void listaTransaktioner(){
        // hämta array på alla genomförda transaktioner
        	// (loggfil -> GjordTransaktion)
        System.out.println("listar gjordaTransaktioner efter antingen kontonr...");	
        System.out.println("...eller transaktionsdatum.");	
    }


    /**        
     * metod avsluta()
     * Returnerar vilken menyNivå programmet skall fortsätta på
     */
    private static boolean avsluta(){
        System.out.print("Avsluta? ");
        String svar = "";

        while (true) { // sålänge varken ja eller nej angetts
            System.out.print("j/n:> ");
            svar = tbScanner.nextLine(); // läs
            if (svar.equals("j")) {                  // om ja
                spara(); return true;
            } else if (svar.equals("n")) {            // om nej
                return false;
            }
        }
    }

    private static void spara(){
        System.out.print("Sparar... ");
        try {Thread.sleep(1300);}catch(Exception e){
            System.out.println("kunde inte pausa" + e.toString());}
    }
}