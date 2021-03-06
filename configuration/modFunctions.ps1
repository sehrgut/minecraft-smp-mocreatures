# build le mod
function buildMod()
{
    logInfo "Building the mod..."
    
    # build le mod
    build $directories.mod.directory
}

# importe des sources
function importSources([string]$importDirectory = $directories.mod.debug.sources.directory, [string]$destinationDirectory = $directories.mod.$target.sources.directory)
{
    logInfo "Updating $target sources..."
    
    # parcours des fichiers java du répertoire sources de debug 
    foreach ($_class in (dir $directories.mod.debug.sources.directory -Include @("*.java", "MANIFEST.MF") -Exclude ($files.mod.debug.fixes.sound.Split('\')[-1], $files.mod.debug.fixes.start.Split('\')[-1]) -Recurse))
    {
        replace $_class.FullName.Replace($directories.mod.debug.sources.directory, $destinationDirectory) $_class.FullName.Replace($directories.mod.debug.sources.directory, $importDirectory)
    }
}

# crée le script d'obfuscation du mod
function makeModObfuscationScript()
{
    # sauvegarde des CSV d'origine
    backup $files.mod.CSV.Classes
    backup $files.mod.CSV.Fields
    backup $files.mod.CSV.Methods

    # ajout de la ligne d'entête
    Set-Content $files.mod.CSV.Classes -Value @("`"name`",`"notch`",`"supername`",`"package`",`"side`"", (Get-Content $files.mod.CSV.Classes))
    Set-Content $files.mod.CSV.Fields -Value @("`"searge`",`"name`",`"notch`",`"sig`",`"notchsig`",`"classname`",`"classnotch`",`"package`",`"side`"", (Get-Content $files.mod.CSV.Fields))
    Set-Content $files.mod.CSV.Methods -Value @("`"searge`",`"name`",`"notch`",`"sig`",`"notchsig`",`"classname`",`"classnotch`",`"package`",`"side`"", (Get-Content $files.mod.CSV.Methods))

    # import des CSV
    $mapping += @{
        "mod" = @{
            "Classes" = Import-Csv $files.mod.CSV.Classes;
            "Fields" = Import-Csv $files.mod.CSV.Fields;
            "Methods" = Import-Csv $files.mod.CSV.Methods
        }
    }

    # ajout des classes, champs puis méthodes
    delete $files.mod.obfuscation.mod
    foreach ($_class in $mapping.mod.Classes)
    {
        Add-Content $files.mod.obfuscation.mod @("CL: " + $_class.notch + " " + $_class.package + "/" + $_class.name)
    }
    foreach ($_field in $mapping.mod.Fields)
    {
        Add-Content $files.mod.obfuscation.mod @("FD: " + $_field.classnotch + "/" + $_field.notch + " " + $_field.package + "/" + $_field.classname + "/" + $_field.name)
    }
    foreach ($_method in $mapping.mod.Methods)
    {
        Add-Content $files.mod.obfuscation.mod @("MD: " + $_method.classnotch + "/" + $_method.notch + " " + $_method.notchsig + " " + $_method.package + "/" + $_method.classname + "/" + $_method.name + " " + $_method.sig)
    }

    # restauration des CSV d'origine
    restore $files.mod.CSV.Classes
    restore $files.mod.CSV.Fields
    restore $files.mod.CSV.Methods
}

# corrige les fichiers CSV
function fixCsvFiles()
{
    logInfo "Fixing CSV files..."
    
    foreach ($_CSV in $files.mod.CSV.Keys)
    {
        # sauvegarde du fichier CSV vanilla
        backup $directories.mcp.CSV.$_CSV
        
        # ajout du fichier CSV du mod
        merge $files.mod.CSV.$_CSV $directories.mcp.CSV.$_CSV
    }
}

# corrige les patches
function fixPatches([string]$side)
{
    logInfo "Fixing $side patches..."

    $_patch = "Patch" + $side
    
    # correction du patch vanilla
    backup $directories.mcp.PATCHES.$_patch
    replace $directories.mcp.PATCHES.$_patch $files.mod.patches.$side
    
    # ajout du patch des dépendances
    merge $files.mod.patches.dependances $directories.mcp.PATCHES.$_patch
    
    # ajout du patch du mod
    merge $files.mod.patches.mod $directories.mcp.PATCHES.$_patch
}

# corrige les obfuscations
function fixObfuscations([string]$side)
{
    logInfo "Fixing obfuscations..."

    $_ObfSRG = "ObfSRG" + $side

    # sauvegarde de l'obfuscation vanilla
    backup $directories.mcp.REOBF.$_ObfSRG

    # ajout de l'obfuscation des dépendances
    merge $files.mod.obfuscation.dependances $directories.mcp.REOBF.$_ObfSRG

    # ajout de l'obfuscation du mod
    merge $files.mod.obfuscation.mod $directories.mcp.REOBF.$_ObfSRG
}

# ajout des dépendances
function addDependances([string]$side)
{
    logInfo "Adding $side dependances..."

    # dézippage du package des dépendances de release dans le répertoire temporaire
    zip ("x " + $files.mod.release.archives.dependances + " -o" + $directories.temp + " * -r")

	# ajout de son contenu dans le package vanilla
    backup $directories.mcp.JAR.$side
    zip ("a " + $directories.mcp.JAR.$side + " " + $directories.temp + "\* -r")

    # suppression du répertoire temporaire
    delete $directories.temp
}

# crée les packages de dépendance
function createDependancePackages([string]$side)
{
    [array]$_dependances = @(dir $directories.mod.configuration.dependances -Include ("*.zip", "*.jar") -Recurse)
    [string]$_package = "Pkg" + $side
    [string]$_source = "Src" + $side
    [string]$_binaries = "Bin" + $side
    if ($_dependances.Count -ne 0)
    {
        logInfo "Creating release dependances package..."
        # dézippage de tous les fichiers des packages de dépendance dans le répertoire temporaire
        foreach ($_dependance in $_dependances)
        {
            zip ("x " + $_dependance.fullname + " -o" + $directories.temp + " * -raoa")
        }
        backup $directories.mcp.JAR.$side
        zip ("a " + $directories.mcp.JAR.$side + " " + $directories.temp + "\* -r")

        # création du package des dépendances de release
        delete $files.mod.release.archives.dependances
        zip ("a " + $files.mod.release.archives.dependances + " " + $directories.temp + "\* -r")
        delete ($directories.temp + "\" + $directories.metaInf)

        logInfo "Creating core dependances package..."
        # création du package des dépendances de core
        [array]$_rootFiles = @(Get-ChildItem $directories.temp -exclude "*.class" | where { (!$_.PSIsContainer) })
        if ($_rootFiles.Count -ne 0)
        {
            $_tmpSrcDirectory = $directories.temp + "\" + $directories.mcp.PACKAGES.$_package.src
            mkdir $_tmpSrcDirectory | Out-Null
            foreach ($_file in $_rootFiles)
            {
                move $_file.fullname $_file.fullname.Replace($directories.temp, $_tmpSrcDirectory)
            }
        }
        zip ("a " + $files.mod.core.archives.dependances + " " + $directories.temp + "\* -r")
        zip ("d " + $files.mod.core.archives.dependances + " *.class")

        logInfo "Creating debug dependances package..."
        # création du package des dépendances de debug
        replace $files.mod.debug.archives.dependances $files.mod.core.archives.dependances
        fixPatches $side
        decompile $side
        $_tmpdirectory = $directories.temp + "\tmp"
        $_tmpSrcDirectory = $_tmpdirectory + "\" + $directories.mcp.PACKAGES.$_package.src
        mkdir $_tmpSrcDirectory | Out-Null
        logInfo "Updating debug dependances package..."
        foreach ($_class in (dir $directories.temp -Recurse -Include *.class))
        {
            if ((Split-Path -Parent $_class) -eq $directories.temp)
            {
                $_deobfuscatedClass = $mapping.mcp.classes | where { ($_.notch -eq $_class.BaseName) -AND $_.side -eq "1" }
                if ($_deobfuscatedClass -eq $null)
                {
                    $_className = $_class.BaseName
                }
                else
                {
                    $_className = $_deobfuscatedClass.name
                }
                replace ($_tmpSrcDirectory + "\" + $_className + ".java") ($directories.mcp.OUTPUT.$_source.src + "\" + $_className + ".java")
                replace ($_tmpSrcDirectory + "\" + $_className + ".class") ($directories.mcp.RECOMPILE.$_binaries.src + "\" + $_className + ".class")
            }
            else
            {
                replace $_class.FullName.Replace($directories.temp, $_tmpdirectory).Replace(".class", ".java") $_class.FullName.Replace($directories.temp, $directories.mcp.OUTPUT.$_source.directory).Replace(".class", ".java")
                replace $_class.FullName.Replace($directories.temp, $_tmpdirectory) $_class.FullName.Replace($directories.temp, $directories.mcp.RECOMPILE.$_binaries.directory)
            }
        }
        zip ("a " + $files.mod.debug.archives.dependances + " " + $_tmpdirectory + "\* -r")
        restoreMcp $side

        # suppression du répertoire temporaire
        delete $directories.temp   
    }
}

# mise à jour des fichiers CSV
function updateCsvFiles()
{
    logInfo "Updating Csv files..."
    [string]$_startClass = "_mms_caa_"
    [string]$_startField = "_mms_faa_"
    [string]$_startMethod = "_mms_maa_"
    [System.IO.FileInfo[]]$_files = (Get-ChildItem $directories.mod.debug.sources.directory -Recurse | where { ($_ -is [System.IO.FileInfo]) })
    [string]$_nullPkg = $directories.mcp.REOBF.NullPkg.Replace($directories.mcp.directory + "\", "").Replace("\", "/") + "/"
    [regex]$_importRegex = "\s*import\s(?<package>(\w+\.)*)(?<type>\w+);"
    [string]$_accessors = "((private)|(public)|(final)|(static)|(volatile)|(protected)|(synchronized))"
    [string]$_decorators = "(\<((\w+)|\?)\>)?(?<array>\[\])?"
    [array]$_importsTmp = @(
        @{ "package" = ""; "type" = "void" },
        @{ "package" = ""; "type" = "boolean" },
        @{ "package" = ""; "type" = "int" },
        @{ "package" = ""; "type" = "double" },
        @{ "package" = ""; "type" = "float" },
        @{ "package" = "java.lang."; "type" = "String" },
        @{ "package" = "java.lang."; "type" = "Thread" },
        @{ "package" = "java.lang."; "type" = "Class" },
        @{ "package" = "java.lang."; "type" = "Object" }
    )
    [hashtable]$_types = @{
        "void" = "V";
        "boolean" = "Z";
        "int" = "I";
        "double" = "D";
        "float" = "F"
    }

    delete $files.mod.CSV.Classes
    [string]$_classNotch = $_startClass
    foreach ($_file in $_files)
    {
        [string]$_contents = (Get-Content $_file.FullName)
        [string]$_className = $_file.BaseName
        if ($_contents -match "class $_className extends (\w+)")
        {
            [string]$_superName = $matches[1]
        }
        else
        {
            [string]$_superName = ""
        }
        [string]$_package = $_file.DirectoryName.Replace(($directories.mod.debug.sources.directory + "\"), "").Replace("\", "/")
        [string]$_side = "1"
        Add-Content $files.mod.CSV.Classes ("`"$_className`",`"$_classNotch`",`"$_superName`",`"$_package`",`"$_side`"")
        $_classNotch = (_increment $_classNotch)
    }
    backup $files.mod.CSV.Classes
    Set-Content $files.mod.CSV.Classes -Value @("`"name`",`"notch`",`"supername`",`"package`",`"side`"", (Get-Content $files.mod.CSV.Classes))
    $classes = (Import-Csv $files.mod.CSV.Classes)
    restore $files.mod.CSV.Classes
     
    delete $files.mod.CSV.Fields
    delete $files.mod.CSV.Methods
    foreach ($_file in $_files)
    {
        ($classes | where { $_.name -eq $_file.BaseName }) | foreach {
            [string]$_className = $_.name
            [string]$_classNotch = $_.notch
            [string]$_package = $_.package
            [string]$_side = $_.side
        }
        [string]$_contents = (Get-Content $_file.FullName)
        [array]$_imports = (_executeRegex $_importRegex $_contents) + $_importsTmp
        [string]$_possibleTypes = "("
        $_imports | %{ $_possibleTypes += "(" + $_.type + ")|" }
        $_files | %{ $_possibleTypes += "(" + $_.BaseName + ")|" }
        $_possibleTypes = $_possibleTypes.SubString(0, $_possibleTypes.Length -1) + ")"
        
        [regex]$_fieldRegex = "[^\t]\t($_accessors\s)*(?<fieldType>$_possibleTypes)$_decorators\s(?<fieldName>\w+)(;|\s=.+;)"
        [array]$_fields = (_executeRegex $_fieldRegex $_contents)
        if ($_fields -ne $null)
        {
            [string]$_fieldNotch = $_startField
            foreach ($_field in $_fields)
            {
                [string]$_fieldName = $_field.fieldName
                [hashtable]$types = _getTypes $_field.fieldType ($_field.array -ne "") $_imports
                [string]$_fieldType = $types.type
                [string]$_fieldTypeNotch = $types.typeNotch
                Add-Content $files.mod.CSV.Fields ("`"$_fieldName`",`"$_fieldName`",`"$_fieldNotch`",`"$_fieldType`",`"$_fieldTypeNotch`",`"$_className`",`"$_classNotch`",`"$_package`",`"$_side`"")
                $_fieldNotch = (_increment $_fieldNotch)
            }
        }
        
        [regex]$_methodRegex = "($_accessors\s)*(?<returnType>$_possibleTypes)$_decorators\s(?<methodName>\w+)\((?<arguments>[^\)]+)?\)"
        [regex]$_argumentsRegex = "(?<argumentType>$_possibleTypes)$_decorators\s\w+"
        [array]$_methods = (_executeRegex $_methodRegex $_contents)
        if ($_methods -ne $null)
        {
            [string]$_methodNotch = $_startMethod
            foreach ($_method in $_methods)
            {
                [string]$_methodName = $_method.methodName
                $_signature = "("
                $_signatureNotch = "("
                if ($_method.arguments -ne "")
                {
                    [array]$_arguments = (_executeRegex $_argumentsRegex $_method.arguments)
                    foreach ($_argument in $_arguments)
                    {
                        [hashtable]$types = _getTypes $_argument.argumentType ($_argument.array -ne "") $_imports
                        $_signature += $types.type
                        $_signatureNotch += $types.typeNotch
                    }
                }
                [hashtable]$types = _getTypes $_method.returnType ($_method.array -ne "") $_imports
                $_signature += ")" + $types.type
                $_signatureNotch += ")" + $types.typeNotch
                Add-Content $files.mod.CSV.Methods ("`"$_methodName`",`"$_methodName`",`"$_methodNotch`",`"$_signature`",`"$_signatureNotch`",`"$_className`",`"$_classNotch`",`"$_package`",`"$_side`"")
                $_methodNotch = (_increment $_methodNotch)
            }
        }
    }
}

##################################################################################################
#######################################      privates      #######################################
##################################################################################################

function _increment([string]$string, [int]$index = -1)
{
    if ($index -eq -1)
    {
        $index = $string.Length - 2
    }
   
    [int]$_value = [int]($string[$index])
    [int]$_newValue = ($_value + 1)

    if (($_value -ge 65) -and ($_value -le 90))
    {
        while ($_newValue -gt 90)
        {
            $_newValue -= 26
            $string = _increment $string ($index - 1)
        }
    }
    elseif ($_value -ge 97 -and $_value -le 122)
    {
        while ($_newValue -gt 122)
        {
            $_newValue -= 26
            $string = _increment $string ($index - 1)
        }
    }
    [array]$_letters = $string.ToCharArray()
    $_letters[$index] = ([char]($_newValue))
    New-Object String ($_letters, 0, $_letters.Count)
}

function _executeRegex([regex]$regex, [string]$text)
{
    [array]$_results = $null
    $regex.matches($text) | foreach { 
       $_match=$_
       [hashtable]$_result = @{}
       $regex.GetGroupNames() | where {$_ -notmatch '^\d+$'} | foreach {
           $_result.$_ = $_match.groups[$regex.GroupNumberFromName($_)].value
       }
       $_results += $_result
    }
    $_results
}
 
function _getTypes([string]$type, [boolean]$isArray, [array]$imports)
{
    [hashtable]$_results = @{}
    if ($isArray)
    {
        $_results.type = "["
        $_results.typeNotch = "["
    }
    if ($_types.Contains($type))
    {
        $_results.type += $_types.$type
        $_results.typeNotch += $_results.type
    }
    else
    {
        $_mcpClass = ($mapping.mcp.Classes | where { ($_.name -eq $type) -and ($_.side -eq $_side) })
        if ($_mcpClass -ne $null)
        {
            [string]$_typePath = $_mcpClass.package + "/"
            [string]$typeNotch = $_mcpClass.notch
        }
        elseif (([hashtable]$_import = ($imports | where { $_.type -eq $type })) -ne $null)
        {
            [string]$_typePath = $_import.package.Replace(".", "/")
            [string]$typeNotch = $type
        }
        else
        {
            $_class = ($classes | where { $_.name -eq $type })
            [string]$_typePath = $_class.package + "/"
            [string]$typeNotch = $_class.notch
        }
        $_results.type += "L$_typePath$type;"
        if ($_typePath -eq $_nullPkg)
        {
            $_typePath = ""
        }
        $_results.typeNotch += "L$_typePath$typeNotch;"
    }
    $_results
}