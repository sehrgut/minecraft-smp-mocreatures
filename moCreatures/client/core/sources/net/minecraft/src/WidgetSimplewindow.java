// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import de.matthiasmann.twl.*;
import de.matthiasmann.twl.model.ButtonModel;
import de.matthiasmann.twl.model.SimpleButtonModel;

// Referenced classes of package net.minecraft.src:
//            ModCallback, WidgetSingleRow, GuiWidgetScreen

public class WidgetSimplewindow extends Widget
{

    protected WidgetSimplewindow()
    {
        Title = "";
        mainwidget = null;
        TitleWidget = null;
        BackButton = null;
        buttonbar = null;
        Title = "test";
        setTheme("");
        init();
    }

    public WidgetSimplewindow(Widget widget)
    {
        Title = "";
        mainwidget = null;
        TitleWidget = null;
        BackButton = null;
        buttonbar = null;
        ScrollPane scrollpane = new ScrollPane(widget);
        scrollpane.setFixed(de.matthiasmann.twl.ScrollPane.Fixed.HORIZONTAL);
        mainwidget = scrollpane;
        Title = "test";
        setTheme("");
        init();
    }

    public WidgetSimplewindow(Widget widget, String s)
    {
        Title = "";
        mainwidget = null;
        TitleWidget = null;
        BackButton = null;
        buttonbar = null;
        ScrollPane scrollpane = new ScrollPane(widget);
        scrollpane.setFixed(de.matthiasmann.twl.ScrollPane.Fixed.HORIZONTAL);
        mainwidget = scrollpane;
        Title = s;
        setTheme("");
        init();
    }

    protected void init()
    {
        TitleWidget = new Label(Title);
        add(TitleWidget);
        BackButton = new Button(new SimpleButtonModel());
        BackButton.getModel().addActionCallback(new ModCallback(0, null));
        BackButton.setText("Back");
        buttonbar = new WidgetSingleRow(200, 20, new Widget[] {
            BackButton
        });
        add(buttonbar);
        add(mainwidget);
    }

    public void layout()
    {
        int i = 1;
        buttonbar.setSize(buttonbar.getPreferredWidth(), buttonbar.getPreferredHeight());
        buttonbar.setPosition(GuiWidgetScreen.screenwidth / 2 - buttonbar.getPreferredWidth() / 2, GuiWidgetScreen.screenheight - (buttonbar.getPreferredHeight() + 4));
        TitleWidget.setPosition(GuiWidgetScreen.screenwidth / 2 - TitleWidget.computeTextWidth() / 2, 10 * i);
        TitleWidget.setSize(TitleWidget.computeTextWidth(), TitleWidget.computeTextHeight());
        int j = 30 * i;
        int k = 40 * i;
        mainwidget.setPosition(j, k);
        mainwidget.setSize(GuiWidgetScreen.screenwidth - j * 2, GuiWidgetScreen.screenheight - k * 2);
    }

    public String Title;
    public Widget mainwidget;
    public Label TitleWidget;
    public Button BackButton;
    public WidgetSingleRow buttonbar;
}
