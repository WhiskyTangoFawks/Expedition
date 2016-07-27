package wtf.core.blocks;


import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wtf.core.Core;
import wtf.core.config.GameplayConfig;

public class BlockLitTorch extends BlockTorch
{


	public static final PropertyBool ISLIT = PropertyBool.create("islit");

	public BlockLitTorch(){
		this.setCreativeTab(Core.wtfTab);
		this.lightValue = 14;
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP).withProperty(ISLIT, true));
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
		if (state.getValue(ISLIT) && GameplayConfig.enableFiniteTorch > 0 && rand.nextInt(GameplayConfig.torchLifespan) == 0){
			if (!world.isAnyPlayerWithinRangeAt(pos.getX(), pos.getY(), pos.getZ(), 32)){
				world.setBlockState(pos, state.withProperty(ISLIT, false));
			}
		}
	}


	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!state.getValue(ISLIT)){
			if(GameplayConfig.enableFiniteTorch ==1) {
				return world.setBlockState(pos, state.withProperty(ISLIT, true));
			}
			else if (GameplayConfig.enableFiniteTorch ==2 && heldItem.getItem() == Items.FLINT_AND_STEEL){
				return world.setBlockState(pos, state.withProperty(ISLIT, true));
			}			
		}
		else {
			return world.setBlockState(pos, state.withProperty(ISLIT, false));
		}
		return false;

	}

	@Override
	@Deprecated
	public int getLightValue(IBlockState state)
	{
		if (state.getValue(ISLIT)){
			return this.lightValue;
		}
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random rand)
	{
		if (state.getValue(ISLIT)){
			EnumFacing enumfacing = state.getValue(FACING);
			double d0 = pos.getX() + 0.5D;
			double d1 = pos.getY() + 0.7D;
			double d2 = pos.getZ() + 0.5D;
			double d3 = 0.22D;
			double d4 = 0.27D;

			if (enumfacing.getAxis().isHorizontal())
			{
				EnumFacing enumfacing1 = enumfacing.getOpposite();
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4 * enumfacing1.getFrontOffsetX(), d1 + d3, d2 + d4 * enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
				worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4 * enumfacing1.getFrontOffsetX(), d1 + d3, d2 + d4 * enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
			}
			else
			{
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
				worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
	}



	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState iblockstate = this.getDefaultState();

		switch (meta)
		{
		case 1:
			iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST).withProperty(ISLIT, true);
			break;
		case 2:
			iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST).withProperty(ISLIT, true);
			break;
		case 3:
			iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH).withProperty(ISLIT, true);
			break;
		case 4:
			iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH).withProperty(ISLIT, true);
			break;
		case 5:
			iblockstate = iblockstate.withProperty(FACING, EnumFacing.UP).withProperty(ISLIT, true);
			break;
		case 6:
			iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST).withProperty(ISLIT, false);
			break;
		case 7:
			iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST).withProperty(ISLIT, false);
			break;
		case 8:
			iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH).withProperty(ISLIT, false);
			break;
		case 9:
			iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH).withProperty(ISLIT, false);
			break;
		case 10:
			iblockstate = iblockstate.withProperty(FACING, EnumFacing.UP).withProperty(ISLIT, false);
		}

		return iblockstate;
	}


	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;

		switch (state.getValue(FACING))
		{
		case EAST:
			i = i | 1;
			break;
		case WEST:
			i = i | 2;
			break;
		case SOUTH:
			i = i | 3;
			break;
		case NORTH:
			i = i | 4;
			break;
		case DOWN:
		case UP:
		default:
			i = i | 5;
		}

		if (!state.getValue(ISLIT)){
			i+=5;
		}
		return i;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {FACING, ISLIT});
	}

}
