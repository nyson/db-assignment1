/* @(#)Transaktion.java        1.00 2013-01-30
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
class Transaktion {
    protected String sourceAccount, destinationAccount, ocrMessage, notering;
    protected double amount; 
    protected Date date; 
    
    public Transaction(String sa, String da, String om, 
		       String n, double a, Date d) {
	setSourceAccount(sa);
	setDestinationAccount(da);
	setOcrMessage(om);
	setNotering(n);
	setAmount(a);
	setDate(d);	
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