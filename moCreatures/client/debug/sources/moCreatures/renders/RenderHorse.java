package moCreatures.renders;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import moCreatures.entities.EntityHorse;
import moCreatures.helpers.EntityLivingHelper;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;

import org.lwjgl.opengl.GL11;

public class RenderHorse extends RenderLiving
{

	public RenderHorse(ModelBase modelbase, ModelBase modelbase1)
	{
		super(modelbase1, 0.5F);
		setRenderPassModel(modelbase);
	}

	protected boolean shouldRenderPass(EntityLiving entityliving, int i, float f)
	{
		return func_176_a((EntityHorse)entityliving, i);
	}

	protected float func_182_a(EntityHorse entityhorse, float f)
	{
		float f1 = entityhorse.fwinge + (entityhorse.fwingb - entityhorse.fwinge) * f;
		float f2 = entityhorse.fwingd + (entityhorse.fwingc - entityhorse.fwingd) * f;
		if(!entityhorse.adult)
		{
			stretch(entityhorse);
		}
		return (MathHelper.sin(f1) + 1.0F) * f2;
	}

	protected void stretch(EntityHorse entityhorse)
	{
		GL11.glScalef(entityhorse.b, entityhorse.b, entityhorse.b);
	}

	protected float func_170_d(EntityLiving entityliving, float f)
	{
		return func_182_a((EntityHorse)entityliving, f);
	}

	protected boolean func_176_a(EntityHorse entityhorse, int i)
	{
		if(!entityhorse.typechosen)
		{
			entityhorse.chooseType();
		}
		String texture = EntityLivingHelper.getTexture(entityhorse);
		loadTexture(texture.substring(0, texture.lastIndexOf('.') - 1) + (entityhorse.rideable ? "saddle" : "a") + ".png");
		return i == 0 && !entityhorse.horseboolean;
	}
}
