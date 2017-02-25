package wtf.worldgen;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.Core;
import wtf.init.WTFBlocks;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;

public class CoreWorldGenListener {

	private static ExecutorService executor; 
	public static HashMap<Integer, GenScanMap> masterMap;

	
	public CoreWorldGenListener(){
		executor = Executors.newFixedThreadPool(5); 
		masterMap = new HashMap<Integer, GenScanMap>();
	}
	
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void populate(PopulateChunkEvent.Post event){

		//boolean village = event.isHasVillageGenerated();
		
		if (event.getWorld().isRemote || event.getWorld().getWorldType() == WorldType.FLAT){
			return;
		}
		
		GenScanMap worldMap = masterMap.get(event.getWorld().provider.getDimension());
		if (worldMap == null){
			worldMap = new GenScanMap(event.getWorld());
			masterMap.put(event.getWorld().provider.getDimension(), worldMap);
		}

		ChunkCoords coords = new ChunkCoords(event.getChunkX(), event.getChunkZ());

		BlockPos pos = coords.getGenMarkerPos();
		event.getWorld().setBlockState(pos, WTFBlocks.genMarker.getDefaultState());	
		
		for (ChunkCoords adjCoords : coords.getChunksInRadius(1)){
			if (shouldScan(worldMap, adjCoords)){
				MultiThreadScanner scanner = new MultiThreadScanner(event.getWorld(), adjCoords);
				worldMap.regScanner(scanner);
				executor.execute(scanner);	
			}
		}
		
		
		for (ChunkCoords adjCoords : coords.getChunksInRadius(2)){
			if (readyForGen(worldMap, adjCoords)){
				
				MultiThreadGenerator generator = new MultiThreadGenerator(event.getWorld(), adjCoords);
				worldMap.storeGenerator(generator);
				executor.execute(generator);	
			}
		}	
	}
	
	
	@SubscribeEvent
	public void chunkLoad(ChunkEvent.Load event){
				
		ChunkCoords coords = new ChunkCoords(event.getChunk().xPosition, event.getChunk().zPosition);
		if (event.getWorld().isRemote || event.getWorld().getWorldType() == WorldType.FLAT){
			return;
		}
		GenScanMap worldMap = masterMap.get(event.getWorld().provider.getDimension());
		if (worldMap == null){
			worldMap = new GenScanMap(event.getWorld());
			masterMap.put(event.getWorld().provider.getDimension(), worldMap);
		}
		if (coords.isWTFGenerated(event.getWorld())){
			//System.out.println("Already generated");
			return;
		}
		for (ChunkCoords adjCoords : coords.getChunksInRadius(1)){
			if (shouldScan(worldMap, adjCoords)){
				MultiThreadScanner scanner = new MultiThreadScanner(event.getWorld(), adjCoords);
				worldMap.regScanner(scanner);
				executor.execute(scanner);	
			}
		}
		
		
		for (ChunkCoords adjCoords : coords.getChunksInRadius(2)){
			if (readyForGen(worldMap, adjCoords)){
				
				MultiThreadGenerator generator = new MultiThreadGenerator(event.getWorld(), adjCoords);
				worldMap.storeGenerator(generator);
				executor.execute(generator);	
			}
		}	
	}
	


	
	private static boolean shouldScan(GenScanMap worldMap, ChunkCoords coords){
		//not not scanned or scanning
		//not generated
		//all adjacent are populated
		
		if (!coords.exists(worldMap.world) || worldMap.getScanner(coords) != null || worldMap.getChunkScan(coords) != null){
			return false;
		}
		
		for (ChunkCoords checkCoords : coords.getChunksInRadius(1)){
			if (!checkCoords.isPopulated(worldMap.world)){
				return false;
			}
		}
		return true;
	}
	
	private static boolean readyForGen(GenScanMap worldMap, ChunkCoords coords){
		
		 //not already generated or generating
		//all adjacent are scanned or scanning
		if (!coords.exists(worldMap.world)){
			return false;
		}
		
		if (worldMap.containsGenerator(coords) || coords.isWTFGenerated(worldMap.world)){
			return false;
		}
		for (ChunkCoords checkCoords : coords.getChunksInRadius(1)){
		
			if (!checkCoords.exists(worldMap.world)){
				return false;
			}
			
			if (worldMap.scanStarted(checkCoords)){
				//continue
			}
			else if (shouldScan(worldMap, checkCoords)){
				MultiThreadScanner scanner = new MultiThreadScanner(worldMap.world, checkCoords);
				worldMap.regScanner(scanner);
				executor.execute(scanner);
			}
			else {
				return false;
			}
		}
		return true;
	}

	public static ChunkScan getChunkScan(World world, ChunkCoords coords){
		GenScanMap worldMap =masterMap.get(world.provider.getDimension()); 
		ChunkScan scan = worldMap.getChunkScan(coords);
		if (scan != null){
			return scan;	
		}
		
		MultiThreadScanner scanner = worldMap.getScanner(coords);
		if (scanner != null){
			try {
				//System.out.println("waiting " );
				scanner.latchOn();
				return worldMap.getChunkScan(coords);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else if (shouldScan(worldMap, coords)){
			scanner = new MultiThreadScanner(world, coords);
			worldMap.regScanner(scanner);
			executor.execute(scanner);
			try {
				scanner.latchOn();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return worldMap.getChunkScan(coords);
		}
		else {
			Core.coreLog.info("Trying to get a chunkscan for a chunk that isn't ready to be scanned");
		}
		
		return scan;
		
	}

	public static void storeScan(World world, ChunkCoords coords, ChunkScan scan) {
		GenScanMap map = masterMap.get(world.provider.getDimension());
		map.storeScan(coords, scan);
		
	}

	public static void deRegScanner(World world, ChunkCoords coords) {
		masterMap.get(world.provider.getDimension()).deRegScanner(coords);
	}

	public static void deRegGenerator(World world, ChunkCoords coords) {
		masterMap.get(world.provider.getDimension()).removeGenerator(coords);
		
	}
	
}
