package wtf.worldgen.trees;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenShrub;
import wtf.api.PopulationGenerator;
import wtf.config.OverworldGenConfig;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;
import wtf.utilities.wrappers.SurfacePos;

public class TreePopulator {


	//@Override
	public void generate(World world, ChunkCoords chunkcoords, Random random, ChunkScan surface) throws Exception {
		genTrees(world, surface, random);

	}



	public void genTrees(World world, ChunkScan chunkscan, Random random) throws Exception {

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
				TreeVars treeType = TreeTypeGetter.getTree(world, oldTree);
				if (treeType != null && random.nextFloat() < OverworldGenConfig.treeReplacementRate){
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
