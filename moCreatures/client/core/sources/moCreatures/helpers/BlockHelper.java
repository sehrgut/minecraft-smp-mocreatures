package moCreatures.helpers;

import java.lang.reflect.Field;

import modManager.loader.ModLoader;
import net.minecraft.src.Block;

public final class BlockHelper
{
	private static final Field _field_blockHardness = ModLoader.getPrivateField(Block.class, "bo", "blockHardness");
	
	public static float getBlockHardness(Block block)
	{
		return (Float)ModLoader.getPrivateValue(_field_blockHardness, block);
	}
}
