// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.model.SimpleButtonModel;

// Referenced classes of package net.minecraft.src:
//            WidgetSetting, SettingMulti, ModSettingScreen, ModSettings, 
//            GuiModScreen

public class WidgetMulti extends WidgetSetting
    implements Runnable
{

    public WidgetMulti(SettingMulti settingmulti, String s)
    {
        super(s);
        b = null;
        setTheme("");
        value = settingmulti;
        value.gui = this;
        SimpleButtonModel simplebuttonmodel = new SimpleButtonModel();
        b = new Button(simplebuttonmodel);
        simplebuttonmodel.addActionCallback(this);
        add(b);
        update();
    }

    public String userString()
    {
        if(nicename.length() > 0)
        {
            return String.format("%s: %s", new Object[] {
                nicename, value.getLabel(ModSettingScreen.guicontext)
            });
        } else
        {
            return value.getLabel(ModSettingScreen.guicontext);
        }
    }

    public void update()
    {
        b.setText(userString());
        ModSettings.dbgout((new StringBuilder("multi update ")).append(userString()).toString());
    }

    public void run()
    {
        value.next(ModSettingScreen.guicontext);
        update();
        GuiModScreen.clicksound();
    }

    public SettingMulti value;
    public Button b;
}
