package wtf.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wtf.Core;

public class BlockPuddle extends Block{
	protected static final AxisAlignedBB CARPET_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);
	public static SoundType puddle = new SoundType(1.0F, 1.0F, SoundEvents.ENTITY_GENERIC_SPLASH, SoundEvents.BLOCK_SLIME_STEP, SoundEvents.ENTITY_GENERIC_SPLASH, SoundEvents.ENTITY_PLAYER_SPLASH, SoundEvents.ENTITY_GENERIC_SPLASH);;
	
	public BlockPuddle()
	{
		super(Material.SPONGE);
		this.setSoundType(puddle);
		this.setTickRandomly(false);
		//this.setLightOpacity(0);
		this.setHardness(0.01F);
		this.setCreativeTab(Core.wtfTab);
		//BlockSets.blockTransformer.put(new StateAndModifier(Blocks.AIR.getDefaultState(), Modifier.FROZEN), this.getDefaultState());
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return CARPET_AABB;
	}

    @Override
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
		/*
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
		 */
		return true;
	}

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return blockAccess.getBlockState(pos.offset(side)).getMaterial() == this.blockMaterial ? false : (side == EnumFacing.UP ? true : super.shouldSideBeRendered(blockState, blockAccess, pos, side));
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}
	
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return true;
    }
    
    @Deprecated
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
    
    public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
        return false;
    }
    
	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}
	
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return null;
    }

    
    public Vec3d modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3d motion)
    {
        return motion.addVector(-0.1, -0.1, -0.1);
    }
    
    
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
    {
    	SoundEvent soundevent = SoundEvents.ENTITY_PLAYER_SPLASH;
        worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), soundevent, SoundCategory.AMBIENT, 0.15F, 1.0F, true);
    }

	
}
