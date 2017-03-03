package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import wtf.init.WTFBlocks;
import wtf.worldgen.GeneratorMethods;

public class CaveTypeIceRocky extends CaveTypeRocky{

	public CaveTypeIceRocky(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		super.generateFloor(gen, random, pos, depth);
		if (simplex.get3DNoiseScaled(gen.getWorld(),pos, 0.2) < 0.5 ){
			gen.setPatch(pos, WTFBlocks.icePatch.getDefaultState());
		}
	}

}
