#!/bin/sh
MAVEN_PROJECTBASEDIR=$(cd "$(dirname "$0")"; pwd)
WRAPPER_JAR="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.jar"
if [ ! -f "$WRAPPER_JAR" ]; then
  echo "Downloading Maven Wrapper jar..."
  MVNW_REPOURL="https://repo.maven.apache.org/maven2/io/takari/maven-wrapper/0.5.6/maven-wrapper-0.5.6.jar"
  mkdir -p "$MAVEN_PROJECTBASEDIR/.mvn/wrapper"
  (curl -fsSL "$MVNW_REPOURL" -o "$WRAPPER_JAR") || (wget -q "$MVNW_REPOURL" -O "$WRAPPER_JAR")
fi
exec java -Dmaven.multiModuleProjectDirectory="$MAVEN_PROJECTBASEDIR" -cp "$WRAPPER_JAR" org.apache.maven.wrapper.MavenWrapperMain "$@"
