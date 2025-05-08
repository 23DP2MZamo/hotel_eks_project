# Viesnīcas Rezervāciju Sistēma

## Ievads
Šī programmatūra ir viesnīcas rezervāciju pārvaldības sistēma, kas ļauj lietotājiem apskatīt pieejamās istabas, veikt rezervācijas un pārvaldīt savas rezervācijas. Sistēma ir izstrādāta kā konsoles lietojumprogramma, kas nodrošina vienkāršu un efektīvu veidu, kā pārvaldīt viesnīcas istabu rezervācijas.

### Sistēmas prasības
- Java programmēšanas valoda (JRE vai JDK)
- CSV failu atbalsts

## Lietotāja interfeisa apraksts
Programma darbojas caur konsoles interfeisu ar 5 galvenajām opcijām:

1. **Rezervēt istabu** - ļauj izvēlēties un rezervēt pieejamo istabu
2. **Parādīt nerezervētas istabas** - attēlo visu pieejamo (nerezervēto) istabu sarakstu
3. **Aizvērt programmu** - iziet no programmas
4. **Parādīt jūsu rezervētas istabas** - attēlo visas pašreizējā lietotāja rezervētās istabas
5. **Atcelt rezervējumu** - ļauj atcelt iepriekš veiktu rezervāciju

## Funkciju apraksti

### Sistēmas iestatīšana
1. Lejupielādējiet programmu no GitHub vai citas avota
2. Atveriet mapi `hotel_eks_project-master`
3. Pārliecinieties, ka Jums ir instalēta Java programmēšanas valoda

### Programmas palaišana
1. Palaidiet failu `Main.java`
2. Ja sistēma neatrod datus, vispirms palaidiet `CsvRoomSeeder.java`, lai ielādētu nepieciešamos datus

### Rezervācijas veikšana
1. Izvēlieties opciju "1" no galvenā izvēlnes
2. Apskatiet pieejamo istabu sarakstu
3. Izvēlieties vēlamo istabu, ievadot tās numuru
4. Rezervācija tiks veikta un saglabāta sistēmā

### Rezervācijas apskate
1. Izvēlieties opciju "4" no galvenā izvēlnes
2. Sistēma attēlos visas Jūsu pašreizējās rezervācijas

### Rezervācijas atcelšana
1. Izvēlieties opciju "5" no galvenā izvēlnes
2. Izvēlieties rezervāciju, kuru vēlaties atcelt
3. Apstipriniet atcelšanu

### Pieejamo istabu apskate
1. Izvēlieties opciju "2" no galvenā izvēlnes
2. Sistēma attēlos visu pieejamo (nerezervēto) istabu sarakstu

## Svarīgas piezīmes
- Rezervācijas ir beztermiņa līdz brīdim, kad tās tiek manuāli atceltas
- Visi dati tiek saglabāti CSV failā, tāpēc ir svarīgi neizdzēst vai nedamaginēt šos failus
- Ja rodas problēmas ar datu ielādi, vienmēr varat izmantot `CsvRoomSeeder.java`, lai atjaunotu sākotnējos datus

## Izstrādātāji
- Maksims Zamovskis
- Timurs Massans
  DP2-2 "Hotel room reservation" sistēma