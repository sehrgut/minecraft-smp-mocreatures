public class ym extends gn
{
  private final ls d;
  private gq e;

  public ym(gq paramgq, ls paramls1, ls paramls2, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramls2, paramInt1, paramInt2, paramInt3);
    e = paramgq;
    d = paramls1;
  }

  public boolean b(iw paramiw) {
    return false;
  }

  public void a(iw paramiw) {
    paramiw.b(e.aI, e);

    if (paramiw.c == un.az.bn) e.a(en.h, 1);
    else if (paramiw.c == gk.r.be) e.a(en.i, 1);
    else if (paramiw.c == un.aC.bn) e.a(en.j, 1);
    else if (paramiw.c == gk.L.be) e.a(en.l, 1);
    else if (paramiw.c == gk.S.be) e.a(en.m, 1);
    else if (paramiw.c == gk.aX.be) e.a(en.n, 1);
    else if (paramiw.c == gk.v.be) e.a(en.o, 1);
    else if (paramiw.c == gk.p.be) e.a(en.r, 1);

    ModLoader.TakenFromCrafting(e, paramiw);

    for (int i = 0; i < d.a(); i++) {
      iw localiw = d.d_(i);
      if (localiw != null) {
        d.a(i, 1);

        if (!localiw.a().j())
          continue;
        d.a(i, new iw(localiw.a().i()));
      }
    }
  }
}