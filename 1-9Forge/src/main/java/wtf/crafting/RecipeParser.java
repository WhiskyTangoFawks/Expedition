package wtf.crafting;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import wtf.Core;
import wtf.config.MasterConfig;
import wtf.config.GameplayConfig;

public class RecipeParser {

	public static ArrayList<RecipeWrapper> parsedRecipes= new ArrayList<RecipeWrapper>();

	static HashSet<IRecipe> toremove = new HashSet<IRecipe>();

	public static void init(){

		List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
		Iterator<IRecipe> iterator = recipeList.iterator();
		while (iterator.hasNext()){
			IRecipe recipe = iterator.next();
		
			ItemStack stack = recipe != null ? recipe.getRecipeOutput() : null;
			Item output = stack != null ? stack.getItem() : null;
			if (MasterConfig.gameplaytweaks && GameplayConfig.removeVanillaTools && Loader.isModLoaded("tconstruct") && 
					  (output instanceof ItemAxe || output instanceof ItemHoe || output instanceof ItemPickaxe|| output instanceof ItemSpade || output instanceof ItemSword)) {
				Core.coreLog.info("Removing recipe for " + recipe.getRecipeOutput().getUnlocalizedName());
				iterator.remove();
				
			}

			else if (GameplayConfig.wcictable && MasterConfig.gameplaytweaks){

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
						System.out.println("Skipping unsupported recipe type for " + recipe.getClass());
					}

					if (wrappedRecipe != null){
						parsedRecipes.add(wrappedRecipe);
					}
				}
			}
			
		}
	}

}
