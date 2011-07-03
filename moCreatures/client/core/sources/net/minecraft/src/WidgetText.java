// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.model.StringModel;

// Referenced classes of package net.minecraft.src:
//            WidgetSetting, SettingText, ModSettings, ModSettingScreen, 
//            GuiModScreen

public class WidgetText extends WidgetSetting
    implements StringModel
{

    public WidgetText(SettingText settingtext, String s)
    {
        super(s);
        setmode = 0;
        setTheme("");
        value = settingtext;
        value.gui = this;
        ModSettings.dbgout("0");
        e = new EditField();
        ModSettings.dbgout("1");
        ModSettings.dbgout("2");
        add(e);
        l = new Label();
        l.setText(String.format("%s: ", new Object[] {
            nicename
        }));
        add(l);
        e.setModel(this);
        update();
        ModSettings.dbgout("3");
    }

    public void layout()
    {
        l.setPosition(getX(), (getY() + getHeight() / 2) - l.computeTextHeight() / 2);
        l.setSize(l.computeTextWidth(), l.computeTextHeight());
        e.setPosition(getX() + l.computeTextWidth(), getY());
        e.setSize(getWidth() - l.computeTextWidth(), getHeight());
    }

    public String userString()
    {
        return String.format("%s: %s", new Object[] {
            nicename, value.get(ModSettingScreen.guicontext)
        });
    }

    public void addCallback(Runnable runnable)
    {
        ModSettings.dbgout("TextinputSetting.addcallback is a noop right now");
    }

    public String getValue()
    {
        return (String)value.get();
    }

    public void removeCallback(Runnable runnable)
    {
        ModSettings.dbgout("TextinputSetting.removecallback is a noop right now");
    }

    public void setValue(String s)
    {
        GuiModScreen.clicksound();
        ModSettings.dbgout(String.format("setvalue %s", new Object[] {
            e.getText()
        }));
        if(setmode <= 0)
        {
            setmode = -1;
            value.set(e.getText(), ModSettingScreen.guicontext);
            setmode = 0;
        }
    }

    public void update()
    {
        ModSettings.dbgout("update");
        l.setText(String.format("%s: ", new Object[] {
            nicename
        }));
        if(setmode >= 0)
        {
            setmode = 1;
            e.setText(value.get(ModSettingScreen.guicontext));
            setmode = 0;
        }
        ModSettings.dbgout(String.format("update %s", new Object[] {
            e.getText()
        }));
    }

    public SettingText value;
    public EditField e;
    public Label l;
    public int setmode;
}
