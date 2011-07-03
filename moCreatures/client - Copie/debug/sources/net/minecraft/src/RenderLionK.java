package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class RenderLionK extends RenderLiving
{

    public RenderLionK(ModelBase modelbase, ModelBase modelbase1, float f)
    {
        super(modelbase, f);
        setRenderPassModel(modelbase1);
    }

    protected boolean func_22004_c(EntityLionK entitylionk, int i)
    {
        if(entitylionk.malelion)
        {
            loadTexture("/mob/lionb.png");
        } else
        {
            loadTexture("/mob/lionc.png");
        }
        return i == 0 && !entitylionk.lionboolean;
    }

    protected boolean shouldRenderPass(EntityLiving entityliving, int i, float f)
    {
        return func_22004_c((EntityLionK)entityliving, i);
    }
}
