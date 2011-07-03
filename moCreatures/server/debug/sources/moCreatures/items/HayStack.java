package moCreatures.items;

import net.minecraft.src.Item;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class HayStack extends Item
{
	public HayStack(int i)
	{
		super(i);
		maxStackSize = 16;
        setMaxDamage(64);
	}
}
