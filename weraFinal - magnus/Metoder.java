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
	public ArrayList<String> log;
	public Metoder(String accountPath, String logPath, 
			String surveillancePath) throws FileNotFoundException{
		accountFile = accountPath;
		transactionLogFile = logPath;
		surveillanceFile = surveillancePath;

		readAccounts();
		readTransactions();
		readLog();
		
	}

	/**
	 * Reads the log
	 * @throws FileNotFoundException
	 */
	public void readLog() throws FileNotFoundException{
		Scanner logFile;
		String[] log;
		String temp;
		transactionLog = new ArrayList<GjordaTransaktioner>();

		logFile = new Scanner(new File(transactionLogFile));

		/**
		 * 	public GjordaTransaktioner(Date exDate, String transNotice, 
			Date dueDate, String sourceAccount, 
			String destinationAccount, double amount, String notice){


		 */
		while(logFile.hasNextLine()) {
			temp = logFile.nextLine();
			log = temp.split(";|#");
			try {
				transactionLog.add(new GjordaTransaktioner(
					dFormat.parse(log[1]), log[0], dFormat.parse(log[2]), 
					log[3], log[4], parseSweDouble(log[5]), ""));
				
			} catch (ParseException e) {
				break; // badly formatted date
			}
		}

		logFile.close();
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

	public void addTransaction(Transaktion t){
		transactions.add(t);
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
	 * Read the surveillance and populate an ArrayList with the data
	 * @return An ArrayList of the given data
	 * @throws FileNotFoundException
	 */
	public void readTransactions() throws FileNotFoundException{
		Scanner tFile;
		String[] t;
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
	public static String accountToString(Konto k) {
		if(k == null)
			return "";
		
		return String.format("%-20s %-15s %20.2f %20s", k.getAccountName(),
				k.getAccountNumber(), k.getAvailableAmount(),
				k.getOwnerName());

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
				executeTransaction(t);
				it.remove();
			} 
		}
	}
	
	/**
	 * Archives older transactions to file
	 * @param olderThan Only archive transactions older than this
	 * @param archive file where to save the archive
	 * @throws IOException
	 */
	public void archiveTransactions(Date olderThan, File archive) 
			throws IOException{		
		BufferedWriter archiveWriter 
			= new BufferedWriter(new FileWriter(archive.toString()));
		
		for(Iterator<Transaktion> it = transactions.iterator(); it.hasNext();) {
			Transaktion t = it.next();
			
			if(t.dueDate.before(olderThan)) {
				archiveWriter.write(t.toFileString() + "\n");
				it.remove();
			} 
		}
		
		archiveWriter.close();
	}
	
	/**
	 * Executes a transaction
	 * @param t
	 */
	public void executeTransaction(Transaktion t){		
		Konto source = findAccount(t.getSourceAccount());
		Konto destination = findAccount(t.getDestinationAccount());
		if(source != null)
			source.withdraw(t.getAmount());
		
		if(destination != null)
			destination.depositAmount(t.getAmount());
		
		log(t);
	}
	
	
	/**
	 * Finds an account by binary search
	 * @param account Account string to search for
	 * @return
	 */	
	public Konto findAccount(String account){
		int size;
		for(size = 0; accounts[size] != null; size++);
		
		int max = size;
		int min = 0; 
		int pos = max / 2;

		while(!accounts[pos].getAccountNumber().equals(account)) {			
			if(accounts[pos].getAccountNumber().compareTo(account) > 0)
				max = pos;
			else
				min = pos;
		
			if(pos == (max - min) / 2 + min)
				return null;
			pos = (max - min) / 2 + min;
		}
		return accounts[pos];
	}
	
	public void log(Transaktion t){		
		transactionLog.add(new GjordaTransaktioner(new Date(), "OK;",
			t.getDueDate(), t.getSourceAccount(),
			t.getDestinationAccount(), t.getAmount(), ""));
	}
	
	
	public Konto[] getAccounts(){
		return accounts.clone();		
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
			if(a == null)
				break;
			else
				accountWriter.write(a.getAccountNumber() + "##"
						+ a.getAvailableAmount() + "##"
						+ a.getAccountName() + "##"
						+ a.getOwnerName());
		
		for(Transaktion t : transactions)
			surveillanceWriter.write(t.toFileString() + "\n");
		
		for(GjordaTransaktioner l : transactionLog)
			logWriter.write(l.toFileString() + "\n");
		
		accountWriter.close();
		surveillanceWriter.close();
		logWriter.close();
	}
	
	/**
	 * 
	 * @param k
	 */
	public void addAccount(Konto k){
		int size;
		for(size = 0; accounts[size] != null; size++);
		accounts[size] = k;
		
		// sorting the array
		Konto temp;
		boolean sorted = true;
		while(sorted){
			sorted = false;
			for(int i = size - 1; i > 0 && i <= size; i--){
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
     * Creates a method object for handling bank business
     * @return a freshly baked Metoder object
     * @throws FileNotFoundException
     */
	public static Metoder buildMetoder() 
    	throws FileNotFoundException, IOException{
    	BufferedReader in 
    		= new BufferedReader(new InputStreamReader (System.in));
    	String temp, accounts, log, survey;
    	
    	String defaultAccountsFile = "_Konton.txt";
    	String defaultLogFile = "_GjordaTransaktioner.txt";
    	String defaultSurveillanceFile = "_Bevakning.txt";
    	
    	System.out.println("Var god och mata in dina filer, eller tryck bara "
    			+ "enter for att ange den forvalda installningen.");
    	
    	System.out.print("Kontofil ("+defaultAccountsFile+"): ");
    	temp = in.readLine();
    	if(temp.trim().length() > 0) 
    		accounts = temp;
    	else {
    		System.out.println("Anvander '"+defaultAccountsFile+"'");
    		accounts = defaultAccountsFile;
    	}
    	
    	System.out.print("Loggfil ("+defaultLogFile+"): ");
    	temp = in.readLine();
    	if(temp.trim().length() > 0)
    		log = temp;
    	else {
    		System.out.println("Använder '"+defaultLogFile+"'");
    		log = defaultLogFile;
    	}
    	
    	System.out.print("Bevakningsfil ("+defaultSurveillanceFile+"): ");
    	temp = in.readLine();
    	if(temp.trim().length() > 0)
    		survey = temp;
    	else {
    		System.out.println("Använder '"+defaultSurveillanceFile+"'");
    		survey = defaultSurveillanceFile;
    	}

    	return new Metoder(accounts, log, survey);
    } 
	
	/**
	 * Fetches all logs after d
	 * @param d 
	 * @return ArrayList of GjordaTransaktioner
	 */
	public ArrayList<GjordaTransaktioner> getLogsAfter (Date d){
		ArrayList<GjordaTransaktioner> out 
			= new ArrayList<GjordaTransaktioner>();
		for(GjordaTransaktioner l : transactionLog) {
			if(l.getDueDate().after(d))
				out.add(l);
		}
		return out;
	}
	
	/**
	 * Get logs by account number
	 * @param account
	 * @return arraylist of gjordatransaktioner
	 */
	public ArrayList<GjordaTransaktioner> getLogsByAccountNumber(String account){
		ArrayList<GjordaTransaktioner> out 
			= new ArrayList<GjordaTransaktioner>();
		for(GjordaTransaktioner l : transactionLog) {
			if(l.getSourceAccount().equals(account)
				|| l.getDestinationAccount().equals(account))
				out.add(l);
		}
		return out;
	}
	
	
	
}
