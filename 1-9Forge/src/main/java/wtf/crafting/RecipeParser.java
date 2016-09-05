package wtf.crafting;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeParser {

	public static ArrayList<RecipeWrapper> parsedRecipes= new ArrayList<RecipeWrapper>();

	public static void init(){
		
		for (IRecipe recipe : CraftingManager.getInstance().getRecipeList()) {
			
			RecipeWrapper wrappedRecipe = null;
			if (recipe.getRecipeOutput() != null){

			if (recipe instanceof ShapelessRecipes) {
				wrappedRecipe = new RecipeWrapper((ShapelessRecipes)recipe);
			} else if (recipe instanceof ShapedRecipes) {
				wrappedRecipe = new RecipeWrapper((ShapedRecipes)recipe);
			} else if (recipe instanceof ShapelessOreRecipe) {
				wrappedRecipe = new RecipeWrapper((ShapelessOreRecipe)recipe);
			} else if (recipe instanceof ShapedOreRecipe) {
				wrappedRecipe = new RecipeWrapper((ShapedOreRecipe)recipe);
			}
			else {
				System.out.println("Unsupported recipe type for " + recipe.getClass());
			}
			
			if (wrappedRecipe != null){
				parsedRecipes.add(wrappedRecipe);
			}
			}
		}
	}
	
}
