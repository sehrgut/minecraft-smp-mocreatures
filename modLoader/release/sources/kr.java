import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class kr
{
  private static final String[] J = { "options.renderDistance.far", "options.renderDistance.normal", "options.renderDistance.short", "options.renderDistance.tiny" };

  private static final String[] K = { "options.difficulty.peaceful", "options.difficulty.easy", "options.difficulty.normal", "options.difficulty.hard" };

  private static final String[] L = { "options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large" };

  private static final String[] M = { "performance.max", "performance.balanced", "performance.powersaver" };

  public float a = 1.0F;
  public float b = 1.0F;
  public float c = 0.5F;
  public boolean d = false;
  public int e = 0;
  public boolean f = true;
  public boolean g = false;
  public boolean h = false;
  public int i = 1;
  public boolean j = true;
  public boolean k = true;
  public String l = "Default";

  public px m = new px("key.forward", 17);
  public px n = new px("key.left", 30);
  public px o = new px("key.back", 31);
  public px p = new px("key.right", 32);
  public px q = new px("key.jump", 57);
  public px r = new px("key.inventory", 18);
  public px s = new px("key.drop", 16);
  public px t = new px("key.chat", 20);
  public px u = new px("key.fog", 33);
  public px v = new px("key.sneak", 42);

  public px[] w = { m, n, o, p, q, v, s, r, t, u };
  protected Minecraft x;
  private File N;
  public int y = 2;
  public boolean z = false;
  public boolean A = false;
  public boolean B = false;
  public String C = "";

  public boolean D = false;
  public boolean E = false;
  public boolean F = false;
  public float G = 1.0F;
  public float H = 1.0F;
  public int I = 0;

  public kr(Minecraft paramMinecraft, File paramFile) {
    x = paramMinecraft;
    N = new File(paramFile, "options.txt");
    w = ModLoader.RegisterAllKeys(w);
    a();
  }

  public kr() {
  }

  public String a(int paramInt) {
    nd localnd = nd.a();
    return localnd.a(w[paramInt].a);
  }

  public String b(int paramInt) {
    return Keyboard.getKeyName(w[paramInt].b);
  }

  public void a(int paramInt1, int paramInt2) {
    w[paramInt1].b = paramInt2;
    b();
  }

  public void a(hr paramhr, float paramFloat) {
    if (paramhr == hr.a) {
      a = paramFloat;
      x.B.a();
    }
    if (paramhr == hr.b) {
      b = paramFloat;
      x.B.a();
    }
    if (paramhr == hr.d)
      c = paramFloat;
  }

  public void a(hr paramhr, int paramInt)
  {
    if (paramhr == hr.c) d = (!d);
    if (paramhr == hr.e) e = (e + paramInt & 0x3);
    if (paramhr == hr.m) I = (I + paramInt & 0x3);
    if (paramhr == hr.f) f = (!f);
    if (paramhr == hr.h) {
      h = (!h);
      x.g.a();
    }
    if (paramhr == hr.g) {
      g = (!g);
      x.p.b();
    }
    if (paramhr == hr.i) i = ((i + paramInt + 3) % 3);
    if (paramhr == hr.j) y = (y + paramInt & 0x3);
    if (paramhr == hr.k) {
      j = (!j);
      x.g.a();
    }
    if (paramhr == hr.l) {
      k = (!k);
      x.g.a();
    }
    b();
  }

  public float a(hr paramhr) {
    if (paramhr == hr.a) return a;
    if (paramhr == hr.b) return b;
    if (paramhr == hr.d) return c;
    return 0.0F;
  }

  public boolean b(hr paramhr) {
    switch (fo.a[paramhr.ordinal()]) {
    case 1:
      return d;
    case 2:
      return f;
    case 3:
      return g;
    case 4:
      return h;
    case 5:
      return k;
    }
    return false;
  }

  public String c(hr paramhr)
  {
    nd localnd = nd.a();
    String str = localnd.a(paramhr.d()) + ": ";

    if (paramhr.a()) {
      float f1 = a(paramhr);

      if (paramhr == hr.d) {
        if (f1 == 0.0F) {
          return str + localnd.a("options.sensitivity.min");
        }
        if (f1 == 1.0F) {
          return str + localnd.a("options.sensitivity.max");
        }
        return str + (int)(f1 * 200.0F) + "%";
      }
      if (f1 == 0.0F) {
        return str + localnd.a("options.off");
      }
      return str + (int)(f1 * 100.0F) + "%";
    }
    if (paramhr.b())
    {
      boolean bool = b(paramhr);
      if (bool) {
        return str + localnd.a("options.on");
      }
      return str + localnd.a("options.off");
    }if (paramhr == hr.e)
      return str + localnd.a(J[e]);
    if (paramhr == hr.j)
      return str + localnd.a(K[y]);
    if (paramhr == hr.m)
      return str + localnd.a(L[I]);
    if (paramhr == hr.i)
      return str + dm.a(M[i]);
    if (paramhr == hr.k) {
      if (j) {
        return str + localnd.a("options.graphics.fancy");
      }
      return str + localnd.a("options.graphics.fast");
    }

    return str;
  }

  public void a()
  {
    try {
      if (!N.exists()) return;
      BufferedReader localBufferedReader = new BufferedReader(new FileReader(N));
      String str = "";
      while ((str = localBufferedReader.readLine()) != null) {
        try {
          String[] arrayOfString = str.split(":");
          if (arrayOfString[0].equals("music")) a = a(arrayOfString[1]);
          if (arrayOfString[0].equals("sound")) b = a(arrayOfString[1]);
          if (arrayOfString[0].equals("mouseSensitivity")) c = a(arrayOfString[1]);
          if (arrayOfString[0].equals("invertYMouse")) d = arrayOfString[1].equals("true");
          if (arrayOfString[0].equals("viewDistance")) e = Integer.parseInt(arrayOfString[1]);
          if (arrayOfString[0].equals("guiScale")) I = Integer.parseInt(arrayOfString[1]);
          if (arrayOfString[0].equals("bobView")) f = arrayOfString[1].equals("true");
          if (arrayOfString[0].equals("anaglyph3d")) g = arrayOfString[1].equals("true");
          if (arrayOfString[0].equals("advancedOpengl")) h = arrayOfString[1].equals("true");
          if (arrayOfString[0].equals("fpsLimit")) i = Integer.parseInt(arrayOfString[1]);
          if (arrayOfString[0].equals("difficulty")) y = Integer.parseInt(arrayOfString[1]);
          if (arrayOfString[0].equals("fancyGraphics")) j = arrayOfString[1].equals("true");
          if (arrayOfString[0].equals("ao")) k = arrayOfString[1].equals("true");
          if (arrayOfString[0].equals("skin")) l = arrayOfString[1];
          if ((arrayOfString[0].equals("lastServer")) && (arrayOfString.length >= 2)) C = arrayOfString[1];

          for (int i1 = 0; i1 < w.length; i1++)
            if (arrayOfString[0].equals("key_" + w[i1].a))
              w[i1].b = Integer.parseInt(arrayOfString[1]);
        }
        catch (Exception localException2)
        {
          System.out.println("Skipping bad option: " + str);
        }
      }
      localBufferedReader.close();
    } catch (Exception localException1) {
      System.out.println("Failed to load options");
      localException1.printStackTrace();
    }
  }

  private float a(String paramString) {
    if (paramString.equals("true")) return 1.0F;
    if (paramString.equals("false")) return 0.0F;
    return Float.parseFloat(paramString);
  }

  public void b() {
    try {
      PrintWriter localPrintWriter = new PrintWriter(new FileWriter(N));

      localPrintWriter.println("music:" + a);
      localPrintWriter.println("sound:" + b);
      localPrintWriter.println("invertYMouse:" + d);
      localPrintWriter.println("mouseSensitivity:" + c);
      localPrintWriter.println("viewDistance:" + e);
      localPrintWriter.println("guiScale:" + I);
      localPrintWriter.println("bobView:" + f);
      localPrintWriter.println("anaglyph3d:" + g);
      localPrintWriter.println("advancedOpengl:" + h);
      localPrintWriter.println("fpsLimit:" + i);
      localPrintWriter.println("difficulty:" + y);
      localPrintWriter.println("fancyGraphics:" + j);
      localPrintWriter.println("ao:" + k);
      localPrintWriter.println("skin:" + l);
      localPrintWriter.println("lastServer:" + C);

      for (int i1 = 0; i1 < w.length; i1++) {
        localPrintWriter.println("key_" + w[i1].a + ":" + w[i1].b);
      }

      localPrintWriter.close();
    } catch (Exception localException) {
      System.out.println("Failed to save options");
      localException.printStackTrace();
    }
  }
}