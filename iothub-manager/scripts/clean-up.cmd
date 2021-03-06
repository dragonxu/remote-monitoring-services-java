@ECHO off & setlocal enableextensions enabledelayedexpansion

:: strlen("\scripts\") => 9
SET APP_HOME=%~dp0
SET APP_HOME=%APP_HOME:~0,-9%
if "%APP_HOME:~20%" == "" (
    echo Unable to detect current folder. Aborting.
    GOTO FAIL
)

:: Clean up folders containing temporary files
echo Removing temporary folders and files...
cd %APP_HOME%
IF %ERRORLEVEL% NEQ 0 GOTO FAIL

rmdir /s /q .\target\
rmdir /s /q .\logs\
rmdir /s /q .\project\target\
rmdir /s /q .\project\project\

echo Done.

:: - - - - - - - - - - - - - -
goto :END

:FAIL
    echo Command failed
    endlocal
    exit /B 1

:END
endlocal
