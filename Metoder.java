import java.io.*;
import java.util.*;
import java.text.*;

public class Metoder {
	private String accountFile;
	private String transactionLogFile;
	private String surveillanceFile;

	public Metoder() {
		String dir = "./utf8data/";
		accountFile = dir + "_Konton.txt";
		transactionLogFile = dir + "_GjordaTransaktioner.txt";
		surveillanceFile = dir + "_Bevakning.txt";	
	}

	/**
	 * Validate an OCR number according to the Luhn algorithm
	 * http://en.wikipedia.org/wiki/Luhn_algorithm
	 *  
	 * @param ocrNumber OCR number to validate
	 * @return true on valid OCR numbeqr 
	 */
	public boolean validOcr(String ocrNumber) {
		int checksum = 0;
		boolean alt = false;
		
		for (int i = ocrNumber.length() - 1; i >= 0; i--){
			int n = Integer.parseInt(ocrNumber.substring(i, i + 1));
			if(alt) {
				n *= 2;
				if(n > 10)
					n++;
			}

			checksum += n;
			alt = !alt;
		}
		return (checksum % 10 == 0);
	}

	/**
	 * Translates a swedish double precision string representation of a digit 
	 * to a double. String must be in "x,y" form.
	 * @param in Swedish String double to convert to double
	 * @return The String transformed to a double 
	 * @throws NumberFormatException on not being in "x,y" form
	 */
	public double parseSweDouble(String in) throws NumberFormatException {
		return Double.parseDouble(in.replace(",", "."));
	}

	/**
	 * Traverses the transaction log file and populates an ArrayList of 
	 * GjordaTransaktioner:s. 
	 * @return an ArrayList representation of the log file
	 * @throws FileNotFoundException
	 */
	public ArrayList<GjordaTransaktioner> readMadeTransactions() 
			throws FileNotFoundException {
		ArrayList<GjordaTransaktioner> et;
		Scanner etFile;
		String[] etTemp;

		etFile = new Scanner(new File(transactionLogFile));
		while(etFile.hasNextLine()) {
			etTemp = etFile.nextLine().split(";|#");
		}
		et = new ArrayList<GjordaTransaktioner>();

		etFile.close();		
		return et;			
	}

	/**
	 * Read the surveillance and populate an ArrayList with the data
	 * @return An ArrayList of the given data
	 * @throws FileNotFoundException
	 */
	public ArrayList<Transaktion> readTransactions() throws FileNotFoundException{
		Scanner tFile;
		String[] t;
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd");
		Date transDate;
		ArrayList<Transaktion> tList = new ArrayList<Transaktion>();

		tFile = new Scanner(new File(surveillanceFile));
		System.out.println("Loading surveillance...");

		while(tFile.hasNextLine()) {
			t = tFile.nextLine().split("#");
			try {
				if(t.length == 5) {
					tList.add(new Transaktion(dFormat.parse(t[0]), 
							t[1], t[2], 
							parseSweDouble(t[3]), t[4]));
				}
				else {
					tList.add(new Transaktion(dFormat.parse(t[0]), 
							t[1], t[2], 
							parseSweDouble(t[3]), t[4], 
							t[5]));
				}
			} catch (ParseException e) {
				System.out.println("Not a valid date, ignoring...");
				continue;
			}

		}

		tFile.close();
		return tList;
	}


	public Konto[] readAccounts() throws FileNotFoundException{
		Konto konton[] = new Konto[400];
		Scanner kontofil;
		String[] kontotemp;

		kontofil = new Scanner(new File(accountFile));
		System.out.println("Loading accounts from '"+accountFile+"'...");

		for(int i = 0; kontofil.hasNextLine(); i++){
			kontotemp = kontofil.nextLine().split("##");
			konton[i] = new Konto(kontotemp[0], 
					parseSweDouble(kontotemp[1]),
					kontotemp[2],
					kontotemp[3]);
		}

		kontofil.close();
		return konton;
	}
	/*
      java.lang.String 	getAccountName() 
      java.lang.String 	getAccountNumber() 
      double 	getAvailableAmount() 
      java.lang.String 	getOwnerName() 
	 */

	public String kontoToString(Konto k) {
		return k.getAccountName() + ", " + k.getAccountNumber()
				+ ", " + k.getAvailableAmount() + ", " + k.getOwnerName();
	}


}
