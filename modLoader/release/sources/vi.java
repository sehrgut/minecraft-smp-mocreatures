public class vi extends gn
{
  private gq d;

  public vi(gq paramgq, ls paramls, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramls, paramInt1, paramInt2, paramInt3);

    d = paramgq;
  }

  public boolean b(iw paramiw) {
    return false;
  }

  public void a(iw paramiw)
  {
    paramiw.b(d.aI, d);
    if (paramiw.c == gk.m.be) d.a(en.k, 1);
    if (paramiw.c == gk.aT.be) d.a(en.p, 1);
    ModLoader.TakenFromFurnace(d, paramiw);
    super.a(paramiw);
  }
}