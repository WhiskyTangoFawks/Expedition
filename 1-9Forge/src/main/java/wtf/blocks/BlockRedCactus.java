package wtf.blocks;

import net.minecraft.block.BlockCactus;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.Core;


public class BlockRedCactus extends BlockCactus{

	public BlockRedCactus(){
		 super();
		 this.setCreativeTab(Core.wtfTab);
		 this.setHardness(0.4F);
		 this.setSoundType(SoundType.CLOTH);
	      
	}
	
    public boolean canBlockStay(World worldIn, BlockPos pos)
    {
    	return worldIn.getBlockState(pos.down()).getMaterial() == Material.SAND;
    }

	
}
