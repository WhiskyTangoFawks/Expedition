package wtf.blocks.UBC;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import wtf.Core;

import java.util.List;

import exterminatorjeff.undergroundbiomes.api.enums.SedimentaryVariant;

public class UBCSand extends BlockFalling{

	public static final PropertyEnum<SedimentaryVariant> VARIANT = PropertyEnum.<SedimentaryVariant>create("variant", SedimentaryVariant.class);
	
	public UBCSand(){
		this.setCreativeTab(Core.wtfTab);
		 this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, SedimentaryVariant.LIMESTONE));
		 
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, VARIANT);
	}


	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, SedimentaryVariant.LIMESTONE);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT).getMetadata();
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		for (int loop = 0; loop < 8; loop++){
			list.add(new ItemStack(itemIn, 1, loop));
		}
	}
	
	
	
	
}
