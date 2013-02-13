import java.util.*;

class GjordaTransaktioner extends Transaktion {
	private Date executionDate;
	private String transactionNotice;
	
	/**
	 * with notice
	 * 
	 * @param dueDate
	 * @param sourceAccount
	 * @param destinationAccount
	 * @param amount
	 * @param ocrMessage
	 */
	public GjordaTransaktioner(Date exDate, String transNotice, 
			Date dueDate, String sourceAccount, 
			String destinationAccount, double amount, String notice){

		super(dueDate, sourceAccount, destinationAccount, 
				amount, "", notice);
		setExecutionDate(exDate);
		setTransactionNotice(transNotice);
	}	

	public void setTransactionNotice(String tn) {
		transactionNotice = tn;
	}	
	public void setExecutionDate(Date ex) {
		executionDate = ex;
	}

	public String getTransactionNotice(){
		return transactionNotice;
	}	
	public Date getExecutionDate() { 
		return executionDate; 
	}
	
	public String toFileString(){
		return "";
	}
}