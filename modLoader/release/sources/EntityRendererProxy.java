import net.minecraft.client.Minecraft;

public class EntityRendererProxy extends pt
{
  private Minecraft game;

  public EntityRendererProxy(Minecraft minecraft)
  {
    super(minecraft);
    game = minecraft;
  }

  public void b(float f1) {
    super.b(f1);

    ModLoader.OnTick(game);
  }
}