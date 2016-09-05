package wtf.worldgen.caves;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import wtf.config.OverworldGenConfig;
import wtf.core.utilities.wrappers.SurfacePos;
import wtf.init.BlockSets.Modifier;
import wtf.worldgen.AbstractCaveType;
import wtf.worldgen.CaveBiomeGenMethods;

public class CaveTypeRocky extends AbstractCaveType{

	public CaveTypeRocky(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
	}

	@Override
	public void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		if (random.nextBoolean() && getNoise(pos, 2, 0.2F) < 1){
			gen.transformBlock(pos, Modifier.COBBLE);
		}
		
	}

	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		if (random.nextBoolean() && getNoise(pos, 5, 1F) < 1){
			gen.transformBlock(pos, Modifier.COBBLE);
		}
		
	}

	@Override
	public void generateCeilingAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		if (random.nextFloat() < depth && gen.genStalactite(pos, depth, false)){
			
		}
		else {
			gen.setCeilingAddon(pos, Modifier.COBBLE);
		}
		
	}

	@Override
	public void generateFloorAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		if (random.nextFloat() < depth && gen.genStalagmite(pos, depth, false)){
			
		}
		else {
			gen.setFloorAddon(pos, Modifier.COBBLE);
		}
	}

	@Override
	public void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height) {
		if (random.nextBoolean() && getNoise(pos, 5, 1) < 1.5){
			gen.transformBlock(pos, Modifier.COBBLE);
		}

	}
	public void setTopBlock(CaveBiomeGenMethods gen, Random random, SurfacePos pos){
		if (getNoise(pos, 0.05, 1) < OverworldGenConfig.mountainFracChunkPercent){
			//and if the blockpos simplex is > than the frac frequency
			if (getNoise(pos, 1, 1F) < OverworldGenConfig.mountainFracFreq){
				gen.transformBlock(pos, Modifier.COBBLE);
			}
		}
	}


}