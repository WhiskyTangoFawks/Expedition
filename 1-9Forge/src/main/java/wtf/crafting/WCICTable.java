package wtf.crafting;

import javax.annotation.Nullable;

import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.core.Core;

public class WCICTable extends BlockEnchantmentTable{




	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote){
			playerIn.openGui(Core.instance, 0, worldIn, 0, 0, 0);
		}
		
		return true;
	}

  
	
}
