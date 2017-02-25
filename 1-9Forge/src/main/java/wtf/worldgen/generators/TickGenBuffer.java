package wtf.worldgen.generators;

import java.util.LinkedList;
import java.util.Queue;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import wtf.utilities.wrappers.BlockMap;
import wtf.worldgen.CoreWorldGenListener;

public class TickGenBuffer {

	private static Queue<BlockMap> mapbuffer = new LinkedList<BlockMap>();

	public synchronized static void addMap(BlockMap blockmap){
		mapbuffer.add(blockmap);
	}


	@SubscribeEvent
	public void bufferGen(ServerTickEvent event){
		/*
		if (mapbuffer.size() > 10){
			System.out.println("Gen Map Buffer " + mapbuffer.size());
			for (int loop = 0; loop < 10; loop++){
				mapbuffer.poll().setBlocks();
			}
		}*/
		if (mapbuffer.size() > 0){
			BlockMap map = mapbuffer.poll();
			map.setBlocks();
		}
	}


}
