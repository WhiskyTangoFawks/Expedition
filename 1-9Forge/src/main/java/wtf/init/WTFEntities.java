package wtf.init;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import wtf.Core;
import wtf.entities.customentities.EntityBlockHead;
import wtf.entities.customentities.EntityDerangedGolem;
import wtf.entities.customentities.EntityFireElemental;
import wtf.entities.customentities.EntityFlyingFlame;
import wtf.entities.customentities.EntityZombieGhost;
import wtf.entities.simpleentities.FarmerZombie;
import wtf.entities.simpleentities.Husk;
import wtf.entities.simpleentities.Mummy;
import wtf.entities.simpleentities.SkeletonKnight;
import wtf.entities.simpleentities.SkeletonMage;
import wtf.entities.simpleentities.Stray;
import wtf.entities.simpleentities.Wither;
import wtf.entities.simpleentities.ZombieMiner;

public class WTFEntities {

	
	public static void initEntites(){
		registerEntity(SkeletonMage.class, "SkeletonMage");
		registerEntity(EntityZombieGhost.class, "Ghost");
		registerEntity(EntityDerangedGolem.class, "DerangedIronGolem");
		registerEntity(EntityFlyingFlame.class, "FlyingFlame");
		registerEntity(EntityBlockHead.class, "BlockHead");
		registerEntity(EntityFireElemental.class, "FireElemental");
		
		registerEntity(Mummy.class, "Mummy");
		registerEntity(Husk.class, "Husk");
		registerEntity(ZombieMiner.class, "ZombieMiner");
		registerEntity(FarmerZombie.class, "ZombieFarmer");
		
		registerEntity(Stray.class, "Stray");
		registerEntity(Wither.class, "Wither");
		registerEntity(SkeletonKnight.class, "SkeletonKnight");
		
		Core.proxy.registerEntityRenderers();
	
	}

	
	static int counter = 1;
	public static void registerEntity(Class<? extends Entity> entityClass, String name)
	{
		int entityId = counter;
		counter++;
		
		EntityRegistry.registerModEntity(entityClass, name, entityId, Core.instance, 64, 1, true, counter*31, counter*62);
		
	}

}
