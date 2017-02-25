package wtf.worldgen.generators.queuedgen;

import net.minecraft.block.state.IBlockState;
import wtf.init.BlockSets;
import wtf.init.BlockSets.Modifier;

public class QModify implements QueuedGenerator {

	private final Modifier modifier;
	
	public QModify(Modifier modifier){
		this.modifier = modifier;
	}

	@Override
	public IBlockState getBlockState(IBlockState oldstate) {
		return BlockSets.getTransformedState(oldstate, modifier);
	}
	
}
