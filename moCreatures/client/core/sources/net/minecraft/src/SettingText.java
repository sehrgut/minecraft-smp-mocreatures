// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.HashMap;

// Referenced classes of package net.minecraft.src:
//            Setting, WidgetSetting, ModSettings

public class SettingText extends Setting<String>
{

    public SettingText(String s, String s1)
    {
        values.put("", s1);
        defvalue = s1;
        backendname = s;
    }

    public void fromString(String s, String s1)
    {
        values.put(s1, s);
        if(gui != null)
        {
            gui.update();
        }
    }

    public String toString(String s)
    {
        return get(s);
    }

    public String get(String s)
    {
        if(values.get(s) != null)
        {
            return (String)values.get(s);
        }
        if(values.get("") != null)
        {
            return (String)values.get("");
        } else
        {
            return (String)defvalue;
        }
    }

    public void set(String s, String s1)
    {
        values.put(s1, s);
        if(parent != null)
        {
            parent.save(s1);
        }
        if(gui != null)
        {
            gui.update();
        }
    }
}
