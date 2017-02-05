package wtf.worldgen.subcaves.mob;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.Core;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class DungeonTypeSkeletonMage extends DungeonSimpleSkeleton{


	public DungeonTypeSkeletonMage() {
		super("SkeletonMage");
	}

	@Override
	public void generateCenter(CaveBiomeGenMethods gen, Random rand, CavePosition pos, float depth) {
		gen.blocksToSet.put(pos.getFloorPos().up(2), Blocks.ENCHANTING_TABLE.getDefaultState());
		gen.spawnVanillaSpawner(pos.getFloorPos().up(), Core.coreID+".SkeletonMage", 2);
	}

	@Override
	public void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height) {
		if (height == 3){
			gen.replaceBlock(pos, Blocks.BOOKSHELF.getDefaultState());
		}
		else {
			super.generateWall(gen, random, pos, depth, height);
		}
	}
}
