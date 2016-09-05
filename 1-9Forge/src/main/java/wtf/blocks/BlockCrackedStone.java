package wtf.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wtf.core.utilities.wrappers.StateAndModifier;
import wtf.init.BlockSets;
import wtf.init.WTFBlocks;
import wtf.init.BlockSets.Modifier;

public class BlockCrackedStone extends AbstractBlockDerivative{

	public BlockCrackedStone(IBlockState stone) {
		super(stone, stone);
		BlockSets.blockTransformer.put(new StateAndModifier(getDefaultState(), Modifier.COBBLE), Blocks.COBBLESTONE.getDefaultState());
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
	{
		//on neighbor change, fracture this block
		//add a mechanism to block fracturing, which if it detects an instance of this block has been fractured, fracture all adjacent blocks
		
		
	}
	
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
    
	
}
