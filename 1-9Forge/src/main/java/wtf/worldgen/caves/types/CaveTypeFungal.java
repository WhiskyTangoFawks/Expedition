package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.caves.AbstractCaveType;

public class CaveTypeFungal extends AbstractCaveType{



	public CaveTypeFungal(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);

	}

	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		if (simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.33) < 0.33){
			gen.replaceBlock(pos, Blocks.DIRT.getDefaultState());
		}

	}


	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		if (simplex.get3DNoise(gen.getWorld(), pos.getX()/3, pos.getY(), pos.getZ()/3) <0.33){
			gen.replaceBlock(pos, Blocks.MYCELIUM.getDefaultState());
		}
		
		
	}


	@Override
	public void generateCeilingAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
			gen.genSpeleothem(pos, getSpelSize(random, depth), depth, false);
	}


	@Override
	public void generateFloorAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		if (random.nextBoolean()){
		if (pos.getY() < 53){
			gen.replaceBlock(pos, Blocks.BROWN_MUSHROOM.getDefaultState());
		}
		else {
			gen.replaceBlock(pos, Blocks.RED_MUSHROOM.getDefaultState());
		}
		}
		else {
			gen.genSpeleothem(pos, getSpelSize(random, depth), depth, false);
		}

	}


	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {
		if (simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.33) < 0.33){
			gen.replaceBlock(pos, Blocks.DIRT.getDefaultState());
		}

	}




}
