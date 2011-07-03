// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.io.PrintStream;

// Referenced classes of package net.minecraft.src:
//            WidgetSimplewindow

/**
 * @deprecated Class WidgetClassicWindow is deprecated
 */

public class WidgetClassicWindow extends WidgetSimplewindow
{

    public WidgetClassicWindow()
    {
    }

    /**
     * @deprecated Method init is deprecated
     */

    public void init()
    {
        super.init();
        System.err.println("WidgetClassicWindow is deprecated, please update mods using it to use WSimplewindow");
    }
}
