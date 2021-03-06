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
createDependancePackages "Client"

# fix de GuiAPI...
foreach ($_dependance in @(dir $directories.mod.configuration.dependances -Include ("*.zip", "*.jar") -Recurse))
{
    if ($_dependance.fullname -like "*GuiAPI*")
    {
        zip ("x " + $_dependance.fullname + " -o" + $directories.temp + " * -r")
        mkdir ($directories.temp + "\" + $directories.mcp.PACKAGES.PkgClient.src + "\gui") | Out-Null
        copy ($directories.temp + "\gui\gui.png") ($directories.temp + "\" + $directories.mcp.PACKAGES.PkgClient.src + "\gui\gui.png")
        copy ($directories.temp + "\font") ($directories.temp + "\" + $directories.mcp.PACKAGES.PkgClient.src + "\font") -Recurse
        foreach ($_target in $targets)
        {
            if ($_target -ne "release")
            {
                zip ("a " + $files.mod.$_target.archives.dependances + " " + $directories.temp + "\net -r")
            }
        }
        delete $directories.temp
    }
}

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
    
    # mise à jour du répertoire des mobs
    if ($_target -ne "release")
    {
        replace $directories.mod.$_target.sources.textures $directories.mod.configuration.resources.textures
    }
    
    # mise à jour des archives vanilla clientes
    foreach ($_archive in $directories.vanilla.jar.$_target.Keys)
    {
        if ($_archive -ne "directory" -AND $_archive -ne "Server")
        {
            replace $directories.mod.$_target.output.$_archive $directories.vanilla.jar.$_target.$_archive
        }
    }
    
    #mise à jour du répertoire des ressources
    merge $directories.mod.configuration.resources.resources $directories.mod.$_target.output.resources
}

# suppression du répertoire META-INF dans l'archive du client
#zip ("d " + $directories.mod.release.output.Client + " " + $directories.metaInf)

# création des scripts d'obfuscation
makeModObfuscationScript

[datetime]$endTime = date
logWarning ("Done in " + (New-TimeSpan -Start $startTime -End $endTime))

# on ne met pas en pause le script si l'on est en train d'enchaîner les scripts
if (!$chain)
{
    sleep 5
}
