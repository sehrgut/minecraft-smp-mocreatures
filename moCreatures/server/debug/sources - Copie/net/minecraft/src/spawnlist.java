
package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Logger;

public final class spawnlist
{
	private static Map<String, List<MobSpawnerBase>> biomes = new HashMap<String, List<MobSpawnerBase>>();
	private static Map<String, Class<? extends RegionFileCache>> mobNames;
	private static List<MobSpawnerBase> hellBiomes = new ArrayList<MobSpawnerBase>();
	private static List<MobSpawnerBase> surfaceBiomes = new ArrayList<MobSpawnerBase>();
	private static List<MobSpawnerBase> baseBiomes = new ArrayList<MobSpawnerBase>();
	private static List<MobSpawnerBase> swampBiomes = new ArrayList<MobSpawnerBase>();
	private static List<MobSpawnerBase> desertBiomes = new ArrayList<MobSpawnerBase>();
	public static final int MONSTER;
	public static final int CREATURE;
	public static final int WATERCREATURE;
	private static final int[] mobTypes;
	private static final Field[] fields;
	private static Field modifierField;
	private static final String[][] fieldNames = {
		{
			"r", "biomeMonsters", "field_4239_r"
		}, {
			"s", "biomeCreatures", "field_4238_s"
		}, {
			"t", "biomeWaterCreatures", "field_21108_t"
		}
	};
	private static final String[] fieldMapping = {
		"a", "stringToClassMapping", "field_1611_a"
	};
	private static final String[] fieldLimit = {
		"e", "maxNumberOfEntityType", "maxNumberOfCreature", "field_4277_d"
	};
	private static boolean loaded;
	private static Logger logger;

	private spawnlist()
	{
	}

	private static void load()
	{
		if(loaded)
		{
			return;
		}
		logger = Logger.getLogger("spawnlist");
		logger.setParent(ModLoader.getLogger());
		biomes.put("Hell biomes", hellBiomes);
		biomes.put("Surface biomes", surfaceBiomes);
		biomes.put("Desert biomes", desertBiomes);
		biomes.put("Base biomes", baseBiomes);
		biomes.put("Swamp biomes", swampBiomes);
		stealThemFields();
		loaded = true;
	}

	private static Field fetchField(Class c, String[] names)
	{
		for (String name : names)
			try {
				Field f = c.getDeclaredField(name);
				f.setAccessible(true);

				int j = modifierField.getInt(f);

				if ((j & 0x10) != 0) {
					modifierField.setInt(f, j & 0xFFFFFFEF);
				}

				return f;
			}
		catch (Throwable e)
		{
		}
		throwException("fetchField", "couldn't find a field: " + getFieldDescription(c, names));
		return null;
	}

	private static String joinString(String glue, String[] strings)
	{
		StringBuilder result = new StringBuilder();
		boolean first = true;

		for (String string : strings)
		{
			if (first)
				first = false;
			else
				result.append(glue);
			result.append(string);
		}

		return result.toString();
	}

	private static String getFieldDescription(Class c, String[] names)
	{
		return new StringBuilder(c.getName()).append(".{").append(joinString(",", names)).append("}").toString();
	}

	private static Object fetchValue(Class c, Object inst, String[] names)
	{
		Field f = fetchField(c, names);
		try
		{
			return f.get(inst);
		}
		catch(Throwable throwable)
		{
			throwException("fetchValue", "couldn't access a field: " + getFieldDescription(c, names));
		}
		return null;
	}

	private static void throwException(String method, String message)
	{
		throwException(method, new RuntimeException(message));
	}

	private static void throwException(String method, String message, Throwable cause)
	{
		throwException(method, new RuntimeException(message, cause));
	}

	private static void throwException(String method, Throwable e)
	{
		logger.throwing("spawnlist", method, e);
		ModLoader.throwException("", e);
	}

	private static void setValue(Class c, Object inst, Object value, String[] names)
	{
		Field field = fetchField(c, names);
		try
		{
			field.set(inst, value);
		}
		catch(Throwable throwable)
		{
			throwException("setValue", "couldn't access a field: " + getFieldDescription(c, names), throwable);
		}
	}

	private static List processArgs(Object[] mobs)
	{
		ArrayList<Class<? extends EntityLiving>> list = new ArrayList<Class<? extends EntityLiving>>();

		for (Object o : mobs) 
		{
			if (o == null)
				continue;
			Class<?> c = null;

			if (o instanceof String)
			{
				c = mobNames.get((String)o);

				if (c == null)
				{
					throwException("processArgs", "unknown mob name: " + (String)o);
					return null;
				}
			} else if (o instanceof Class) 
			{
				c = (Class<?>)o;
			} else 
			{
				throwException("processArgs", "unknown parameter type: " + o.getClass().getName());
				return null;
			}

			if(!EntityLiving.class.isAssignableFrom(c))
			{
				throwException("processArgs", "can't add non living entities to spawnlists: " + c.getName());
				return null;
			}
			list.add(c.asSubclass(EntityLiving.class));
		}

		return list;
	}

	private static Field getField(int type)
	{
		checkMobType("getField", type);
		return fields[type];
	}

	private static void checkMobType(String method, int mobType)
	{
		if(mobType < 0 || mobType > fieldNames.length)
			throwException(method, "unknown mob type: " + Integer.toString(mobType));
	}

	public static void setLimit(int mobType, int limit)
	{
		checkMobType("setLimit", mobType);
		logger.fine((new StringBuilder()).append("set limit of ").append(fieldNames[mobType][1]).append(" to ").append(limit).toString());
		EnumCreatureType enumcreaturetype = EnumCreatureType.values()[mobType];
		setValue(EnumCreatureType.class, enumcreaturetype, Integer.valueOf(limit), fieldLimit);
	}

	public static int getLimit(int mobType)
	{
		checkMobType("getLimit", mobType);
		EnumCreatureType enumcreaturetype = EnumCreatureType.values()[mobType];
		return ((Integer)fetchValue(EnumCreatureType.class, enumcreaturetype, fieldLimit)).intValue();
	}

	public static void add(String biomeName, int mobType, Object[] mobs)
	{
		add(new String[] { biomeName }, mobType, mobs);
	}

	public static void add(String[] biomeNames, int type, Object[] mobs)
	{
		load();
		add(biomeNames, getField(type), processArgs(mobs));
	}

	private static List getBiomesByName(String name)
	{
		List<MobSpawnerBase> list = biomes.get(name);
		if(list == null)
			throwException("getBiomesByName", "Unknown biome: " + name);
		return list;
	}

	private static void add(String[] biomeNames, Field field, List mobs)
	{
		StringBuilder msg = new StringBuilder("add({").append(joinString(", ", biomeNames)).append("}, ").append(field.getName()).append(", {");
		boolean first = true;
		for (Class<? extends EntityLiving> mob : (List<Class<? extends EntityLiving>>)mobs) 
		{
			if (first)
				first = false;
			else
				msg.append(", ");
			msg.append(mob.getName());
		}

		msg.append("})");
		logger.fine(msg.toString());
		for (String name : biomeNames)
		{
			List<MobSpawnerBase> biomes = getBiomesByName(name);
			for (MobSpawnerBase biome : biomes) 
			{
				Set<Class<? extends EntityLiving>> existing = getMobs(biome, field);
				existing.addAll(mobs);
				setMobs(biome, field, existing);
			}
		}
	}

	public static void remove(String biomeName, int type, Object[] mobs)
	{
		remove(new String[] { biomeName }, type, mobs);
	}

	public static void remove(String[] biomeNames, int type, Object[] mobs)
	{
		load();
		remove(biomeNames, getField(type), processArgs(mobs));
	}

	private static void remove(String[] biomeNames, Field field, List mobs)
	{
		StringBuilder msg = new StringBuilder("remove({").append(joinString(", ", biomeNames)).append("}, ").append(field.getName()).append(", {");
		boolean first = true;

		for (Class<? extends EntityLiving> mob : (List<Class<? extends EntityLiving>>)mobs) 
		{
			if (first)
				first = false;
			else
				msg.append(", ");
			msg.append(mob.getName());
		}

		msg.append("})");
		logger.fine(msg.toString());
		for (String name : biomeNames)
		{
			List<MobSpawnerBase> biomes = getBiomesByName(name);
			for (MobSpawnerBase biome : biomes) 
			{
				Set<Class<? extends EntityLiving>> existing = getMobs(biome, field);
				existing.removeAll(mobs);
				setMobs(biome, field, existing);
			}
		}
	}

	private static void stealThemFields()
	{
		try
		{
			modifierField = (java.lang.reflect.Field.class).getDeclaredField("modifiers");
			modifierField.setAccessible(true);
		}
		catch(Throwable throwable)
		{
			throwException("stealThemFields", "couldn't get the modifier field of Field.class", throwable);
		}
		Object obj = fetchValue(EntityList.class, null, fieldMapping);
		if(!(obj instanceof Map))
			throwException("stealThemFields", "couldn't get EntityList.stringToClassMapping: unexpected type");
		mobNames = (Map<String, Class<? extends RegionFileCache>>)obj;
		try
		{
			for (Field field : MobSpawnerBase.class.getDeclaredFields()) 
			{
				field.setAccessible(true);
				if(Modifier.isStatic(field.getModifiers()))
				{
					Object value = field.get(null);
					if(value instanceof MobSpawnerBase)
					{
						MobSpawnerBase biome = (MobSpawnerBase)value;
						ArrayList<MobSpawnerBase> arraylist = new ArrayList<MobSpawnerBase>();
						arraylist.add(biome);
						biomes.put(biome.biomeName, arraylist);
						if(value instanceof MobSpawnerHell)
							hellBiomes.add(biome);
						else
						{
							surfaceBiomes.add(biome);
							((biome instanceof MobSpawnerDesert) ? desertBiomes : (biome instanceof MobSpawnerSwamp) ? swampBiomes : baseBiomes).add(biome);
						}
					}
				}
			}

		}
		catch(Throwable throwable1)
		{
			throwException("stealThemFields", "couldn't steal biome fields", throwable1);
			return;
		}
		for(int i = 0; i < mobTypes.length; i++)
			fields[i] = fetchField(MobSpawnerBase.class, fieldNames[i]);
	}

	private static Set getMobs(MobSpawnerBase biome, Field field)
	{
		try
		{
			Class<? extends EntityLiving>[] result = (Class<? extends EntityLiving>[])field.get(biome);
			Set<Class<? extends EntityLiving>> set = new LinkedHashSet<Class<? extends EntityLiving>>(Arrays.asList(result));
			return set;
		}
		catch(Throwable throwable)
		{
			throwException("getMobs", "couldn't get biome mob field", throwable);
		}
		return null;
	}

	private static void setMobs(MobSpawnerBase mobspawnerbase, Field field, Set set)
	{
		try
		{
			Class<? extends EntityLiving> aclass[] = new Class[set.size()];
			set.toArray(aclass);
			field.set(mobspawnerbase, aclass);
		}
		catch(Throwable throwable)
		{
			throwException("setMobs", "couldn't set biome mob field", throwable);
			return;
		}
	}

	static 
	{
		mobTypes = (new int[] { MONSTER = 0, CREATURE = 1, WATERCREATURE = 2 });
		fields = new Field[mobTypes.length];
	}
}
