package wtf.gameplay.eventlisteners;

import net.minecraft.item.ItemStack;

public class ListenerHelper {

	public static boolean isHammer(ItemStack stack){
		if (stack == null){
			return false;
		}
		return stack.getItem().getItemStackDisplayName(stack).contains("ammer");

	}
	
}
