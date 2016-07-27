package wtf.cavebiomes.worldgeneration.dungeontypes.mob;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.cavebiomes.worldgeneration.AbstractDungeonType;
import wtf.cavebiomes.worldgeneration.CaveBiomeGenMethods;
import wtf.core.Core;
import wtf.core.utilities.wrappers.CaveListWrapper;
import wtf.core.utilities.wrappers.CavePosition;

public class DungeonTypeSkeletonMage extends AbstractDungeonType{


	public DungeonTypeSkeletonMage() {
		super("SkeletonMage", 0, 0);
	}

	@Override
	public boolean canGenerateAt(CaveBiomeGenMethods gen, CaveListWrapper cave) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void generateCenter(CaveBiomeGenMethods gen, Random rand, CavePosition pos, float depth) {
		gen.blocksToSet.put(pos.getFloorPos().up(), Blocks.ENCHANTING_TABLE.getDefaultState());
		gen.spawnVanillaSpawner(pos.getFloorPos(), Core.coreID+".SkeletonMage", 2);
	}

	@Override
	public void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		gen.replaceBlock(pos, Blocks.OBSIDIAN.getDefaultState());
		
	}

	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		gen.replaceBlock(pos, Blocks.OBSIDIAN.getDefaultState());
		
	}

	@Override
	public void generateCeilingAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateFloorAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height) {
		if (height == 3){
			gen.replaceBlock(pos, Blocks.BOOKSHELF.getDefaultState());
		}
		else {
			gen.replaceBlock(pos, Blocks.OBSIDIAN.getDefaultState());
		}
	}
}
