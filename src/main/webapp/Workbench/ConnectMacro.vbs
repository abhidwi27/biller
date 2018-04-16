'**************************************************************************************************
'MODULE : CONNECTMACROS.VBS
'AUTHOR : SUPREETH VANKADARI

'THIS FILE WILL BE THE BASE FILE FOR RUNNING ALL THE MACROS RESIDING IN DIFFERENT SHEETS.
'THIS FILE WILL ALSO DEAL WITH CREATION OF BACKUP'S OF SLA AND ILC SHEETS FOR EVERY UNIQUE RUN.

'***************************************************************************************************

'******************************************DECLARATIONS*********************************************

Dim args,objExcel,objExcel1
Dim fso,vFolder,fso1
Dim filesys
Dim splitvar
Dim FolderName
Dim SLA
Dim vbpath
Dim currentpath
Dim backupath,backupdir
Dim times
'***************************************************************************************************

'*******************************************APP PATH LOCATOR*****************************************

set fso1 = CreateObject("Scripting.FileSystemObject")
vbpath = fso1.GetParentFolderName(WScript.ScriptFullName)
currentpath = Left(WScript.ScriptFullName, InStrRev(WScript.ScriptFullName, "\"))
backupath = split(currentpath,"\")
'msgbox(backupath (0))
backupdir = backupath (0) + "\"
'***************************************************************************************************

'*******************************************ILC GENERATION******************************************

times =  Right("0" & Hour(Now), 2) & Right("0" & Minute(Now), 2) & Right("0" & Second(Now), 2)
'msgbox(times)
 set args = wscript.Arguments
 directory = args.item(0)
 SLA = args.item(1)

set objExcel = Createobject("Excel.Application")
objExcel.Workbooks.Open(currentpath&"ILC Macro.xlsm")
objExcel.Visible  =  False
objExcel.Run  "'ILC Macro.xlsm'!ThisWorkbook.Copy_Macro_ILC"
objExcel.Activeworkbook.Save
objExcel.Activeworkbook.Close(0)
objExcel.Quit

'***************************************************************************************************

'*******************************************SLA GENERATION*******************************************

If (SLA=1) Then
set objExcel1 = Createobject("Excel.Application")
objExcel1.Workbooks.Open(currentpath&"FFIC SLA Report.xlsm")
objExcel1.Visible  =  False
objExcel1.Run  "'FFIC SLA Report.xlsm'!ThisWorkbook.Generate_SLA"
objExcel1.Activeworkbook.Save
objExcel1.Activeworkbook.Close(0)
objExcel1.Quit
End If

'***************************************************************************************************

'**************************************BACKUP FOLDER CREATION***************************************

splitvar = split(directory,"-",2)
FolderName = splitvar(1)


Set fso = CreateObject("Scripting.FileSystemObject")
If fso.FolderExists(backupdir&"billerData\Downloads\"&FolderName) Then
Else
Set vFolder = fso.CreateFolder(backupdir&"billerData\Downloads\"&FolderName)
CreateFolderDemo = vFolder.Path
End If

'***************************************************************************************************

'*************************************MOVE FILES TO BACKUP FOLDER***********************************


If (SLA=1) Then

set filesys=CreateObject("Scripting.FileSystemObject")

If filesys.FileExists(currentpath&"FFIC SLA Report.xlsx") Then

filesys.CopyFile currentpath&"FFIC SLA Report.xlsx", backupdir&"billerData\Downloads\"&FolderName&"\"

'filesys.MoveFile "C:\Biller\Workbench\BackUp\"&FolderName&"\FFIC SLA Report.xlsx" , "C:\Biller\Workbench\BackUp\"&FolderName&"\FFIC SLA Report_"&directory &".xlsx"

filesys.MoveFile backupdir&"billerData\Downloads\"&FolderName&"\FFIC SLA Report.xlsx" , backupdir&"billerData\Downloads\"&FolderName&"\SLA Report_"&directory &"_"&times &".xlsx"

End If

Else


set filesys=CreateObject("Scripting.FileSystemObject")

If filesys.FileExists(currentpath&"FFIC ILC Report.xlsx") Then

filesys.CopyFile currentpath&"FFIC ILC Report.xlsx", backupdir&"billerData\Downloads\"&FolderName&"\"

filesys.MoveFile backupdir&"billerData\Downloads\"&FolderName&"\FFIC ILC Report.xlsx" , backupdir&"billerData\Downloads\"&FolderName&"\ILC Report_"&directory &"_"&times &".xlsx"

End If
End If

'***************************************************************************************************
'***************************************************************************************************
