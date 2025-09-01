@ECHO OFF
SETLOCAL
set MAVEN_PROJECTBASEDIR=%~dp0
set WRAPPER_JAR=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar
IF NOT EXIST "%WRAPPER_JAR%" (
  ECHO Downloading Maven Wrapper jar...
  powershell -Command "Invoke-WebRequest -Uri https://repo.maven.apache.org/maven2/io/takari/maven-wrapper/0.5.6/maven-wrapper-0.5.6.jar -OutFile %WRAPPER_JAR%"
)
java -Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR% -cp "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
