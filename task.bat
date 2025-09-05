@echo off
REM TaskManager - Maven Build Script
REM Usage: task.bat [command] [args...]

REM Check if Maven is installed
if exist "C:\apache-maven-3.9.11\bin\mvn.cmd" (
    set "MAVEN_CMD=C:\apache-maven-3.9.11\bin\mvn.cmd"
) else (
    set MAVEN_CMD=mvn
)

%MAVEN_CMD% -version >nul 2>&1
if errorlevel 1 (
    echo Maven is not installed! Using legacy build...
    echo Install Maven from: https://maven.apache.org/download.cgi
    call task-legacy.bat %*
    exit /b
)

echo Using Maven at: %MAVEN_CMD%
echo Building TaskManager JAR...
%MAVEN_CMD% package -q
if errorlevel 1 (
    echo Maven build failed with code %errorlevel%! Using legacy build...
    call task-legacy.bat %*
    exit /b
) else (
    echo Build successful!
)

echo Running: java -jar target\TaskManager-1.0.0.jar %*
java -jar target\TaskManager-1.0.0.jar %*

pause
