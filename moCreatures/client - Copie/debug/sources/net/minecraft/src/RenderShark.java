package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import org.lwjgl.opengl.GL11;

public class RenderShark extends RenderLiving
{

    public RenderShark(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, 
            float f, float f1)
    {
        EntityShark entityshark = (EntityShark)entityliving;
        super.doRenderLiving(entityshark, d, d1, d2, f, f1);
    }

    protected void stretch(EntityShark entityshark)
    {
        GL11.glScalef(entityshark.b, entityshark.b, entityshark.b);
    }

    protected float func_170_d(EntityLiving entityliving, float f)
    {
        stretch((EntityShark)entityliving);
        return (float)entityliving.ticksExisted + f;
    }
}
