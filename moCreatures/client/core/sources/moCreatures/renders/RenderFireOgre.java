package moCreatures.renders;

import net.minecraft.src.ModelBase;
import moCreatures.entities.EntityOgre;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
import moCreatures.models.ModelOgre2;


public class RenderFireOgre extends RenderOgre
{

    public RenderFireOgre(ModelOgre2 modelogre2, ModelBase modelbase, float f)
    {
        super(modelogre2, modelbase, f);
        setRenderPassModel(modelbase);
    }

    protected boolean func_22003_b(EntityOgre entityogre, int i)
    {
        loadTexture("/moCreatures/textures/fireogreb.png");
        return i == 0 && !entityogre.ogreboolean;
    }
}
