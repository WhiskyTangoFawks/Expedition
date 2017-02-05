package wtf.worldgen.subcaves.mob;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.utilities.wrappers.AdjPos;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class DungeonBlaze extends DungeonAbstractSimple{

	public DungeonBlaze(String name) {
		super(name);
	}
	
	@Override
	public void generateCenter(CaveBiomeGenMethods gen, Random rand, CavePosition pos, float depth) {
		gen.spawnVanillaSpawner(pos.getFloorPos().up(), "Blaze", 2);	
	}
	
	IBlockState block = null;
	
	@Override
	public boolean canGenerateAt(CaveBiomeGenMethods gen, CaveListWrapper cave) {
		block = Blocks.NETHER_BRICK.getDefaultState();
		return true;
	}

	public void generate(CaveBiomeGenMethods gen, Random random, BlockPos pos) {
			gen.replaceBlock(pos, block);
	}

	@Override
	public void generateAdjacentWall(CaveBiomeGenMethods gen, Random random, AdjPos pos, float depth, int height){
		int x = pos.getX()%10;
		int z = pos.getZ()%10;
		if (x == 1 || x == 5 || x == 8 ||z == 1 || z == 5 ||z == 8){
			gen.replaceBlock(pos.down(), Blocks.NETHER_BRICK_FENCE.getDefaultState());
		}
		
	}
	
}
