// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.model.SimpleButtonModel;

// Referenced classes of package net.minecraft.src:
//            WidgetSetting, SettingBoolean, ModSettingScreen, GuiModScreen

public class WidgetBoolean extends WidgetSetting
    implements Runnable
{

    public WidgetBoolean(SettingBoolean settingboolean, String s)
    {
        this(settingboolean, s, "true", "false");
    }

    public WidgetBoolean(SettingBoolean settingboolean, String s, String s1, String s2)
    {
        super(s);
        value = null;
        setTheme("");
        ttext = s1;
        ftext = s2;
        SimpleButtonModel simplebuttonmodel = new SimpleButtonModel();
        b = new Button(simplebuttonmodel);
        simplebuttonmodel.addActionCallback(this);
        add(b);
        value = settingboolean;
        value.gui = this;
        update();
    }

    public String userString()
    {
        if(value != null)
        {
            if(nicename.length() > 0)
            {
                return String.format("%s: %s", new Object[] {
                    nicename, value.get(ModSettingScreen.guicontext).booleanValue() ? ttext : ftext
                });
            } else
            {
                return value.get(ModSettingScreen.guicontext).booleanValue() ? ttext : ftext;
            }
        }
        if(nicename.length() > 0)
        {
            return String.format("%s: %s", new Object[] {
                nicename, "no value"
            });
        } else
        {
            return "no value or title";
        }
    }

    public void update()
    {
        b.setText(userString());
    }

    public void run()
    {
        if(value != null)
        {
            value.set(Boolean.valueOf(!value.get(ModSettingScreen.guicontext).booleanValue()), ModSettingScreen.guicontext);
        }
        update();
        GuiModScreen.clicksound();
    }

    public String ttext;
    public String ftext;
    public Button b;
    public SettingBoolean value;
}
