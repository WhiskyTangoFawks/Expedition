package wtf.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.gameplay.StoneCrack;
import wtf.gameplay.StoneFractureMethods;
import wtf.init.BlockSets;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.StateAndModifier;

public class BlockCrackedStone extends BlockStone{

	public BlockCrackedStone(IBlockState stone) {
		super();
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
    	//How to put a delay on this thing
    	//1) swap over to a tile entity- like how one is used for a delay in explosions
    	//model it off the secondary exploder entity I'm already using- so it's just an entity, not a tile entity
    	

    	//StoneFractureMethods.frac(world, pos, 1);
    	Entity crack = new StoneCrack(world, pos);
		world.spawnEntityInWorld(crack);
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
