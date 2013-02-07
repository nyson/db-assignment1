/*
Lägga in överföringar och räkningar. Se klasserna Transaktion för information om vad som skall matas in för en räkning/överföring.
*/

import java.util.*;
import java.io.*;

public class Transfer {
	public static void main (String args[]) {
	boolean Accval = false;
	double Value = 0,NValMon = 0;
	BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream
												(new FileInputStream("Konton.txt"))));
	String strLine;
	String[] temp = new String[20];
	String[] temp1 = new String[20];
	String[] temp2 = new String[20];
	String[] AccNamn = new String[20];
	Scanner ScKb = new Scanner(System.in);
	try {
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


//minnesanteckning

//vill du göra en anteckning?
if ja
	
	String Input5 = ScKb.nextLine();
	if (Input5.length() >15) {
		System.out.println("string too long");
	}
	if (Input5.length() > 0)&&(Input5.length() < 16)&&(Input5 != null) {
			String antekning = Input5;
			
	}


//Betalning ocr

int Langd1 = ocr.length();
String[] temp7 = new String[20];
for (int xix = 0; xix < Langd1) {
	 char temp7[xix] = ocr.charAt(xix);
	 xix++;
}


//check 10 **sista OCR nummert skall inte inn. Kollar OCR
int xiy = 0,CalNum1,CalNum2,xiz = 0,NewChar1,Knark = 0,a = 0;
String[] temp8 = new String[20];
int[] NewOCR1 = new int[20];
ArrayList<String> Data1 = new ArrayList<String>();
for (int xix = 0; xix <= Langd1; xix++) {
	CalNum1 = Integer.parseInt(temp7[xiy]);
	NewOCR1[xiy] = CalNum1 * 1;
	xiy++;
	CalNum2 = Integer.parseInt(temp7[xiy]);
	NewOCR1[xiy] = CalNum2 * 2;
	xiy++;
	*/
}
while (xiz < NewOCR1.size()) {
	if (NewOCR1[xiz] > 9) {
	NewChar1 = NewOCR1[xiz] - 9;
	set.NewOCR1[xiz, NewChar1];
	} else {
		continue;
	}
	xiz++;
}
while (a < NewOCR1.size()) {
	Knark = Knark + NewOCR1[a];
	a++
}
int nytt = knark - (knark % 10) + knark;
int Check1 = nytt - knark;
if (NewOCR1.charAt(NewOCR1.size()) == Check1) {
	System.out.println("Allt är bra");
} else {
	System.out.println("Checksiffran stämmer inte");
}


//Ange medelande
String Message;
String inOCR = ScKb.nextLine();
if (String(OCR).equals(inOCR)) {
	SimpleDateFormat dateForm = new SimpleDateFormat("dd-MM-yyyy");
	Date today = new Date();
	System.out.println(dateForm.format(today));
	while (Message.length() <= 0)||(Message.length() > 15) {
		System.out.println("Ange ett medelande");
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
boolean val = false;
while (val) {
	System.out.println("Skriv in OCR nummret");
	String OCR = ScKb.nextLine();
	try {
		int OCRnum = Integer.parseInt(OCR);
		System.out.println("Bara siffror angavs är detta ett ocr?"); //behöver en meny här
		if yes
		else
		
	} catch (Exception e) {
		System.out.println("Detta är formaterat som ett medelande?");
		if (ok){
		OCR
		} else {
		}
	}
}
