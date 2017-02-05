package wtf.entities.customentities.renderers;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderZombieGhost extends  RenderBlockHead{

	public RenderZombieGhost(RenderManager renderManager) {
		super(renderManager);
		 TEXTURE = new ResourceLocation("wtfcore:textures/entity/transparent.png");
	}


	   
	
}
