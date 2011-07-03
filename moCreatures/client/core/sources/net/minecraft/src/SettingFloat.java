// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.HashMap;

// Referenced classes of package net.minecraft.src:
//            Setting, WidgetSetting, ModSettings

public class SettingFloat extends Setting<Float>
{

    public SettingFloat(String s)
    {
        this(s, 0.0F, 0.0F, 0.1F, 1.0F);
    }

    public SettingFloat(String s, float f)
    {
        this(s, f, 0.0F, 0.1F, 1.0F);
    }

    public SettingFloat(String s, float f, float f1, float f2)
    {
        this(s, f, f1, 0.1F, f2);
    }

    public SettingFloat(String s, float f, float f1, float f2, float f3)
    {
        values.put("", Float.valueOf(f));
        defvalue = Float.valueOf(f);
        min = f1;
        step = f2;
        max = f3;
        backendname = s;
        if(min > max)
        {
            float f4 = min;
            min = max;
            max = f4;
        }
    }

    public String toString(String s)
    {
        return (new StringBuilder()).append(get(s)).toString();
    }

    public void fromString(String s, String s1)
    {
        values.put(s1, new Float(s));
        if(gui != null)
        {
            gui.update();
        }
    }

    public void set(Float float1, String s)
    {
        if(step > 0.0F)
        {
            values.put(s, Float.valueOf((float)Math.round(float1.floatValue() / step) * step));
        } else
        {
            values.put(s, float1);
        }
        if(parent != null)
        {
            parent.save(s);
        }
        if(gui != null)
        {
            gui.update();
        }
    }

    public Float get(String s)
    {
        if(values.get(s) != null)
        {
            return (Float)values.get(s);
        }
        if(values.get("") != null)
        {
            return (Float)values.get("");
        } else
        {
            return (Float)defvalue;
        }
    }

    public float step;
    public float min;
    public float max;
}
