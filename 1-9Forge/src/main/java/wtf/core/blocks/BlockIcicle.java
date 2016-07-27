package wtf.core.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wtf.core.init.BlockSets;


public class BlockIcicle extends AbstractBlockDerivative{

	public static final IProperty<IcicleType> TYPE = PropertyEnum.create("type", IcicleType.class);
	
	public BlockIcicle(IBlockState state) {
		super(state, state);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn)
    {
    	if(!canBlockStay(world, pos)){
			world.destroyBlock(pos, true);

		}

    }
	/*
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		//The block hasn't actually been placed yet, so this method is completely unaware of the state of the block
		//If I want to add player placeable stalactites with logic, it is going to have to be through an item
		return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos);
	}
	*/
	

    @Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
    	if(!canBlockStay(world, pos)){
    		world.destroyBlock(pos, true);
		}
    }
	
	public boolean canBlockStay(World world, BlockPos pos)
	{
		for (int loopx = -1; loopx < 2; loopx++){
			for (int loopy = -2; loopy < 1; loopy++){
				for (int loopz = -1; loopz < 2; loopz++){
					BlockPos relpos = new BlockPos(pos.getX()+loopx, pos.getY()+loopy, pos.getZ()+loopz);
					if (BlockSets.meltBlocks.contains(world.getBlockState(relpos).getBlock())){
						return false;
					}
				}
			}	
		}
		
		switch (world.getBlockState(pos).getValue(TYPE)){
		
		case icicle_base:
			return (world.getBlockState(pos.up()).isBlockNormalCube());
		case icicle_small:
			return (world.getBlockState(pos.up()).isBlockNormalCube());
		case icicle_tip:
			return (world.getBlockState(pos.up()) == this.getDefaultState().withProperty(TYPE, IcicleType.icicle_base));
		default :
			return false;
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE);
	}


	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, IcicleType.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		IcicleType type = state.getValue(TYPE);
		return type.getID();
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		for (int loop = 0; loop < IcicleType.values().length; loop++){
			list.add(new ItemStack(itemIn, 1, loop));
		}
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
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return state.getValue(TYPE).boundingBox;
	}
    
	@Override
	@Deprecated
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return null;
    }
	
	
	public enum IcicleType implements IStringSerializable {
		icicle_small(0, "stalactite_small", new AxisAlignedBB(0.2F, 0.2F, 0.2F, 0.8F, 1F, 0.8F)),
		icicle_base(1, "stalactite_base", new AxisAlignedBB(0.2F, 0F, 0.2F, .8F, 1F, .8F)),
		icicle_tip(2, "stalactite_tip", new AxisAlignedBB(0.3F, 0.4F, 0.3F, 0.7F, 1F, 0.7F));
		
		private final int ID;
		private final String name;
		public final AxisAlignedBB boundingBox;

		private IcicleType(int ID, String name, AxisAlignedBB box) {
			this.ID = ID;
			this.name = name;
			this.boundingBox = box;
		}

		@Override
		public String getName() {
			return name;
		}

		public int getID() {
			return ID;
		}
		@Override
		public String toString() {
			return getName();
		}
		
	}
	
	public IBlockState getBlockState(IcicleType type){
		return this.getDefaultState().withProperty(TYPE, type);
	}
}
