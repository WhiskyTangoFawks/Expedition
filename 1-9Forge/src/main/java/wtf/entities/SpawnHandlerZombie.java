package wtf.entities;

import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.ZombieType;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import wtf.config.GameplayConfig;
import wtf.entities.zombies.EntityMummy;
import wtf.init.WTFArmor;
import wtf.utilities.GenMethods;

public class SpawnHandlerZombie {

	static Random random = ThreadLocalRandom.current();

	public static EntityLiving zombieSpawn(LivingSpawnEvent event){	
		Biome biome = event.getWorld().getBiome(new BlockPos(event.getX(), event.getY(), event.getZ()));
		EntityZombie zombie =(EntityZombie) event.getEntityLiving();
		if (zombie.isChild() && GameplayConfig.childZombie){
			zombie.setChild(false);
		}
		if (BiomeDictionary.isBiomeOfType(biome, Type.SANDY)){
			if (zombie.getZombieType() == ZombieType.HUSK){
				if (random.nextBoolean()){
					zombie.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(WTFArmor.mummyChestplate));
				}
				if (random.nextBoolean()){
					zombie.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(WTFArmor.mummyLeggings));
				}
				if (random.nextBoolean()){
				zombie.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(WTFArmor.mummyBoots));	 
				}
				if (random.nextBoolean()){
					zombie.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(WTFArmor.mummyHelmet));
				}
				zombie.setDropChance(EntityEquipmentSlot.HEAD, 0F);
				zombie.setDropChance(EntityEquipmentSlot.CHEST, 0F);
				zombie.setDropChance(EntityEquipmentSlot.LEGS, 0F);
				zombie.setDropChance(EntityEquipmentSlot.FEET, 0F);

				//returns null, so that spawn isn't cancelled
				return null;
			}
			return new EntityMummy(event.getWorld(), false);
		}

		if (event.getY() < 60){ // if underground
			HashSet<Block> nearbyBlocks = GenMethods.getAreaHashSet(event.getWorld(), new BlockPos(event.getX(), event.getY(), event.getZ()), -3, 3, -2, 2, -3, 3);
			if (nearbyBlocks.contains(Blocks.RAIL)){
				EntityDresser.setMiner(zombie);
			}
		}
		//else if (BiomeDictionary.isBiomeOfType(biome, Type.SNOWY)){
		//	replaceEntity(event.entity, new ZombieFrozen(event.world));
		//}
		//}
		return null;
	}
}
