package wtf.blocks;


import javax.annotation.Nullable;

import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.init.BlockSets;
import wtf.utilities.wrappers.StoneAndOre;

public class BlockDenseOre extends AbstractBlockDerivative{

	public static final PropertyInteger DENSITY = PropertyInteger.create("density", 0, 2);
	
	public BlockDenseOre(IBlockState backState, IBlockState foreState) {
		super(backState, foreState);
		this.setDefaultState(this.blockState.getBaseState().withProperty(DENSITY, 0));
		BlockSets.stoneAndOre.put(new StoneAndOre(backState, foreState), this.getDefaultState());
		BlockSets.oreAndFractures.add(this);
		BlockSets.surfaceBlocks.add(this);
		this.disableStats();
		
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
        	this.parentBackground.getBlock().dropBlockAsItem(world, pos, this.parentBackground, 0);
        	state = Blocks.AIR.getDefaultState();
        }
        player.addStat(StatList.getBlockStats(this.parentForeground.getBlock()));
        return world.setBlockState(pos, state, world.isRemote ? 11 : 3);
    }
    
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, DENSITY);
	}
	
    @Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        return this.parentForeground.getBlock().canSilkHarvest(world, pos, state, player);
    }
    
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack)
    {
        player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.025F);

        if (this.canSilkHarvest(worldIn, pos, state, player) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0)
        {
            java.util.List<ItemStack> items = new java.util.ArrayList<ItemStack>();
            int meta = this.parentForeground.getBlock().getMetaFromState(this.parentForeground);
            ItemStack itemstack = new ItemStack(this.parentForeground.getBlock(),1, meta);

            if (itemstack != null)
            {
                items.add(itemstack);
            }

            net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
            for (ItemStack item : items)
            {
                spawnAsEntity(worldIn, pos, item);
            }
        }
        else
        {
            harvesters.set(player);
            int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
            this.dropBlockAsItem(worldIn, pos, state, i);
            harvesters.set(null);
        }
    }
    
}
