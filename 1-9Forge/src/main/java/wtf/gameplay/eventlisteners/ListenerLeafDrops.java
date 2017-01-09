package wtf.gameplay.eventlisteners;

import net.minecraft.block.BlockLeaves;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.config.GameplayConfig;

public class ListenerLeafDrops {

	@SubscribeEvent
	public void BlockHarvestEvent(HarvestDropsEvent event){
		if (event.getState().getBlock() instanceof BlockLeaves){
			if (event.getWorld().rand.nextInt(100) < GameplayConfig.stickDrop){
				event.getDrops().add(new ItemStack(Items.STICK, 1));
			}
		}

	}
	
}
