package weraFinal;

import java.util.*;

class GjordaTransaktioner extends Transaktion {
	private Date executionDate;

	/**
	 * without notice
	 * 
	 * @param dueDate
	 * @param sourceAccount
	 * @param destinationAccount
	 * @param amount
	 * @param ocrMessage
	 */
	public GjordaTransaktioner(Date dueDate, String sourceAccount, 
			String destinationAccount, double amount, 
			String ocrMessage){
		super(dueDate, sourceAccount, destinationAccount, amount, ocrMessage);
	}

	/**
	 * with notice
	 * 
	 * @param dueDate
	 * @param sourceAccount
	 * @param destinationAccount
	 * @param amount
	 * @param ocrMessage
	 */
	public GjordaTransaktioner(Date dueDate, String sourceAccount, 
			String destinationAccount, double amount, 
			String ocrMessage, String notice){
		super(dueDate, sourceAccount, destinationAccount, 
				amount, ocrMessage, notice);		
	}	
    /**
     */
}