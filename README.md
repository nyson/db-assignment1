Weras betalservice

Gäller för samtliga program, klasser, etc:
Om det saknas specifika instruktioner kan vi besluta själva efter tillgängliga generella riktlinjer. Vi skall dock kunna förklara oss vid systemtestet.

-- KOMMENTERING --
* Alla klasser och metoder skall märkas ut med vem som gjort dem.

* En metod bör vara max 50 rader. Alla rader måste ha rätt indrag och får inte vara längre än 80 tecken.


-- MENYN --

Oavsett vilket delprogram man startar skall följande generella struktur gälla:

1. Programmets namn skrivs ut på skärmen
 följt av en kort beskrivning av vad det kan göra.

2. Man skall erbjudas att ange filnamnet på in- och utdatafilerna.
 * Om man anger ett namn på en fil som inte finns så skall programmet
   * säga till och begära att ett giltigt filnamn anges.
   * Om man inte anger något alls så skall programmet avslutas.
     (Om man ska exportera ut data till andra filer så ska man inte ange de filnamnen förrän man i menyn valt att exportera något).

3. En meny skall skrivas ut på skärmen där användaren ser vilka programval som finns tillgängliga.

4. Vidare skall användaren erbjudas att ange en siffra som motsvarar det menyval som skall utföras.

5. Efter att menyvalet utförts skall man återvända till menyn och kunna göra ett nytt menyval.

6. Anges icke existerande menyval skall man uppmanas mata in en ny siffra.


-- UPPBYGGNAD ---
* Alla lösningar ska vara fullständigt objektorienterade.

* Ett program får under en körning inte läsa en och samma textfil mer än en gång
och den bör stängas ( close() ) endast en gång.

* Kontofilens innehåll skall lagras i en array av typen Konto[] och ha plats för 400 konton.

* Övriga filers innehåll ska sparas i ArrayList<KLASSNAMN>.

* När man letar i en array efter något, exempelvis ett konto, så skall binärsökning tillämpas. (För att det ska vara möjligt så skall arrayen/Listan vara sorterad).

* Lämpliga utskrifter till skärm skall formateras med String.format eller liknande för att uppnå fina kolumner med informationen. \t får inte användas. Se bilaga 1 för exempel.

* Alla belopp i datafiler skall sparas med två decimalers noggrannhet.


-- LOGG --

Alla transaktioner som utförs i programmet skall loggas till lämplig fil direkt.
   * Alltså uppdateras innan programmet avslutas.
   * Gamla transaktioner får ej försvinna när man öppnar filen utan de ska läggas till det som redan finns i filen.


--AVSLUT --
   *  När programmet avslutas skall indatafilerna uppdateras med de förändringar som gjorts under programkörningen.
   * Existerande fil skall få det nya efternamnet .bak.
   * Eventuellt redan existerande .bak-fil ska tas bort.
     Exempel: 1. _Konton.bak tas bort.
  		   2. Konto.txt döps om till _Konto.bak
  		   3. och den uppdaterade informationen sparas i _Konto.txt
   * Innehållet skall sparas till fil i sorterat format, 
     (exempelvis betaldatum för räkningar)
     (och kontonummer för konton.)

