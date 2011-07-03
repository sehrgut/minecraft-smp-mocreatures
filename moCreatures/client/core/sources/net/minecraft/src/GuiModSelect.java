// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.SimpleButtonModel;
import java.util.ArrayList;

// Referenced classes of package net.minecraft.src:
//            GuiModScreen, WidgetClassicTwocolumn, GuiScreen, ModSettingScreen, 
//            ModCallback, WidgetSimplewindow

public class GuiModSelect extends GuiModScreen
{

    protected GuiModSelect(GuiScreen guiscreen)
    {
        super(guiscreen);
        WidgetClassicTwocolumn widgetclassictwocolumn = new WidgetClassicTwocolumn(new Widget[0]);
        for(int i = 0; i < ModSettingScreen.modscreens.size(); i++)
        {
            ModSettingScreen modsettingscreen = (ModSettingScreen)ModSettingScreen.modscreens.get(i);
            Button button = new Button(modsettingscreen.buttontitle);
            SimpleButtonModel simplebuttonmodel = new SimpleButtonModel();
            simplebuttonmodel.addActionCallback(new ModCallback(1, new Integer(i)));
            button.setModel(simplebuttonmodel);
            widgetclassictwocolumn.add(button);
        }

        mainwidget = new WidgetSimplewindow(widgetclassictwocolumn, "Select a Mod");
    }
}
