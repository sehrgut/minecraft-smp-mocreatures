package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ModelBunny extends ModelBase
{

    public ModelBunny()
    {
        byte byte0 = 16;
        a = new ModelRenderer(0, 0);
        a.addBox(-2F, -1F, -4F, 4, 4, 6, 0.0F);
        a.setPosition(0.0F, -1 + byte0, -4F);
        m = new ModelRenderer(14, 0);
        m.addBox(-2F, -5F, -3F, 1, 4, 2, 0.0F);
        m.setPosition(0.0F, -1 + byte0, -4F);
        g2 = new ModelRenderer(14, 0);
        g2.addBox(1.0F, -5F, -3F, 1, 4, 2, 0.0F);
        g2.setPosition(0.0F, -1 + byte0, -4F);
        h = new ModelRenderer(20, 0);
        h.addBox(-4F, 0.0F, -3F, 2, 3, 2, 0.0F);
        h.setPosition(0.0F, -1 + byte0, -4F);
        r2 = new ModelRenderer(20, 0);
        r2.addBox(2.0F, 0.0F, -3F, 2, 3, 2, 0.0F);
        r2.setPosition(0.0F, -1 + byte0, -4F);
        b = new ModelRenderer(0, 10);
        b.addBox(-3F, -4F, -3F, 6, 8, 6, 0.0F);
        b.setPosition(0.0F, 0 + byte0, 0.0F);
        b2 = new ModelRenderer(0, 24);
        b2.addBox(-2F, 4F, -2F, 4, 3, 4, 0.0F);
        b2.setPosition(0.0F, 0 + byte0, 0.0F);
        e1 = new ModelRenderer(24, 16);
        e1.addBox(-2F, 0.0F, -1F, 2, 2, 2);
        e1.setPosition(3F, 3 + byte0, -3F);
        e2 = new ModelRenderer(24, 16);
        e2.addBox(0.0F, 0.0F, -1F, 2, 2, 2);
        e2.setPosition(-3F, 3 + byte0, -3F);
        l1 = new ModelRenderer(16, 24);
        l1.addBox(-2F, 0.0F, -4F, 2, 2, 4);
        l1.setPosition(3F, 3 + byte0, 4F);
        n2 = new ModelRenderer(16, 24);
        n2.addBox(0.0F, 0.0F, -4F, 2, 2, 4);
        n2.setPosition(-3F, 3 + byte0, 4F);
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        a.render(f5);
        m.render(f5);
        g2.render(f5);
        h.render(f5);
        r2.render(f5);
        b.render(f5);
        b2.render(f5);
        e1.render(f5);
        e2.render(f5);
        l1.render(f5);
        n2.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        a.rotateAngleX = -(f4 / 57.29578F);
        a.rotateAngleY = f3 / 57.29578F;
        m.rotateAngleX = a.rotateAngleX;
        m.rotateAngleY = a.rotateAngleY;
        g2.rotateAngleX = a.rotateAngleX;
        g2.rotateAngleY = a.rotateAngleY;
        h.rotateAngleX = a.rotateAngleX;
        h.rotateAngleY = a.rotateAngleY;
        r2.rotateAngleX = a.rotateAngleX;
        r2.rotateAngleY = a.rotateAngleY;
        b.rotateAngleX = 1.570796F;
        b2.rotateAngleX = 1.570796F;
        e1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.0F * f1;
        l1.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.2F * f1;
        e2.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.0F * f1;
        n2.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.2F * f1;
    }

    public ModelRenderer a;
    public ModelRenderer b;
    public ModelRenderer b2;
    public ModelRenderer e1;
    public ModelRenderer e2;
    public ModelRenderer l1;
    public ModelRenderer n2;
    public ModelRenderer m;
    public ModelRenderer g2;
    public ModelRenderer h;
    public ModelRenderer r2;
}
