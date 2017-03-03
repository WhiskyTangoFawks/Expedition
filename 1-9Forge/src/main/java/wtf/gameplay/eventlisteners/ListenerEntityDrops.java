package wtf.gameplay.eventlisteners;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.config.GameplayConfig;

public class ListenerEntityDrops {

	Random random = new Random();
	
	@SubscribeEvent
	public void entityDrops(LivingDropsEvent event){
		if (event.getEntity() instanceof EntityPlayer || event.getSource().getEntity() instanceof EntityPlayer){
			return;
		}
		int r = random.nextInt(100);
		//System.out.println("Mob drop event caught " + r + " > " + GameplayConfig.mobDropsReqPlayer);
		if (r > GameplayConfig.mobDropsReqPlayer){
			//event.setCanceled(true);
			//System.out.println("Canceling drops");
		}
	}
	
}
