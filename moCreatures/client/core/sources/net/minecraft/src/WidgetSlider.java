// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import de.matthiasmann.twl.ValueAdjusterFloat;
import de.matthiasmann.twl.model.FloatModel;

public class WidgetSlider extends ValueAdjusterFloat
{

    public WidgetSlider(FloatModel floatmodel)
    {
        super(floatmodel);
    }

    public void startEdit()
    {
        cancelEdit();
    }
}
