package wtf.worldgen.generators.queuedgen;

import net.minecraft.block.state.IBlockState;

public interface QueuedGenerator {

	public IBlockState getBlockState(IBlockState oldstate);
	
}
