@ECHO OFF

:: configuration
CALL configuration\setup %~n0
SET __Dir_Target_Net_Debug=!Dir_Target_Net:%Dir_Core%=%Dir_Debug%!
SET __Dir_Src2_Debug=!Dir_Src2:%Dir_Core%=%Dir_Debug%!

:: préparation de la décompilation
CALL %Cmd_Disable% %MCJAR%
CALL %Cmd_RestoreMinecraftServerJar%

:: décompilation des sources
CALL %Cmd_CleanUp%
CALL %Cmd_Decompile%

ECHO :: Updating %~n0 sources ::

:: récupération des sources décompilées
CALL %Cmd_Delete% %Dir_Target_Net%

CALL %Cmd_Replace% %Dir_Src1% %MCPMCSSRC1%
CALL %Cmd_Replace% %Dir_Src2% %MCPMCSSRC2%

:: copie des sources de debug
XCOPY /I /S /Y %__Dir_Target_Net_Debug% %Dir_Target_Net% > NUL

:: mise à jour du fichier d'obfuscation
CALL %Cmd_Delete% %File_Obfuscation%
FOR /f %%a IN ('DIR %__Dir_Src2_Debug% /A:-D /B') DO (
	ECHO %%~na>> %File_Obfuscation%
)

:: finalisation
CALL %Cmd_Finalize%