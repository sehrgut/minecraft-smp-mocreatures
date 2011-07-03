package moCreatures.proxies;

import net.minecraft.src.Entity;
import net.minecraft.src.World;

public abstract class EntityProxy extends Entity
{
	public EntityProxy(World world)
	{
		super(world);
	}

	public boolean inWater()
	{
		return inWater;
	}
}
