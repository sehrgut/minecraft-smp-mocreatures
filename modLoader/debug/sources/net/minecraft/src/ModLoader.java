package net.minecraft.src;

import net.minecraft.src.BaseMod;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public final class ModLoader
{
	private static final List<TextureFX> animList = new LinkedList<TextureFX>();
	private static final Map<Integer, BaseMod> blockModels = new HashMap<Integer, BaseMod>();
	private static final Map<Integer, Boolean> blockSpecialInv = new HashMap<Integer, Boolean>();
	private static final File cfgdir = new File(Minecraft.getMinecraftDir(), "/config/");
	private static final File cfgfile = new File(cfgdir, "ModLoader.cfg");
	public static Level cfgLoggingLevel = Level.FINER;
	private static Map<String, Class<? extends Entity>> classMap = null;
	private static long clock = 0L;
	public static final boolean DEBUG = false;
	private static Field field_animList = null;
	private static Field field_armorList = null;
	private static Field field_blockList = null;
	private static Field field_modifiers = null;
	private static Field field_TileEntityRenderers = null;
	private static boolean hasInit = false;
	private static int highestEntityId = 3000;
	private static final Map<BaseMod, Boolean> inGameHooks = new HashMap<BaseMod, Boolean>();
	private static final Map<BaseMod, Boolean> inGUIHooks = new HashMap<BaseMod, Boolean>();
	private static Minecraft instance = null;
	private static int itemSpriteIndex = 0;
	private static int itemSpritesLeft = 0;
	private static final Map<BaseMod, Map<KeyBinding, boolean[]>> keyList = new HashMap<BaseMod, Map<KeyBinding, boolean[]>>();
	private static final File logfile = new File(Minecraft.getMinecraftDir(), "ModLoader.txt");
	private static final File modDir = new File(Minecraft.getMinecraftDir(), "/mods/");
	private static final Logger logger = Logger.getLogger("ModLoader");
	private static FileHandler logHandler = null;
	private static Method method_RegisterEntityID = null;
	private static Method method_RegisterTileEntity = null;
	private static final Map<String, BaseMod> modList = new HashMap<String, BaseMod>();
	private static int nextBlockModelID = 1000;
	private static final Map<Integer, Map<String, Integer>> overrides = new HashMap<Integer, Map<String, Integer>>();
	public static final Properties props = new Properties();
	private static BiomeGenBase[] standardBiomes;
	private static int terrainSpriteIndex = 0;
	private static int terrainSpritesLeft = 0;
	private static String texPack = null;
	private static boolean texturesAdded = false;
	private static final boolean[] usedItemSprites = new boolean[256];
	private static final boolean[] usedTerrainSprites = new boolean[256];
	public static final String VERSION = "ModLoader Beta 1.6.6";

	public ModLoader()
	{
	}

	public static void AddAchievementDesc(Achievement achievement, String name, String description)
	{
		try
		{
			if(achievement.statName.contains("."))
			{
				String key = achievement.statName.split("\\.")[1];
				AddLocalization("achievement." + key, name);
				AddLocalization((new StringBuilder("achievement.")).append(key).append(".desc").toString(), description);
				setPrivateValue(StatBase.class, achievement, 1, StatCollector.translateToLocal("achievement." + key));
				setPrivateValue(Achievement.class, achievement, 3, StatCollector.translateToLocal((new StringBuilder("achievement.")).append(key).append(".desc").toString()));
			} else
			{
				setPrivateValue(StatBase.class, achievement, 1, name);
				setPrivateValue(Achievement.class, achievement, 3, description);
			}
		}
		catch(IllegalArgumentException e)
		{
			logger.throwing("ModLoader", "AddAchievementDesc", e);
			ThrowException(e);
		}
		catch(SecurityException e)
		{
			logger.throwing("ModLoader", "AddAchievementDesc", e);
			ThrowException(e);
		}
		catch(NoSuchFieldException e)
		{
			logger.throwing("ModLoader", "AddAchievementDesc", e);
			ThrowException(e);
		}
	}

	public static int AddAllFuel(int id)
	{
		logger.finest("Finding fuel for " + id);
		int result = 0;
		for (BaseMod mod : modList.values())
		{
			if ((result = mod.AddFuel(id)) == 0)
			{
				logger.finest("Returned " + result);
				break;
			}
		}
		return result;
	}

	public static void AddAllRenderers(Map<Class<? extends Entity>, Render> o)
	{
		if(!hasInit)
		{
			init();
			logger.fine("Initialized");
		}

		for (BaseMod mod : modList.values())
			mod.AddRenderer(o);
	}

	public static void addAnimation(TextureFX anim)
	{
		logger.finest("Adding animation " + anim.toString());
		for (TextureFX oldAnim : animList)
		{
			if (oldAnim.iconIndex == anim.iconIndex)
			{
				animList.remove(anim);
				break;
			}
		}
		animList.add(anim);
	}

	public static int AddArmor(String armor)
	{
		try
		{
			String[] existingArmor = (String[])field_armorList.get(null);
			List<String> existingArmorList = Arrays.asList(existingArmor);
			List<String> combinedList = new ArrayList<String>();
			combinedList.addAll(existingArmorList);
			if (!combinedList.contains(armor))
				combinedList.add(armor);
			int index = combinedList.indexOf(armor);
			field_armorList.set(null, combinedList.toArray(new String[0]));
			return index;
		} catch (IllegalArgumentException e) {
			logger.throwing("ModLoader", "AddArmor", e);
			ThrowException("An impossible error has occured!", e);
		} catch (IllegalAccessException e) {
			logger.throwing("ModLoader", "AddArmor", e);
			ThrowException("An impossible error has occured!", e);
		}
		return -1;
	}

	public static void AddLocalization(String key, String value)
	{
		Properties props = null;
		try {
			props = (Properties)getPrivateValue(StringTranslate.class, StringTranslate.getInstance(), 1);
		} catch (SecurityException e) {
			logger.throwing("ModLoader", "AddLocalization", e);
			ThrowException(e);
		} catch (NoSuchFieldException e) {
			logger.throwing("ModLoader", "AddLocalization", e);
			ThrowException(e);
		}
		if (props != null)
			props.put(key, value);
	}

	private static void addMod(ClassLoader classLoader, String className, String classFullName)
	{
		try
		{
			classFullName = classFullName.substring(0, classFullName.lastIndexOf('.'));
			if(!classFullName.contains("$") && (!props.containsKey(className) || (!props.getProperty(className).equalsIgnoreCase("no") && !props.getProperty(className).equalsIgnoreCase("off"))))
			{
				Class modClass = classLoader.loadClass(classFullName);
				if(BaseMod.class.isAssignableFrom(modClass))
				{
					setupProperties(modClass);
					BaseMod mod = (BaseMod)modClass.newInstance();
					String modName = mod.toString();
					if(mod != null && !modList.containsKey(modName))
					{
						modList.put(modName, mod);
						logger.fine((new StringBuilder("Mod Loaded: \"")).append(modName).append("\" from ").append(className).toString());
						System.out.println("Mod Loaded: " + modName);
					}
				}
			}
		}
		catch(Throwable e)
		{
			logger.fine((new StringBuilder("Failed to load mod from \"")).append(className).append("\"").toString());
			System.out.println((new StringBuilder("Failed to load mod from \"")).append(className).append("\"").toString());
			logger.throwing("ModLoader", "addMod", e);
			ThrowException(e);
		}
	}

	private static void setupProperties(Class<? extends BaseMod> mod) throws IllegalArgumentException, IllegalAccessException, IOException, SecurityException, NoSuchFieldException
	{
		Properties modprops = new Properties();

		File modcfgfile = new File(cfgdir, mod.getName() + ".cfg");
		if ((modcfgfile.exists()) && (modcfgfile.canRead())) {
			modprops.load(new FileInputStream(modcfgfile));
		}
		StringBuilder helptext = new StringBuilder();

		for (Field field : mod.getFields()) {
			if (((field.getModifiers() & 0x8) != 0) && (field.isAnnotationPresent(MLProp.class))) {
				Class type = field.getType();
				MLProp annotation = field.getAnnotation(MLProp.class);
				String key = annotation.name().length() == 0 ? field.getName() : annotation.name();
				Object currentvalue = field.get(null);

				StringBuilder range = new StringBuilder();
				if (annotation.min() != (-1.0D / 0.0D))
					range.append(String.format(",>=%.1f", new Object[] { Double.valueOf(annotation.min()) }));
				if (annotation.max() != (1.0D / 0.0D)) {
					range.append(String.format(",<=%.1f", new Object[] { Double.valueOf(annotation.max()) }));
				}
				StringBuilder info = new StringBuilder();
				if (annotation.info().length() > 0) {
					info.append(" -- ");
					info.append(annotation.info());
				}

				helptext.append(String.format("%s (%s:%s%s)%s\n", new Object[] { key, type.getName(), currentvalue, range, info }));

				if (modprops.containsKey(key)) {
					String strvalue = modprops.getProperty(key);

					Object value = null;
					if (type.isAssignableFrom(String.class)) value = strvalue;
					else if (type.isAssignableFrom(Integer.TYPE)) value = Integer.valueOf(Integer.parseInt(strvalue));
					else if (type.isAssignableFrom(Short.TYPE)) value = Short.valueOf(Short.parseShort(strvalue));
					else if (type.isAssignableFrom(Byte.TYPE)) value = Byte.valueOf(Byte.parseByte(strvalue));
					else if (type.isAssignableFrom(Boolean.TYPE)) value = Boolean.valueOf(Boolean.parseBoolean(strvalue));
					else if (type.isAssignableFrom(Float.TYPE)) value = Float.valueOf(Float.parseFloat(strvalue));
					else if (type.isAssignableFrom(Double.TYPE)) {
						value = Double.valueOf(Double.parseDouble(strvalue));
					}
					if (value != null) {
						if ((value instanceof Number)) {
							double num = ((Number)value).doubleValue();
							if ((annotation.min() != (-1.0D / 0.0D)) && (num < annotation.min()))
								continue;
							if ((annotation.max() != (1.0D / 0.0D)) && (num > annotation.max())) {
								continue;
							}
						}
						logger.finer(key + " set to " + value);
						if (!value.equals(currentvalue))
							field.set(null, value);
					}
				} else {
					logger.finer(key + " not in config, using default: " + currentvalue);
					modprops.setProperty(key, currentvalue.toString());
				}
			}
		}

		if ((!modprops.isEmpty()) && ((modcfgfile.exists()) || (modcfgfile.createNewFile())) && (modcfgfile.canWrite()))
			modprops.store(new FileOutputStream(modcfgfile), helptext.toString());
	}

	public static void AddName(Object instance, String name)
	{
		String tag = null;
		if ((instance instanceof Item)) {
			Item item = (Item)instance;
			if (item.getItemName() != null)
				tag = item.getItemName() + ".name";
		} else if ((instance instanceof Block)) {
			Block block = (Block)instance;
			if (block.getBlockName() != null)
				tag = block.getBlockName() + ".name";
		} else if ((instance instanceof ItemStack)) {
			ItemStack stack = (ItemStack)instance;
			if (stack.getItemName() != null)
				tag = stack.getItemName() + ".name";
		} else {
			Exception e = new Exception(instance.getClass().getName() + " cannot have name attached to it!");
			logger.throwing("ModLoader", "AddName", e);
			ThrowException(e);
		}
		if (tag != null) { 
			AddLocalization(tag, name);
		} else {
			Exception e = new Exception(instance + " is missing name tag!");
			logger.throwing("ModLoader", "AddName", e);
			ThrowException(e);
		}
	}

	public static int addOverride(String fileToOverride, String fileToAdd)
	{
		try
		{
			int i = getUniqueSpriteIndex(fileToOverride);
			addOverride(fileToOverride, fileToAdd, i);
			return i;
		} catch (Throwable e) {
			logger.throwing("ModLoader", "addOverride", e);
			ThrowException(e);
			throw new RuntimeException(e);
		}
	}

	public static void addOverride(String path, String overlayPath, int index)
	{
		int dst = -1;
		int left = 0;
		if (path.equals("/terrain.png")) {
			dst = 0;
			left = terrainSpritesLeft;
		} else if (path.equals("/gui/items.png")) {
			dst = 1;
			left = itemSpritesLeft; } else {
				return;
			}System.out.println("Overriding " + path + " with " + overlayPath + " @ " + index + ". " + left + " left.");
			logger.finer("addOverride(" + path + "," + overlayPath + "," + index + "). " + left + " left.");
			Map<String, Integer> overlays = overrides.get(Integer.valueOf(dst));
			if (overlays == null) {
				overlays = new HashMap<String, Integer>();
				overrides.put(Integer.valueOf(dst), overlays);
			}
			overlays.put(overlayPath, Integer.valueOf(index));
	}

	public static void AddRecipe(ItemStack output, Object[] params)
	{
		CraftingManager.getInstance().addRecipe(output, params);
	}

	public static void AddShapelessRecipe(ItemStack output, Object[] params)
	{
		CraftingManager.getInstance().addShapelessRecipe(output, params);
	}

	public static void AddSmelting(int input, ItemStack output)
	{
		FurnaceRecipes.smelting().addSmelting(input, output);
	}

	public static void AddSpawn(Class<? extends EntityLiving> entityClass, int weightedProb, EnumCreatureType spawnList)
	{
		AddSpawn(entityClass, weightedProb, spawnList, null);
	}

	public static void AddSpawn(Class<? extends EntityLiving> entityClass, int weightedProb, EnumCreatureType spawnList, BiomeGenBase[] biomes)
	{
		if(entityClass == null)
		{
			throw new IllegalArgumentException("entityClass cannot be null");
		}
		if(spawnList == null)
		{
			throw new IllegalArgumentException("spawnList cannot be null");
		}
		if(biomes == null)
		{
			biomes = standardBiomes;
		}
		for(int j = 0; j < biomes.length; j++)
		{
			List<SpawnListEntry> list = biomes[j].getSpawnableList(spawnList);

			if (list != null) {
				boolean exists = false;
				for (SpawnListEntry entry : list) {
					if (entry.entityClass == entityClass) {
						entry.spawnRarityRate = weightedProb;
						exists = true;
						break;
					}
				}
				if (!exists)
					list.add(new SpawnListEntry(entityClass, weightedProb));
			}
		}
	}

	public static void AddSpawn(String entityName, int weightedProb, EnumCreatureType spawnList)
	{
		AddSpawn(entityName, weightedProb, spawnList, null);
	}

	public static void AddSpawn(String entityName, int weightedProb, EnumCreatureType spawnList, BiomeGenBase[] biomes)
	{
		Class class1 = (Class)classMap.get(entityName);
		if(class1 != null && (EntityLiving.class).isAssignableFrom(class1))
		{
			AddSpawn(class1, weightedProb, spawnList, biomes);
		}
	}

	public static boolean DispenseEntity(World world, double x, double y, double z, int xVel, int zVel, ItemStack item)
	{
		for (BaseMod mod : modList.values())
			if (mod.DispenseEntity(world, x, y, z, xVel, zVel, item))
				return true;
		return false;
	}

	public static List<BaseMod> getLoadedMods()
	{
		return Collections.unmodifiableList(new LinkedList<BaseMod>(modList.values()));
	}

	public static Logger getLogger()
	{
		return logger;
	}

	public static Minecraft getMinecraftInstance()
	{
		if (instance == null) {
			try {
				ThreadGroup group = Thread.currentThread().getThreadGroup();
				int count = group.activeCount();
				Thread[] threads = new Thread[count];
				group.enumerate(threads);
				for (Thread thread : threads)
					if (thread.getName().equals("Minecraft main thread")) {
						instance = (Minecraft)getPrivateValue(Thread.class, thread, "target");
						break;
					}
			}
			catch (SecurityException e) {
				logger.throwing("ModLoader", "getMinecraftInstance", e);
				throw new RuntimeException(e);
			} catch (NoSuchFieldException e) {
				logger.throwing("ModLoader", "getMinecraftInstance", e);
				throw new RuntimeException(e);
			}
		}
		return instance;
	}

	public static <T, E> T getPrivateValue(Class<? super E> instanceclass, E instance, int fieldindex)
	throws IllegalArgumentException, SecurityException, NoSuchFieldException
	{
		try
		{
			Field f = instanceclass.getDeclaredFields()[fieldindex];
			f.setAccessible(true);
			return (T)f.get(instance);
		} catch (IllegalAccessException e) {
			logger.throwing("ModLoader", "getPrivateValue", e);
			ThrowException("An impossible error has occured!", e);
		}
		return null;
	}

	public static <T, E> T getPrivateValue(Class<? super E> instanceclass, E instance, String field)
	throws IllegalArgumentException, SecurityException, NoSuchFieldException
	{
		try
		{
			Field f = instanceclass.getDeclaredField(field);
			f.setAccessible(true);
			return (T)f.get(instance);
		} catch (IllegalAccessException e) {
			logger.throwing("ModLoader", "getPrivateValue", e);
			ThrowException("An impossible error has occured!", e);
		}
		return null;
	}

	public static int getUniqueBlockModelID(BaseMod basemod, boolean flag)
	{
		int i = nextBlockModelID++;
		blockModels.put(Integer.valueOf(i), basemod);
		blockSpecialInv.put(Integer.valueOf(i), Boolean.valueOf(flag));
		return i;
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
				itemSpritesLeft--;
				return itemSpriteIndex++;
			}
		}

		Exception exception = new Exception("No more empty item sprite indices left!");
		logger.throwing("ModLoader", "getUniqueItemSpriteIndex", exception);
		ThrowException(exception);
		return 0;
	}

	public static int getUniqueSpriteIndex(String s)
	{
		if(s.equals("/gui/items.png"))
		{
			return getUniqueItemSpriteIndex();
		}
		if(s.equals("/terrain.png"))
		{
			return getUniqueTerrainSpriteIndex();
		} else
		{
			Exception exception = new Exception("No registry for this texture: " + s);
			logger.throwing("ModLoader", "getUniqueItemSpriteIndex", exception);
			ThrowException(exception);
			return 0;
		}
	}

	private static int getUniqueTerrainSpriteIndex()
	{
		for(; terrainSpriteIndex < usedTerrainSprites.length; terrainSpriteIndex++)
		{
			if(!usedTerrainSprites[terrainSpriteIndex])
			{
				usedTerrainSprites[terrainSpriteIndex] = true;
				terrainSpritesLeft--;
				return terrainSpriteIndex++;
			}
		}

		Exception exception = new Exception("No more empty terrain sprite indices left!");
		logger.throwing("ModLoader", "getUniqueItemSpriteIndex", exception);
		ThrowException(exception);
		return 0;
	}

	private static void init()
	{
		hasInit = true;
		String usedItemSpritesString = "1111111111111111111111111111111111111101111111011111111111111001111111111111111111111111111010111111100110000011111110000000001111111001100000110000000100000011000000010000001100000000000000110000000000000000000000000000000000000000000000001100000000000000";
		String usedTerrainSpritesString = "1111111111111111111111111111110111111111111111111111110111111111111111111111000111111011111111111111001111000000111111111111100011111111000010001111011110000000111111000000000011111100000000001111000000000111111000000000001101000000000001111111111111000011";
		for(int i = 0; i < 256; i++)
		{
			usedItemSprites[i] = usedItemSpritesString.charAt(i) == '1';
			if(!usedItemSprites[i])
			{
				itemSpritesLeft++;
			}
			usedTerrainSprites[i] = usedTerrainSpritesString.charAt(i) == '1';
			if(!usedTerrainSprites[i])
			{
				terrainSpritesLeft++;
			}
		}

		try
		{
			instance = (Minecraft)getPrivateValue(Minecraft.class, null, 1);
			instance.entityRenderer = new EntityRendererProxy(instance);
			classMap = (Map<String, Class<? extends Entity>>)getPrivateValue(EntityList.class, null, 0);
			field_modifiers = (Field.class).getDeclaredField("modifiers");
			field_modifiers.setAccessible(true);
			field_blockList = (Session.class).getDeclaredFields()[0];
			field_blockList.setAccessible(true);
			field_TileEntityRenderers = (TileEntityRenderer.class).getDeclaredFields()[0];
			field_TileEntityRenderers.setAccessible(true);
			field_armorList = (RenderPlayer.class).getDeclaredFields()[3];
			field_modifiers.setInt(field_armorList, field_armorList.getModifiers() & 0xffffffef);
			field_armorList.setAccessible(true);
			field_animList = (RenderEngine.class).getDeclaredFields()[5];
			field_animList.setAccessible(true);
			Field afield[] = (BiomeGenBase.class).getDeclaredFields();
			LinkedList<BiomeGenBase> linkedlist = new LinkedList<BiomeGenBase>();
			for(int j = 0; j < afield.length; j++)
			{
				Class class1 = afield[j].getType();
				if((afield[j].getModifiers() & 8) != 0 && class1.isAssignableFrom(BiomeGenBase.class))
				{
					BiomeGenBase biomegenbase = (BiomeGenBase)afield[j].get(null);
					if(!(biomegenbase instanceof BiomeGenHell))
					{
						linkedlist.add(biomegenbase);
					}
				}
			}

			standardBiomes = linkedlist.toArray(new BiomeGenBase[0]);
			try
			{
				method_RegisterTileEntity = (TileEntity.class).getDeclaredMethod("a", new Class[] {
						java.lang.Class.class, java.lang.String.class
				});
			}
			catch(NoSuchMethodException e)
			{
				method_RegisterTileEntity = (TileEntity.class).getDeclaredMethod("addMapping", new Class[] {
						java.lang.Class.class, java.lang.String.class
				});
			}
			method_RegisterTileEntity.setAccessible(true);
			try
			{
				method_RegisterEntityID = (EntityList.class).getDeclaredMethod("a", new Class[] {
						java.lang.Class.class, java.lang.String.class, Integer.TYPE
				});
			}
			catch(NoSuchMethodException e)
			{
				method_RegisterEntityID = (EntityList.class).getDeclaredMethod("addMapping", new Class[] {
						java.lang.Class.class, java.lang.String.class, Integer.TYPE
				});
			}
			method_RegisterEntityID.setAccessible(true);
		}
		catch(SecurityException e)
		{
			logger.throwing("ModLoader", "init", e);
			ThrowException(e);
			throw new RuntimeException(e);
		}
		catch(NoSuchFieldException e)
		{
			logger.throwing("ModLoader", "init", e);
			ThrowException(e);
			throw new RuntimeException(e);
		}
		catch(NoSuchMethodException e)
		{
			logger.throwing("ModLoader", "init", e);
			ThrowException(e);
			throw new RuntimeException(e);
		}
		catch(IllegalArgumentException e)
		{
			logger.throwing("ModLoader", "init", e);
			ThrowException(e);
			throw new RuntimeException(e);
		}
		catch(IllegalAccessException e)
		{
			logger.throwing("ModLoader", "init", e);
			ThrowException(e);
			throw new RuntimeException(e);
		}
		try
		{
			loadConfig();
			if(props.containsKey("loggingLevel"))
			{
				cfgLoggingLevel = Level.parse(props.getProperty("loggingLevel"));
			}
			if(props.containsKey("grassFix"))
			{
				RenderBlocks.cfgGrassFix = Boolean.parseBoolean(props.getProperty("grassFix"));
			}
			logger.setLevel(cfgLoggingLevel);
			if((logfile.exists() || logfile.createNewFile()) && logfile.canWrite() && logHandler == null)
			{
				logHandler = new FileHandler(logfile.getPath());
				logHandler.setFormatter(new SimpleFormatter());
				logger.addHandler(logHandler);
			}
			logger.fine("ModLoader Beta 1.6.6 Initializing...");
			System.out.println("ModLoader Beta 1.6.6 Initializing...");
			File source = new File((ModLoader.class).getProtectionDomain().getCodeSource().getLocation().toURI());
			modDir.mkdirs();
			readFromModFolder(modDir);
			readFromClassPath(source);
			System.out.println("Done.");
			props.setProperty("loggingLevel", cfgLoggingLevel.getName());
			props.setProperty("grassFix", Boolean.toString(RenderBlocks.cfgGrassFix));

			for (BaseMod mod : modList.values()) {
				mod.ModsLoaded();
				if (!props.containsKey(mod.getClass().getName())) {
					props.setProperty(mod.getClass().getName(), "on");
				}
			}

			initStats();
			saveConfig();
		}
		catch(Throwable throwable)
		{
			logger.throwing("ModLoader", "init", throwable);
			ThrowException("ModLoader has failed to initialize.", throwable);
			if(logHandler != null)
			{
				logHandler.close();
			}
			throw new RuntimeException(throwable);
		}
	}

	private static void initStats()
	{
		for(int i = 0; i < Block.blocksList.length; i++)
		{
			if(!StatList.field_25169_C.containsKey(Integer.valueOf(0x1000000 + i)) && Block.blocksList[i] != null && Block.blocksList[i].getEnableStats())
			{
				String s = StatCollector.translateToLocalFormatted("stat.mineBlock", new Object[] {
						Block.blocksList[i].func_25016_i()
				});
				StatList.mineBlockStatArray[i] = (new StatCrafting(0x1000000 + i, s, i)).registerStat();
				StatList.field_25185_d.add(StatList.mineBlockStatArray[i]);
			}
		}

		for(int j = 0; j < Item.itemsList.length; j++)
		{
			if(!StatList.field_25169_C.containsKey(Integer.valueOf(0x1020000 + j)) && Item.itemsList[j] != null)
			{
				String s1 = StatCollector.translateToLocalFormatted("stat.useItem", new Object[] {
						Item.itemsList[j].getStatName()
				});
				StatList.field_25172_A[j] = (new StatCrafting(0x1020000 + j, s1, j)).registerStat();
				if(j >= Block.blocksList.length)
				{
					StatList.field_25186_c.add(StatList.field_25172_A[j]);
				}
			}
			if(!StatList.field_25169_C.containsKey(Integer.valueOf(0x1030000 + j)) && Item.itemsList[j] != null && Item.itemsList[j].isDamagable())
			{
				String s2 = StatCollector.translateToLocalFormatted("stat.breakItem", new Object[] {
						Item.itemsList[j].getStatName()
				});
				StatList.field_25170_B[j] = (new StatCrafting(0x1030000 + j, s2, j)).registerStat();
			}
		}

		HashSet<Integer> idHashSet = new HashSet<Integer>();

		for (Object result : CraftingManager.getInstance().getRecipeList())
			idHashSet.add(Integer.valueOf(((IRecipe)result).func_25117_b().itemID));
		for (Object result : FurnaceRecipes.smelting().getSmeltingList().values())
			idHashSet.add(Integer.valueOf(((ItemStack)result).itemID));

		for (int id : idHashSet) {
			if(!StatList.field_25169_C.containsKey(Integer.valueOf(0x1010000 + id)) && Item.itemsList[id] != null)
			{
				String s3 = StatCollector.translateToLocalFormatted("stat.craftItem", new Object[] {
						Item.itemsList[id].getStatName()
				});
				StatList.field_25158_z[id] = (new StatCrafting(0x1010000 + id, s3, id)).registerStat();
			}
		}
	}

	public static boolean isGUIOpen(Class<? extends GuiScreen> gui)
	{
		Minecraft minecraft = getMinecraftInstance();
		if(gui == null)
		{
			return minecraft.currentScreen == null;
		}
		if(minecraft.currentScreen == null && gui != null)
		{
			return false;
		} else
		{
			return gui.isInstance(minecraft.currentScreen);
		}
	}

	public static boolean isModLoaded(String modname)
	{
		Class chk = null;
		try {
			chk = Class.forName(modname);
		} catch (ClassNotFoundException e) {
			return false;
		}
		if (chk != null) {
			for (BaseMod mod : modList.values()) {
				if (chk.isInstance(mod))
					return true;
			}
		}
		return false;
	}

	public static void loadConfig()
	throws IOException
	{
		cfgdir.mkdir();
		if(!cfgfile.exists() && !cfgfile.createNewFile())
		{
			return;
		}
		if(cfgfile.canRead())
		{
			FileInputStream fileinputstream = new FileInputStream(cfgfile);
			props.load(fileinputstream);
			fileinputstream.close();
		}
	}

	public static BufferedImage loadImage(RenderEngine texCache, String path)
	throws Exception
	{
		TexturePackList texturepacklist = (TexturePackList)getPrivateValue(RenderEngine.class, texCache, 11);
		InputStream inputstream = texturepacklist.selectedTexturePack.getResourceAsStream(path);
		if(inputstream == null)
		{
			throw new Exception("Image not found: " + path);
		}
		BufferedImage bufferedimage = ImageIO.read(inputstream);
		if(bufferedimage == null)
		{
			throw new Exception("Image not found: " + path);
		} else
		{
			return bufferedimage;
		}
	}

	public static void OnTick(Minecraft game)
	{
		if(!hasInit)
		{
			init();
			logger.fine("Initialized");
		}
		if(texPack == null || game.gameSettings.skin != texPack)
		{
			texturesAdded = false;
			texPack = game.gameSettings.skin;
		}
		if(!texturesAdded && game.renderEngine != null)
		{
			RegisterAllTextureOverrides(game.renderEngine);
			texturesAdded = true;
		}
		long newclock = 0L;
		if(game.theWorld != null)
		{
			newclock = game.theWorld.getWorldTime();
			for (Map.Entry<BaseMod, Boolean> modSet : inGameHooks.entrySet())
				if ((clock == newclock) && (modSet.getValue().booleanValue()))
					modSet.getKey().OnTickInGame(game);

		}
		if(game.currentScreen != null)
		{
			for (Map.Entry<BaseMod, Boolean> modSet : inGUIHooks.entrySet())
				if(clock != newclock || !(modSet.getValue().booleanValue() & (game.theWorld != null)))
					modSet.getKey().OnTickInGUI(game, game.currentScreen);
		}
		if(clock != newclock)
		{
			for (Map.Entry<BaseMod, Map<KeyBinding, boolean[]>> modSet : keyList.entrySet()) {
				for (Map.Entry<KeyBinding, boolean[]> keySet : modSet.getValue().entrySet()) {
					boolean state = Keyboard.isKeyDown(keySet.getKey().keyCode);
					boolean[] keyInfo = keySet.getValue();
					boolean oldState = keyInfo[1];
					keyInfo[1] = state;
					if ((!state) || ((oldState) && (!keyInfo[0])))
						continue;
					modSet.getKey().KeyboardEvent(keySet.getKey());
				}
			}
		}
		clock = newclock;
	}

	public static void OpenGUI(EntityPlayer player, GuiScreen gui)
	{
		if(!hasInit)
		{
			init();
			logger.fine("Initialized");
		}
		Minecraft minecraft = getMinecraftInstance();
		if(minecraft.thePlayer != player)
		{
			return;
		}
		if(gui != null)
		{
			minecraft.displayGuiScreen(gui);
		}
	}

	public static void PopulateChunk(IChunkProvider generator, int chunkX, int chunkZ, World world)
	{
		if(!hasInit)
		{
			init();
			logger.fine("Initialized");
		}
		for (BaseMod mod : modList.values())
		{
			if (generator.makeString().equals("RandomLevelSource")) mod.GenerateSurface(world, world.rand, chunkX, chunkZ);
			else if (generator.makeString().equals("HellRandomLevelSource"))
				mod.GenerateNether(world, world.rand, chunkX, chunkZ);
		}
	}

	private static void readFromModFolder(File folder)
	throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException
	{
		if(!folder.isDirectory())
			throw new IllegalArgumentException("folder must be a Directory.");
		logger.finer((new StringBuilder("Adding mods from ")).append(folder.getCanonicalPath()).toString());
		System.out.println((new StringBuilder("Adding mods from ")).append(folder.getCanonicalPath()).toString());
		readFromFileRecursive(folder, folder);
	}

	private static void readFromClassPath(File file)
	throws FileNotFoundException, IOException
	{
		logger.finer((new StringBuilder("Adding mods from ")).append(file.getCanonicalPath()).toString());
		System.out.println((new StringBuilder("Adding mods from ")).append(file.getCanonicalPath()).toString());
		readFromFileRecursive(file, file.isDirectory() ? file : file.getParentFile());
	}

	private static void readFromFileRecursive(File file, File folder)
	throws FileNotFoundException, IOException
	{
		if(file.isDirectory())
			for (File subfFile : file.listFiles())
				readFromFileRecursive(subfFile, folder);
		else if(file.isFile())
		{
			String fileName = file.getName();
			if (fileName.endsWith(".jar") || fileName.endsWith(".zip"))
			{
				logger.finer("Archive found : " + fileName);
				ZipInputStream zipinputstream = new ZipInputStream(new BufferedInputStream(new FileInputStream(file)));
				try
				{
					for (ZipEntry entry = zipinputstream.getNextEntry(); entry != null; entry = zipinputstream.getNextEntry())
					{
						String entryName = entry.getName();
						String className = entryName.substring(entryName.lastIndexOf('/') + 1, entryName.length());
						if(!entry.isDirectory() && className.startsWith("mod_") && className.endsWith(".class"))
							addMod(new URLClassLoader(new URL[] { folder.toURI().toURL() }), className, entryName.replace('/', '.'));
					}
				}
				finally
				{
					zipinputstream.close();
				}
			}
			else if(fileName.startsWith("mod_") && fileName.endsWith(".class"))
				addMod(new URLClassLoader(new URL[] { folder.toURI().toURL() }), fileName, file.getAbsolutePath().replace(folder.getAbsolutePath() + "\\", "").replace('\\', '.'));
		}
	}

	public static KeyBinding[] RegisterAllKeys(KeyBinding[] akeybinding)
	{
		List<KeyBinding> existingKeyList = Arrays.asList(akeybinding);
		List<KeyBinding> combinedList = new ArrayList<KeyBinding>();
		combinedList.addAll(existingKeyList);
		for (Map<KeyBinding, boolean[]> keyMap : keyList.values())
			combinedList.addAll(keyMap.keySet());
		return combinedList.toArray(new KeyBinding[0]);
	}

	public static void RegisterAllTextureOverrides(RenderEngine texCache)
	{
		animList.clear();
		Minecraft game = getMinecraftInstance();
		for (BaseMod mod : modList.values())
			mod.RegisterAnimation(game);
		for (TextureFX anim : animList)
			texCache.registerTextureFX(anim);

		for (Map.Entry<Integer, Map<String, Integer>> overlay : overrides.entrySet())
			for (Map.Entry<String, Integer> overlayEntry : overlay.getValue().entrySet()) {
				String overlayPath = overlayEntry.getKey();
				int index = overlayEntry.getValue().intValue();
				int dst = overlay.getKey().intValue();
				String dstPath = null;
				if (dst == 0) dstPath = "/terrain.png";
				else if (dst == 1) dstPath = "/gui/items.png"; else
					throw new ArrayIndexOutOfBoundsException(dst);
				try {
					BufferedImage im = loadImage(texCache, overlayPath);
					ModTextureStatic anim = new ModTextureStatic(index, dst, im);

					texCache.registerTextureFX(anim);
				} catch (Exception e) {
					logger.throwing("ModLoader", "RegisterAllTextureOverrides", e);
					ThrowException(e);
					throw new RuntimeException(e);
				}
			}
	}

	public static void RegisterBlock(Block block)
	{
		RegisterBlock(block, null);
	}

	public static void RegisterBlock(Block block, Class<? extends ItemBlock> class1)
	{
		try
		{
			if(block == null)
			{
				throw new IllegalArgumentException("block parameter cannot be null.");
			}
			List<Block> list = (List<Block>)field_blockList.get(null);
			list.add(block);
			int id = block.blockID;
			ItemBlock item = null;
			if(class1 != null)
				item = (ItemBlock)class1.getConstructor(new Class[] { Integer.TYPE }).newInstance(new Object[] { Integer.valueOf(id - 256) });
			else
				item = new ItemBlock(id - 256);
			if(Block.blocksList[id] != null && Item.itemsList[id] == null)
				Item.itemsList[id] = item;
		}
		catch(IllegalArgumentException e)
		{
			logger.throwing("ModLoader", "RegisterBlock", e);
			ThrowException(e);
		}
		catch(IllegalAccessException e)
		{
			logger.throwing("ModLoader", "RegisterBlock", e);
			ThrowException(e);
		}
		catch(SecurityException e)
		{
			logger.throwing("ModLoader", "RegisterBlock", e);
			ThrowException(e);
		}
		catch(InstantiationException e)
		{
			logger.throwing("ModLoader", "RegisterBlock", e);
			ThrowException(e);
		}
		catch(InvocationTargetException e)
		{
			logger.throwing("ModLoader", "RegisterBlock", e);
			ThrowException(e);
		}
		catch(NoSuchMethodException e)
		{
			logger.throwing("ModLoader", "RegisterBlock", e);
			ThrowException(e);
		}
	}

	public static void RegisterEntityID(Class<? extends Entity> entityClass, String entityName, int id)
	{
		try
		{
			method_RegisterEntityID.invoke(null, new Object[] { entityClass, entityName, Integer.valueOf(id) });
		} catch (IllegalArgumentException e) {
			logger.throwing("ModLoader", "RegisterEntityID", e);
			ThrowException(e);
		} catch (IllegalAccessException e) {
			logger.throwing("ModLoader", "RegisterEntityID", e);
			ThrowException(e);
		} catch (InvocationTargetException e) {
			logger.throwing("ModLoader", "RegisterEntityID", e);
			ThrowException(e);
		}
	}

	public static void RegisterKey(BaseMod mod, KeyBinding keyHandler, boolean allowRepeat)
	{
		Map<KeyBinding, boolean[]> keyMap = keyList.get(mod);
		if (keyMap == null)
			keyMap = new HashMap<KeyBinding, boolean[]>();
			keyMap.put(keyHandler, new boolean[] { allowRepeat });
			keyList.put(mod, keyMap);
	}

	public static void RegisterTileEntity(Class<? extends TileEntity> tileEntityClass, String id)
	{
		RegisterTileEntity(tileEntityClass, id, null);
	}

	public static void RegisterTileEntity(Class<? extends TileEntity> tileEntityClass, String id, TileEntitySpecialRenderer renderer)
	{
		try
		{
			method_RegisterTileEntity.invoke(null, new Object[] { tileEntityClass, id });
			if (renderer != null) {
				TileEntityRenderer ref = TileEntityRenderer.instance;

				Map<Class<? extends TileEntity>, TileEntitySpecialRenderer> renderers = (Map<Class<? extends TileEntity>, TileEntitySpecialRenderer>)field_TileEntityRenderers.get(ref);
				renderers.put(tileEntityClass, renderer);
				renderer.setTileEntityRenderer(ref);
			}
		} catch (IllegalArgumentException e) {
			logger.throwing("ModLoader", "RegisterTileEntity", e);
			ThrowException(e);
		} catch (IllegalAccessException e) {
			logger.throwing("ModLoader", "RegisterTileEntity", e);
			ThrowException(e);
		} catch (InvocationTargetException e) {
			logger.throwing("ModLoader", "RegisterTileEntity", e);
			ThrowException(e);
		}
	}

	public static void RemoveSpawn(Class<? extends EntityLiving> entityClass, EnumCreatureType spawnList)
	{
		RemoveSpawn(entityClass, spawnList, null);
	}

	public static void RemoveSpawn(Class<? extends EntityLiving> entityClass, EnumCreatureType spawnList, BiomeGenBase[] biomes)
	{
		if (entityClass == null) {
			throw new IllegalArgumentException("entityClass cannot be null");
		}
		if (spawnList == null) {
			throw new IllegalArgumentException("spawnList cannot be null");
		}
		if (biomes == null) {
			biomes = standardBiomes;
		}
		for (int i = 0; i < biomes.length; i++) {
			List<SpawnListEntry> list = biomes[i].getSpawnableList(spawnList);

			if (list != null)
				for (SpawnListEntry entry : list)
					if (entry.entityClass == entityClass) {
						list.remove(entry);
						break;
					}
		}
	}

	public static void RemoveSpawn(String entityName, EnumCreatureType spawnList)
	{
		RemoveSpawn(entityName, spawnList, null);
	}

	public static void RemoveSpawn(String entityName, EnumCreatureType spawnList, BiomeGenBase[] biomes)
	{
		Class entityClass = classMap.get(entityName);
		if ((entityClass != null) && (EntityLiving.class.isAssignableFrom(entityClass)))
			RemoveSpawn(entityClass, spawnList, biomes);
	}

	public static boolean RenderBlockIsItemFull3D(int modelID)
	{
		if(!blockSpecialInv.containsKey(Integer.valueOf(modelID)))
			return modelID == 11;
		return blockSpecialInv.get(Integer.valueOf(modelID)).booleanValue();
	}

	public static void RenderInvBlock(RenderBlocks renderer, Block block, int metadata, int modelID)
	{
		BaseMod mod = blockModels.get(Integer.valueOf(modelID));
		if (mod == null)
			return;
		mod.RenderInvBlock(renderer, block, metadata, modelID);
	}

	public static boolean RenderWorldBlock(RenderBlocks renderer, IBlockAccess world, int x, int y, int z, Block block, int modelID)
	{
		BaseMod mod = blockModels.get(Integer.valueOf(modelID));
		if (mod == null)
			return false;
		return mod.RenderWorldBlock(renderer, world, x, y, z, block, modelID);
	}

	public static void saveConfig()
	throws IOException
	{
		cfgdir.mkdir();
		if(!cfgfile.exists() && !cfgfile.createNewFile())
		{
			return;
		}
		if(cfgfile.canWrite())
		{
			FileOutputStream fileoutputstream = new FileOutputStream(cfgfile);
			props.store(fileoutputstream, "ModLoader Config");
			fileoutputstream.close();
		}
	}

	public static void SetInGameHook(BaseMod mod, boolean enable, boolean useClock)
	{
		if (enable) inGameHooks.put(mod, Boolean.valueOf(useClock)); else
			inGameHooks.remove(mod);
	}

	public static void SetInGUIHook(BaseMod mod, boolean enable, boolean useClock)
	{
		if (enable) inGUIHooks.put(mod, Boolean.valueOf(useClock)); else
			inGUIHooks.remove(mod);
	}

	public static <T, E> void setPrivateValue(Class<? super T> instanceclass, T instance, int fieldindex, E value)
	throws IllegalArgumentException, SecurityException, NoSuchFieldException
	{
		try
		{
			Field f = instanceclass.getDeclaredFields()[fieldindex];
			f.setAccessible(true);
			int modifiers = field_modifiers.getInt(f);
			if ((modifiers & 0x10) != 0)
				field_modifiers.setInt(f, modifiers & 0xFFFFFFEF);
			f.set(instance, value);
		} catch (IllegalAccessException e) {
			logger.throwing("ModLoader", "setPrivateValue", e);
			ThrowException("An impossible error has occured!", e);
		}
	}

	public static <T, E> void setPrivateValue(Class<? super T> instanceclass, T instance, String field, E value)
	throws IllegalArgumentException, SecurityException, NoSuchFieldException
	{
		try
		{
			Field f = instanceclass.getDeclaredField(field);
			int modifiers = field_modifiers.getInt(f);
			if ((modifiers & 0x10) != 0)
				field_modifiers.setInt(f, modifiers & 0xFFFFFFEF);
			f.setAccessible(true);
			f.set(instance, value);
		} catch (IllegalAccessException e) {
			logger.throwing("ModLoader", "setPrivateValue", e);
			ThrowException("An impossible error has occured!", e);
		}
	}

	public static void TakenFromCrafting(EntityPlayer player, ItemStack item)
	{
		for (BaseMod mod : modList.values())
			mod.TakenFromCrafting(player, item);
	}

	public static void TakenFromFurnace(EntityPlayer player, ItemStack item)
	{
		for (BaseMod mod : modList.values())
			mod.TakenFromFurnace(player, item);
	}

	public static void OnItemPickup(EntityPlayer player, ItemStack item)
	{
		for (BaseMod mod : modList.values())
			mod.OnItemPickup(player, item);
	}

	public static void ThrowException(String message, Throwable e)
	{
		Minecraft minecraft = getMinecraftInstance();
		if(minecraft != null)
		{
			minecraft.displayUnexpectedThrowable(new UnexpectedThrowable(message, e));
		} else
		{
			throw new RuntimeException(e);
		}
	}

	private static void ThrowException(Throwable e)
	{
		ThrowException("Exception occured in ModLoader", e);
	}
}