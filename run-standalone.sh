#!/bin/bash

#
# You may use the following variable to point your installation to the right location of your JVM
#
JAVAHOME=/usr/lib/jvm/java-11-openjdk-amd64

#
# You don't need to edit the following lines
#
# classpath is now produced by Gradle.
# Run './gradlew clean assemble copyRuntimeLibs'
# to create the application JAR and populate `build/lib/` with runtime jars.
#
# Ensure the application jar exists (produced by Gradle)
APP_JAR=$(ls build/libs/serva-*.jar 2>/dev/null | head -n1)
if [ -z "$APP_JAR" ]; then
  echo "Application JAR not found. Run './gradlew clean assemble copyRuntimeLibs' first."
  exit 1
fi

# Use the built application jar first, then the runtime libraries under build/lib/
CLASSPATH="$APP_JAR:build/lib/*"
#
# Show Java version
#
$JAVAHOME/bin/java -version

#
# Run Serva standalone
#
$JAVAHOME/bin/java -cp $CLASSPATH -Xmx256m ch.serva.RunServaStandalone $@

