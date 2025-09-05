@echo off
java -version >nul 2>&1
if errorlevel 1 (
    echo Java is not installed!
    pause
    exit /b 1
)

if not exist build mkdir build
if not exist build\classes mkdir build\classes

set NEED_COMPILE=0
set NEED_RESOURCES=0

REM Check if Java files need compilation
if not exist build\classes\com\taskmanager\TaskManagerCLI.class (
    set NEED_COMPILE=1
    echo First build - compiling...
) else (
    for %%f in (src\main\java\com\taskmanager\*.java) do (
        if "%%f" neq "" (
            for /f %%i in ('powershell -command "(Get-Item 'build\classes\com\taskmanager\TaskManagerCLI.class').LastWriteTime -lt (Get-Item '%%f').LastWriteTime"') do (
                if "%%i"=="True" (
                    set NEED_COMPILE=1
                    echo Source files changed - recompiling...
                )
            )
        )
    )
)

REM Compile if needed
if %NEED_COMPILE%==1 (
    echo Compiling Java files...
    javac -d build\classes src\main\java\com\taskmanager\*.java
    if errorlevel 1 (
        echo Compilation failed!
        pause
        exit /b 1
    )
    set NEED_RESOURCES=1
) else (
    echo Java files up to date - skipping compilation
)

REM Check if resources need copying
if exist src\main\resources (
    if not exist build\classes\config.properties (
        set NEED_RESOURCES=1
        echo Resource files missing - copying...
    ) else (
        for %%f in (src\main\resources\*) do (
            if "%%f" neq "" (
                for /f %%i in ('powershell -command "(Get-Item 'build\classes\config.properties').LastWriteTime -lt (Get-Item '%%f').LastWriteTime"') do (
                    if "%%i"=="True" (
                        set NEED_RESOURCES=1
                        echo Resource files changed - copying...
                    )
                )
            )
        )
    )
)

REM Copy resources if needed
if %NEED_RESOURCES%==1 (
    if exist src\main\resources (
        xcopy /E /I /Y /Q src\main\resources\* build\classes\ >nul
        echo Resources copied successfully
    )
) else (
    if exist src\main\resources (
        echo Resource files up to date - skipping copy
    )
)

echo Running TaskManager...
java -cp build\classes com.taskmanager.TaskManagerCLI %*
pause