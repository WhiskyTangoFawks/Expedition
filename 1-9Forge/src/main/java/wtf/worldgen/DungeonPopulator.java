package wtf.worldgen;

import java.util.HashSet;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import wtf.Core;
import wtf.api.PopulationGenerator;
import wtf.config.CaveBiomesConfig;
import wtf.utilities.UBC.UBCCavebiomesGenMethods;
import wtf.utilities.simplex.SimplexHelper;
import wtf.utilities.wrappers.AdjPos;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;
import wtf.worldgen.caves.CaveBiomeGenMethods;
import wtf.worldgen.caves.CaveProfile;
import wtf.worldgen.caves.CaveTypeRegister;

public class DungeonPopulator extends PopulationGenerator{

	private HashSet<ChunkCoords> spawned = new HashSet<ChunkCoords>();
	
	@Override
	public void generate(World world, ChunkCoords coords, Random random, ChunkScan chunkscan) {

		//System.out.println("generating dungeons in " + coords.getChunkX() + " "+ coords.getChunkZ());
		if (world.provider.getDimensionType() == DimensionType.OVERWORLD){


			CaveListWrapper cave = getLargestCave(world, chunkscan);
			if (cave==null){
				//if the cave is null, or if the cave floor center isn't a naturally occuring block
				return;
			}
			
			for (ChunkCoords adj : coords.getChunksInRadius(1)){
				if (spawned.contains(adj)){
					//System.out.println("stopped from spawning adjacency");
					return;
				}
			}

			CaveBiomeGenMethods gen = Core.UBC ? new UBCCavebiomesGenMethods(world, coords, world.rand) : new CaveBiomeGenMethods(world, coords, world.rand);

			CaveProfile profile = CaveTypeRegister.getCaveProfile(cave.getBiome(world));
			AbstractDungeonType dungeon = profile.getDungeonForCave(gen, random, cave, (int) chunkscan.surfaceAvg);

			if (dungeon != null && random.nextFloat() < CaveBiomesConfig.dungeonChance){
				System.out.println("Generating dungeon " + dungeon.name + " @ " + cave.getCenter().x + " " + (cave.getCenter().floor+2) + " " + cave.getCenter().z );
				spawned.add(coords);
				
				dungeon.setupForGen(cave);
				float depth = (float) (cave.getAvgFloor()/chunkscan.surfaceAvg);

				for (CavePosition position : cave.getCaveSet()) {
					generateDungeon(gen, random, dungeon, position,depth);
				}
				dungeon.generateCenter(gen, random, cave.getCenter(), depth);
			}
			gen.blocksToSet.setBlockSet();
		}
	}


	public static void generateDungeon(CaveBiomeGenMethods gen, Random random, AbstractDungeonType dungeon, CavePosition pos, float depth){

		
		if (dungeon.shouldPosGen(gen, pos.getFloorPos())){
			dungeon.generateFloor(gen, random, pos.getFloorPos(), depth);
		}
		
		if (dungeon.shouldPosGen(gen, pos.getCeilingPos())){
			dungeon.generateCeiling(gen, random, pos.getCeilingPos(), depth);
		}

		for (BlockPos wallpos : pos.wall){
			if (dungeon.shouldPosGen(gen, wallpos)){
				dungeon.generateWall(gen, random, wallpos, depth, wallpos.getY() - pos.floor);
			}
		}
		for (AdjPos adjpos : pos.adj){
			if (dungeon.shouldPosGen(gen, adjpos)){
				dungeon.generateAdjacentWall(gen, random, adjpos, depth, adjpos.getY()-pos.floor);
			}
		}


		if (random.nextInt(100) < dungeon.ceilingaddonchance+(1-depth)*5 && dungeon.shouldPosGen(gen, pos.getCeilingPos().down()))
		{
			dungeon.generateCeilingAddons(gen, random, pos.getCeilingPos().down(), depth);
		}
		if (random.nextInt(100) < dungeon.flooraddonchance+(1-depth)*5 && dungeon.shouldPosGen(gen,  pos.getFloorPos().up()))
		{
			dungeon.generateFloorAddons(gen, random, pos.getFloorPos().up(), depth);
		}
		if (dungeon.genAir){
			for (BlockPos airpos : pos.getAirPos()){
				if (dungeon.shouldPosGen(gen, airpos)){
					dungeon.generateAir(gen, random, airpos, depth);
				}
			}
		}
	}


	public static CaveListWrapper getLargestCave(World world, ChunkScan chunkscan){
		CaveListWrapper best = null;
		double bestvalue = 1;

		for (CaveListWrapper wrappedcave : chunkscan.caveset.getCaves()){

			double score = wrappedcave.dungeonScore(world, chunkscan.surfaceAvg); 
			if (score > bestvalue){
				best = wrappedcave;
				bestvalue = score;
			}
		}
		return best;
	}




}
