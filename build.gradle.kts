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

// Dedicated configuration for Jetty runtime artifacts used by the
// standalone distribution. We keep Jetty off the main runtime classpath
// to avoid polluting other consumers but still copy the jars into
// `build/lib` so the standalone launcher can use them.
val jettyRuntime by configurations.creating

dependencies {

    // Commons
    implementation("commons-fileupload:commons-fileupload:1.5")                 { isTransitive = false }
    implementation("commons-io:commons-io:2.15.1")                              { isTransitive = false }
    implementation("commons-logging:commons-logging:1.3.0")                     { isTransitive = false }
    implementation("com.sun.mail:javax.mail:1.6.2")                             { isTransitive = false }
    implementation("javax.activation:activation:1.1.1")                         { isTransitive = false }
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client:3.3.2")                   { isTransitive = false }
    implementation("io.github.reload4j:reload4j:1.2.25")                        { isTransitive = false }

    // FOP and related
    implementation("org.apache.xmlgraphics:fop:2.9")                            { isTransitive = false }
    implementation("org.apache.xmlgraphics:fop-core:2.9")                       { isTransitive = false }
    implementation("org.apache.xmlgraphics:fop-events:2.9")                     { isTransitive = false }
    implementation("org.apache.xmlgraphics:fop-util:2.9")                       { isTransitive = false }
    implementation("org.apache.xmlgraphics:xmlgraphics-commons:2.9")            { isTransitive = false }
    implementation("org.apache.xmlgraphics:batik-all:1.17")                     { isTransitive = false }
    implementation("org.apache.pdfbox:fontbox:2.0.27")                          { isTransitive = false }
    implementation("com.thoughtworks.qdox:qdox:1.12")                           { isTransitive = false }

    // Hibernate and persistence
    implementation("org.hibernate:hibernate-core:5.6.15.Final")                 { isTransitive = false }
    implementation("org.hibernate:hibernate-entitymanager:5.6.15.Final")        { isTransitive = false }
    implementation("org.hibernate:hibernate-c3p0:5.6.15.Final")                 { isTransitive = false }
    implementation("org.hibernate.common:hibernate-commons-annotations:5.1.2.Final")  { isTransitive = false }
    implementation("org.jboss.logging:jboss-logging:3.4.3.Final")               { isTransitive = false }
    implementation("org.jboss:jandex:2.4.2.Final")                              { isTransitive = false }
    implementation("net.bytebuddy:byte-buddy:1.12.18")                          { isTransitive = false }
    implementation("com.mchange:mchange-commons-java:0.2.19")                   { isTransitive = false }
    implementation("com.mchange:c3p0:0.9.5.5")                                  { isTransitive = false }
    implementation("com.fasterxml:classmate:1.5.1")                             { isTransitive = false }
    implementation("javax.persistence:javax.persistence-api:2.2")               { isTransitive = false }
    implementation("javax.xml.bind:jaxb-api:2.3.1")                             { isTransitive = false }
    implementation("javax.activation:javax.activation-api:1.2.0")               { isTransitive = false }
    // mark transaction API as runtime-only so the standalone includes it, but
    // exclude it from the WAR (container should provide this)
    runtimeOnly("org.jboss.spec:jboss-transaction-api_1.2_spec:1.1.1.Final")    { isTransitive = false }
    implementation("antlr:antlr:2.7.7")                                         { isTransitive = false }

    // Jetty: compile against the API but place actual runtime jars into
    // the `jettyRuntime` configuration so they are copied into the
    // standalone `build/lib` without appearing on the normal project
    // runtimeClasspath.
    compileOnly("org.eclipse.jetty:jetty-continuation:7.6.7.v20120910")         { isTransitive = false }
    add("jettyRuntime", "org.eclipse.jetty:jetty-continuation:7.6.7.v20120910") { isTransitive = false }
    compileOnly("org.eclipse.jetty:jetty-http:7.6.7.v20120910")                 { isTransitive = false }
    add("jettyRuntime", "org.eclipse.jetty:jetty-http:7.6.7.v20120910")         { isTransitive = false }
    compileOnly("org.eclipse.jetty:jetty-io:7.6.7.v20120910")                   { isTransitive = false }
    add("jettyRuntime", "org.eclipse.jetty:jetty-io:7.6.7.v20120910")           { isTransitive = false }
    compileOnly("org.eclipse.jetty:jetty-security:7.6.7.v20120910")             { isTransitive = false }
    add("jettyRuntime", "org.eclipse.jetty:jetty-security:7.6.7.v20120910")     { isTransitive = false }
    compileOnly("org.eclipse.jetty:jetty-server:7.6.7.v20120910")               { isTransitive = false }
    add("jettyRuntime", "org.eclipse.jetty:jetty-server:7.6.7.v20120910")       { isTransitive = false }
    compileOnly("org.eclipse.jetty:jetty-servlet:7.6.7.v20120910")              { isTransitive = false }
    add("jettyRuntime", "org.eclipse.jetty:jetty-servlet:7.6.7.v20120910")      { isTransitive = false }
    compileOnly("org.eclipse.jetty:jetty-util:7.6.7.v20120910")                 { isTransitive = false }
    add("jettyRuntime", "org.eclipse.jetty:jetty-util:7.6.7.v20120910")         { isTransitive = false }

    // Servlet API is provided by container for WARs
    compileOnly("javax.servlet:servlet-api:2.5")                                { isTransitive = false }
    add("jettyRuntime", "javax.servlet:servlet-api:2.5")                        { isTransitive = false }

    // JUnit for testing
    // Source: https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter
    // (use a release compatible with the project's Java toolchain)
    // JUnit 6 requires Java 17+, but this project targets Java 11 via the toolchain,
    // so use JUnit 5.x which is compatible with Java 11.
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// Copy runtime jars and the produced project jar into build/lib/ so the
// existing run script can use separate jars (no fat jar).
tasks.register<Copy>("copyRuntimeLibs") {
    // depend only on the project JAR so we don't trigger the WAR task
    // (avoids duplicate packaging issues during migration)
    dependsOn("jar")
    from(configurations.runtimeClasspath)
    // include the dedicated jettyRuntime jars so the standalone runtime
    // distribution contains Jetty without polluting the main runtime
    // configuration.
    from(configurations.named("jettyRuntime"))
    from(tasks.named("jar"))
    into(layout.buildDirectory.dir("lib"))
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

// Avoid duplicate file errors when distributions also copy runtime libraries
tasks.withType<Copy> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

// distribution duplicatesStrategy configured above in `distributions` block
