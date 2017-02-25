package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import wtf.init.BlockSets;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.AdjPos;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.caves.AbstractCaveType;

public class CaveTypeSwamp extends AbstractCaveType {

	public CaveTypeSwamp(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		if (random.nextFloat()<0.15){
			gen.transformBlock(pos, BlockSets.Modifier.WATER_DRIP);
		} else{
			if (simplex.get3DNoise(gen.getWorld(), pos) <0.1){
				gen.transformBlock(pos, Modifier.COBBLE);
				
			}
			if (simplex.get3DNoise(gen.getWorld(), pos)-0.5 > depth){
				gen.transformBlock(pos, Modifier.MOSSY);
			}
		}
	}

	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		if (simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.5) < 0.2){
			gen.setWaterPatch(pos);
		}

		if (simplex.get3DNoise(gen.getWorld(), pos) <0.1){
			gen.transformBlock(pos, Modifier.COBBLE);
			
		}
		if (simplex.get3DNoise(gen.getWorld(), pos)-0.5 > depth){
			gen.transformBlock(pos, Modifier.MOSSY);
		}
		
	}

	@Override
	public void generateCeilingAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		if (random.nextBoolean() && gen.setCeilingAddon(pos, Modifier.COBBLE)){
			for (int loop = random.nextInt(3)+1; loop > -1; loop--){
				gen.GenVines(pos.east().down(loop), EnumFacing.WEST);
			}
			for (int loop = random.nextInt(3)+1; loop > -1; loop--){
				gen.GenVines(pos.west().down(loop), EnumFacing.EAST);
			}
			for (int loop = random.nextInt(3)+1; loop > -1; loop--){
				gen.GenVines(pos.north().down(loop), EnumFacing.SOUTH);
			}
			for (int loop = random.nextInt(3)+1; loop > -1; loop--){
				gen.GenVines(pos.south().down(loop), EnumFacing.NORTH);
			}

		}
		else {
			gen.genSpeleothem(pos, getSpelSize(random, depth), depth, false);
		}
	}


	@Override
	public void generateFloorAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		if (random.nextBoolean() && gen.setFloorAddon(pos, Modifier.COBBLE)){
				gen.GenVines(pos.east(), EnumFacing.WEST);
				gen.GenVines(pos.west(), EnumFacing.EAST);
				gen.GenVines(pos.north(), EnumFacing.SOUTH);
				gen.GenVines(pos.south(), EnumFacing.NORTH);
					}
		else {
			gen.genSpeleothem(pos, getSpelSize(random, depth), depth, false);
		}
	}


	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {
		
		if (simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.5) < 0.2){
			gen.setWaterPatch(pos);
		}

		if (simplex.get3DNoise(gen.getWorld(), pos) <0.1){
			gen.transformBlock(pos, Modifier.COBBLE);
			
		}
		if (simplex.get3DNoise(gen.getWorld(), pos)-0.5 > depth){
			gen.transformBlock(pos, Modifier.MOSSY);
		}

	}

	@Override
	public void generateAdjacentWall(GeneratorMethods gen, Random random, AdjPos pos, float depth, int height){
		if (simplex.get3DNoise(gen.getWorld(), pos) > depth*2){
			gen.GenVines(pos, pos.getFace(random));
		}
	}

}
