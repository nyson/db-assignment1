/*
Sätta in pengar på konto.
Ta ut pengar från konto.
*/

import java.util.*;
import java.io.*;

public class Betalning {
  public static void main (String args[]) {
	try {
	boolean Accval = false;
	double Value = 0;
	double NValMon = 0;
	String delimiter = ("##");
	String delim2 = ("-");
	String DeciDelim = (",");
	String DotDelim = (".");
	BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream
												(new FileInputStream("Konton.txt"))));
	String strLine;
	String[] temp = new String[20];
	String[] temp1 = new String[20];
	String[] AccNamn = new String[20];
	String[] temp2 = new String[20];
	Scanner ScKb = new Scanner(System.in);
		while (!Accval) {
			System.out.println("Skriv in ditt konto nummer:");
			String indata = ScKb.nextLine();
			AccNamn = indata.split(delim2);
			int FiAcNu = Integer.parseInt(AccNamn[0]);
			int SeAcNu = Integer.parseInt(AccNamn[1]);
			while ((strLine = br.readLine()) != null)   {
				if (strLine.length() > 0) {
					temp = strLine.split(delimiter);
					temp1 = temp[0].split(delim2);
					int a = Integer.parseInt(temp1[0]);
					int b = Integer.parseInt(temp1[1]);
					if ((FiAcNu == a )&&(SeAcNu == b )) {
						int Polp = 1;
						if (Polp == 1) {
						Accval = true;
						System.out.println(strLine);
						temp2 = temp[1].split(DeciDelim);
						String aString = (temp2[0] + "." + temp2[1]);
						Value = Double.parseDouble(aString);
						System.out.println("You have " + temp[1] + " in your account");
						System.out.println("How much would you like to add?");
						String inMoney = ScKb.nextLine();
						double money = Double.parseDouble(inMoney);
						PValMon = Value + money;
						System.out.println("You have " + PValMon + " in your account");
						String ToWrite = Double.toString(PValMon);
						Arrays.fill(temp2, null);
						temp2 =  ToWrite.split(DotDelim);
						String NewMoney = (temp2[0] + "," + temp2[1]);
						} else {
						Accval = true;
						System.out.println(strLine);
						temp2 = temp[1].split(DeciDelim);
						String aString = (temp2[0] + "." + temp2[1]);
						Value = Double.parseDouble(aString);
						System.out.println("You have " + temp[1] + " in your account");
						System.out.println("How much would you like to withdraw?");
						String inMoney = ScKb.nextLine();
						double money = Double.parseDouble(inMoney);
						NValMon = Value - money;
						System.out.println("You have " + NValMon + " in your account");
						String ToWrite1 = Double.toString(NValMon);
						Arrays.fill(temp2, null);
						temp2 =  ToWrite.split(DotDelim);
						String NewMoney = (temp2[0] + "," + temp2[1]);
						}
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
