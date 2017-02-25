package wtf.worldgen.generators.queuedgen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public class QModifyAdjacent implements QueuedGenerator {

	IBlockState state;
	BlockPos pos;
	
	
	public QModifyAdjacent(BlockPos parent, BlockPos pos, IBlockState state){
		
	}
	
	@Override
	public IBlockState getBlockState(IBlockState oldstate) {
		// TODO Auto-generated method stub
		return null;
	}

	//Do the getblock call in the genmethods
	//This accepts a parent position, a block position, and a block
	//it checks to see if the parent position has a queue
	//if it doesn't it sets the block based on the parent block (which we got earlier)
	
	
	
}
