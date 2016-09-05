package wtf.crafting;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class RecipeDisplayContainer extends Container{

	//ArrayList<IRecipe> craftInventory;

	final EntityPlayer player;

	RecipeInventory recipeInventory;
	IngrediantsAndOutputsInventory ingrediantsInventory;

	public RecipeDisplayContainer(EntityPlayer player){
		this.player = player;
		recipeInventory = new RecipeInventory(player);
		ingrediantsInventory = new IngrediantsAndOutputsInventory();
		

		for (int loopHorizontal = 0; loopHorizontal < 3; ++loopHorizontal)
		{
			for (int loopVertical = 0; loopVertical < 3; ++loopVertical)
			{
				this.addSlotToContainer(new Slot(this.ingrediantsInventory, 1+loopVertical + loopHorizontal * 3, 30 + loopVertical * 18, 17 + loopHorizontal * 18));
			}
		}
		//Craft output slot
		this.addSlotToContainer(new Slot(this.ingrediantsInventory, 0, 124, 35));

		for (int vertLoop = 0; vertLoop < 3; ++vertLoop){
			for (int horzLoop = 0; horzLoop < 9; ++horzLoop)
			{
				this.addSlotToContainer(new Slot(recipeInventory, horzLoop + vertLoop * 9, 8 + horzLoop * 18, 84 + vertLoop * 18));
			}
		}
		
        for (int l = 0; l < 9; ++l)
        {
            this.addSlotToContainer(new Slot(recipeInventory, 37, 8 + 0 * 18, 142));
            this.addSlotToContainer(new Slot(recipeInventory, 38, 8 + 8 * 18, 142));
        }

	}

	@Override
	@Nullable
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player)
	{
		if (slotId == 37){
			recipeInventory.pageBack();
		}
		else if (slotId == 38){
			recipeInventory.pageForward();
		}
		if (slotId-10 > -1 && slotId - 10 < recipeInventory.recipes.length){	
		RecipeWrapper recipe = recipeInventory.recipes[slotId-10];
			if (recipe != null){
				ingrediantsInventory.setRecipe(recipe);
			}
			else {
				//null recipe
			}
		}
		
		return null;

	}


	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return false;
	}
}
