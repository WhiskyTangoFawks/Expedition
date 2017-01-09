package wtf.ores;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.world.World;
import wtf.Core;
import wtf.api.PopulationGenerator;
import wtf.utilities.UBC.UBCChunkDividedOreMap;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;

public class OreGenerator extends PopulationGenerator{
	
	public OreGenerator(){
		
	}
	
	//the list the generators are registered to
	public static ArrayList<OreGenAbstract> oreGenRegister = new ArrayList<OreGenAbstract>();

	//I've changed the method to only do chunks which all adjacent chunks exist- so I should be able to just set blocks, instead of doing the ore gen map
	//use chunkdivided ghashmap, and just add ore blcoks to it, remove oremap method

	@Override
	public void generate(World world, ChunkCoords coords, Random random, ChunkScan chunkscan) throws Exception {
		
		ChunkDividedOreMap map = Core.UBC ? new UBCChunkDividedOreMap(world, coords) : new ChunkDividedOreMap(world, coords);
		
		for (OreGenAbstract gen : oreGenRegister){
			//long start = System.nanoTime();
			gen.generate(world, map, random, coords, chunkscan);
			//long end = System.nanoTime();
			//long time = (end-start)/100;
			//if (time > 500){
			//	System.out.println("Time for " + gen.oreBlock.toString() + " = " + time);
			//}
		}
		
		map.setBlockSet();

	}


	
}
