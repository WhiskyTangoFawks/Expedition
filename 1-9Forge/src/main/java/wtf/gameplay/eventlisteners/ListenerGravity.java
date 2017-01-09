package wtf.gameplay.eventlisteners;

import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.gameplay.GravityMethods;
import wtf.init.BlockSets;

public class ListenerGravity {

	@SubscribeEvent
	public void PlayerPlaceBlock (PlaceEvent event)
	{
		if (!event.getPlayer().capabilities.isCreativeMode && BlockSets.fallingBlocks.containsKey(event.getState().getBlock())){
			GravityMethods.checkPos(event.getWorld(), event.getPos());
		}
	}

	@SubscribeEvent
	public void BlockHarvestEvent(HarvestDropsEvent event){
		//instead of using the block break event, which fires, before the block is replaced, I use the harvest, which is fired afterwards
		GravityMethods.checkPos(event.getWorld(), event.getPos().up());
	}

}
