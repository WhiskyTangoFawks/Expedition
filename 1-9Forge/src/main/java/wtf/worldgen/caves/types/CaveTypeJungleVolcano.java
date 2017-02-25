package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.AdjPos;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.caves.AbstractCaveType;

public class CaveTypeJungleVolcano extends AbstractCaveType{

	public CaveTypeJungleVolcano(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
	}


	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		double noise = simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.1);
		double n = simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.33);
		
		if (noise > 0.66){
			
			if (n > 0.75){
				gen.transformBlock(pos, Modifier.COBBLE);
			}
			else if (n > 0.5){
				gen.transformBlock(pos, Modifier.LAVA_CRUST);
			}
		}
		else {
			boolean mossy =  simplex.get3DNoise(gen.getWorld(), pos) > depth;	
			if (mossy){	
				gen.transformBlock(pos, Modifier.MOSSY);
			}
		}
		if (simplex.get3DNoise(gen.getWorld(), pos) < 0.1){
			gen.transformBlock(pos, Modifier.CRACKED);
		}
		
	}

	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		
		double noise = simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.1);
		double n = simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.33);
		
		if (noise > 0.66){
			
			if (n > 0.75){
				gen.transformBlock(pos, Modifier.COBBLE);
			}
			else if (n > 0.5){
				gen.transformBlock(pos, Modifier.LAVA_CRUST);
			}
		}
		else {
			boolean mossy =  simplex.get3DNoise(gen.getWorld(), pos) > depth;	
			if (mossy){	
				gen.transformBlock(pos, Modifier.MOSSY);
			}
		}
		if (simplex.get3DNoise(gen.getWorld(), pos) < 0.1){
			gen.transformBlock(pos, Modifier.CRACKED);
		}
		
	}


	@Override
	public void generateCeilingAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		double noise = simplex.get3DNoiseScaled(gen.getWorld(), pos.up(), 0.1);
		
		if (noise < 0.66){
			gen.setCeilingAddon(pos, Modifier.COBBLE);
			if (random.nextBoolean()){
				for (int loop = random.nextInt(3)+1; loop > -1; loop--){
					gen.GenVines(pos.east().down(loop), EnumFacing.WEST);
				}
			}
			if (random.nextBoolean()){
				for (int loop = random.nextInt(3)+1; loop > -1; loop--){
					gen.GenVines(pos.west().down(loop), EnumFacing.EAST);
				}
			}
			if (random.nextBoolean()){
				for (int loop = random.nextInt(3)+1; loop > -1; loop--){
					gen.GenVines(pos.north().down(loop), EnumFacing.SOUTH);
				}
			}
			if (random.nextBoolean()){
				for (int loop = random.nextInt(3)+1; loop > -1; loop--){
					gen.GenVines(pos.south().down(loop), EnumFacing.NORTH);
				}
			}
		}
		else {
			gen.genSpeleothem(pos, this.getSpelSize(random, depth), depth, false);
		}
		
	}

	@Override
	public void generateFloorAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		double noise = simplex.get3DNoiseScaled(gen.getWorld(), pos.down(), 0.1);
		if (noise < 0.66){
			if (random.nextBoolean()){
				
			}
			else {
				gen.genSpeleothem(pos, this.getSpelSize(random, depth), depth, false);
			}
		}
		else {
			gen.genSpeleothem(pos, this.getSpelSize(random, depth), depth, false);
		}

	}

	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {

		double noise = simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.1);
		double n = simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.33);
		
		if (noise > 0.66){
			
			if (n > 0.75){
				gen.transformBlock(pos, Modifier.COBBLE);
			}
			else if (n > 0.5){
				gen.transformBlock(pos, Modifier.LAVA_CRUST);
			}
		}
		else {
			boolean mossy =  simplex.get3DNoise(gen.getWorld(), pos) > depth;	
			if (mossy){	
				gen.transformBlock(pos, Modifier.MOSSY);
			}
		}
		if (simplex.get3DNoise(gen.getWorld(), pos) < 0.1){
			gen.transformBlock(pos, Modifier.CRACKED);
		}
	}

	@Override
	public void generateAdjacentWall(GeneratorMethods gen, Random random, AdjPos pos, float depth, int height){
		boolean mossy =  simplex.get3DNoise(gen.getWorld(), pos) > depth;
		if (mossy){
			gen.GenVines(pos, pos.getFace(random));
		}
	}
	
}
