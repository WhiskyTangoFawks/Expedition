package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.caves.AbstractCaveType;

public class CaveTypeDefault extends AbstractCaveType{


	public CaveTypeDefault(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void generateCeilingAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		//size calculation for speleothem?
		gen.genSpeleothem(pos, getSpelSize(random, depth), depth, false);
		
	}
	@Override
	public void generateFloorAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		gen.genSpeleothem(pos, getSpelSize(random, depth), depth, false);
		
	}
	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {
		// TODO Auto-generated method stub
		
	}




}
