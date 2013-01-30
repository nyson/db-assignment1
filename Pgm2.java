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


/**   
 * I denna klass skall...
 * @version      1.0 30 Jan 2013
 * @author       xxx, xxx, xxx
 */
public class Pgm1 {
       
    /**        
     * visar information om ett konto
     */
    public static void listaKonton(String konto) {
        // om kontot finns
            // Visa snyggt ägare och saldo om detta kontot
        // annars ???        
    }
    
    /**        
     * visar information om alla konton
     */
    public static void listaKonton() {
        // Visa snyggt konto, ägare och saldo alla konton
    }

    /**        
     * Lägga till konto/betalningsmottagare
     */
    public static void nyttKonto() {
        // kontroll så att det inte redan finns ett konto med det numret.
            // Valfritt är att lägga in en funktion som slumpar fram ett kontonummer.
            // Ett kontonummer får inte bestå av fler än 12 tecken inklusive max/minst ett bindestreck.
    }

    /**        
     * Sätta in pengar på konto.
     */
    public static void sattInPengar() {
        // just do it
    }

    /**        
     * Ta ut pengar från konto.
     */
    public static void taUtPengar() {
        // just do it
    }
 
    /**        
     * Lägga in överföringar och räkningar.
     */
    public static void registreraTransaktion() {
        // Se klasserna Transaktion för information om vad som skall matas in för en räkning/överföring.
        // En minnesantackning för en betalning/överföring får vara max 15 tecken.
        // Det ska gå att välja att skicka antingen ett OCR-nummer eller ett meddelande till mottagaren av betalningen/överföringen.
        // Ett meddelande/OCR-nummer får vara minst ett tecken och max 15 tecken. (Om endast siffror har angivits så antas det vara ett OCR-nummer annars antas det vara ett meddelande till betalningsmottagaren).
        // Notera att OCR-numrets kontrollsiffra skall kontrolleras.
    }

}