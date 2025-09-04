@echo off
java -version >nul 2>&1
if errorlevel 1 (
    echo Java is not installed!
    pause
    exit /b 1
)

if not exist build mkdir build
if not exist build\classes mkdir build\classes

REM Compile Java files
javac -d build\classes src\main\java\com\taskmanager\*.java
if errorlevel 1 (
    echo Compilation failed!
    pause
    exit /b 1
)

REM Copy resource files
if exist src\main\resources (
    xcopy /E /I /Y src\main\resources\* build\classes\
    echo Resources copied successfully
) else (
    echo No resources directory found - that's okay
)

echo Compilation successful! Running TaskManager...
java -cp build\classes com.taskmanager.TaskManagerCLI %*
pause