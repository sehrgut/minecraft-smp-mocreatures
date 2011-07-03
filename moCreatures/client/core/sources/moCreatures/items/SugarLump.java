package moCreatures.items;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class SugarLump extends Item
{

    public SugarLump(int i)
    {
        super(i);
        maxStackSize = 32;
        setMaxDamage(64);
        a = 3;
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        itemstack.stackSize--;
        entityplayer.heal(a);
        return itemstack;
    }

    private int a;
}
