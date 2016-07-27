package wtf.cavebiomes.worldgeneration.dungeontypes.ambient;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.cavebiomes.worldgeneration.AbstractDungeonType;
import wtf.cavebiomes.worldgeneration.CaveBiomeGenMethods;
import wtf.core.blocks.AnimatedBlock;
import wtf.core.init.BlockSets;
import wtf.core.init.BlockSets.Modifier;
import wtf.core.utilities.wrappers.CaveListWrapper;
import wtf.core.utilities.wrappers.CavePosition;

public class DungeonTypeRain extends AbstractDungeonType{


	public DungeonTypeRain(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canGenerateAt(CaveBiomeGenMethods gen, CaveListWrapper cave) {
		return true;
	}

	@Override
	public void generateCeilingAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		gen.genStalactite(pos, depth, false);
	}


	@Override
	public void generateCenter(CaveBiomeGenMethods gen, Random rand, CavePosition pos, float depth) {
		// TODO Auto-generated method stub

	}

	@Override
	public void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		if (gen.transformBlock(pos, BlockSets.Modifier.WATER_DRIP) && random.nextBoolean()){
			((AnimatedBlock)gen.getBlockState(pos).getBlock()).setFast(gen.chunk.getWorld(), pos);
		}
		else {
			int var = random.nextInt(3);
			switch (var){
			case 0:
				gen.transformBlock(pos, Modifier.COBBLE);
				break;
			case 1:
				gen.transformBlock(pos, Modifier.MOSSY);
				break;
			case 2:
				gen.transformBlock(pos, Modifier.COBBLE);
				gen.transformBlock(pos, Modifier.MOSSY);
				break;
			}
		}

	}

	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		int var = random.nextInt(3);
		boolean water = random.nextBoolean();
		switch (var){
		case 0:
			if (water){
				gen.setWaterPatch(pos);
				gen.transformBlock(pos, Modifier.COBBLE);
			}
			else {
				gen.transformBlock(pos, Modifier.COBBLE);
			}
			break;
		case 1:
			if (water){
				gen.setWaterPatch(pos);
				gen.transformBlock(pos, Modifier.MOSSY);
			}
			else {
				gen.transformBlock(pos, Modifier.MOSSY);	
			}
			break;
		case 2:
			if (water){
				gen.setWaterPatch(pos);
				gen.transformBlock(pos, Modifier.COBBLE);
				gen.transformBlock(pos, Modifier.MOSSY);
			}
			else {
				gen.transformBlock(pos, Modifier.COBBLE);
				gen.transformBlock(pos.down(), Modifier.MOSSY);
			}
			break;
		}

	}

	@Override
	public void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height) {
		int var = random.nextInt(3);
		switch (var){
		case 0:
			gen.transformBlock(pos, Modifier.COBBLE);
			break;
		case 1:
			gen.transformBlock(pos, Modifier.MOSSY);
			break;
		case 2:
			gen.transformBlock(pos, Modifier.COBBLE);
			gen.transformBlock(pos, Modifier.MOSSY);
			break;
		}

	}

	@Override
	public void generateFloorAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}


}
