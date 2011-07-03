// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import de.matthiasmann.twl.Widget;
import java.util.ArrayList;

// Referenced classes of package net.minecraft.src:
//            WidgetClassicTwocolumn, WidgetSimplewindow

public class ModSettingScreen
{

    public ModSettingScreen(String s)
    {
        this(s, s);
    }

    public ModSettingScreen(String s, String s1)
    {
        modscreens.add(this);
        buttontitle = s1;
        nicename = s;
        w = new WidgetClassicTwocolumn(new Widget[0]);
        thewidget = new WidgetSimplewindow(w, nicename);
    }

    public ModSettingScreen(Widget widget, String s)
    {
        modscreens.add(this);
        buttontitle = s;
        thewidget = widget;
    }

    public void append(Widget widget)
    {
        if(w != null)
        {
            w.add(widget);
        } else
        {
            thewidget.add(widget);
        }
    }

    public void remove(Widget widget)
    {
        if(w != null)
        {
            w.removeChild(widget);
        } else
        {
            thewidget.removeChild(widget);
        }
    }

    public static ArrayList modscreens = new ArrayList();
    public static String guicontext = "";
    public Widget thewidget;
    public WidgetClassicTwocolumn w;
    public String buttontitle;
    public String nicename;

}
