package moCreatures.helpers;

import java.lang.reflect.Field;

import modManager.loader.ModLoader;
import net.minecraft.src.EntityLiving;

public final class EntityLivingHelper
{
	private static final Field _field_texture = ModLoader.getPrivateField(EntityLiving.class, "O", "texture");
	private static final Field _field_isJumping = ModLoader.getPrivateField(EntityLiving.class, "az", "isJumping");
	
	public static String getTexture(EntityLiving entityLiving)
	{
		return (String)ModLoader.getPrivateValue(_field_texture, entityLiving);
	}
	
	public static boolean getIsJumping(EntityLiving entityLiving)
	{
		return (Boolean)ModLoader.getPrivateValue(_field_isJumping, entityLiving);
	}
	
	public static void setTexture(EntityLiving entityLiving, String texture)
	{
		ModLoader.setPrivateValue(_field_texture, entityLiving, texture);
	}
}
