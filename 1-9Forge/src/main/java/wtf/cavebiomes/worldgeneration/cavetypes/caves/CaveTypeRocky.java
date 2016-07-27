package wtf.cavebiomes.worldgeneration.cavetypes.caves;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import wtf.cavebiomes.worldgeneration.AbstractCaveType;
import wtf.cavebiomes.worldgeneration.CaveBiomeGenMethods;
import wtf.core.init.BlockSets.Modifier;

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
		if (random.nextBoolean() && getNoise(pos, 2, 0.2F) < 1){
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
		if (random.nextBoolean() && getNoise(pos, 5, 1) < 2.5){
			gen.transformBlock(pos, Modifier.COBBLE);
		}

	}



}