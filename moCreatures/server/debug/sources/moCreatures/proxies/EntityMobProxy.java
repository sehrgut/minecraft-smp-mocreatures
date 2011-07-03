package moCreatures.proxies;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityMob;
import net.minecraft.src.World;

public class EntityMobProxy extends EntityMob
{
	public EntityMobProxy(World world)
	{
		super(world);
	}
	
	public void setPlayerToAttack(Entity entity)
	{
		playerToAttack = entity;
	}
}
