/*
Lista konton/ betalningsmottagare samt tillgängligt saldo. 
Om man anger ett konto nummer så ska information endast visas om det kontonumret.
Om man inte anger ett konto nummer så skall information om alla kontonummer visas.
*/

import java.util.*;
import java.io.*;

public class Kontoinfo {
  public static void main (String args[]) {
	try {
	boolean Accval = false;
	int Puppies = 0;
	int i = 0;
	int count = 0;
	double Value = 0;
	double NValMon = 0;
	String delimiter = ("##");
	String delim2 = ("-");
	String DeciDelim = (",");
	BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream
												(new FileInputStream("Konton.txt"))));
	String strLine;
	String[] temp = new String[20];
	String[] temp1 = new String[20];
	String[] AccNamn = new String[20];
	String[] temp2 = new String[20];
	Scanner ScKb = new Scanner(System.in);
	ArrayList<String> ArData1 = new ArrayList<String>();
		while (!Accval) {
			System.out.println("Skriv in ditt konto nummer:");
			String indata = ScKb.nextLine();
			AccNamn = indata.split(delim2);
			int FiAcNu = Integer.parseInt(AccNamn[0]);
			int SeAcNu = Integer.parseInt(AccNamn[1]);
			while ((strLine = br.readLine()) != null)   {
				if (strLine.length() > 0) {
					ArData1.add(strLine);
					count++;
					temp = strLine.split(delimiter);
					temp1 = temp[0].split(delim2);
					int a = Integer.parseInt(temp1[0]);
					int b = Integer.parseInt(temp1[1]);
					if ((FiAcNu == a )&&(SeAcNu == b )) {
						Accval = true;
						System.out.println(strLine);
						Puppies = 1;
					} else {
						continue;
					}
				} else {
					continue;
				}
			}
			if (Puppies == 0) {
				while (i < count) {
					System.out.println(ArData1.get(i));
					Accval = true;
					i++;
				}
			} else {
				continue;
			}
		}
		} catch (Exception e) {
		System.err.println("Caught Exception: " + e.getMessage());
		}
	br.close();
  }
}
