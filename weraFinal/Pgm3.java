/*Funktionalitet
 * 1
 * - Flytta (gallra) gamla obetalda räkningar (förföll för en vecka sedan men
 *  som ej är betalda) till nytt filnamn
 * (filen får inte finnas som man skriver in vilket filnamn man vill spara till).
 * - När en gallring gjorts skall en utskrift till skärm göras där det anges antal gallrade poster (rader).
 * 
 * 2
 * - Lista alla genomförda transaktioner. För detta ändamål skall klassen GjordTransaktion skapas och den
 * skall ärva Transaktion. I klassen GjordTransaktion sparas transaktionsnoteringen och transaktionsdatum. I
 * klassen Transaktion sparas önskat betaldatum samt betalningsnotering tillsammans med övriga variabler.
 * - Listningen på genomförda tansaktioner skall kunna sökas fram baserat på:
 * o Kontonummer
 * o Transaktionsdatum 
 */




import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

public class Pgm3{
	private static Metoder m;
    private static Scanner tbScanner = new Scanner(System.in);

	public static void main(String[] args) {
        System.out.println("-= Pgm3, Hanterar gamla transaktioner =-\n");
        
		try {
			m = Metoder.buildMetoder();
		} catch (IOException e) {
			System.out.println("Kunde inte oppna angivna filer.");
			return; // Avslutar programmet
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
                	break; // Fel inmatning
            }
        }
    }

    /** 
	 * Flyttar (gallra) gamla obetalda räkningar (förföll för en vecka sedan men
	 *  som ej är betalda) till nytt filnamn
	 * (filen får inte finnas som man skriver in vilket filnamn man vill spara till).
	 * - När en gallring gjorts skall en utskrift till skärm göras där det anges antal gallrade poster (rader).
     */
    private static void arkiveraGamlaTransaktioner(){
   	 // Välj fil att arkivera till
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
        	// TODO 
        	m.archiveTransactions(c.getTime(), archive); 
        } catch (IOException e) {
        	System.out.println("Kunde inte skriva till arkivfilen!");
        }        
    }//slut på arkiveraGamlaTransaktioner

    /** 
     * Lista alla genomfÃ¶rda transaktioner. FÃ¶r detta Ã¤ndamÃ¥l skall klassen
     *  GjordTransaktion skapas och den skall Ã¤rva Transaktion. I klassen
     *  GjordTransaktion sparas transaktionsnoteringen och transaktionsdatum.
     *  I klassen Transaktion sparas Ã¶nskat betaldatum samt betalningsnotering
     *  tillsammans med Ã¶vriga variabler. 
     * 
     * Listningen pÃ¥ genomfÃ¶rda transaktioner skall kunna sÃ¶kas fram baserat pÃ¥:
     *	Kontonummer
     *	Transaktionsdatum
     */
    private static void listaTransaktioner(){
        
        System.out.print("Vill du lista transaktioner efter\n" +
        				 "1. kontonummer  2. datum  (enter avbryter)\nAnge ditt val: ");
        String datumEllerKonto ="konto";
        switch (tbScanner.nextLine()){
        case "1" :
        	datumEllerKonto = "konto";
        	break;
        case "2" :
        	datumEllerKonto = "datum";
        	break;
        case "0" :
        	return;
        default :
        	return;
        }

        if (datumEllerKonto == "konto"){ // 
        	// System.out.println
        	//	("...eller transaktionsdatum.");
		    System.out.print("Ange det konto vars transaktioner du vill visa: ");
		    String svar; 
		    while (true){
		    	svar = tbScanner.nextLine();
		    	if (m.accountExists(svar)){ // testa om giltigt konto
		    	//if (true){ // *** DUMMY *** testa om giltigt konto
			    	break; // avbryt loop
		    	}
		    	else{ // annars (inte ett konto
		    		System.out.print("Inte ett giltigt konto. Forsok igen: ");
		    		continue; // bï¿½rja om loop
		    	}
		    }
		    //System.out.println("Forsoker lista transaktioner tillhorande ett visst konto..");
		    m.getLogsByAccountNumber(svar);        	
    	}
        else if (datumEllerKonto == "datum"){ // Fï¿½rsï¿½ker lï¿½sa in ett datum
		    System.out.print("Ange det datum transaktionen(erna) genomforts: (yyyyMMdd)");
	        SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd");
		    Date svar; 
		    while (true){ // loopar tills ett datum ar inmatat
		    	try {// testa om datum
		    		svar = dFormat.parse(tbScanner.nextLine());  
		    	}catch (ParseException | NumberFormatException e) { // om inte ett datum.. 
		    		System.out.println("Anvï¿½nd formatet yyyyMMdd!");
		    		continue; //bï¿½rja om loop
		    	}
		    	break; // annars avbryt loop;
		    }
		    // listar transaktioner under ett visst datum
		    System.out.println("Forsoker lista transaktioner under ett visst datum..");
		    m.getLogsAfter(svar);
        }
    }
}