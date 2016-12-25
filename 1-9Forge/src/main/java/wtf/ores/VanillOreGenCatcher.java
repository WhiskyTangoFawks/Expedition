package wtf.ores;

import net.minecraft.init.Blocks;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.ores.config.WTFOresNewConfig;

public class VanillOreGenCatcher {

	@SubscribeEvent
	public void catchOreGen(GenerateMinable event){
	
		//I need to add replacers to emerald here, to remove them during gen
		
		switch (event.getType()){
		case ANDESITE:
			break;
		case COAL:
			if (WTFOresNewConfig.cancelOres.contains(Blocks.COAL_ORE.getDefaultState())){event.setResult(Event.Result.DENY);}
			break;
		case CUSTOM:
			break;
		case DIAMOND:
			if (WTFOresNewConfig.cancelOres.contains(Blocks.DIAMOND_ORE.getDefaultState())){event.setResult(Event.Result.DENY);}
			break;
		case DIORITE:
			break;
		case DIRT:
			break;
		case EMERALD:
			if (WTFOresNewConfig.cancelOres.contains(Blocks.EMERALD_ORE.getDefaultState())){event.setResult(Event.Result.DENY);}
			break;
		case GOLD:
			if (WTFOresNewConfig.cancelOres.contains(Blocks.GOLD_ORE.getDefaultState())){event.setResult(Event.Result.DENY);}
			break;
		case GRANITE:
			break;
		case GRAVEL:
			break;
		case IRON:
			if (WTFOresNewConfig.cancelOres.contains(Blocks.IRON_ORE.getDefaultState())){event.setResult(Event.Result.DENY);}
			break;
		case LAPIS:
			if (WTFOresNewConfig.cancelOres.contains(Blocks.LAPIS_ORE.getDefaultState())){event.setResult(Event.Result.DENY);}
			break;
		case QUARTZ:
			if (WTFOresNewConfig.cancelOres.contains(Blocks.QUARTZ_ORE.getDefaultState())){event.setResult(Event.Result.DENY);}
			break;
		case REDSTONE:
			if (WTFOresNewConfig.cancelOres.contains(Blocks.REDSTONE_ORE.getDefaultState())){event.setResult(Event.Result.DENY);}
			break;
		case SILVERFISH:
			break;
		default:
			break;
		
		}
	}

}
