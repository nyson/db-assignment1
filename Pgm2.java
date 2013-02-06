/*
 * @(#)Pgm2.java        1.0 2013-01-30
 *
Lista konton/ betalningsmottagare samt tillgängligt saldo. 
Om man anger ett konto nummer så ska information endast visas om det kontonumret.
Om man inte anger ett konto nummer så skall information om alla kontonummer visas.
Lägga till konto/betalningsmottagare
När man lägger in ett nytt kontonummer så skall kontroll ske så att det inte redan finns ett konto med det numret. Valfritt är att lägga in en funktion som slumpar fram ett kontonummer.
Ett kontonummer får inte bestå av fler än 12 tecken inklusive max/minst ett bindestreck.
Sätta in pengar på konto.
Ta ut pengar från konto.
Lägga in överföringar och räkningar. Se klasserna Transaktion för information om vad som skall matas in för en räkning/överföring.
En minnesantackning för en betalning/överföring får vara max 15 tecken.
Det ska gå att välja att skicka antingen ett OCR-nummer eller ett meddelande till mottagaren av betalningen/överföringen.
Ett meddelande/OCR-nummer får vara minst ett tecken och max 15 tecken. (Om endast siffror har angivits så antas det vara ett OCR-nummer annars antas det vara ett meddelande till betalningsmottagaren).
Notera att OCR-numrets kontrollsiffra skall kontrolleras. I
 *
 */

// import java.awt.*;
import java.io.*;
import java.util.*;

/**   
 * Pgm2 presenterar en meny for foljande tjanster:
 * Lista konton, satt in/ta ut pengar, skapa konto och registrera transaktioner.
 * @version      1.0 30 Jan 2013
 * @author       xxx, xxx, xxx
 */
public class Pgm2 {

    private static Scanner tbScanner = new Scanner(System.in);

    /**
     *  Mainmetod med while-loop för Pgm2's val
     */
    public static void main(String[] args){

        System.out.println("\n*** Wera's betalservice - Pgm2 ***");
        System.out.println("\n* Detta program gor foljande: *");

        String huvudMeny =
        "1. Lista konto        4. Ta ut pengar\n" +
        "2. Skapa nytt konto   5. Registrera ny transaktion\n" +
        "3. Satt in pengar     0. Avsluta\n" +
        "Pgm2:>";

        boolean avsluta = false; // Huvudmenyn är igång sålänge 0
        while(!avsluta) {
            System.out.print(huvudMeny);
            switch (tbScanner.nextLine()){
                case "1": listaKonton(); break;             // Val 1. Konto(n)
                case "2": skapaKonto(); break;              // Val 2. Skapa nytt konto
                case "3": sattInPengar(); break;            // Val 3. Sätt in pengar
                case "4": taUtPengar(); break;              // Val 4. Ta ut pengar                
                case "5": registreraTransaktion(); break;   // Val 5. Registrera transaktion               
                case "0": avsluta = avsluta(); break;       // avsluta programmet?
                default: System.out.println("Forsok igen! (0-5)"); break; // Fel inmatning
            }
        }
        System.out.print("Avslutar. Tack och hej.");
    }

    /**        
     * kontrollerar om ett kontonummer existerar
     */
    private static boolean giltigtKontonummer(String kontoNummer){
            System.out.println("Kollar om kontot " + kontoNummer + " finns..");
            try {Thread.sleep(2000);}catch(Exception e){
                System.out.println("kunde inte pausa" + e.toString());}
        return true;
    }

    /**        
     * visar information om ett konto
     */
    private static void listaKonton() {
        System.out.print("Ange kontonummer (Enter for att se alla konton): ");
        String kontoNummer = tbScanner.nextLine();
        if (kontoNummer.length() > 0) {  // om ett konto skall visas?
            if (giltigtKontonummer(kontoNummer)) {
                System.out.println("Visar info om kontot"); // Visa snyggt ägare och saldo om detta kontot
            } else {
                System.out.println("Annars meddela att kontot inte finns");
            }
            tbScanner.nextLine();
        } else { // Visa alla konton, snyggt med kontonr, ägare och saldo 
            System.out.print("Konto hit, konto dit, ags av hen, har si mycket saldo...");
            tbScanner.nextLine();    
        }
    }
    
    /**        
     * Lägga till konto/betalningsmottagare
     */
    private static void skapaKonto() {
        // kontroll så att det inte redan finns ett konto med det numret.
            // Valfritt är att lägga in en funktion som slumpar fram ett kontonummer.
            // Ett kontonummer får inte bestå av fler än 12 tecken inklusive max/minst ett bindestreck.
    }

    /**        
     * Sätta in pengar på konto.
     */
    private static void sattInPengar() {
        // just do it
    }

    /**        
     * Ta ut pengar från konto.
     */
    private static void taUtPengar() {
        // just do it
    }
 
    /**        
     * Lägga in överföringar och räkningar.
     */
    private static void registreraTransaktion() {
        // Se klasserna Transaktion för information om vad som skall matas in för en räkning/överföring.
        // En minnesantackning för en betalning/överföring får vara max 15 tecken.
        // Det ska gå att välja att skicka antingen ett OCR-nummer eller ett meddelande till mottagaren av betalningen/överföringen.
        // Ett meddelande/OCR-nummer får vara minst ett tecken och max 15 tecken. (Om endast siffror har angivits så antas det vara ett OCR-nummer annars antas det vara ett meddelande till betalningsmottagaren).
        // Notera att OCR-numrets kontrollsiffra skall kontrolleras.
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