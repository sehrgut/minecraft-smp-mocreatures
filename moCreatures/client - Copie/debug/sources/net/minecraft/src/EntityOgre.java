package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class EntityOgre extends EntityMob
{

    public EntityOgre(World world)
    {
        super(world);
        attackStrength = 3;
        attackRange = 16D;
        ogreboolean = false;
        texture = "/mob/ogre.png";
        setSize(1.5F, 4F);
        health = 35;
        bogrefire = false;
        ogreattack = false;
        ogrehasenemy = false;
        destroyForce = 2.5F;
        isImmuneToFire = false;
        frequencyA = 30;
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("OgreBoolean", ogreboolean);
        nbttagcompound.setBoolean("OgreAttack", ogreattack);
        nbttagcompound.setInteger("CounterEntity", counterEntity);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        ogreboolean = nbttagcompound.getBoolean("OgreBoolean");
        ogreattack = nbttagcompound.getBoolean("OgreAttack");
        counterEntity = nbttagcompound.getInteger("CounterEntity");
    }

    protected String getLivingSound()
    {
        return "ogre";
    }

    protected String getHurtSound()
    {
        return "ogrehurt";
    }

    protected String getDeathSound()
    {
        return "ogredying";
    }

    protected int getDropItemId()
    {
        return Block.obsidian.blockID;
    }

    protected Entity findPlayerToAttack()
    {
        float f = getEntityBrightness(1.0F);
        if(f < 0.5F)
        {
            EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, attackRange);
            if(entityplayer != null && worldObj.difficultySetting > 0)
            {
                ogrehasenemy = true;
                return entityplayer;
            }
        }
        ogrehasenemy = false;
        return null;
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
                ogrehasenemy = true;
            }
            return true;
        } else
        {
            return false;
        }
    }

    public void onLivingUpdate()
    {
        findPlayerToAttack();
        if(ogrehasenemy && rand.nextInt(frequencyA) == 0)
        {
            ogreattack = true;
            attackTime = 15;
        }
        super.onLivingUpdate();
    }

    protected void attackEntity(Entity entity, float f)
    {
        if((double)f < 2.5D && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY && worldObj.difficultySetting > 0)
        {
            entity.attackEntityFrom(this, attackStrength);
        }
    }

    public void DestroyingOgre()
    {
        Destroyer.DestroyBlast(worldObj, this, posX, posY + 1.0D, posZ, destroyForce, bogrefire);
    }

    public void setEntityDead()
    {
        counterEntity--;
        super.setEntityDead();
    }

    public boolean getCanSpawnHere()
    {
        if(worldObj.difficultySetting >= mod_mocreatures.ogreSpawnDifficulty.get() + 1 && super.getCanSpawnHere())
        {
            if(counterEntity >= mod_mocreatures.maxOgreS.get())
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

    public boolean c2()
    {
        return super.getCanSpawnHere();
    }

    public int getMaxSpawnedInChunk()
    {
        return 3;
    }

    public static int counterEntity;
    public int frequencyA;
    public float destroyForce;
    public boolean ogrehasenemy;
    public boolean ogreattack;
    public boolean bogrefire;
    public boolean ogreboolean;
    protected double attackRange;
}
