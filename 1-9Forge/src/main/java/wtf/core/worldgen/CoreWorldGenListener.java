package wtf.core.worldgen;

import java.util.HashMap;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.api.PopulationGenerator;
import wtf.api.WTFWorldGen;
import wtf.core.utilities.wrappers.ChunkCoords;
import wtf.core.utilities.wrappers.ChunkScan;

public class CoreWorldGenListener {

	public static HashMap<ChunkCoords, ChunkScan> worldChunkScans = new HashMap<ChunkCoords, ChunkScan>();


	//Reworking generation to require multiple chunks be present
	//For trees: the core idea is to generate based on aa 16 x 16 area, at the center of 4 chunk corners
	//that way, I've always got  a boundary of 8 blocks
	//alternatively:
	//- I can swap the primary chunk scanner over to requrie a boundry of one chunk be completely populated first
	//pros- larger boiundry, doesn't require any extra code, meshes perfectly with what I already have done
	
	
	//code to do it
	//whenever a chunk populates, I need to check and see if it's the last one of each possible set it can belong to
	//so, I need to check 25 chunks for isPopulated

	//I can setup and populate a boolean array, and then just check it with a loop structure
	
	
	//Is there a way I can bypass much of these problems by calling getChunkScan
	//It's effectively a database of whether or not chunks have been populated
	//SO- the first step is to check the central chunk of each set 3x3 and see if it's already been done- because if so, it doesn't need to be done again
	
	//So- create a method to call, which first checks teh central chunk isPopulated, but not scanned
	//then, call a secondary method which checks that the surrounding chunks are populated (scanned or no, doesn't matter)
	
	
	
	
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void populate(PopulateChunkEvent.Post event) throws Exception{
		
		if (!event.getWorld().isRemote){
			ChunkCoords coords = new ChunkCoords(event.getChunkX(), event.getChunkZ());
			ChunkProviderServer chunkserver = (ChunkProviderServer)event.getWorld().getChunkProvider();
		
			for (int xloop = -1; xloop < 2; xloop++){
				for (int zloop = -1; zloop < 2; zloop++){
					int adjX = coords.getChunkX()+xloop;
					int adjZ = coords.getChunkZ()+zloop;
					if (chunkserver.chunkExists(adjX, adjZ) && event.getWorld().getChunkFromChunkCoords(adjX, adjZ).isTerrainPopulated()){
						doScanIfAdjArePopulated(event.getWorld(), chunkserver, event.getRand(), new ChunkCoords(adjX, adjZ));
					}
				}	
			}
						
			
			
		}
	}
	
	public static void doScanIfAdjArePopulated(World world, ChunkProviderServer chunkserver, Random random, ChunkCoords coords) throws Exception{
		
		for (int xloop = -1; xloop < 2; xloop++){
			for (int zloop = -1; zloop < 2; zloop++){
				int adjX = coords.getChunkX()+xloop;
				int adjZ = coords.getChunkZ()+zloop;
				if (! (chunkserver.chunkExists(adjX, adjZ) && world.getChunkFromChunkCoords(adjX, adjZ).isTerrainPopulated())){
					return;
				}
			}	
		}
		doChunk(world, random, coords);
	}
	

	public static void doChunk(World world, Random random, ChunkCoords coords) throws Exception{
				
		WorldScanner scanner = new WorldScanner();
		ChunkScan scan = scanner.getChunkScan(world, coords);
		
		
		worldChunkScans.put(coords, scan);
		
		
		for (PopulationGenerator generator : WTFWorldGen.getGenerators()){
			generator.generate(world, coords, random, scan);
		}

		//long endTime = System.currentTimeMillis();
		//System.out.println("population "+ event.getWorld().getBiomeGenForCoords(new BlockPos(coords.getWorldX(), 100, coords.getWorldZ())).getBiomeName() + (endTime - startTime) + " milliseconds");

		
		
	}
	
	

	public static ChunkScan getChunkScan(World world, ChunkCoords coords) throws Exception{
		
		ChunkScan scan = worldChunkScans.get(coords);
		 
		if (scan == null && coords.exists(world)){
			//System.out.println("Scan not found for existing chunk " + coords.getChunkX() + " " + coords.getChunkZ() + " ");
			//System.out.println("chunk populated " + coords.getChunk(world).isPopulated() + " terrain populated " + coords.getChunk(world).isTerrainPopulated());
			WorldScanner scanner = new WorldScanner();
			scan = scanner.getChunkScan(world, coords);
			
			worldChunkScans.put(coords, scan);
		}
		else{
			//System.out.println("Chunk not loaded");
		}
		
		if (worldChunkScans.size() > 1000){
			System.out.println("Clearing World Scans");
			worldChunkScans.clear();
		}
		return scan;
	}

	

}
