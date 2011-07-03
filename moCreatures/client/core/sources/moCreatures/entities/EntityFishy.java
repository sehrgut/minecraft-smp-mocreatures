package moCreatures.entities;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;

import moCreatures.helpers.EntityHelper;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.PathEntity;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class EntityFishy extends EntityCustomWM
{

    public EntityFishy(World world)
    {
        super(world);
        texture = "/moCreatures/textures/fishy1.png";
        setSize(0.3F, 0.3F);
        health = 2;
        wingb = 0.0F;
        wingc = 0.0F;
        wingh = 1.0F;
        typeint = 0;
        typechosen = false;
        hasreproduced = false;
        b = 1.0F;
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
        float f2 = 0.91F;
        f2 = 0.5460001F;
        int i = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
        if(i > 0)
        {
            f2 = Block.blocksList[i].slipperiness * 0.91F;
        }
        float f3 = 0.162771F / (f2 * f2 * f2);
        moveFlying(f, f1, 0.1F * f3);
        f2 = 0.91F;
        f2 = 0.5460001F;
        int j = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
        if(j > 0)
        {
            f2 = Block.blocksList[j].slipperiness * 0.91F;
        }
        if(isOnLadder())
        {
            fallDistance = 0.0F;
            if(motionY < -0.14999999999999999D)
            {
                motionY = -0.14999999999999999D;
            }
        }
        moveEntity(motionX, motionY, motionZ);
        if(isCollidedHorizontally && isOnLadder())
        {
            motionY = 0.20000000000000001D;
        }
        motionX *= f2;
        motionZ *= f2;
        if(!handleWaterMovement())
        {
            motionY -= 0.080000000000000002D;
            motionY *= 0.98000001907348633D;
        } else
        {
            motionY -= 0.02D;
            motionY *= 0.5D;
        }
        field_705_Q = field_704_R;
        double d = posX - prevPosX;
        double d1 = posZ - prevPosZ;
        float f4 = MathHelper.sqrt_double(d * d + d1 * d1) * 4F;
        if(f4 > 1.0F)
        {
            f4 = 1.0F;
        }
        field_704_R += (f4 - field_704_R) * 0.4F;
        field_703_S += field_704_R;
    }

    @SuppressWarnings("rawtypes")
	public void onLivingUpdate()
    {
        if(onGround && inWater && !gettingOutOfWater())
        {
            motionY += 0.029999999999999999D;
        }
        if(health <= 0 || !inWater)
        {
            isJumping = false;
            moveStrafing = 0.0F;
            moveForward = 0.0F;
            randomYawVelocity = 0.0F;
        } else
        if(!field_9343_G)
        {
            updatePlayerActionState();
        }
        boolean flag = handleWaterMovement();
        boolean flag1 = gettingOutOfWater();
        if(isJumping && flag && !flag1)
        {
            motionY += 0.02D;
        }
        moveStrafing *= 0.98F;
        moveForward *= 0.98F;
        randomYawVelocity *= 0.9F;
        moveEntityWithHeading(moveStrafing, moveForward);
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(0.20000000298023221D, 0.0D, 0.20000000298023221D));
        if(list != null && list.size() > 0)
        {
            for(int i = 0; i < list.size(); i++)
            {
                Entity entity = (Entity)list.get(i);
                if(entity.canBePushed())
                {
                    entity.applyEntityCollision(this);
                }
            }

        }
        winge = wingb;
        wingd = wingc;
        wingc = (float)((double)wingc + (double)(onGround ? -1 : 4) * 0.29999999999999999D);
        if(wingc < 0.0F)
        {
            wingc = 0.0F;
        }
        if(wingc > 1.0F)
        {
            wingc = 1.0F;
        }
        if(!onGround && wingh < 1.0F)
        {
            wingh = 1.0F;
        }
        wingh = (float)((double)wingh * 0.90000000000000002D);
        wingb += wingh * 2.0F;
        if(!adult && rand.nextInt(100) == 0)
        {
            b += 0.02F;
            if(b >= 1.0F)
            {
                adult = true;
            }
        }
    }

    protected void fall(float f)
    {
        if(!inWater)
        {
            super.fall(f);
        }
    }

    protected void updatePlayerActionState()
    {
        hasAttacked = false;
        float f = 16F;
        if(playerToAttack == null)
        {
            playerToAttack = findPlayerToAttack();
            if(playerToAttack != null && EntityHelper.getInWater(playerToAttack))
            {
                a = worldObj.getPathToEntity(this, playerToAttack, f);
            }
        } else
        if(!playerToAttack.isEntityAlive() || !EntityHelper.getInWater(playerToAttack))
        {
            playerToAttack = null;
        } else
        {
            float f1 = playerToAttack.getDistanceToEntity(this);
            if(canEntityBeSeen(playerToAttack))
            {
                attackEntity(playerToAttack, f1);
            }
        }
        if(!hasAttacked && playerToAttack != null && EntityHelper.getInWater(playerToAttack) && (a == null || rand.nextInt(20) == 0))
        {
            a = worldObj.getPathToEntity(this, playerToAttack, f);
        } else
        if(a == null && rand.nextInt(80) == 0 || rand.nextInt(80) == 0)
        {
            boolean flag = false;
            int j = -1;
            int k = -1;
            int l = -1;
            float f2 = -99999F;
            for(int i1 = 0; i1 < 10; i1++)
            {
                int j1 = MathHelper.floor_double((posX + (double)rand.nextInt(13)) - 6D);
                int k1 = MathHelper.floor_double((posY + (double)rand.nextInt(7)) - 3D);
                int l1 = MathHelper.floor_double((posZ + (double)rand.nextInt(13)) - 6D);
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
            {
                a = worldObj.getEntityPathToXYZ(this, j, k, l, 10F);
            }
        }
        int i = MathHelper.floor_double(boundingBox.minY);
        boolean flag1 = handleWaterMovement();
        boolean flag2 = handleLavaMovement();
        rotationPitch = 0.0F;
        if(a == null || rand.nextInt(100) == 0)
        {
            super.updatePlayerActionState();
            a = null;
            return;
        }
        Vec3D vec3d = a.getPosition(this);
        for(double d = width * 2.0F; vec3d != null && vec3d.squareDistanceTo(posX, vec3d.yCoord, posZ) < d * d;)
        {
            a.incrementPathIndex();
            if(a.isFinished())
            {
                vec3d = null;
                a = null;
            } else
            {
                vec3d = a.getPosition(this);
            }
        }

        isJumping = false;
        if(vec3d != null)
        {
            double d1 = vec3d.xCoord - posX;
            double d2 = vec3d.zCoord - posZ;
            double d3 = vec3d.yCoord - (double)i;
            float f4 = (float)((Math.atan2(d2, d1) * 180D) / 3.1415927410125728D) - 90F;
            float f5 = f4 - rotationYaw;
            moveForward = moveSpeed;
            for(; f5 < -180F; f5 += 360F) { }
            for(; f5 >= 180F; f5 -= 360F) { }
            if(f5 > 30F)
            {
                f5 = 30F;
            }
            if(f5 < -30F)
            {
                f5 = -30F;
            }
            rotationYaw += f5;
            if(hasAttacked && playerToAttack != null)
            {
                double d4 = playerToAttack.posX - posX;
                double d5 = playerToAttack.posZ - posZ;
                float f6 = rotationYaw;
                rotationYaw = (float)((Math.atan2(d5, d4) * 180D) / 3.1415927410125728D) - 90F;
                float f7 = (((f6 - rotationYaw) + 90F) * 3.141593F) / 180F;
                moveStrafing = -MathHelper.sin(f7) * moveForward * 1.0F;
                moveForward = MathHelper.cos(f7) * moveForward * 1.0F;
            }
            if(d3 > 0.0D && playerToAttack != null && EntityHelper.getInWater(playerToAttack))
            {
                isJumping = true;
            }
        }
        if(playerToAttack != null)
        {
            faceEntity(playerToAttack, 30F, 30F);
        }
        if(isCollidedHorizontally)
        {
            isJumping = true;
        }
        if(rand.nextFloat() < 0.8F && (flag1 || flag2))
        {
            isJumping = true;
        }
    }

    protected Entity func_22007_w2()
    {
        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 24D);
        if(entityplayer != null && EntityHelper.getInWater(entityplayer))
        {
            return entityplayer;
        } else
        {
            return null;
        }
    }

    public boolean attackEntityFrom(Entity entity, int i)
    {
        if(super.attackEntityFrom(entity, i))
        {
            if(riddenByEntity == entity || ridingEntity == entity)
            {
                return true;
            }
            if(entity != this)
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
        if((double)f < 2D && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY)
        {
            attackTime = 20;
            entity.attackEntityFrom(this, 1);
        }
    }

    public void setType(int i)
    {
        typeint = i;
        typechosen = false;
        chooseType();
    }

    public void chooseType()
    {
        if(typeint == 0)
        {
            int i = rand.nextInt(100);
            if(i <= 15)
            {
                typeint = 1;
            } else
            if(i <= 30)
            {
                typeint = 2;
            } else
            if(i <= 45)
            {
                typeint = 3;
            } else
            if(i <= 60)
            {
                typeint = 4;
            } else
            if(i <= 75)
            {
                typeint = 5;
            } else
            if(i <= 90)
            {
                typeint = 6;
            } else
            {
                typeint = 2;
            }
        }
        if(!typechosen)
        {
            if(typeint == 1)
            {
                texture = "/moCreatures/textures/fishy1.png";
            } else
            if(typeint == 2)
            {
                texture = "/moCreatures/textures/fishy1.png";
            } else
            if(typeint == 3)
            {
                texture = "/moCreatures/textures/fishy1.png";
            } else
            if(typeint == 4)
            {
                texture = "/moCreatures/textures/fishy1.png";
            } else
            if(typeint == 5)
            {
                texture = "/moCreatures/textures/fishy1.png";
            } else
            if(typeint == 6)
            {
                texture = "/moCreatures/textures/fishy1.png";
            }
        }
        typechosen = true;
    }

//    private float b(float f, float f1, float f2)
//    {
//        float f3;
//        for(f3 = f1 - f; f3 < -180F; f3 += 360F) { }
//        for(; f3 >= 180F; f3 -= 360F) { }
//        if(f3 > f2)
//        {
//            f3 = f2;
//        }
//        if(f3 < -f2)
//        {
//            f3 = -f2;
//        }
//        return f + f3;
//    }

    protected int getDropItemId()
    {
        return Item.fishRaw.shiftedIndex;
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Tamed", tamed);
        nbttagcompound.setInteger("TypeInt", typeint);
        nbttagcompound.setBoolean("HasReproduced", hasreproduced);
        nbttagcompound.setBoolean("Adult", adult);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        tamed = nbttagcompound.getBoolean("Tamed");
        hasreproduced = nbttagcompound.getBoolean("HasReproduced");
        typeint = nbttagcompound.getInteger("TypeInt");
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

    private boolean hasreproduced;
    public int typeint;
    public boolean typechosen;
    public float wingb;
    public float wingc;
    public float wingd;
    public float winge;
    public float wingh;
    public float b;
    public boolean adult;
    public boolean tamed;
    private PathEntity a;
}
