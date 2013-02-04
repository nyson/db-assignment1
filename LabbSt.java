/*
*  Lägga till konto/betalningsmottagare
*	När man lägger in ett nytt kontonummer så skall kontroll ske
*	så att det inte redan finns ett konto med det numret.

*	Valfritt är att lägga in en funktion som slumpar fram ett kontonummer.
*	Ett kontonummer får inte bestå av fler än
*	12 tecken inklusive max/minst ett bindestreck.
*/

import java.util.*;
import java.io.*;
import java.awt.*;

public class LabbSt {
	public static void main (String args[]) {
	try {
	boolean Accval = false;
	int x = 0;
	int y = 0;
	String delimiter = "##";
	String delim2 = "-";
	BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream
												(new FileInputStream("Konton.txt"))));
	String strLine;
	String[] temp = new String[20];
	String[] temp1 = new String[20];
		while (!Accval) {
			Random Numb = new Random();
			x = Numb.nextInt(10000);
			y = Numb.nextInt(10000000);
			while ((strLine = br.readLine()) != null)   {
				if (strLine.length() > 0) {
					temp = strLine.split(delimiter);
					temp1 = temp[0].split(delim2);
					int a = Integer.parseInt(temp1[0]);
					int b = Integer.parseInt(temp1[1]);
					if ((x != a)&&(y != b )) {
						Accval = true;
					} else {
						continue;
					}
				} else {
					continue;
				}
			}
		}
	String Accnum = (Integer.toString(x) + "-" + Integer.toString(y));
	System.out.println("Your new Account Number is: " + Accnum);
		} catch (Exception e) {
    System.err.println("Caught Exception: " + e.getMessage());
	}
	}
}
