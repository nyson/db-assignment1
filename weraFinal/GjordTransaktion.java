import java.util.*;

class GjordTransaktion extends Transaktion {
	public static enum TransactionType {TRANSACTION, WITHDRAWAL, 
			DEPOSIT, INVALID};
	private Date executionDate;
	private String transactionNotice;
	private TransactionType type;
	public static TransactionType analyzeTransactionType(String raw) {
		/**	
		 * Withdrawal split
		 * 0: Status, 1: Transaction date, 2: Planned date,
		 * 3: Identifier, 4: Source account
		 * 5: Amount to deposit, 6: OCR
		 */
		if(raw.matches("OK;[0-9]{8}#[0-9]{8};KONTANTER;"
				+ "[0-9]+-[0-9]+;([0-9]+|[0-9]+[,\\.][0-9]{1,2})"
				+ "(;.+)?")) {
			return TransactionType.WITHDRAWAL;

		/**
		 * Deposit split
		 * 0: Status, 1: Transaction date, 2: Planned date,
		 * 3: Identifier, 4: Destination account
		 * 5: Amount to deposit, 6: Ocr message
		 */
		} else if(raw.matches("OK;[0-9]{8}#[0-9]{8};KONTANTER;[0-9]+-[0-9]+;"
				+ "([0-9]+|[0-9]+[,\\.][0-9]{1,2})(;.+)?")) {
			return TransactionType.DEPOSIT;

		/**
		 * Transaction split
		 * 0: Status, 1: Transaction date, 2: Planned date,
		 * 3: Source account, 4: Destination account, 
		 * 5: Amount to deposit, 6: OCR message
		 * 7: Eventual transaction notice
		 */
		} else if(raw.matches("OK;[0-9]{8}#[0-9]{8};[0-9]+-[0-9]+;"
				+ "[0-9]+-[0-9]+;([0-9]+|[0-9]+[,\\.][0-9]{1,2});"
				+ ".+(;.+)")) {
			return TransactionType.TRANSACTION;
			
		/**
		 * Non-valid split
		 */
		} else {
			
			return TransactionType.INVALID;			
		}
		
		
	}
	
	
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
			double amount){
		
		super(dueDate, sourceAccount, destinationAccount, 
				amount, "", "");
		
		type = tType;
		setExecutionDate(exDate);
		setTransactionNotice(transNotice);
	}	

	

	public void setType(TransactionType t) {
		type = t;
	}
	public void setTransactionNotice(String tn) {
		transactionNotice = tn;
	}	
	public void setExecutionDate(Date ex) {
		executionDate = ex;
	}

	public TransactionType getType() {
		return type;
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