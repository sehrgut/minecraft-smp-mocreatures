param
(
    [string]$_target = "error"
)

if ($_target -eq "error")
{
    throw "You must specify the target"
}

Write-Host -BackgroundColor Black -ForegroundColor Yellow "== Global configuration... =="
[datetime]$startTime = date

# répertoires
[array]$targets = ("core", "debug", "release")
[string]$target = $_target
[hashtable]$directories = @{ "globalConfiguration" = "D:\Minecraft\Workspaces\configuration" }
pushd $directories.globalConfiguration
pushd ..
$directories.workspaces = (gl).path
pushd mcp
[string]$_mcpDirectory = (gl).path
popd
popd
. .\globalFunctions.ps1
. .\modFunctions.ps1
popd

# répertoires de base
$directories += @{
    "mcp" = importMcpConf "$_mcpDirectory\conf\mcp.cfg" $_mcpDirectory;
    "packages" = $directories.workspaces + "\packages";
    "temp" = $directories.workspaces + "\temp";
    "metaInf" = "META-INF";
    "vanilla" = @{ "directory" = $directories.globalConfiguration + "\vanilla" };
    "mod" = @{ "directory" = ($pwd).path }
}

# répertoires mcp
$directories.mcp += @{
    "directory" = $_mcpDirectory;
    "fixes" = @{ "directory" = $directories.mcp.RECOMPILE.ClientFixes }
}
$directories.mcp.fixes += @{
    "sound" = $directories.mcp.fixes.directory + "\" + $directories.mcp.REOBF.FixSound.Split('\')[-1].Replace(".class", ".java");
    "start" = $directories.mcp.fixes.directory + "\" + $directories.mcp.REOBF.FixStart.Split('\')[-1].Replace(".class", ".java")
}
$directories.mcp.JAR.resources = $directories.mcp.DEFAULT.DirJars + "\resources"
foreach ($_directory in $directories.mcp.RECOMPILE.ClassPathClient.Split(','))
{
    if (!$_directory.Contains('%') -and !$directories.mcp.JAR.ContainsValue($_directory))
    {
        $directories.mcp.JAR.((gi $_directory).BaseName) = $_directory
    }
}
foreach ($_type in ("Client", "Server"))
{
    [array]$_packages = $directories.mcp.PACKAGES.("Pkg" + $_type).Split(',')
    [string]$_pkgType = "Pkg" + $_type
    [string]$_srcType = "Src" + $_type
    [string]$_binType = "Bin" + $_type
    $directories.mcp.PACKAGES.$_pkgType = @{}
    $directories.mcp.OUTPUT.$_srcType = @{ "directory" = $directories.mcp.OUTPUT.$_srcType }
    $directories.mcp.RECOMPILE.$_binType = @{ "directory" = $directories.mcp.RECOMPILE.$_binType }
    foreach ($_package in $_packages)
    {
        [string]$_packageName = $_package.Split('\')[-1]
        $directories.mcp.PACKAGES.$_pkgType.$_packageName = $_package
        $directories.mcp.OUTPUT.$_srcType.$_packageName = $directories.mcp.OUTPUT.$_srcType.directory + "\$_package"
        $directories.mcp.RECOMPILE.$_binType.$_packageName = $directories.mcp.RECOMPILE.$_binType.directory + "\$_package"
    }
}

# répertoires vanilla
$directories.vanilla += @{
    "bin" = @{
        "directory" = $directories.mcp.DEFAULT.DirBin.Replace($_mcpDirectory, $directories.vanilla.directory)
    };
    "src" = @{
        "directory" = $directories.mcp.DEFAULT.DirSrc.Replace($_mcpDirectory, $directories.vanilla.directory)
    };
    "jar" = @{ "directory" = $directories.mcp.DEFAULT.DirJars.Replace($_mcpDirectory, $directories.vanilla.directory) }
}
foreach ($_type in ("Client", "Server"))
{
    [string]$_pkgType = "Pkg" + $_type
    $directories.vanilla.bin += @{
        $_type = @{
            "directory" = $directories.mcp.RECOMPILE.("Bin" + $_type).directory.Replace($_mcpDirectory, $directories.vanilla.directory);
            "packages" = @{}
        }
    }
    $directories.vanilla.src += @{
        $_type = @{
            "directory" = $directories.mcp.OUTPUT.("Src" + $_type).directory.Replace($_mcpDirectory, $directories.vanilla.directory);
            "packages" = @{}
        }
    }
    foreach ($_package in $directories.mcp.PACKAGES.$_pkgType.Keys)
    {
        $directories.vanilla.bin.$_type.packages.$_package = $directories.vanilla.bin.$_type.directory + "\" + $directories.mcp.PACKAGES.$_pkgType.$_package
        $directories.vanilla.src.$_type.packages.$_package = $directories.vanilla.src.$_type.directory + "\" + $directories.mcp.PACKAGES.$_pkgType.$_package
    }
}
foreach ($_target in $targets)
{
    $directories.vanilla.jar.$_target = @{ "directory" = $directories.vanilla.jar.directory + "\$_target" }
    foreach ($_key in $directories.mcp.JAR.Keys)
    {
        if (!$_key.Contains("MD5"))
        {
            $directories.vanilla.jar.$_target.$_key = $directories.mcp.JAR.$_key.Replace($directories.mcp.DEFAULT.DirJars, $directories.vanilla.jar.$_target.directory)
        }
    }
}

# répertoires du mod
$directories.mod.target = $directories.mod.directory + "\$target"
$directories.mod.configuration = @{ "directory" = $directories.mod.directory + "\configuration" }
$directories.mod.configuration.dependances = $directories.mod.configuration.directory + "\dependances"
foreach ($_target in $targets)
{
    $directories.mod.$_target = @{ "directory" = $directories.mod.directory + "\$_target" }
    $directories.mod.$_target += @{
        "output" = @{ "directory" = $directories.mod.$_target.directory + "\output" };
        "sources" = @{
            "directory" = $directories.mod.$_target.directory + "\sources";
            "packages" = @{}
        }
    }
    foreach ($_archive in $directories.vanilla.jar.$_target.Keys)
    {
        if ($_archive -ne "directory")
        {
            $directories.mod.$_target.output += @{ $_archive = $directories.vanilla.jar.$_target.$_archive.Replace($directories.vanilla.jar.$_target.directory, $directories.mod.$_target.output.directory) }
        }
    }
}

# fichiers
[hashtable]$files = @{
    "privateKey" = $directories.globalConfiguration + "\privateKey";
    "logo" = $directories.globalConfiguration + "\logo.ico"
    "mod" = @{
        "patches" = @{
            "Client" = $directories.mcp.PATCHES.PatchClient.Replace($directories.mcp.fixes.directory, $directories.mod.configuration.directory);
            "Server" = $directories.mcp.PATCHES.PatchServer.Replace($directories.mcp.fixes.directory, $directories.mod.configuration.directory);
            "dependances" = $directories.mod.configuration.directory + "\dependances.patch";
            "mod" = $directories.mod.configuration.directory + "\mod.patch"
        };
        "CSV" = @{
            "Classes" = $directories.mcp.CSV.Classes.Replace($directories.mcp.DEFAULT.DirConf, $directories.mod.configuration.directory);
            "Fields" = $directories.mcp.CSV.Fields.Replace($directories.mcp.DEFAULT.DirConf, $directories.mod.configuration.directory);
            "Methods" = $directories.mcp.CSV.Methods.Replace($directories.mcp.DEFAULT.DirConf, $directories.mod.configuration.directory)
        };
        "obfuscation" = @{
            "dependances" = $directories.mod.configuration.directory + "\dependances.srg";
            "mod" = $directories.mod.configuration.directory + "\mod.srg"
        };
        "version" = $directories.mod.configuration.directory + "\Version";
        "manifest" = $directories.mod.configuration.directory + "\MANIFEST.MF";
    }
}
foreach ($_target in $targets)
{
    $files.mod.$_target = @{
        "fixes" = @{
            "sound" = $directories.mcp.fixes.sound.Replace($directories.mcp.fixes.directory, $directories.mod.$_target.sources.directory);
            "start" = $directories.mcp.fixes.start.Replace($directories.mcp.fixes.directory, $directories.mod.$_target.sources.directory)
        }
    }
}

# variables
[hashtable]$mapping = @{
    "mcp" = @{
        "Classes" = Import-Csv $directories.mcp.CSV.Classes;
        "Fields" = Import-Csv $directories.mcp.CSV.Fields;
        "Methods" = Import-Csv $directories.mcp.CSV.Methods
    }
}    

[datetime]$endTime = date
logWarning ("Done in " + (New-TimeSpan -Start $startTime -End $endTime))