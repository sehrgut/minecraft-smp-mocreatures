// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import de.matthiasmann.twl.Widget;
import java.util.ArrayList;

public class WidgetSingleRow extends Widget
{

    public WidgetSingleRow(int i, int j, Widget awidget[])
    {
        xSpacing = 3;
        defaultwidth = 150;
        defaultheight = 20;
        overridewidth = false;
        overrideheight = false;
        wiggets = new ArrayList();
        heights = new ArrayList();
        widths = new ArrayList();
        setTheme("");
        defaultwidth = i;
        defaultheight = j;
        for(int k = 0; k < awidget.length; k++)
        {
            add(awidget[k]);
        }

    }

    public void add(Widget widget)
    {
        add(widget, defaultwidth, defaultheight);
    }

    public void add(Widget widget, int i, int j)
    {
        wiggets.add(widget);
        heights.add(Integer.valueOf(j));
        widths.add(Integer.valueOf(i));
        super.add(widget);
    }

    public boolean removeChild(Widget widget)
    {
        int i = wiggets.indexOf(widget);
        wiggets.remove(i);
        heights.remove(i);
        widths.remove(i);
        return super.removeChild(widget);
    }

    public Widget removeChild(int i)
    {
        wiggets.remove(i);
        heights.remove(i);
        widths.remove(i);
        return super.removeChild(i);
    }

    private int getHeight(int i)
    {
        if(((Integer)heights.get(i)).intValue() >= 0)
        {
            return ((Integer)heights.get(i)).intValue();
        } else
        {
            return ((Widget)wiggets.get(i)).getPreferredHeight();
        }
    }

    private int getWidth(int i)
    {
        if(((Integer)widths.get(i)).intValue() >= 0)
        {
            return ((Integer)widths.get(i)).intValue();
        } else
        {
            return ((Widget)wiggets.get(i)).getPreferredWidth();
        }
    }

    public int getPreferredWidth()
    {
        int i = (widths.size() - 1) * xSpacing;
        i = i < 0 ? 0 : i;
        for(int j = 0; j < widths.size(); j++)
        {
            i += getWidth(j);
        }

        return i;
    }

    public int getPreferredHeight()
    {
        int i = 0;
        for(int j = 0; j < heights.size(); j++)
        {
            if(getHeight(j) > i)
            {
                i = getHeight(j);
            }
        }

        return i;
    }

    public void layout()
    {
        int i = 0;
        for(int j = 0; j < wiggets.size(); j++)
        {
            Widget widget = (Widget)wiggets.get(j);
            widget.setPosition(i + getX(), getY());
            widget.setSize(getWidth(j), getHeight(j));
            i += getWidth(j) + xSpacing;
        }

    }

    public int xSpacing;
    public int defaultwidth;
    public int defaultheight;
    public boolean overridewidth;
    public boolean overrideheight;
    protected ArrayList wiggets;
    protected ArrayList heights;
    protected ArrayList widths;
}
