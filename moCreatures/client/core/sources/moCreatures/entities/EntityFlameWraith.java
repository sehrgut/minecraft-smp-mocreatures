package moCreatures.entities;

import net.minecraft.src.Entity;
import net.minecraft.src.Item;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import moCreatures.mod_mocreatures;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntityFlameWraith extends EntityWraith
{
    public EntityFlameWraith(World world)
    {
        super(world);
        texture = "/moCreatures/textures/flamewraith.png";
        setSize(1.5F, 1.5F);
        isImmuneToFire = true;
        burningTime = 30;
        health = 15;
        moveSpeed = 1.1F;
    }

    protected int getDropItemId()
    {
        return Item.redstone.shiftedIndex;
    }

    public void onLivingUpdate()
    {
        if(rand.nextInt(40) == 0)
        {
            fire = 2;
        }
        if(worldObj.isDaytime())
        {
            float f = getEntityBrightness(1.0F);
            if(f > 0.5F && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) && rand.nextFloat() * 30F < (f - 0.4F) * 2.0F)
            {
                health -= 2;
            }
        }
        super.onLivingUpdate();
    }

    protected void attackEntity(Entity entity, float f)
    {
        if((double)f < 2.5D && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY)
        {
            attackTime = 20;
            entity.attackEntityFrom(this, 2);
            entity.fire = burningTime;
        }
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
        if(worldObj.difficultySetting >= mod_mocreatures.fwraithSpawnDifficulty.get() + 1 && rand.nextInt(2) == 0 && super.c2())
        {
            if(counterEntity >= mod_mocreatures.maxFWraithS.get())
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

    protected int burningTime;
    public static int counterEntity;
}
