import java.io.File;
import java.lang.reflect.Field;

import net.minecraft.client.Minecraft; 

public class Start
{
	public static void main(String[] args)
	{
		Field f;
		try 
		{
			try
			{
				f = Minecraft.class.getDeclaredField("af");
			}
			catch (NoSuchFieldException e)
			{
				f = Minecraft.class.getDeclaredField("minecraftDir");
			}
			Field.setAccessible(new Field[] { f }, true);
			f.set(null, new File("output"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
		Minecraft.main(args);
	}
}
