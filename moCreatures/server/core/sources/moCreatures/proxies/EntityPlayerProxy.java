package moCreatures.proxies;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public abstract class EntityPlayerProxy extends EntityPlayer
{
	public EntityPlayerProxy(World world)
	{
		super(world);
	}

	public boolean inWater()
	{
		return inWater;
	}

	public boolean isJumping()
	{
		return isJumping;
	}
}
