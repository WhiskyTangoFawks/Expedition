package wtf.core.init;

import net.minecraftforge.fml.common.registry.EntityRegistry;
import wtf.core.Core;
import wtf.core.entities.skeleton.SkeletonMage;
import wtf.core.entities.zombie.EntityMummy;

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
