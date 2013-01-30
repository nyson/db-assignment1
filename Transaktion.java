
/* @(#)Transaktioner.java        1.00 2013-01-30
 *
 * I denna klass skall informationen från textfilen _Bevakning.txt läggas
 * under programkörning. Klassen skall innehålla lämpliga get- och 
 * set-metoder samt nedan variabler.
 * 
 * variabler            Lagringsform    Exempel på värden
 *
 * sourceAccount        String          8888-8888
 * destinationAccount   String          5453-7834851
 * amount               double          500,00
 * ocrMessage           String          302848274820184
 * date                 Date            20130228
 * notering             String          Medlemsavgift
 */

import java.io.*;
import java.util.Date;

/**   
 * I denna klass skall informationen från textfilen _Bevakning.txt läggas under programkörning.
 * @version      1.0 30 Jan 2013
 * @author       Magnus Duberg, xxx, xxx
 */
public class Transaktion {
       
    /**          
	 * sourceAccount = källkontot
     */
    private String sourceAccount;

    /**
	 *destinationAccount = mottagarkontot
	 */
    private String destinationAccount;

    /**  
     * amount = summan att överföra
     */
    private double amount;

	/**
	 * ocrMessage = OCR-kod eller meddelande
	 */
    private String ocrMessage;

    /**
	 * date = datum då överföringen skall utföras
	 */
    private Date date;

	/**
	 * note = anteckning
	 */
    private String note;

    /**
	 * Konstruktor 2/2 för klassen Transaktion 
	 * @param sourceAccount			källkonto
     */
	public Transaktion(String sourceAccount, String destinationAccount, double amount, String ocrMessage, Date date, String notering) throws Exception{          
      // ...implementation goes here...
    }

    /**         
	 * Konstruktor 1/2 för klassen Transaktion
	 * @param accountLine			sträng med all data (format:)
     */
	public Transaktion(String accountLine) throws Exception{          
      // ...implementation goes here...
    }

    /**        
     * method getSourceAccount documentation comment...
     * @return		returnerar källkontot
     */
    public String getSourceAccount() {
        // ...implementation goes here... 
        return sourceAccount;
    }

    /**
     * method setDestinationAccount  
     * @param destinationAccount  	målkontot   
     * method setDestinationAccount documentation comment...
     */
    public void setDestinationAccount(String destinationAccount) {
       // ...implementation goes here... 
    }
}