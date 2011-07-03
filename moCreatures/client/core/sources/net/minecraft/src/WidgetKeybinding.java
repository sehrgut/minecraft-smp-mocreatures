// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import de.matthiasmann.twl.Event;
import de.matthiasmann.twl.ToggleButton;
import de.matthiasmann.twl.model.SimpleBooleanModel;
import java.io.PrintStream;
import org.lwjgl.input.Keyboard;

// Referenced classes of package net.minecraft.src:
//            WidgetSetting, SettingKey, ModSettingScreen, GuiModScreen

public class WidgetKeybinding extends WidgetSetting
    implements Runnable
{

    public WidgetKeybinding(SettingKey settingkey, String s)
    {
        super(s);
        CLEARKEY = 211;
        NEVERMINDKEY = 1;
        setTheme("");
        value = settingkey;
        value.gui = this;
        bmodel = new SimpleBooleanModel(false);
        b = new ToggleButton(bmodel);
        add(b);
        update();
    }

    public boolean handleEvent(Event event)
    {
        if(event.isKeyEvent() && !event.isKeyPressedEvent() && bmodel.getValue())
        {
            System.out.println(Keyboard.getKeyName(event.getKeyCode()));
            int i = event.getKeyCode();
            if(i == CLEARKEY)
            {
                value.set(Integer.valueOf(0), ModSettingScreen.guicontext);
            } else
            if(i != NEVERMINDKEY)
            {
                value.set(Integer.valueOf(i), ModSettingScreen.guicontext);
            }
            bmodel.setValue(false);
            update();
            GuiModScreen.clicksound();
            return true;
        } else
        {
            return false;
        }
    }

    public void keyboardFocusLost()
    {
        GuiModScreen.clicksound();
        bmodel.setValue(false);
    }

    public String userString()
    {
        return String.format("%s: %s", new Object[] {
            nicename, Keyboard.getKeyName(value.get(ModSettingScreen.guicontext).intValue())
        });
    }

    public void update()
    {
        b.setText(userString());
    }

    public void run()
    {
        GuiModScreen.clicksound();
    }

    public SettingKey value;
    public SimpleBooleanModel bmodel;
    public ToggleButton b;
    public int CLEARKEY;
    public int NEVERMINDKEY;
}
