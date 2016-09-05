package wtf.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import wtf.core.Core;

public class RecipeWrapper {

	private final ArrayList<ArrayList<ItemStack>> wrappedRecipe = new ArrayList<ArrayList<ItemStack>>();
	public final ItemStack output;


	//modify the recipe wrapper to be an ArrayList<ArrayList<IngrediantWrapper>>
	//basic recipes, everything will have a size of one


	public RecipeWrapper(ShapelessRecipes rawRecipe){
		for (ItemStack stack: rawRecipe.recipeItems){
			wrappedRecipe.add(getSublist(stack));
		}
		output = rawRecipe.getRecipeOutput();
	}


	public RecipeWrapper(ShapedRecipes rawRecipe){

		//int width = ObfuscationReflectionHelper.getPrivateValue(ShapedRecipes.class, rawRecipe, "recipeWidth");
		//int height = ObfuscationReflectionHelper.getPrivateValue(ShapedRecipes.class, rawRecipe, "recipeHeight");
		for (ItemStack stack : rawRecipe.recipeItems){
			wrappedRecipe.add(getSublist(stack));
		}
		output = rawRecipe.getRecipeOutput();

	}

	public RecipeWrapper(ShapelessOreRecipe rawRecipe){
		
		for (Object obj : rawRecipe.getInput()){
			wrappedRecipe.add(getSublist(obj));
		}
		output = rawRecipe.getRecipeOutput();
	}

	public RecipeWrapper(ShapedOreRecipe rawRecipe){
		int width = ObfuscationReflectionHelper.getPrivateValue(ShapedOreRecipe.class, rawRecipe, "width");
		int height = ObfuscationReflectionHelper.getPrivateValue(ShapedOreRecipe.class, rawRecipe, "height");

		//for (Object obj : rawRecipe.getInput()){
		//	wrappedRecipe.add(getSublist(obj));
		//}
		
		//  This approach doesn't work, and I don't know why 
		int count = 0;
		Object[] ingrediants = rawRecipe.getInput();
		for (int hloop = 0; hloop < 3; hloop++){
			for (int vloop = 0; vloop < 3; vloop++){
				if (hloop < height && vloop < width && count < ingrediants.length){
					wrappedRecipe.add(getSublist(ingrediants[count]));
					count++;
				}
				else {
					wrappedRecipe.add(new ArrayList<ItemStack>());					
				}
			}
		}
		output = rawRecipe.getRecipeOutput();
	}



	public ArrayList<ArrayList<ItemStack>> getIngrediants(){
		return wrappedRecipe;
	}

	
	private ArrayList<ItemStack> getSublist(Object obj){
		ArrayList<ItemStack> subList = new ArrayList<ItemStack>();
		if (obj instanceof ItemStack){
			subList = parseItemStack(subList, (ItemStack)obj);
		}
		else if (obj instanceof List){
			for (Object subObj : (List<?>)obj){
				subList = parseItemStack(subList, (ItemStack)subObj);
			}
		}
		else if (obj == null){
			//do nothing, this is a placeholder null in a shaped recipe
		}
		else {
			Core.coreLog.info("Unrecognised object in a recipe " + obj);
		}
		return subList;
	}

		public ArrayList<ItemStack> parseItemStack(ArrayList<ItemStack> subList, ItemStack stack){
			if (stack.getItemDamage() < 0 || stack.getItemDamage() > 15){
				for (int loop = 0; loop < 15 ; loop ++){
					ItemStack newStack =new ItemStack(stack.getItem(), stack.stackSize, loop); 
					subList.add(newStack);
				}
			}
			else {
				subList.add(stack);
			}
			return subList;
		}
	}
