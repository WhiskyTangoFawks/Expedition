package wtf.init;

import net.minecraftforge.fml.common.registry.EntityRegistry;
import wtf.Core;
import wtf.entities.skeletons.SkeletonMage;
import wtf.entities.zombies.EntityMummy;

public class WTFEntities {

	
	public static void initEntites(){
		registerEntity(SkeletonMage.class, "SkeletonMage");
		registerEntity(EntityMummy.class, "ZombieMummy");
	}

	
	static int counter = 1;
	public static void registerEntity(Class entityClass, String name)
	{
		int entityId = counter;
		counter++;
		
		EntityRegistry.registerModEntity(entityClass, name, entityId, Core.instance, 64, 1, true, counter*100, counter*200);
		
	}

}
