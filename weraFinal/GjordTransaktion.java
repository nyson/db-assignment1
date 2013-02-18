import java.util.*;

class GjordTransaktion extends Transaktion {
	public static enum TransactionType {TRANSACTION, WITHDRAWAL, DEPOSIT};
	private Date executionDate;
	private String transactionNotice;
	private TransactionType type;
	
	/**
	 * with notice
	 * 
	 * @param tType
	 * @param dueDate
	 * @param sourceAccount
	 * @param destinationAccount
	 * @param amountew
	 * @param ocrMessage
	 */
	public GjordTransaktion(
			TransactionType tType, 
			Date exDate, 
			String transNotice,	
			Date dueDate, 
			String sourceAccount, 
			String destinationAccount, 
			double amount, 
			String notice){
		
		super(dueDate, sourceAccount, destinationAccount, 
				amount, "", notice);
		
		type = tType;
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
		if(type == TransactionType.WITHDRAWAL) {
			return "WITHDRAWAL: " + super.toFileString();
		} else if (type == TransactionType.DEPOSIT) {
			return "DEPOSIT: " + super.toFileString();
		} else if (type == TransactionType.TRANSACTION) {
			return "TRANSACTION: " + super.toFileString();
		} else {
			System.out.println("This shouldn't happen.");
			return "";
		}
	}
}