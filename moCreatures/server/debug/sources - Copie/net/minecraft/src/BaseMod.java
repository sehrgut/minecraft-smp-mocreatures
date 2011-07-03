package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public abstract class BaseMod
{
	public BaseMod()
	{
	}

	public String toString()
	{
		return new StringBuilder(getClass().getName()).append(" ").append(version()).toString();
	}

	public abstract String version();

	public abstract void addRecipes(CraftingManager craftingmanager);
}
