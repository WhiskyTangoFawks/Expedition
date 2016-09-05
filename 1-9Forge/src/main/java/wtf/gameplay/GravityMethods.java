package wtf.gameplay;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import wtf.config.GameplayConfig;
import wtf.init.BlockSets;

public class GravityMethods {
	
	private final static Random random = new Random();

	private static int grassHash = Blocks.GRASS.hashCode();
	private static int airHash = Blocks.AIR.hashCode();
	
	public static void checkPos(World world, BlockPos pos
			){
		
		IBlockState state =world.getBlockState(pos); 
		Block block = state.getBlock();
		
		if (block.hashCode() == grassHash){
			block = Blocks.DIRT;
		}
		if (!BlockSets.fallingBlocks.containsKey(block)){
			return;
		}
		
		int blockhash = block.hashCode();
		double fallchance = 1;

		for (int loop = 1; loop < 6 && blockhash == world.getBlockState(pos.up(loop)).getBlock().hashCode(); loop++){
			fallchance *= (1-BlockSets.fallingBlocks.get(block));
		}
		if (random.nextFloat() < fallchance){
			dropBlock(world, pos, true);
		}
		

			//start check for tower conditions : all adjacent are air, and unstable block below
			BlockPos downpos = pos.down();
			Block downBlock = world.getBlockState(downpos).getBlock();
			if (BlockSets.fallingBlocks.containsKey(downBlock)
					&& unstableTowerPos(world, pos.down())
					&& !fenceNear(world, pos, 1, 2)){ //we check for fences down two blocks at the start, then in each posCheck it checks down 1 level
				
				int count = 1;
				while (count < 5){
					if (unstableTowerPos(world, pos.down(count))){
						count++;
					}
					else {
						break;
					}
					
				}
				System.out.println("count = " + count);
				if (random.nextFloat()*count > BlockSets.fallingBlocks.get(downBlock)){
					EntityFallingBlock entityfallingblock = new WTFSlidingBlock(world, pos, getRandomAdj(pos));
					entityfallingblock.setHurtEntities(GameplayConfig.fallingBlocksDamage);
				}
				//downward iteration is within the sliding block class
			}
	}
	
	public static boolean unstableTowerPos(World world, BlockPos pos){
		return world.getBlockState(pos.north()).getBlock().hashCode() == airHash
		&& world.getBlockState(pos.north().east()).getBlock().hashCode() == airHash
		&& world.getBlockState(pos.north().west()).getBlock().hashCode() == airHash
		&& world.getBlockState(pos.south()).getBlock().hashCode() == airHash
		&& world.getBlockState(pos.south().east()).getBlock().hashCode() == airHash
		&& world.getBlockState(pos.south().west()).getBlock().hashCode() == airHash
		&& world.getBlockState(pos.east()).getBlock().hashCode() == airHash
		&& world.getBlockState(pos.west()).getBlock().hashCode() == airHash
		&& !fenceNear(world, pos.down(2), 1, 0);
	}

	public static boolean dropBlock(World world, BlockPos pos, Boolean checkSupport){

		
		if (BlockSets.fallingBlocks.containsKey(world.getBlockState(pos).getBlock())){

			if (checkSupport && fenceNear(world, pos, 1, 1)){
				return false;
			}

			EntityFallingBlock entityfallingblock = new EntityFallingBlock(world, pos.getX()+0.5, pos.getY(), pos.getZ()+0.5, world.getBlockState(pos));
			entityfallingblock.setHurtEntities(GameplayConfig.fallingBlocksDamage);
			if (world.spawnEntityInWorld(entityfallingblock)){

			}

			if (checkSupport){
				checkPos(world, pos.up());
			}
			else {
				dropBlock(world, pos.up(), false);
			}
			return true;
		}
		return false;
	}
	
	public static void throwBlock(World world, BlockPos pos, Vec3d vec){
		EntityFallingBlock entityfallingblock = new EntityFallingBlock(world, pos.getX()+0.5, pos.getY(), pos.getZ()+0.5, world.getBlockState(pos));
		entityfallingblock.setHurtEntities(GameplayConfig.fallingBlocksDamage);
		if (world.spawnEntityInWorld(entityfallingblock)){
			entityfallingblock.setVelocity(vec.xCoord, vec.yCoord, vec.zCoord);
			entityfallingblock.velocityChanged=true;
		}
	}
	
	public static boolean fenceNear(World world, BlockPos pos, int radius, int down){
		for (int xloop = -radius; xloop < radius+1; xloop++){
			for (int yloop = -down; yloop < 1; yloop++){
				for (int zloop = -radius; zloop < radius+1; zloop++){
					if (world.getBlockState(new BlockPos(pos.getX()+xloop, pos.getY()+yloop, pos.getZ()+zloop)).getBlock() instanceof BlockFence){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static BlockPos getRandomAdj(BlockPos pos){
		int chance = random.nextInt(8);
		switch (chance){
		case 0:
			return pos.north();
		case 1:
			return pos.north().east();
		case 2:
			return pos.east();
		case 3:
			return pos.south().east();
		case 4:
			return pos.south();
		case 5:
			return pos.south().west();
		case 6:
			return pos.west();
		case 7:
			return pos.north().west();
		}
		return null;
	}


}
