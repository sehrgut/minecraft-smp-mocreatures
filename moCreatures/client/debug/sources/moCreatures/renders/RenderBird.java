package moCreatures.renders;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;
import moCreatures.entities.EntityBird;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class RenderBird extends RenderLiving
{

    public RenderBird(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, 
            float f, float f1)
    {
        EntityBird entitybird = (EntityBird)entityliving;
        if(!entitybird.typechosen)
        {
            entitybird.chooseType();
        }
        super.doRenderLiving(entitybird, d, d1, d2, f, f1);
    }

    protected float func_170_d(EntityLiving entityliving, float f)
    {
        EntityBird entitybird = (EntityBird)entityliving;
        float f1 = entitybird.winge + (entitybird.wingb - entitybird.winge) * f;
        float f2 = entitybird.wingd + (entitybird.wingc - entitybird.wingd) * f;
        return (MathHelper.sin(f1) + 1.0F) * f2;
    }
}
