package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import wtf.init.BlockSets.Modifier;
import wtf.init.WTFBlocks;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.caves.AbstractCaveType;

public class CaveTypeMossy extends AbstractCaveType{

	public CaveTypeMossy(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
	}

	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {

		if (simplex.get3DNoise(gen.getWorld(), pos) > 0.66){
			gen.transformBlock(pos, Modifier.MOSSY);
		}
	}

	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		if (simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.2) < 0.3){
			gen.replaceBlock(pos.up(), WTFBlocks.dirtSlab.getDefaultState());
		}
		else {
			if (simplex.get3DNoise(gen.getWorld(), pos) > 0.66){
				gen.transformBlock(pos, Modifier.MOSSY);
			}
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

		if (simplex.get3DNoise(gen.getWorld(), pos) > 0.66){
			gen.transformBlock(pos, Modifier.MOSSY);
		}
	}



}




