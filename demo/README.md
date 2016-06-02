# Usermessage

### Veza korisnik-poruke
**Verzija 0.0.1**

# Manual

Aplikacija se nalazi na OpenShift cloudu na ovoj [adresi](http://usermessage-aportolan.rhcloud.com/usermessage)
Na openshift hub-u je instaliran Tomcat 8
Serverski dio je napravljen u Spring BOOT-u (gleda na spring 4), klijentski u AngularJS-u
Baza je In memory H2 baza (dropira se i podiže kod svakog restarta aplikacije)
Build je na Mavenu (testirano na 3.3)

**User:** korisnik
**Pass:** dobar dan

Svako 5 sekundi se starta CRON Job koji (po evaluation task-u) briše poruke sa isteklim validity vremenom

## Klijentska funkcionalnost
Klijentska forma se sastoji od forme pregleda korisnika i forme za pregled poruka koja se opet sastoji od dijela za pretragu i dijela za prikaz rezultata.
Korisnička forma može dodavati nove korisnike i mijenjati i brisati stare(standardne CRUD funkcionalnosti), također ima mogućnost unosa inicijalnih korisnika . To je dogotrajnija operacija pa se to odrađuje u zasebnom procesu, a klijent prima poruke od servera o tijeku procesa putem push messaging-a preko web socket komunikacije. Svaki redak tablice korisnika ima tipku za dovlačenje njegovih poruka. 

Forma poruka ima većinu funkcionalnosti istih kao i forma korisnika (bez unosa inicijalnih korisnika), te ima mogućnost unosa 1 poruke za sve korisnike. Pritiskom te tipke se podatak iz forme za unos poruke propagira za sve korisnike
**Prelaskom kursora preko ikona na klijentskom dijelu, može se vidjeti njihova funkcionalnost** 
 

## Rest linkovi 
REST linkovi su (naravno, treba nadodati još root aplikacije http://usermessage-aportolan.rhcloud.com/usermessage):

**Users**
- /users/saveAll - (lista objekata) spremanje jednog ili više korisnika
- /users/initLoad - inicijalno punjenje 100 000 korisnika u bazu (sa imenima sa [ove lokacije](http://www.quietaffiliate.com/free-first-name-and-last-name-databases-csv-and-sql/)) - ovaj link ne prima ništa
- /users/get - dovlačenje liste korisnika
- /users/delete - (lista objekata) brisanje korisnika (jednog ili više)
- /users/save - spremanje jednog korisnika

**Messages**
- /messages/saveAll - (sprema jednu poruku za veći broj korisnika (po uid-u, imenu ili tag-u))
- /messages/get - dovlači podatke po korisniku
- /messages/delete - (lista objekata) - briše jednu ili više poruka
- /messages/save - (sprema pojedinačnu poruku)


Svaki **request** mora biti wrappan u objekt:
```
{
  "limit": 0,
  "offset": 0,
  "payload": {}
}
```
**Response:**
```
{
  "errorMessage": "",
  "payload": {},
  "success": true
}
```

Payload može biti :

**User:**
```
{
  "messages": [],
  "name": "",
  "tag": "",
  "uid": 0
}
```
**Message**
```
{
  "errorMessage": "",
  "payload": {},
  "success": true
}
```
...ili lista sa **User:** ili **Message** podacima.

Primjeri request-ova su u datotekama unutar test resursa (...src/test/resources/files)

Isto tako opis REST dijela se nalazi na [ovoj lokaciji](http://usermessage-aportolan.rhcloud.com/usermessage/jsondoc-ui.html?url=http://localhost:8400/usermessage/jsondoc#)

**Testovi**
Integracijski dio kroz maven se preskače jer je resursno zahtjevan

**JMeter**
JMeter skripta se nalazi u ..src/main/resources/jmeter/jmeter_test.jmx. Scenarij je napravljen po evaluation task-u za više korisnika i više simultanih requestova 
