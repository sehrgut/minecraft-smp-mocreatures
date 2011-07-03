package moCreatures.models;

import net.minecraft.src.ModelQuadruped;
import net.minecraft.src.ModelRenderer;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ModelWildWolf2 extends ModelQuadruped
{

    public ModelWildWolf2()
    {
        super(10, 0.0F);
        head = new ModelRenderer(0, 0);
        head.addBox(-4F, -2F, -6F, 8, 8, 6, 0.0F);
        head.setRotationPoint(0.0F, 4F, -8F);
        b = new ModelRenderer(8, 15);
        b.addBox(-2F, 3F, -12F, 4, 4, 6, 0.0F);
        b.setRotationPoint(0.0F, 3F, -7F);
        body = new ModelRenderer(28, 6);
        body.addBox(-5F, -8F, -9F, 10, 16, 6, 0.0F);
        body.setRotationPoint(0.0F, 5F, 2.0F);
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(f, f1, f2, f3, f4, f5);
        b.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5);
        b.rotateAngleY = head.rotateAngleY;
        b.rotateAngleX = head.rotateAngleX;
    }

    ModelRenderer b;
}
