/*
Lägga in överföringar och räkningar. Se klasserna Transaktion för information om vad som skall matas in för en räkning/överföring.
*/

import java.util.*;
import java.io.*;

public class fort {
  public static void main (String args[]) {
	try {
	boolean Accval = false;
	double Value = 0;
	double NValMon = 0;
	BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream
												(new FileInputStream("Konton.txt"))));
	String strLine;
	String[] temp = new String[20];
	String[] temp1 = new String[20];
	String[] temp2 = new String[20];
	String[] AccNamn = new String[20];
	Scanner ScKb = new Scanner(System.in);
		while (!Accval) {
			System.out.println("Skriv in ditt konto nummer:");
			String indata = ScKb.nextLine();
			AccNamn = indata.split("-");
			int FiAcNu = Integer.parseInt(AccNamn[0]);
			int SeAcNu = Integer.parseInt(AccNamn[1]);
			while ((strLine = br.readLine()) != null)   {
				if (strLine.length() > 0) {
					temp = strLine.split("##");
					temp1 = temp[0].split("-");
					int a = Integer.parseInt(temp1[0]);
					int b = Integer.parseInt(temp1[1]);
					if ((FiAcNu == a )&&(SeAcNu == b )) {
						Accval = true;
						System.out.println(strLine);
						temp2 = temp[1].split(",");
						String aString = (temp2[0] + "." + temp2[1]);
						Value = Double.parseDouble(aString);
						System.out.println("You have " + temp[1] + " in your account");
						System.out.println("How much would you like to add?");
						String inMoney = ScKb.nextLine();
						double money = Double.parseDouble(inMoney);
						NValMon = money + Value;
						System.out.println("You have " + NValMon + " in your account");
					} else {
						continue;
					}
				} else {
					continue;
				}
			}
		}
		} catch (Exception e) {
		System.err.println("Caught Exception: " + e.getMessage());
		}
	}
}



//Betalning ocr 
String Message;

if (String(OCR).equals(inOCR)) {
	SimpleDateFormat dateForm = new SimpleDateFormat("dd-MM-yyyy");
	Date today = new Date();
	System.out.println(dateForm.format(today));
	while (Message.length() <= 0)||(Message.length() > 15) {
		System.out.println("Ange ett medelande);
		String input = ScKb.nextLine();
		if (Message.length() <= 0) {
			System.out.println( Message is to short!);
		}
		if (Message.length() > 15) {
			System.out.println( Message is to long!);
		} else {
		 continue;
		}
	System.out.println(Message + ";" + today + "#");
	}
}


// OCRnummer/medelande

try {
		int OCRnum = Integer.parseInt(OCR);
		
		System.out.println("Bara siffror angavs är detta ett ocr?");
		if yes
		else
		
} catch (Exception e) {
		System.out.println("Detta är formaterat som ett medelande?");
