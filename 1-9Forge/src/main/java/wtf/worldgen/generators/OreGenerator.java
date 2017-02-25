package wtf.worldgen.generators;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.world.World;
import wtf.ores.OreGenAbstract;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;
import wtf.worldgen.GeneratorMethods;

public class OreGenerator {
	
	
	//the list the generators are registered to
	public static ArrayList<OreGenAbstract> oreGenRegister = new ArrayList<OreGenAbstract>();


	public void generate(World world, ChunkCoords coords, Random random, ChunkScan chunkscan, GeneratorMethods gen) throws Exception {
		
		
		for (OreGenAbstract oregen : oreGenRegister){
			oregen.generate(world, gen, random, coords, chunkscan);
		}
	}


	
}
