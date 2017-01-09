package wtf.gameplay.eventlisteners;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.config.GameplayConfig;

public class ListenerChickenDrops {

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		if (!event.getEntity().worldObj.isRemote && (event.getEntity() instanceof EntityChicken)){
			EntityChicken chicken = (EntityChicken) event.getEntity();
			if (!chicken.isChild()){

				if(event.getEntity().getEntityWorld().rand.nextInt(GameplayConfig.featherDrop) == 1){
					chicken.dropItem(Items.FEATHER, 1);
				}
			}
		}
	}
	
}
