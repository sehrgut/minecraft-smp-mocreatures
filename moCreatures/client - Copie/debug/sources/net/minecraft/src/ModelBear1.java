package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ModelBear1 extends ModelQuadruped
{

    public ModelBear1()
    {
        super(12, 0.0F);
        head = new ModelRenderer(0, 0);
        head.addBox(-5F, -8F, -2F, 10, 4, 1, 0.0F);
        head.setPosition(0.0F, 4F, -8F);
        body = new ModelRenderer(20, 0);
        body.addBox(-2F, 9F, -4F, 4, 2, 4, 0.0F);
        body.setPosition(0.0F, 5F, 2.0F);
    }
}
