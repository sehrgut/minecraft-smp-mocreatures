package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;

public class EntityFishy extends EntityCustomWM
{
	private boolean hasReproduced;
	public int typeInt;
	public boolean typeChosen;
	public float wingB;
	public float wingC;
	public float wingD;
	public float wingE;
	public float wingH;
	public float _b;
	public boolean adult;
	public boolean tamed;
	private PathEntity pathToEntity;

	public EntityFishy(World world)
	{
		super(world);
		texture = "/mob/fishy1.png";
		setSize(0.3F, 0.3F);
		health = 2;
		wingB = 0.0F;
		wingC = 0.0F;
		wingH = 1.0F;
		typeInt = 0;
		typeChosen = false;
		hasReproduced = false;
		_b = 1.0F;
		adult = false;
		tamed = false;
	}

	public boolean handleWaterMovement()
	{
		return worldObj.handleMaterialAcceleration(boundingBox, Material.water, this);
	}

	public boolean gettingOutOfWater()
	{
		int i = (int)posX;
		int j = (int)posY;
		int k = (int)posZ;
		int l = 1;
		l = worldObj.getBlockId(i, j + 1, k);
		return l == 0;
	}

	public void moveEntityWithHeading2(float f, float f1)
	{
		float f2 = 0.55F;
		int i = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
		if(i > 0)
			f2 = Block.blocksList[i].slipperiness * 0.91F;
		float f3 = 0.16F / (f2 * f2 * f2);
		moveFlying(f, f1, 0.1F * f3);
		f2 = 0.55F;
		int j = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
		if(j > 0)
			f2 = Block.blocksList[j].slipperiness * 0.91F;
		if(isOnLadder())
		{
			fallDistance = 0.0F;
			if(motionY < -0.15D)
				motionY = -0.15D;
		}
		moveEntity(motionX, motionY, motionZ);
		if(isCollidedHorizontally && isOnLadder())
			motionY = 0.2D;
		motionX *= f2;
		motionZ *= f2;
		if(!handleWaterMovement())
		{
			motionY -= 0.08D;
			motionY *= 0.98D;
		} else
		{
			motionY -= 0.02D;
			motionY *= 0.5D;
		}
		field_9142_bc = field_9141_bd;
		double d = posX - prevPosX;
		double d1 = posZ - prevPosZ;
		float f4 = MathHelper.sqrt_double(d * d + d1 * d1) * 4.0F;
		if(f4 > 1.0F)
			f4 = 1.0F;
		field_9141_bd += (f4 - field_9141_bd) * 0.4F;
		field_386_ba += field_9141_bd;
	}

	public void onLivingUpdate()
	{
		if(onGround && inWater && !gettingOutOfWater())
			motionY += 0.03D;
		if(health <= 0 || !inWater)
		{
			isJumping = false;
			moveStrafing = 0.0F;
			moveForward = 0.0F;
			randomYawVelocity = 0.0F;
		} else if(!field_9112_aN)
			updatePlayerActionState();
		boolean flag = handleWaterMovement();
		boolean flag1 = gettingOutOfWater();
		if(isJumping && flag && !flag1)
			motionY += 0.02D;
		moveStrafing *= 0.98F;
		moveForward *= 0.98F;
		randomYawVelocity *= 0.9F;
		moveEntityWithHeading(moveStrafing, moveForward);
		List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(0.2D, 0.0D, 0.2D));
		if(list != null && list.size() > 0)
		{
			for(int i = 0; i < list.size(); i++)
			{
				Entity entity = list.get(i);
				if(entity.canBePushed())
					entity.applyEntityCollision(this);
			}

		}
		wingE = wingB;
		wingD = wingC;
		wingC = (float)(wingC + (onGround ? -1 : 4) * 0.3D);
		if(wingC < 0.0F)
			wingC = 0.0F;
		if(wingC > 1.0F)
			wingC = 1.0F;
		if(!onGround && wingH < 1.0F)
			wingH = 1.0F;
		wingH = (float)(wingH * 0.9D);
		wingB += wingH * 2.0F;
		if(!adult && rand.nextInt(100) == 0)
		{
			_b += 0.02F;
			if(_b >= 1.0F)
				adult = true;
		}
	}

	protected void fall(float f)
	{
		if(!inWater)
			super.fall(f);
	}

	protected void updatePlayerActionState()
	{
		hasAttacked = false;
		float f = 16F;
		if(playerToAttack == null)
		{
			playerToAttack = findPlayerToAttack();
			if(playerToAttack != null && playerToAttack.inWater)
				pathToEntity = worldObj.getPathToEntity(this, playerToAttack, f);
		} else if(!playerToAttack.isEntityAlive() || !playerToAttack.inWater)
			playerToAttack = null;
		else
		{
			float f1 = playerToAttack.getDistanceToEntity(this);
			if(canEntityBeSeen(playerToAttack))
				attackEntity(playerToAttack, f1);
		}
		if(!hasAttacked && playerToAttack != null && playerToAttack.inWater && (pathToEntity == null || rand.nextInt(20) == 0))
			pathToEntity = worldObj.getPathToEntity(this, playerToAttack, f);
		else if(pathToEntity == null && rand.nextInt(80) == 0 || rand.nextInt(80) == 0)
		{
			boolean flag = false;
			int j = -1;
			int k = -1;
			int l = -1;
			float f2 = -99999F;
			for(int i1 = 0; i1 < 10; i1++)
			{
				int j1 = MathHelper.floor_double(posX + rand.nextInt(13) - 6.0D);
				int k1 = MathHelper.floor_double(posY + rand.nextInt(7) - 3.0D);
				int l1 = MathHelper.floor_double(posZ + rand.nextInt(13) - 6.0D);
				float f3 = getBlockPathWeight(j1, k1, l1);
				if(f3 > f2)
				{
					f2 = f3;
					j = j1;
					k = k1;
					l = l1;
					flag = true;
				}
			}

			if(flag)
				pathToEntity = worldObj.getEntityPathToXYZ(this, j, k, l, 10.0F);
		}
		int i = MathHelper.floor_double(boundingBox.minY);
		boolean flag1 = handleWaterMovement();
		boolean flag2 = handleLavaMovement();
		rotationPitch = 0.0F;
		if(pathToEntity == null || rand.nextInt(100) == 0)
		{
			super.updatePlayerActionState();
			pathToEntity = null;
			return;
		}
		Vec3D vec3d = pathToEntity.getPosition(this);
		for(double d = width * 2.0F; vec3d != null && vec3d.squareDistanceTo(posX, vec3d.yCoord, posZ) < d * d;)
		{
			pathToEntity.incrementPathIndex();
			if(pathToEntity.isFinished())
			{
				vec3d = null;
				pathToEntity = null;
			} else
				vec3d = pathToEntity.getPosition(this);
		}

		isJumping = false;
		if(vec3d != null)
		{
			double d1 = vec3d.xCoord - posX;
			double d2 = vec3d.zCoord - posZ;
			double d3 = vec3d.yCoord - i;
			float f4 = (float)(Math.atan2(d2, d1) * 180D / Math.PI) - 90.0F;
			float f5 = f4 - rotationYaw;
			moveForward = moveSpeed;
			while (f5 < -180.0F) f5 += 360.0F;
			while (f5 >= 180.0F) f5 -= 360.0F;
			if(f5 > 30.0F)
				f5 = 30.0F;
			if(f5 < -30.0F)
				f5 = -30.0F;
			rotationYaw += f5;
			if(hasAttacked && playerToAttack != null)
			{
				double d4 = playerToAttack.posX - posX;
				double d5 = playerToAttack.posZ - posZ;
				float f6 = rotationYaw;
				rotationYaw = (float)(Math.atan2(d5, d4) * 180.0D / Math.PI) - 90.0F;
				float f7 = (f6 - rotationYaw + 90.0F) * (float)Math.PI / 180.0F;
				moveStrafing = -MathHelper.sin(f7) * moveForward * 1.0F;
				moveForward = MathHelper.cos(f7) * moveForward * 1.0F;
			}
			if(d3 > 0.0D && playerToAttack != null && playerToAttack.inWater)
				isJumping = true;
		}
		if(playerToAttack != null)
			faceEntity(playerToAttack, 30.0F);
		if(isCollidedHorizontally)
			isJumping = true;
		if(rand.nextFloat() < 0.8F && (flag1 || flag2))
			isJumping = true;
	}

	protected Entity findPlayerToAttack2()
	{
		EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 24.0D);
		if(entityplayer != null && entityplayer.inWater)
			return entityplayer;
		return null;
	}

	public boolean attackEntityFrom(Entity entity, int i)
	{
		if(super.attackEntityFrom(entity, i))
		{
			if(riddenByEntity == entity || ridingEntity == entity)
				return true;
			if(entity != this)
				playerToAttack = entity;
			return true;
		}
		return false;
	}

	protected void attackEntity(Entity entity, float f)
	{
		if(f < 2.0D && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY)
		{
			attackTime = 20;
			entity.attackEntityFrom(this, 1);
		}
	}

	public void setType(int i)
	{
		typeInt = i;
		typeChosen = false;
		chooseType();
	}

	public void chooseType()
	{
		if(typeInt == 0)
		{
			int i = rand.nextInt(100);
			if(i <= 15)
				typeInt = 1;
			else if(i <= 30)
				typeInt = 2;
			else if(i <= 45)
				typeInt = 3;
			else if(i <= 60)
				typeInt = 4;
			else if(i <= 75)
				typeInt = 5;
			else if(i <= 90)
				typeInt = 6;
			else
				typeInt = 2;
		}
		if(!typeChosen)
		{
			switch (typeInt)
			{
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			default:
				texture = "/mob/fishy1.png";
				break;
			}
		}
		typeChosen = true;
	}

	protected int getDropItemId()
	{
		return Item.fishRaw.shiftedIndex;
	}

	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setBoolean("Tamed", tamed);
		nbttagcompound.setInteger("TypeInt", typeInt);
		nbttagcompound.setBoolean("HasReproduced", hasReproduced);
		nbttagcompound.setBoolean("Adult", adult);
	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);
		tamed = nbttagcompound.getBoolean("Tamed");
		hasReproduced = nbttagcompound.getBoolean("HasReproduced");
		typeInt = nbttagcompound.getInteger("TypeInt");
		adult = nbttagcompound.getBoolean("Adult");
	}

	protected String getLivingSound()
	{
		return null;
	}

	protected String getHurtSound()
	{
		return null;
	}

	protected String getDeathSound()
	{
		return null;
	}

	protected float getSoundVolume()
	{
		return 0.4F;
	}
}
