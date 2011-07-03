// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import de.matthiasmann.twl.Widget;
import java.util.HashMap;

// Referenced classes of package net.minecraft.src:
//            ModSettings, WidgetSetting

public abstract class Setting<T> extends Widget
{

    public Setting()
    {
        values = new HashMap();
        gui = null;
        parent = null;
    }

    public abstract String toString(String s);

    public abstract void fromString(String s, String s1);

    public void reset()
    {
        reset(ModSettings.currentContext);
    }

    public void reset(String s)
    {
        set(defvalue, s);
    }

    public void copyContext(String s, String s1)
    {
        values.put(s1, values.get(s));
    }

    public abstract void set(T obj, String s);

    public void set(T obj)
    {
        set(obj, ModSettings.currentContext);
    }

    public abstract T get(String s);

    public T get()
    {
        return get(ModSettings.currentContext);
    }

    public HashMap<String, T> values;
    public T defvalue;
    public String backendname;
    public WidgetSetting gui;
    public ModSettings parent;
}
