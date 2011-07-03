package moCreatures.proxies;

import net.minecraft.src.EntityWaterMob;
import net.minecraft.src.World;

public class EntityWaterMobProxy extends EntityWaterMob
{
	public EntityWaterMobProxy(World world)
	{
		super(world);
	}

	public boolean playerToAttackInWater()
	{
		return ((EntityProxy)playerToAttack).inWater();
	}
}
