// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.ArrayList;

// Referenced classes of package net.minecraft.src:
//            GuiModScreen, ModSettingScreen

public class ModCallback
    implements Runnable
{

    public ModCallback(int i, Object obj)
    {
        type = i;
        data = obj;
    }

    public void run()
    {
        if(type == 0)
        {
            GuiModScreen.back();
            GuiModScreen.clicksound();
        } else
        if(type == 1)
        {
            Integer integer = (Integer)data;
            int i = integer.intValue();
            GuiModScreen.show(((ModSettingScreen)ModSettingScreen.modscreens.get(i)).thewidget);
            GuiModScreen.clicksound();
        }
    }

    public static final int BACK = 0;
    public static final int SELECTMOD = 1;
    public Object data;
    public int type;
}
