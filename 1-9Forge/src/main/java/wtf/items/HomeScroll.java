package wtf.items;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.Core;


public class HomeScroll extends Item{
	
	public HomeScroll(){
		this.setCreativeTab(Core.wtfTab);
	}
	Random random = new Random();
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
	//public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float px, float py, float pz){
		BlockPos home = player.getBedLocation(world.provider.getDimension());
		if (home != null){
			--stack.stackSize;
			if (!world.isRemote)
			{

				for (int i = 0; i < 32; ++i)
			        {
			            world.spawnParticle(EnumParticleTypes.PORTAL, home.getX(), home.getY() + random.nextDouble() * 2.0D, home.getZ(), random.nextGaussian(), 0.0D, random.nextGaussian());
			        }
				player.setPositionAndUpdate( home.getX(), home.getY(), home.getZ());

			}
		}
		return EnumActionResult.SUCCESS;
	}

}
