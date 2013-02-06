class GjordaTransaktioner extends Transaktion {
    

    /**
     */
    private String logFormatDeposit(double wAmount) {
	/*
	  "TransaktionsNotering;transaktionsDatum#"
	  + "önskatDatum;KONTANTER;destinationsKontonr;belopp;ocrMsg";
	*/
	return getNotering() + ";" + getDate().toString() + "#"
	    + new Date().toString() + ";" 
	    + wAmount + ";" + getDestinationAccount() + ";"
	    + getAmount() + ";" + getOcrMessage(); 
    }

    /**
     */
    private String logFormatWithdrawal(Transaction t) {
	return "TransaktionsNotering;transaktionsDatum#"
	    + "önskatDatum;KONTANTER;belopp;ocrMeddelande"
    }

    /**
     */
    private String logFormatTransaction(Transaction t) {
	return "TransaktionsNotering;transaktionsDatum#önskatDatum;"
	    +"källKontonummer;destinationsKonto;belopp;"
	    +"ocrMeddelande;betalningsNotering";
    }

}