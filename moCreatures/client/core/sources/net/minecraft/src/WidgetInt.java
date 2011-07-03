// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import de.matthiasmann.twl.model.SimpleFloatModel;

// Referenced classes of package net.minecraft.src:
//            WidgetSetting, SettingInt, WidgetSlider, ModSettingScreen, 
//            ModSettings

public class WidgetInt extends WidgetSetting
    implements Runnable
{

    public WidgetInt(SettingInt settingint, String s1)
    {
        this(settingint, s1, 15);
    }

    public WidgetInt(SettingInt settingint, String s1, int i)
    {
        super(s1);
        setTheme("");
        displaylen = i;
        value = settingint;
        value.gui = this;
        SimpleFloatModel simplefloatmodel = new SimpleFloatModel(value.min, value.max, ((Integer)value.get()).intValue());
        s = new WidgetSlider(simplefloatmodel);
        s.setFormat(String.format("%s: %%.0f", new Object[] {
            nicename
        }));
        if(value.step > 1 && value.step <= value.max)
        {
            s.setStepSize(value.step);
        }
        simplefloatmodel.addCallback(this);
        add(s);
        update();
    }

    public String userString()
    {
        String s1 = String.format("%d", new Object[] {
            Integer.valueOf(displaylen)
        });
        return String.format((new StringBuilder("%s: %.")).append(s1).append("d").toString(), new Object[] {
            nicename, value.get(ModSettingScreen.guicontext)
        });
    }

    public void update()
    {
        s.setValue(value.get(ModSettingScreen.guicontext).intValue());
        s.setFormat(String.format("%s: %%.0f", new Object[] {
            nicename
        }));
        ModSettings.dbgout((new StringBuilder("update ")).append(value.get(ModSettingScreen.guicontext)).append(" -> ").append((int)s.getValue()).toString());
    }

    public void run()
    {
        ModSettings.dbgout((new StringBuilder("run ")).append((int)s.getValue()).toString());
        value.set(Integer.valueOf((int)s.getValue()), ModSettingScreen.guicontext);
    }

    public int displaylen;
    public String formatstring;
    public WidgetSlider s;
    public SettingInt value;
}
