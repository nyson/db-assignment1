import java.text.SimpleDateFormat;
import java.util.*;

class GjordTransaktion extends Transaktion {
	public static enum TransactionType {TRANSACTION, WITHDRAWAL, 
			DEPOSIT, INVALID};
	private Date executionDate;
	private String transactionNotice;
	private TransactionType type;
	private SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd");

	
	public static TransactionType analyzeTransactionType(String raw) {
		/**	
		 * Withdrawal split
		 * 0: Status, 1: Transaction date, 2: Planned date,
		 * 3: Identifier, 4: Source account
		 * 5: Amount to deposit, 6: OCR
		 */
		if(raw.matches("OK;[0-9]{8}#[0-9]{8};KONTANTER;"
				+ "[0-9]+-[0-9]+;([0-9]+[,\\.][0-9]{1,2}|[0-9]+)"
				+ "(;.+)?")) {
			return TransactionType.WITHDRAWAL;

		/**
		 * Deposit split
		 * 0: Status, 1: Transaction date, 2: Planned date,
		 * 3: Identifier, 4: Destination account
		 * 5: Amount to deposit, 6: Ocr message
		 */
		} else if(raw.matches("OK;[0-9]{8}#[0-9]{8};[0-9]+-[0-9]+;KONTANTER;"
				+ "([0-9]+[,\\.][0-9]{1,2}|[0-9]+)(;.+)?")) {
			return TransactionType.DEPOSIT;

		/**
		 * Transaction split
		 * 0: Status, 1: Transaction date, 2: Planned date,
		 * 3: Source account, 4: Destination account, 
		 * 5: Amount to deposit, 6: OCR message
		 */
		} else if(raw.matches("OK;"
				+ "[0-9]{8}#[0-9]{8};[0-9]+-[0-9]+;"
				+ "[0-9]+-[0-9]+;([0-9]+[,\\.][0-9]{1,2}|[0-9]+)"
				+ "(;.+)?")) {
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

	
	public String toFileString(){
		String ret = "";
		switch(type){
		case WITHDRAWAL:
			ret += "OK;" + dFormat.format(executionDate) + "#"
					+ dFormat.format(dueDate) + ";KONTANTER;" 
					+ sourceAccount + ";" 
					+ Double.toString(amount).replace(".", ",");
			
			if(ocrMessage != null && !ocrMessage.equals(""))
				ret += ";" + ocrMessage;
			
			break;
			
		case DEPOSIT:
			ret += "OK;" + dFormat.format(executionDate) + "#"
					+ dFormat.format(dueDate) + ";" 
					+ destinationAccount + ";KONTANTER;" 
					+ Double.toString(amount).replace(".", ",");
			
			if(ocrMessage != null && !ocrMessage.equals(""))
				ret += ";" + ocrMessage;
			
			break;			
			
		case TRANSACTION:
			ret += "OK;" + dFormat.format(executionDate) + "#"
					+ dFormat.format(dueDate) + ";" + sourceAccount + ";" 
					+ destinationAccount + ";" 
					+ Double.toString(amount).replace(".", ",");
			
			if(ocrMessage != null && !ocrMessage.equals(""))
				ret += ";" + ocrMessage;
			
			break;			
		
		default:
		case INVALID:
			ret += "INVALID TRANSACTION FORMAT";
		
		}
		
		return ret;
		 
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
	
}