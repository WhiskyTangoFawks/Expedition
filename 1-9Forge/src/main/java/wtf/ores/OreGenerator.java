package wtf.ores;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.world.World;
import wtf.api.OreGenAbstract;
import wtf.api.PopulationGenerator;
import wtf.core.utilities.wrappers.ChunkCoords;
import wtf.core.utilities.wrappers.ChunkDividedOreMap;
import wtf.core.utilities.wrappers.ChunkScan;

public class OreGenerator extends PopulationGenerator{
	
	public OreGenerator(){
		
	}
	
	//the list the generators are registered to
	public static ArrayList<OreGenAbstract> oreGenRegister = new ArrayList<OreGenAbstract>();

	//I've changed the method to only do chunks which all adjacent chunks exist- so I should be able to just set blocks, instead of doing the ore gen map
	//use chunkdivided ghashmap, and just add ore blcoks to it, remove oremap method

	@Override
	public void generate(World world, ChunkCoords coords, Random random, ChunkScan chunkscan) throws Exception {
		
		ChunkDividedOreMap map = new ChunkDividedOreMap(world, coords);
		
		for (int loop = 0; loop < OreGenerator.oreGenRegister.size(); loop++){
			
			OreGenerator.oreGenRegister.get(loop).generate(world, map, random, coords, chunkscan);
		}
		
		map.setBlockSet();

	}


	
}
