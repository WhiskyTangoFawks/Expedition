package wtf.worldgen.generators.queuedgen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import wtf.worldgen.generators.TreeGenerator;

public class QTreeReplace implements QueuedGenerator{

	private final IBlockState state;
	private final BlockPos pos;
	
	public QTreeReplace(BlockPos pos, IBlockState state){
		this.state = state;
		this.pos = pos;
	}
	
	@Override
	public IBlockState getBlockState(IBlockState oldstate) {
		TreeGenerator.removeTreeMapPos(pos);
		return state;
	}
}
