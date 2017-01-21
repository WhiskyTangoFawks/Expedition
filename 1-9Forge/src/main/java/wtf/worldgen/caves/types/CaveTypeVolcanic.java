package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import wtf.config.CaveBiomesConfig;
import wtf.init.BlockSets.Modifier;
import wtf.worldgen.AbstractCaveType;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class CaveTypeVolcanic extends AbstractCaveType{

	public CaveTypeVolcanic(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {

		if (getNoise(gen.chunk.getWorld(), pos, 5, 0.1F) < 1){
			gen.transformBlock(pos, Modifier.LAVA_DRIP);
		}
	}

	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		
		
		double noise = getNoise(gen.chunk.getWorld(), pos, 8, 1F);
		//System.out.println(noise);
		if (noise < 2){
			if (CaveBiomesConfig.enableLavaPools){
				gen.setLavaPatch(pos);
			}
			gen.transformBlock(pos, Modifier.LAVA_CRUST);
		}
		else if (noise < 3){
			gen.transformBlock(pos, Modifier.LAVA_CRUST);
		}
		else if (noise < 4){
				gen.transformBlock(pos, Modifier.COBBLE);						
		}
		
	}

	@Override
	public void generateCeilingAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
			gen.setCeilingAddon(pos, Modifier.COBBLE);
		}

	@Override
	public void generateFloorAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		if (getNoise(gen.chunk.getWorld(), pos.down(), 5, 0.1F) >= 2){
			gen.setFloorAddon(pos, Modifier.COBBLE);
		}
	}

	@Override
	public void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height) {
		if (getNoise(gen.chunk.getWorld(), pos, 5, 0.1F) >= 2){
			gen.transformBlock(pos, Modifier.COBBLE);
		}
	}
	
	public void generateAdjacentWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height){

	}

}
