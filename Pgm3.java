import Transaktioner.*;


public static class Pgm1{
	public static void main(String args) {
	
}

/** Flytta (gallra) gamla obetalda räkningar (förföll för en vecka sedan men
 *  som ej är betalda) till nytt filnamn (filen får inte finnas som man
 *  skriver in vilket filnamn man vill spara till).
 */
gallraGamlaTransaktioner(Transaktioner transaktioner);


/** Lista alla genomförda transaktioner. För detta ändamål skall klassen
 *  GjordTransaktion skapas och den skall ärva Transaktion. I klassen
 *  GjordTransaktion sparas transaktionsnoteringen och transaktionsdatum.
 *  I klassen Transaktion sparas önskat betaldatum samt betalningsnotering
 *  tillsammans med övriga variabler. 
 */
listaTransaktioner();

/** Listningen på genomförda transaktioner skall kunna sökas fram baserat på:
 *	* Kontonummer
 *	* Transaktionsdatum
 */