// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.HashMap;

// Referenced classes of package net.minecraft.src:
//            Setting, WidgetSetting, ModSettings

public class SettingBoolean extends Setting<Boolean>
{

    public SettingBoolean(String s, Boolean boolean1)
    {
        defvalue = boolean1;
        values.put("", (Boolean)defvalue);
        backendname = s;
    }

    public SettingBoolean(String s)
    {
        this(s, Boolean.valueOf(false));
    }

    public String toString(String s)
    {
        return get(s).booleanValue() ? "true" : "false";
    }

    public void fromString(String s, String s1)
    {
        values.put(s1, Boolean.valueOf(s.equals("true")));
        if(gui != null)
        {
            gui.update();
        }
    }

    public Boolean get(String s)
    {
        if(values.get(s) != null)
        {
            return (Boolean)values.get(s);
        }
        if(values.get("") != null)
        {
            return (Boolean)values.get("");
        } else
        {
            return (Boolean)defvalue;
        }
    }

    public void set(Boolean boolean1, String s)
    {
        values.put(s, boolean1);
        if(parent != null)
        {
            parent.save(s);
        }
        if(gui != null)
        {
            gui.update();
        }
    }
}
