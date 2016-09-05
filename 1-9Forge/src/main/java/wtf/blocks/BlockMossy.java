package wtf.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wtf.init.BlockSets;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.StateAndModifier;

public class BlockMossy extends AbstractBlockDerivative{

	public BlockMossy(IBlockState state) {
		super(state, state);
		
		BlockSets.blockTransformer.put(new StateAndModifier(state, BlockSets.Modifier.MOSSY), this.getDefaultState());
		
		//BlockSets.blockMiningSpeed
		if (BlockSets.fallingBlocks.containsKey(state.getBlock())){
			BlockSets.fallingBlocks.put(this, BlockSets.fallingBlocks.get(state.getBlock()));
		}
		if (state.getBlock() == Blocks.STONE){
			BlockSets.blockTransformer.put(new StateAndModifier(this.getDefaultState(), Modifier.COBBLE), Blocks.MOSSY_COBBLESTONE.getDefaultState());
		}
		
	}
	
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
    

	
}
