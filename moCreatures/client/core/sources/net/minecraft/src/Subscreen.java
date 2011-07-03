// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.SimpleButtonModel;
import java.util.ArrayList;

// Referenced classes of package net.minecraft.src:
//            WidgetClassicTwocolumn, WidgetSimplewindow, GuiModScreen

public class Subscreen extends Button
    implements Runnable
{

    public Subscreen(String s, String s1)
    {
        super(s1);
        children = new ArrayList();
        setTheme("/button");
        subsubWindow = new WidgetClassicTwocolumn(new Widget[0]);
        subscreenwindow = new WidgetSimplewindow(subsubWindow, s);
        SimpleButtonModel simplebuttonmodel = new SimpleButtonModel();
        simplebuttonmodel.addActionCallback(this);
        setModel(simplebuttonmodel);
    }

    public Subscreen(String s, String s1, Widget widget)
    {
        super(s1);
        children = new ArrayList();
        setTheme("/button");
        subsubWindow = widget;
        subscreenwindow = new WidgetSimplewindow(subsubWindow, s);
        SimpleButtonModel simplebuttonmodel = new SimpleButtonModel();
        simplebuttonmodel.addActionCallback(this);
        setModel(simplebuttonmodel);
    }

    public Subscreen(String s, Widget widget)
    {
        super(s);
        children = new ArrayList();
        setTheme("/button");
        subsubWindow = widget;
        subscreenwindow = widget;
        SimpleButtonModel simplebuttonmodel = new SimpleButtonModel();
        simplebuttonmodel.addActionCallback(this);
        setModel(simplebuttonmodel);
    }

    public void add(Widget widget)
    {
        subsubWindow.add(widget);
    }

    public void run()
    {
        GuiModScreen.show(subscreenwindow);
    }

    public ArrayList children;
    public Widget subscreenwindow;
    public Widget subsubWindow;
}
