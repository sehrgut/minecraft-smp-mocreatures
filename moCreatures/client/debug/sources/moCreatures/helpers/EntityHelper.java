package moCreatures.helpers;

import java.lang.reflect.Field;

import net.minecraft.src.Entity;
import modManager.loader.ModLoader;

public final class EntityHelper
{
	private static final Field _field_inWater = ModLoader.getPrivateField(Entity.class, "bx", "inWater");
	
	public static boolean getInWater(Entity entity)
	{
		return (Boolean)ModLoader.getPrivateValue(_field_inWater, entity);
	}
}
