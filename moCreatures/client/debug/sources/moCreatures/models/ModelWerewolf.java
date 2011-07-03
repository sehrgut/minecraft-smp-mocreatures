package moCreatures.models;

import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ModelWerewolf extends ModelBase
{

    public ModelWerewolf()
    {
        Head = new ModelRenderer(0, 0);
        Head.addBox(-0.5F, -8.5F, -8F, 3, 3, 4, 1.6F);
        Head.setRotationPoint(0.0F, 2.0F, -1F);
        RightEar = new ModelRenderer(15, 0);
        RightEar.addBox(-0.5F, -8.5F, -8F, 2, 4, 1, 0.0F);
        RightEar.setRotationPoint(3.5F, -4F, 3.5F);
        RightEar.rotateAngleZ = -0.7853981F;
        RightEar.rotateAngleX = -0.5235988F;
        LeftEar = new ModelRenderer(15, 0);
        LeftEar.addBox(-0.5F, -8.5F, -8F, 2, 4, 1, 0.0F);
        LeftEar.setRotationPoint(-2.5F, -4.5F, 3.5F);
        LeftEar.mirror = true;
        LeftEar.rotateAngleZ = 0.7853981F;
        LeftEar.rotateAngleX = -0.5235988F;
        Torso = new ModelRenderer(0, 7);
        Torso.addBox(-1.5F, -8F, -3.5F, 5, 4, 5, 2.4F);
        Torso.setRotationPoint(0.0F, 2.0F, 0.0F);
        Torso.rotateAngleX = 1.047198F;
        Torso2 = new ModelRenderer(20, 7);
        Torso2.addBox(-1.5F, -8F, -3.5F, 5, 4, 5, 2.4F);
        Torso2.setRotationPoint(0.0F, 3.5F, 12F);
        Torso2.rotateAngleX = 0.6108652F;
        Abdomen = new ModelRenderer(0, 16);
        Abdomen.addBox(-1F, 2.5F, 6F, 4, 5, 4, 2.4F);
        Abdomen.setRotationPoint(0.0F, 2.0F, 0.0F);
        Abdomen.rotateAngleX = 0.4363323F;
        RightArm = new ModelRenderer(48, 0);
        RightArm.addBox(-8F, 0.0F, 0.0F, 4, 16, 4, 0.0F);
        RightArm.setRotationPoint(0.0F, -6F, 0.0F);
        LeftArm = new ModelRenderer(48, 0);
        LeftArm.addBox(6F, 0.0F, 0.0F, 4, 16, 4, 0.0F);
        LeftArm.setRotationPoint(0.0F, -6F, 0.0F);
        LeftArm.mirror = true;
        RightTigh = new ModelRenderer(32, 20);
        RightTigh.addBox(0.0F, 0.0F, 0.0F, 4, 8, 4, 0.0F);
        RightTigh.setRotationPoint(-3.5F, 8F, 9F);
        RightTigh.rotateAngleX = -0.7853981F;
        LeftTigh = new ModelRenderer(32, 20);
        LeftTigh.addBox(0.0F, 0.0F, 0.0F, 4, 8, 4, 0.0F);
        LeftTigh.setRotationPoint(1.5F, 8F, 9F);
        LeftTigh.mirror = true;
        LeftTigh.rotateAngleX = -0.7853981F;
        RightLeg = new ModelRenderer(48, 20);
        RightLeg.addBox(2.0F, 8F, 0.0F, 4, 8, 4, 0.0F);
        RightLeg.setRotationPoint(-5.5F, 6F, -3F);
        LeftLeg = new ModelRenderer(48, 20);
        LeftLeg.addBox(2.0F, 8F, 0.0F, 4, 8, 4, 0.0F);
        LeftLeg.setRotationPoint(-0.5F, 6F, -3F);
        LeftLeg.mirror = true;
        Tail = new ModelRenderer(9, 22);
        Tail.addBox(0.0F, -2F, 16F, 2, 2, 8, 1.0F);
        Tail.setRotationPoint(0.0F, 2.0F, 0.0F);
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5, boolean flag)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5, flag);
        Head.render(f5);
        RightEar.render(f5);
        LeftEar.render(f5);
        Torso.render(f5);
        Torso2.render(f5);
        Abdomen.render(f5);
        RightArm.render(f5);
        LeftArm.render(f5);
        RightTigh.render(f5);
        LeftTigh.render(f5);
        RightLeg.render(f5);
        LeftLeg.render(f5);
        Tail.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean flag)
    {
        Head.rotateAngleY = f3 / 57.29578F / 2.0F;
        Head.rotateAngleX = f4 / 57.29578F;
        RightEar.rotateAngleY = Head.rotateAngleX;
        RightEar.rotateAngleX = -0.5235988F - Head.rotateAngleY / 4F;
        RightEar.rotationPointX = 3.5F - Head.rotateAngleY * 2.0F;
        LeftEar.rotateAngleY = Head.rotateAngleX;
        LeftEar.rotateAngleX = -0.5235988F + Head.rotateAngleY / 4F;
        LeftEar.rotationPointX = -2.5F - Head.rotateAngleY * 2.0F;
        RightArm.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 2.0F * f1 * 0.5F;
        LeftArm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
        RightArm.rotateAngleZ = 0.0F;
        LeftArm.rotateAngleZ = 0.0F;
        RightTigh.rotateAngleX = -0.7853981F + (MathHelper.cos(f * 0.6662F) * 1.4F * f1) / 3F;
        LeftTigh.rotateAngleX = -0.7853981F + (MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1) / 3F;
        RightLeg.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1) / 2.0F;
        RightLeg.rotationPointZ = 3F + (MathHelper.cos(f * 0.6662F) * 1.4F * f1) / 10F;
        RightLeg.rotationPointY = 7.5F + MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        LeftLeg.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1) / 2.0F;
        LeftLeg.rotationPointZ = 3F + (MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1) / 10F;
        LeftLeg.rotationPointY = 7.5F + MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
        RightLeg.rotateAngleY = 0.0F;
        LeftLeg.rotateAngleY = 0.0F;
        if(flag)
        {
            Tail.rotateAngleX = 0.261799F;
            Tail.rotationPointY = 10F;
            Tail.rotationPointZ = -2F;
            Abdomen.rotateAngleX = 1.308997F;
            Abdomen.rotationPointY = 13F;
            Abdomen.rotationPointZ = 0.0F;
            Torso.rotateAngleX = 1.919862F;
            Torso.rotationPointY = 4F;
            Torso.rotationPointZ = 1.0F;
            Torso2.rotateAngleX = 1.570796F;
            Torso2.rotationPointY = 5F;
            Torso2.rotationPointZ = 8.5F;
            RightArm.rotationPointY = 8F;
            RightArm.rotationPointZ = -6F;
            LeftArm.rotationPointY = 8F;
            LeftArm.rotationPointZ = -6F;
            Head.rotationPointY = 16F;
            Head.rotationPointZ = -7F;
            RightEar.rotationPointY = 12.5F;
            RightEar.rotationPointZ = -6F - Head.rotateAngleY * 2.0F;
            LeftEar.rotationPointY = 12F;
            LeftEar.rotationPointZ = -6F - Head.rotateAngleY * 2.0F;
        } else
        {
            Tail.rotateAngleX = -0.4363323F;
            Tail.rotationPointY = 1.0F;
            Tail.rotationPointZ = -1F;
            Abdomen.rotateAngleX = 0.4363323F;
            Abdomen.rotationPointY = 2.0F;
            Abdomen.rotationPointZ = 0.0F;
            Torso.rotateAngleX = 1.047198F;
            Torso.rotationPointY = -3F;
            Torso.rotationPointZ = 9F;
            Torso2.rotateAngleX = 0.6108652F;
            Torso2.rotationPointY = 3.5F;
            Torso2.rotationPointZ = 12F;
            RightArm.rotationPointY = -7F;
            RightArm.rotationPointZ = 0.0F;
            LeftArm.rotationPointY = -7F;
            LeftArm.rotationPointZ = 0.0F;
            Head.rotationPointY = -0.5F;
            Head.rotationPointZ = 1.5F;
            RightEar.rotationPointY = -4F;
            RightEar.rotationPointZ = 2.5F - Head.rotateAngleY * 2.0F;
            LeftEar.rotationPointY = -4.5F;
            LeftEar.rotationPointZ = 2.5F - Head.rotateAngleY * 2.0F;
        }
        RightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
        LeftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
        RightArm.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
        LeftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067F) * 0.05F;
    }

    public ModelRenderer Head;
    public ModelRenderer Torso;
    public ModelRenderer Torso2;
    public ModelRenderer Abdomen;
    public ModelRenderer RightArm;
    public ModelRenderer LeftArm;
    public ModelRenderer RightTigh;
    public ModelRenderer LeftTigh;
    public ModelRenderer RightLeg;
    public ModelRenderer LeftLeg;
    public ModelRenderer Tail;
    public ModelRenderer RightEar;
    public ModelRenderer LeftEar;
}
