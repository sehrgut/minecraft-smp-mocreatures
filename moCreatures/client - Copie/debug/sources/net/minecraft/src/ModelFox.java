package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ModelFox extends ModelBase
{

    public ModelFox()
    {
        byte byte0 = 8;
        Body = new ModelRenderer(0, 0);
        Body.addBox(0.0F, 0.0F, 0.0F, 6, 6, 12, 0.0F);
        Body.setPosition(-4F, 10F, -6F);
        Head = new ModelRenderer(0, 20);
        Head.addBox(-4F, -4F, -6F, 6, 6, 4, 0.0F);
        Head.setPosition(0.0F, 12F, -4F);
        Snout = new ModelRenderer(20, 20);
        Snout.addBox(-2F, 1.0F, -10F, 2, 2, 4, 0.0F);
        Snout.setPosition(0.0F, 11F, -3.5F);
        Ears = new ModelRenderer(50, 20);
        Ears.addBox(-4F, -4F, -6F, 6, 4, 1, 0.0F);
        Ears.setPosition(0.0F, 9F, -2F);
        Tail = new ModelRenderer(32, 20);
        Tail.addBox(-5F, -5F, -2F, 3, 3, 8, 0.0F);
        Tail.setPosition(2.5F, 15F, 5F);
        Tail.rotateAngleX = -0.5235988F;
        Leg1 = new ModelRenderer(0, 0);
        Leg1.addBox(-2F, 0.0F, -2F, 3, byte0, 3, 0.0F);
        Leg1.setPosition(-2F, 24 - byte0, 5F);
        Leg2 = new ModelRenderer(0, 0);
        Leg2.addBox(-2F, 0.0F, -2F, 3, byte0, 3, 0.0F);
        Leg2.setPosition(1.0F, 24 - byte0, 5F);
        Leg3 = new ModelRenderer(0, 0);
        Leg3.addBox(-2F, 0.0F, -2F, 3, byte0, 3, 0.0F);
        Leg3.setPosition(-2F, 24 - byte0, -4F);
        Leg4 = new ModelRenderer(0, 0);
        Leg4.addBox(-2F, 0.0F, -2F, 3, byte0, 3, 0.0F);
        Leg4.setPosition(1.0F, 24 - byte0, -4F);
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Body.render(f5);
        Leg1.render(f5);
        Leg2.render(f5);
        Leg3.render(f5);
        Leg4.render(f5);
        Head.render(f5);
        Snout.render(f5);
        Tail.render(f5);
        Ears.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        Head.rotateAngleY = f3 / 57.29578F;
        Head.rotateAngleX = f4 / 57.29578F;
        Snout.rotateAngleY = f3 / 57.29578F;
        Snout.rotateAngleX = f4 / 57.29578F;
        Snout.offsetX = 0.0F + (f3 / 57.29578F) * 0.8F;
        Ears.rotateAngleY = f3 / 57.29578F;
        Ears.rotateAngleX = f4 / 57.29578F;
        Ears.offsetX = 0.0F + (f3 / 57.29578F) * 2.5F;
        Leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        Leg2.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
        Leg3.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
        Leg4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    }

    public ModelRenderer Body;
    public ModelRenderer Leg1;
    public ModelRenderer Leg2;
    public ModelRenderer Leg3;
    public ModelRenderer Leg4;
    public ModelRenderer Snout;
    public ModelRenderer Head;
    public ModelRenderer Tail;
    public ModelRenderer Ears;
}
