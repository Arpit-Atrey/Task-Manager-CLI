@echo off
REM Build and Install TaskManager System-wide

echo Building TaskManager...
mvn package -q
if errorlevel 1 (
    echo Build failed!
    pause
    exit /b 1
)

echo Installing to system...
echo Cleaning old versions...
if exist "C:\tools\TaskManager\TaskManager-*.jar" (
    del "C:\tools\TaskManager\TaskManager-*.jar"
    echo Old versions removed
) else (
    echo No old versions found
)
echo Copying new version...
copy /Y "target\TaskManager-1.0.0.jar" "C:\tools\TaskManager\"
if errorlevel 1 (
    echo Installation failed!
    pause
    exit /b 1
)

echo TaskManager installed successfully!
echo You can now use: task list

pause
