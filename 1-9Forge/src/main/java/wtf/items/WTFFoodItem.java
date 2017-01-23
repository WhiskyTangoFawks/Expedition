package wtf.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class WTFFoodItem extends ItemFood{

	private final int[] healAmount;
	private final ItemStack[] variable;
	private final ArrayList<ItemStack> base;
	
	
	public WTFFoodItem(ArrayList<ItemStack> base, ItemStack[] variable) {
		super(-1, false);
		this.setHasSubtypes(true);
		healAmount = new int[variable.length];
		
		int baseHeal = 0;
		for (ItemStack stack : base){
			if (stack.getItem() instanceof ItemFood){
				ItemFood food = (ItemFood)stack.getItem();
				baseHeal += food.getHealAmount(stack)*stack.stackSize;
			}
			else {
				//else if it's not a food ingrediant- I need a backup hashset of added values for non-standard foods
				//or I need a conversion crafting step for non foods into foods
			}
		}
		
		for (int loop = 0; loop < variable.length; loop++){
			if (variable[loop].getItem() instanceof ItemFood){
				ItemFood food = (ItemFood)variable[loop].getItem();
				healAmount[loop] = baseHeal+food.getHealAmount(variable[loop])*variable[loop].stackSize;
			}
			else {
				//else if it's not a food ingrediant- I need a backup hashset of added values for non-standard foods
				//or I need a conversion crafting step for non foods into foods
			}
		}
		this.variable = variable;
		this.base= base;
	
	}
	
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
		for (int loop = 0; loop < variable.length; loop++){
			subItems.add(new ItemStack(this, 1, loop));
		}
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack stack)
    {
       
        return variable[stack.getItemDamage()].getUnlocalizedName() + "_" + this.getUnlocalizedName();
    }

	
    public int getHealAmount(ItemStack stack)
    {
        return healAmount[stack.getItemDamage()];
    }

}
