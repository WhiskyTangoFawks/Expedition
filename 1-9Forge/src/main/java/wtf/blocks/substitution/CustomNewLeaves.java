package wtf.blocks.substitution;

import java.util.HashSet;
import java.util.Random;

import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CustomNewLeaves extends BlockNewLeaf{

	public CustomNewLeaves(){
		this.setDefaultState(this.getStateFromMeta(0));
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


}
