package wtf.blocks.substitution;

import java.util.HashSet;
import java.util.Random;

import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CustomOldLeaves extends BlockOldLeaf{

	public CustomOldLeaves(){
		 this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockPlanks.EnumType.OAK).withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
    }
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (!worldIn.isRemote)
		{
			if (state.getValue(CHECK_DECAY).booleanValue() && state.getValue(DECAYABLE).booleanValue())
			{
				LeafUpdateHelper.alreadyChecked = new HashSet<BlockPos>();

				if (LeafUpdateHelper.findLog(worldIn, pos, pos))
				{
					worldIn.setBlockState(pos, state.withProperty(CHECK_DECAY, Boolean.valueOf(false)), 4);
				}
				else
				{
					this.dropBlockAsItem(worldIn, pos,state, 0);
					worldIn.setBlockToAir(pos);
				}
			}
		}
	}
	
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, this.getWoodType(meta)).withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
    }



}
