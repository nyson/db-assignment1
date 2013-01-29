public class TestApplication {
    public static void main(String[] args) {
	Metoder m = new Metoder();
	Konto konton[] = m.readAccounts();

	for(int i = 0; i < 400; i++)
	    System.out.println(konton[i].getAccountNumber());
    }
}