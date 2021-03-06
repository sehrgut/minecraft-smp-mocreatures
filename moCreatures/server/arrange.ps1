param
(
    [bool]$chain = $false
)

# configuration
. .\configuration\setup.ps1 "arrange"

logWarning "Local initialization..."
[datetime]$startTime = date

logInfo "Cleaning output folders..."
# suppression des répertoires de sortie
foreach ($_target in $targets)
{
    delete $directories.mod.$_target.output.directory
}

# création des packages de dépendance
createDependancePackages "Server"

logInfo "Updating development environements..."

# parcours des environnements
foreach ($_target in $targets)
{
    # suppression totale du répertoire des sources
    if ($_target -ne "debug")
    {
        delete $directories.mod.$_target.sources.directory
        mkdir $directories.mod.$_target.sources.directory | Out-Null
    }
    
    # mise à jour des archives vanilla server
    replace $directories.mod.$_target.output.Server $directories.vanilla.jar.$_target.Server
}

[datetime]$endTime = date
logWarning ("Done in " + (New-TimeSpan -Start $startTime -End $endTime))
