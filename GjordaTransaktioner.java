class GjordaTransaktioner extends Transaktion {
    public void log(Transaktion t) {
	
    }   


    /**
     */
    private String logFormatDeposit(Transaction t) {
	return "TransaktionsNotering;transaktionsDatum#"
	    + "önskatDatum;KONTANTER;destinationsKontonr;belopp;ocrMsg";
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