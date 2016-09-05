package wtf.crafting;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public class RecipeInventory implements IInventory{

	int currentPage = 0;	
	
	 
	
	public ItemStack[] stacks = new ItemStack[27];
	public RecipeWrapper [] recipes = new RecipeWrapper[27];
	ArrayList<RecipeWrapper> craftableRecipes;
	final int size;
	
	
	public RecipeInventory(EntityPlayer player) {
		craftableRecipes = CraftingLogic.getCraftableStack(player);
		size = craftableRecipes.size();
		
		updateStacks();

	}
	
	private void updateStacks(){
		for (int loop = 0; loop < 27 ; loop++) {
			if (loop < size-(currentPage*27)){
				setRecipeSlot(loop, craftableRecipes.get(loop+(27*currentPage)));
			}
			else {
				setRecipeSlot(loop, null);
			}
			
		}
	}
	
	public void pageForward(){
		if (size-(currentPage*27) >27){
			currentPage++;
			updateStacks();
			}
		
	}
	
	public void pageBack(){
		if (this.currentPage > 0){
			currentPage--;
			updateStacks();
		}
		}
	
	public void setRecipeSlot(int slot, RecipeWrapper recipe){
		this.stacks[slot] = recipe != null ? recipe.output : null;
		this.recipes[slot] = recipe;
		
	}
	
	
	public ItemStack[] getInv() {
		return stacks;
	}

	@Override
	public void clear() {
		stacks = new ItemStack[27];
	}

	@Override
	public int getSizeInventory() {
		return 27;
	}

	ItemStack arrow = new ItemStack(Items.ARROW);
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot == 37 &&  currentPage > 0){
			return arrow;
		}
		else if (slot == 38 &&  size-(currentPage*27) > 27){
			return arrow;
		}
		else if (slot < 37){
			return stacks[slot];
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int size) {
		return null;
	}


	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.stacks[slot] = stack;
		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
	}

	public void setInventorySlotContents(int slot, RecipeWrapper recipe) {
		if (recipe != null){
			ItemStack stack = recipe.output;
			this.stacks[slot] = stack;
			this.recipes[slot] = recipe;
			if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
				stack.stackSize = this.getInventoryStackLimit();
			}
		}
		else {
			this.stacks[slot] = null;
			this.recipes[slot] = null;
		}
	}
	
	
	@Override
	public String getName() {
		return "CraftableRecipes";
	}

	@Override
	public boolean hasCustomName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public void markDirty() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
