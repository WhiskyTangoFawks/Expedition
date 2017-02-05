package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.block.BlockDirt;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import wtf.utilities.wrappers.ChunkScan;
import wtf.worldgen.AbstractCaveType;
import wtf.worldgen.caves.CaveBiomeGenMethods;
import wtf.worldgen.trees.TreeTypeGetter;
import wtf.worldgen.trees.TreeVars;
import wtf.worldgen.trees.types.PoplarTree;

public class CaveTypeDirtWater extends AbstractCaveType{

	public CaveTypeDirtWater(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {

	}


	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		
		if (getNoise(gen.getWorld(), pos, 5, 0.1F) < 0.65 ){
			gen.setWaterPatch(pos);
		}
		if (getNoise(gen.getWorld(), pos, 5, 1F) < 1 ){
			gen.replaceBlock(pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT));
		}

	}


	@Override
	public void generateCeilingAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		gen.genStalactite(pos, depth, false);

	}


	@Override
	public void generateFloorAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		gen.genStalagmite(pos, depth, false);
	}


	@Override
	public void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height) {

		if (getNoise(gen.getWorld(), pos, 5, 0.5F) < height/2 ){ //dirt
			gen.replaceBlock(pos, Blocks.DIRT.getDefaultState());
		}

	}



}
