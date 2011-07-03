package moCreatures.entities;

import net.minecraft.src.Block;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import moCreatures.mod_mocreatures;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntityFireOgre extends EntityOgre
{

    public EntityFireOgre(World world)
    {
        super(world);
        attackStrength = 3;
        attackRange = 16D;
        ogreboolean = false;
        texture = "/moCreatures/textures/fireogre.png";
        setSize(1.5F, 4F);
        health = 35;
        bogrefire = true;
        destroyForce = 2.0F;
        isImmuneToFire = true;
        frequencyA = 35;
    }

    protected int getDropItemId()
    {
        return Block.fire.blockID;
    }

    public void onLivingUpdate()
    {
        findPlayerToAttack();
        if(ogrehasenemy && rand.nextInt(frequencyA) == 0)
        {
            ogreattack = true;
            attackTime = 15;
        }
        if(worldObj.isDaytime())
        {
            float f = getEntityBrightness(1.0F);
            if(f > 0.5F && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) && rand.nextFloat() * 30F < (f - 0.4F) * 2.0F)
            {
                health -= 5;
            }
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
        if(worldObj.difficultySetting >= mod_mocreatures.fogreSpawnDifficulty.get() + 1 && super.c2())
        {
            if(counterEntity >= mod_mocreatures.maxFOgreS.get())
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

    public int getMaxSpawnedInChunk()
    {
        return 2;
    }

    public static int counterEntity;
}
