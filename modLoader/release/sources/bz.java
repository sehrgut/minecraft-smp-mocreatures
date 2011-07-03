import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.TextArea;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

public class bz extends Panel
{
  public bz(mc parammc)
  {
    setBackground(new Color(3028036));
    setLayout(new BorderLayout());
    StringWriter localStringWriter = new StringWriter();
    parammc.b.printStackTrace(new PrintWriter(localStringWriter));
    String str1 = localStringWriter.toString();

    String str2 = "";

    String str3 = "";
    try {
      str3 = str3 + "Generated " + new SimpleDateFormat().format(new Date()) + "\n";
      str3 = str3 + "\n";
      str3 = str3 + "Minecraft: Minecraft Beta 1.6.6\n";
      str3 = str3 + "OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version") + "\n";
      str3 = str3 + "Java: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor") + "\n";
      str3 = str3 + "VM: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor") + "\n";
      str3 = str3 + "LWJGL: " + Sys.getVersion() + "\n";

      str2 = GL11.glGetString(7936);
      str3 = str3 + "OpenGL: " + GL11.glGetString(7937) + " version " + GL11.glGetString(7938) + ", " + GL11.glGetString(7936) + "\n";
    }
    catch (Throwable localThrowable) {
      str3 = str3 + "[failed to get system properties (" + localThrowable + ")]\n";
    }
    str3 = str3 + "\n";
    str3 = str3 + str1;

    String str4 = "";
    str4 = str4 + "Mods loaded: " + (ModLoader.getLoadedMods().size() + 1) + "\n";
    str4 = str4 + "ModLoader Beta 1.6.6" + "\n";
    for (BaseMod mod : ModLoader.getLoadedMods()) {
      str4 = str4 + mod.getClass().getName() + " " + mod.Version() + "\n";
    }
    str4 = str4 + "\n";

    if (str1.contains("Pixel format not accelerated")) {
      str4 = str4 + "      Bad video card drivers!      \n";
      str4 = str4 + "      -----------------------      \n";
      str4 = str4 + "\n";
      str4 = str4 + "Minecraft was unable to start because it failed to find an accelerated OpenGL mode.\n";
      str4 = str4 + "This can usually be fixed by updating the video card drivers.\n";
      if (str2.toLowerCase().contains("nvidia")) {
        str4 = str4 + "\n";
        str4 = str4 + "You might be able to find drivers for your video card here:\n";
        str4 = str4 + "  http://www.nvidia.com/\n";
      } else if (str2.toLowerCase().contains("ati")) {
        str4 = str4 + "\n";
        str4 = str4 + "You might be able to find drivers for your video card here:\n";
        str4 = str4 + "  http://www.amd.com/\n";
      }
    } else {
      str4 = str4 + "      Minecraft has crashed!      \n";
      str4 = str4 + "      ----------------------      \n";
      str4 = str4 + "\n";
      str4 = str4 + "Minecraft has stopped running because it encountered a problem.\n";
      str4 = str4 + "\n";
      str4 = str4 + "If you wish to report this, please copy this entire text and email it to support@mojang.com.\n";
      str4 = str4 + "Please include a description of what you did when the error occured.\n";
    }
    str4 = str4 + "\n";
    str4 = str4 + "\n";
    str4 = str4 + "\n";
    str4 = str4 + "--- BEGIN ERROR REPORT " + Integer.toHexString(str4.hashCode()) + " --------\n";
    str4 = str4 + str3;

    str4 = str4 + "--- END ERROR REPORT " + Integer.toHexString(str4.hashCode()) + " ----------\n";
    str4 = str4 + "\n";
    str4 = str4 + "\n";

    TextArea localTextArea = new TextArea(str4, 0, 0, 1);
    localTextArea.setFont(new Font("Monospaced", 0, 12));
    add(new fw(), "North");
    add(new wm(80), "East");
    add(new wm(80), "West");
    add(new wm(100), "South");
    add(localTextArea, "Center");
  }
}