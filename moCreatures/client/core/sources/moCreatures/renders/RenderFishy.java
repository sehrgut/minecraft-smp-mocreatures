package moCreatures.renders;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import moCreatures.entities.EntityFishy;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;

import org.lwjgl.opengl.GL11;

public class RenderFishy extends RenderLiving
{

    public RenderFishy(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, 
            float f, float f1)
    {
        EntityFishy entityfishy = (EntityFishy)entityliving;
        if(!entityfishy.typechosen)
        {
            entityfishy.chooseType();
        }
        super.doRenderLiving(entityfishy, d, d1, d2, f, f1);
    }

    protected void stretch(EntityFishy entityfishy)
    {
        GL11.glScalef(entityfishy.b, entityfishy.b, entityfishy.b);
    }

    protected float func_170_d(EntityLiving entityliving, float f)
    {
        EntityFishy entityfishy = (EntityFishy)entityliving;
        if(!entityfishy.adult)
        {
            stretch(entityfishy);
        }
        float f1 = entityfishy.winge + (entityfishy.wingb - entityfishy.winge) * f;
        float f2 = entityfishy.wingd + (entityfishy.wingc - entityfishy.wingd) * f;
        return (MathHelper.sin(f1) + 1.0F) * f2;
    }
}
