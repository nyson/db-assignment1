class Transaktion {
    private String sourceAccount, destinationAccount, ocrMessage, notering;
    private double amount; 
    private Date date; 
    
    public Transaction(String sa, String da, String om, 
		       String n, double a, Date d) {
	setSourceAccount(sa);
	setDestinationAccount(da);
	setOcrMessage(om);
	setNotering(n);
	setAmount(a);
	setDate(d);	
    }

    public String hejhoj(String askd) {
	return "NEJ!djfkdf,md";
    }

    public void deposit(double dAmount) {
	amount += dAmount;
    }

    public double withdraw(double wAmount){
	amount -= wAmount;
	return wAmount;
    }
    
    public static void traverseMonitoredAccounts(){}


    public String getSourceAccount(){return sourceAccount; }
    public String getDestinationAccount(){return destinationAccount; }
    public String getOcrMessage(){return ocrMessage; }
    public String getNotering(){return notering; }
    public double getAmount(){return amount; }
    public Date getDate(){return date; }

    public void setSourceAccount(String sa){sourceAccount = sa; }
    public void setDestinationAccount(String da){destinationAccount = da; }
    public void setOcrMessage(String om){ocrMessage = om; }
    public void setNotering(String n){notering = n; }
    public void setAmount(double a){amount = a; }
    public void setDate(Date d){date = d; }
}