import java.util.Map;
import java.util.Random;
import net.minecraft.client.Minecraft;

public abstract class BaseMod
{
  public int AddFuel(int id)
  {
    return 0;
  }

  public void AddRenderer(Map<Class<? extends si>, bu> renderers)
  {
  }

  public boolean DispenseEntity(fb world, double x, double y, double z, int xVel, int zVel, iw item)
  {
    return false;
  }

  public void GenerateNether(fb world, Random random, int chunkX, int chunkZ)
  {
  }

  public void GenerateSurface(fb world, Random random, int chunkX, int chunkZ)
  {
  }

  public void KeyboardEvent(px event)
  {
  }

  public void ModsLoaded()
  {
  }

  public void OnTickInGame(Minecraft game)
  {
  }

  public void OnTickInGUI(Minecraft game, cy gui)
  {
  }

  public void RegisterAnimation(Minecraft game)
  {
  }

  public void RenderInvBlock(ct renderer, un block, int metadata, int modelID)
  {
  }

  public boolean RenderWorldBlock(ct renderer, xg world, int x, int y, int z, un block, int modelID)
  {
    return false;
  }

  public void TakenFromCrafting(gq player, iw item)
  {
  }

  public void TakenFromFurnace(gq player, iw item)
  {
  }

  public void OnItemPickup(gq player, iw item)
  {
  }

  public String toString()
  {
    return getClass().getName() + " " + Version();
  }

  public abstract String Version();
}