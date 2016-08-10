package wtf.gameplay;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.core.config.GameplayConfig;
import wtf.core.init.BlockSets;

public class GravityMethods {



	public static void dropBlock(World world, int x, int y, int z, Boolean checkStability){

		BlockPos blockpos = new BlockPos(x,y,z);
		if (BlockSets.fallingBlocks.containsKey(world.getBlockState(blockpos).getBlock())){

			for (int xloop = -2; xloop < 3; xloop++){
				for (int yloop = -1; yloop < 1; yloop++){
					for (int zloop = -2; zloop < 3; zloop++){
						if (world.getBlockState(new BlockPos(x+xloop, y+yloop, z+zloop)).getBlock() instanceof BlockFence){
							return;
						}
					}
				}
			}


			EntityFallingBlock entityfallingblock = new EntityFallingBlock(world, x + 0.5F, y, z + 0.5F, world.getBlockState(blockpos));
			entityfallingblock.setHurtEntities(GameplayConfig.fallingBlocksDamage);
			if (world.spawnEntityInWorld(entityfallingblock)){

			}

			if (checkStability){
				disturbBlock(world, x, y+1, z);
			}
			else {
				dropBlock(world, x, y+1, z, false);
			}

		}
	}

	public static void disturbBlock(World world, int x, int y, int z){
		BlockPos pos = new BlockPos(x, y, z);
		Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
		if (BlockSets.fallingBlocks.containsKey(block)){
			if (shouldFall(world, pos, BlockSets.fallingBlocks.get(block))){
				dropBlock(world, x, y, z, true);
			}
		}
	}


	public static boolean shouldFall(World world, BlockPos pos, int stability){

		Block block = world.getBlockState(pos).getBlock();
		for (int loop = 1; loop < stability+1; loop++){
			if (world.getBlockState(pos.up(loop)).getBlock() != block){
				return true;
			}
		}
		return false;
	}

}
