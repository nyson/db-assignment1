import java.util.*;
import java.io.*;
import java.awt.*;

public class Pgm2 {
  
	/*
*	Lägga till konto/betalningsmottagare
*	När man lägger in ett nytt kontonummer så skall kontroll ske
*	så att det inte redan finns ett konto med det numret.

*	Valfritt är att lägga in en funktion som slumpar fram ett kontonummer.
*	Ett kontonummer får inte bestå av fler än
*	12 tecken inklusive max/minst ett bindestreck.
*/
	
	private static void KontoNum() {
	boolean AccVal = false;
	int x = 0,y = 0;
	BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream
												(new FileInputStream("Konton.txt"))));
	String strLine;
	String[] temp = new String[20];
	String[] temp1 = new String[20];
	try {
		while (!AccVal) {
			Random Numb = new Random();
			x = Numb.nextInt(10000);
			y = Numb.nextInt(10000000);
			while ((strLine = br.readLine()) != null)   {
				if (strLine.length() > 0) {
					temp = strLine.split("##");
					temp1 = temp[0].split("-");
					int a = Integer.parseInt(temp1[0]);
					int b = Integer.parseInt(temp1[1]);
					if ((x != a)&&(y != b )) {
						AccVal = true;
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

/*
Lista konton/ betalningsmottagare samt tillgängligt saldo. 
Om man anger ett konto nummer så ska information endast visas om det kontonumret.
Om man inte anger ett konto nummer så skall information om alla kontonummer visas.
*/


	private static void ListKont() {
	boolean Accval = false;
	int AccExist = 0, i = 0,count = 0;
	double Value = 0, NValMon = 0;
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
	try {
		while (!Accval) {
			System.out.println("Skriv in ditt konto nummer:");
			String indata = ScKb.nextLine();
			AccNamn = indata.split("-");
			int FiAcNu = Integer.parseInt(AccNamn[0]);
			int SeAcNu = Integer.parseInt(AccNamn[1]);
			while ((strLine = br.readLine()) != null)   {
				if (strLine.length() > 0) {
					ArData1.add(strLine);
					count++;
					temp = strLine.split("##");
					temp1 = temp[0].split("-");
					int a = Integer.parseInt(temp1[0]);
					int b = Integer.parseInt(temp1[1]);
					if ((FiAcNu == a )&&(SeAcNu == b )) {
						Accval = true;
						System.out.println(strLine);
						AccExist = 1;
					} else {
						continue;
					}
				} else {
					continue;
				}
			}
			if (AccExist == 0) {
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
	}
	
	/*
Sätta in pengar på konto.
Ta ut pengar från konto.
*/

	private static void DepWith() {
	boolean Accval = false;
	double Value = 0,NValMon = 0;
	BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream
												(new FileInputStream("Konton.txt"))));
	String strLine;
	String[] temp = new String[20];
	String[] temp1 = new String[20];
	String[] AccNamn = new String[20];
	String[] temp2 = new String[20];
	Scanner ScKb = new Scanner(System.in);
	try {
	// här behövs en meny "Action" Action= 1 om deposit annars withdraw.
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
						System.out.println("How much would you like to move?");
						String inMoney = ScKb.nextLine();
						double money = Double.parseDouble(inMoney);
						if (Action == 1) {
							PValMon = Value + money;
						}else {
							NValMon = Value - money;
						}
						System.out.println("You have " + PValMon + " in your account");
						String ToWrite = Double.toString(PValMon);
						Arrays.fill(temp2, null);
						temp2 =  ToWrite.split(".");
						String NewMoney = (temp2[0] + "," + temp2[1]);

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
