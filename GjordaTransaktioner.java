import java.util.*;

class GjordaTransaktioner extends Transaktion {


	public GjordaTransaktioner(Date dueDate, String sourceAccount, 
			String destinationAccount, double amount, 
			String ocrMessage){
		super(dueDate, sourceAccount, destinationAccount, amount, ocrMessage);
	}
	
	public GjordaTransaktioner(Date dueDate, String sourceAccount, 
			String destinationAccount, double amount, 
			String ocrMessage, String notice){
		super(dueDate, sourceAccount, destinationAccount, 
				amount, ocrMessage, notice);		
	}	
    /**
     */
    private String logFormatDeposit(double wAmount) {
	/*
	  "TransaktionsNotering;transaktionsDatum#"
	  + "önskatDatum;KONTANTER;destinationsKontonr;belopp;ocrMsg";
	*/
	return getNotice() + ";" + getDueDate().toString() + "#"
	    + new Date().toString() + ";" 
	    + wAmount + ";" + getDestinationAccount() + ";"
	    + getAmount() + ";" + getOcrMessage(); 
    }

    /**
     */
    private String logFormatWithdrawal(Transaktion t) {
	return "TransaktionsNotering;transaktionsDatum#"
	    + "önskatDatum;KONTANTER;belopp;ocrMeddelande";
    }

    /**
     */
    private String logFormatTransaction(Transaktion t) {
	return "TransaktionsNotering;transaktionsDatum#önskatDatum;"
	    +"källKontonummer;destinationsKonto;belopp;"
	    +"ocrMeddelande;betalningsNotering";
    }

}