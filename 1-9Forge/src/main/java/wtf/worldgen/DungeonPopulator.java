package wtf.worldgen;

import java.util.Iterator;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.api.PopulationGenerator;
import wtf.config.CaveBiomesConfig;
import wtf.utilities.wrappers.AdjPos;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;

public class DungeonPopulator extends PopulationGenerator{

	@Override
	public void generate(World world, ChunkCoords coords, Random random, ChunkScan chunkscan) {


		CaveBiomeGenMethods gen = new CaveBiomeGenMethods(world, coords, random);
		
		
		CaveListWrapper cave = getLargestCave(chunkscan);
		if (cave==null){return;}
		
		
		
		CaveProfile profile = CaveTypeRegister.getCaveProfile(cave.getBiome(world));
		AbstractDungeonType dungeon = profile.getDungeonForCave(gen, random, cave, (int) chunkscan.surfaceAvg);

		if (dungeon != null &&  random.nextFloat() < CaveBiomesConfig.dungeonChance){
			System.out.println("Generating dungeon " + dungeon.name + " @ " + cave.getCenter().x + " " + cave.getCenter().floor + " " + cave.getCenter().z);

			
			Iterator<BlockPos> wallIterator = cave.wall.iterator();
			BlockPos pos;
			while (wallIterator.hasNext()){
				pos = wallIterator.next();
				int x = pos.getX() >> 4;
				int z = pos.getX() >> 4;
				if (x != coords.getChunkX() || z != coords.getChunkX()){

					
					dungeon.generateWall(gen, random, pos, pos.getY()/(float)chunkscan.surfaceAvg, pos.getY() - (int)cave.getAvgFloor());
				}
				else {
					dungeon.generateWall(gen, random, pos, pos.getY()/(float)chunkscan.surfaceAvg, pos.getY() - (int)cave.getAvgFloor());
				}
			}
			
			Iterator<AdjPos> adjacentIterator = cave.adjacentWall.iterator();
			AdjPos adj;
			while (adjacentIterator.hasNext()){
				adj = adjacentIterator.next();
				dungeon.generateAdjacentWall(gen, random, adj, adj.getY()/(float)chunkscan.surfaceAvg, adj.getY() - cave.getCenter().floor);
			}
			
			CavePosition position;
			Iterator<CavePosition> caveIterator= cave.cave.iterator();
			while (caveIterator.hasNext()) {
				position = caveIterator.next();
				if (!position.alreadyGenerated){
					generateDungeon(gen, random, dungeon, position, (float) ((float)cave.getAvgFloor()/chunkscan.surfaceAvg), (position == cave.getCenter()));
					if (dungeon.genOver){
						position.alreadyGenerated = true;
					}
				}		
			}
		}
		
		
		gen.blocksToSet.setBlockSet();
	}


	public static void generateDungeon(CaveBiomeGenMethods gen, Random random, AbstractDungeonType dungeon, CavePosition pos, float depth, boolean center){

		dungeon.generateFloor(gen, random, pos.getFloorPos(), depth);
		dungeon.generateCeiling(gen, random, pos.getCeilingPos(), depth);

		if (random.nextInt(100) < dungeon.ceilingaddonchance+(1-depth)*5)
		{
			dungeon.generateCeilingAddons(gen, random, pos.getCeilingPos().down(), depth);
		}
		if (random.nextInt(100) < dungeon.flooraddonchance+(1-depth)*5)
		{
			dungeon.generateCeilingAddons(gen, random, pos.getCeilingPos().down(), depth);
		}
		if (center){
			dungeon.generateCenter(gen, random, pos, depth);
		}
		

	}


	public static CaveListWrapper getLargestCave(ChunkScan chunkscan){
		for (CaveListWrapper wrappedcave : chunkscan.caveset){
			for (int loop = 14; loop > 7; loop--){//loop down from 16- the theoretically largest possible cave size
				if (wrappedcave.cave.size() > loop*loop //if the number of blocks is larger than the area of a square size loop 
						&& wrappedcave.getSizeX() > loop-1 && wrappedcave.getSizeZ() > loop-1  //id the x and z min and max are at least as large as the loop size
						&& wrappedcave.getCenter() != null //and if the cave has a center within it's defined blockset 
						&& wrappedcave.getSizeX()*wrappedcave.getSizeZ()/wrappedcave.cave.size() > 0.8//and the density is at least 80%
						&& wrappedcave.getAvgCeiling() - wrappedcave.getAvgFloor() > 3 // and it's at least 3 blocks high
						&& wrappedcave.getAvgCeiling() - wrappedcave.getAvgFloor() < 8) //and less than 7 blocks high - so not a ravine
				{  
					return wrappedcave;
				}
			}
		}
		return null;
	}

	


}
