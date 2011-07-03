@ECHO OFF

:: configuration
CALL configuration\setup %~n0
SET __Dir_Target_Net_Core=!Dir_Target_Net:%Dir_Debug%=%Dir_Core%!
SET __Dir_Target_Output_Net_Core=!Dir_Target_Output_Net:%Dir_Debug%=%Dir_Core%!
SET __Dir_Temp_Net=!Dir_Target_Net:%Dir_Target_Sources%=%Dir_Temp%!
SET __Dir_Temp_Src2=!Dir_Src2:%Dir_Target_Sources%=%Dir_Temp%!

:: préparation de la décompilation
CALL %Cmd_Disable% %MCSJAR%
CALL %Cmd_RestorePatches%
CALL %Cmd_AddDependances%

ECHO :: Updating %~n0 sources ::

:: création du package minecraft
CALL %Cmd_RestoreMinecraftJar%

:: import des sources de core
CALL %Cmd_Delete% %Dir_Target_Net%
MKDIR %Dir_Src2% > NUL
XCOPY /I /S %__Dir_Target_Net_Core% %__Dir_Temp_Net% > NUL
XCOPY /I /S %__Dir_Target_Output_Net_Core% %__Dir_Temp_Net% > NUL

:: récupération des sources du mod
FOR /f "tokens=*" %%a IN ('TYPE %File_Obfuscation%') DO (
	CALL %Cmd_Delete% %__Dir_Temp_Src2%\%%a.class
	MOVE /Y %__Dir_Temp_Src2%\%%a.java %Dir_Src2% > NUL
)

:: création du package des dépendances
FOR /f "tokens=*" %%a IN ('TYPE %File_Dependances%') DO (
	MOVE /Y %__Dir_Temp_Src2%\%%a.class %Dir_Temp% > NUL
	MOVE /Y %__Dir_Temp_Src2%\%%a.java %Dir_Temp% > NUL
)
CALL %Cmd_Delete% %__Dir_Temp_Net% > NUL
MKDIR %__Dir_Temp_Src2% > NUL
MOVE /Y %Dir_Temp%\*.class %__Dir_Temp_Src2% > NUL
MOVE /Y %Dir_Temp%\*.java %__Dir_Temp_Src2% > NUL
%Cmd_Zip% a %Package_Dependances% %Dir_Temp%\* -r > NUL

:: ajout du répertoire des mobs
CALL %Cmd_Replace% %Dir_Target_Mod% %Dir_Mob%

:: finalisation
CALL %Cmd_Finalize%