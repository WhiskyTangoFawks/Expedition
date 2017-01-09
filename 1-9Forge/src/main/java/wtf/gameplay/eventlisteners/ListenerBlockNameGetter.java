package wtf.gameplay.eventlisteners;

import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ListenerBlockNameGetter {

	@SubscribeEvent
	public void PlayerPlaceBlock (PlaceEvent event)
	{
		event.getPlayer().addChatComponentMessage(new TextComponentString("The block name is : " + event.getState().getBlock().getRegistryName()));
		event.getPlayer().addChatComponentMessage(new TextComponentString("The block metadata is : " + event.getState().getBlock().getMetaFromState(event.getState())));
	}


}
