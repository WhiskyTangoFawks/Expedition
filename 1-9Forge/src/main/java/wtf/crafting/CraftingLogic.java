package wtf.crafting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import wtf.core.Core;

public class CraftingLogic {
	

	
	public static ArrayList<RecipeWrapper> getCraftableStack(EntityPlayer player) {
		
		HashMap<Integer, Integer> inventoryHashMap = new HashMap<Integer, Integer>();
		
		for (ItemStack stack : player.inventory.mainInventory){
			if (stack != null){
				inventoryHashMap.put(itemNum(stack), stack.stackSize);
			}
		}
		
		ArrayList<RecipeWrapper> craftableRecipes = new ArrayList<RecipeWrapper>();
	
		for (RecipeWrapper recipe : RecipeParser.parsedRecipes) {
			
			if (canCraft(recipe, inventoryHashMap)) {
				craftableRecipes.add(recipe);
			}
		}
		return craftableRecipes;
	}
	
	public static boolean canCraft(RecipeWrapper recipe, HashMap<Integer, Integer> inventoryHashMap){
		
		for (ArrayList<ItemStack> subList : recipe.getIngrediants()){
			if (subList.size() > 0 && !hasIngrediant(inventoryHashMap, subList)){
					return false;
			}
		}
		return true;
		
	}
	
	public static boolean hasIngrediant(HashMap<Integer, Integer> inventoryHashMap, ArrayList<ItemStack> subList){
		
		for (ItemStack ingrediant : subList){
			if(ingrediant == null){
				return true;
			}
			if (inventoryHashMap.containsKey(itemNum(ingrediant))){// && inventoryHashMap.get(ingrediant) >= ingrediant.stackSize){
				return true;
			}
		}
		return false;
	}
	
	private static int itemNum(ItemStack stack){
		return Item.getIdFromItem(stack.getItem())*16+stack.getItemDamage();
	}
	

	


}
