package wtf.core.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPatchFluidStatic extends BlockPatchFluid{

	public BlockPatchFluidStatic(Material material, String name) {
		super(material);
	}


	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
	{

		if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP) || !canBlockStay(worldIn, pos)){
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
		else {
			worldIn.setBlockState(pos, otherState);
		}
		
	}
	
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{

		if (this.blockMaterial == Material.LAVA)
		{
			if (worldIn.getGameRules().getBoolean("doFireTick"))
			{
				int i = rand.nextInt(3);

				if (i > 0)
				{
					BlockPos blockpos = pos;

					for (int j = 0; j < i; ++j)
					{
						blockpos = blockpos.add(rand.nextInt(3) - 1, 1, rand.nextInt(3) - 1);

						if (blockpos.getY() >= 0 && blockpos.getY() < worldIn.getHeight() && !worldIn.isBlockLoaded(blockpos))
						{
							return;
						}

						Block block = worldIn.getBlockState(blockpos).getBlock();

						if (block.getMaterial(state) == Material.AIR)
						{
							if (this.isSurroundingBlockFlammable(worldIn, blockpos))
							{
								worldIn.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
								return;
							}
						}
						else if (block.getMaterial(state).blocksMovement())
						{
							return;
						}
					}
				}
				else
				{
					for (int k = 0; k < 3; ++k)
					{
						BlockPos blockpos1 = pos.add(rand.nextInt(3) - 1, 0, rand.nextInt(3) - 1);

						if (blockpos1.getY() >= 0 && blockpos1.getY() < 256 && !worldIn.isBlockLoaded(blockpos1))
						{
							return;
						}

						if (worldIn.isAirBlock(blockpos1.up()) && this.getCanBlockBurn(worldIn, blockpos1))
						{
							worldIn.setBlockState(blockpos1.up(), Blocks.FIRE.getDefaultState());
						}
					}
				}
			}
		}
	}
	
}
