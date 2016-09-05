package wtf.blocks;

import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wtf.core.utilities.wrappers.StoneAndOre;
import wtf.init.BlockSets;

public class BlockDenseOreFalling extends AbstractBlockDerivativeFalling {
	public static final PropertyInteger DENSITY = PropertyInteger.create("density", 0, 2);
	
	public BlockDenseOreFalling(IBlockState backState, IBlockState foreState) {
		super(backState, foreState);
		this.setDefaultState(this.blockState.getBaseState().withProperty(DENSITY, 0));
		BlockSets.stoneAndOre.put(new StoneAndOre(backState, foreState), this.getDefaultState());
		BlockSets.oreAndFractures.add(this);
		this.disableStats();
	}
	

	
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
    
    @Override
	public IBlockState getStateFromMeta(int meta) {
    	return this.blockState.getBaseState().withProperty(DENSITY, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(DENSITY);
	}
    
	
    @Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
    	this.onBlockHarvested(world, pos, state, player);
        if(state.getValue(DENSITY) < 2){
        	state = state.withProperty(DENSITY, state.getValue(DENSITY)+1);
        }
        else {
        	
        	state = Blocks.AIR.getDefaultState();
        }
        player.addStat(StatList.getBlockStats(this.parentForeground.getBlock()));
        return world.setBlockState(pos, state, world.isRemote ? 11 : 3);
    }
    
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, DENSITY);
	}
    
}
