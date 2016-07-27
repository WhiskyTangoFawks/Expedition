package wtf.cavebiomes.worldgeneration.cavetypes.caves;

import java.util.Random;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import wtf.cavebiomes.worldgeneration.AbstractCaveType;
import wtf.cavebiomes.worldgeneration.CaveBiomeGenMethods;
import wtf.core.init.BlockSets;
import wtf.core.init.BlockSets.Modifier;
import wtf.core.utilities.wrappers.AdjPos;

public class CaveTypeSwamp extends AbstractCaveType {

	public CaveTypeSwamp(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		if (random.nextFloat()<0.25){
			gen.transformBlock(pos, BlockSets.Modifier.WATER_DRIP);
		}
	}

	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		double noise = getNoise(pos, 5, 0.33F);
		double mossNoise = getNoise(pos, 5, 2F);
		if (noise < 2.5){
			gen.setWaterPatch(pos);
		}

		if (mossNoise < 2*depth && random.nextBoolean()){
			gen.transformBlock(pos, Modifier.COBBLE);
			
		}
		if (mossNoise < 3*depth){
				gen.transformBlock(pos.down(), BlockSets.Modifier.MOSSY);
		}
		
	}

	@Override
	public void generateCeilingAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		if (getNoise(pos, 5, 1) < depth*3 && !gen.isChunkEdge(pos) && gen.setCeilingAddon(pos, Modifier.COBBLE)){
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
			gen.genStalactite(pos, depth, false);
		}
	}


	@Override
	public void generateFloorAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		if (getNoise(pos, 5, 1) < depth*3 && !gen.isChunkEdge(pos) && gen.setFloorAddon(pos, Modifier.COBBLE)){
				gen.GenVines(pos.east(), EnumFacing.WEST);
				gen.GenVines(pos.west(), EnumFacing.EAST);
				gen.GenVines(pos.north(), EnumFacing.SOUTH);
				gen.GenVines(pos.south(), EnumFacing.NORTH);
					}
		else {
			gen.genStalactite(pos, depth, false);
		}
	}


	@Override
	public void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height) {
		
		double mossNoise = getNoise(pos, 5, 2F);
		if (mossNoise < 2*depth && random.nextBoolean()){
			gen.transformBlock(pos, Modifier.COBBLE);
			
		}
		if (mossNoise < 3*depth){
			gen.transformBlock(pos.down(), BlockSets.Modifier.MOSSY);
		}

	}

	@Override
	public void generateAdjacentWall(CaveBiomeGenMethods gen, Random random, AdjPos pos, float depth, int height){
		if (getNoise(pos, 5, 1) < depth*1.5){	
			gen.GenVines(pos, pos.getFace(random));
		}
	}
}
