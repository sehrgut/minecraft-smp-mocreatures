package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class RenderPolarBear extends RenderLiving
{

    public RenderPolarBear(ModelBase modelbase, ModelBase modelbase1, float f)
    {
        super(modelbase, f);
        setRenderPassModel(modelbase1);
    }

    protected boolean func_22006_t(EntityBear entitybear, int i)
    {
        loadTexture("/mob/polarbearb.png");
        return i == 0 && !entitybear.bearboolean;
    }

    protected boolean shouldRenderPass(EntityLiving entityliving, int i, float f)
    {
        return func_22006_t((EntityBear)entityliving, i);
    }
}
