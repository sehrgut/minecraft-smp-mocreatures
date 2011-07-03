package moCreatures.entities;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;

import moCreatures.helpers.EntityHelper;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityWaterMob;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.PathEntity;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class EntityCustomWM extends EntityWaterMob
{

    public EntityCustomWM(World world)
    {
        super(world);
        outOfWater = 0;
        tamed = true;
        temper = 50;
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

    public double speed()
    {
        return 1.5D;
    }

    public int tametemper()
    {
        return temper;
    }

    public boolean istamed()
    {
        return tamed;
    }

    public void setTame()
    {
        tamed = true;
    }

    public void moveEntityWithHeading(float f, float f1)
    {
        if(handleWaterMovement())
        {
            if(riddenByEntity != null && !istamed())
            {
                if(rand.nextInt(5) == 0 && !isJumping)
                {
                    motionY += 0.40000000000000002D;
                    isJumping = true;
                }
                if(rand.nextInt(10) == 0)
                {
                    motionX += rand.nextDouble() / 30D;
                    motionZ += rand.nextDouble() / 10D;
                }
                moveEntity(motionX, motionY, motionZ);
                if(rand.nextInt(50) == 0)
                {
                    worldObj.playSoundAtEntity(this, getUpsetSound(), 1.0F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
                    riddenByEntity.motionY += 0.90000000000000002D;
                    riddenByEntity.motionZ -= 0.29999999999999999D;
                    riddenByEntity = null;
                }
                if(onGround)
                {
                    isJumping = false;
                }
                if(rand.nextInt(tametemper() * 8) == 0)
                {
                    setTame();
                }
            }
            if(riddenByEntity != null && istamed())
            {
                boundingBox.maxY = riddenByEntity.boundingBox.maxY;
                motionX += riddenByEntity.motionX * speed();
                motionZ += riddenByEntity.motionZ * speed();
                if(motionY != 0.0D)
                {
                    motionY = 0.0D;
                }
                moveEntity(motionX, motionY, motionZ);
                rotationPitch = riddenByEntity.rotationPitch * 0.5F;
                if(rotationYaw > riddenByEntity.rotationYaw)
                {
                    float f2 = rotationYaw - riddenByEntity.rotationYaw;
                    if(f2 > 25F)
                    {
                        rotationYaw -= f2 / 10F;
                    }
                } else
                {
                    float f3 = riddenByEntity.rotationYaw - rotationYaw;
                    if(f3 > 25F)
                    {
                        rotationYaw += f3 / 10F;
                    }
                }
                setRotation(rotationYaw, rotationPitch);
            }
            moveFlying(f, f1, 0.02F);
            moveEntity(motionX, motionY, motionZ);
            if(riddenByEntity == null)
            {
                motionX *= 0.80000001192092896D;
                motionZ *= 0.80000001192092896D;
            }
        } else
        if(handleLavaMovement())
        {
            double d = posY;
            moveFlying(f, f1, 0.02F);
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.5D;
            motionY *= 0.5D;
            motionZ *= 0.5D;
            motionY -= 0.02D;
            if(isCollidedHorizontally && isOffsetPositionInLiquid(motionX, ((motionY + 0.60000002384185791D) - posY) + d, motionZ))
            {
                motionY = 0.30000001192092901D;
            }
        }
        float f4 = 0.91F;
        f4 = 0.5460001F;
        int i = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
        if(i > 0)
        {
            f4 = Block.blocksList[i].slipperiness * 0.91F;
        }
        float f5 = 0.162771F / (f4 * f4 * f4);
        moveFlying(f, f1, 0.1F * f5);
        f4 = 0.91F;
        f4 = 0.5460001F;
        int j = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
        if(j > 0)
        {
            f4 = Block.blocksList[j].slipperiness * 0.91F;
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
        motionX *= f4;
        motionZ *= f4;
        if(!handleWaterMovement())
        {
            motionY -= 0.080000000000000002D;
            motionY *= 0.98000001907348633D;
        } else
        if(riddenByEntity == null)
        {
            motionY -= 0.02D;
            motionY *= 0.5D;
        }
        field_705_Q = field_704_R;
        double d1 = posX - prevPosX;
        double d2 = posZ - prevPosZ;
        float f6 = MathHelper.sqrt_double(d1 * d1 + d2 * d2) * 4F;
        if(f6 > 1.0F)
        {
            f6 = 1.0F;
        }
        field_704_R += (f6 - field_704_R) * 0.4F;
        field_703_S += field_704_R;
    }

    protected void updatePlayerActionState()
    {
        if(riddenByEntity != null && tamed)
        {
            return;
        }
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

    protected void fall(float f)
    {
        if(!inWater)
        {
            super.fall(f);
        }
    }

    @SuppressWarnings("rawtypes")
	public EntityItem getClosestFish(Entity entity, double d)
    {
        double d1 = -1D;
        EntityItem entityitem = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(d, d, d));
        for(int i = 0; i < list.size(); i++)
        {
            Entity entity1 = (Entity)list.get(i);
            if(!(entity1 instanceof EntityItem))
            {
                continue;
            }
            EntityItem entityitem1 = (EntityItem)entity1;
            if(entityitem1.item.itemID != Item.fishRaw.shiftedIndex || !EntityHelper.getInWater(entityitem1))
            {
                continue;
            }
            double d2 = entityitem1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if((d < 0.0D || d2 < d * d) && (d1 == -1D || d2 < d1))
            {
                d1 = d2;
                entityitem = entityitem1;
            }
        }

        return entityitem;
    }

    @SuppressWarnings("rawtypes")
	public void onLivingUpdate()
    {
        if(onGround && inWater && !gettingOutOfWater())
        {
            motionY += 0.029999999999999999D;
        }
        if(!inWater && rand.nextInt(20) == 0 && riddenByEntity == null)
        {
            outOfWater++;
            posY += outOfWater / 30;
            attackEntityFrom(this, 1);
        }
        if(health <= 0 || !inWater && riddenByEntity == null)
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
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
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

    protected String getUpsetSound()
    {
        return null;
    }

    private PathEntity a;
    private int outOfWater;
    private boolean tamed;
    private int temper;
}
