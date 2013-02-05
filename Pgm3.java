import Transaktioner.*;


public static class Pgm1{
	public static void main(String args) {
	
        System.out.println("\n*** Wera's betalservice - Pgm3 ***");
        System.out.println("\n* Detta program listar transaktioner *");

        String huvudMeny =
        "1. Arkivera forfallna transaktioner\n" +
        "2. Lista alla utforda transaktioner\n" +
        "3. Avsluta\n" +
        "Pgm3:>";

        boolean avsluta = false; // Huvudmenyn är igång sålänge 0
        while(!avsluta) {
            System.out.print(huvudMeny);
            switch (tbScanner.nextLine()){
                case "1": arkiveraGamlaTransaktioner(); break;
                case "2": skapaKonto(); break;
                case "0": avsluta = avsluta(); break;       // avsluta programmet?
                default: System.out.println("Forsok igen! (0-2)"); break; // Fel inmatning
            }
        }
        System.out.print("Avslutar. Tack och hej.");
    }

}

/** Flytta (gallra) gamla obetalda räkningar (förföll för en vecka sedan men
 *  som ej är betalda) till nytt filnamn (filen får inte finnas som man
 *  skriver in vilket filnamn man vill spara till).
 */
arkiveraGamlaTransaktioner(Transaktioner transaktioner){
	System.out.println("Ange filnamn att spara till.");
	
	boolean filRedanFinns = true;
	File fil = new File(tbScanner.next());

	while(filRedanFinns) {
		System.out.print("Filnamn: ");
		File fil = new File(tbScanner.next());
		if (!fil.exists()) filRedanFinns = false;
		else System.out.println("Finns redan, ange nytt namn.");
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
listaTransaktioner(){
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