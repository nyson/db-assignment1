import java.util.*;

class GjordaTransaktioner extends Transaktion {
	private Date executionDate;

	/**
	 * with notice
	 * 
	 * @param dueDate
	 * @param sourceAccount
	 * @param destinationAccount
	 * @param amount
	 * @param ocrMessage
	 */
	public GjordaTransaktioner(Date exDate, Date dueDate, String sourceAccount, 
			String destinationAccount, double amount, String notice){

		super(dueDate, sourceAccount, destinationAccount, 
				amount, "", notice);
		setExecutionDate(exDate);
	}	

	public void setExecutionDate(Date ex) {
		executionDate = ex;
	}
	
	public Date getExecutionDate() { 
		return executionDate; 
	}
}