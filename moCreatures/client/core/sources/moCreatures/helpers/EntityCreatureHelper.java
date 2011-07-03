package moCreatures.helpers;

import java.lang.reflect.Field;

import modManager.loader.ModLoader;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityCreature;

public final class EntityCreatureHelper
{
	private static final Field _field_playerToAttack = ModLoader.getPrivateField(EntityCreature.class, "d", "playerToAttack");

	public static void setPlayerToAttack(EntityCreature entityCreature, Entity player)
	{
		ModLoader.setPrivateValue(_field_playerToAttack, entityCreature, player);
	}
}
