package moCreatures.renders;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import moCreatures.entities.EntityBunny;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;

import org.lwjgl.opengl.GL11;

public class RenderBunny extends RenderLiving
{

    public RenderBunny(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    public void doRenderLiving(EntityBunny entitybunny, double d, double d1, double d2, 
            float f, float f1)
    {
        super.doRenderLiving(entitybunny, d, d1, d2, f, f1);
    }

    protected float func_22001_u(EntityBunny entitybunny, float f)
    {
        float f1 = entitybunny.e + (entitybunny.b - entitybunny.e) * f;
        float f2 = entitybunny.d + (entitybunny.c - entitybunny.d) * f;
        return (MathHelper.sin(f1) + 1.0F) * f2;
    }

    protected float func_167_c(EntityLiving entityliving, float f)
    {
        return func_22001_u((EntityBunny)entityliving, f);
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, 
            float f, float f1)
    {
        doRenderLiving((EntityBunny)entityliving, d, d1, d2, f, f1);
    }

    public void doRender(Entity entity, double d, double d1, double d2, 
            float f, float f1)
    {
        doRenderLiving((EntityBunny)entity, d, d1, d2, f, f1);
    }

    protected void rotBunny(EntityBunny entitybunny)
    {
        if(!entitybunny.isCollidedHorizontally && entitybunny.worldObj == null)
        {
            if(entitybunny.motionZ > 0.5D)
            {
                GL11.glRotatef(35F, -1F, 0.0F, 0.0F);
            } else
            if(entitybunny.motionZ < -0.5D)
            {
                GL11.glRotatef(-35F, -1F, 0.0F, 0.0F);
            } else
            {
                GL11.glRotatef((float)(entitybunny.motionZ * 70D), -1F, 0.0F, 0.0F);
            }
        }
    }

    protected void preRenderCallback(EntityLiving entityliving, float f)
    {
        rotBunny((EntityBunny)entityliving);
    }
}
