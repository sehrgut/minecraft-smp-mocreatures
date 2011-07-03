package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ModelWerewolf extends ModelBase
{

    public ModelWerewolf()
    {
        Head = new ModelRenderer(0, 0);
        Head.addBox(-0.5F, -8.5F, -8F, 3, 3, 4, 1.6F);
        Head.setPosition(0.0F, 2.0F, -1F);
        RightEar = new ModelRenderer(15, 0);
        RightEar.addBox(-0.5F, -8.5F, -8F, 2, 4, 1, 0.0F);
        RightEar.setPosition(3.5F, -4F, 3.5F);
        RightEar.rotateAngleZ = -0.7853981F;
        RightEar.rotateAngleX = -0.5235988F;
        LeftEar = new ModelRenderer(15, 0);
        LeftEar.addBox(-0.5F, -8.5F, -8F, 2, 4, 1, 0.0F);
        LeftEar.setPosition(-2.5F, -4.5F, 3.5F);
        LeftEar.mirror = true;
        LeftEar.rotateAngleZ = 0.7853981F;
        LeftEar.rotateAngleX = -0.5235988F;
        Torso = new ModelRenderer(0, 7);
        Torso.addBox(-1.5F, -8F, -3.5F, 5, 4, 5, 2.4F);
        Torso.setPosition(0.0F, 2.0F, 0.0F);
        Torso.rotateAngleX = 1.047198F;
        Torso2 = new ModelRenderer(20, 7);
        Torso2.addBox(-1.5F, -8F, -3.5F, 5, 4, 5, 2.4F);
        Torso2.setPosition(0.0F, 3.5F, 12F);
        Torso2.rotateAngleX = 0.6108652F;
        Abdomen = new ModelRenderer(0, 16);
        Abdomen.addBox(-1F, 2.5F, 6F, 4, 5, 4, 2.4F);
        Abdomen.setPosition(0.0F, 2.0F, 0.0F);
        Abdomen.rotateAngleX = 0.4363323F;
        RightArm = new ModelRenderer(48, 0);
        RightArm.addBox(-8F, 0.0F, 0.0F, 4, 16, 4, 0.0F);
        RightArm.setPosition(0.0F, -6F, 0.0F);
        LeftArm = new ModelRenderer(48, 0);
        LeftArm.addBox(6F, 0.0F, 0.0F, 4, 16, 4, 0.0F);
        LeftArm.setPosition(0.0F, -6F, 0.0F);
        LeftArm.mirror = true;
        RightTigh = new ModelRenderer(32, 20);
        RightTigh.addBox(0.0F, 0.0F, 0.0F, 4, 8, 4, 0.0F);
        RightTigh.setPosition(-3.5F, 8F, 9F);
        RightTigh.rotateAngleX = -0.7853981F;
        LeftTigh = new ModelRenderer(32, 20);
        LeftTigh.addBox(0.0F, 0.0F, 0.0F, 4, 8, 4, 0.0F);
        LeftTigh.setPosition(1.5F, 8F, 9F);
        LeftTigh.mirror = true;
        LeftTigh.rotateAngleX = -0.7853981F;
        RightLeg = new ModelRenderer(48, 20);
        RightLeg.addBox(2.0F, 8F, 0.0F, 4, 8, 4, 0.0F);
        RightLeg.setPosition(-5.5F, 6F, -3F);
        LeftLeg = new ModelRenderer(48, 20);
        LeftLeg.addBox(2.0F, 8F, 0.0F, 4, 8, 4, 0.0F);
        LeftLeg.setPosition(-0.5F, 6F, -3F);
        LeftLeg.mirror = true;
        Tail = new ModelRenderer(9, 22);
        Tail.addBox(0.0F, -2F, 16F, 2, 2, 8, 1.0F);
        Tail.setPosition(0.0F, 2.0F, 0.0F);
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
        RightEar.offsetX = 3.5F - Head.rotateAngleY * 2.0F;
        LeftEar.rotateAngleY = Head.rotateAngleX;
        LeftEar.rotateAngleX = -0.5235988F + Head.rotateAngleY / 4F;
        LeftEar.offsetX = -2.5F - Head.rotateAngleY * 2.0F;
        RightArm.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 2.0F * f1 * 0.5F;
        LeftArm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
        RightArm.rotateAngleZ = 0.0F;
        LeftArm.rotateAngleZ = 0.0F;
        RightTigh.rotateAngleX = -0.7853981F + (MathHelper.cos(f * 0.6662F) * 1.4F * f1) / 3F;
        LeftTigh.rotateAngleX = -0.7853981F + (MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1) / 3F;
        RightLeg.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1) / 2.0F;
        RightLeg.offsetZ = 3F + (MathHelper.cos(f * 0.6662F) * 1.4F * f1) / 10F;
        RightLeg.offsetY = 7.5F + MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        LeftLeg.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1) / 2.0F;
        LeftLeg.offsetZ = 3F + (MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1) / 10F;
        LeftLeg.offsetY = 7.5F + MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
        RightLeg.rotateAngleY = 0.0F;
        LeftLeg.rotateAngleY = 0.0F;
        if(flag)
        {
            Tail.rotateAngleX = 0.261799F;
            Tail.offsetY = 10F;
            Tail.offsetZ = -2F;
            Abdomen.rotateAngleX = 1.308997F;
            Abdomen.offsetY = 13F;
            Abdomen.offsetZ = 0.0F;
            Torso.rotateAngleX = 1.919862F;
            Torso.offsetY = 4F;
            Torso.offsetZ = 1.0F;
            Torso2.rotateAngleX = 1.570796F;
            Torso2.offsetY = 5F;
            Torso2.offsetZ = 8.5F;
            RightArm.offsetY = 8F;
            RightArm.offsetZ = -6F;
            LeftArm.offsetY = 8F;
            LeftArm.offsetZ = -6F;
            Head.offsetY = 16F;
            Head.offsetZ = -7F;
            RightEar.offsetY = 12.5F;
            RightEar.offsetZ = -6F - Head.rotateAngleY * 2.0F;
            LeftEar.offsetY = 12F;
            LeftEar.offsetZ = -6F - Head.rotateAngleY * 2.0F;
        } else
        {
            Tail.rotateAngleX = -0.4363323F;
            Tail.offsetY = 1.0F;
            Tail.offsetZ = -1F;
            Abdomen.rotateAngleX = 0.4363323F;
            Abdomen.offsetY = 2.0F;
            Abdomen.offsetZ = 0.0F;
            Torso.rotateAngleX = 1.047198F;
            Torso.offsetY = -3F;
            Torso.offsetZ = 9F;
            Torso2.rotateAngleX = 0.6108652F;
            Torso2.offsetY = 3.5F;
            Torso2.offsetZ = 12F;
            RightArm.offsetY = -7F;
            RightArm.offsetZ = 0.0F;
            LeftArm.offsetY = -7F;
            LeftArm.offsetZ = 0.0F;
            Head.offsetY = -0.5F;
            Head.offsetZ = 1.5F;
            RightEar.offsetY = -4F;
            RightEar.offsetZ = 2.5F - Head.rotateAngleY * 2.0F;
            LeftEar.offsetY = -4.5F;
            LeftEar.offsetZ = 2.5F - Head.rotateAngleY * 2.0F;
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
