package wtf.core.entities;

import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import wtf.core.entities.skeleton.SkeletonMage;
import wtf.core.utilities.GenMethods;

public class SpawnHandlerSkeleton {
	
	static Random random = ThreadLocalRandom.current();
	
	public static EntitySkeleton skeletonSpawn(LivingSpawnEvent event){
		

		if (event.getY() < 60){ // if underground
			HashSet<Block> nearbyBlocks = GenMethods.getAreaHashSet(event.getWorld(), new BlockPos(event.getX(), event.getY(), event.getZ()), -3, 3, -2, 2, -3, 3);
		
			if (nearbyBlocks.contains(Blocks.STONEBRICK) && random.nextBoolean()){
				if (random.nextBoolean()){
					return new SkeletonMage(event.getWorld());
				}
				else {
					EntityDresser.setKnight((EntitySkeleton)(event.getEntityLiving()));
				}
			}
			//if (BiomeDictionary.isBiomeOfType(biome, Type.SNOWY) && event.y <40){
			//	replaceEntity(event.entity, new SkeletonIce(event.world));
			//}
			//else if (event.y < 32 && random.nextBoolean()){
			//	replaceEntity(event.entity, new SkeletonLava(event.world));
			//}
		}
		return null;
	}

}
