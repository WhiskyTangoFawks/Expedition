package wtf.cavebiomes.worldgeneration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Map.Entry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import wtf.api.PopulationGenerator;
import wtf.cavebiomes.worldgeneration.cavetypes.CaveProfile;
import wtf.core.utilities.wrappers.AdjPos;
import wtf.core.utilities.wrappers.CaveListWrapper;
import wtf.core.utilities.wrappers.CavePosition;
import wtf.core.utilities.wrappers.ChunkCoords;
import wtf.core.utilities.wrappers.ChunkScan;

public class CavePopulator extends PopulationGenerator{

	@Override
	public void generate(World world, ChunkCoords coords, Random random, ChunkScan chunkscan) {

		CaveBiomeGenMethods gen = new CaveBiomeGenMethods(coords.getChunk(world), random);
		HashMap<ChunkCoords, CaveBiomeGenMethods> altGen = new HashMap<ChunkCoords, CaveBiomeGenMethods>();

		for (CaveListWrapper cave : chunkscan.caveset){

			CaveProfile profile = CaveTypeRegister.getCaveProfile(cave.getBiome(world));
			double depth =  (float)cave.getAvgFloor()/chunkscan.surfaceAvg;
			AbstractCaveType cavetype = profile.getCave(cave.cave.get(0), (int) chunkscan.surfaceAvg);

			CavePosition position;
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
					cavetype.generateWall(adjGen, random, pos, pos.getY()/(float)chunkscan.surfaceAvg, pos.getY() - (int)cave.getAvgFloor());
				}
				else {
					cavetype.generateWall(gen, random, pos, pos.getY()/(float)chunkscan.surfaceAvg, pos.getY() - (int)cave.getAvgFloor());
				}
			}

			Iterator<AdjPos> adjacentIterator = cave.adjacentWall.iterator();
			AdjPos adj;
			while (adjacentIterator.hasNext()){
				adj = adjacentIterator.next();

				cavetype.generateAdjacentWall(gen, random, adj, adj.getY()/(float)chunkscan.surfaceAvg, adj.getY() - (int)cave.getAvgFloor());

			}

			Iterator<CavePosition> caveIterator= cave.cave.iterator();
			while (caveIterator.hasNext()) {
				position = caveIterator.next();
				if (!position.alreadyGenerated){

					generateCaveType(gen, profile.getCave(position, (int) chunkscan.surfaceAvg), world, random, position, (float) depth);
					position.alreadyGenerated = true;
				}
			}
		}

		
		setBlockSet(gen);
		
		for (CaveBiomeGenMethods alt : altGen.values()){
			setBlockSet(alt);
		}

	}

	public static void generateCaveType(CaveBiomeGenMethods gen, AbstractCaveType cavetype, World world, Random random, CavePosition pos, float depth){

		cavetype.generateFloor(gen, random, pos.getFloorPos(), depth);
		cavetype.generateCeiling(gen, random, pos.getCeilingPos(), depth);

		if (random.nextInt(100) < cavetype.ceilingaddonchance+(1-depth)*5)
		{
			cavetype.generateCeilingAddons(gen, random, pos.getCeilingPos().down(), depth);
		}
		if (random.nextInt(100) < cavetype.flooraddonchance+(1-depth)*5)
		{
			cavetype.generateFloorAddons(gen, random, pos.getFloorPos().up(), depth);
		}
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
			chunk.setBlockState(entry.getKey(), entry.getValue());
			//BlockPos pos = entry.getKey();
			//ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[pos.getY() >> 4];

			//if (extendedblockstorage != Chunk.NULL_BLOCK_STORAGE)
			//{
			//	extendedblockstorage.set(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15, entry.getValue());
				
			//}
		}
	}

}
