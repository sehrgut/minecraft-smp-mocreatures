package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;
import java.util.Random;

public class EntityLionK extends EntityAnimal
{

    public EntityLionK(World world)
    {
        super(world);
        lionboolean = false;
        malelion = false;
        texture = "/mob/lionf.png";
        setSize(0.9F, 1.3F);
        health = 25;
        force = 1;
        attackRange = 1.0D;
        liongender = rand.nextInt(3);
        if(liongender == 0)
        {
            malelion = true;
        }
    }

    public void onLivingUpdate()
    {
        if(worldObj.difficultySetting == 1)
        {
            attackRange = 6D;
            if(malelion)
            {
                attackRange = 4D;
            }
            force = 3;
        } else
        if(worldObj.difficultySetting > 1)
        {
            attackRange = 8D;
            if(malelion)
            {
                attackRange = 6D;
            }
            force = 5;
        }
        super.onLivingUpdate();
    }

    protected Entity findPlayerToAttack()
    {
        if(worldObj.difficultySetting > 0)
        {
            EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, attackRange);
            if(entityplayer != null)
            {
                if(!malelion)
                {
                    return entityplayer;
                }
                if(rand.nextInt(30) == 0)
                {
                    return entityplayer;
                }
            }
            if(rand.nextInt(80) == 0)
            {
                EntityLiving entityliving = getClosestTarget(this, 10D);
                return entityliving;
            }
        }
        return null;
    }

    public EntityLiving getClosestTarget(Entity entity, double d)
    {
        double d1 = -1D;
        EntityLiving entityliving = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(d, d, d));
        for(int i = 0; i < list.size(); i++)
        {
            Entity entity1 = (Entity)list.get(i);
            if(!(entity1 instanceof EntityLiving) || entity1 == entity || entity1 == entity.riddenByEntity || entity1 == entity.ridingEntity || (entity1 instanceof EntityPlayer) || (entity1 instanceof EntityMob) || (entity1 instanceof EntityHorse) && !mod_mocreatures.attackhorses.get())
            {
                continue;
            }
            if(entity1 instanceof EntityLionK)
            {
                EntityLionK entitylionk = (EntityLionK)entity1;
                if(!entitylionk.malelion || !malelion)
                {
                    continue;
                }
            }
            double d2 = entity1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if((d < 0.0D || d2 < d * d) && (d1 == -1D || d2 < d1) && ((EntityLiving)entity1).canEntityBeSeen(entity))
            {
                d1 = d2;
                entityliving = (EntityLiving)entity1;
            }
        }

        return entityliving;
    }

    public boolean attackEntityFrom(Entity entity, int i)
    {
        if(super.attackEntityFrom(entity, i))
        {
            if(riddenByEntity == entity || ridingEntity == entity)
            {
                return true;
            }
            if(entity != this && worldObj.difficultySetting > 0)
            {
                playerToAttack = entity;
            }
            return true;
        } else
        {
            return false;
        }
    }

    protected void attackEntity(Entity entity, float f)
    {
        if((double)f < 2.5D && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY)
        {
            attackTime = 20;
            entity.attackEntityFrom(this, force);
            if(!(entity instanceof EntityPlayer))
            {
                destroyDrops(this, 3D);
            }
        }
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("LionBoolean", lionboolean);
        nbttagcompound.setBoolean("MaleLion", malelion);
        nbttagcompound.setInteger("CounterEntity", counterEntity);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        lionboolean = nbttagcompound.getBoolean("LionBoolean");
        malelion = nbttagcompound.getBoolean("MaleLion");
        counterEntity = nbttagcompound.getInteger("CounterEntity");
    }

    protected String getLivingSound()
    {
        return "liongrunt";
    }

    protected String getHurtSound()
    {
        return "lionhurt";
    }

    protected String getDeathSound()
    {
        return "liondeath";
    }

    protected int getDropItemId()
    {
        return Item.leather.shiftedIndex;
    }

    public void destroyDrops(Entity entity, double d)
    {
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.expand(d, d, d));
        for(int i = 0; i < list.size(); i++)
        {
            Entity entity1 = (Entity)list.get(i);
            if(!(entity1 instanceof EntityItem))
            {
                continue;
            }
            EntityItem entityitem = (EntityItem)entity1;
            if(entityitem != null && entityitem.age < 50 && mod_mocreatures.destroyitems.get())
            {
                entityitem.setEntityDead();
            }
        }

    }

    public int getMaxSpawnedInChunk()
    {
        return 3;
    }

    public void setEntityDead()
    {
        counterEntity--;
        super.setEntityDead();
    }

    public boolean getCanSpawnHere()
    {
        if(super.getCanSpawnHere())
        {
            if(counterEntity >= mod_mocreatures.maxLionsS.get())
            {
                return false;
            } else
            {
                counterEntity++;
                return true;
            }
        } else
        {
            return false;
        }
    }

    public boolean malelion;
    public boolean lionboolean;
    protected int force;
    protected double attackRange;
    public int liongender;
    public static int counterEntity;
}
