package wtf.blocks;

import java.util.List;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wtf.init.WTFBlocks;


public class BlockSpeleothem extends AbstractBlockDerivative{

	public static final IProperty<SpType> TYPE = PropertyEnum.create("type", SpType.class);

	public BlockSpeleothem frozen;

	public BlockSpeleothem(IBlockState state) {
		super(state, state);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, SpType.stalactite_small));
		WTFBlocks.speleothemMap.put(state, this);

	}
	protected BlockSpeleothem(IBlockState backState, IBlockState foreState) { //used for frozen speleothems
		super(backState, foreState);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, SpType.stalactite_small));
		//WTFBlocks.speleothemMap.put(state, this); //don't put it in the map

	}

	public Block setFrozen(String string){
		frozen = (BlockSpeleothem) WTFBlocks.registerBlockItemSubblocks(new BlockSpeleothemFrozen(this), 6, string+"Frozen");
		return this;
	}
/*
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn)
	{
		if(!canBlockStay(world, pos)){
			world.destroyBlock(pos, true);
		}
	}
*/


	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if(!canBlockStay(world, pos)){
			world.destroyBlock(pos, true);
		}
	}


	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return state.getValue(TYPE).boundingBox;
	}




	public boolean canBlockStay(World world, BlockPos pos)
	{
		switch (world.getBlockState(pos).getValue(TYPE)){
		case column:
			return (hasProperty(world.getBlockState(pos.down()), SpType.stalagmite_base) ||
					hasProperty(world.getBlockState(pos.down()), SpType.column) ||
					hasProperty(world.getBlockState(pos.up()), SpType.stalactite_base) ||
					hasProperty(world.getBlockState(pos.up()), SpType.column));
		case stalactite_base:
			return (world.getBlockState(pos.up()) == this.parentBackground);
		case stalactite_small:
			return (world.getBlockState(pos.up()) == this.parentBackground);
		case stalactite_tip:
			return (hasProperty(world.getBlockState(pos.up()), SpType.stalactite_base) ||
					hasProperty(world.getBlockState(pos.up()),  SpType.column));
		case stalagmite_base:
			return (world.getBlockState(pos.down()) == this.parentBackground);
		case stalagmite_small:
			return (world.getBlockState(pos.down()) == this.parentBackground);
		case stalagmite_tip:
			return (hasProperty(world.getBlockState(pos.down()),  SpType.stalagmite_base) ||
					hasProperty(world.getBlockState(pos.down()), SpType.column));
		default :
			return true;
		}
	}

	private boolean hasProperty(IBlockState state, SpType type){
		if (state.getBlock() instanceof BlockSpeleothem){
			return state.getValue(TYPE) == type;
		}
		return false;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE);
	}


	@Override
	public IBlockState getStateFromMeta(int meta) {		
		return getDefaultState().withProperty(TYPE, SpType.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		SpType type = state.getValue(TYPE);
		return type.getID();
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		for (int loop = 0; loop < SpType.values().length; loop++){
			list.add(new ItemStack(itemIn, 1, loop));
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	public enum SpType implements IStringSerializable {
		stalactite_small(0, "stalactite_small", new AxisAlignedBB(0.2F, 0.2F, 0.2F, 0.8F, 1F, 0.8F)),
		stalactite_base(1, "stalactite_base", new AxisAlignedBB(0.2F, 0F, 0.2F, .8F, 1F, .8F)),
		stalactite_tip(2, "stalactite_tip", new AxisAlignedBB(0.3F, 0.4F, 0.3F, 0.7F, 1F, 0.7F)),
		column(3, "column", new AxisAlignedBB(0.3F, 0F, 0.3F, 0.7F, 1F, 0.7F)),
		stalagmite_small(4, "stalagmite_small", new AxisAlignedBB(0.2F, 0F, 0.2F, 0.8F, 0.8F, 0.8F)),
		stalagmite_base(5, "stalagmite_base", new AxisAlignedBB(0.2F, 0F, 0.2F, .8F, 1F, .8F)),
		stalagmite_tip(6, "stalagmite_tip", new AxisAlignedBB(0.3F, 0F, 0.3F, 0.7F, 0.7F, 0.7F));

		private final int ID;
		private final String name;
		public final AxisAlignedBB boundingBox;

		private SpType(int ID, String name, AxisAlignedBB box) {
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

	public IBlockState getBlockState(SpType type){
		return this.getDefaultState().withProperty(TYPE, type);
	}





}
