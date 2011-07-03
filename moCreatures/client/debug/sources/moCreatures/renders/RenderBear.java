package moCreatures.renders;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;
import moCreatures.entities.EntityBear;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class RenderBear extends RenderLiving
{

    public RenderBear(ModelBase modelbase, ModelBase modelbase1, float f)
    {
        super(modelbase, f);
        setRenderPassModel(modelbase1);
    }

    protected boolean func_22002_b(EntityBear entitybear, int i)
    {
        loadTexture("/moCreatures/textures/bearb.png");
        return i == 0 && !entitybear.bearboolean;
    }

    protected boolean shouldRenderPass(EntityLiving entityliving, int i, float f)
    {
        return func_22002_b((EntityBear)entityliving, i);
    }
}
