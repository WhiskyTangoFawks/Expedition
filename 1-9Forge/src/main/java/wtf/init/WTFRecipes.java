package wtf.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wtf.config.GameplayConfig;



public class WTFRecipes {
	
	public static void initRecipes(){
		GameRegistry.addShapelessRecipe(new ItemStack(Items.GUNPOWDER), new Object[] {WTFItems.sulfur, WTFItems.nitre, WTFItems.nitre, WTFItems.nitre, new ItemStack(Items.COAL, 1, 1)});
		if (GameplayConfig.homescroll){
			GameRegistry.addRecipe(new ItemStack(WTFItems.homescroll), "x","y","x",'x', new ItemStack(Items.PAPER), 'y', new ItemStack(Items.ENDER_PEARL));
		}
		GameRegistry.addShapelessRecipe(new ItemStack(Items.DYE, 1, 15), new Object[]{WTFItems.nitre});
		GameRegistry.addShapelessRecipe(new ItemStack(WTFItems.sulfur, 1), new Object[]{Blocks.NETHERRACK});
		
		if (GameplayConfig.wcictable){
			GameRegistry.addRecipe(new ItemStack(WTFBlocks.wcicTable), " ","y","x",'y', new ItemStack(Items.BOOK), 'x', new ItemStack(Blocks.CRAFTING_TABLE));
		}
	}
	
}
