package wtf.gameplay.eventlisteners;

import net.minecraft.block.material.Material;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.world.BlockEvent.CreateFluidSourceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

public class ListenerWaterSpawn {
	@SubscribeEvent
	public void watergen(CreateFluidSourceEvent event){
		if (event.getWorld().getBlockState(event.getPos()).getMaterial() == Material.WATER 
				&& !BiomeDictionary.isBiomeOfType(event.getWorld().getBiome(event.getPos()), Type.WET)){
			event.setResult(Result.DENY);
		}
	}
	
}
