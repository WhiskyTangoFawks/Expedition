package wtf.gameplay.eventlisteners;

import net.minecraft.world.Explosion;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.gameplay.CustomExplosion;

public class ListenerCustomExplosion {

	@SubscribeEvent
	public void explosion(ExplosionEvent.Start event){
			Explosion explosion = event.getExplosion();
			float size = ObfuscationReflectionHelper.getPrivateValue(Explosion.class, explosion, 8);
			new CustomExplosion(explosion.getExplosivePlacedBy(), event.getWorld(), explosion.getPosition(), size);
		event.setCanceled(true);
		}
	
	
}
