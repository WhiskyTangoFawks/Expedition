package wtf.blocks.substitution;


import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
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
import wtf.Core;
import wtf.config.GameplayConfig;

public class BlockWTFTorch extends BlockTorch
{

	private final boolean isLit;
	public static Block torch_off;
	public static Block torch_on;
	
	public BlockWTFTorch(boolean lit){
		this.setCreativeTab(Core.wtfTab);
		this.isLit = lit;
		this.lightValue = lit ? 14 : 0;
		
		
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
		if (isLit && rand.nextInt(100) < GameplayConfig.torchLifespan){
			if (!world.isAnyPlayerWithinRangeAt(pos.getX(), pos.getY(), pos.getZ(), GameplayConfig.torchRange)){
				IBlockState newState = torch_off.getStateFromMeta(state.getBlock().getMetaFromState(state));
				world.setBlockState(pos, newState);
			}
		}
	}


	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!isLit){
			if(GameplayConfig.relightTorchByHand) {
				IBlockState newState = torch_on.getStateFromMeta(state.getBlock().getMetaFromState(state));
				return world.setBlockState(pos, newState);
				}
			else if (heldItem != null && heldItem.getItem() == Items.FLINT_AND_STEEL){
				IBlockState newState = torch_on.getStateFromMeta(state.getBlock().getMetaFromState(state));
				return world.setBlockState(pos, newState);
				}			
		}
		return false;

	}



	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random rand)
	{
		if (isLit){
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



	
}
