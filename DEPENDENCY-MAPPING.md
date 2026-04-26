# Dependency mapping (vendored JAR -> Maven coordinate)

This file lists the current vendored jars under `lib/` and the proposed
Maven Central coordinates to use in the Gradle build. Versions are kept the
same as the vendored artifacts.

Top-level libs
- `lib/commons-fileupload-1.5.jar` -> `commons-fileupload:commons-fileupload:1.5`
- `lib/commons-io-2.15.1.jar` -> `commons-io:commons-io:2.15.1`
- `lib/commons-logging-1.3.0.jar` -> `commons-logging:commons-logging:1.3.0`
- `lib/javax.activation-1.1.1.jar` -> `javax.activation:activation:1.1.1`
- `lib/javax.mail-1.6.2.jar` -> `com.sun.mail:javax.mail:1.6.2`
- `lib/mariadb-java-client-3.3.2.jar` -> `org.mariadb.jdbc:mariadb-java-client:3.3.2`
- `lib/reload4j-1.2.25.jar` -> `org.reload4j:reload4j:1.2.25`

FOP-related (`lib/fop/`)
- `lib/fop/batik-all-1.17.jar` -> `org.apache.xmlgraphics:batik-all:1.17`
- `lib/fop/fontbox-2.0.27.jar` -> `org.apache.pdfbox:fontbox:2.0.27`
- `lib/fop/fop-2.9.jar` -> `org.apache.xmlgraphics:fop:2.9`
- `lib/fop/fop-core-2.9.jar` -> `org.apache.xmlgraphics:fop-core:2.9`
- `lib/fop/fop-events-2.9.jar` -> `org.apache.xmlgraphics:fop-events:2.9`
- `lib/fop/fop-util-2.9.jar` -> `org.apache.xmlgraphics:fop-util:2.9`
- `lib/fop/qdox-1.12.jar` -> `com.thoughtworks.qdox:qdox:1.12`
- `lib/fop/xmlgraphics-commons-2.9.jar` -> `org.apache.xmlgraphics:xmlgraphics-commons:2.9`

Hibernate and friends (`lib/hibernate/`)
- `lib/hibernate/antlr-2.7.7.jar` -> `antlr:antlr:2.7.7`
- `lib/hibernate/byte-buddy-1.12.18.jar` -> `net.bytebuddy:byte-buddy:1.12.18`
- `lib/hibernate/c3p0-0.9.5.5.jar` -> `com.mchange:c3p0:0.9.5.5`
- `lib/hibernate/classmate-1.5.1.jar` -> `com.fasterxml:classmate:1.5.1`
- `lib/hibernate/hibernate-c3p0-5.6.15.Final.jar` -> `org.hibernate:hibernate-c3p0:5.6.15.Final`
- `lib/hibernate/hibernate-commons-annotations-5.1.2.Final.jar` -> `org.hibernate.common:hibernate-commons-annotations:5.1.2.Final`
- `lib/hibernate/hibernate-core-5.6.15.Final.jar` -> `org.hibernate:hibernate-core:5.6.15.Final`
- `lib/hibernate/hibernate-entitymanager-5.6.15.Final.jar` -> `org.hibernate:hibernate-entitymanager:5.6.15.Final`
- `lib/hibernate/jandex-2.4.2.Final.jar` -> `org.jboss:jandex:2.4.2.Final`
- `lib/hibernate/javax.activation-api-1.2.0.jar` -> `javax.activation:javax.activation-api:1.2.0`
- `lib/hibernate/javax.persistence-api-2.2.jar` -> `javax.persistence:javax.persistence-api:2.2`
- `lib/hibernate/jaxb-api-2.3.1.jar` -> `javax.xml.bind:jaxb-api:2.3.1`
- `lib/hibernate/jboss-logging-3.4.3.Final.jar` -> `org.jboss.logging:jboss-logging:3.4.3.Final`
- `lib/hibernate/jboss-transaction-api_1.2_spec-1.1.1.Final.jar` -> `org.jboss.spec:jboss-transaction-api_1.2_spec:1.1.1.Final`
- `lib/hibernate/mchange-commons-java-0.2.19.jar` -> `com.mchange:mchange-commons-java:0.2.19`

Jetty (`lib/jetty/`) — runtime-only for standalone
- `lib/jetty/jetty-continuation-7.6.7.v20120910.jar` -> `org.eclipse.jetty:jetty-continuation:7.6.7.v20120910`
- `lib/jetty/jetty-http-7.6.7.v20120910.jar` -> `org.eclipse.jetty:jetty-http:7.6.7.v20120910`
- `lib/jetty/jetty-io-7.6.7.v20120910.jar` -> `org.eclipse.jetty:jetty-io:7.6.7.v20120910`
- `lib/jetty/jetty-security-7.6.7.v20120910.jar` -> `org.eclipse.jetty:jetty-security:7.6.7.v20120910`
- `lib/jetty/jetty-server-7.6.7.v20120910.jar` -> `org.eclipse.jetty:jetty-server:7.6.7.v20120910`
- `lib/jetty/jetty-servlet-7.6.7.v20120910.jar` -> `org.eclipse.jetty:jetty-servlet:7.6.7.v20120910`
- `lib/jetty/jetty-util-7.6.7.v20120910.jar` -> `org.eclipse.jetty:jetty-util:7.6.7.v20120910`
- `lib/jetty/servlet-api-2.5-jetty-7.6.7.jar` -> `javax.servlet:servlet-api:2.5` (compileOnly for WAR)

Notes
- All versions are kept identical to the vendored jars. During implementation
  each coordinate will be verified against Maven Central and corrected if a
  different groupId/artifactId is required.
