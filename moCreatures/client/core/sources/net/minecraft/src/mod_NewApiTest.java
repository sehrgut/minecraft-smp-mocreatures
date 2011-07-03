// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.SimpleButtonModel;
import net.minecraft.client.Minecraft;

// Referenced classes of package net.minecraft.src:
//            BaseMod, ModSettings, ModSettingScreen, SettingBoolean, 
//            WidgetBoolean, SettingFloat, WidgetFloat, SettingInt, 
//            WidgetInt, SettingKey, WidgetKeybinding, SettingMulti, 
//            WidgetMulti, SettingText, WidgetText, Subscreen, 
//            WidgetSinglecolumn, ModAction, ModLoader, WidgetSimplewindow, 
//            WidgetClassicTwocolumn, GuiModScreen

public class mod_NewApiTest extends BaseMod
{

    public mod_NewApiTest()
    {
        settings = new ModSettings("mod_settingstest");
        modscreen = new ModSettingScreen("Tests");
        sbool = new SettingBoolean("bool", Boolean.valueOf(false));
        wbool = new WidgetBoolean(sbool, "Boolean", "yes", "no");
        modscreen.append(wbool);
        settings.append(sbool);
        sfloat = new SettingFloat("float");
        wfloat = new WidgetFloat(sfloat, "Floating-point");
        modscreen.append(wfloat);
        settings.append(sfloat);
        sint = new SettingInt("int");
        wint = new WidgetInt(sint, "Integer");
        modscreen.append(wint);
        settings.append(sint);
        skey = new SettingKey("key", "F10");
        wkey = new WidgetKeybinding(skey, "keybinding");
        settings.append(skey);
        smult = new SettingMulti("multi", new String[] {
            "A", "B", "c"
        });
        wmult = new WidgetMulti(smult, "Multi-choice");
        settings.append(smult);
        stext = new SettingText("text", "defvalue");
        wtext = new WidgetText(stext, "Text");
        settings.append(stext);
        settings.addSetting(modscreen, "addtestbool", "addtestbool", false, "yes", "no");
        settings.addSetting(modscreen, "addtestfloat", "addtestfloat", 0.8F, 0.0F, 0.1F, 2.0F);
        settings.addSetting(modscreen, "addtestint", "addtestint", 8, 0, 1, 20);
        skey2 = settings.addSetting(modscreen, "addtestkey", "addtestkey", 48);
        settings.addSetting(modscreen, "addtestmulti", "addtestmulti", 0, new String[] {
            "one", "two", "three", "five"
        });
        settings.addSetting(modscreen, "addtesttext", "addfdsatesttext", "this was adsfa test");
        settings.addSetting(modscreen, "addtestt1ext", "addtestasdftext", "this wasfads a test");
        settings.addSetting(modscreen, "addtest3t2ext", "addtefdsasttext", "this wfadsas a test");
        settings.addSetting(modscreen, "addtes4t3text", "addteasdfsttext", "thiasdfs was a test");
        settings.addSetting(modscreen, "addtes5ttext", "addtasdfesttext", "this asdfwas a test");
        settings.addSetting(modscreen, "addtest5text", "addasdftesttext", "thifdss was a test");
        settings.addSetting(modscreen, "addtes4ttext", "addtesttfsdaext", "this wdsaas a test");
        settings.addSetting(modscreen, "addtest6text", "addtfdsaaesttext", "this wadsas a test");
        settings.addSetting(modscreen, "addtes7ttext", "addtesttsfdext", "this wassdf a test");
        Subscreen subscreen = new Subscreen("button", "title", new WidgetSinglecolumn(new Widget[0]));
        subscreen.setText("subscreen");
        settings.addSetting(subscreen, "subscreentest", "subscreentest", 0, new String[] {
            "one", "two", "three", "five"
        });
        subscreen.add(wkey);
        subscreen.add(wmult);
        subscreen.add(wtext);
        modscreen.append(subscreen);
        SimpleButtonModel simplebuttonmodel = new SimpleButtonModel();
        simplebuttonmodel.addActionCallback(new ModAction(settings, "resetAll", new Class[0]));
        Button button = new Button(simplebuttonmodel);
        button.setText("Reset all to defaults");
        modscreen.append(button);
        settings.load();
        ModLoader.SetInGameHook(this, true, false);
    }

    public void OnTickInGame(Minecraft minecraft)
    {
        if(minecraft.fontRenderer == null && skey2.isKeyDown())
        {
            GuiModScreen.show(new WidgetSimplewindow(new WidgetClassicTwocolumn(new Widget[0]), "test"));
        }
    }

    public String Version()
    {
        return "1.4_01";
    }

    public ModSettings settings;
    public ModSettingScreen modscreen;
    public SettingBoolean sbool;
    public WidgetBoolean wbool;
    public SettingFloat sfloat;
    public WidgetFloat wfloat;
    public SettingInt sint;
    public WidgetInt wint;
    public SettingKey skey;
    public WidgetKeybinding wkey;
    public SettingMulti smult;
    public WidgetMulti wmult;
    public SettingText stext;
    public WidgetText wtext;
    public SettingKey skey2;
}
