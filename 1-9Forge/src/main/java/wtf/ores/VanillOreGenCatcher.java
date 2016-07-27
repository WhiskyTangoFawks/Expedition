package wtf.ores;

import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.ores.config.WTFOreConfig;

public class VanillOreGenCatcher {

	@SubscribeEvent
	public void catchOreGen(GenerateMinable event){
	
		//I need to add replacers to emerald here, to remove them during gen
		
		switch (event.getType()){
		case ANDESITE:
			break;
		case COAL:
			if (WTFOreConfig.cancelVanillaCoal){event.setResult(Event.Result.DENY);}
			break;
		case CUSTOM:
			break;
		case DIAMOND:
			if (WTFOreConfig.cancelVanillaDiamond){event.setResult(Event.Result.DENY);}
			break;
		case DIORITE:
			break;
		case DIRT:
			break;
		case EMERALD:
			if (WTFOreConfig.cancelVanillaEmerald){event.setResult(Event.Result.DENY);}
			break;
		case GOLD:
			if (WTFOreConfig.cancelVanillaGold){event.setResult(Event.Result.DENY);}
			break;
		case GRANITE:
			break;
		case GRAVEL:
			break;
		case IRON:
			if (WTFOreConfig.cancelVanillaIron){event.setResult(Event.Result.DENY);}
			break;
		case LAPIS:
		if (WTFOreConfig.cancelVanillaLapis){event.setResult(Event.Result.DENY);}
			break;
		case QUARTZ:
			if (WTFOreConfig.cancelVanillaQuartz){event.setResult(Event.Result.DENY);}
			break;
		case REDSTONE:
			if (WTFOreConfig.cancelVanillaRedstone){event.setResult(Event.Result.DENY);}
			break;
		case SILVERFISH:
			break;
		default:
			break;
		
		}
	}

}
