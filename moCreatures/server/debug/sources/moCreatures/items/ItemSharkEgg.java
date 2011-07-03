package moCreatures.items;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import moCreatures.entities.EntitySharkEgg;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class ItemSharkEgg extends Item
{
	public ItemSharkEgg(int i)
	{
		super(i);
		maxStackSize = 16;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		itemstack.stackSize--;
		if(!world.singleplayerWorld)
		{
			EntitySharkEgg entitysharkegg = new EntitySharkEgg(world);
			entitysharkegg.setPosition(entityplayer.posX, entityplayer.posY, entityplayer.posZ);
			world.entityJoinedWorld(entitysharkegg);
			entitysharkegg.motionY += world.rand.nextFloat() * 0.05F;
			entitysharkegg.motionX += (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F;
			entitysharkegg.motionZ += (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F;
		}
		return itemstack;
	}
}
