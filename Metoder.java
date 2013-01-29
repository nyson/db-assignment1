public class Metoder {
    private String accountFile;
    
    public function Metoder() {
	accountFile = "";
    }

    public Konto[] readAccounts(){
	Konto konton[] = new Konto[400];

	for(int i = 0; i < 400; i++)
	    konton[i] = new Konto("1234-1234", 20.20, "Löner", "Gunnar");
	return konton;
    }
}