// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.HashMap;

// Referenced classes of package net.minecraft.src:
//            Setting, ModSettings, WidgetSetting

public class SettingMulti extends Setting<Integer>
{

    public SettingMulti(String s, int i, String as[])
    {
        if(as.length == 0)
        {
            return;
        } else
        {
            values.put("", Integer.valueOf(i));
            defvalue = Integer.valueOf(i);
            labels = as;
            backendname = s;
            return;
        }
    }

    public SettingMulti(String s, String as[])
    {
        this(s, 0, as);
    }

    public String toString(String s)
    {
        return labels[get(s).intValue()];
    }

    public void fromString(String s, String s1)
    {
        int i = -1;
        for(int j = 0; j < labels.length; j++)
        {
            if(labels[j].equals(s))
            {
                i = j;
            }
        }

        if(i != -1)
        {
            values.put(s1, Integer.valueOf(i));
        } else
        {
            values.put(s1, Integer.valueOf((new Float(s)).intValue()));
        }
        ModSettings.dbgout((new StringBuilder("fromstring multi ")).append(s).toString());
        if(gui != null)
        {
            gui.update();
        }
    }

    public void set(String s, String s1)
    {
        int i = -1;
        for(int j = 0; j < labels.length; j++)
        {
            if(labels[j].equals(s))
            {
                i = j;
            }
        }

        if(i != -1)
        {
            set(Integer.valueOf(i), s1);
        }
    }

    public void set(Integer integer, String s)
    {
        values.put(s, integer);
        if(parent != null)
        {
            parent.save(s);
        }
        if(gui != null)
        {
            gui.update();
        }
    }

    public void set(String s)
    {
        set(s, ModSettings.currentContext);
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

    public String getLabel(String s)
    {
        return labels[get(s).intValue()];
    }

    public String getLabel()
    {
        return labels[((Integer)get()).intValue()];
    }

    public void next(String s)
    {
        int i;
        for(i = get(s).intValue() + 1; i >= labels.length; i -= labels.length) { }
        set(Integer.valueOf(i), s);
    }

    public void next()
    {
        next(ModSettings.currentContext);
    }

    public String labels[];
}
