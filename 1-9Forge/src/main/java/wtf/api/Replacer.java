package wtf.api;


//import exterminatorJeff.undergroundBiomes.api.BlockCodes;
//import exterminatorJeff.undergroundBiomes.api.UBAPIHook;
//import exterminatorJeff.undergroundBiomes.api.UBStrataColumn;
//import exterminatorJeff.undergroundBiomes.api.UBStrataColumnProvider;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import wtf.init.BlockSets;
import wtf.utilities.GenMethods;


public abstract class Replacer {

	public Replacer(Block block){
		BlockSets.isNonSolidAndCheckReplacement.put(block,  this);
	}
	
	public abstract boolean isNonSolidAndReplacement(Chunk chunk, BlockPos pos, IBlockState oldState);
	

	/*
	protected static BlockInfo getUBCStone(World world, int x, int y, int z){
		UBStrataColumnProvider columnProvider = UBAPIHook.ubAPIHook.dimensionalStrataColumnProvider.ubStrataColumnProvider(0);
		UBStrataColumn column = columnProvider.strataColumn(x, z);
		BlockCodes stoneCode = column.stone(y);
		return new BlockInfo(stoneCode.block, stoneCode.metadata);
	}
	*/

	
	protected boolean setBlock(World world, BlockPos pos, IBlockState state){
		return GenMethods.setBlockState(world, pos, state);
		
	}

}
