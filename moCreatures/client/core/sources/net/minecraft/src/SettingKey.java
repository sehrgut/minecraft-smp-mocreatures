// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.HashMap;
import org.lwjgl.input.Keyboard;

// Referenced classes of package net.minecraft.src:
//            Setting, WidgetSetting, ModSettings

public class SettingKey extends Setting<Integer>
{

    public SettingKey(String s, int i)
    {
        defvalue = Integer.valueOf(i);
        values.put("", Integer.valueOf(i));
        backendname = s;
    }

    public SettingKey(String s, String s1)
    {
        this(s, Keyboard.getKeyIndex(s1));
    }

    public String toString(String s)
    {
        return Keyboard.getKeyName(get(s).intValue());
    }

    public void fromString(String s, String s1)
    {
        if(s.equals("UNBOUND"))
        {
            values.put(s1, Integer.valueOf(0));
        } else
        {
            values.put(s1, Integer.valueOf(Keyboard.getKeyIndex(s)));
        }
        if(gui != null)
        {
            gui.update();
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

    public void set(String s, String s1)
    {
        set(Integer.valueOf(Keyboard.getKeyIndex(s)), s1);
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

    public boolean isKeyDown(String s)
    {
        if(get(s).intValue() != -1)
        {
            return Keyboard.isKeyDown(get(s).intValue());
        } else
        {
            return false;
        }
    }

    public boolean isKeyDown()
    {
        return isKeyDown(ModSettings.currentContext);
    }
}
