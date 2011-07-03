package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntityCaveOgre extends EntityOgre
{
	public static int counterEntity;

	public EntityCaveOgre(World world)
	{
		super(world);
		attackStrength = 3;
		attackRange = 16.0D;
		ogreBoolean = false;
		texture = "/mob/caveogre.png";
		setSize(1.5F, 4.0F);
		health = 50;
		fireOgre = false;
		destroyForce = 3.0F;
		isImmuneToFire = false;
		frequencyA = 35;
	}

	public void onLivingUpdate()
	{
		findPlayerToAttack();
		if(ogreHasEnemy && rand.nextInt(frequencyA) == 0)
		{
			ogreAttack = true;
			attackTime = 15;
		}
		if(worldObj.isDaytime())
		{
			float f = getEntityBrightness(1.0F);
			if(f > 0.5F && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) && rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F)
				health -= 5;
		}
		super.onLivingUpdate();
	}

	public boolean maxNumberReached()
	{
		int i = 0;
		for(int j = 0; j < worldObj.loadedEntityList.size(); j++)
		{
			Entity entity = (Entity)worldObj.loadedEntityList.get(j);
			if(entity instanceof EntityCaveOgre)
				i++;
		}

		return false;
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
		if(worldObj.difficultySetting >= mod_mocreatures.caveOgresSpawnDifficulty && !worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) && posY < 50.0D && super.getCanSpawnHere2())
		{
			if(counterEntity >= mod_mocreatures.maxCaveOgres)
				return false;
			counterEntity++;
			return true;
		}
		return false;
	}

	protected int getDropItemId()
	{
		return Item.diamond.shiftedIndex;
	}
}