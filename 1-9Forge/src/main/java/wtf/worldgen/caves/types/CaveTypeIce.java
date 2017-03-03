package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.init.WTFBlocks;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.caves.AbstractCaveType;

public class CaveTypeIce extends AbstractCaveType{


	public CaveTypeIce(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		
		if (simplex.get3DNoiseScaled(gen.getWorld(),pos, 0.2) < 0.5 ){
			//in shallow caves, depth = 0 to 0.33
			if (simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.1)/3 > depth){
				gen.setPatch(pos, WTFBlocks.icePatch.getDefaultState());
			}
			else {
				gen.setPatch(pos, Blocks.SNOW_LAYER.getDefaultState());
			}
		}
	}


	@Override
	public void generateCeilingAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		gen.genIcicle(pos);
	}


	@Override
	public void generateFloorAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		
		
	}


	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {
		// TODO Auto-generated method stub
		
	}




}
