// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import de.matthiasmann.twl.model.SimpleFloatModel;

// Referenced classes of package net.minecraft.src:
//            WidgetSetting, SettingFloat, WidgetSlider, ModSettingScreen

public class WidgetFloat extends WidgetSetting
    implements Runnable
{

    public WidgetFloat(SettingFloat settingfloat, String s)
    {
        this(settingfloat, s, 2, "");
    }

    public WidgetFloat(SettingFloat settingfloat, String s, int i)
    {
        this(settingfloat, s, i, "");
    }

    public WidgetFloat(SettingFloat settingfloat, String s, int i, String s1)
    {
        super(s);
        setTheme("");
        displaylen = i;
        formatstring = s1;
        value = settingfloat;
        value.gui = this;
        SimpleFloatModel simplefloatmodel = new SimpleFloatModel(value.min, value.max, ((Float)value.get()).floatValue());
        simplefloatmodel.addCallback(this);
        slider = new WidgetSlider(simplefloatmodel);
        if(value.step > 0.0F && value.step <= value.max)
        {
            slider.setStepSize(value.step);
        }
        slider.setFormat(String.format("%s: %%.%df", new Object[] {
            nicename, Integer.valueOf(displaylen)
        }));
        add(slider);
        update();
    }

    public String userString()
    {
        String s = String.format("%02d", new Object[] {
            Integer.valueOf(displaylen)
        });
        return String.format((new StringBuilder("%s: %.")).append(s).append("f").toString(), new Object[] {
            nicename, value
        });
    }

    public void update()
    {
        slider.setValue(value.get(ModSettingScreen.guicontext).floatValue());
        slider.setFormat(String.format("%s: %%.%df", new Object[] {
            nicename, Integer.valueOf(displaylen)
        }));
    }

    public void run()
    {
        value.set(Float.valueOf(slider.getValue()), ModSettingScreen.guicontext);
    }

    public int displaylen;
    public String formatstring;
    public WidgetSlider slider;
    public SettingFloat value;
}
