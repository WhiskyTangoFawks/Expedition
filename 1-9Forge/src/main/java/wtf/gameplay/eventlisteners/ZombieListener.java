package wtf.gameplay.eventlisteners;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ZombieListener {

	@SubscribeEvent
	public void SpawnReplacer (LivingSpawnEvent.CheckSpawn event)
	{
		if (!event.getWorld().isRemote){
			if (event.getEntityLiving() instanceof EntityZombie){
				EntityZombie mob = (EntityZombie)event.getEntityLiving();
				if (mob.isChild()){	
				}
			}
		}
	}
}
