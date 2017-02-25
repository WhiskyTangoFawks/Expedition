package wtf.worldgen;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import wtf.config.MasterConfig;
import wtf.config.OverworldGenConfig;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;
import wtf.worldgen.generators.DungeonGenerator;
import wtf.worldgen.generators.OreGenerator;
import wtf.worldgen.generators.SubBiomeGenerator;
import wtf.worldgen.generators.SurfaceGenerator;
import wtf.worldgen.generators.TickGenBuffer;
import wtf.worldgen.generators.TreeGenerator;
import wtf.worldgen.generators.CaveGenerator;

public class MultiThreadGenerator implements Runnable{

	//So, I instantiate a new runnable for every chunk
	//But that then gets passed to the pool, which is static

	private final World world;
	public final ChunkCoords coords;

	private static final OreGenerator oreGen = new OreGenerator();
	private static final DungeonGenerator dungeonGen = new DungeonGenerator();
	private static final CaveGenerator caveGen = new CaveGenerator();
	private static final TreeGenerator treeGen = new TreeGenerator();
	private static final SubBiomeGenerator subGen = new SubBiomeGenerator();
	private static final SurfaceGenerator surfaceGen = new SurfaceGenerator();

	public MultiThreadGenerator(World world, ChunkCoords coords){
		this.world = world;
		this.coords = coords;
	}

	@Override
	public void run() {
				
		ChunkScan scan = CoreWorldGenListener.getChunkScan(world, coords);
		generate(scan);
		
	}
	

	//Better way to do this- registry of all populated and ungenerated chunks- registry rebuilds itself on chunk load after exit
	//registry is effectively needs to be generated
	//on every add, check needs to be generated for adjacent

	private void generate(ChunkScan scan){
		//System.out.println("Generating " + coords.getChunkX() + " " + coords.getChunkZ());
		Random random = ThreadLocalRandom.current();
		GeneratorMethods gen = scan.gen;
		
		ChunkCoords coords = scan.coords;
		if (gen == null){
			System.out.println("******************FOUND NULL GEN");
			return;
		}
			
		gen.overrideBlock(scan.coords.getGenMarkerPos(), Blocks.BEDROCK.getDefaultState());
		
		try {	
			if (MasterConfig.enableOreGen){
				oreGen.generate(world, coords, random, scan, gen);
			}
			if (MasterConfig.dungeonGeneration){
				dungeonGen.generate(world, coords, random, scan, gen);
			}
			if (MasterConfig.caveGeneration){
				caveGen.generate(world, coords, random, scan, gen);
			}
			if (MasterConfig.enableOverworldGeneration && OverworldGenConfig.genTrees){
				treeGen.generate(world, coords, random, scan, gen);
			}
			if (OverworldGenConfig.modifySurface){ // I don't have this under a config a the moment
				subGen.generate(world, coords, random, scan, gen);
			}
			if (OverworldGenConfig.modifySurface && MasterConfig.enableOverworldGeneration){
				surfaceGen.generate(world, coords, random, scan, gen);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		TickGenBuffer.addMap(gen.blockmap);
		scan.gen = null;
	}




}
