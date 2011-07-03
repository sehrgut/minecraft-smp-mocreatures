package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class HorseSaddle extends Item
{
	public HorseSaddle(int i)
	{
		super(i);
		maxStackSize = 1;
		maxDamage = 64;
	}

	public void saddleEntity(ItemStack itemstack, EntityLiving entityliving)
	{
		if(entityliving instanceof EntityHorse)
		{
			EntityHorse entityhorse = (EntityHorse)entityliving;
			if(!entityhorse.rideable && entityhorse.adult)
			{
				entityhorse.rideable = true;
				itemstack.stackSize--;
			}
		}
	}

	public void hitEntity(ItemStack itemstack, EntityLiving entityliving)
	{
		saddleEntity(itemstack, entityliving);
	}
}
