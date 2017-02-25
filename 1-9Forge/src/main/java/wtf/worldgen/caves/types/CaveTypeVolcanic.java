package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import wtf.init.BlockSets.Modifier;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.caves.AbstractCaveType;

public class CaveTypeVolcanic extends AbstractCaveType{

	public CaveTypeVolcanic(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {

		double noise = simplex.get3DNoise(gen.getWorld(), pos);
		 if (noise < 0.2){
			gen.transformBlock(pos, Modifier.LAVA_CRUST);
		}
		else if (noise < 0.4){
				gen.transformBlock(pos, Modifier.COBBLE);						
		}
		
	}

	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		
		double noise = simplex.get3DNoise(gen.getWorld(), pos);
		 if (noise < 0.2){
			gen.transformBlock(pos, Modifier.LAVA_CRUST);
		}
		else if (noise < 0.4){
				gen.transformBlock(pos, Modifier.COBBLE);						
		}
		
	}

	@Override
	public void generateCeilingAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		double noise = simplex.get3DNoise(gen.getWorld(), pos.up());
		 if (noise < 0.2){
			 gen.setCeilingAddon(pos, Modifier.COBBLE);
		}
		else {
			gen.genSpeleothem(pos, getSpelSize(random, depth), depth, false);						
		}	
		gen.setCeilingAddon(pos, Modifier.COBBLE);
	}

	@Override
	public void generateFloorAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		double noise = simplex.get3DNoise(gen.getWorld(), pos.down());
		 if (noise < 0.2){
			 gen.setFloorAddon(pos, Modifier.COBBLE);
		}
		else {
			gen.genSpeleothem(pos, getSpelSize(random, depth), depth, false);						
		}	
		gen.setFloorAddon(pos, Modifier.COBBLE);
	}

	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {
		double noise = simplex.get3DNoise(gen.getWorld(), pos);
		 if (noise < 0.2){
			gen.transformBlock(pos, Modifier.LAVA_CRUST);
		}
		else if (noise < 0.4){
				gen.transformBlock(pos, Modifier.COBBLE);						
		}
	}
	
	public void generateAdjacentWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height){

	}

}
