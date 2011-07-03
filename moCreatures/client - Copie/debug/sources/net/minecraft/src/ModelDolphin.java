package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ModelDolphin extends ModelBase
{

    public ModelDolphin()
    {
        Body = new ModelRenderer(4, 6);
        Body.addBox(0.0F, 0.0F, 0.0F, 6, 8, 18, 0.0F);
        Body.setPosition(-4F, 17F, -10F);
        UHead = new ModelRenderer(0, 0);
        UHead.addBox(0.0F, 0.0F, 0.0F, 5, 7, 8, 0.0F);
        UHead.setPosition(-3.5F, 18F, -16.5F);
        DHead = new ModelRenderer(50, 0);
        DHead.addBox(0.0F, 0.0F, 0.0F, 3, 3, 4, 0.0F);
        DHead.setPosition(-2.5F, 21.5F, -20.5F);
        PTail = new ModelRenderer(34, 9);
        PTail.addBox(0.0F, 0.0F, 0.0F, 5, 5, 10, 0.0F);
        PTail.setPosition(-3.5F, 19F, 8F);
        UpperFin = new ModelRenderer(4, 12);
        UpperFin.addBox(0.0F, 0.0F, 0.0F, 1, 4, 8, 0.0F);
        UpperFin.setPosition(-1.5F, 18F, -4F);
        UpperFin.rotateAngleX = 0.7853981F;
        LTailFin = new ModelRenderer(34, 0);
        LTailFin.addBox(0.0F, 0.0F, 0.0F, 4, 1, 8, 0.3F);
        LTailFin.setPosition(-2F, 21.5F, 18F);
        LTailFin.rotateAngleY = 0.7853981F;
        RTailFin = new ModelRenderer(34, 0);
        RTailFin.addBox(0.0F, 0.0F, 0.0F, 4, 1, 8, 0.3F);
        RTailFin.setPosition(-3F, 21.5F, 15F);
        RTailFin.rotateAngleY = -0.7853981F;
        LeftFin = new ModelRenderer(14, 0);
        LeftFin.addBox(0.0F, 0.0F, 0.0F, 8, 1, 4, 0.0F);
        LeftFin.setPosition(2.0F, 24F, -7F);
        LeftFin.rotateAngleY = -0.5235988F;
        LeftFin.rotateAngleZ = 0.5235988F;
        RightFin = new ModelRenderer(14, 0);
        RightFin.addBox(0.0F, 0.0F, 0.0F, 8, 1, 4, 0.0F);
        RightFin.setPosition(-10F, 27.5F, -3F);
        RightFin.rotateAngleY = 0.5235988F;
        RightFin.rotateAngleZ = -0.5235988F;
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Body.render(f5);
        PTail.render(f5);
        UHead.render(f5);
        DHead.render(f5);
        UpperFin.render(f5);
        LTailFin.render(f5);
        RTailFin.render(f5);
        LeftFin.render(f5);
        RightFin.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        RTailFin.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
        LTailFin.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
    }

    public ModelRenderer UHead;
    public ModelRenderer DHead;
    public ModelRenderer RTail;
    public ModelRenderer LTail;
    public ModelRenderer PTail;
    public ModelRenderer Body;
    public ModelRenderer UpperFin;
    public ModelRenderer RTailFin;
    public ModelRenderer LTailFin;
    public ModelRenderer LowerFin;
    public ModelRenderer RightFin;
    public ModelRenderer LeftFin;
}
