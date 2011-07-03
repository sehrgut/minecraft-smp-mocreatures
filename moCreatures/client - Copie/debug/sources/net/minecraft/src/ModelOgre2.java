package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ModelOgre2 extends ModelBiped
{

    public ModelOgre2()
    {
        super(0.0F, 0.0F);
        bipedHead = new ModelRenderer(0, 0);
        bipedHead.addBox(1.0F, -5F, -4F, 1, 1, 1, 0.0F);
        bipedHead.setPosition(0.0F, 0.0F, 0.0F);
        bipedEars = new ModelRenderer(0, 0);
        bipedEars.addBox(-4F, -1F, -4F, 1, 1, 1, 0.5F);
        bipedEars.setPosition(0.0F, 0.0F, 0.0F);
        bipedBody = new ModelRenderer(0, 16);
        bipedBody.addBox(-6F, -3F, -5F, 12, 8, 8, 2.3F);
        bipedBody.setPosition(0.0F, 0.0F, 0.0F);
        bipedRightArm = new ModelRenderer(40, 0);
        bipedRightArm.addBox(-7F, -2F, -2F, 4, 18, 4, 1.5F);
        bipedRightArm.setPosition(-5F, -14F, 0.0F);
        bipedLeftArm = new ModelRenderer(40, 0);
        bipedLeftArm.mirror = true;
        bipedLeftArm.addBox(3F, -2F, -2F, 4, 18, 4, 1.5F);
        bipedLeftArm.setPosition(5F, -14F, 0.0F);
        bipedRightLeg = new ModelRenderer(0, 0);
        bipedRightLeg.addBox(-2F, 9F, -6F, 4, 2, 8, 1.5F);
        bipedRightLeg.setPosition(-4F, 0.0F, 0.0F);
        bipedLeftLeg = new ModelRenderer(0, 0);
        bipedLeftLeg.mirror = true;
        bipedLeftLeg.addBox(-2F, 9F, -6F, 4, 2, 8, 1.5F);
        bipedLeftLeg.setPosition(4F, 0.0F, 0.0F);
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5, boolean flag)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5, flag);
        bipedHead.render(f5);
        bipedBody.render(f5);
        bipedRightArm.render(f5);
        bipedLeftArm.render(f5);
        bipedRightLeg.render(f5);
        bipedLeftLeg.render(f5);
        bipedEars.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean flag)
    {
        bipedHead.rotateAngleY = f3 / 57.29578F;
        bipedEars.rotateAngleY = bipedHead.rotateAngleY;
        bipedEars.rotateAngleX = bipedHead.rotateAngleX;
        bipedRightArm.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 2.0F * f1 * 0.5F;
        bipedLeftArm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
        bipedRightArm.rotateAngleZ = 0.0F;
        bipedLeftArm.rotateAngleZ = 0.0F;
        bipedRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        bipedLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
        bipedRightLeg.rotateAngleY = 0.0F;
        bipedLeftLeg.rotateAngleY = 0.0F;
        if(isRiding)
        {
            bipedRightArm.rotateAngleX += -0.6283186F;
            bipedLeftArm.rotateAngleX += -0.6283186F;
            bipedRightLeg.rotateAngleX = -1.256637F;
            bipedLeftLeg.rotateAngleX = -1.256637F;
            bipedRightLeg.rotateAngleY = 0.314159F;
            bipedLeftLeg.rotateAngleY = -0.314159F;
        }
        if(flag)
        {
            bipedLeftArm.rotateAngleX = 3F;
            bipedRightArm.rotateAngleX = 3F;
        }
        if(field_1279_h)
        {
            bipedLeftArm.rotateAngleX = bipedLeftArm.rotateAngleX * 0.5F - 0.314159F;
        }
        if(field_1278_i)
        {
            bipedRightArm.rotateAngleX = bipedRightArm.rotateAngleX * 0.5F - 0.314159F;
        }
        bipedRightArm.rotateAngleY = 0.0F;
        bipedLeftArm.rotateAngleY = 0.0F;
        if(onGround > -9990F)
        {
            float f6 = onGround;
            bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * 3.141593F * 2.0F) * 0.2F;
            bipedRightArm.offsetZ = MathHelper.sin(bipedBody.rotateAngleY) * 5F;
            bipedRightArm.offsetX = -MathHelper.cos(bipedBody.rotateAngleY) * 5F;
            bipedLeftArm.offsetZ = -MathHelper.sin(bipedBody.rotateAngleY) * 5F;
            bipedLeftArm.offsetX = MathHelper.cos(bipedBody.rotateAngleY) * 5F;
            bipedRightArm.rotateAngleY += bipedBody.rotateAngleY;
            bipedLeftArm.rotateAngleY += bipedBody.rotateAngleY;
            bipedLeftArm.rotateAngleX += bipedBody.rotateAngleY;
            f6 = 1.0F - onGround;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0F - f6;
            float f7 = MathHelper.sin(f6 * 3.141593F);
            float f8 = MathHelper.sin(onGround * 3.141593F) * -(bipedHead.rotateAngleX - 0.7F) * 0.75F;
            ModelRenderer modelrenderer = bipedRightArm;
            modelrenderer.rotateAngleX = (float)((double)modelrenderer.rotateAngleX - ((double)f7 * 1.2D + (double)f8));
            bipedRightArm.rotateAngleY += bipedBody.rotateAngleY * 2.0F;
            bipedRightArm.rotateAngleZ = MathHelper.sin(onGround * 3.141593F) * -0.4F;
        }
        if(isSneak)
        {
            bipedBody.rotateAngleX = 0.5F;
            bipedRightLeg.rotateAngleX -= 0.0F;
            bipedLeftLeg.rotateAngleX -= 0.0F;
            bipedRightArm.rotateAngleX += 0.4F;
            bipedLeftArm.rotateAngleX += 0.4F;
            bipedRightLeg.offsetZ = 4F;
            bipedLeftLeg.offsetZ = 4F;
            bipedRightLeg.offsetY = 9F;
            bipedLeftLeg.offsetY = 9F;
            bipedHead.offsetY = 1.0F;
        } else
        {
            bipedBody.rotateAngleX = 0.0F;
            bipedRightLeg.offsetZ = 0.0F;
            bipedLeftLeg.offsetZ = 0.0F;
            bipedRightLeg.offsetY = 12F;
            bipedLeftLeg.offsetY = 12F;
            bipedHead.offsetY = 0.0F;
        }
        bipedRightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
        bipedLeftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
        bipedRightArm.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
        bipedLeftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067F) * 0.05F;
    }
}
