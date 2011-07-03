package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class RenderCaveOgre extends RenderOgre
{

    public RenderCaveOgre(ModelOgre2 modelogre2, ModelBase modelbase, float f)
    {
        super(modelogre2, modelbase, f);
        setRenderPassModel(modelbase);
        tempOgre = modelogre2;
    }

    protected boolean func_22003_b(EntityOgre entityogre, int i)
    {
        loadTexture("/mob/caveogreb.png");
        return i == 0 && !entityogre.ogreboolean;
    }

    private ModelOgre2 tempOgre;
}
