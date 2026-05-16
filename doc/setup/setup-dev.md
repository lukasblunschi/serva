# Setup of Serva for Development

## 1. Create Database

```
$ sudo mariadb
> status
  (check that everything is stored in utf8)
> create database serva;
> grant all on serva.* to serva@localhost identified by '123';
> flush privileges;
> exit
```

```
$ mariadb -u serva --password=123 serva
> show databases;
> status
  (check that everything is stored in utf8)
> exit
```

## 2. Restore Empty Database

```
$ cd serva/scripts/
$ ./restore-db.sh empty.sql
```

## 3. Import Project in Eclipse

- File > Import... > Gradle > Existing Gradle Project

## 4. Install WST components

- Help > Install New Software...
  - Work with: 2026-03 - https://download.eclipse.org/releases/2026-03
    - Eclipse Faceted Project Framework
    - Eclipse Faceted Project Framework JDT Enablement
    - Eclipse Java EE Developer Tools
    - ? Eclipse Java Web Developer Tools
      -> only needed for JSP pages?
    - ? Eclipse Java Web Developer Tools - JavaScript Support
      -> only needed for JSP pages?
    - Eclipse Web Developer Tools
    - Eclipse Web Developer Tools - JavaScript Support
    - Eclipse XML Editors and Tools
    - Eclipse XSL Editors and Tools
    - ? JavaScript Development Tools
      -> needed?
    - ? JavaScript Development Tools Chromium/V8 Remote Debugger
      -> needed?
    - JST Server Adapters
    - JST Server Adapters Extensions (Apache Tomcat)
    - JST Server UI
    - WST Server Adapters

## 5. Download Eclipse Temurin 11

- https://adoptium.net/
  -> Other Downloads > JDK 11 - LTS > JDK
- unpack under /opt/eclipse-temurin/
- configure: Window > Preferences... > Java > Installed JREs > Add...

## 6. Download Tomcat 9

- https://tomcat.apache.org/
  -> Download > Tomcat 9 > Binary Distributions > Core > zip
     (the tar.gz version was not recognized by Eclipse)
- unpack under /opt/apache-tomcat/
- configure: Window > Preferences... > Server > Runtime Environments > Add...
- TODO use Log4j 2 instead of JULI (https://tomcat.apache.org/tomcat-9.0-doc/logging.html)

## 7. Add Server

- Window > Show View > Other... > Server > Servers
- add new server
  - Server name: tomcat9-8080

## 8. Configure Source Code Formatting

- Window > Preferences... > XML > XML Files > Editor > Formatting
  - Line width: 100

TODO continue here
