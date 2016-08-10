package wtf.core.blocks;

import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wtf.core.Core;
import wtf.core.init.WTFItems;

public class OreNitre extends BlockOre{
	public OreNitre()
	{
		super ();
		this.setUnlocalizedName("oreNitre");
		this.setHardness(3.0F);
		this.setResistance(5.0F);
		this.setCreativeTab(Core.wtfTab);
	}
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune){
		return WTFItems.nitre;
	}
	@Override
	public int quantityDropped(Random par1Random)
	{
		return par1Random.nextInt(4) + 1;
	}
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
}
