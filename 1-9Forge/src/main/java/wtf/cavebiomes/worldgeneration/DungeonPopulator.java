package wtf.cavebiomes.worldgeneration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Map.Entry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import wtf.api.PopulationGenerator;
import wtf.cavebiomes.worldgeneration.cavetypes.CaveProfile;
import wtf.core.config.CaveBiomesConfig;
import wtf.core.utilities.wrappers.AdjPos;
import wtf.core.utilities.wrappers.CaveListWrapper;
import wtf.core.utilities.wrappers.CavePosition;
import wtf.core.utilities.wrappers.ChunkCoords;
import wtf.core.utilities.wrappers.ChunkScan;

public class DungeonPopulator extends PopulationGenerator{

	@Override
	public void generate(World world, ChunkCoords coords, Random random, ChunkScan chunkscan) {


		CaveBiomeGenMethods gen = new CaveBiomeGenMethods(coords.getChunk(world), random);
		HashMap<ChunkCoords, CaveBiomeGenMethods> altGen = new HashMap<ChunkCoords, CaveBiomeGenMethods>();
		
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

					ChunkCoords adjCoords = new ChunkCoords(x, z);
					CaveBiomeGenMethods adjGen = altGen.get(adjCoords);
					if (adjGen == null){
						adjGen = new CaveBiomeGenMethods(adjCoords.getChunk(world), random);
						altGen.put(adjCoords, adjGen);
					}
					dungeon.generateWall(adjGen, random, pos, pos.getY()/(float)chunkscan.surfaceAvg, pos.getY() - (int)cave.getAvgFloor());
				}
				else {
					dungeon.generateWall(gen, random, pos, pos.getY()/(float)chunkscan.surfaceAvg, pos.getY() - (int)cave.getAvgFloor());
				}
			}
			
			Iterator<AdjPos> adjacentIterator = cave.adjacentWall.iterator();
			AdjPos adj;
			while (adjacentIterator.hasNext()){
				adj = adjacentIterator.next();
				dungeon.generateAdjacentWall(gen, random, adj, (float)adj.getY()/(float)chunkscan.surfaceAvg, adj.getY() - cave.getCenter().floor);
			}
			
			CavePosition position;
			Iterator<CavePosition> caveIterator= cave.cave.iterator();
			while (caveIterator.hasNext()) {
				position = caveIterator.next();
				if (!position.alreadyGenerated){
					generateDungeon(gen, random, dungeon, position, (float) ((float)cave.getAvgFloor()/chunkscan.surfaceAvg), (position == cave.getCenter()));
					position.alreadyGenerated = true;
				}		
			}
		}
		
		
		setBlockSet(gen);
		
		for (CaveBiomeGenMethods alt : altGen.values()){
			setBlockSet(alt);
		}
		
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

	protected void setBlockSet(CaveBiomeGenMethods gen){
		//System.out.println("blockset set called for " + blockset.size());
		Chunk chunk = gen.chunk;
		Iterator<Entry<BlockPos, IBlockState>> iterator = gen.blocksToSet.entrySet().iterator();
		while (iterator.hasNext()){
			Entry<BlockPos, IBlockState> entry = iterator.next();

			/*//shouldn't be necessary here
			while (entry.getKey().getY() < 0 || entry.getKey().getY() > world.getHeight()){
				if (iterator.hasNext()){
					entry = iterator.next();
				}
				else {
					return;
				}
			}
			 */

			BlockPos pos = entry.getKey();
			ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[pos.getY() >> 4];

			if (extendedblockstorage != Chunk.NULL_BLOCK_STORAGE)
			{
				extendedblockstorage.set(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15, entry.getValue());
				//chunk.setBlockState(entry.getKey(), entry.getValue());
			}
		}
	}


}
