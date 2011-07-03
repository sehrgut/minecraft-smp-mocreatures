package moCreatures.models;

import net.minecraft.src.ModelQuadruped;
import net.minecraft.src.ModelRenderer;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ModelBear2 extends ModelQuadruped
{

    public ModelBear2()
    {
        super(12, 0.8F);
        head = new ModelRenderer(0, 0);
        head.addBox(-4F, -4F, -6F, 8, 8, 6, 0.0F);
        head.setRotationPoint(0.0F, 4F, -8F);
        b = new ModelRenderer(23, 0);
        b.addBox(-2F, 1.0F, -10F, 4, 4, 6, 0.0F);
        b.setRotationPoint(0.0F, 3F, -7F);
        body = new ModelRenderer(32, 10);
        body.addBox(-4F, -8F, -5F, 8, 14, 8, 3F);
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
