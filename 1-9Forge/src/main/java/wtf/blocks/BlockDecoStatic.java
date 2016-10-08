package wtf.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import wtf.init.BlockSets;
import wtf.utilities.wrappers.StateAndModifier;

public class BlockDecoStatic extends AbstractBlockDerivative{

	public BlockDecoStatic(IBlockState state) {
		super(state, state);
		
		BlockSets.blockTransformer.put(new StateAndModifier(state, BlockSets.Modifier.MOSSY), this.getDefaultState());
		
		//BlockSets.blockMiningSpeed
		if (BlockSets.fallingBlocks.containsKey(state.getBlock())){
			BlockSets.fallingBlocks.put(this, BlockSets.fallingBlocks.get(state.getBlock()));
		}		
	}
	
	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
    {
        switch (layer){
		case CUTOUT:
			return false;
		case CUTOUT_MIPPED:
			return false;
		case SOLID:
			return true;
		case TRANSLUCENT:
			return true;
		default:
			break;
        
        }
        return false;
    }


	
}
