package wtf.blocks.redstone;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wtf.blocks.BlockDenseOre;
import wtf.init.BlockSets;

public class DenseRedstoneOre extends BlockDenseOre{
	
	private final boolean active;
	
	public static Block denseRedstone_on;
	public static Block denseRedstone_off;
	
	public DenseRedstoneOre(boolean isOn) {
		super(Blocks.STONE.getDefaultState(), Blocks.REDSTONE_ORE.getDefaultState());
		active = isOn;
		if (isOn){
			this.setTickRandomly(true);
			this.setLightLevel(0.625F);
			BlockSets.explosiveBlocks.put(this, 2F);
		}
		else {
			BlockSets.explosiveBlocks.put(this, 3F);
		}
	}
	
	
	 public int tickRate(World worldIn)
	    {
	        return 30;
	    }

	    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
	    {
	        this.activate(worldIn, pos);
	        super.onBlockClicked(worldIn, pos, playerIn);
	    }

	    /**
	     * Triggered whenever an entity collides with this block (enters into the block)
	     */
	    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
	    {
	        this.activate(worldIn, pos);
	        super.onEntityWalk(worldIn, pos, entityIn);
	    }

	    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	    {
	        this.activate(worldIn, pos);
	        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
	    }

	    private void activate(World worldIn, BlockPos pos)
	    {
	        this.spawnParticles(worldIn, pos);

	        if (active)
	        {
	            worldIn.setBlockState(pos,  getToggled(worldIn.getBlockState(pos)));
	        }
	    }

	    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	    {
	        if (active)
	        {
	            worldIn.setBlockState(pos, getToggled(state));
	        }
	    }

	    @SideOnly(Side.CLIENT)
	    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	    {
	        if (this.active)
	        {
	            this.spawnParticles(worldIn, pos);
	        }
	    }

	    private void spawnParticles(World worldIn, BlockPos pos)
	    {
	        Random random = worldIn.rand;
	      //  double d0 = 0.0625D;

	        for (int i = 0; i < 6; ++i)
	        {
	            double d1 = (double)((float)pos.getX() + random.nextFloat());
	            double d2 = (double)((float)pos.getY() + random.nextFloat());
	            double d3 = (double)((float)pos.getZ() + random.nextFloat());

	            if (i == 0 && !worldIn.getBlockState(pos.up()).isOpaqueCube())
	            {
	                d2 = (double)pos.getY() + 0.0625D + 1.0D;
	            }

	            if (i == 1 && !worldIn.getBlockState(pos.down()).isOpaqueCube())
	            {
	                d2 = (double)pos.getY() - 0.0625D;
	            }

	            if (i == 2 && !worldIn.getBlockState(pos.south()).isOpaqueCube())
	            {
	                d3 = (double)pos.getZ() + 0.0625D + 1.0D;
	            }

	            if (i == 3 && !worldIn.getBlockState(pos.north()).isOpaqueCube())
	            {
	                d3 = (double)pos.getZ() - 0.0625D;
	            }

	            if (i == 4 && !worldIn.getBlockState(pos.east()).isOpaqueCube())
	            {
	                d1 = (double)pos.getX() + 0.0625D + 1.0D;
	            }

	            if (i == 5 && !worldIn.getBlockState(pos.west()).isOpaqueCube())
	            {
	                d1 = (double)pos.getX() - 0.0625D;
	            }

	            if (d1 < (double)pos.getX() || d1 > (double)(pos.getX() + 1) || d2 < 0.0D || d2 > (double)(pos.getY() + 1) || d3 < (double)pos.getZ() || d3 > (double)(pos.getZ() + 1))
	            {
	                worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d1, d2, d3, 0.0D, 0.0D, 0.0D, new int[0]);
	            }
	        }
	    }
	    
	    private IBlockState getToggled(IBlockState state){
	    	int density = state.getValue(DENSITY);
	    	return state.getBlock().hashCode() == this.denseRedstone_off.hashCode() ? denseRedstone_off.getDefaultState().withProperty(DENSITY, density) :
	    		denseRedstone_on.getDefaultState().withProperty(DENSITY, density);
	    		
	    }

	
}
