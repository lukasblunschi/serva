#!/bin/bash

#
# You may use the following variable to point your installation to the right location of your JVM
#
JAVAHOME=/usr/lib/jvm/java-11-openjdk-amd64

#
# You don't need to edit the following lines
#
CLASSPATH=bin:\
dist/serva-0.2.7.jar:\
lib/commons-fileupload-1.5.jar:\
lib/commons-io-2.15.1.jar:\
lib/commons-logging-1.3.0.jar:\
lib/javax.activation-1.1.1.jar:\
lib/javax.mail-1.6.2.jar:\
lib/mariadb-java-client-3.3.2.jar:\
lib/reload4j-1.2.25.jar:\
lib/fop/batik-all-1.17.jar:\
lib/fop/fontbox-2.0.27.jar:\
lib/fop/fop-2.9.jar:\
lib/fop/fop-core-2.9.jar:\
lib/fop/fop-events-2.9.jar:\
lib/fop/fop-util-2.9.jar:\
lib/fop/qdox-1.12.jar:\
lib/fop/xmlgraphics-commons-2.9.jar:\
lib/hibernate/antlr-2.7.7.jar:\
lib/hibernate/byte-buddy-1.12.18.jar:\
lib/hibernate/c3p0-0.9.5.5.jar:\
lib/hibernate/classmate-1.5.1.jar:\
lib/hibernate/hibernate-c3p0-5.6.15.Final.jar:\
lib/hibernate/hibernate-commons-annotations-5.1.2.Final.jar:\
lib/hibernate/hibernate-core-5.6.15.Final.jar:\
lib/hibernate/hibernate-entitymanager-5.6.15.Final.jar:\
lib/hibernate/jandex-2.4.2.Final.jar:\
lib/hibernate/javax.activation-api-1.2.0.jar:\
lib/hibernate/javax.persistence-api-2.2.jar:\
lib/hibernate/jaxb-api-2.3.1.jar:\
lib/hibernate/jboss-logging-3.4.3.Final.jar:\
lib/hibernate/jboss-transaction-api_1.2_spec-1.1.1.Final.jar:\
lib/hibernate/mchange-commons-java-0.2.19.jar:\
lib/jetty/jetty-continuation-7.6.7.v20120910.jar:\
lib/jetty/jetty-http-7.6.7.v20120910.jar:\
lib/jetty/jetty-io-7.6.7.v20120910.jar:\
lib/jetty/jetty-security-7.6.7.v20120910.jar:\
lib/jetty/jetty-server-7.6.7.v20120910.jar:\
lib/jetty/jetty-servlet-7.6.7.v20120910.jar:\
lib/jetty/jetty-util-7.6.7.v20120910.jar:\
lib/jetty/servlet-api-2.5-jetty-7.6.7.jar:\
#
# Show Java version
#
$JAVAHOME/bin/java -version

#
# Run Serva standalone
#
$JAVAHOME/bin/java -cp $CLASSPATH -Xmx256m ch.serva.RunServaStandalone $@

