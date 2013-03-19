//package weraFinal;

import java.text.SimpleDateFormat;
import java.util.*;
/**
 * 
 * @author Jonathan Skårstedt
 * @author Oskar Pålsgård
 * @author Magnus Duberg
 * 
 */
public class Transaktion {
	protected Date dueDate; 
	protected String sourceAccount;
	protected String destinationAccount;
	protected double amount; 
	protected String ocrMessage; 
	protected String paymentNote;

	private SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd");
	
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
			String destinationAccount, double amount, String ocrMessage) {

		setDueDate(dueDate);	
		setSourceAccount(sourceAccount);
		setDestinationAccount(destinationAccount);
		setAmount(amount);
		setOcrMessage(ocrMessage);
		setNotice("");
	}

	/**
	 * Constructor with notice
	 * 
	 * @param d due date of the Transaktion
	 * @param sa Source account of the Transaktion
	 * @param da Destination account of the Transaktion
	 * @param a Amount to transact
	 * @param om OCR message of the Transaktion
	 * @param n Notice of the Transaktion
	 */
	public Transaktion(Date d, String sa, String da, 
			double a, String om, String n) {
		setDueDate(d);	
		setSourceAccount(sa);
		setDestinationAccount(da);
		setAmount(a);
		setOcrMessage(om);
		setNotice(n);
	}
	
	/**
	 * Returns a Transaktion object as a String representation
	 * @return A formatted string of the transaction
	 */
	public String toString(){
		return "Transaction\n\t"
				+ getDueDate() + "\t"
				+ getSourceAccount() + "\t"
				+ getDestinationAccount() + "\t"
				+ getAmount() + "\t"
				+ getOcrMessage() + "\t"
				+ getNotice() + "\n\n";
	}
	
	/**
	 * Formats a Transaktion for file output
	 * 
	 * @return A string representing a Transaktion object in log form
	 */
	public String toFileString(){
		String out = dFormat.format(getDueDate()) + "#" + getSourceAccount() 
			+ "#" + getDestinationAccount() + "#" + getAmount() + "#"
			+ getOcrMessage();
		
		if(paymentNote.trim().length() > 0)
			out += ";" + paymentNote;
		
		return out;
	}
	
	public static void traverseMonitoredAccounts() throws Exception {
		throw new Exception("not implemented");
	}

	// Get methods
	public Date getDueDate(){return dueDate; }
	public String getSourceAccount(){return sourceAccount; }
	public String getDestinationAccount(){return destinationAccount; }
	public double getAmount(){return amount; }
	public String getOcrMessage(){return ocrMessage; }
	public String getNotice(){return paymentNote; }
	
	// Set methods
	public void setDueDate(Date d){dueDate = d; }
	public void setSourceAccount(String sa){sourceAccount = sa; }
	public void setDestinationAccount(String da){destinationAccount = da; }
	public void setAmount(double a){amount = a; }
	public void setOcrMessage(String om){ocrMessage = om; }
	public void setNotice(String n){paymentNote = n; }
}