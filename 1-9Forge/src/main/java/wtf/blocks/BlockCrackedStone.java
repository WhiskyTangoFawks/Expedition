package wtf.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.gameplay.StoneFractureMethods;
import wtf.init.BlockSets;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.StateAndModifier;

public class BlockCrackedStone extends AbstractBlockDerivative{

	public BlockCrackedStone(IBlockState stone) {
		super(stone, stone);
		BlockSets.blockTransformer.put(new StateAndModifier(getDefaultState(), Modifier.COBBLE), Blocks.COBBLESTONE.getDefaultState());
		BlockSets.oreAndFractures.add(this);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
	{
		//on neighbor change, fracture this block
		//add a mechanism to block fracturing, which if it detects an instance of this block has been fractured, fracture all adjacent blocks
		
		
	}
	
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
    }
	
    @Override
	public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state)
    {
    	StoneFractureMethods.frac(world, pos, 1);
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
