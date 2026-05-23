# Setup of Serva for Development

## Setup Database

### 1. Create Database

Create database:

```
$ sudo mariadb
> status
  (check that everything is stored in utf8)
> create database serva;
> grant all on serva.* to serva@localhost identified by '123';
> flush privileges;
> exit
```

Make sure connect works:

```
$ mariadb -u serva --password=123 serva
> show databases;
> status
  (check that everything is stored in utf8)
> exit
```

### 2. Restore Empty Database

Restore empty schema:

```
$ cd serva/scripts/
$ ./restore-db.sh empty.sql
```

## Setup Eclipse

### 1. Install WST Components

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

### 2. Install Eclipse Temurin 11

- Download from https://adoptium.net/
  -> Other Downloads > JDK 11 - LTS > JDK

- Unpack under /opt/eclipse-temurin/

- Configure: Window > Preferences... > Java > Installed JREs > Add...

### 3. Install Tomcat 9

- Download from https://tomcat.apache.org/
  -> Download > Tomcat 9 > Binary Distributions > Core > zip
     (the tar.gz version was not recognized by Eclipse)

- Unpack under /opt/apache-tomcat/

- Configure: Window > Preferences... > Server > Runtime Environments > Add...

- Configure to use Log4j
    - see https://tomcat.apache.org/tomcat-9.0-doc/logging.html
    - see https://logging.apache.org/log4j/2.x/jakarta.html#replace-tomcat

- Create log4j dir inside unpack dir, e.g.

```
$ mkdir /opt/apache-tomcat/apache-tomcat-9.0.118/log4j
```

- Copy libs into log4j dir

```
$ cd /opt/apache-tomcat/apache-tomcat-9.0.118/log4j/
$ cp log4j-appserver-2.26.0.jar .
$ cp log4j-api-2.26.0.jar       .
$ cp log4j-core-2.26.0.jar      .
```

- Add log4j2.xml into log4j dir

```
$ nano log4j2.xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration xmlns="https://logging.apache.org/xml/ns"
               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="
                   https://logging.apache.org/xml/ns
                   https://logging.apache.org/xml/ns/log4j-config-2.xsd">
  <Appenders>
    <Console name="CONSOLE">
      <PatternLayout pattern="%d [%t] %p %c - %m%n"/>
    </Console>
  </Appenders>
  <Loggers>
    <Root level="INFO">
      <AppenderRef ref="CONSOLE"/>
    </Root>
  </Loggers>
</Configuration>
```

### 4. Configure Source Code Formatting

- Window > Preferences... > XML > XML Files > Editor > Formatting
  - Line width: 100

### 5. Import Project in Eclipse

- File > Import... > Gradle > Existing Gradle Project

### 6. Add Server

- Window > Show View > Other... > Server > Servers

- Add new server
    - Server name: tomcat9-8080

- Replace `context.xml` and `server.xml` with the versions provided
  under `doc/setup/tomcat/` named
  `context.xml_tomcat9-dev.xml` and `server.xml_tomcat9-dev.xml`.

- Right-click on tomcat9-8080 > Clean...

- Run once to create Run Configuration

- Run > Debug Configurations... > tomcat9-8080 > Classpath > User Entries
    - add the JAR files and the log4j folder itself

