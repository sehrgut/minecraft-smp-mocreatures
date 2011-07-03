// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import de.matthiasmann.twl.Widget;

// Referenced classes of package net.minecraft.src:
//            WidgetClassicTwocolumn

public class WidgetSinglecolumn extends WidgetClassicTwocolumn
{

    public WidgetSinglecolumn(Widget awidget[])
    {
        super(awidget);
        defaultwidth = 200;
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
            widget.setPosition((getX() + getWidth() / 2) - widget.getWidth() / 2, getY() + 24 * i);
        }

    }

    public int getPreferredWidth()
    {
        return Math.max(getParent().getWidth(), defaultwidth);
    }

    public int getPreferredHeight()
    {
        return 24 * getNumChildren() + 1;
    }
}
