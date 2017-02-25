package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import wtf.init.BlockSets.Modifier;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.caves.AbstractCaveType;

public class CaveTypeRocky extends AbstractCaveType{

	public CaveTypeRocky(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
	}

	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		double noise = simplex.get3DNoise(gen.getWorld(), pos);
		if (noise < 0.05){
			gen.transformBlock(pos, Modifier.CRACKED);
		}
		else if (noise > 0.75){
			gen.transformBlock(pos, Modifier.COBBLE);
		}
	}

	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		double noise = simplex.get3DNoise(gen.getWorld(), pos);
		if (noise < 0.05){
			gen.transformBlock(pos, Modifier.CRACKED);
		}
		else if (noise > 0.75){
			gen.transformBlock(pos, Modifier.COBBLE);
		}
	}

	@Override
	public void generateCeilingAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		gen.genSpeleothem(pos, getSpelSize(random, depth), depth, false);
	}

	@Override
	public void generateFloorAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		if (random.nextBoolean()){
			gen.genSpeleothem(pos, getSpelSize(random, depth), depth, false);
		}
				
		else {
			gen.setFloorAddon(pos, Modifier.COBBLE);
		}
	}

	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {
		double noise = simplex.get3DNoise(gen.getWorld(), pos);
		if (noise < 0.05){
			gen.transformBlock(pos, Modifier.CRACKED);
		}
		else if (noise > 0.75){
			gen.transformBlock(pos, Modifier.COBBLE);
		}
	}



}