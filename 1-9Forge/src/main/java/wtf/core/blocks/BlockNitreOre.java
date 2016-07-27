package wtf.core.blocks;

import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import wtf.core.Core;
import wtf.core.init.WTFItems;

public class BlockNitreOre extends BlockOre{
	public BlockNitreOre()
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

}
