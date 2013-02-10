import java.util.*;
import java.io.*;

public class TestApplication {
	private static Metoder m;
	public static void main(String[] args) {
		m = new Metoder();
		Konto konton[];
		
		try {
			konton = m.readAccounts();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return;
		}

		for(Konto k : konton)
			if(k == null)
				break;
			else
				System.out.println(m.kontoToString(k));


		try {
			ArrayList<Transaktion> transor = m.readTransactions();
			for(Transaktion t : transor){
			//	System.out.println(t);
			}

		}catch (FileNotFoundException e){
			System.out.println(e);
		}
	}
	
}