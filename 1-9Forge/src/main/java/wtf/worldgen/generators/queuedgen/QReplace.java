package wtf.worldgen.generators.queuedgen;

import net.minecraft.block.state.IBlockState;

public class QReplace implements QueuedGenerator{

	private final IBlockState state;
	
	public QReplace(IBlockState state){
		this.state = state;
	}
	
	@Override
	public IBlockState getBlockState(IBlockState oldstate) {
		// TODO Auto-generated method stub
		return state;
	}

}
