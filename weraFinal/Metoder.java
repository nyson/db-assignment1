import java.util.*;
import java.io.*;
import java.text.*;

/**
 * 
 * @author Jonathan Skårstedt
 * @author Oskar Pålsgård
 * @author Magnus Duberg
 * 
 */
public class Metoder {
	private String accountFile;
	private String transactionLogFile;
	private String surveillanceFile;

	private SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd");

	public ArrayList<Transaktion> transactions;
	public Konto[] accounts;
	public ArrayList<GjordTransaktion> transactionLog;
	public ArrayList<String> log;

	/**
	 * Constructor for the Metoder object
	 * Reads all data files and loads them into object
	 * 
	 * @param accountPath Account file path
	 * @param logPath Log file path
	 * @param surveillancePath Surveillance file path
	 * @throws FileNotFoundException If files are not found
	 */
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
	 * Reads the log and saves them to Metoder
	 * 
	 * @throws FileNotFoundException if file isn't found
	 */
	public void readLog() throws FileNotFoundException{
		Scanner logFile;
		String[] log;
		String temp;
		GjordTransaktion trans;
		GjordTransaktion.TransactionType type;
		
		transactionLog = new ArrayList<GjordTransaktion>();

		logFile = new Scanner(new File(transactionLogFile));

		while(logFile.hasNextLine()) {
			temp = logFile.nextLine();
			type = GjordTransaktion.analyzeTransactionType(temp);			
			log = temp.split(";|#");
			
			try {
				trans = new GjordTransaktion(
						GjordTransaktion.TransactionType.INVALID,
						dFormat.parse(log[1]), log[0], 
						dFormat.parse(log[2]), "", "", 
						parseSweDouble(log[5]));
			
				switch(type) {
					case WITHDRAWAL:
						trans.setType(GjordTransaktion
								.TransactionType.WITHDRAWAL);
						trans.setSourceAccount(log[4]);
						if(log.length >= 7)
							trans.setOcrMessage(log[6]);
						
						
						break;
					case DEPOSIT:
						trans.setType(GjordTransaktion.TransactionType.DEPOSIT);
						trans.setDestinationAccount(log[3]);
						if(log.length >= 7)
							trans.setNotice(log[6]);
	
						
						break;
					case TRANSACTION:
						trans.setType(GjordTransaktion.TransactionType.TRANSACTION);
						trans.setSourceAccount(log[3]);
						trans.setDestinationAccount(log[4]);
						if(log.length >= 7)
							trans.setOcrMessage(log[6]);
						
						break;
					
					default:
					case INVALID:
						System.out.println("I don't understand '" +temp+ "'");
						break;
				}
				
				transactionLog.add(trans);

			} catch (NumberFormatException e) {
				System.out.println("Badly formatted date at row: " + e.getMessage());
				break;
				
			} catch (ParseException e) {
				System.out.println("Can't load row: " + e.getMessage());
				break;
				
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
		
		// Kollar f�rst om det bara �r siffror
	        try {
	            Double.parseDouble(ocrNumber);
	        } catch (NumberFormatException e) {
	            return false;
	        }
	    // Kollar om l�ngden �r mellan 2 och 15 tecken
	        if (2 > ocrNumber.length() || ocrNumber.length() > 15)
	        	return false;
		
		int checksum = 0;
		boolean alt = false;

		for (int i = ocrNumber.length() - 1; i >= 0; i--){
			int n = Integer.parseInt(ocrNumber.substring(i, i + 1));

			if(alt) {
				n *= 2;				
				if(n > 10)
					n++;
			}

			checksum += n % 10;
			alt = !alt;
		}

		return (checksum % 10 == 0);
	}

	/**
	 * Adds a Transaktion to the transactions list
	 *  
	 * @param t Transaktion to add
	 */
	public void addTransaction(Transaktion t){
		transactions.add(t);
	}

	/**
	 * Translates a swedish double precision string representation of a digit 
	 * to a double. String must be in "x,y" form.
	 * 
	 * @param in Swedish String double to convert to double
	 * @return The String transformed to a double 
	 * @throws NumberFormatException on not being in "x,y" form
	 */
	public double parseSweDouble(String in) throws NumberFormatException {
		return Double.parseDouble(in.replace(",", "."));
	}

	/**
	 * Read the surveillance and populate an ArrayList with the data
	 * 
	 * @throws FileNotFoundException
	 */
	public void readTransactions() throws FileNotFoundException{
		Scanner tFile;
		String[] t;
		transactions  = new ArrayList<Transaktion>();

		tFile = new Scanner(new File(surveillanceFile));

		while(tFile.hasNextLine()) {
			t = tFile.nextLine().split("#");
			try {
				if(t.length == 5) {
					transactions.add(new Transaktion(dFormat.parse(t[0]), 
							t[1], t[2], 
							parseSweDouble(t[3]), t[4]));
				}
				else {
					transactions.add(new Transaktion(dFormat.parse(t[0]), 
							t[1], t[2], 
							parseSweDouble(t[3]), t[4], 
							t[5]));
				}
			} catch (ParseException e) {
				System.out.println("Not a valid date, ignoring: " 
						+ e.getMessage());
			}
		}

		tFile.close();
	}

	/**
	 * 
	 * Reads an account file; sorts and parses the data to an Array.
	 * 		Jumps over empty lines
	 *  
	 * @throws FileNotFoundException if the account file isn't found
	 */
	public void readAccounts() throws FileNotFoundException {
		accounts = new Konto[400];

		Scanner kontofil;
		String[] kontotemp;
		String accountLine;

		kontofil = new Scanner(new File(accountFile));

		int accountSize = 0;
		for(int i = 0; kontofil.hasNextLine(); i++){
			accountLine = kontofil.nextLine();
			if(accountLine.trim().equals(""))
				continue;

			kontotemp = accountLine.split("##");

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
		while(sorted) {
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
	 * @return If found returns a formatted string, else a Konto object 
	 */
	public static String accountToString(Konto k) {
		if(k == null)
			return "";

		return String.format("%-20s %-15s %20.2f %20s", k.getAccountName(),
				k.getAccountNumber(), k.getAvailableAmount(),
				k.getOwnerName());

	}

	/**
	 * Traverses the transaction list. Executes and removes transactions 
	 * 		between two dates.
	 *  
	 * @param after The date where after to remove a certain transaction
	 * @param before The date where before to remove a certain transaction 
	 */
	public void executeAllTransactionsBetween(Date after, Date before) {	
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
	public int archiveTransactions(Date olderThan, File archive) 
			throws IOException{

		int size = transactions.size(); 
		
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
		
		return size - transactions.size();
	}

	/**
	 * Executes a Transaktion - withdraws and deposit as described in the 
	 * 		transaction file
	 * @param t Transaktion to execute
	 */
	public void executeTransaction(Transaktion t){
		try {
			Konto source = findAccount(t.getSourceAccount());
			Konto destination = findAccount(t.getDestinationAccount());

			source.withdraw(t.getAmount());
			destination.depositAmount(t.getAmount());

			log(t);	
			
		} catch (Exception e) {
			System.out.println("Couldn't execute transaction '"
					+t.toFileString()+"'");
		}


	}

	/**
	 * Checks if an account exists
	 * 
	 * @param account account number to check if it exists
	 * @return true if account exists, else false
	 */
	public boolean accountExists(String account){
		try {
			findAccount(account);
			return true;
		} catch (NoSuchFieldException e) {
			return false;
		}
		
		
	}
	/**
	 * Finds an account by binary search
	 * 
	 * @param account Account string to search for
	 * @return The found account
	 */	
	public Konto findAccount(String account) throws NoSuchFieldException{
		int size;
		for(size = 0; accounts[size] != null; size++);

		int max = size;
		int min = 0; 
		int pos = max / 2;

		String current; 

		while(!accounts[pos].getAccountNumber().equals(account)) {	
			current = accounts[pos].getAccountNumber();

			if(current.compareTo(account) > 0)
				max = pos;
			else
				min = pos;

			if(pos == (max - min) / 2 + min)
				throw new NoSuchFieldException
					("Couldn't find account with number '"+account+"'");

			pos = (max - min) / 2 + min;
		}
		return accounts[pos];
	}

	public void log(Transaktion t){	
		transactionLog.add(t.toLog());
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
		BufferedWriter accountWriter;
		BufferedWriter logWriter;
		BufferedWriter surveillanceWriter;

		for(String file: files) {
			if(file.equals(transactionLogFile))
				break;
			
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
						+ a.getOwnerName() + "\n");

		for(Transaktion t : transactions)
			surveillanceWriter.write(t.toFileString() + "\n");

		for(GjordTransaktion l : transactionLog) {
			logWriter.write(l.toFileString() + "\n");
		}
		
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
		accounts[size++] = k;

		
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

		String defaultAccountsFile = "utf8data/_Konton.txt";
		String defaultLogFile = "utf8data/_GjordaTransaktioner.txt";
		String defaultSurveillanceFile = "utf8data/_Bevakning.txt";

		System.out.println("Var god och mata in dina filer, eller tryck bara "
				+ "enter för att ange den förvalda inställningen.");

		System.out.println("Förinställda filer: "
				+ "\n\tKontofil: \t" + defaultAccountsFile 
				+ "\n\tLogfil: \t" + defaultLogFile 
				+ "\n\tBevakningsfil: \t" + defaultSurveillanceFile);
		
		System.out.print("Kontofil: ");
		temp = in.readLine();
		if(temp.trim().length() > 0) { 
			accounts = temp;
		} else {
			accounts = defaultAccountsFile;
		}

		System.out.print("Logfil: ");
		temp = in.readLine();
		if(temp.trim().length() > 0) {
			log = temp;
		} else {
			log = defaultLogFile;
		}

		System.out.print("Bevakningsfil: ");
		temp = in.readLine();
		if(temp.trim().length() > 0)
			survey = temp;
		else {
			survey = defaultSurveillanceFile;
		}

		System.out.println("Använder (pwd: '"
				+ new File(".").getAbsolutePath() +"'): "
				+ "\n\tKontofil: \t" + accounts 
				+ "\n\tLogfil: \t" + log
				+ "\n\tBevakningsfil: \t" + survey);
		
		return new Metoder(accounts, log, survey);
	} 

	/**
	 * Fetches all logs after a certain date
	 * 
	 * @param d Date to search from
	 * @return ArrayList of GjordaTransaktioner
	 */
	public ArrayList<GjordTransaktion> getLogsAfter (Date d){
		ArrayList<GjordTransaktion> out 
		= new ArrayList<GjordTransaktion>();
		for(GjordTransaktion l : transactionLog) {
			if(l.getDueDate().after(d))
				out.add(l);
		}
		return out;
	}

	/**
	 * Get logs by account number
	 * 
	 * @param account
	 * @return arraylist of gjordatransaktioner
	 */
	public ArrayList<GjordTransaktion> getLogsByAccountNumber (String account){
		ArrayList<GjordTransaktion> out 
		= new ArrayList<GjordTransaktion>();
		for(GjordTransaktion l : transactionLog) {
			if(l.getSourceAccount().equals(account)
					|| l.getDestinationAccount().equals(account))
				out.add(l);
		}
		return out;
	}



}
