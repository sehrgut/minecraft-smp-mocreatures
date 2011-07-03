package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

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
        if(entityhorse.texture == "/mob/horseb.png")
        {
            if(!entityhorse.rideable)
            {
                loadTexture("/mob/horsea.png");
            } else
            {
                loadTexture("/mob/horseasaddle.png");
            }
        } else
        if(entityhorse.texture == "/mob/horsebrownb.png")
        {
            if(!entityhorse.rideable)
            {
                loadTexture("/mob/horsebrowna.png");
            } else
            {
                loadTexture("/mob/horsebrownsaddle.png");
            }
        } else
        if(entityhorse.texture == "/mob/horseblackb.png")
        {
            if(!entityhorse.rideable)
            {
                loadTexture("/mob/horseblacka.png");
            } else
            {
                loadTexture("/mob/horseblacksaddle.png");
            }
        } else
        if(entityhorse.texture == "/mob/horsegoldb.png")
        {
            if(!entityhorse.rideable)
            {
                loadTexture("/mob/horsegolda.png");
            } else
            {
                loadTexture("/mob/horsegoldsaddle.png");
            }
        } else
        if(entityhorse.texture == "/mob/horsewhiteb.png")
        {
            if(!entityhorse.rideable)
            {
                loadTexture("/mob/horsewhitea.png");
            } else
            {
                loadTexture("/mob/horsewhitesaddle.png");
            }
        } else
        if(entityhorse.texture == "/mob/horsepackb.png")
        {
            if(!entityhorse.rideable)
            {
                loadTexture("/mob/horsepacka.png");
            } else
            {
                loadTexture("/mob/horsepacksaddle.png");
            }
        } else
        if(entityhorse.texture == "/mob/horsenightb.png")
        {
            if(!entityhorse.rideable)
            {
                loadTexture("/mob/horsenighta.png");
            } else
            {
                loadTexture("/mob/horsenightsaddle.png");
            }
        } else
        if(entityhorse.texture == "/mob/horsebpb.png")
        {
            if(!entityhorse.rideable)
            {
                loadTexture("/mob/horsebpa.png");
            } else
            {
                loadTexture("/mob/horsebpsaddle.png");
            }
        }
        return i == 0 && !entityhorse.horseboolean;
    }
}
