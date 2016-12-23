package wtf.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSpeleothemFrozen extends BlockSpeleothem{

	Block parentBlock;

	public BlockSpeleothemFrozen(BlockSpeleothem block) {
		super(Blocks.ICE.getDefaultState(), block.getDefaultState());
		parentBlock = block;
		this.setSoundType(SoundType.GLASS);
		this.setHarvestLevel(Blocks.ICE.getDefaultState().getBlock().getHarvestTool(Blocks.ICE.getDefaultState()), Blocks.ICE.getDefaultState().getBlock().getHarvestLevel(Blocks.ICE.getDefaultState()));
	}


	@Override
	@Deprecated
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{

		switch (side){
		case DOWN:
			return isFrozen(blockAccess.getBlockState(pos.down()));
		case EAST:
			return isFrozen(blockAccess.getBlockState(pos.east()));
		case NORTH:
			return isFrozen(blockAccess.getBlockState(pos.north()));
		case SOUTH:
			return isFrozen(blockAccess.getBlockState(pos.south()));
		case UP:
			return isFrozen(blockAccess.getBlockState(pos.up()));
		case WEST:
			return isFrozen(blockAccess.getBlockState(pos.west()));
		}
		return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	private Boolean isFrozen(IBlockState state){
		if (state.getBlock() instanceof BlockSpeleothemFrozen || state.getBlock() instanceof BlockIce){
			return false;
		}
		return true;

	}

	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
	{
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return true;
	}

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
	{
		worldIn.setBlockState(pos, this.parentBlock.getDefaultState().withProperty(TYPE, state.getValue(TYPE)));
	}


	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return FULL_BLOCK_AABB;
	}


	@Override
	@Deprecated
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return blockState.getBoundingBox(worldIn, pos);
	}

	@Override
	public boolean canBlockStay(World world, BlockPos pos)
	{
		/*
			for (int loopx = -1; loopx < 2; loopx++){
				for (int loopy = -2; loopy < 1; loopy++){
					for (int loopz = -1; loopz < 2; loopz++){
						BlockPos relpos = new BlockPos(pos.getX()+loopx, pos.getY()+loopy, pos.getZ()+loopz);
						if (BlockSets.meltBlocks.contains(world.getBlockState(relpos).getBlock())){
							int meta = getMetaFromState(world.getBlockState(pos));
							IBlockState newState = this.parentForeground.getBlock().getStateFromMeta(meta);

							world.setBlockState(pos, newState);
						}
					}
				}	
			}
		 */
		return true;
	}

	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
    {
        switch (layer){
		case CUTOUT:
			return false;
		case CUTOUT_MIPPED:
			return false;
		case SOLID:
			return true;
		case TRANSLUCENT:
			return true;
		default:
			break;
        
        }
        return false;
    }

}
