package wtf.worldgen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenShrub;
import wtf.api.PopulationGenerator;
import wtf.config.CoreConfig;
import wtf.config.OverworldGenConfig;
import wtf.utilities.wrappers.AdjPos;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;
import wtf.utilities.wrappers.SurfacePos;
import wtf.worldgen.trees.GenTree;
import wtf.worldgen.trees.TreePos;
import wtf.worldgen.trees.TreeVars;

public class PopulationDecorator extends PopulationGenerator{

	@Override
	public void generate(World world, ChunkCoords coords, Random random, ChunkScan chunkscan) throws Exception {

		
		CaveBiomeGenMethods gen = new CaveBiomeGenMethods(world, coords, random);
		if (CoreConfig.caveGeneration){
			genCaves(world, gen, coords, random, chunkscan);
		}
		//get cave profile for a block roughlyh in the center of the chunk's surface
		CaveProfile profile = CaveTypeRegister.getCaveProfile(world.getBiomeForCoordsBody(chunkscan.surface[8][8]));
		if (OverworldGenConfig.modifySurface){
			genSurfaceBlocks(profile, gen, random, chunkscan);
		}
		//the tree populator has it's own setblockset, so we run setblockset now
		gen.blocksToSet.setBlockSet();

		if (OverworldGenConfig.genTrees){
			genTrees(world, profile, chunkscan);
		}


		
	}
	
	public static void genCaves(World world, CaveBiomeGenMethods gen, ChunkCoords coords, Random random, ChunkScan chunkscan){
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

				
					cavetype.generateWall(gen, random, pos, pos.getY()/(float)chunkscan.surfaceAvg, pos.getY() - (int)cave.getAvgFloor());
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
					
					cavetype.generateFloor(gen, random, position.getFloorPos(), (float) depth);
					cavetype.generateCeiling(gen, random, position.getCeilingPos(), (float) depth);

					if (random.nextInt(100) < cavetype.ceilingaddonchance+(1-depth)*5)
					{
						cavetype.generateCeilingAddons(gen, random, position.getCeilingPos().down(), (float) depth);
					}
					if (random.nextInt(100) < cavetype.flooraddonchance+(1-depth)*5)
					{
						cavetype.generateFloorAddons(gen, random, position.getFloorPos().up(), (float) depth);
					}


					position.alreadyGenerated = true;
				}
			}
		}
	}

	public static void genSurfaceBlocks(CaveProfile profile, CaveBiomeGenMethods gen, Random random, ChunkScan scan){
		for (SurfacePos[] arrayZ : scan.surface){
			for (SurfacePos pos : arrayZ){
				profile.caveShallow.setTopBlock(gen, random, pos);
			}
		}
	}

	public void genTrees(World world, CaveProfile profile, ChunkScan chunkscan) throws Exception {

		Biome biome = world.getBiomeGenForCoords(new BlockPos(chunkscan.chunkX, 100, chunkscan.chunkZ));
		int numTrees = MathHelper.ceiling_double_int(biome.theBiomeDecorator.treesPerChunk);

		
		ArrayList<WorldGenAbstractTree> shrubgenerators  = new ArrayList<WorldGenAbstractTree>();

		for (int n = 0; n < numTrees; n++){ 

			WorldGenAbstractTree oldTree = biome.genBigTreeChance(world.rand);
			if (oldTree==null){return;} //not sure where this would be getting a null from, but cancel generation if it does, because biome has no trees
			
			if (oldTree instanceof WorldGenShrub){
				shrubgenerators.add(oldTree);
			}
			else {
			TreeVars treeType = profile.caveShallow.getTreeType(world, chunkscan, oldTree);
			if (treeType != null){
				SurfacePos pos = chunkscan.getPosForTreeGeneration(world, treeType);
				
				if (pos != null){
					TreePos	tree = new TreePos(world, world.rand, chunkscan, pos, treeType);
					
					if (GenTree.generate(tree)){
						//success++;
						//System.out.println("generated");
					}
					else {
						//System.out.println("Generation failed");
						//failGen++;
					}
				}
				else {
					//System.out.println("No positions found");
					//no more suitable positions left in the chunk
					//failPos++;
					break;
				}
			}
			
			else if (oldTree != null){
				SurfacePos pos = chunkscan.getRandomNotGenerated(world.rand);
				if (pos != null){
					oldTree.generate(world, world.rand, pos);
				}
				else {
					//no more suitable positions left in the chunk
					break;
				}
				//other ++;
			}

			}
			
			for (WorldGenAbstractTree shrub : shrubgenerators){
				SurfacePos pos = chunkscan.getRandomNotGenerated(world.rand);
				if (pos != null){
					shrub.generate(world, world.rand, pos);
				}
				else {
					break;
				}
			}
		}



	}
	
}
