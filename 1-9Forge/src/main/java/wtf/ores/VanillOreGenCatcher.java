package wtf.ores;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.Core;

public class VanillOreGenCatcher {
	

	private static boolean iron = true;
	private static boolean coal = true;
	private static boolean diamond = true;
	private static boolean emerald = true;
	private static boolean lapis = true;
	private static boolean gold = true;
	private static boolean quartz = true;
	private static boolean redstone = true;
	
	@SubscribeEvent
	public void catchOreGen(GenerateMinable event){
	
		switch (event.getType()){

		case COAL:
			if (!coal){event.setResult(Event.Result.DENY);}
			break;
		case DIAMOND:
			if (!diamond){event.setResult(Event.Result.DENY);}
			break;
		case EMERALD:
			if (!emerald){event.setResult(Event.Result.DENY);}
			break;
		case GOLD:
			if (!gold){event.setResult(Event.Result.DENY);}
			break;
		case IRON:
			if (!iron){event.setResult(Event.Result.DENY);}
			break;
		case LAPIS:
			if (!lapis){event.setResult(Event.Result.DENY);}
			break;
		case QUARTZ:
			if (!quartz){event.setResult(Event.Result.DENY);}
			break;
		case REDSTONE:
			if (!redstone){event.setResult(Event.Result.DENY);}
			break;
		default:
			break;
		}

	}
	public static void vanillaCanceler(IBlockState state){
		if (state == Blocks.COAL_ORE.getDefaultState()){
			coal = false;
		}
		else if (state == Blocks.IRON_ORE.getDefaultState()){
			iron = false;
		}
		else if (state == Blocks.GOLD_ORE.getDefaultState()){
			gold = false;
		}
		else if (state == Blocks.DIAMOND_ORE.getDefaultState()){
			diamond = false;
		}
		else if (state == Blocks.EMERALD_ORE.getDefaultState()){
			emerald = false;
		}
		else if (state == Blocks.QUARTZ_ORE.getDefaultState()){
			quartz = false;
		}
		else if (state == Blocks.REDSTONE_ORE.getDefaultState()){
			redstone = false;
		}
		else if (state == Blocks.LAPIS_ORE.getDefaultState()){
			 lapis = false;
		}
		
		//Now add some sort of replacer here
		
	}
}
