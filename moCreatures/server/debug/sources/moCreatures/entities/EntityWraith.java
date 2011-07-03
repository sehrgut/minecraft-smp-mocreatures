package moCreatures.entities;

import moCreatures.mod_MoCreatures;
import net.minecraft.src.Item;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityWraith extends EntityFlyerMob
{
	public static int counterEntity;

	public EntityWraith(World world)
	{
		super(world);
		texture = "/mob/wraith.png";
		setSize(1.5F, 1.5F);
		isImmuneToFire = false;
		attackStrength = 3;
		health = 10;
		moveSpeed = 1.3F;
	}

	protected String getLivingSound()
	{
		return "wraith";
	}

	protected String getHurtSound()
	{
		return "wraithhurt";
	}

	protected String getDeathSound()
	{
		return "wraithdying";
	}

	protected int getDropItemId()
	{
		return Item.gunpowder.shiftedIndex;
	}

	public void onLivingUpdate()
	{
		health = 0;
		setEntityDead();
		if(worldObj.difficultySetting == 1)
			attackStrength = 2;
		else if(worldObj.difficultySetting > 1)
			attackStrength = 3;
		if(worldObj.isDaytime())
		{
			float f = getEntityBrightness(1.0F);
			if(f > 0.5F && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) && rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F)
				fire = 300;
		}
		super.onLivingUpdate();
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
		if(worldObj.difficultySetting >= mod_MoCreatures.wraithsSpawnDifficulty && rand.nextInt(2) == 0 && super.getCanSpawnHere())
		{
			if(counterEntity >= mod_MoCreatures.maxWraiths)
				return false;
			counterEntity++;
			return true;
		}
		return false;
	}

	public boolean getCanSpawnHere2()
	{
		return super.getCanSpawnHere();
	}
}
