package wtf.entities;



import java.util.ArrayList;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import wtf.config.GameplayConfig;
import wtf.init.WTFArmor;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntitySpawnListener {

	@SubscribeEvent
	public void SpawnReplacer (LivingSpawnEvent.CheckSpawn event)
	{
		if (!event.getWorld().isRemote){

			EntityLiving overrideEntity = null;	
			if (GameplayConfig.deHelmet){
				event.getEntityLiving().setItemStackToSlot(EntityEquipmentSlot.HEAD, null);
			}
			
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
	
	public void SpawnSpecialReplacer (LivingSpawnEvent.CheckSpawn event)
	{
		if (!event.getWorld().isRemote){
			if (GameplayConfig.deHelmet){
				event.getEntityLiving().setItemStackToSlot(EntityEquipmentSlot.HEAD, null);
			}
		}
	}

}
