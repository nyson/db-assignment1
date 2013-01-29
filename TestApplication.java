public class TestApplication {
    public static void main(String[] args) {
	Metoder m = new Metoder();
	Konto konton[] = m.readAccounts();

	for(Konto k : konton)
	    if(k == null)
		break;
	    else
		System.out.println(m.kontoToString(k));
    }
}