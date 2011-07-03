import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class og
  implements cj
{
  private Set a = new HashSet();
  private li b;
  private cj c;
  private be d;
  private Map e = new HashMap();
  private List f = new ArrayList();
  private fb g;

  public og(fb paramfb, be parambe, cj paramcj)
  {
    b = new le(paramfb, new byte[32768], 0, 0);

    g = paramfb;
    d = parambe;
    c = paramcj;
  }

  public boolean a(int paramInt1, int paramInt2) {
    return e.containsKey(Integer.valueOf(yp.a(paramInt1, paramInt2)));
  }

  public li c(int paramInt1, int paramInt2)
  {
    int i = yp.a(paramInt1, paramInt2);
    a.remove(Integer.valueOf(i));

    li localli = (li)e.get(Integer.valueOf(i));
    if (localli == null) {
      localli = d(paramInt1, paramInt2);
      if (localli == null) {
        if (c == null)
          localli = b;
        else {
          localli = c.b(paramInt1, paramInt2);
        }

      }

      e.put(Integer.valueOf(i), localli);
      f.add(localli);

      if (localli != null) {
        localli.d();
        localli.e();
      }

      if ((!localli.n) && (a(paramInt1 + 1, paramInt2 + 1)) && (a(paramInt1, paramInt2 + 1)) && (a(paramInt1 + 1, paramInt2))) a(this, paramInt1, paramInt2);
      if ((a(paramInt1 - 1, paramInt2)) && (!b(paramInt1 - 1, paramInt2).n) && (a(paramInt1 - 1, paramInt2 + 1)) && (a(paramInt1, paramInt2 + 1)) && (a(paramInt1 - 1, paramInt2))) a(this, paramInt1 - 1, paramInt2);
      if ((a(paramInt1, paramInt2 - 1)) && (!b(paramInt1, paramInt2 - 1).n) && (a(paramInt1 + 1, paramInt2 - 1)) && (a(paramInt1, paramInt2 - 1)) && (a(paramInt1 + 1, paramInt2))) a(this, paramInt1, paramInt2 - 1);
      if ((a(paramInt1 - 1, paramInt2 - 1)) && (!b(paramInt1 - 1, paramInt2 - 1).n) && (a(paramInt1 - 1, paramInt2 - 1)) && (a(paramInt1, paramInt2 - 1)) && (a(paramInt1 - 1, paramInt2))) a(this, paramInt1 - 1, paramInt2 - 1);

    }

    return localli;
  }

  public li b(int paramInt1, int paramInt2)
  {
    li localli = (li)e.get(Integer.valueOf(yp.a(paramInt1, paramInt2)));

    if (localli == null)
    {
      return c(paramInt1, paramInt2);
    }

    return localli;
  }

  private li d(int paramInt1, int paramInt2) {
    if (d == null) return null; try
    {
      li localli = d.a(g, paramInt1, paramInt2);
      if (localli != null) {
        localli.r = g.t();
      }
      return localli;
    } catch (Exception localException) {
      localException.printStackTrace();
    }return null;
  }

  private void a(li paramli)
  {
    if (d == null) return; try
    {
      d.b(g, paramli);
    } catch (Exception localException) {
      localException.printStackTrace();
    }
  }

  private void b(li paramli) {
    if (d == null) return; try
    {
      paramli.r = g.t();
      d.a(g, paramli);
    } catch (IOException localIOException) {
      localIOException.printStackTrace();
    }
  }

  public void a(cj paramcj, int paramInt1, int paramInt2) {
    li localli = b(paramInt1, paramInt2);
    if (!localli.n) {
      localli.n = true;
      if (c != null) {
        c.a(paramcj, paramInt1, paramInt2);
        ModLoader.PopulateChunk(c, paramInt1 << 4, paramInt2 << 4, g);
        localli.g();
      }
    }
  }

  public boolean a(boolean paramBoolean, xs paramxs)
  {
    int i = 0;
    for (int j = 0; j < f.size(); j++) {
      li localli = (li)f.get(j);
      if ((paramBoolean) && (!localli.p)) a(localli);
      if (localli.a(paramBoolean)) {
        b(localli);
        localli.o = false;
        i++; if ((i == 24) && (!paramBoolean)) return false;
      }
    }

    if (paramBoolean) {
      if (d == null) return true;
      d.b();
    }
    return true;
  }

  public boolean a() {
    for (int i = 0; i < 100; i++) {
      if (!a.isEmpty()) {
        Integer localInteger = (Integer)a.iterator().next();

        li localli = (li)e.get(localInteger);
        localli.f();
        b(localli);
        a(localli);
        a.remove(localInteger);

        e.remove(localInteger);
        f.remove(localli);
      }
    }

    if (d != null) d.a();

    return c.a();
  }

  public boolean b() {
    return true;
  }

  public String c() {
    return "ServerChunkCache: " + e.size() + " Drop: " + a.size();
  }
}