package wtf.api;


import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import wtf.init.BlockSets;
import wtf.utilities.GenMethods;
import wtf.worldgen.caves.CaveBiomeGenMethods;


public abstract class Replacer {

	public Replacer(Block block){
		BlockSets.isNonSolidAndCheckReplacement.put(block,  this);
	}
	
	public abstract boolean isNonSolidAndReplacement(Chunk chunk, BlockPos pos, CaveBiomeGenMethods gen, IBlockState oldState);
	


	protected boolean setBlock(World world, BlockPos pos, IBlockState state){
		return GenMethods.setBlockState(world, pos, state);
		
	}

}
