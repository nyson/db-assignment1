import java.text.SimpleDateFormat;
import java.util.*;

class GjordTransaktion extends Transaktion {

	public static enum TransactionType {TRANSACTION, WITHDRAWAL, 
			DEPOSIT, INVALID};
		
/* derived from Transaktion
 * 	protected Date dueDate; 
	protected String sourceAccount;
	protected String destinationAccount;
	protected double amount; 
	protected String ocrMessage; 
	protected String notice;
 */			
	private String transactionNote;
	private Date transactionDate;

	private SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd");

/**
 * analyzeTransactionType()
 * @param raw
 * @return
 */
	public static TransactionType analyzeTransactionType(String raw) {
		/* Withdrawal
		* 0: Status, 1: Transaction date, 2: Planned date,
		* 3: Source account, 4: KONTANTER
		* 5: Amount to deposit, 6: OCR Message */
		if(raw.matches(".+;[0-9]{8}#[0-9]{8};[0-9]+-[0-9]+;KONTANTER;" +
				"[0-9]+,[0-9]{1,2};.+")) {
			return TransactionType.WITHDRAWAL;
		}

		/* Deposit
		   0: Status, 1: Transaction date, 2: Planned date,
		   3: "KONTANTER", 4: Destination account
		   5: Amount to deposit, 6: OCR Message */
		else if(raw.matches(".+;[0-9]{8}#[0-9]{8};KONTANTER;[0-9]+-[0-9]+;" +
				"[0-9]+,[0-9]{1,2};.+")) {
			return TransactionType.DEPOSIT;
		}

		/* Transaction
		   0: Status, 1: Transaction date, 2: Planned date,
		   3: Source account, 4: Destination account, 
		   5: Amount to deposit, 6: OCR message */
		else if(raw.matches(".+;[0-9]+#[0-9]+;[0-9]+-[0-9]+;[0-9]+-[0-9]+;" +
				"[0-9]+,[0-9]{1,2};.+")) {
			return TransactionType.TRANSACTION;
		}

		/* Non-valid split */
		else {
			return TransactionType.INVALID;
		}
	}
	
	/**
	 * Constructor 
	 * 
	 */ /*@param transType*/ /**
	 * @param transNote
	 * @param transDate
	 * @param dueDate
	 * @param sourceAccount
	 * @param destinationAccount
	 * @param amount
	 * @param ocrMessage
	 * @param paymentNote
	 */
	public GjordTransaktion(String transNote, Date transDate,
			Date dueDate, String sourceAccount, String destinationAccount, 
			double amount, String ocrMessage, String paymentNote){
		super(dueDate, sourceAccount, destinationAccount, 
				amount, ocrMessage, paymentNote);		
		setTransactionDate(transDate);
		setTransactionNote(transNote);
	}	

	/** Construktor that takes a Transaction object
	 * @param transNote String
	 * @param transDate Date
	 * @param trans Transaktion
	 */	
	public GjordTransaktion(String transNote,Date transDate,Transaktion trans){
		super(trans.dueDate, trans.sourceAccount, trans.destinationAccount, 
				trans.amount, trans.ocrMessage, trans.paymentNote);		
		setTransactionDate(transDate);
		setTransactionNote(transNote);
	}	
	
	
	
	
	/**
	 * 
	 */
	public String toFileString(){
		String ret = "";
				
		ret += transactionNote + ";" + dFormat.format(transactionDate) + "#"
			+ dFormat.format(dueDate) + ";"
			+ sourceAccount + ";" + destinationAccount + ";" 
			+ Double.toString(amount).replace(".", ",") + ";" + ocrMessage;
			
		if(paymentNote.length() > 0){
			ret += ";" + paymentNote;
		}
		return ret;
	}
	
	// Set methods
//	public void setType(TransactionType t){type = t;}
	public void setTransactionNote(String tn){transactionNote = tn;}
	public void setTransactionDate(Date ex){transactionDate = ex;}
	// Get methods
//	public TransactionType getType(){return type;}
	public String getTransactionNote(){return transactionNote;}
	public Date getTransactionDate(){return transactionDate;}
	
}