package wtf.core.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import wtf.core.Core;
import wtf.core.init.BlockSets;
import wtf.gameplay.StoneFractureMethods;

public class BlockPatchFluid extends BlockLiquid{

	public IBlockState otherState;
	
	public BlockPatchFluid(Material material) {
		super(material);
		this.setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, 7));
		this.setCreativeTab(Core.wtfTab);
		if (material == Material.WATER){
		
		}
		if (material == Material.LAVA){
		
			this.setLightLevel(0.8F);
		}
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState();
	}
   
	@Override
	public Vec3d modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3d motion)
    {
        return motion;//.add(this.getFlow(worldIn, pos, worldIn.getBlockState(pos)));
    }


	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
	{

		if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP) || !canBlockStay(worldIn, pos)){
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
		else {
			if (!this.checkForMixing(worldIn, pos, state))
			{
				this.updateLiquid(worldIn, pos, state);
				worldIn.setBlockState(pos, this.getDefaultState());
			}
		}
		
	}
	
	Block[] waterarray = {Blocks.WATER, Blocks.FLOWING_WATER};
	Block[] lavaarray = {Blocks.FLOWING_LAVA, Blocks.LAVA};
	HashSet <Block> water = new HashSet<Block>(Arrays.asList(waterarray));
	HashSet <Block> lava = new HashSet<Block>(Arrays.asList(lavaarray));
	
	public boolean canBlockStay(World world, BlockPos pos)
	{
		if (BlockSets.nonSolidBlockSet.contains(world.getBlockState(pos.down()))){
			return false;
		}
	
		BlockPos[] sides = {pos.south(), pos.north(), pos.east(), pos.west()};
		for (BlockPos adj : sides){
			IBlockState state = world.getBlockState(adj);
			if (water.contains(state.getBlock()) && state.getValue(BlockLiquid.LEVEL) < 7 || lava.contains(state.getBlock()) && state.getValue(BlockLiquid.LEVEL) < 6){
				if (this.getDefaultState().getMaterial() == Material.LAVA){
					StoneFractureMethods.frac(world, pos.down(), -1);
				}
				return false;
			}
		}
		
		return true;
	}

	@Override
	protected int getDepth(IBlockState p_189542_1_)
	{
		return 7;//p_189542_1_.getMaterial() == this.blockMaterial ? ((Integer)p_189542_1_.getValue(LEVEL)).intValue() : -1;
	}

	@Override
	protected int getRenderedDepth(IBlockState p_189545_1_)
	{
		//System.out.println("render depth called");
		//int i = this.getDepth(p_189545_1_);
		return 7;//i >= 8 ? 0 : i;
	}


	private int airHash = Blocks.AIR.hashCode();
	
	private void updateLiquid(World worldIn, BlockPos pos, IBlockState state)
	{
		
		//BlockDynamicLiquid blockdynamicliquid = getFlowingBlock(this.blockMaterial);
		//worldIn.setBlockState(pos, blockdynamicliquid.getDefaultState().withProperty(LEVEL, state.getValue(LEVEL)), 2);
		//worldIn.scheduleUpdate(pos, blockdynamicliquid, this.tickRate(worldIn));
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		
		boolean foundThis = false;
		ArrayList<BlockPos> airPos = new ArrayList<BlockPos>();
		for (int xloop = -1; xloop < 2; xloop++){
			for (int zloop = -1; zloop < 2; zloop++){
				BlockPos checkPos = new BlockPos(pos.getX()+xloop, pos.getY()-1, pos.getZ()+zloop);
				Block block = worldIn.getBlockState(checkPos).getBlock();
				if (block.hashCode() == airHash && worldIn.getBlockState(checkPos.down()).hashCode() != airHash){
					airPos.add(checkPos);
				}
				if (block.hashCode() == this.hashCode()){
					foundThis = true;
				}
			}	
		}
		
		if (foundThis && airPos.size() > 0){
			
			for (BlockPos setPos : airPos){
				worldIn.setBlockState(setPos, this.getDefaultState());
			}
		}

		worldIn.setBlockState(pos, otherState);
		
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

	protected boolean isSurroundingBlockFlammable(World worldIn, BlockPos pos)
	{
		for (EnumFacing enumfacing : EnumFacing.values())
		{
			if (this.getCanBlockBurn(worldIn, pos.offset(enumfacing)))
			{
				return true;
			}
		}

		return false;
	}

	protected boolean getCanBlockBurn(World worldIn, BlockPos pos)
	{
		return pos.getY() >= 0 && pos.getY() < 256 && !worldIn.isBlockLoaded(pos) ? false : worldIn.getBlockState(pos).getMaterial().getCanBurn();
	}


}
