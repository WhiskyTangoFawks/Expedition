package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
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
		double noise = getNoise(gen.getWorld(), pos, 6, 0.2F);
		if (random.nextFloat()<0.25){
			gen.transformBlock(pos, BlockSets.Modifier.WATER_DRIP);
		}
		if (random.nextBoolean() && noise < 3*depth){
			gen.transformBlock(pos, Modifier.COBBLE);
		}
	}

	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		double noise = getNoise(gen.getWorld(), pos, 6, 0.2F);
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
		double noise = getNoise(gen.getWorld(), pos, 6, 0.2F);
		if (random.nextBoolean() && noise < 3*depth){
			gen.transformBlock(pos, Modifier.COBBLE);
		}
	}

	int gravelHash = Blocks.GRAVEL.hashCode();
	public void setTopBlock(CaveBiomeGenMethods gen, Random random, SurfacePos pos){

		if (Biome.getIdForBiome(gen.getWorld().getBiome(pos)) == 7
				&& getNoise(gen.getWorld(), pos, 1, 0.05F) < OverworldGenConfig.riverFracChunkPercent && getNoise(gen.getWorld(), pos, 1, 1) < OverworldGenConfig.riverFracFreq){
			if (gen.getBlockState(pos).getBlock().hashCode() == gravelHash){
				gen.replaceBlock(pos, Blocks.COBBLESTONE.getDefaultState());
			}
			else {
				gen.transformBlock(pos, Modifier.COBBLE);
			}


		}

	}

}
