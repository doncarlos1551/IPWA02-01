# IPWA02-01
IPWA02-01 – Programmierung von industriellen Informationssystemen mit Java EE

In diesem Repository befindet sich meine Lösung zur Aufgabenstellung des Kurses IPWA02-01. Hierbei habe ich mich für die "Aufgabenstellung 1: Like Hero To Zero" entschieden. Dieses Projekt baut auf dem vorangegangenen Projekt aus dem Kurs IPWA01-01 auf und verwendet Teile dessen Quellcodes wieder, [hier](https://github.com/doncarlos1551/IPWA01-01/) ein link zum entsprechenden repository. Dieses Projekt lässt sich in Frontend und Backend unterteilen.

## Frontend
Als Basis für das Frontend wurde der Code aus dem [alten Repository](https://github.com/doncarlos1551/IPWA01-01/) in den Frontend-Ordner kopiert und weiterentwickelt, aber auch angepasst. 

Um die Frontend-Webapp ausführen zu können muss diese entweder gebuildet werden, oder es kann ein Dev-Server gestartet werden. Die Webapp wurde mit Hilfe von Vue3 und Quasar gebaut. Um die Web-App bei ihnen Test-Weise lokal auszuführen benötigen Sie folgendes:
- Git installiert auf ihrem System
- Node.js installiert auf Ihrem System
- Yarn (Node-Modul)
- @quasar/cli (Node-Modul)
- @vue/cli (Node-Modul)

Installation und Ausführung
- Klonen des Repositories
```bash
git clone https://github.com/doncarlos1551/IPWA02-01.git
```
- Navigieren zum Projektverzeichnis
```bash
cd frontend
```
- Installieren der benötigten Node-Module
```bash
yarn install
```
- Starten des Development-Servers
```bash
yarn run quasar dev
```

Nach diesen Schritten sollte die Web-App auf Ihrem lokalen Dev-Server laufen und im Browser über localhost erreichbar sein. Sie werden in ihrer Konsole die lokale URL mitsamt port genannt bekommen, auf welchem ihr Dev-Server läuft.
*Wichtig!* Der Funktionsumfang des Frontends lässt sich ohne das entsprechende Backen nicht nutzen. Oben rechts in der Navigation befindet sich ein "Zahnrad"-Symbol mit Hilfe dessen die BaseUrl über welche der Backend-Server Erreichbar ist eintragen lässt. Im gleichen Fenster lässt sich ein Test-Request abschicken, der eine Benachrichtigung in Grün bei Erfolg und Gelb bei Misserfolg anzeigt.

## Backend
Um die Backend-Anwendung auszuführen muss das Projekt im Backend-Ordner per Maven gebuildet werden und dann in einem WildFly-Server gestartet werden. Bei der Backend-Anwendung handelt es sich um eine RESTful Api, welche in Java entwickelt wurde mit Hilfe von Jakarta EE, JAX-RS und Hibernate, sowie ein paar anderen Libraries. Um die Backend-Anwendung ausführen zu können benötigen sie:
- Git installiert auf ihrem System
- Java JDK (Java 11 oder 17) installiert auf ihrem System
- WildFly 30.0.0.Final Server Installation auf ihrem System
- Maven zum builden des Projekts
- MySQL installiert auf ihrem System

### Installation und Ausführung
- Klonen des Repositories (falls nicht bereits geschehen)
```bash
git clone https://github.com/doncarlos1551/IPWA02-01.git
```
- Navigieren zum Projektverzeichnis
```bash
cd backend
```
- Maven-Build durchführen
```bash
mvn clean install
```
- Erstellen einer MySql-Datenbank mit einem Nutzer, welcher zugriff darauf hat z.B. über MySQL Configurator auf Windows oder per bevorzugter Shell in ihrem Betriebsystem. Wenn Sie die Konfiguration "standalone.xml" welche ich in diesem Projekt zur Verfügung stelle nutzen wollen ohne etwas abzuändern, dann nennen Sie ihre Datenbank bitte "heroToZero" und erstellen Sie einen nutzer namens "test" mit passwort ebenfalls "test". Andernfalls passen Sie anschließend in der "standalone.xml" die Daten im "datasource"-tag ab Zeile 130 an ihre MySql-Konfiguration an.
*Achtung!* Die MySQL Datenbank kann ruhig leer sein, da JPA Hibernate sich darum kümmern wird die nötigen Tabellen zu erstellen! Lassen sie die DatenBank also bitte einfach leer!
- Konfigurieren des WildFly servers:
Laden Sie "mysql-connector-j-8.2.0.jar" herunter, vorzugsweise auf der [MySql-Seite](https://dev.mysql.com/downloads/connector/j/?os=26), und kopieren sie es in den "/standalone/deployments/"-Ordner ihres Wildfly-Servers. Kopieren sie in den selben Ordner ebenfalls die per Maven-Build generierte ".war"-Datei aus dem Target-Verzeichnis des Backend-Projekts. Kopieren Sie die "standalone.xml" welche sich im "wildfly-config" ordner befinden in den "/standalone/configuration/" ordner und ersetzen sie die bestehende config oder nennen sie eine davon um.
- OPTIONAL: Generieren sie neue Private und Public Keys über das Powershell-Script "rsa-to-sysenv" und kommentieren sie vor dem Maven Build die entsprechenden Zeilen der "KeyVerwaltung"-Klasse wieder ein und die jeweils folgende aus wie im Kommentar beschrieben. Der neue Public-Key muss jedoch auch noch in "src/main/resources/META-INF" kopiert werden damit Microprofile JWT richtig funktioniert. Dieser Schritt ist nur wichtig um für Sicherheit zu sorgen, da hier der Beispiel Private-Key bereits in einem öffentlichen Repository allgemein zur Verfügung gestellt wurde.
- Starten Sie den WildFly-Server:
Öffnen sie ein Terminal im Verzeichnis "/bin/" ihres Wildfly servers.
```bash
./standalone.sh -c standalone.xml
```
```powershell oder cmd
standalone.bat -c standalone.xml
```
Tauschen sie "standalone.xml" aus, falls sie einen anderen Namen verwendet haben für die Konfiguration

### Testen der Endpunkte in Postman
Um die Endpunkte zu Testen können Sie Postman verwenden und die Collection aus diesem Repository "IPWA02-01.postman_collection.json" in Postman importieren. Ersetzen sie die BaseUrl durch die Url auf welcher ihr server bei ihnen Lokal läuft oder eine URL mit einer laufenden Instanz des Backends (In meiner Arbeit Stelle ich eine solche URL zur Verfügung!)

Nutzen sie erst eine der Login funktionen um sich als Admin oder als anderer Nutzer anzumelden. Initial existiert noch kein normaler nutzer mit "User"-Berechtigung. Der Token wird in der Postman Collection automatisch gespeichert und wird für alle nachfolgenden Requests zur Authentifizierung genutzt.

## Weiteres
Falls Sie auf einem Gerät sowohl Backend als auch Frontend laufen lassen wollen, passen sie den Port für HTTP in der "standalone.xml" an falls sie diesen ändern wollen. Falls nicht starten sie *zu erst* den Backend Server und anschließend den Quasar Dev Server, da der Quasar Dev Server automatisch den Port wechselt falls dieser Belegt sein sollte.

Falls sie nicht den Dev Server verwenden sondern eine gebuildete Version des Frontends Verwenden beispielsweise für ein produktives Deoployment, kann ich ihnen empfehlen nginx als Webserver und Reverse Proxy zu nutzen und den WildFly-Server auf einem beliebigen anderen Port als 8080 (für http) laufen zu lassen. Sie können dann per Reverse Proxy das Backend auf einem beliebigen Pfad zur Verfügung zu stellen. Das Frontend kann dann in ein entsprechendes location block gebracht werden um Frontend und Backend auf dem selben server laufen zu lassen.

*Bei Problemen mit der Konfiguration oder Ausführung der Anwendungen Kontaktieren Sie mich Gerne!*
