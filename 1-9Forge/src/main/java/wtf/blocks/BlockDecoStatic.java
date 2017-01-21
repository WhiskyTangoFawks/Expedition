package wtf.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.gameplay.fracturing.EntityStoneCrack;
import wtf.init.BlockSets;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.StateAndModifier;

public class BlockDecoStatic extends AbstractBlockDerivative{

	public static final IProperty<DecoType> TYPE = PropertyEnum.create("type", DecoType.class);

	public static ArrayList<BlockDecoStatic> crackedList = new ArrayList<BlockDecoStatic>();

	public BlockDecoStatic(IBlockState state) {
		super(state, state);

		
		if (state.getMaterial() == Material.ROCK || state.getMaterial() == Material.GROUND){
			BlockSets.blockTransformer.put(new StateAndModifier(state, BlockSets.Modifier.MOSSY), this.getDefaultState());
			//I want to put the be able to cobbilify the mossy states too
			//the simplest way to do this, is to just make sure that the cobblestone version of the block is processed first
			//that way, I can just get the cobble version of the stone, then get the mossified version of the cobble
			//because I can get a cobble version from a stone, but It's harder to get a stone version from a cobble
			
			//So- we've already done cobble and this is not the stone pass
			IBlockState cobble = BlockSets.getTransformedState(state, Modifier.COBBLE);
			if (cobble != null){
				IBlockState mossyCobble = BlockSets.getTransformedState(cobble, Modifier.MOSSY);
				if (mossyCobble != null){
					BlockSets.blockTransformer.put(new StateAndModifier(this.getDefaultState(), Modifier.COBBLE), mossyCobble);
				}
			}
		}
			BlockSets.blockTransformer.put(new StateAndModifier(state, BlockSets.Modifier.SOUL), this.getDefaultState().withProperty(TYPE, DecoType.SOUL));
		if (state.getMaterial() == Material.ROCK || state.getMaterial() == Material.ICE){
			BlockSets.blockTransformer.put(new StateAndModifier(state, BlockSets.Modifier.CRACKED), this.getDefaultState().withProperty(TYPE, DecoType.CRACKED));
		}
		if (BlockSets.fallingBlocks.containsKey(this.parentBackground)){
			BlockSets.fallingBlocks.put(this, BlockSets.fallingBlocks.get(this.parentBackground));
		}

		//BlockSets.blockMiningSpeed
		if (BlockSets.fallingBlocks.containsKey(state.getBlock())){
			BlockSets.fallingBlocks.put(this, BlockSets.fallingBlocks.get(state.getBlock()));
		}
		BlockSets.ReplaceHashset.add(this);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state)
	{
		if (!world.isRemote && state.getValue(TYPE)==DecoType.CRACKED){
			EntityStoneCrack.crackStone(world, pos);
		}
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		return true;
	}

	@Override
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

	public enum DecoType implements IStringSerializable {
		MOSS(0, "moss", BlockSets.Modifier.MOSSY),
		SOUL(1, "soul", BlockSets.Modifier.SOUL),
		CRACKED(2, "cracked", BlockSets.Modifier.CRACKED);


		private final int ID;
		private final String name;
		public final BlockSets.Modifier modifier;

		private DecoType(int ID, String name, BlockSets.Modifier mod) {
			this.ID = ID;
			this.name = name;
			this.modifier = mod;
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

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		for (int loop = 0; loop < DecoType.values().length; loop++){
			list.add(new ItemStack(itemIn, 1, loop));
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{

		return this.getDefaultState().withProperty(TYPE, DecoType.values()[meta]);
	}


	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(TYPE).ID;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {TYPE});
	}

}
