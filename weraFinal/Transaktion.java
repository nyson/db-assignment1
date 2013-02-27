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
	protected String sourceAccount;
	protected String destinationAccount;
	protected String ocrMessage; 
	protected String notice;

	protected double amount; 

	protected Date dueDate; 

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
			String destinationAccount, double amount, 
			String ocrMessage) {
		setSourceAccount(sourceAccount);
		setDestinationAccount(destinationAccount);
		setOcrMessage(ocrMessage);
		setAmount(amount);
		setDueDate(dueDate);	
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
		setSourceAccount(sa);
		setDestinationAccount(da);
		setOcrMessage(om);
		setAmount(a);
		setDueDate(d);	
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
		
		if(notice.trim().length() > 0)
			out += ";" + notice;
		
		return out;
	}
	
	/**
	 * Creates a log object of the transaction
	 * @return A log object of the current transaction
	 */
	public GjordTransaktion toLog() {
		if(!sourceAccount.equals("") && !destinationAccount.equals("")) {
			return new GjordTransaktion(
					GjordTransaktion.TransactionType.TRANSACTION,
					new Date(), "OK", dueDate,
					sourceAccount, destinationAccount,
					amount);		
		} else if(!sourceAccount.equals("")){
			return new GjordTransaktion(
					GjordTransaktion.TransactionType.WITHDRAWAL,
					new Date(), "OK", dueDate,
					sourceAccount, destinationAccount,
					amount);
		} else if(!destinationAccount.equals("")) {
			return new GjordTransaktion(
					GjordTransaktion.TransactionType.DEPOSIT,
					new Date(), "OK", dueDate,
					sourceAccount, destinationAccount,
					amount);
		} else {
			return new GjordTransaktion(
					GjordTransaktion.TransactionType.INVALID,
					new Date(), "OK", dueDate,
					sourceAccount, destinationAccount,
					amount);
		}
		
	}

	public static void traverseMonitoredAccounts() throws Exception {
		throw new Exception("not implemented");
	}

	public String getSourceAccount(){return sourceAccount; }
	public String getDestinationAccount(){return destinationAccount; }
	public String getOcrMessage(){return ocrMessage; }
	public String getNotice(){return notice; }
	public double getAmount(){return amount; }
	public Date getDueDate(){return dueDate; }

	public void setSourceAccount(String sa){sourceAccount = sa; }
	public void setDestinationAccount(String da){destinationAccount = da; }
	public void setOcrMessage(String om){ocrMessage = om; }
	public void setNotice(String n){notice = n; }
	public void setAmount(double a){amount = a; }
	public void setDueDate(Date d){dueDate = d; }
}