// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.HashMap;

// Referenced classes of package net.minecraft.src:
//            Setting, WidgetSetting, ModSettings

public class SettingInt extends Setting<Integer>
{

    public SettingInt(String s)
    {
        this(s, 0, 0, 1, 100);
    }

    public SettingInt(String s, int i)
    {
        this(s, i, 0, 1, 100);
    }

    public SettingInt(String s, int i, int j, int k)
    {
        this(s, i, j, 1, k);
    }

    public SettingInt(String s, int i, int j, int k, int l)
    {
        values.put("", Integer.valueOf(i));
        defvalue = Integer.valueOf(i);
        min = j;
        step = k;
        max = l;
        backendname = s;
        if(min > max)
        {
            int i1 = min;
            min = max;
            max = i1;
        }
    }

    public String toString(String s)
    {
        return (new StringBuilder()).append(get(s)).toString();
    }

    public void fromString(String s, String s1)
    {
        values.put(s1, new Integer(s));
        if(gui != null)
        {
            gui.update();
        }
        ModSettings.dbgout((new StringBuilder("fromstring ")).append(s).toString());
    }

    public void set(Integer integer, String s)
    {
        ModSettings.dbgout((new StringBuilder("set ")).append(integer).toString());
        if(step > 1)
        {
            values.put(s, Integer.valueOf((int)((float)Math.round((float)integer.intValue() / (float)step) * (float)step)));
        } else
        {
            values.put(s, integer);
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

    public Integer get(String s)
    {
        if(values.get(s) != null)
        {
            return (Integer)values.get(s);
        }
        if(values.get("") != null)
        {
            return (Integer)values.get("");
        } else
        {
            return (Integer)defvalue;
        }
    }

    public int step;
    public int min;
    public int max;
}
