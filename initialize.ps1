# configuration
. .\configuration\globalSetup.ps1 "initialize"

logWarning "Global initialization..."
[datetime]$startTime = date

# décompilation de minecraft vanilla
cleanUp
decompile

logInfo "Updating vanilla packages..."
# parcours de tous les environnements
foreach ($_target in $targets)
{
    # import des packages vanilla
    foreach ($_directory in $directories.vanilla.jar.$_target.Keys)
    {
        if ($_directory -ne "directory")
        {   
            replace $directories.vanilla.jar.$_target.$_directory $directories.mcp.JAR.$_directory
        }
    }
}

# script exécuté côté client et serveur
foreach ($_side in ("Client", "Server"))
{
    logInfo "Updating vanilla $_side files..."
    # import des sources et des binaires
    replace $directories.vanilla.src.$_side.directory $directories.mcp.OUTPUT.("Src" + $_side).directory
    replace $directories.vanilla.bin.$_side.directory $directories.mcp.RECOMPILE.("Bin" + $_side).directory
    
    # parcours de tous les environnements
    foreach ($_target in $targets)
    {
        # aucune action pour la release
        if ($_target -ne "release")
        {
            # suppression du meta-inf et des classes à la racine
            $_targetjar = $directories.vanilla.jar.$_target.$_side
            zip ("d " + $_targetjar + " " + $directories.metaInf)
            zip ("d " + $_targetjar + " *.class")
            
            # suppression des packages vanilla
            foreach ($_subDirectory in $directories.mcp.PACKAGES.("Pkg" + $_side).Values)
            {
                zip ("d " + $_targetjar + " " + $_subDirectory)
            }
            
            # en debug, ajout des packages mcp (sources et compilés)
            if ($_target -eq "debug")
            {
                zip ("a " + $_targetjar + " " + $directories.vanilla.src.$_side.directory + "\*")
                zip ("a " + $_targetjar + " " + $directories.vanilla.bin.$_side.directory + "\*")
                zip ("d " + $_targetjar + " *.class")
            }
        }
    }
}

# nettoyage
cleanUp

[datetime]$endTime = date
logWarning ("Done in " + (New-TimeSpan -Start $startTime -End $endTime))

sleep 5