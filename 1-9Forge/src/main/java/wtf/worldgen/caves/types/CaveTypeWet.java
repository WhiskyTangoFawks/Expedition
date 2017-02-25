package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import wtf.init.BlockSets;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.caves.AbstractCaveType;

public class CaveTypeWet extends AbstractCaveType {

	public CaveTypeWet(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
	}


	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		if (random.nextFloat()<0.1){
			gen.transformBlock(pos, BlockSets.Modifier.WATER_DRIP);
		}
		
	}

	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		if (simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.25) < 0.33){
			gen.setWaterPatch(pos);
		}
	}

	@Override
	public void generateCeilingAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		gen.genSpeleothem(pos, getSpelSize(random, depth), depth, false);

	}

	@Override
	public void generateFloorAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		gen.genSpeleothem(pos, getSpelSize(random, depth), depth, false);

	}

	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {

	}

	

}
