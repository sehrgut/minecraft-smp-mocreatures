// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import de.matthiasmann.twl.Widget;
import java.util.ArrayList;

public abstract class WidgetSetting extends Widget
{

    public WidgetSetting(String s)
    {
        nicename = s;
        all.add(this);
    }

    public void add(Widget widget)
    {
        String s = widget.getTheme();
        if(s.length() == 0)
        {
            widget.setTheme("/-defaults");
        } else
        if(!s.substring(0, 1).equals("/"))
        {
            widget.setTheme((new StringBuilder("/")).append(s).toString());
        }
        super.add(widget);
    }

    public void layout()
    {
        for(int i = 0; i < getNumChildren(); i++)
        {
            Widget widget = getChild(i);
            widget.setPosition(getX(), getY());
            widget.setSize(getWidth(), getHeight());
        }

    }

    public static void updateAll()
    {
        for(int i = 0; i < all.size(); i++)
        {
            ((WidgetSetting)all.get(i)).update();
        }

    }

    public abstract void update();

    public String nicename;
    public static ArrayList all = new ArrayList();

}
