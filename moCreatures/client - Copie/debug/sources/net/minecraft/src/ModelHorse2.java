package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ModelHorse2 extends ModelBase
{

    public ModelHorse2()
    {
        Body = new ModelRenderer(0, 0);
        Body.addBox(-5F, 0.0F, -10F, 10, 10, 20, 0.0F);
        Body.setPosition(0.0F, 2.0F, 0.0F);
        Tail = new ModelRenderer(40, 0);
        Tail.addBox(-2F, 0.0F, -2F, 4, 12, 4, 0.0F);
        Tail.setPosition(0.0F, 2.0F, 8F);
        Tail.rotateAngleX = 0.5235988F;
        Leg1 = new ModelRenderer(0, 0);
        Leg1.addBox(-2F, 0.0F, -2F, 4, 12, 4, 0.0F);
        Leg1.setPosition(3F, 12F, 8F);
        Leg2 = new ModelRenderer(0, 0);
        Leg2.addBox(-2F, 0.0F, -2F, 4, 12, 4, 0.0F);
        Leg2.setPosition(-3F, 12F, 8F);
        Leg3 = new ModelRenderer(0, 0);
        Leg3.addBox(-2F, 0.0F, -2F, 4, 12, 4, 0.0F);
        Leg3.setPosition(3F, 12F, -8F);
        Leg4 = new ModelRenderer(0, 0);
        Leg4.addBox(-2F, 0.0F, -2F, 4, 12, 4, 0.0F);
        Leg4.setPosition(-3F, 12F, -8F);
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Body.render(f5);
        Tail.render(f5);
        Leg1.render(f5);
        Leg2.render(f5);
        Leg3.render(f5);
        Leg4.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        Leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        Leg2.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
        Leg3.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
        Leg4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    }

    public ModelRenderer Body;
    public ModelRenderer Tail;
    public ModelRenderer Leg1;
    public ModelRenderer Leg2;
    public ModelRenderer Leg3;
    public ModelRenderer Leg4;
}
