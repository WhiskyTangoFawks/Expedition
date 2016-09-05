package wtf.crafting;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public class IngrediantsAndOutputsInventory implements IInventory{

	private ItemStack[] stacks = new ItemStack[10];


	public void setRecipe(RecipeWrapper recipe){
		//#0 is the result
		//1-9 are the recipe
		// the +1 -1 stuff is to account for this
		ArrayList<ArrayList<ItemStack>> ingrediantsLists= recipe.getIngrediants();
		for (int slotLoop = 1; slotLoop < 10 ; slotLoop++){
			if (slotLoop-1 < ingrediantsLists.size() && !ingrediantsLists.get(slotLoop-1).isEmpty()){

				setInventorySlotContents(slotLoop, ingrediantsLists.get(slotLoop-1).get(0));
			}	
			else {
				setInventorySlotContents(slotLoop, null);

			}
		}
		setInventorySlotContents(0, recipe.output);

	}





	public IngrediantsAndOutputsInventory() {
		// TODO Auto-generated constructor stub
	}





	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.stacks[slot] = stack;
	}
	
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
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
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return stacks[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
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

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

}
