// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import de.matthiasmann.twl.Widget;

public class WidgetClassicTwocolumn extends Widget
{

    public WidgetClassicTwocolumn(Widget awidget[])
    {
        defaultwidth = 150;
        defaultheight = 20;
        defaultpad = 4;
        overridewidth = true;
        overrideheight = true;
        split = 10;
        for(int i = 0; i < awidget.length; i++)
        {
            add(awidget[i]);
        }

        setTheme("");
    }

    public void layout()
    {
        for(int i = 0; i < getNumChildren(); i++)
        {
            Widget widget = getChild(i);
            int j = defaultheight;
            if(!overrideheight)
            {
                j = widget.getPreferredHeight();
            }
            int k = defaultwidth;
            if(!overridewidth)
            {
                k = widget.getPreferredWidth();
            }
            widget.setSize(k, j);
            if(i % 2 == 0)
            {
                widget.setPosition((getX() + getWidth() / 2) - (150 + split / 2), getY() + (defaultheight + defaultpad) * (i >> 1));
            } else
            {
                widget.setPosition(getX() + getWidth() / 2 + split / 2, getY() + (defaultheight + defaultpad) * (i >> 1));
            }
        }

    }

    public int getPreferredWidth()
    {
        return getParent().getWidth();
    }

    public int getPreferredHeight()
    {
        return (defaultheight + defaultpad) * (1 * (getNumChildren() + 1) >> 1);
    }

    public int defaultwidth;
    public int defaultheight;
    public int defaultpad;
    public boolean overridewidth;
    public boolean overrideheight;
    public int split;
}
