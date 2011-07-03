package moCreatures.models;

import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ModelSharkEgg extends ModelBase
{

    public ModelSharkEgg()
    {
        Egg = new ModelRenderer(0, 0);
        Egg.addBox(0.0F, 0.0F, 0.0F, 3, 1, 6, 0.0F);
        Egg.setRotationPoint(-2F, 23F, -2F);
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Egg.render(f5);
    }

    public ModelRenderer Egg;
}
