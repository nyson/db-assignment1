import java.io.*;
import java.util.*;

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
    
    public ArrayList<Transaktion> readTransactions(){
	Scanner tFile;
	String[] t;
	ArrayList tList = new ArrayList<Transaktion>;

	try {
	    tFile = new Scanner(new File(surveillanceFile));
	    System.out.println("Loading surveillance...");
	} catch (FileNotFoundException e) {
	    System.out.println("Couldn't find file at '"+surveillanceFile+"'!");
	    return null;
	}
	
	while(tFile.hasNextLine()) {
	    /* t string format:
	     * 0 datum
	     * 1 #källKontonummer
	     * 2 #destinationsKontonummer
	     * 3 #belopp
	     * 4 #ocrMeddelande
	     * 5 #betalningsNotering
	     */
	    t = tFile.nextLine().split("#");
	    if(t.lenght = 5)
		tList.add(new Transaktion(t[0], t[1], t[2], t[3], t[4]));
	    else
		tList.add(new Transaktion(t[0], t[1], t[2], t[3], t[4], t[5]));
	}

	for(Transaktion t : tFile) {
	    System.out.println(t);
	}
    }
    
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



int x = 24; 

x = 10 - (x % 10) + x;