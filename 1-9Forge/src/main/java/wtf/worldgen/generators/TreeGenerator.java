package wtf.worldgen.generators;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenerator;
import wtf.config.OverworldGenConfig;
import wtf.utilities.simplex.SimplexHelper;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;
import wtf.utilities.wrappers.SurfacePos;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.trees.TreeGenMethods;
import wtf.worldgen.trees.TreeInstance;
import wtf.worldgen.trees.TreeTypeGetter;
import wtf.worldgen.trees.types.AbstractTreeType;
import wtf.worldgen.trees.types.Shrub;

public class TreeGenerator {

	private static SimplexHelper treeSimplex = new SimplexHelper("TreeSimplex");
	private static HashMap<BlockPos, IBlockState> worldBlockMap = new HashMap<BlockPos, IBlockState>();
	
	public static synchronized void addTreeMapPos(BlockPos pos, IBlockState state){
		worldBlockMap.put(pos, state);
	}
	public static synchronized void removeTreeMapPos(BlockPos pos){
		worldBlockMap.remove(pos);
	}
	public static synchronized IBlockState getTreeMapPos(BlockPos pos){
		return worldBlockMap.get(pos);
	}
	public static boolean shouldTreePosGenerate(World world, Random random, BlockPos pos){
		double noise = (treeSimplex.get2DNoise(world, pos.getX()/32, pos.getZ()/32)); 
		double rand = random.nextFloat(); 
		double noise2 = noise - (noise - OverworldGenConfig.treeReplacementRate)*OverworldGenConfig.simplexTreeScale;	
		return rand < noise2;
	}
	
	public void generate(World world, ChunkCoords chunkcoords, Random random, ChunkScan chunkscan, GeneratorMethods gen){
		
		Queue<Shrub> shrubQ = new LinkedList<Shrub>(); 
		
		for (int loopx = 0; loopx < 4; loopx++){
			for (int loopz = 0; loopz < 4; loopz++){

				SurfacePos pos = chunkscan.surface[4*loopx+random.nextInt(4)][4*loopz+random.nextInt(4)];
				//int failloop = 0;
				//while (pos.generated && failloop < 3){
					//pos = chunkscan.surface[4*loopx+random.nextInt(4)][4*loopz+random.nextInt(4)];
					//failloop++;
				//}
				Biome biome = world.getBiome(pos);
				double numTrees = biome.theBiomeDecorator.treesPerChunk > -1 ? biome.theBiomeDecorator.treesPerChunk : 16;
				double genChance = 16*numTrees/256;
				
				if (random.nextFloat() > genChance || pos.generated || !shouldTreePosGenerate(world, random, pos)){
					continue;
				}
				
				WorldGenerator oldTree = biome.genBigTreeChance(random);
				
				if (oldTree instanceof WorldGenShrub){
						shrubQ.add(new Shrub((WorldGenShrub) oldTree, gen));
				}
				else if (oldTree == null){
					continue;
				}
				AbstractTreeType treeType = TreeTypeGetter.getTree(world, oldTree);
				
				if (treeType != null){
					try {
						
						if (TreeGenMethods.tryGenerate(new TreeInstance(world, random, chunkscan, pos, treeType), gen)){
							
						}
						else {
							//System.out.println("Tree gen failed");
						}
					} 
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		}
		
		while (!shrubQ.isEmpty()){
			shrubQ.poll().generate(world, random, chunkscan.getRandomNotGenerated(random));
		}
		
		
		
		
		
		
		
	}
	
	
	
	
}
