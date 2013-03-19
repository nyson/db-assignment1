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
	private File accountFile;
	private File transactionLogFile;
	private File surveillanceFile;

	private SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd");
	private static String dataPath = "datafiler/";

	public Konto[] accounts; // Konton
	public ArrayList<Transaktion> transactions; // Bevakning
	public ArrayList<GjordTransaktion> transactionLog; // Gjorda transaktioner
	public ArrayList<String> splitLine; // ???

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
		accountFile = new File(accountPath);
		transactionLogFile = new File(logPath);
		surveillanceFile = new File(surveillancePath);

		readAccounts();		//ladda _Konton.txt
		readTransactions();	//ladda _Bevakning.txt
		readLog();			//ladda _GjordaTransaktioner.txt
	}

	/** Reads the log and loads the to Metoder
	 * @throws FileNotFoundException if file isn't found
	 */
	public void readLog() throws FileNotFoundException{
		/*DEBUG*/ System.out.print("Executing readLog:");

		transactionLog = new ArrayList<GjordTransaktion>();
		Scanner logFileScanner = new Scanner(transactionLogFile);

		GjordTransaktion gjordTrans;
		GjordTransaktion.TransactionType transType;
		int lineNumber = 0;
		
	// För varje rade ur transaktionsfilen 
		while(logFileScanner.hasNextLine()) {
			lineNumber += 1;
			String line = logFileScanner.nextLine();
			transType = GjordTransaktion.analyzeTransactionType(line);			
			String[] splittedLine = line.split(";|#");

		// Försök skapa ett GjordTransaktions-objekt
			try {
				gjordTrans = new GjordTransaktion(splittedLine[0], // trans.not
						dFormat.parse(splittedLine[1]), // trans.datum
						dFormat.parse(splittedLine[2]), // önskat datum
						splittedLine[3], splittedLine[4], // käll- & dest.konto
						parseSweDouble(splittedLine[5]), // belopp
						splittedLine[6], // ocrMeddelande
						"");
			} catch (NumberFormatException e) {
				System.out.println("NumberFormatException at row("+
						lineNumber+"): " + e.getMessage());
				break;	
			} catch (ParseException e) {
				System.out.println("Error reading row("+lineNumber+"): " + 
						e.getMessage());
				break;
			} // end try
			
		// Lägg till betalningsnotering om det finns
			if(splittedLine.length > 7)
				gjordTrans.setNotice(splittedLine[7]);

		// Avryter programmet om inte klarat regexp i analyzeTransactionType 
			switch (transType){
			case DEPOSIT:break;
			case WITHDRAWAL:break;
			case TRANSACTION:break;
			case INVALID:
				System.out.println("Invalid line("+lineNumber+") in logfil: ");
				System.out.println("Line nr = "+line);
				System.exit(0);
				break;
			}
		// Lägg till gjord transaktion i gjorda transaktioner-objektet
			transactionLog.add(gjordTrans);
			
		}// end while		
/*DEBUG*/ System.out.println(" laddat " + transactionLog.size() + " fran " + transactionLogFile);
		logFileScanner.close();
	}		
	
	/** Validate an OCR number according to the Luhn algorithm
	 * http://en.wikipedia.org/wiki/Luhn_algorithm
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

	/** Adds a Transaktion to the transactions list
	 * @param t Transaktion to add
	 */
	public void addTransaction(Transaktion t){
		transactions.add(t);
	}

	/** Translates a swedish double precision string representation of a digit 
	 * to a double. String must be in "x,y" form.
	 * @param in Swedish String double to convert to double
	 * @return The String transformed to a double 
	 * @throws NumberFormatException on not being in "x,y" form
	 */
	public double parseSweDouble(String in) throws NumberFormatException {
		return Double.parseDouble(in.replace(",", "."));
	}

	/** Läser transaktioner från bevakningsfilen och laddar dem till en ArrayList
	 */
	public void readTransactions(){
/*DEBUG*/ System.out.print("Executing readTransactions: ");

		Scanner transScanner;
		String[] transStrings;
		
		transactions  = new ArrayList<Transaktion>();
		try {
			transScanner = new Scanner(surveillanceFile);

			while(transScanner.hasNextLine()) {
				transStrings = transScanner.nextLine().split("#");

				try {
					if(transStrings.length == 5) {
						transactions.add(new Transaktion(dFormat.parse(transStrings[0]), 
								transStrings[1], transStrings[2], 
								parseSweDouble(transStrings[3]), transStrings[4]));
					}
					else {
						transactions.add(new Transaktion(dFormat.parse(transStrings[0]), 
								transStrings[1], transStrings[2], 
								parseSweDouble(transStrings[3]), transStrings[4], 
								transStrings[5]));
					}
				} catch (ParseException e) {
					System.out.println("Not a valid date, ignoring: " 
							+ e.getMessage());
				}
			}
/*DEBUG*/ System.out.print("laddat " + transactions.size() + " fran " + surveillanceFile);        
			transScanner.close();
		}catch (FileNotFoundException e){
			System.out.println("fil kunde inte hittas.");        
		}
		System.out.println();
	}

	/** Reads an account file; sorts and parses the data to an Array.
	 * 		Jumps over empty lines
	 * @throws FileNotFoundException if the account file isn't found
	 */
	public void readAccounts() throws FileNotFoundException {
/*DEBUG*/ System.out.print("Executing readAccounts: ");

		accounts = new Konto[400];
		Scanner accountFileScanner;
		String[] splittedAccountLine;
		String accountLine;

		accountFileScanner = new Scanner(accountFile);

		int numberOfAccounts = 0;
		for(int i = 0; accountFileScanner.hasNextLine(); i++){
			accountLine = accountFileScanner.nextLine();
			if(accountLine.trim().equals(""))
				continue;

			splittedAccountLine = accountLine.split("##");

			accounts[i] = new Konto(splittedAccountLine[0], 
					parseSweDouble(splittedAccountLine[1]),
					splittedAccountLine[2],
					splittedAccountLine[3]);
			numberOfAccounts = i;
		}
/*DEBUG*/ System.out.println("laddat " + numberOfAccounts + " fran " + accountFile);        
		accountFileScanner.close();

		// sorting the array
		Konto temp;
		boolean sorted = true;
		while(sorted) {
			sorted = false;
			for(int i = numberOfAccounts; i > 0 && i <= numberOfAccounts; i--){
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

		System.out.println("Executing executeAllTransactionsBetween:");
		
		for(Iterator<Transaktion> it = transactions.iterator(); it.hasNext();) {
			Transaktion t = it.next();

			if(t.dueDate.before(before) && t.dueDate.after(after)) {
				try {
					executeTransaction(t);	
				}catch( Exception e){
					System.out.println("Exception: " + e);
				}
				it.remove();
			}
		}
    	System.out.println(" Antal transaktioner efter: "+transactions.size());
	}

	/**
	 * Archives older transactions to file
	 * @param olderThan Only archive transactions older than this
	 * @param archiveFile file where to save the archive
	 * @throws IOException
	 */
	public int archiveTransactions(Date olderThan, File archiveFile) 
			throws IOException{
		
		BufferedWriter archiveWriter 
		= new BufferedWriter(new FileWriter(dataPath + archiveFile.getPath()));
		
	// 
		int antalTransaktioner = transactions.size(); 
		for(Iterator<Transaktion> it = transactions.iterator(); it.hasNext();) {
			Transaktion t = it.next();
			if(t.dueDate.before(olderThan)) { // Alla gamla transaktioner
				archiveWriter.write(t.toFileString() + "\r\n"); // arkiveras
				it.remove(); // och tas bort från transaktions-listan
			} 
		}
		archiveWriter.close();
		// Returnerar antalet arkiverade transaktioner
		return antalTransaktioner - transactions.size();
	}

	/** Executes a Transaktion - withdraws and deposit as described in the 
	 * 		transaction file
	 * @param trans Transaktion to execute
	 */
	public void executeTransaction(Transaktion trans){
		
		try {
			Konto source = findAccount(trans.getSourceAccount());
			Konto destination = findAccount(trans.getDestinationAccount());

			if (trans.amount <= source.getAvailableAmount()){ // om tillräckligt
				source.withdraw(trans.getAmount()); // flyttas pengarna
				destination.depositAmount(trans.getAmount()); // till dest.konto
				appendToLog("OK", trans);
			}
			else { // transaktionen genomförs inte
				// 
			}
		} catch (Exception e) {
			System.out.println("Couldn't execute transaction '"
					+trans.toFileString()+"'");
		}
	}

	/** Checks if an account exists
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

	/**
	 * Lägger till en GjordTransaktion till transaktions-loggen i minnet
	 * @param status
	 * @param trans
	 */
	public void appendToLog(String status, Transaktion trans){
		GjordTransaktion gjordTrans = 
				new GjordTransaktion(status, new Date(), trans);								
		transactionLog.add(gjordTrans);
	}

	/**
	 * Returnerar en kopia av konton
	 * @return Konto[]-lista
	 */
	public Konto[] getAccounts(){
		return accounts.clone();		
	}

	/**
	 * Save current changes to given files.  
	 * @throws IOException
	 */
	public void saveChanges() throws IOException{
    	System.out.println("Executing saveChanges:");
		
/*DEBUG*/ System.out.println(" Kontofil: "+accountFile.getPath());
/*DEBUG*/ System.out.println(" Gjorda transaktioner: "+transactionLogFile.getPath());
/*DEBUG*/ System.out.println(" Bevakningsfil: "+surveillanceFile.getPath());
		
	// Skapa lista på datafilerna
		File[] allFiles = {accountFile, transactionLogFile, surveillanceFile};		
	// För varje fil
		for(File eachFile: allFiles) {
		// Hoppa över transaktionsloggen
			if(eachFile.equals(transactionLogFile)) continue;			
		// Skapar backupfil
			File backupFile = new File(eachFile.getPath().substring(0,
					eachFile.getPath().lastIndexOf(".")) + ".bak");
/*DEBUG*/	System.out.print(" Backupfil: " + backupFile);
		// Tar bort den om redan finns
			if(backupFile.exists()){
/*DEBUG*/		System.out.print(", tar bort gamla, ");
				backupFile.delete();
			}
		// Byter namn till backupfil
			if(eachFile.renameTo(backupFile)){
				System.out.println("bytt namn.");
			}
		}

	// Skriver konton till fil
		BufferedWriter accountWriter = new BufferedWriter(new FileWriter(accountFile));
		for(Konto acc : accounts){
			if(acc == null){
				break;
			}
			else{
				accountWriter.write(acc.getAccountNumber() + "##"
						+ acc.getAvailableAmount() + "##"
						+ acc.getAccountName() + "##"
						+ acc.getOwnerName() + "\r\n");
			}
		}
		// Skriver återstående transaktioner till bevakningsobjektet
		BufferedWriter surveillanceWriter = new BufferedWriter(new FileWriter(surveillanceFile));
		for(Transaktion t : transactions){
			surveillanceWriter.write(t.toFileString() + "\r\n");
		}
		// Skriver utförda transaktioner till transaktionsloggen 
		BufferedWriter logWriter = new BufferedWriter(new FileWriter(transactionLogFile));
		for(GjordTransaktion gt : transactionLog) {
/*DEBUG/	System.out.println("gjordTrans.toFileString: "+gt.toFileString());	/*DEBUG*/	
			logWriter.write(gt.toFileString() + "\r\n");
		}
		
		// Sparar och stänger datafilerna
		accountWriter.close();
		surveillanceWriter.close();
		logWriter.close();
	}

	/**
	 * Lägger till ett konto och sorterar kontolistan
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
	public static Metoder buildMetoder() throws IOException{

		BufferedReader in 
		= new BufferedReader(new InputStreamReader (System.in));
		String temp, accounts, log, survey;

		String defaultAccountsFilePath = dataPath + "_Konton.txt";
		String defaultLogFilePath = dataPath + "/_GjordaTransaktioner.txt";
		String defaultSurveillanceFilePath = dataPath + "/_Bevakning.txt";
		
		System.out.print("Ange kontofil (default="+ defaultAccountsFilePath +"): ");
		temp = in.readLine();
		if(temp.trim().length() > 0) { 
			accounts = temp;
		} else {
			accounts = defaultAccountsFilePath;
		}

		System.out.print("Ange loggfil (default="+defaultLogFilePath+"): ");
		temp = in.readLine();
		if(temp.trim().length() > 0) {
			log = temp;
		} else {
			log = defaultLogFilePath;
		}

		System.out.print("Ange bevakningsfil (default="+defaultSurveillanceFilePath+"): ");
		temp = in.readLine();
		if(temp.trim().length() > 0)
			survey = temp;
		else {
			survey = defaultSurveillanceFilePath;
		}

/*DEBUG/
		System.out.println("Använder: " + new File(".").getAbsolutePath() +
						  "\nKontofil: \t" + accounts +
						  "\nLogfil: \t" + log +
						  "\nBevakningsfil: \t" + survey);
/*DEBUG*/		
		return new Metoder(accounts, log, survey);
	} 

	/**
	 * Returnerar en ArrayList med utförda transaktioner sorterade efter datum
	 * @return ArrayList<GjordTransaktion>
	 */
	public ArrayList<GjordTransaktion> getLogsSortedByDate(){
		int size = transactionLog.size();
		ArrayList<GjordTransaktion> gTransToSort = transactionLog;
		
		// sorting the ArrayList
		GjordTransaktion tempGT;
		boolean sorted = true;
		while(sorted){
			sorted = false;
			for(int i = size - 1; i > 0 && i <= size; i--){
				if(gTransToSort.get(i).getTransactionDate()
						.compareTo(gTransToSort.get(i-1).getTransactionDate()) < 0){

					sorted = true;
					tempGT = gTransToSort.get(i);
					gTransToSort.set(i,gTransToSort.get(i-1));
					gTransToSort.set(i-1,tempGT);							
				}
			}
		}		
		return gTransToSort;
	}

	/** Get logs sorted by account number
	 * @return ArrayList of gjordatransaktioner
	 */
	public ArrayList<GjordTransaktion> getLogsSortedByAccountNumber(){
		System.out.println();
		int size = transactionLog.size();
		ArrayList<GjordTransaktion> gTransToSort = transactionLog;
		
		// sorting the ArrayList
		GjordTransaktion tempGT;
		boolean sorted = true;
		while(sorted){
			sorted = false;
			for(int i = size - 1; i > 0 && i <= size; i--){
				if(gTransToSort.get(i).getSourceAccount()
						.compareTo(gTransToSort.get(i-1).getSourceAccount()) < 0){

					sorted = true;
					tempGT = gTransToSort.get(i);
					gTransToSort.set(i,gTransToSort.get(i-1));
					gTransToSort.set(i-1,tempGT);							
				}
			}
		}
		return gTransToSort;
	}
	/**
	 * Skriver ut sorterad och formaterad lista över utförda transaktioner
	 * @param transList
	 */
	public void printLogs(ArrayList<GjordTransaktion> transList){
		System.out.println(
"OK Utford   Planerad Kallkonto    Dest.konto   Summa     OCR/Meddelande  Not\n"+
"-- -------- -------- ------------ ------------ --------- --------------- -------");
		for (GjordTransaktion gT : transList){
			System.out.println(String.format(
					"%1$-2s %2$tY%2$tm%2$td %3$tY%3$tm%3$td %4$-12s %5$-12s %6$9.2f %7$-15s %8$-10s",
					gT.getTransactionNote(), gT.getTransactionDate(),
					gT.getDueDate(), gT.getSourceAccount(),
					gT.getDestinationAccount(), gT.getAmount(),
					gT.getOcrMessage(), gT.getNotice()));
		}
	}
}
