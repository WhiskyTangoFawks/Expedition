package wtf.cavebiomes.worldgeneration.cavetypes.caves;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.cavebiomes.worldgeneration.AbstractCaveType;
import wtf.cavebiomes.worldgeneration.CaveBiomeGenMethods;
import wtf.core.init.BlockSets;
import wtf.core.init.BlockSets.Modifier;

public class CaveTypeMossy extends AbstractCaveType{

	private final IBlockState dirt;

	public CaveTypeMossy(String name, int ceilingAddonPercentChance, int floorAddonPercentChance, IBlockState block) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		dirt = block;
	}

	@Override
	public void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub

	}

	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {

		if (getNoise(pos, 5, 0.2F) < (depth*2)) //dirt
		{
			gen.replaceBlock(pos, dirt);
		}	
		if (getNoise(pos, 5, 2F) < depth){
			gen.transformBlock(pos, Modifier.MOSSY);
		}
	}

	@Override
	public void generateCeilingAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		gen.genStalactite(pos, depth, false);

	}

	@Override
	public void generateFloorAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		if (random.nextBoolean()){
			gen.genStalagmite(pos, depth, false);
		}
		else {
			gen.setFloorAddon(pos, Modifier.COBBLE);
			gen.transformBlock(pos.up(), Modifier.MOSSY);
		}

	}

	@Override
	public void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height) {

		if (getNoise(pos, 5, 0.33F) > height/2 ){ //dirt
			gen.replaceBlock(pos, Blocks.DIRT.getDefaultState());
			if (getNoise(pos, 2, 2F) < depth){
				gen.transformBlock(pos, BlockSets.Modifier.MOSSY);
			}
		}
		
		else if(getNoise(pos, 5, 1F) < depth){ //stone
			gen.transformBlock(pos, Modifier.MOSSY);
		}
		
	}
}




