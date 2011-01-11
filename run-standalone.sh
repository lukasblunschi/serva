#!/bin/bash

#
# You may use the following variable to point your installation to the right location of your JVM
#
JAVAHOME=/usr/lib/jvm/java-6-openjdk

#
# You don't need to edit the following lines
#
CLASSPATH=bin:\
dist/serva-0.2.5.jar:\
lib/activation-1.1.1.jar:\
lib/commons-fileupload-1.2.1.jar:\
lib/commons-io-1.4.jar:\
lib/ejb3-persistence-1.0.2-GA.jar:\
lib/junit-4.5.jar:\
lib/log4j-1.2.15.jar:\
lib/mail-1.4.2.jar:\
lib/mysql-connector-java-5.1.10-bin.jar:\
lib/servlet-api-2.5-6.1.21.jar:\
lib/slf4j-api-1.5.8.jar:\
lib/slf4j-log4j12-1.5.8.jar:\
lib/fop/avalon-framework-4.2.0.jar:\
lib/fop/batik-all-1.7.jar:\
lib/fop/commons-logging-1.1.1.jar:\
lib/fop/fop-0.95.jar:\
lib/fop/xml-apis-ext-1.3.04.jar:\
lib/fop/xmlgraphics-commons-1.3.1.jar:\
lib/hibernate/antlr-2.7.6.jar:\
lib/hibernate/c3p0-0.9.1.jar:\
lib/hibernate/commons-collections-3.1.jar:\
lib/hibernate/dom4j-1.6.1.jar:\
lib/hibernate/hibernate-annotations-3.4.0.GA.jar:\
lib/hibernate/hibernate-commons-annotations-3.4.0.GA.jar:\
lib/hibernate/hibernate-core-3.3.2.GA.jar:\
lib/hibernate/hibernate-entitymanager-3.4.0.GA.jar:\
lib/hibernate/javassist-3.9.0.GA.jar:\
lib/hibernate/jta-1.1.jar:\
lib/jetty/jetty-6.1.21.jar:\
lib/jetty/jetty-util-6.1.21.jar:\

#
# Show Java version
#
$JAVAHOME/bin/java -version

#
# Run Serva standalone
#
$JAVAHOME/bin/java -cp $CLASSPATH -Xmx256m ch.serva.RunServaStandalone $@

