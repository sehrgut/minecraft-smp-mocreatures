package moCreatures.models;

import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ModelHorse1 extends ModelBase
{

    public ModelHorse1()
    {
        Head = new ModelRenderer(0, 0);
        Head.addBox(-2.5F, 0.0F, -5.5F, 5, 5, 11, 0.0F);
        Head.setRotationPoint(0.0F, -5.5F, -13.8F);
        Head.rotateAngleX = 0.3490658F;
        Unicorn = new ModelRenderer(50, 0);
        Unicorn.addBox(-2.5F, 0.0F, -5.5F, 0, 8, 3, 0.0F);
        Unicorn.setRotationPoint(2.5F, -15F, -11F);
        Unicorn.rotateAngleX = 0.3490658F;
        Ear1 = new ModelRenderer(0, 0);
        Ear1.addBox(-0.5F, 0.0F, -1F, 1, 2, 2, 0.0F);
        Ear1.setRotationPoint(1.8F, -8F, -11F);
        Ear1.rotateAngleX = 0.3490658F;
        Ear2 = new ModelRenderer(0, 0);
        Ear2.addBox(-0.5F, 0.0F, -1F, 1, 2, 2, 0.0F);
        Ear2.setRotationPoint(-1.8F, -8F, -11F);
        Ear2.mirror = true;
        Ear2.rotateAngleX = 0.3490658F;
        Neck = new ModelRenderer(0, 10);
        Neck.addBox(-2F, 0.0F, -4F, 4, 14, 8, 0.0F);
        Neck.setRotationPoint(0.0F, -5F, -12F);
        Neck.rotateAngleX = 0.5235988F;
        RightWing = new ModelRenderer(34, 0);
        RightWing.addBox(0.0F, 0.0F, -5F, 1, 20, 12, 0.5F);
        RightWing.setRotationPoint(-6.6F, 2.5F, 0.0F);
        LeftWing = new ModelRenderer(34, 0);
        LeftWing.addBox(-1F, 0.0F, -5F, 1, 20, 12, 0.5F);
        LeftWing.setRotationPoint(6.6F, 2.5F, 0.0F);
        LeftWing.mirror = true;
        Bag1 = new ModelRenderer(22, 0);
        Bag1.addBox(0.0F, 0.0F, 0.0F, 8, 8, 3, -0.5F);
        Bag1.setRotationPoint(-7.5F, 3F, 10F);
        Bag1.rotateAngleY = 1.570796F;
        Bag2 = new ModelRenderer(22, 0);
        Bag2.addBox(0.0F, 0.0F, 0.0F, 8, 8, 3, -0.5F);
        Bag2.setRotationPoint(4.5F, 3F, 10F);
        Bag2.rotateAngleY = 1.570796F;
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Head.render(f5);
        Ear1.render(f5);
        Ear2.render(f5);
        Neck.render(f5);
        Unicorn.render(f5);
        RightWing.render(f5);
        LeftWing.render(f5);
        Bag1.render(f5);
        Bag2.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        RightWing.rotateAngleZ = f2;
        LeftWing.rotateAngleZ = -f2;
        Bag1.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f2) / 10F;
        Bag2.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f2) / 10F;
    }

    public ModelRenderer Head;
    public ModelRenderer Ear1;
    public ModelRenderer Ear2;
    public ModelRenderer Neck;
    public ModelRenderer RightWing;
    public ModelRenderer LeftWing;
    public ModelRenderer Unicorn;
    public ModelRenderer Bag1;
    public ModelRenderer Bag2;
}
