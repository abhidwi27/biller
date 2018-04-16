cd "C:\biller\src\main\webapp\Workbench"
REM ECHO %1
REM ECHO %2
cscript ConnectMacro.vbs %1 %2
ECHO "Generating ILC Report, Please wait...."
REM cscript ConnectMacro.vbs 07-JUN-2020 0
Pause
