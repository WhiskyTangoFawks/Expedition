package wtf.worldgen.caves;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import wtf.init.BlockSets.Modifier;
import wtf.worldgen.AbstractCaveType;
import wtf.worldgen.CaveBiomeGenMethods;

public class CaveTypeVolcanic extends AbstractCaveType{

	public CaveTypeVolcanic(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {

		if (getNoise(pos, 5, 0.1F) < 1){
			gen.transformBlock(pos, Modifier.LAVA_DRIP);
		}
	}

	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		
		
		double noise = getNoise(pos, 8, 0.05F);
		//System.out.println(noise);
		if (noise < 2){
			gen.setLavaPatch(pos);
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
		if (getNoise(pos.down(), 5, 0.1F) >= 2){
			gen.setFloorAddon(pos, Modifier.COBBLE);
		}
	}

	@Override
	public void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height) {
		if (getNoise(pos, 5, 0.1F) >= 2){
			gen.transformBlock(pos, Modifier.COBBLE);
		}
	}
	
	public void generateAdjacentWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height){

	}

}
