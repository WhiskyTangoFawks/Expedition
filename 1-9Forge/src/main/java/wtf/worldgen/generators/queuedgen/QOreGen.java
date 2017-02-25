package wtf.worldgen.generators.queuedgen;

import net.minecraft.block.state.IBlockState;
import wtf.blocks.BlockDenseOre;
import wtf.blocks.BlockDenseOreFalling;
import wtf.init.BlockSets;
import wtf.utilities.wrappers.StoneAndOre;

public class QOreGen implements QueuedGenerator{

	private final IBlockState ore;
	private final int density;

	public QOreGen(IBlockState state, int density){
		this.ore = state;
		this.density = density;
	}

	@Override
	public IBlockState getBlockState(IBlockState oldstate) {
		IBlockState newstate = BlockSets.stoneAndOre.get(new StoneAndOre(oldstate, ore));
		if (newstate != null){
			if (newstate.getBlock() instanceof BlockDenseOre){
				newstate = newstate.withProperty(BlockDenseOre.DENSITY, density);
			}
			else if (newstate.getBlock() instanceof BlockDenseOreFalling){
				newstate = newstate.withProperty(BlockDenseOreFalling.DENSITY, density);
			}
		}
		return newstate;
	}

}
