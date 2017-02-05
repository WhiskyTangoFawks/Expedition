package wtf.worldgen.subcaves.mob;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.init.BlockSets;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class DungeonSimpleSkeleton extends DungeonAbstractSimple{

	public DungeonSimpleSkeleton(String name) {
		super(name);
	}

	IBlockState block = null;
	
	@Override
	public boolean canGenerateAt(CaveBiomeGenMethods gen, CaveListWrapper cave) {
		block = BlockSets.getTransformedState(gen.getBlockState(cave.centerpos.getFloorPos()), Modifier.BRICK);
		if (block==null){
			block = Blocks.STONEBRICK.getDefaultState();
		}
		
		return true;
	}
	
	@Override
	public void generateCenter(CaveBiomeGenMethods gen, Random rand, CavePosition pos, float depth) {
		gen.spawnVanillaSpawner(pos.getFloorPos().up(), "Skeleton", 5);	
	}

	public void generate(CaveBiomeGenMethods gen, Random random, BlockPos pos) {
			gen.replaceBlock(pos, block);
		
	}

}
