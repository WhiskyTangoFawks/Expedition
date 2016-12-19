package wtf.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import wtf.init.BlockSets;
import wtf.init.WTFBlocks;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.StateAndModifier;

public class BlockDecoStatic extends AbstractBlockDerivative{

	public static final IProperty<DecoType> TYPE = PropertyEnum.create("type", DecoType.class);
	
	public BlockDecoStatic(IBlockState state) {
		super(state, state);
		
		BlockSets.blockTransformer.put(new StateAndModifier(state, BlockSets.Modifier.MOSSY), this.getDefaultState());
		BlockSets.blockTransformer.put(new StateAndModifier(state, BlockSets.Modifier.SOUL), this.getDefaultState().withProperty(TYPE, DecoType.SOUL));
		if (this.blockMaterial == Material.SAND){
			BlockSets.defaultFallingBlocks.add(this.getRegistryName()+"@10");
		}
		
		if (state == Blocks.SANDSTONE.getDefaultState()){
			Block decoSand = WTFBlocks.registerBlockItemSubblocks(new BlockDecoStatic(Blocks.SAND.getDefaultState()), BlockDecoStatic.DecoType.values().length-1, "sand0DecoStatic");
			BlockSets.blockTransformer.put(new StateAndModifier(this.getDefaultState().withProperty(TYPE, DecoType.MOSS), Modifier.COBBLE), decoSand.getDefaultState().withProperty(TYPE, DecoType.MOSS));
			BlockSets.blockTransformer.put(new StateAndModifier(this.getDefaultState().withProperty(TYPE, DecoType.SOUL), Modifier.COBBLE), decoSand.getDefaultState().withProperty(TYPE, DecoType.SOUL));
		}
		else if (state == Blocks.RED_SANDSTONE.getDefaultState()){
			Block decoSand = WTFBlocks.registerBlockItemSubblocks(new BlockDecoStatic(Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND)), BlockDecoStatic.DecoType.values().length-1, "sand1DecoStatic");
			BlockSets.blockTransformer.put(new StateAndModifier(this.getDefaultState().withProperty(TYPE, DecoType.MOSS), Modifier.COBBLE), decoSand.getDefaultState().withProperty(TYPE, DecoType.MOSS));
			BlockSets.blockTransformer.put(new StateAndModifier(this.getDefaultState().withProperty(TYPE, DecoType.SOUL), Modifier.COBBLE), decoSand.getDefaultState().withProperty(TYPE, DecoType.SOUL));
		}
			
		//BlockSets.blockMiningSpeed
		if (BlockSets.fallingBlocks.containsKey(state.getBlock())){
			BlockSets.fallingBlocks.put(this, BlockSets.fallingBlocks.get(state.getBlock()));
		}
		BlockSets.ReplaceHashset.add(this);
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
		SOUL(1, "soul", BlockSets.Modifier.SOUL);
	
		
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
