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