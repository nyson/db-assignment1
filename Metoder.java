import java.io.*;
import java.util.*;
import java.text.*;

public class Metoder {
	private String accountFile, transactionLogFile, surveillanceFile;

	public Metoder() {
		String dir = "./utf8data/";
		accountFile = dir + "_Konton.txt";
		transactionLogFile = dir + "_GjordaTransaktioner.txt";
		surveillanceFile = dir + "_Bevakning.txt";	
	}

	public double parseSweDouble(String in) throws NumberFormatException {
		return Double.parseDouble(in.replace(",", "."));
	}

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
				return null;
			}

		}

		tFile.close();
		return tList;
	}


	/**
	 * Reads all accounts to a Konto array
	 * 
	 */
	public Konto[] readAccounts(){
		Konto konton[] = new Konto[400];
		Scanner kontofil;
		String[] kontotemp;

		try {
			kontofil = new Scanner(new File(accountFile));
			System.out.println("Loading accounts from '"+accountFile+"'...");
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't find file!");
			return null;
		} 


		for(int i = 0; kontofil.hasNextLine(); i++){
			kontotemp = kontofil.nextLine().split("##");
			konton[i] = new Konto(kontotemp[0], 
					parseSweDouble(kontotemp[1]),
					kontotemp[2],
					kontotemp[3]);
		}

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
