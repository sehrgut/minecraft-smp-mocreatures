package moCreatures.proxies;

import net.minecraft.src.Block;
import net.minecraft.src.Material;

public class BlockProxy extends Block
{
	protected BlockProxy(int i, int j, Material material)
	{
		super(i, j, material);
	}

	protected BlockProxy(int i, Material material)
	{
		super(i, material);
	}
	
	public static float getBlockHardness(int blockId)
	{
		return ((BlockProxy)blocksList[blockId]).blockHardness;
	}
}
