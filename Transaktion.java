import java.util.*;


/**
 * 
 * @author Jonathan Skårstedt jonathan.skarstedt@gmail.com
 */
public class Transaktion {
    protected String sourceAccount;
    protected String destinationAccount;
    protected String ocrMessage; 
    protected String notering;

    protected double amount; 

    protected Date dueDate; 

    /**
     * Constructor without notice
     * 
     * @param dueDate Date of execution
     * @param sourceAccount Source Account of the transaction
     * @param destinationAccount
     * @param amount
     * @param ocrMessage
     */
    public Transaktion(Date dueDate, String sourceAccount, 
		       String destinationAccount, double amount, 
		       String ocrMessage) {
	setSourceAccount(sourceAccount);
	setDestinationAccount(destinationAccount);
	setOcrMessage(ocrMessage);
	setAmount(amount);
	setDate(dueDate);	
    }

    /**
     * Constructor with notice
     * 
     * @param Date 
     * @param String
     * @param String
     * @param double
     * @param om
     */
    public Transaktion(Date d, String sa, String da, 
		       double a, String om, String n) {
	setSourceAccount(sa);
	setDestinationAccount(da);
	setOcrMessage(om);
	setAmount(a);
	setDate(d);	
	setNotice(n);
    }

    public String toString(){
	return "Transaction\n\t"
	    + getDate() + "\n\t"
	    + getSourceAccount() + "\n\t"
	    + getDestinationAccount() + "\n\t"
	    + getAmount() + "\n\t"
	    + getOcrMessage() + "\n\t"
	    + getNotice() + "\n\n";
    }

    public double deposit(Konto account, double amount){
	account.depositAmount(amount);
	return 0;
    }

    public double withdraw(double wAmount){
	amount -= wAmount;
	return wAmount;
    }
    
    public static void traverseMonitoredAccounts() throws Exception {
	throw new Exception("not implemented");
    }

    public String getSourceAccount(){return sourceAccount; }
    public String getDestinationAccount(){return destinationAccount; }
    public String getOcrMessage(){return ocrMessage; }
    public String getNotice(){return notice; }
    public double getAmount(){return amount; }
    public Date getDate(){return date; }
    
    public void setSourceAccount(String sa){sourceAccount = sa; }
    public void setDestinationAccount(String da){destinationAccount = da; }
    public void setOcrMessage(String om){ocrMessage = om; }
    public void setNotice(String n){notice = n; }
    public void setAmount(double a){amount = a; }
    public void setDate(Date d){date = d; }
}