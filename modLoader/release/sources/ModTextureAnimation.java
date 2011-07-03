import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ModTextureAnimation extends av
{
  private final int tickRate;
  private final byte[][] images;
  private int index = 0;
  private int ticks = 0;

  public ModTextureAnimation(int slot, int dst, BufferedImage source, int rate) {
    this(slot, 1, dst, source, rate);
  }

  public ModTextureAnimation(int slot, int size, int dst, BufferedImage source, int rate) {
    super(slot);

    e = size;
    f = dst;
    tickRate = rate;
    ticks = rate;

    int width = source.getWidth();
    int height = source.getHeight();
    int images = (int)Math.floor(height / width);
    if (images <= 0)
      throw new IllegalArgumentException("source has no complete images");
    int requiredsize = (int)Math.sqrt(this.a.length / 4);

    this.images = new byte[images][];

    if (width != requiredsize) {
      BufferedImage img = new BufferedImage(requiredsize, requiredsize * images, 6);
      Graphics2D gfx = img.createGraphics();
      gfx.drawImage(source, 0, 0, requiredsize, requiredsize * images, 0, 0, width, height, null);
      gfx.dispose();

      source = img;
    }

    for (int i = 0; i < images; i++)
    {
      int[] temp = new int[requiredsize * requiredsize];

      source.getRGB(0, requiredsize * i, requiredsize, requiredsize, temp, 0, requiredsize);

      this.images[i] = new byte[requiredsize * requiredsize * 4];
      for (int j = 0; j < temp.length; j++) {
        int a = temp[j] >> 24 & 0xFF;
        int r = temp[j] >> 16 & 0xFF;
        int g = temp[j] >> 8 & 0xFF;
        int b = temp[j] >> 0 & 0xFF;

        this.images[i][(j * 4 + 0)] = (byte)r;
        this.images[i][(j * 4 + 1)] = (byte)g;
        this.images[i][(j * 4 + 2)] = (byte)b;
        this.images[i][(j * 4 + 3)] = (byte)a;
      }
    }
  }

  public void a()
  {
    if (ticks >= tickRate) {
      index += 1;
      if (index >= images.length)
        index = 0;
      a = images[index];
      ticks = 0;
    }
    ticks += 1;
  }
}