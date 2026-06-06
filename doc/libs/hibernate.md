# Hibernate

## Which Release to Choose

https://hibernate.org/orm/releases/

### Java Compatibility

Links:

- https://hibernate.org/orm/releases/#compatibility-matrix
- https://hibernate.org/community/integrations/#java

Version 6.6 is the last version to support Java 11.

### Java/Jakarta EE Compatibility

Links:

- https://hibernate.org/community/integrations/#jakarta_ee

Version 5.6 is the last release with Java EE version 8 support.

Version 6.6 is the last version to support Jakarta EE version 10.

### Persistence API Compatibility

Links:

- https://hibernate.org/community/integrations/#jakarta_ee_jpa

Version 5.6 is the last release with javax.persistence (JPA) support.

Version 6.6 is the last version to support Jakarta Persistence version 3.1.

## Upgrade Guide

1. Edit `build.gradle.kts`

Comment everything Hibernate related except `hibernate-entitymanager` and `hibernate-c3p0`.

Set `isTransitive = true` for `hibernate-entitymanager` and `hibernate-c3p0`.

2. Show dependencies

```
./gradlew dependencies
```

check `runtimeClasspath`:

```
...
+--- org.hibernate:hibernate-entitymanager:5.6.15.Final
|    +--- org.jboss.logging:jboss-logging:3.4.3.Final
|    +--- org.hibernate:hibernate-core:5.6.15.Final
|    |    +--- org.jboss.logging:jboss-logging:3.4.3.Final
|    |    +--- javax.persistence:javax.persistence-api:2.2
|    |    +--- net.bytebuddy:byte-buddy:1.12.18
|    |    +--- antlr:antlr:2.7.7
|    |    +--- org.jboss.spec.javax.transaction:jboss-transaction-api_1.2_spec:1.1.1.Final
|    |    +--- org.jboss:jandex:2.4.2.Final
|    |    +--- com.fasterxml:classmate:1.5.1
|    |    +--- javax.activation:javax.activation-api:1.2.0
|    |    +--- org.hibernate.common:hibernate-commons-annotations:5.1.2.Final
|    |    |    \--- org.jboss.logging:jboss-logging:3.3.2.Final -> 3.4.3.Final
|    |    +--- javax.xml.bind:jaxb-api:2.3.1
|    |    |    \--- javax.activation:javax.activation-api:1.2.0
|    |    \--- org.glassfish.jaxb:jaxb-runtime:2.3.1
|    |         +--- javax.xml.bind:jaxb-api:2.3.1 (*)
|    |         +--- org.glassfish.jaxb:txw2:2.3.1
|    |         +--- com.sun.istack:istack-commons-runtime:3.0.7
|    |         +--- org.jvnet.staxex:stax-ex:1.8
|    |         +--- com.sun.xml.fastinfoset:FastInfoset:1.2.15
|    |         \--- javax.activation:javax.activation-api:1.2.0
|    +--- org.hibernate.common:hibernate-commons-annotations:5.1.2.Final (*)
|    +--- javax.persistence:javax.persistence-api:2.2
|    +--- net.bytebuddy:byte-buddy:1.12.18
|    \--- org.jboss.spec.javax.transaction:jboss-transaction-api_1.2_spec:1.1.1.Final
+--- org.hibernate:hibernate-c3p0:5.6.15.Final
|    +--- org.jboss.logging:jboss-logging:3.4.3.Final
|    +--- org.hibernate:hibernate-core:5.6.15.Final (*)
|    \--- com.mchange:c3p0:0.9.5.5
|         \--- com.mchange:mchange-commons-java:0.2.19
...
```

### Version Notes 5.6

License: LGPL v2.1

## User Guide

https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html


