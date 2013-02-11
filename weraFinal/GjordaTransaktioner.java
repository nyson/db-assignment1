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
	public GjordaTransaktioner(String in){
		super(new Date(), "", "", 0, "", "");
		
		/**
		 * insättning
		 * tn;transdat#bevdat;KONTANTER;destk;belopp;ocr;bn
		 */
		if(in.matches("*;*#*;KONTANTER;*;*")) {

		/**
		 * uttag
		 * tn;td#bd;KONTANTER;belopp;ocr
		 */
		} else if(in.matches(".*;[0-9]{8}#.*;KONTANTER;.*;.*")) {
			
		/**
		 * transaktion
		 * tn;td#bd;källkon;destkon;belopp;ocr;notering
		 */
		} else if(in.matches("*;*#*;*;*;*")) {
			
		}
		//setExecutionDate(exDate);
	}	

	public void setExecutionDate(Date ex) {
		executionDate = ex;
	}
	
	public Date getExecutionDate() { 
		return executionDate; 
	}
}