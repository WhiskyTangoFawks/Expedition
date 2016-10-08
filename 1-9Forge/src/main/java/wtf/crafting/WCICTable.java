package wtf.crafting;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wtf.Core;
import wtf.crafting.render.WCICTileEntity;

public class WCICTable extends BlockContainer{

	 protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);

	public WCICTable() {
		super(Material.WOOD);
		this.setHarvestLevel(null, 0);
			
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote){
			playerIn.openGui(Core.instance, 0, worldIn, 0, 0, 0);
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new WCICTileEntity();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{

	}



	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{

	}
    
	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return Blocks.CRAFTING_TABLE.getDefaultState().getBlockHardness(worldIn, pos);
    }
	 @Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	    {
	        return AABB;
	    }

	    @Override
		public boolean isFullCube(IBlockState state)
	    {
	        return false;
	    }

	

	    /**
	     * Used to determine ambient occlusion and culling when rebuilding chunks for render
	     */
	    @Override
		public boolean isOpaqueCube(IBlockState state)
	    {
	        return false;
	    }

	    /**
	     * The type of render function called. 3 for standard block models, 2 for TESR's, 1 for liquids, -1 is no render
	     */
	    @Override
		public EnumBlockRenderType getRenderType(IBlockState state)
	    {
	        return EnumBlockRenderType.MODEL;
	    }

	
}
