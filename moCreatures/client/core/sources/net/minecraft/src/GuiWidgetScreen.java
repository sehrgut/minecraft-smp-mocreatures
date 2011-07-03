// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.input.lwjgl.LWJGLInput;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import java.net.URL;
import net.minecraft.client.Minecraft;

// Referenced classes of package net.minecraft.src:
//            ModSettings, ModLoader, ScreenScaleProxy

public class GuiWidgetScreen extends Widget
{

    public static GuiWidgetScreen getInstance()
    {
        if(instance != null)
        {
            return instance;
        }
        try
        {
            instance = new GuiWidgetScreen();
            instance.renderer = new LWJGLRenderer();
            String s = "twlGuiTheme.xml";
            instance.gui = new GUI(instance, instance.renderer, new LWJGLInput());
            ModSettings.dbgout((net.minecraft.src.GuiWidgetScreen.class).getResource(s).toString());
            instance.theme = ThemeManager.createThemeManager((net.minecraft.src.GuiWidgetScreen.class).getResource(s), instance.renderer);
            if(instance.theme == null)
            {
                throw new RuntimeException("I don't think you installed the theme correctly ...");
            }
            instance.gui.applyTheme(instance.theme);
            instance.setTheme("");
            instance.mcinstance = ModLoader.getMinecraftInstance();
            instance.screensize = new ScreenScaleProxy(instance.mcinstance.displayWidth, instance.mcinstance.displayHeight);
        }
        catch(Throwable throwable)
        {
            throwable.printStackTrace();
            RuntimeException runtimeexception = new RuntimeException("error loading theme");
            runtimeexception.initCause(throwable);
            throw runtimeexception;
        }
        return instance;
    }

    public GuiWidgetScreen()
    {
        gui = null;
        renderer = null;
        currentwidget = null;
        theme = null;
        screensize = null;
    }

    public void setScreen(Widget widget)
    {
        gui.resyncTimerAfterPause();
        gui.clearKeyboardState();
        gui.clearMouseState();
        removeAllChildren();
        add(widget);
        currentwidget = widget;
    }

    public void resetScreen()
    {
        removeAllChildren();
        currentwidget = null;
    }

    public void layout()
    {
        screensize = new ScreenScaleProxy(mcinstance.displayWidth, mcinstance.displayHeight);
        if(currentwidget != null)
        {
            screenwidth = screensize.getScaledWidth();
            screenheight = screensize.getScaledHeight();
            currentwidget.setSize(screenwidth, screenheight);
            currentwidget.setPosition(0, 0);
        }
    }

    public static GuiWidgetScreen instance;
    public GUI gui;
    public LWJGLRenderer renderer;
    public Widget currentwidget;
    public ThemeManager theme;
    public Minecraft mcinstance;
    public ScreenScaleProxy screensize;
    public static int screenwidth;
    public static int screenheight;
}
