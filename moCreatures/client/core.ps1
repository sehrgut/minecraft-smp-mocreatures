param
(
    [bool]$chain = $false
)

# configuration
. .\configuration\setup.ps1 "core"

logWarning "Updating core..."
[datetime]$startTime = date

# préparation
fixPatches "Client"
addDependances "Client"
restoreFixes
decompile "Client"

logInfo "Importing vanilla packages..."
# récupération des packages décompilés
foreach ($_package in $directories.vanilla.src.Client.packages.Keys)
{
    replace $directories.mod.core.sources.packages.$_package $directories.mcp.OUTPUT.SrcClient.$_package
}

# import des sources de debug
importSources

# restauration de MCP
restoreMcp "Client"

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
