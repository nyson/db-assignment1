import java.util.*;
import java.io.*;
import java.text.*;
/**
 * 
 * @author Jonathan Skårstedt
 * 
 */
public class Metoder {
	private String accountFile;
	private String transactionLogFile;
	private String surveillanceFile;
	private SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd");

	public ArrayList<Transaktion> transactions;
	public Konto[] accounts;
	public ArrayList<GjordaTransaktioner> transactionLog;
	
	public Metoder(String accountPath, String logPath, 
			String surveillancePath) throws FileNotFoundException{
		accountFile = accountPath;
		transactionLogFile = logPath;
		surveillanceFile = surveillancePath;

		readAccounts();
		
		/*
		readTransactions();
		readTransactionLog();
		*/
	}

	/**
	 * Validate an OCR number according to the Luhn algorithm
	 * http://en.wikipedia.org/wiki/Luhn_algorithm
	 *  
	 * @param ocrNumber OCR number to validate
	 * @return true on valid OCR number 
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
	public void readTransactionLog() 
			throws FileNotFoundException {
		ArrayList<GjordaTransaktioner> et 
			= new ArrayList<GjordaTransaktioner>();
		Scanner etFile;
		String[] etTemp;

		etFile = new Scanner(new File(transactionLogFile));
		while(etFile.hasNextLine()) {
			etTemp = etFile.nextLine().split(";|#");
			et.add(new GjordaTransaktioner(new Date(), "", "", 0, ""));
			
		}

		etFile.close();
		transactionLog = et;
	}

	/**
	 * Read the surveillance and populate an ArrayList with the data
	 * @return An ArrayList of the given data
	 * @throws FileNotFoundException
	 */
	public void readTransactions() throws FileNotFoundException{
		Scanner tFile;
		String[] t;
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
		transactions = tList;
	}

/*
	public void insertionSort()
	{
		int in, out;

		for(out=1; out<nElems; out++)   // out is dividing line
		{
			long temp = a[out];    // remove marked item
			in = out;           // start shifts at out
			while(in>0 && a[in-1] >= temp) // until one is smaller,
			{
				a[in] = a[in-1];      // shift item right,
				--in;            // go left one position
			}
			a[in] = temp;         // insert marked item
		} // end for
	} // end insertionSort()
	
	*/
	/**
	 * 
	 * Reads an account file; sorts and parses the data to an Array. 
	 * @return accounts in the datafile 
	 * @throws FileNotFoundException if the account file isn't found
	 */
	public void readAccounts() throws FileNotFoundException {
		accounts = new Konto[400];

		Scanner kontofil;
		String[] kontotemp;

		kontofil = new Scanner(new File(accountFile));
		System.out.println("Loading accounts from '"+accountFile+"'...");


		int accountSize = 0;
		for(int i = 0; kontofil.hasNextLine(); i++){
			kontotemp = kontofil.nextLine().split("##");
			accounts[i] = new Konto(kontotemp[0], 
				parseSweDouble(kontotemp[1]),
				kontotemp[2],
				kontotemp[3]);
			accountSize = i;
		}

		kontofil.close();
		
		// sorting the array
		Konto temp;
		boolean sorted = true;
		while(sorted){
			sorted = false;
			for(int i = accountSize; i > 0 && i <= accountSize; i--){
				if(accounts[i].getAccountNumber()
					.compareTo(accounts[i-1].getAccountNumber()) < 0){

					sorted = true;
					temp = accounts[i];
					accounts[i] = accounts[i-1];
					accounts[i-1] = temp;							
				}
			}
		}
	}
	

	/**
	 * Translate a Konto to a formatted string
	 * @param k Konto to format
	 * @return 
	 */
	public String accountToString(Konto k) {
		if(k == null)
			return "That's no account, that's a space station!";
		return k.getAccountName() + ", " + k.getAccountNumber()
				+ ", " + k.getAvailableAmount() + ", " + k.getOwnerName();
	}
	
	/**
	 * Traverses the transaction list. Executes and removes transactions younger
	 * 		than a certain date. 
	 * @param after The date where after to remove a certain transaction
	 * @param before The date where before to remove a certain transaction 
	 */
	public void executeAllTransactionsBetween(Date after, Date before){	
		for(Iterator<Transaktion> it = transactions.iterator(); it.hasNext();) {
			Transaktion t = it.next();
			
			if(t.dueDate.before(before) && t.dueDate.after(after)) {
				t.execute();
				it.remove();
			} else
				System.out.println
					(dFormat.format(t.dueDate) + " won't be processed");
		}
	}
	
	
	public Konto findAccount(String account){
		// find size
		int size;
		for(size = 0; accounts[size] != null; size++);
		
		int max = size;
		int min = 0; 
		int pos = max / 2;

		while(!accounts[pos].getAccountNumber().equals(account)) {			
			System.out.println("min, pos, max: " + min +", "+ pos +", "+ max);
			if(accounts[pos].getAccountNumber().compareTo(account) > 0)
				max = pos;
			else
				min = pos;
			
			pos = (max - min) / 2 + min;
		}
		
		System.out.println("Found at " + pos);
		
		return accounts[pos];
	}
	/**
	 * Save current changes to given files.  
	 * @throws IOException
	 */
	public void saveChanges() throws IOException{
		File backup;
		String[] files = {accountFile, transactionLogFile, surveillanceFile};
		BufferedWriter accountWriter, logWriter, surveillanceWriter;
		
		for(String file: files) {
			backup = new File(file + ".bak");
			if(backup.exists())
				backup.delete();
			new File(file)
				.renameTo(new File(file + ".bak"));
		}
	
	
		accountWriter
			= new BufferedWriter(new FileWriter(accountFile));
		logWriter
			= new BufferedWriter(new FileWriter(transactionLogFile));
		surveillanceWriter
			= new BufferedWriter(new FileWriter(surveillanceFile));
		
		for(Konto a : accounts)
			accountWriter.write(accountToString(a));
		
		for(Transaktion t : transactions)
			surveillanceWriter.write(t.toString());
		
		for(GjordaTransaktioner l : transactionLog)
			logWriter.write(l.toString());
		
		accountWriter.close();
		surveillanceWriter.close();
		logWriter.close();
	}
}
