package moCreatures.proxies;

import net.minecraft.src.EntityItem;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class EntityItemProxy extends EntityItem
{
	public EntityItemProxy(World world)
	{
		super(world);
	}
	
	public EntityItemProxy(World world, double d, double d1, double d2, ItemStack itemstack)
	{
		super(world, d, d1, d2, itemstack);
	}

	public boolean inWater()
	{
		return inWater;
	}
}
