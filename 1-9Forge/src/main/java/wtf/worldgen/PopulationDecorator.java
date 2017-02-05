package wtf.worldgen;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.Core;
import wtf.api.PopulationGenerator;
import wtf.config.MasterConfig;
import wtf.utilities.UBC.UBCCavebiomesGenMethods;
import wtf.utilities.wrappers.AdjPos;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;
import wtf.worldgen.caves.CaveBiomeGenMethods;
import wtf.worldgen.caves.CaveProfile;
import wtf.worldgen.caves.CaveTypeRegister;

public class PopulationDecorator extends PopulationGenerator{
	
	@Override
	public void generate(World world, ChunkCoords coords, Random random, ChunkScan chunkscan) throws Exception {
		
		CaveBiomeGenMethods gen = Core.UBC ? new UBCCavebiomesGenMethods(world, coords, world.rand) : new CaveBiomeGenMethods(world, coords, world.rand);
		
		if (MasterConfig.caveGeneration){
			genCaves(world, gen, coords, random, chunkscan);
		}
		//get cave profile for a block roughlyh in the center of the chunk's surface

		//the tree populator has it's own setblockset, so we run setblockset now
		gen.blocksToSet.setBlockSet();
	}
	
	public static void genCaves(World world, CaveBiomeGenMethods gen, ChunkCoords coords, Random random, ChunkScan chunkscan){
		for (CaveListWrapper cave : chunkscan.caveset.getCaves()){

			CaveProfile profile = CaveTypeRegister.getCaveProfile(cave.getBiome(world));
			float depth =  (float) (cave.getAvgFloor()/chunkscan.surfaceAvg);
			AbstractCaveType cavetype = profile.getCave(cave.getAvgFloor()/chunkscan.surfaceAvg);

		for (CavePosition position : cave.getCaveSet() ) {
				//position = caveIterator.next();
				
					
					cavetype.generateFloor(gen, random, position.getFloorPos(), depth);
					cavetype.generateCeiling(gen, random, position.getCeilingPos(), depth);
					for (BlockPos wallpos : position.wall){
						cavetype.generateWall(gen, random, wallpos, depth, wallpos.getY() - position.floor);
					}
					for (AdjPos adjpos : position.adj){
						cavetype.generateAdjacentWall(gen, random, adjpos, depth, adjpos.getY() - position.floor);
					}
					
					
					if (random.nextInt(100) < cavetype.ceilingaddonchance+(1-depth)*5)
					{
						cavetype.generateCeilingAddons(gen, random, position.getCeilingPos().down(), depth);
					}
					
					if (random.nextInt(100) < cavetype.flooraddonchance+(1-depth)*5)
					{
						cavetype.generateFloorAddons(gen, random, position.getFloorPos().up(), depth);
					}

					if (cavetype.genAir){
						for (BlockPos airpos : position.getAirPos()){
								cavetype.generateAir(gen, random, airpos, depth);
						}
						
					}

				
				
			}
		}
	}	
}
