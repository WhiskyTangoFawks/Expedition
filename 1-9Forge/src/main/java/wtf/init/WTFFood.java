package wtf.init;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import wtf.items.WTFFoodItem;

public class WTFFood {
	
	
	public final static ItemStack[] meats = {new ItemStack(Items.COOKED_CHICKEN), new ItemStack(Items.COOKED_BEEF), new ItemStack(Items.COOKED_FISH), new ItemStack(Items.COOKED_MUTTON),
			new ItemStack(Items.COOKED_PORKCHOP), new ItemStack(Items.COOKED_RABBIT)};
	
	//public final static ItemStack[] altMeats = {new ItemStack(Blocks.BROWN_MUSHROOM, 3), new ItemStack(Blocks.RED_MUSHROOM, 3), Items.EGG, lamb, rabi, pork};
	
	public static void initFood(){
	
		//Sandwiches
		ArrayList<ItemStack> sandwich = new ArrayList<ItemStack>();
		sandwich.add(new ItemStack(Items.BREAD));
		Item wich = GameRegistry.register(new WTFFoodItem(sandwich, meats).setRegistryName("sandwich").setUnlocalizedName("sandwich"));
		
		for (int loop = 0; loop < meats.length; loop++){
			GameRegistry.addShapelessRecipe(new ItemStack(wich, 1, loop), meats[loop], new ItemStack(Items.BREAD));
		}
				
		
	}
	
	
	
	
	
	
	
	
}
