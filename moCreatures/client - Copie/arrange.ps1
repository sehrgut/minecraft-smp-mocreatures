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

logInfo "Creating release dependances package..."
# dézippage de tous les fichiers des packages de dépendance dans le répertoire temporaire
foreach ($_dependance in (dir $directories.mod.configuration.dependances -Include @("*.zip", "*.jar") -Recurse))
{
    zip ("x " + $_dependance.fullname + " -o" + $directories.temp + " * -raoa")
}
backup $directories.mcp.JAR.Client
zip ("a " + $directories.mcp.JAR.Client + " " + $directories.temp + "\* -r")

# création du package des dépendances de release
delete $files.mod.release.archives.dependances
zip ("x " + $directories.vanilla.jar.release.Client + " -o" + $directories.temp + " font")
zip ("x " + $directories.vanilla.jar.release.Client + " -o" + $directories.temp + " gui\gui.png")
zip ("a " + $files.mod.release.archives.dependances + " " + $directories.temp + "\* -r")

logInfo "Creating core dependances package..."
# création du package des dépendances de core
$_tmpSrcDirectory = $directories.temp + "\" + $directories.mcp.PACKAGES.PkgClient.src
mkdir $_tmpSrcDirectory | Out-Null
move ($directories.temp + "\font") $_tmpSrcDirectory
move ($directories.temp + "\gui") $_tmpSrcDirectory
Get-ChildItem $directories.temp -exclude "*.class" | where { (!$_.PSIsContainer) } | foreach($_) { move $_.fullname $_.fullname.Replace($directories.temp, $_tmpSrcDirectory) }
zip ("a " + $files.mod.core.archives.dependances + " " + $directories.temp + "\* -r")
zip ("d " + $files.mod.core.archives.dependances + " *.class")

logInfo "Creating debug dependances package..."
# création du package des dépendances de debug
fixPatches "Client"
decompile "Client"
replace $files.mod.debug.archives.dependances $files.mod.core.archives.dependances
$_tmpdirectory = $directories.temp + "\tmp"
$_tmpSrcDirectory = $_tmpdirectory + "\" + $directories.mcp.PACKAGES.PkgClient.src
mkdir $_tmpSrcDirectory | Out-Null
logInfo "Updating debug dependances package..."
foreach ($_class in (dir ($directories.temp + "\*.class")))
{
    $_deobfuscatedClass = $mapping.mcp.classes | where { ($_.notch -eq $_class.BaseName) -AND $_.side -eq "0" }
    if ($_deobfuscatedClass -eq $null)
    {
        $_className = $_class.BaseName
    }
    else
    {
        $_className = $_deobfuscatedClass.name
    }
    move ($directories.mcp.OUTPUT.SrcClient.src + "\" + $_className + ".java") ($_tmpSrcDirectory + "\" + $_className + ".java")
    move ($directories.mcp.RECOMPILE.BinClient.src + "\" + $_className + ".class") ($_tmpSrcDirectory + "\" + $_className + ".class")
}
zip ("a " + $files.mod.debug.archives.dependances + " " + $_tmpdirectory + "\* -r")
restoreMcp "Client"

# suppression du répertoire temporaire
delete $directories.temp

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
zip ("d " + $directories.mod.release.output.Client + " " + $directories.metaInf)

# création des scripts d'obfuscation
makeModObfuscationScript

[datetime]$endTime = date
logWarning ("Done in " + (New-TimeSpan -Start $startTime -End $endTime))

# on ne met pas en pause le script si l'on est en train d'enchaîner les scripts
if (!$chain)
{
    sleep 5
}
