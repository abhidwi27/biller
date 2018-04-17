
@Echo off

cd "C:\biller\src\main\webapp\Workbench"

ECHO "Generating ILC/SLA Report, Please wait...."

cscript ConnectMacro.vbs %1 %2

ECHO " ILC/SLA Generation complete!! please close the window to proceed "

Pause
