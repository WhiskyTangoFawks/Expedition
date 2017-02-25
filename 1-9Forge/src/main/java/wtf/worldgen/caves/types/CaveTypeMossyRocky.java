package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import wtf.init.BlockSets.Modifier;
import wtf.worldgen.GeneratorMethods;

public class CaveTypeMossyRocky extends CaveTypeRocky{

		public CaveTypeMossyRocky(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
			super(name, ceilingAddonPercentChance, floorAddonPercentChance);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
			super.generateCeiling(gen, random, pos, depth);
			if (simplex.get3DNoiseShifted(gen.getWorld(), pos, 100) > 0.80){
				gen.transformBlock(pos, Modifier.MOSSY);
			}
			
		}

		@Override
		public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
			super.generateFloor(gen, random, pos, depth);
			if (simplex.get3DNoiseShifted(gen.getWorld(), pos, 100) > 0.80){
				gen.transformBlock(pos, Modifier.MOSSY);
			}
		}


		@Override
		public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {
			super.generateWall(gen, random, pos, depth, height);
			if (simplex.get3DNoiseShifted(gen.getWorld(), pos, 100) > 0.80){
				gen.transformBlock(pos, Modifier.MOSSY);
			}
		}
}
