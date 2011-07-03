package moCreatures.renders;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import moCreatures.entities.EntityDolphin;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;

import org.lwjgl.opengl.GL11;

public class RenderDolphin extends RenderLiving
{

    public RenderDolphin(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, 
            float f, float f1)
    {
        EntityDolphin entitydolphin = (EntityDolphin)entityliving;
        if(!entitydolphin.typechosen)
        {
            entitydolphin.chooseType();
        }
        super.doRenderLiving(entitydolphin, d, d1, d2, f, f1);
    }

    protected void stretch(EntityDolphin entitydolphin)
    {
        GL11.glScalef(entitydolphin.b, entitydolphin.b, entitydolphin.b);
    }

    protected float func_170_d(EntityLiving entityliving, float f)
    {
        stretch((EntityDolphin)entityliving);
        return (float)entityliving.ticksExisted + f;
    }
}
