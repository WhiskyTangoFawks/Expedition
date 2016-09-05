package wtf.worldgen.caves;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.worldgen.AbstractCaveType;
import wtf.worldgen.CaveBiomeGenMethods;

public class CaveTypeIceRocky extends AbstractCaveType{

	public CaveTypeIceRocky(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		double noise = getNoise(pos, 5, 1);
		if (noise > depth*5){
			gen.replaceBlock(pos, Blocks.PACKED_ICE.getDefaultState());
		}
		else if (random.nextBoolean() && noise < depth*3){
			gen.replaceBlock(pos, Blocks.COBBLESTONE.getDefaultState());
		}

	}

	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {

		double noise = getNoise(pos, 5, 1);
		if (noise > depth*5){
			gen.replaceBlock(pos, Blocks.PACKED_ICE.getDefaultState());
		}
		else if (random.nextBoolean() && noise < depth*3){
			gen.replaceBlock(pos, Blocks.COBBLESTONE.getDefaultState());
		}
		gen.setIcePatch(pos);

	}

	@Override
	public void generateCeilingAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		if (!gen.genStalactite(pos, depth, true)){
			gen.replaceBlock(pos, Blocks.PACKED_ICE.getDefaultState());
		}
	}

	@Override
	public void generateFloorAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		if (!gen.genStalagmite(pos, depth, true)){
			gen.replaceBlock(pos, Blocks.PACKED_ICE.getDefaultState());
		}

	}

	@Override
	public void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height) {
		double noise = getNoise(pos, 5, 1);
		if (noise > depth*5){
			gen.replaceBlock(pos, Blocks.PACKED_ICE.getDefaultState());
		}
		else if (random.nextBoolean() &&noise < depth*3){
			gen.replaceBlock(pos, Blocks.COBBLESTONE.getDefaultState());
		}
	}

}
