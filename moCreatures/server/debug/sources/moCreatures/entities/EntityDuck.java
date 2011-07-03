package moCreatures.entities;

import moCreatures.mod_MoCreatures;
import net.minecraft.src.EntityChicken;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityDuck extends EntityChicken
{
	public static int counterEntity;

	public EntityDuck(World world)
	{
		super(world);
		texture = "/mob/duck.png";
		setSize(0.3F, 0.4F);
		health = 4;
		timeUntilNextEgg = rand.nextInt(6000) + 6000;
	}

	protected String getLivingSound()
	{
		return "duck";
	}

	protected String getHurtSound()
	{
		return "duckhurt";
	}

	protected String getDeathSound()
	{
		return "duckhurt";
	}

	public void setEntityDead()
	{
		counterEntity--;
		super.setEntityDead();
	}

	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setInteger("CounterEntity", counterEntity);
	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);
		counterEntity = nbttagcompound.getInteger("CounterEntity");
	}

	public boolean getCanSpawnHere()
	{
		if(super.getCanSpawnHere())
		{
			if(counterEntity >= mod_MoCreatures.maxDucks)
				return false;
			counterEntity++;
			return true;
		} 
		return false;
	}
}