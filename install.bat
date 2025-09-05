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
del "C:\tools\TaskManager\TaskManager-*.jar" /Q >nul 2>&1
echo Old versions cleaned
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
