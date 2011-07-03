// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import de.matthiasmann.twl.Widget;
import java.io.*;
import java.util.*;
import net.minecraft.client.Minecraft;

// Referenced classes of package net.minecraft.src:
//            SettingBoolean, WidgetBoolean, ModSettingScreen, SettingFloat, 
//            WidgetFloat, SettingInt, WidgetInt, SettingKey, 
//            WidgetKeybinding, SettingMulti, WidgetMulti, SettingText, 
//            WidgetText, Setting

public class ModSettings
{

    public ModSettings(String s)
    {
        have_loaded = false;
        backendname = s;
        Settings = new ArrayList();
        all.add(this);
    }

    public SettingBoolean addSetting(ModSettingScreen modsettingscreen, String s, String s1, boolean flag)
    {
        SettingBoolean settingboolean = new SettingBoolean(s1, Boolean.valueOf(flag));
        WidgetBoolean widgetboolean = new WidgetBoolean(settingboolean, s);
        modsettingscreen.append(widgetboolean);
        append(settingboolean);
        return settingboolean;
    }

    public SettingBoolean addSetting(ModSettingScreen modsettingscreen, String s, String s1, boolean flag, String s2, String s3)
    {
        SettingBoolean settingboolean = new SettingBoolean(s1, Boolean.valueOf(flag));
        WidgetBoolean widgetboolean = new WidgetBoolean(settingboolean, s, s2, s3);
        modsettingscreen.append(widgetboolean);
        append(settingboolean);
        return settingboolean;
    }

    public SettingFloat addSetting(ModSettingScreen modsettingscreen, String s, String s1, float f)
    {
        SettingFloat settingfloat = new SettingFloat(s1, f);
        WidgetFloat widgetfloat = new WidgetFloat(settingfloat, s);
        modsettingscreen.append(widgetfloat);
        append(settingfloat);
        return settingfloat;
    }

    public SettingFloat addSetting(ModSettingScreen modsettingscreen, String s, String s1, float f, float f1, float f2, float f3)
    {
        SettingFloat settingfloat = new SettingFloat(s1, f, f1, f2, f3);
        WidgetFloat widgetfloat = new WidgetFloat(settingfloat, s);
        modsettingscreen.append(widgetfloat);
        append(settingfloat);
        return settingfloat;
    }

    public SettingInt addSetting(ModSettingScreen modsettingscreen, String s, String s1, int i, int j, int k)
    {
        SettingInt settingint = new SettingInt(s1, i, j, 1, k);
        WidgetInt widgetint = new WidgetInt(settingint, s);
        modsettingscreen.append(widgetint);
        append(settingint);
        return settingint;
    }

    public SettingInt addSetting(ModSettingScreen modsettingscreen, String s, String s1, int i, int j, int k, int l)
    {
        SettingInt settingint = new SettingInt(s1, i, j, k, l);
        WidgetInt widgetint = new WidgetInt(settingint, s);
        modsettingscreen.append(widgetint);
        append(settingint);
        return settingint;
    }

    public SettingKey addSetting(ModSettingScreen modsettingscreen, String s, String s1, int i)
    {
        SettingKey settingkey = new SettingKey(s1, i);
        WidgetKeybinding widgetkeybinding = new WidgetKeybinding(settingkey, s);
        modsettingscreen.append(widgetkeybinding);
        append(settingkey);
        return settingkey;
    }

    public SettingMulti addSetting(ModSettingScreen modsettingscreen, String s, String s1, int i, String as[])
    {
        SettingMulti settingmulti = new SettingMulti(s1, i, as);
        WidgetMulti widgetmulti = new WidgetMulti(settingmulti, s);
        modsettingscreen.append(widgetmulti);
        append(settingmulti);
        return settingmulti;
    }

    public SettingText addSetting(ModSettingScreen modsettingscreen, String s, String s1, String s2)
    {
        SettingText settingtext = new SettingText(s1, s2);
        WidgetText widgettext = new WidgetText(settingtext, s);
        modsettingscreen.append(widgettext);
        append(settingtext);
        return settingtext;
    }

    public SettingBoolean addSetting(Widget widget, String s, String s1, boolean flag)
    {
        SettingBoolean settingboolean = new SettingBoolean(s1, Boolean.valueOf(flag));
        WidgetBoolean widgetboolean = new WidgetBoolean(settingboolean, s);
        widget.add(widgetboolean);
        append(settingboolean);
        return settingboolean;
    }

    public SettingBoolean addSetting(Widget widget, String s, String s1, boolean flag, String s2, String s3)
    {
        SettingBoolean settingboolean = new SettingBoolean(s1, Boolean.valueOf(flag));
        WidgetBoolean widgetboolean = new WidgetBoolean(settingboolean, s, s2, s3);
        widget.add(widgetboolean);
        append(settingboolean);
        return settingboolean;
    }

    public SettingFloat addSetting(Widget widget, String s, String s1, float f)
    {
        SettingFloat settingfloat = new SettingFloat(s1, f);
        WidgetFloat widgetfloat = new WidgetFloat(settingfloat, s);
        widget.add(widgetfloat);
        append(settingfloat);
        return settingfloat;
    }

    public SettingFloat addSetting(Widget widget, String s, String s1, float f, float f1, float f2, float f3)
    {
        SettingFloat settingfloat = new SettingFloat(s1, f, f1, f2, f3);
        WidgetFloat widgetfloat = new WidgetFloat(settingfloat, s);
        widget.add(widgetfloat);
        append(settingfloat);
        return settingfloat;
    }

    public SettingInt addSetting(Widget widget, String s, String s1, int i, int j, int k)
    {
        SettingInt settingint = new SettingInt(s1, i, j, 1, k);
        WidgetInt widgetint = new WidgetInt(settingint, s);
        widget.add(widgetint);
        append(settingint);
        return settingint;
    }

    public SettingInt addSetting(Widget widget, String s, String s1, int i, int j, int k, int l)
    {
        SettingInt settingint = new SettingInt(s1, i, j, k, l);
        WidgetInt widgetint = new WidgetInt(settingint, s);
        widget.add(widgetint);
        append(settingint);
        return settingint;
    }

    public SettingKey addSetting(Widget widget, String s, String s1, int i)
    {
        SettingKey settingkey = new SettingKey(s1, i);
        WidgetKeybinding widgetkeybinding = new WidgetKeybinding(settingkey, s);
        widget.add(widgetkeybinding);
        append(settingkey);
        return settingkey;
    }

    public SettingMulti addSetting(Widget widget, String s, String s1, int i, String as[])
    {
        SettingMulti settingmulti = new SettingMulti(s1, i, as);
        WidgetMulti widgetmulti = new WidgetMulti(settingmulti, s);
        widget.add(widgetmulti);
        append(settingmulti);
        return settingmulti;
    }

    public SettingText addSetting(Widget widget, String s, String s1, String s2)
    {
        SettingText settingtext = new SettingText(s1, s2);
        WidgetText widgettext = new WidgetText(settingtext, s);
        widget.add(widgettext);
        append(settingtext);
        return settingtext;
    }

    public static void setContext(String s, String s1)
    {
        if(s != null)
        {
            contextDatadirs.put(s, s1);
            currentContext = s;
            if(!s.equals(""))
            {
                loadAll(currentContext);
            }
        } else
        {
            currentContext = "";
        }
    }

    public void append(Setting setting)
    {
        Settings.add(setting);
        setting.parent = this;
    }

    public void remove(Setting setting)
    {
        Settings.remove(setting);
        setting.parent = null;
    }

    public int size()
    {
        return Settings.size();
    }

    public void resetAll(String s)
    {
        for(int i = 0; i < Settings.size(); i++)
        {
            ((Setting)Settings.get(i)).reset(s);
        }

    }

    public void resetAll()
    {
        resetAll(currentContext);
    }

    public void copyContextAll(String s, String s1)
    {
        for(int i = 0; i < Settings.size(); i++)
        {
            ((Setting)Settings.get(i)).copyContext(s, s1);
        }

    }

    public static File getAppDir(String s)
    {
        return Minecraft.getAppDir(s);
    }

    public void save(String s)
    {
        if(!have_loaded)
        {
            return;
        }
        try
        {
            File file = getAppDir((new StringBuilder("minecraft/")).append((String)contextDatadirs.get(s)).append("/").append(backendname).append("/").toString());
            dbgout((new StringBuilder("saving context ")).append(s).append(" (").append(file.getAbsolutePath()).append(" [").append((String)contextDatadirs.get(s)).append("])").toString());
            if(!file.exists())
            {
                file.mkdirs();
            }
            File file1 = new File(file, "guiconfig.properties");
            Properties properties = new Properties();
            for(int i = 0; i < Settings.size(); i++)
            {
                Setting setting = (Setting)Settings.get(i);
                properties.put(setting.backendname, setting.toString(s));
            }

            FileOutputStream fileoutputstream = new FileOutputStream(file1);
            properties.store(fileoutputstream, "");
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void load(String s)
    {
        try
        {
            if(contextDatadirs.get(s) != null)
            {
                File file = getAppDir((new StringBuilder("minecraft/")).append((String)contextDatadirs.get(s)).append("/").append(backendname).append("/").toString());
                if(file.exists())
                {
                    File file1 = new File(file, "guiconfig.properties");
                    if(file1.exists())
                    {
                        Properties properties = new Properties();
                        properties.load(new FileInputStream(file1));
                        for(int i = 0; i < Settings.size(); i++)
                        {
                            if(Settings.get(i) instanceof Setting)
                            {
                                dbgout("setting load");
                                Setting setting = (Setting)Settings.get(i);
                                if(properties.containsKey(setting.backendname))
                                {
                                    dbgout((new StringBuilder("setting ")).append((String)properties.get(setting.backendname)).toString());
                                    setting.fromString((String)properties.get(setting.backendname), s);
                                }
                            }
                        }

                    }
                }
            }
        }
        catch(Exception exception)
        {
            System.out.println(exception);
        }
    }

    public void load()
    {
        load("");
        have_loaded = true;
    }

    public static void loadAll(String s)
    {
        for(int i = 0; i < all.size(); i++)
        {
            ((ModSettings)all.get(i)).load(s);
        }

    }

    public static void dbgout(String s)
    {
    }

    public ArrayList Settings;
    public static ArrayList all = new ArrayList();
    public String backendname;
    public static String currentContext = "";
    public static HashMap contextDatadirs;
    public boolean have_loaded;
    public static final boolean debug = false;

    static 
    {
        contextDatadirs = new HashMap();
        contextDatadirs.put("", "mods");
    }
}
