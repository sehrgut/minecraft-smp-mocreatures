package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.minecraft.server.MinecraftServer;

public final class ModLoader
{
	public static final boolean DEBUG = false;
	private static Field modifiers = null;
	private static int highestEntityId = 100;
	private static int itemSpriteIndex = 0;
	private static final Logger logger = Logger.getLogger("ModLoader");
	private static FileHandler logHandler = null;
	private static Method method_RegisterEntityID = null;
	private static Method method_RegisterTileEntity = null;
	private static final LinkedList<BaseMod> modList = new LinkedList<BaseMod>();
	private static final Map<Integer, Map<String, Integer>> overrides = new HashMap<Integer, Map<String, Integer>>();
	private static int terrainSpriteIndex = 0;
	private static final boolean[] usedItemSprites = new boolean[256];
	private static final boolean[] usedTerrainSprites = new boolean[256];
	public static final String VERSION = "v1.0.0 (MC 1.3_01)";

	private ModLoader()
	{
	}

	public static void addAllRecipes(CraftingManager recipes)
	{
		logger.finer("Initializing recipes");
		for (BaseMod mod : modList)
			mod.addRecipes(recipes);
	}

	private static void addMod(ClassLoader loader, String fileName)
	{
		try
		{
			String name = fileName.split("\\.")[0];
			if(name.contains("$"))
				return;
			Package pack = ModLoader.class.getPackage();
			if(pack != null)
				name = new StringBuilder(pack.getName()).append(".").append(name).toString();
			Class<?> instClass = loader.loadClass(name);
			if(instClass.isAssignableFrom(BaseMod.class))
				return;
			BaseMod basemod = (BaseMod)instClass.newInstance();
			if(basemod != null)
			{
				modList.add(basemod);
				logger.fine((new StringBuilder("Mod Loaded: \"")).append(basemod.toString()).append("\" from ").append(fileName).toString());
				System.out.println("Mod Loaded: " + basemod.toString());
			}
		}
		catch(Throwable e)
		{
			logger.fine((new StringBuilder("Failed to load mod from \"")).append(fileName).append("\"").toString());
			System.out.println((new StringBuilder("Failed to load mod from \"")).append(fileName).append("\"").toString());
			logger.throwing("ModLoader", "addMod", e);
			throwException(e);
		}
	}

	public static int addOverride(String fileToOverride, String fileToAdd)
	{
		try
		{
			int i = getUniqueSpriteIndex(fileToOverride);
			addOverride(fileToOverride, fileToAdd, i);
			return i;
		}
		catch(Throwable e)
		{
			logger.throwing("ModLoader", "addOverride", e);
			throwException(e);
			throw new RuntimeException(e);
		}
	}

	public static void addOverride(String path, String overlayPath, int index)
	{
		int dst = -1;
		if(path.equals("/terrain.png"))
			dst = 0;
		else if(path.equals("/gui/items.png"))
			dst = 1;
		else
			return;
		logger.finer((new StringBuilder("addOverride(")).append(path).append(",").append(overlayPath).append(",").append(index).append(")").toString());
		Map<String, Integer> overlays = overrides.get(Integer.valueOf(dst));
		if(overlays == null)
		{
			overlays = new HashMap<String, Integer>();
			overrides.put(Integer.valueOf(dst), overlays);
		}
		overlays.put(overlayPath, Integer.valueOf(index));
	}

	public static Logger getLogger()
	{
		return logger;
	}

	public static int getUniqueEntityId()
	{
		return highestEntityId++;
	}

	private static int getUniqueItemSpriteIndex()
	{
		for(; itemSpriteIndex < usedItemSprites.length; itemSpriteIndex++)
		{
			if(!usedItemSprites[itemSpriteIndex])
			{
				usedItemSprites[itemSpriteIndex] = true;
				return itemSpriteIndex++;
			}
		}

		Exception e = new Exception("No more empty item sprite indices left!");
		logger.throwing("ModLoader", "getUniqueItemSpriteIndex", e);
		throwException(e);
		return 0;
	}

	public static int getUniqueSpriteIndex(String path)
	{
		if(path.equals("/gui/items.png"))
			return getUniqueItemSpriteIndex();
		if(path.equals("/terrain.png"))
			return getUniqueTerrainSpriteIndex();
		Exception e = new Exception((new StringBuilder("No registry for this texture: ")).append(path).toString());
		logger.throwing("ModLoader", "getUniqueItemSpriteIndex", e);
		throwException(e);
		return 0;
	}

	private static int getUniqueTerrainSpriteIndex()
	{
		for(; terrainSpriteIndex < usedTerrainSprites.length; terrainSpriteIndex++)
		{
			if(!usedTerrainSprites[terrainSpriteIndex])
			{
				usedTerrainSprites[terrainSpriteIndex] = true;
				return terrainSpriteIndex++;
			}
		}

		Exception e = new Exception("No more empty terrain sprite indices left!");
		logger.throwing("ModLoader", "getUniqueItemSpriteIndex", e);
		throwException(e);
		return 0;
	}

	public static void initialize()
	{
		String usedItemSpritesString = "1111111111111111111111111111111111111101111111011111111111110001111111111111111111111011111000111111100110000011111110000000001111111001100000110000000100000011000000010000001100000000000000110000000000000000000000000000000000000000000000001100000000000000";
		String usedTerrainSpritesString = "1111111111111111111111111111110111111100011111111111110001111110111111111111000011110011111111111111001111000000111111111111100011111111000010001111011110000000111011000000000011100000000000001110000000000111111000000000001101000000000001111111111111000011";
		for(int i = 0; i < 256; i++)
		{
			usedItemSprites[i] = usedItemSpritesString.charAt(i) == '1';
			usedTerrainSprites[i] = usedTerrainSpritesString.charAt(i) == '1';
		}

		try
		{
			modifiers = Field.class.getDeclaredField("modifiers");
			modifiers.setAccessible(true);
			try
			{
				method_RegisterTileEntity = TileEntity.class.getDeclaredMethod("a", new Class[] { Class.class, String.class });
			}
			catch(NoSuchMethodException e)
			{
				method_RegisterTileEntity = TileEntity.class.getDeclaredMethod("addMapping", new Class[] { Class.class, String.class });
			}
			method_RegisterTileEntity.setAccessible(true);
			try
			{
				method_RegisterEntityID = (EntityList.class).getDeclaredMethod("a", new Class[] { Class.class, String.class, Integer.TYPE });
			}
			catch(NoSuchMethodException e)
			{
				method_RegisterEntityID = (EntityList.class).getDeclaredMethod("addMapping", new Class[] { Class.class, String.class, Integer.TYPE });
			}
			method_RegisterEntityID.setAccessible(true);
		}
		catch(SecurityException e)
		{
			logger.throwing("ModLoader", "init", e);
			throwException(e);
			throw new RuntimeException(e);
		}
		catch(NoSuchFieldException e)
		{
			logger.throwing("ModLoader", "init", e);
			throwException(e);
			throw new RuntimeException(e);
		}
		catch(NoSuchMethodException e)
		{
			logger.throwing("ModLoader", "init", e);
			throwException(e);
			throw new RuntimeException(e);
		}
		catch(IllegalArgumentException e)
		{
			logger.throwing("ModLoader", "init", e);
			throwException(e);
			throw new RuntimeException(e);
		}
		try
		{
			MinecraftServer.logger.fine("ModLoader Initializing...");
			System.out.println("ModLoader Initializing...");
			File file1 = new File((ModLoader.class).getProtectionDomain().getCodeSource().getLocation().toURI());
			readFromClassPath(file1);
			System.out.println("ModLoader Initialized.");
			addAllRecipes(CraftingManager.getInstance());
		}
		catch(Throwable e)
		{
			logger.throwing("ModLoader", "init", e);
			throwException("ModLoader has failed to initialize.", e);
			if(logHandler != null)
				logHandler.close();
			throw new RuntimeException(e);
		}
	}

	private static void readFromClassPath(File file)
	throws FileNotFoundException, IOException
	{
		logger.finer((new StringBuilder("Adding mods from ")).append(file.getCanonicalPath()).toString());
		ClassLoader classloader = (ModLoader.class).getClassLoader();
		if(file.isFile() && (file.getName().endsWith(".jar") || file.getName().endsWith(".zip")))
		{
			logger.finer("Zip found.");
			FileInputStream fileinputstream = new FileInputStream(file);
			ZipInputStream zipinputstream = new ZipInputStream(fileinputstream);
			do
			{
				ZipEntry zipentry = zipinputstream.getNextEntry();
				if(zipentry == null)
					break;
				String s1 = zipentry.getName();
				if(!zipentry.isDirectory() && s1.startsWith("mod_") && s1.endsWith(".class"))
					addMod(classloader, s1);
			} while(true);
			fileinputstream.close();
		} else if(file.isDirectory())
		{
			Package package1 = (ModLoader.class).getPackage();
			if(package1 != null)
			{
				String s = package1.getName().replace('.', File.separatorChar);
				file = new File(file, s);
			}
			logger.finer("Directory found.");
			File afile[] = file.listFiles();
			if(afile != null)
			{
				for(int i = 0; i < afile.length; i++)
				{
					String s2 = afile[i].getName();
					if(afile[i].isFile() && s2.startsWith("mod_") && s2.endsWith(".class"))
						addMod(classloader, s2);
				}

			}
		}
	}

	public static void zzz(Class<? extends Entity> entityClass, String entityName, int id)
	{
		try
		{
			method_RegisterEntityID.invoke(null, new Object[] { entityClass, entityName, Integer.valueOf(id) });
		}
		catch(IllegalArgumentException illegalargumentexception)
		{
			logger.throwing("ModLoader", "RegisterEntityID", illegalargumentexception);
			throwException(illegalargumentexception);
		}
		catch(IllegalAccessException illegalaccessexception)
		{
			logger.throwing("ModLoader", "RegisterEntityID", illegalaccessexception);
			throwException(illegalaccessexception);
		}
		catch(InvocationTargetException invocationtargetexception)
		{
			logger.throwing("ModLoader", "RegisterEntityID", invocationtargetexception);
			throwException(invocationtargetexception);
		}
	}

	public static <T, E> void zzy(Class<? super T> instanceClass, T instance, String field, E value)
	throws IllegalArgumentException, SecurityException, NoSuchFieldException
	{
		try
		{
			Field f = instanceClass.getDeclaredField(field);
			int i = modifiers.getInt(f);
			if((i & 0x10) != 0)
			{
				modifiers.setInt(f, i & 0xffffffef);
			}
			f.setAccessible(true);
			f.set(instance, value);
		}
		catch(IllegalAccessException illegalaccessexception)
		{
			logger.throwing("ModLoader", "setPrivateValue", illegalaccessexception);
			throwException("An impossible error has occured!", illegalaccessexception);
		}
	}

	public static void throwException(String s, Throwable throwable)
	{
		MinecraftServer.logger.warning(s);
	}

	private static void throwException(Throwable throwable)
	{
		throwException("Exception occured in ModLoader", throwable);
	}
}
