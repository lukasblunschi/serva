plugins {
    java
    war
    application
    distribution
}

import java.io.BufferedOutputStream
import java.io.FileOutputStream
import java.io.FileInputStream
import java.io.IOException
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import java.util.zip.ZipEntry
import org.gradle.api.tasks.SourceSetContainer

distributions {
    named("main") {
        contents {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
    }
}

group = "ch.serva"
version = "0.3.1-beta1"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

war {
    // package the existing `war/` directory into the produced WAR
    webAppDirName = "war"
}

// I only want the JAR file to contain Java classes.
// Resources should be kept in the WAR under WEB-INF/classes/.
tasks.named<Jar>("jar") {

    // Ensure the produced project JAR has a predictable name like `serva-<version>.jar`.
    archiveBaseName.set("serva")
    archiveVersion.set(project.version.toString())

    // Exclude resources that originate from the main resources source dirs (src/main/resources/**).
    //
    // Processed resources are copied to build/resources/main by the resources task.
    // Exclude files coming from that output directory.
    val resourceOutputDir = layout.buildDirectory.dir("resources/main").get().asFile.toPath().toAbsolutePath().normalize()
    exclude { fileTreeElement ->
        val file = fileTreeElement.file
        val path = file.toPath().toAbsolutePath().normalize()
        path.startsWith(resourceOutputDir)
    }
}

// I prefer to have a single JAR in WEB-INF/lib instead of many unpacked classes in WEB-INF/classes.
// Create a repacked WAR from the produced WAR and add it in build/libs.
// This keeps the regular war task as is.
tasks.register("repackedWar") {
    val warTask = tasks.named<org.gradle.api.tasks.bundling.War>("war")
    val jarTask = tasks.named<Jar>("jar")
    dependsOn(warTask)
    dependsOn(jarTask)

    doLast {
        val warFile = warTask.get().archiveFile.get().asFile
        val jarFile = jarTask.get().archiveFile.get().asFile
        val tmp = file("${layout.buildDirectory.asFile.get()}/libs/serva-${project.version.toString()}-repacked.war")

        ZipFile(warFile).use { zipIn ->
            BufferedOutputStream(FileOutputStream(tmp)).use { fos ->
                ZipOutputStream(fos).use { zipOut ->
                    val entries = zipIn.entries()
                    val existingNames = mutableSetOf<String>()
                    while (entries.hasMoreElements()) {
                        val entry = entries.nextElement()
                        val name = entry.name
                        existingNames.add(name)

                        // skip compiled class files but keep non-class resources
                        // (e.g. persistence.xml, properties, xsl, etc.)
                        if (name.startsWith("WEB-INF/classes/ch/")) {
                            continue
                        }

                        // no META-INF/ entries needed in the WAR
                        if (name.startsWith("META-INF/")) {
                            continue
                        }

                        // copy all other entries as-is (preserve timestamps)
                        val newEntry = ZipEntry(name)
                        newEntry.time = entry.time
                        zipOut.putNextEntry(newEntry)
                        zipIn.getInputStream(entry).use { ins ->
                            ins.copyTo(zipOut)
                        }
                        zipOut.closeEntry()
                    }

                    // add the project JAR into WEB-INF/lib/ if it's not already
                    val jarEntryName = "WEB-INF/lib/${jarFile.name}"
                    if (!existingNames.contains(jarEntryName)) {
                        val jarEntry = ZipEntry(jarEntryName)
                        jarEntry.time = jarFile.lastModified()
                        zipOut.putNextEntry(jarEntry)
                        FileInputStream(jarFile).use { jfis ->
                            jfis.copyTo(zipOut)
                        }
                        zipOut.closeEntry()
                    }
                }
            }
        }
    }
}

// Make the assemble lifecycle produce the repacked WAR
tasks.named("assemble") {
    dependsOn(tasks.named("repackedWar"))
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    // Some older JBoss-spec artifacts may be available from the JBoss releases repo
    maven("https://repository.jboss.org/nexus/content/repositories/releases/")
    // Fallback to local vendor jars while migrating: do not overwrite existing lib/
    flatDir {
        dirs("lib/hibernate")
    }
}

dependencies {

    // Commons
    implementation("commons-fileupload:commons-fileupload:1.5")                 { isTransitive = false }
    implementation("commons-io:commons-io:2.15.1")                              { isTransitive = false }
    implementation("commons-logging:commons-logging:1.3.0")                     { isTransitive = false }
    implementation("com.sun.mail:javax.mail:1.6.2")                             { isTransitive = false }
    implementation("javax.activation:activation:1.1.1")                         { isTransitive = false }
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client:3.3.2")                   { isTransitive = false }
    implementation("ch.qos.reload4j:reload4j:1.2.25")                           { isTransitive = false }

    // Logging
    //implementation("org.apache.logging.log4j:log4j-core:2.26.0")                { isTransitive = false }
    //implementation("org.apache.logging.log4j:log4j-api:2.26.0")                 { isTransitive = false }
    //implementation("org.apache.logging.log4j:log4j-appserver:2.26.0")           { isTransitive = false }

    // FOP and related
    // notes:
    // - fop-2.9.jar         has no sources - it only contains a manifest
    // - batik-all-1.17.jar  has no sources - no sources found
    implementation("org.apache.xmlgraphics:fop:2.9")                            { isTransitive = false }
    implementation("org.apache.xmlgraphics:fop-core:2.9")                       { isTransitive = false }
    implementation("org.apache.xmlgraphics:fop-events:2.9")                     { isTransitive = false }
    implementation("org.apache.xmlgraphics:fop-util:2.9")                       { isTransitive = false }
    implementation("org.apache.xmlgraphics:xmlgraphics-commons:2.9")            { isTransitive = false }
    implementation("org.apache.xmlgraphics:batik-all:1.17")                     { isTransitive = false }
    implementation("org.apache.pdfbox:fontbox:2.0.27")                          { isTransitive = false }
    implementation("com.thoughtworks.qdox:qdox:1.12")                           { isTransitive = false }

    // Hibernate and persistence
    // notes:
    // - jboss-transaction-api_1.2_spec-1.1.1.Final.jar not found
    // - antlr-2.7.7.jar     has no sources - no sources found
    //   -> newer versions relocated to https://mvnrepository.com/artifact/org.antlr/antlr
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

    // Servlet API is provided by container for WARs
    compileOnly("javax.servlet:servlet-api:2.5")                                { isTransitive = false }

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
    from(tasks.named("jar"))
    into(layout.buildDirectory.dir("lib"))
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

// Avoid duplicate file errors when distributions also copy runtime libraries
tasks.withType<Copy> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

// distribution duplicatesStrategy configured above in `distributions` block
