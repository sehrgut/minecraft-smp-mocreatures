// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.renderer.lwjgl.RenderScale;
import net.minecraft.client.Minecraft;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, ModLoader, SoundManager, GuiWidgetScreen, 
//            ScreenScaleProxy

public class GuiModScreen extends GuiScreen
{

    protected GuiModScreen(GuiScreen guiscreen)
    {
        drawbg = true;
        t = 0;
        firstrun = true;
        parentScreen = guiscreen;
        currentscreen = this;
    }

    public GuiModScreen(GuiScreen guiscreen, Widget widget)
    {
        drawbg = true;
        t = 0;
        firstrun = true;
        mainwidget = widget;
        parentScreen = guiscreen;
        currentscreen = this;
    }

    public static void back()
    {
        if(currentscreen != null)
        {
            Minecraft minecraft = ModLoader.getMinecraftInstance();
            minecraft.displayGuiScreen(currentscreen.parentScreen);
            if(currentscreen.parentScreen instanceof GuiModScreen)
            {
                currentscreen = (GuiModScreen)currentscreen.parentScreen;
            } else
            {
                currentscreen = null;
            }
        }
    }

    public static void show(Widget widget)
    {
        Minecraft minecraft = ModLoader.getMinecraftInstance();
        GuiModScreen guimodscreen = new GuiModScreen(currentscreen, widget);
        minecraft.displayGuiScreen(guimodscreen);
        guimodscreen.setActive();
    }

    public static void show(GuiModScreen guimodscreen)
    {
        Minecraft minecraft = ModLoader.getMinecraftInstance();
        minecraft.displayGuiScreen(guimodscreen);
        guimodscreen.setActive();
    }

    public static void clicksound()
    {
        Minecraft minecraft = ModLoader.getMinecraftInstance();
        minecraft.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
    }

    private void setActive()
    {
        GuiWidgetScreen.getInstance().setScreen(mainwidget);
    }

    public void handleInput()
    {
    }

    public void drawScreen(int i, int j, float f)
    {
        if(drawbg)
        {
            drawDefaultBackground();
        }
        LWJGLRenderer lwjglrenderer = (LWJGLRenderer)GuiWidgetScreen.getInstance().gui.getRenderer();
        ScreenScaleProxy screenscaleproxy = new ScreenScaleProxy(GuiWidgetScreen.getInstance().mcinstance.displayWidth, GuiWidgetScreen.getInstance().mcinstance.displayHeight);
        RenderScale.scale = screenscaleproxy.scaleFactor;
        lwjglrenderer.syncViewportSize();
        GuiWidgetScreen.getInstance().gui.update();
    }

    public GuiScreen parentScreen;
    public boolean drawbg;
    public Widget mainwidget;
    public static GuiModScreen currentscreen;
    private int t;
    boolean firstrun;
}
