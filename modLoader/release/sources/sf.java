public class sf extends os
  implements ls
{
  private iw[] h = new iw[3];
  public int a = 0;
  public int b = 0;
  public int c = 0;

  public int a() {
    return h.length;
  }

  public iw d_(int paramInt) {
    return h[paramInt];
  }

  public iw a(int paramInt1, int paramInt2) {
    if (h[paramInt1] != null) {
      if (h[paramInt1].a <= paramInt2) {
        iw localiw = h[paramInt1];
        h[paramInt1] = null;
        return localiw;
      }
      iw localiw = h[paramInt1].a(paramInt2);
      if (h[paramInt1].a == 0)
        h[paramInt1] = null;
      return localiw;
    }

    return null;
  }

  public void a(int paramInt, iw paramiw) {
    h[paramInt] = paramiw;
    if ((paramiw != null) && (paramiw.a > d()))
      paramiw.a = d();
  }

  public String c() {
    return "Furnace";
  }

  public void a(nq paramnq) {
    super.a(paramnq);
    sk localsk = paramnq.l("Items");
    h = new iw[a()];
    for (int i = 0; i < localsk.c(); i++) {
      nq localnq = (nq)localsk.a(i);
      int j = localnq.c("Slot");
      if ((j < 0) || (j >= h.length))
        continue;
      h[j] = new iw(localnq);
    }

    a = paramnq.d("BurnTime");
    c = paramnq.d("CookTime");
    b = a(h[1]);
  }

  public void b(nq paramnq) {
    super.b(paramnq);
    paramnq.a("BurnTime", (short)a);
    paramnq.a("CookTime", (short)c);
    sk localsk = new sk();

    for (int i = 0; i < h.length; i++) {
      if (h[i] != null) {
        nq localnq = new nq();
        localnq.a("Slot", (byte)i);
        h[i].a(localnq);
        localsk.a(localnq);
      }
    }
    paramnq.a("Items", localsk);
  }

  public int d() {
    return 64;
  }

  public int b(int paramInt) {
    return c * paramInt / 200;
  }

  public int c(int paramInt) {
    if (b == 0)
      b = 200;
    return a * paramInt / b;
  }

  public boolean b() {
    return a > 0;
  }

  public void m_() {
    int i = a > 0 ? 1 : 0;
    int j = 0;
    if (a > 0) {
      a -= 1;
    }

    if (!d.B) {
      if ((a == 0) && (i())) {
        b = (this.a = a(h[1]));
        if (a > 0) {
          j = 1;
          if (h[1] != null) {
            if (h[1].a().j()) h[1] = new iw(h[1].a().i()); else
              h[1].a -= 1;
            if (h[1].a == 0) {
              h[1] = null;
            }
          }
        }
      }
      if ((b()) && (i())) {
        c += 1;
        if (c == 200) {
          c = 0;
          g();
          j = 1;
        }
      } else {
        c = 0;
      }

      if (i != (a > 0 ? 1 : 0)) {
        j = 1;
        sx.a(a > 0, d, e, f, g);
      }
    }

    if (j != 0)
      y_();
  }

  private boolean i() {
    if (h[0] == null)
      return false;
    iw localiw = ew.a().a(h[0].a().be);
    if (localiw == null)
      return false;
    if (h[2] == null)
      return true;
    if (!h[2].a(localiw))
      return false;
    if ((h[2].a < d()) && (h[2].a < h[2].c()))
      return true;
    return h[2].a < localiw.c();
  }

  public void g() {
    if (!i()) {
      return;
    }
    iw localiw = ew.a().a(h[0].a().be);
    if (h[2] == null) h[2] = localiw.k();
    else if (h[2].c == localiw.c) {
      h[2].a += localiw.a;
    }
    if (h[0].a().j()) h[0] = new iw(h[0].a().i()); else
      h[0].a -= 1;
    if (h[0].a <= 0)
      h[0] = null;
  }

  private int a(iw paramiw) {
    if (paramiw == null)
      return 0;
    int i = paramiw.a().be;

    if ((i < 256) && (un.m[i].bA == lj.d)) {
      return 300;
    }

    if (i == gk.B.be) {
      return 100;
    }

    if (i == gk.k.be) {
      return 1600;
    }
    if (i == gk.aw.be) {
      return 20000;
    }
    if (i == un.z.bn) {
      return 100;
    }
    return ModLoader.AddAllFuel(i);
  }

  public boolean a_(gq paramgq) {
    if (d.b(e, f, g) != this)
      return false;
    return paramgq.f(e + 0.5D, f + 0.5D, g + 0.5D) <= 64.0D;
  }
}