package moCreatures.models;

import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ModelFishy extends ModelBase
{

    public ModelFishy()
    {
        Body = new ModelRenderer(0, 0);
        Body.addBox(0.0F, 0.0F, 0.0F, 1, 5, 5, 0.0F);
        Body.setRotationPoint(0.0F, 19F, 0.0F);
        Body.rotateAngleX = 0.7853981F;
        Tail = new ModelRenderer(12, 0);
        Tail.addBox(0.0F, 0.0F, 0.0F, 1, 3, 3, 0.0F);
        Tail.setRotationPoint(0.0F, 18.7F, 6F);
        Tail.rotateAngleX = 0.7853981F;
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Body.render(f5);
        Tail.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        Tail.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    }

    public ModelRenderer Body;
    public ModelRenderer UpperFin;
    public ModelRenderer LowerFin;
    public ModelRenderer RightFin;
    public ModelRenderer LeftFin;
    public ModelRenderer Tail;
}
