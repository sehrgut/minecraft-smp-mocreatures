package moCreatures.entities;

import moCreatures.mod_MoCreatures;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityMob;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityWerewolf extends EntityMob
{
	public boolean wereBoolean;
	public boolean humanForm;
	private boolean transforming;
	private int counter;
	public boolean hunched;
	public boolean isUndead;
	public static int counterEntity;

	public EntityWerewolf(World world)
	{
		super(world);
		wereBoolean = false;
		texture = "/mob/werehuman.png";
		setSize(0.9F, 1.3F);
		humanForm = true;
		health = 15;
		transforming = false;
		counter = 0;
		hunched = false;
		isUndead = true;
	}

	protected Entity findPlayerToAttack()
	{
		if(humanForm)
			return null;
		EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 16.0D);
		if(entityplayer != null && canEntityBeSeen(entityplayer))
			return entityplayer;
		return null;
	}

	protected void updatePlayerActionState()
	{
		if(!transforming)
			super.updatePlayerActionState();
	}

	protected void attackEntity(Entity entity, float f)
	{
		if(humanForm)
		{
			playerToAttack = null;
			return;
		}
		if(f > 2.0F && f < 6.0F && rand.nextInt(15) == 0)
		{
			if(onGround)
			{
				hunched = true;
				double d = entity.posX - posX;
				double d1 = entity.posZ - posZ;
				float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
				motionX = (d / (double)f1) * 0.5D * 0.8D + motionX * 0.2D;
				motionZ = (d1 / (double)f1) * 0.5D * 0.8D + motionZ * 0.2D;
				motionY = 0.4D;
			}
		} else
			super.attackEntity(entity, f);
	}

	public boolean attackEntityFrom(Entity entity, int i)
	{
		if(!humanForm && entity != null && (entity instanceof EntityPlayer))
		{
			EntityPlayer entityplayer = (EntityPlayer)entity;
			ItemStack itemstack = entityplayer.getCurrentEquippedItem();
			if(itemstack != null)
			{
				i = 1;
				if(itemstack.itemID == Item.hoeGold.shiftedIndex)
					i = 6;
				if(itemstack.itemID == Item.shovelGold.shiftedIndex)
					i = 7;
				if(itemstack.itemID == Item.pickaxeGold.shiftedIndex)
					i = 8;
				if(itemstack.itemID == Item.axeGold.shiftedIndex)
					i = 9;
				if(itemstack.itemID == Item.swordGold.shiftedIndex)
					i = 10;
			}
		}
		return super.attackEntityFrom(entity, i);
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if(isNight() && humanForm || !isNight() && !humanForm && rand.nextInt(250) == 0)
			transforming = true;
		if(humanForm && playerToAttack != null)
			playerToAttack = null;
		if(playerToAttack != null && !humanForm && playerToAttack.posX - posX > 3.0D && playerToAttack.posZ - posZ > 3.0D)
			hunched = true;
		if(hunched && rand.nextInt(50) == 0)
			hunched = false;
		if(transforming && rand.nextInt(3) == 0)
		{
			counter++;
			if(counter % 2 == 0)
			{
				posX += 0.3D;
				posY += counter / 30;
				attackEntityFrom(this, 1);
			}
			if(counter % 2 != 0)
				posX -= 0.3D;
			if(counter == 10)
				worldObj.playSoundAtEntity(this, "weretransform", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
			if(counter > 30)
			{
				transform();
				counter = 0;
				transforming = false;
			}
		}
		if(rand.nextInt(300) == 0)
		{
			age -= 100 * worldObj.difficultySetting;
			if(age < 0)
				age = 0;
		}
	}

	public boolean isNight()
	{
		return !worldObj.isDaytime();
	}

	public void moveEntityWithHeading(float f, float f1)
	{
		if(!humanForm && onGround)
		{
			motionX *= 1.2D;
			motionZ *= 1.2D;
		}
		super.moveEntityWithHeading(f, f1);
	}

	private void transform()
	{
		if(deathTime > 0)
			return;
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY) + 1;
		int k = MathHelper.floor_double(posZ);
		float f = 0.1F;
		for(int l = 0; l < 30; l++)
		{
			double d = i + worldObj.rand.nextFloat();
			double d1 = j + worldObj.rand.nextFloat();
			double d2 = k + worldObj.rand.nextFloat();
			double d3 = d - i;
			double d4 = d1 - j;
			double d5 = d2 - k;
			double d6 = MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
			d3 /= d6;
			d4 /= d6;
			d5 /= d6;
			double d7 = 0.5D / (d6 / f + 0.1D);
			d7 *= worldObj.rand.nextFloat() * worldObj.rand.nextFloat() + 0.3F;
			d3 *= d7;
			d4 *= d7;
			d5 *= d7;
			worldObj.spawnParticle("explode", (d + i * 1.0D) / 2.0D, (d1 + j * 1.0D) / 2.0D, (d2 + k * 1.0D) / 2D, d3, d4, d5);
		}

		if(humanForm)
		{
			humanForm = false;
			health = 40;
			transforming = false;
		} else
		{
			humanForm = true;
			health = 15;
			transforming = false;
		}
	}

	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setBoolean("HumanForm", humanForm);
		nbttagcompound.setInteger("CounterEntity", counterEntity);
	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);
		humanForm = nbttagcompound.getBoolean("HumanForm");
		counterEntity = nbttagcompound.getInteger("CounterEntity");
	}

	protected String getLivingSound()
	{
		if(humanForm)
			return "werehumangrunt";
		return "werewolfgrunt";
	}

	protected String getHurtSound()
	{
		if(humanForm)
			return "werehumanhurt";
		return "werewolfhurt";
	}

	protected String getDeathSound()
	{
		if(humanForm)
			return "werehumandying";
		return "werewolfdying";
	}

	protected int getDropItemId()
	{
		int i = rand.nextInt(12);
		if(humanForm)
		{
			switch(i)
			{
			case 0: // '\0'
				return Item.shovelWood.shiftedIndex;

			case 1: // '\001'
				return Item.axeWood.shiftedIndex;

			case 2: // '\002'
				return Item.swordWood.shiftedIndex;

			case 3: // '\003'
				return Item.hoeWood.shiftedIndex;

			case 4: // '\004'
				return Item.pickaxeWood.shiftedIndex;
			default:
				return Item.stick.shiftedIndex;
			}
		}
		switch(i)
		{
		case 0: // '\0'
			return Item.hoeSteel.shiftedIndex;

		case 1: // '\001'
			return Item.shovelSteel.shiftedIndex;

		case 2: // '\002'
			return Item.axeSteel.shiftedIndex;

		case 3: // '\003'
			return Item.pickaxeSteel.shiftedIndex;

		case 4: // '\004'
			return Item.swordSteel.shiftedIndex;

		case 5: // '\005'
			return Item.hoeStone.shiftedIndex;

		case 6: // '\006'
			return Item.shovelStone.shiftedIndex;

		case 7: // '\007'
			return Item.axeStone.shiftedIndex;

		case 8: // '\b'
			return Item.pickaxeStone.shiftedIndex;

		case 9: // '\t'
			return Item.swordStone.shiftedIndex;
		default:
			return Item.appleGold.shiftedIndex;
		}
	}

	public void onDeath(Entity entity)
	{
		if(scoreValue > 0 && entity != null)
			entity.addToPlayerScore(this, scoreValue);
        unused_flag = true;
		if(!worldObj.singleplayerWorld)
		{
			for(int i = 0; i < 2; i++)
			{
				int j = getDropItemId();
				if(j > 0)
					dropItem(j, 1);
			}

		}
		//worldObj.getAnaglyphs(this, 3F);
	}

	public int getMaxSpawnedInChunk()
	{
		return 1;
	}

	public void setEntityDead()
	{
		counterEntity--;
		super.setEntityDead();
	}

	public boolean getCanSpawnHere()
	{
		if(worldObj.difficultySetting >= mod_MoCreatures.werewolvesSpawnDifficulty && rand.nextInt(2) == 0 && super.getCanSpawnHere())
		{
			if(counterEntity >= mod_MoCreatures.maxWerewolves)
				return false;
			counterEntity++;
			return true;
		}
		return false;
	}
}
