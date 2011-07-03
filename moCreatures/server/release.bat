@ECHO OFF

:: configuration
CALL configuration\setup %~n0
SET __Dir_Target_Net_Debug=!Dir_Target_Net:%Dir_Release%=%Dir_Debug%!
SET McJadOutNet=!Dir_Target_Net:%Dir_Target_Sources%=%MCJADOUT%!
SET __File_Version=%MCREOBDIR%\Version
SET __Dir_Mob_Temp=!Dir_Mob:%Dir_Common%=%Dir_Temp%!
::SET __Package_Public_Temp=!Package_Public:.jar=.tmp.jar!

:: préparation de l'obfuscation
CALL %Cmd_Disable% %MCSJAR%
CALL %Cmd_RestoreMinecraftJar%
CALL %Cmd_MakeScript%
CALL %Cmd_FixPatch%
CALL %Cmd_AddDependances%
CALL %Cmd_RestorePatches%
CALL %Cmd_Backup% %MCPMCOBF%
CALL %Cmd_Replace% %MCPMCOBF% %File_Obfuscation%

:: obfuscation du code en utilisant les sources de debug
CALL %Cmd_CleanUp%
CALL %Cmd_Decompile%
XCOPY /I /S /Y %__Dir_Target_Net_Debug% %McJadOutNet% > NUL
CALL %Cmd_Recompile%
CALL %Cmd_Reobf%

ECHO :: Updating %~n0 sources ::

:: construction du package public
CALL %Cmd_Delete% %Package_Public%
ECHO %Version%> %__File_Version%
CALL %Cmd_Backup% %File_Manifest%
ECHO Implementation-Version: %Version%>> %File_Manifest%
ECHO Specification-Version: %Version%>> %File_Manifest%
XCOPY /I /S %MCREOBDIR%\* %Dir_Temp% > NUL
XCOPY /I /S %Dir_Mob%\* %__Dir_Mob_Temp% > NUL
%Cmd_Jar% cfm %Package_Public% %File_Manifest% -C %Dir_Temp%\ .
::%Cmd_Jar% cfm %__Package_Public_Temp% %File_Manifest% -C %Dir_Temp%/ .
::%Cmd_JarSigner% %Package_Public% %__Package_Public_Temp% %Cmd_JarSigner_Args%
::CALL %Cmd_Delete% %__Package_Public_Temp%
CALL %Cmd_Restore% %File_Manifest%
CALL %Cmd_Replace% %Package_Mod% %Package_Public%

:: finalisation
CALL %Cmd_Finalize%
