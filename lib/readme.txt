Readme Libraries of Serva
-------------------------

Library                                        What                                    Use

- activation-1.1.1.jar                         JavaBeans Activation Framework (JAF)    Email notifications (implicitly used by mail-1.4.2.jar)
- commons-fileupload-1.2.1.jar                 Commons File Upload                     Post requests
- commons-io-1.4.jar                           Commons I/O                             Post requests
- ejb3-persistence-1.0.2-GA.jar                EJB Persistence API                     Database
- junit-4.5.jar                                JUnit                                   Testing
- log4j-1.2.15.jar                             Log4j                                   Logging
- mail-1.4.2.jar                               JavaMail                                Email notifications
- mysql-connector-java-5.1.10.jar              MySQL JDBC Connector                    Database
- servlet-api-2.5-6.1.21.jar                   Java Servlet API (from Jetty 6.1.21)    Web frontend
- slf4j-api-1.5.8.jar                          SLF4j                                   Logging
- slf4j-log4j12-1.5.8.jar                      SLF4j binding to Log4j 1.2              Logging

jetty/:
- jetty-6.1.21.jar                             Jetty                                   Web frontend (standalone)
- jetty-util-6.1.21.jar                        Jetty Utilities                         Web frontend (standalone)

hibernate/:
- antlr-2.7.6.jar
- c3p0-0.9.1.jar                               Connection pool
- commons-collections-3.1.jar
- dom4j-1.6.1.jar
- hibernate-annotations-3.4.0.GA.jar           Hibernate Annotations
- hibernate-commons-annotations-3.4.0.GA.jar
- hibernate-core-3.3.2.GA.jar
- hibernate-entitymanager-3.4.0.GA.jar
- javaassist-3.9.0.GA.jar
- jta-1.1.jar


Notes:
- activation-1.1.1.jar is included in Java SE 6. But since we want Serva to be
  runnable on Java SE 5, we still have to include it here.

