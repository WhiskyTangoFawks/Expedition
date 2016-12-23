package wtf.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wtf.Core;

public abstract class AbstractBlockDerivative extends Block{
	
	public final IBlockState parentBackground;
	protected final IBlockState parentForeground;
	

	/**
	 * This class is used to create blocks whose properties are derivative of other blocks
	 * backState is used to determine the properties of the block
	 * foreState is used to determine a block's harvesting and drops
	 * they can be the same block, for simple blocks, or more different for things like ores, with an ore and a background stone
	 * @param backState
	 * @param foreState
	 */
	public AbstractBlockDerivative(IBlockState backState, IBlockState foreState) {
		super(backState.getMaterial());
		this.parentBackground = backState;
		this.parentForeground = foreState;
		if (this.parentBackground != null){
			this.setCreativeTab(Core.wtfTab);
		}
		
		this.setHarvestLevel(foreState.getBlock().getHarvestTool(foreState), foreState.getBlock().getHarvestLevel(foreState));
	}


    @Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return parentBackground.getBlockHardness(worldIn, pos);
    }
	
    @Override
	public float getExplosionResistance(Entity exploder)
    {
        return parentBackground.getBlock().getExplosionResistance(exploder);
    }
    
    int airHash = Blocks.AIR.hashCode();
    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots) // do not drop items while restoring blockstates, prevents item dupe
        {
            java.util.List<ItemStack> items = parentForeground.getBlock().getDrops(worldIn, pos, this.parentForeground, fortune);
            chance = net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, this.parentForeground, fortune, chance, false, harvesters.get());

            if (worldIn.getBlockState(pos).getBlock().hashCode() != airHash){
            	if (worldIn.getBlockState(pos.up()).getBlock().hashCode() == airHash){ pos = pos.up();}
            	else if (worldIn.getBlockState(pos.down()).getBlock().hashCode() == airHash){ pos = pos.down();}
            	else if (worldIn.getBlockState(pos.north()).getBlock().hashCode() == airHash){ pos = pos.north();}
            	else if (worldIn.getBlockState(pos.south()).getBlock().hashCode() == airHash){ pos = pos.south();}
            	else if (worldIn.getBlockState(pos.east()).getBlock().hashCode() == airHash){ pos = pos.east();}
            	else if (worldIn.getBlockState(pos.west()).getBlock().hashCode() == airHash){ pos = pos.west();}         	
            }
            
            for (ItemStack item : items)
            {
                if (worldIn.rand.nextFloat() <= chance)
                {
                    spawnAsEntity(worldIn, pos, item);
                }
            }
        }
    }

    
    @Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return parentBackground.getBlock().getBlockLayer();
    }
    
    @Override
	public SoundType getSoundType()
    {
        return parentBackground.getBlock().getSoundType();
    }
    
    
    @Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return parentBackground.getBlock().getFlammability(world, pos, face);
    }
       
}
