param
(
    [bool]$chain = $false
)

# configuration
. .\configuration\setup.ps1 "release"

logWarning "Updating release..."
[datetime]$startTime = date

# préparation de l'obfuscation
fixPatches "Server"
#fixCsvFiles
addDependances "Server"
restoreFixes

# obfuscation du code en utilisant les sources de debug
decompile "Server"

# import des sources de debug dans le répertoire de sources de MCP
importSources -destinationDirectory $directories.mcp.Output.SrcServer.directory

# réobfuscation du mod
recompile "Server"
#fixObfuscations "Server"
reobfuscate

# construction du package public
backup $files.mod.manifest
Add-Content $files.mod.manifest "Implementation-Version: $version"
delete $files.publicPackage
copyExcluding $directories.mod.debug.sources.directory $directories.mcp.REOBF.ReobfDirServer "*.java"
jar ("cfm " + $files.publicPackage + " " + $files.mod.manifest + " -C " + $directories.mcp.REOBF.ReobfDirServer + "\ .")
#%Cmd_JarSigner% %Package_Public% %__Package_Public_Temp% %Cmd_JarSigner_Args%
#CALL %Cmd_Delete% %__Package_Public_Temp%
restore $files.mod.manifest
replace $files.mod.release.archives.mod $files.publicPackage

# restauration de MCP
restoreMcp "Server"

# on ne build pas le mod si l'on est en train d'enchaîner les scripts
if (!$chain)
{
    buildMod
}

[datetime]$endTime = date
logWarning ("Done in " + (New-TimeSpan -Start $startTime -End $endTime))

# on ne met pas en pause le script si l'on est en train d'enchaîner les scripts
if (!$chain)
{
    sleep 5
}
