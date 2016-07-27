package wtf.core.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockState extends ItemBlock{

	public ItemBlockState(Block block) {
		super(block);
		 this.setMaxDamage(0);
	        this.setHasSubtypes(true);
	}
   
	@Override
	public int getMetadata(int damage)
    {
        return damage;
    }

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return this.getUnlocalizedName() + "."+itemstack.getItemDamage();
	}



}
