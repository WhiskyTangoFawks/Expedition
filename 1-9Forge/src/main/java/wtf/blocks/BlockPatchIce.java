package wtf.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wtf.Core;
import wtf.init.BlockSets;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.StateAndModifier;

public class BlockPatchIce extends BlockBreakable{

	protected static final AxisAlignedBB CARPET_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);

	public BlockPatchIce()
	{
		super(Material.ICE, false);
		this.slipperiness = 0.98F;

		this.setSoundType(SoundType.GLASS);
		this.setTickRandomly(false);
		//this.setLightOpacity(0);
		this.setCreativeTab(Core.wtfTab);
		BlockSets.blockTransformer.put(new StateAndModifier(Blocks.AIR.getDefaultState(), Modifier.FROZEN), this.getDefaultState());
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return CARPET_AABB;
	}

    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
    {
    	return true;
    }

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}


	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

    @Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn)
    {
    	if(!canBlockStay(world, pos)){
			world.setBlockToAir(pos);
		}

    }
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if(!canBlockStay(world, pos)){
			world.setBlockToAir(pos);
		}
	}
	
	

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos);
	}

	public boolean canBlockStay(World world, BlockPos pos)
	{
		if (!world.getBlockState(pos.down()).isBlockNormalCube()){return false;}
		for (int loopx = -1; loopx < 2; loopx++){
			for (int loopy = 0; loopy < 2; loopy++){
				for (int loopz = -1; loopz < 2; loopz++){
					BlockPos relpos = new BlockPos(pos.getX()+loopx, pos.getY()+loopy, pos.getZ()+loopz);
					if (BlockSets.meltBlocks.contains(world.getBlockState(relpos).getBlock())){
						return false;
					}
				}
			}	
		}

		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}











}
