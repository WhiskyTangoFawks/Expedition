package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.config.OverworldGenConfig;
import wtf.init.BlockSets;
import wtf.init.WTFBlocks;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.SurfacePos;
import wtf.worldgen.AbstractCaveType;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class CaveTypeWet extends AbstractCaveType {
	
	public CaveTypeWet(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		double noise = getNoise(pos, 6, 0.2F);
		if (random.nextFloat()<0.25){
			gen.transformBlock(pos, BlockSets.Modifier.WATER_DRIP);
		}
		if (random.nextBoolean() && noise < 3*depth){
			gen.transformBlock(pos, Modifier.COBBLE);
		}
	}

	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		double noise = getNoise(pos, 6, 0.2F);
		if (noise < 2){
			gen.setWaterPatch(pos);
		}
		if (random.nextBoolean() && noise < 3*depth){
			gen.transformBlock(pos, Modifier.COBBLE);
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
		double noise = getNoise(pos, 6, 0.2F);
		if (random.nextBoolean() && noise < 3*depth){
			gen.transformBlock(pos, Modifier.COBBLE);
		}
	}
	
	public void setTopBlock(CaveBiomeGenMethods gen, Random random, SurfacePos pos){
		
		if (gen.chunk.getWorld().getBiomeGenForCoords(pos).hashCode() == Biomes.RIVER.hashCode() &&
				getNoise(pos, 1, 1F) < OverworldGenConfig.mountainFracFreq){
				gen.transformBlock(pos, Modifier.COBBLE);
		
		}
	}

}
