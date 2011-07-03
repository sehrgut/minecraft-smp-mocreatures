package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;

public class EntityBunny extends EntityAnimals
{
	public boolean hasReproduced;
	public float _b;
	public float _c;
	public float _d;
	public float _e;
	public float _f;
	public float _g;
	public boolean _h;
	public int _i;
	public int _j;
	public static int counterEntity;

	public EntityBunny(World world)
	{
		super(world);
		hasReproduced = false;
		_b = 0.0F;
		_c = 0.0F;
		_g = 1.0F;
		moveSpeed = 1.5F;
		texture = "/mob/bunny.png";
		yOffset = -0.16F;
		setSize(0.4F, 0.4F);
		health = 4;
		_j = rand.nextInt(64);
		_i = 0;
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		_f = _b;
		_d = _c;
		_c = (float)(_c + (onGround ? -1 : 4) * 0.3D);
		if(_c < 0.0F)
			_c = 0.0F;
		if(_c > 1.0F)
			_c = 1.0F;
		if(!onGround && _g < 1.0F)
			_g = 1.0F;
		_g = (float)(_g * 0.9D);
		_b += _g * 2.0F;
	}

	public void onUpdate()
	{
		if(_j < 1023)
			_j++;
		else if(_i < 127)
			_i++;
		else
		{
			int i = 0;
			List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(16D, 16D, 16D));
			for(int j = 0; j < list.size(); j++)
			{
				Entity entity = list.get(j);
				if(entity instanceof EntityBunny)
					i++;
			}

			if(i > 12)
			{
				proceed();
				return;
			}
			List<Entity> list1 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(1.0D, 1.0D, 1.0D));
			boolean flag = false;
			for(int k = 0; k < list.size(); k++)
			{
				Entity entity1 = list1.get(k);
				if(!(entity1 instanceof EntityBunny) || entity1 == this)
					continue;
				EntityBunny entitybunny = (EntityBunny)entity1;
				if(entitybunny.worldObj != null || entitybunny._j < 1023)
					continue;
				EntityBunny entitybunny1 = new EntityBunny(worldObj);
				entitybunny1.setPosition(posX, posY, posZ);
				worldObj.entityJoinedWorld(entitybunny1);
				worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
				proceed();
				entitybunny.proceed();
				flag = true;
				break;
			}

			if(!flag)
				i = rand.nextInt(16);
		}
		super.onUpdate();
	}

	protected void fall(float f)
	{
	}

	protected void updatePlayerActionState()
	{
		if(onGround && (motionX > 0.05D || motionZ > 0.05D || motionX < -0.05D || motionZ < -0.05D))
			motionY = 0.45D;
		if(!_h)
			super.updatePlayerActionState();
		else if(onGround)
		{
			_h = false;
			worldObj.playSoundAtEntity(this, "rabbitland", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
			List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(12D, 12D, 12D));
			for(int i = 0; i < list.size(); i++)
			{
				Entity entity = list.get(i);
				if(entity instanceof EntityMobs)
				{
					EntityMobs entitymobs = (EntityMobs)entity;
					entitymobs.playerToAttack = this;
				}
			}
		}
	}

	public boolean interact(EntityPlayer entityplayer)
	{
		rotationYaw = entityplayer.rotationYaw;
		mountEntity(entityplayer);
		if(ridingEntity == null)
			_h = true;
		else
			worldObj.playSoundAtEntity(this, "rabbitlift", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
		motionX = entityplayer.motionX * 5.0D;
		motionY = entityplayer.motionY / 2.0D + 0.5D;
		motionZ = entityplayer.motionZ * 5.0D;
		return true;
	}

	public double getYOffset()
	{
		if(ridingEntity instanceof EntityPlayer)
			return yOffset - 1.15F;
		return yOffset;
	}

	protected String getLivingSound()
	{
		return null;
	}

	public void proceed()
	{
		_i = 0;
		_j = rand.nextInt(64);
	}

	protected String getHurtSound()
	{
		return "rabbithurt";
	}

	public void knockBack(Entity entity, int i, double d, double d1)
	{
		super.knockBack(entity, i, d, d1);
	}

	protected String getDeathSound()
	{
		return "rabbitdeath";
	}

	public boolean maxNumberReached()
	{
		int i = 0;
		for(int j = 0; j < worldObj.loadedEntityList.size(); j++)
		{
			Entity entity = (Entity)worldObj.loadedEntityList.get(j);
			if(entity instanceof EntityBunny)
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
		if(super.getCanSpawnHere())
		{
			if(counterEntity >= mod_mocreatures.maxBunnies)

				return false;
			counterEntity++;
			return true;
		}
		return false;
	}
}
