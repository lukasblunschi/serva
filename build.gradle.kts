plugins {
    java
    war
    application
    distribution
}

distributions {
    named("main") {
        contents {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
    }
}

// Toggle to disable transitive dependency resolution globally during migration.
// Set to `false` to keep Gradle's default transitive resolution.
val excludeTransitive = true

if (excludeTransitive) {
    // Configure all configurations to not resolve transitive dependencies.
    // This means only the explicitly declared artifacts will be fetched.
    configurations.configureEach {
        isTransitive = false
    }
}

group = "ch.serva"
version = "0.3.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

application {
    mainClass.set("ch.serva.RunServaStandalone")
}

war {
    // package the existing `war/` directory into the produced WAR
    webAppDirName = "war"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    // Some older JBoss-spec artifacts may be available from the JBoss releases repo
    maven("https://repository.jboss.org/nexus/content/repositories/releases/")
    // Fallback to local vendor jars while migrating: do not overwrite existing lib/
    flatDir {
        dirs("lib", "lib/hibernate", "lib/fop", "lib/jetty")
    }
}

dependencies {
    // Commons
    implementation("commons-fileupload:commons-fileupload:1.5")
    implementation("commons-io:commons-io:2.15.1")
    implementation("commons-logging:commons-logging:1.3.0")
    implementation("com.sun.mail:javax.mail:1.6.2")
    implementation("javax.activation:activation:1.1.1")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client:3.3.2")
    implementation("io.github.reload4j:reload4j:1.2.25")

    // FOP and related
    implementation("org.apache.xmlgraphics:fop:2.9")
    implementation("org.apache.xmlgraphics:fop-core:2.9")
    implementation("org.apache.xmlgraphics:fop-events:2.9")
    implementation("org.apache.xmlgraphics:fop-util:2.9")
    implementation("org.apache.xmlgraphics:xmlgraphics-commons:2.9")
    implementation("org.apache.xmlgraphics:batik-all:1.17")
    implementation("org.apache.pdfbox:fontbox:2.0.27")
    implementation("com.thoughtworks.qdox:qdox:1.12")

    // Hibernate and persistence
    implementation("org.hibernate:hibernate-core:5.6.15.Final")
    implementation("org.hibernate:hibernate-entitymanager:5.6.15.Final")
    implementation("org.hibernate:hibernate-c3p0:5.6.15.Final")
    implementation("org.hibernate.common:hibernate-commons-annotations:5.1.2.Final")
    implementation("org.jboss.logging:jboss-logging:3.4.3.Final")
    implementation("org.jboss:jandex:2.4.2.Final")
    implementation("net.bytebuddy:byte-buddy:1.12.18")
    implementation("com.mchange:mchange-commons-java:0.2.19")
    implementation("com.mchange:c3p0:0.9.5.5")
    implementation("com.fasterxml:classmate:1.5.1")
    implementation("javax.persistence:javax.persistence-api:2.2")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("javax.activation:javax.activation-api:1.2.0")
    // mark transaction API as runtime-only so the standalone includes it, but
    // exclude it from the WAR (container should provide this)
    runtimeOnly("org.jboss.spec:jboss-transaction-api_1.2_spec:1.1.1.Final")
    implementation("antlr:antlr:2.7.7")

    // Jetty (runtime only for standalone)
    runtimeOnly("org.eclipse.jetty:jetty-continuation:7.6.7.v20120910")
    runtimeOnly("org.eclipse.jetty:jetty-http:7.6.7.v20120910")
    runtimeOnly("org.eclipse.jetty:jetty-io:7.6.7.v20120910")
    runtimeOnly("org.eclipse.jetty:jetty-security:7.6.7.v20120910")
    runtimeOnly("org.eclipse.jetty:jetty-server:7.6.7.v20120910")
    runtimeOnly("org.eclipse.jetty:jetty-servlet:7.6.7.v20120910")
    runtimeOnly("org.eclipse.jetty:jetty-util:7.6.7.v20120910")

    // Servlet API is provided by container for WARs
    compileOnly("javax.servlet:servlet-api:2.5")
}

// Copy runtime jars and the produced project jar into build/lib/ so the
// existing run script can use separate jars (no fat jar).
tasks.register<Copy>("copyRuntimeLibs") {
    // depend only on the project JAR so we don't trigger the WAR task
    // (avoids duplicate packaging issues during migration)
    dependsOn("jar")
    from(configurations.runtimeClasspath)
    from(tasks.named("jar"))
    into(layout.buildDirectory.dir("lib"))
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

// Avoid duplicate file errors when distributions also copy runtime libraries
tasks.withType<Copy> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

// distribution duplicatesStrategy configured above in `distributions` block
