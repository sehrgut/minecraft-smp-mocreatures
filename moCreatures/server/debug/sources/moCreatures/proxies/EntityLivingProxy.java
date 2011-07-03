package moCreatures.proxies;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;

public class EntityLivingProxy extends EntityLiving
{
	public EntityLivingProxy(World world)
	{
		super(world);
	}

	public boolean inWater()
	{
		return inWater;
	}
}
