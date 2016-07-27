package wtf.core.entities;



import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntitySpawnListener {

	@SubscribeEvent
	public void SpawnReplacer (LivingSpawnEvent.CheckSpawn event)
	{
		if (!event.getWorld().isRemote){

			EntityLiving overrideEntity = null;	
			if (!((EntityLiving) event.getEntityLiving()).getCanSpawnHere()){
					//Checks the spawn rules before going any farther
					event.setResult(Result.DENY);
					return;
				}
				if (event.getEntityLiving() instanceof EntityZombie){
					overrideEntity = SpawnHandlerZombie.zombieSpawn(event);
				}
				else if (event.getEntityLiving() instanceof EntitySkeleton){
					overrideEntity = SpawnHandlerSkeleton.skeletonSpawn(event);
				}

				if (overrideEntity != null){
					EntityLivingBase oldEntity = event.getEntityLiving();
					event.setResult(Result.DENY);
					overrideEntity.setLocationAndAngles(oldEntity.posX, oldEntity.posY, oldEntity.posZ, oldEntity.rotationYaw, oldEntity.rotationPitch);
					oldEntity.worldObj.spawnEntityInWorld(overrideEntity);
					oldEntity.setDead();
				}

			}
		
	}
	
	@SubscribeEvent
	public void mobSpawnerReplacer (LivingSpawnEvent.SpecialSpawn event){
		if (!event.getWorld().isRemote){

			EntityLiving overrideEntity = null;	
			if (!((EntityLiving) event.getEntityLiving()).getCanSpawnHere()){
					//Checks the spawn rules before going any farther
					event.setResult(Result.DENY);
					return;
				}
				if (event.getEntityLiving() instanceof EntityZombie){
					overrideEntity = SpawnHandlerZombie.zombieSpawn(event);
				}
				else if (event.getEntityLiving() instanceof EntitySkeleton){
					overrideEntity = SpawnHandlerSkeleton.skeletonSpawn(event);
				}


				if (overrideEntity != null){
					EntityLivingBase oldEntity = event.getEntityLiving();
					event.setResult(Result.DENY);
					
					//My custom entities are spawning inside the wall or inside blocks- not sure why they're doing it, but I need to fix it
					BlockPos pos = new BlockPos(oldEntity.posX, oldEntity.posY, oldEntity.posZ);
					for (int loop = 0; loop < 3; loop++){
						if (!event.getWorld().isBlockNormalCube(pos, false)){
							overrideEntity.setLocationAndAngles(oldEntity.posX, oldEntity.posY, oldEntity.posZ, oldEntity.rotationYaw, oldEntity.rotationPitch);
							oldEntity.worldObj.spawnEntityInWorld(overrideEntity);
							oldEntity.setDead();
							return;
						}
						else {
							pos = pos.up();
						}
						
					}
					oldEntity.setDead();
				}

			}
		
	}




}
