package wtf.worldgen;

import java.util.HashMap;

import net.minecraft.world.World;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;

public class GenScanMap {

	private volatile HashMap<ChunkCoords, ChunkScan> worldChunkScans;
	private volatile HashMap<ChunkCoords, MultiThreadScanner> scanners;
	private  volatile HashMap<ChunkCoords, MultiThreadGenerator> generators;
	public final World world;
	
	public GenScanMap(World world){
		this.world = world;
		worldChunkScans = new HashMap<ChunkCoords, ChunkScan>();
		scanners = new HashMap<ChunkCoords, MultiThreadScanner>();
		generators = new HashMap<ChunkCoords, MultiThreadGenerator>();
	}
	
	public ChunkScan getChunkScan(ChunkCoords coords){
		return worldChunkScans.get(coords);
	}
	
	public int scannersRemaining(){
		return scanners.size();
	}

	public void storeScan(ChunkCoords coords, ChunkScan scan){
		worldChunkScans.put(coords, scan);
	}
	
	public void storeGenerator(MultiThreadGenerator gen){
		generators.put(gen.coords, gen);
	}
	public void removeGenerator(ChunkCoords coords){
		generators.remove(coords);
	}
	public boolean containsGenerator(ChunkCoords coords){
		return generators.containsKey(coords);
	}
	
	public void regScanner(MultiThreadScanner scanner){
		scanners.put(scanner.coords, scanner);
	}
	
	public MultiThreadScanner getScanner(ChunkCoords coords) {
		return scanners.get(coords);
	}

	public void deRegScanner(ChunkCoords coords) {
		scanners.remove(coords);
	}

	public boolean scanStarted(ChunkCoords coords) {
		return scanners.containsKey(coords) || worldChunkScans.containsKey(coords);
		
	}
	
	
	
}
