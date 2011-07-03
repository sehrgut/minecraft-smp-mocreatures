import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ModTextureStatic extends av
{
  private boolean oldanaglyph;
  private int[] pixels = null;

  public ModTextureStatic(int slot, int dst, BufferedImage source) {
    this(slot, 1, dst, source);
  }

  public ModTextureStatic(int slot, int size, int dst, BufferedImage source) {
    super(slot);
    e = size;
    f = dst;
    int width = source.getWidth();
    int height = source.getHeight();
    int requiredsize = (int)Math.sqrt(a.length / 4);
    pixels = new int[requiredsize * requiredsize];

    if ((width != height) || (width != requiredsize)) {
      BufferedImage img = new BufferedImage(requiredsize, requiredsize, 6);
      Graphics2D gfx = img.createGraphics();
      gfx.drawImage(source, 0, 0, requiredsize, requiredsize, 0, 0, width, height, null);
      img.getRGB(0, 0, requiredsize, requiredsize, pixels, 0, requiredsize);
      gfx.dispose();
    } else {
      source.getRGB(0, 0, width, height, pixels, 0, width);
    }

    update();
  }

  public void update() {
    for (int i = 0; i < pixels.length; i++) {
      int a = pixels[i] >> 24 & 0xFF;
      int r = pixels[i] >> 16 & 0xFF;
      int g = pixels[i] >> 8 & 0xFF;
      int b = pixels[i] >> 0 & 0xFF;

      if (c) {
        int grey = (r + g + b) / 3;
        r = g = b = grey;
      }

      this.a[(i * 4 + 0)] = (byte)r;
      this.a[(i * 4 + 1)] = (byte)g;
      this.a[(i * 4 + 2)] = (byte)b;
      this.a[(i * 4 + 3)] = (byte)a;
    }

    oldanaglyph = c;
  }

  public void a()
  {
    if (oldanaglyph != c)
      update();
  }

  public static BufferedImage scale2x(BufferedImage in)
  {
    int width = in.getWidth();
    int height = in.getHeight();
    BufferedImage out = new BufferedImage(width * 2, height * 2, 2);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int E = in.getRGB(x, y);
        int B;
        int B;
        if (y == 0)
          B = E;
        else
          B = in.getRGB(x, y - 1);
        int D;
        int D;
        if (x == 0)
          D = E;
        else
          D = in.getRGB(x - 1, y);
        int F;
        int F;
        if (x >= width - 1)
          F = E;
        else
          F = in.getRGB(x + 1, y);
        int H;
        int H;
        if (y >= height - 1)
          H = E;
        else
          H = in.getRGB(x, y + 1);
        int E3;
        int E0;
        int E1;
        int E2;
        int E3;
        if ((B != H) && (D != F)) {
          int E0 = D == B ? D : E;
          int E1 = B == F ? F : E;
          int E2 = D == H ? D : E;
          E3 = H == F ? F : E;
        } else {
          E0 = E;
          E1 = E;
          E2 = E;
          E3 = E;
        }
        out.setRGB(x * 2, y * 2, E0);
        out.setRGB(x * 2 + 1, y * 2, E1);
        out.setRGB(x * 2, y * 2 + 1, E2);
        out.setRGB(x * 2 + 1, y * 2 + 1, E3);
      }
    }
    return out;
  }
}