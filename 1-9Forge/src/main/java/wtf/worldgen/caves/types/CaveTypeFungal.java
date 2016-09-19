package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.worldgen.AbstractCaveType;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class CaveTypeFungal extends AbstractCaveType{



	public CaveTypeFungal(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);

	}

	@Override
	public void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		if (getNoise(pos, 2, 0.5F) > 1){
			gen.replaceBlock(pos, Blocks.DIRT.getDefaultState());
		}

	}


	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		if (getNoise(pos, 2, 0.1F) > 1){
			gen.replaceBlock(pos, Blocks.MYCELIUM.getDefaultState());
		}
		
		
	}


	@Override
	public void generateCeilingAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		if (getNoise(pos.up(), 2, 0.5F) > 1){
			gen.replaceBlock(pos, Blocks.DIRT.getDefaultState());
		}
		else {
			gen.genStalactite(pos, depth, false);
		}
	}


	@Override
	public void generateFloorAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		if (getNoise(pos.down(), 2, 0.1F) > 1){
		if (pos.getY() < 53){
			gen.replaceBlock(pos, Blocks.BROWN_MUSHROOM.getDefaultState());
		}
		else {
			gen.replaceBlock(pos, Blocks.RED_MUSHROOM.getDefaultState());
		}
		}
		else {
			gen.genStalagmite(pos, depth, false);
		}

	}


	@Override
	public void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height) {
		if (getNoise(pos, 2, 0.5F) > 1){
			gen.replaceBlock(pos, Blocks.DIRT.getDefaultState());
		}

	}




}
