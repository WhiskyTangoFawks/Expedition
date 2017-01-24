package wtf.gameplay.food;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FoodListener {

	static int milkHash = Items.MILK_BUCKET.hashCode();
	
	@SubscribeEvent
	public static void openContainer(PlayerContainerEvent.Open event){
		
		//How to keep from just reopening and closing
		
		//swap this over to an iterator so I can modify it on the fly
		for (ItemStack stack : event.getContainer().inventoryItemStacks){
			if (stack.getItem().hashCode() == milkHash){
				event.getContainer().getInventory().remove(o)
				//add bucket and cheese
			}
		}
	}
	
}
