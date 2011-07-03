package moCreatures.helpers;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class AnimalChest implements IInventory
{
	private String localstring;
	public ItemStack[] cheststack;

	public AnimalChest(ItemStack[] aitemstack, String s)
	{
		cheststack = aitemstack;
		localstring = s;
	}

	public int getSizeInventory()
	{
		return 27;
	}

	public ItemStack getStackInSlot(int i)
	{
		return cheststack[i];
	}

	public ItemStack decrStackSize(int i, int j)
	{
		if(cheststack[i] != null)
		{
			if(cheststack[i].stackSize <= j)
			{
				ItemStack itemstack = cheststack[i];
				cheststack[i] = null;
				onInventoryChanged();
				return itemstack;
			}
			ItemStack itemstack1 = cheststack[i].splitStack(j);
			if(cheststack[i].stackSize == 0)
				cheststack[i] = null;
			onInventoryChanged();
			return itemstack1;
		} 
		return null;
	}

	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		cheststack[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
			itemstack.stackSize = getInventoryStackLimit();
		onInventoryChanged();
	}

	public String getInvName()
	{
		return localstring;
	}

	public int getInventoryStackLimit()
	{
		return 64;
	}

	public void onInventoryChanged()
	{
	}

	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}
}
