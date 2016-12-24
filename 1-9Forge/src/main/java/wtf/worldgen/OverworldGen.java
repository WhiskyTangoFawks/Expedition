package wtf.worldgen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.Loader;
import wtf.api.PopulationGenerator;
import wtf.config.CoreConfig;
import wtf.config.OverworldGenConfig;
import wtf.utilities.Simplex;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;
import wtf.utilities.wrappers.SurfacePos;
import wtf.worldgen.caves.CaveBiomeGenMethods;
import wtf.worldgen.caves.CaveProfile;
import wtf.worldgen.caves.CaveTypeRegister;
import wtf.worldgen.subbiomes.SubBiome;
import wtf.worldgen.trees.GenTree;
import wtf.worldgen.trees.TreePos;
import wtf.worldgen.trees.TreeTypeGetter;
import wtf.worldgen.trees.TreeVars;
import wtf.worldgen.trees.types.RootsOnly;

public class OverworldGen extends PopulationGenerator{

	Simplex simplex = null;
	Simplex simplex2 = null;
	World prevWorld = null;

	public static boolean RTG = false;

	public static HashMap <Byte, SubBiome> subBiomeRegistry = new HashMap<Byte, SubBiome>();

	boolean streamsSetBiome = Loader.isModLoaded("Streams");
	
	@Override
	public void generate(World world, ChunkCoords chunkcoords, Random random, ChunkScan chunkscan) throws Exception{
		CaveBiomeGenMethods gen = new CaveBiomeGenMethods(world, chunkcoords, random);
		
		if (simplex == null || world.hashCode() != prevWorld.hashCode()){
			simplex = new Simplex((int) world.getSeed());
			simplex2 = new Simplex((int) world.getSeed()*2);
			prevWorld = world;
		}

		byte[] newBiomes = chunkcoords.getChunk(world).getBiomeArray();

		for (int xloop = 0; xloop < 16 ; xloop++){
			for (int zloop = 0; zloop < 16; zloop++){
				int loop = xloop + zloop*16;
				SubBiome sub = subBiomeRegistry.get(newBiomes[loop]);
				Biome biome = world.getBiome(chunkscan.surface[xloop][zloop]);
				
				CaveProfile profile = CaveTypeRegister.getCaveProfile(biome);
				profile.caveShallow.setTopBlock(gen, random, chunkscan.surface[xloop][zloop]);
				
				if (sub != null){
					double x = (xloop+chunkcoords.getWorldX())/sub.scale();
					double z = (zloop+chunkcoords.getWorldZ())/sub.scale();
					if ((simplex.noise(x, z)*0.5+0.5) < sub.freq()){
						newBiomes[loop] = sub.getID();
					}
						//if (streamsSetBiome && BlockSets.riverBlocks.contains(world.getBlockState(chunkscan.surface[xloop][zloop].up()).getBlock())){
							//newBiomes[loop] = (byte)7;
						//}
				}
			}
		}

		gen.blocksToSet.setBlockSet();
		
		if (CoreConfig.enableOverworldGeneration && OverworldGenConfig.genTrees){
			genTrees(world, chunkcoords, random, chunkscan);
		}
	}

	public void genTrees(World world, ChunkCoords chunkcoords, Random random, ChunkScan chunkscan) throws Exception {

		ArrayList<WorldGenerator> shrubgenerators  = new ArrayList<WorldGenerator>();
		for (int loopx = 0; loopx < 4; loopx++){
			for (int loopz = 0; loopz < 4; loopz++){
				
				SurfacePos pos = chunkscan.surface[4*loopx+random.nextInt(4)][4*loopz+random.nextInt(4)];
				for (int loop = 0; loop < 3 && pos.generated; loop++){
					pos = chunkscan.surface[4*loopx+random.nextInt(4)][4*loopz+random.nextInt(4)];
				}
				
				Biome biome = world.getBiome(pos);
				double numTrees = getGenRate(biome);
				double genChance = 16*numTrees/240;

				if (genChance > 0 && !pos.generated && random.nextFloat()<genChance){

					boolean doReplace = customTree(random, pos);

					WorldGenerator oldTree = null; //getTree(random, biome, chunkscan, doReplace);

					for (int loop = 0; loop< 4 && oldTree == null; loop++){
							WorldGenerator tempTree = getTree(random, biome, chunkscan, doReplace);
							if (tempTree instanceof WorldGenShrub){
								shrubgenerators.add(tempTree);
							}
							else {
								oldTree = tempTree;
							}
					}

					TreeVars treeType = TreeTypeGetter.getTree(world, oldTree);

					if (treeType != null){
						if (GenTree.tryGenerate(new TreePos(world, random, chunkscan, pos, treeType))){
							//generation successful
						}
						else {
							//System.out.println("Tree gen failed for " + treeType.getClass().toString());
						}
					}
					else {
						doGen(oldTree, world, chunkscan, pos, random, chunkscan);
					}
				}
			}	
		}

		for (WorldGenerator shrub : shrubgenerators){
			SurfacePos pos = chunkscan.getRandomNotGenerated(world.rand);
			if (pos != null){
				shrub.generate(world, world.rand, pos);
			}
			else {
				break;
			}
		}
	}
	
	public double getGenRate(Biome biome){
		return biome.theBiomeDecorator.treesPerChunk > -1 ? biome.theBiomeDecorator.treesPerChunk : 16;
	}
	

	public WorldGenerator getTree(Random random, Biome biome, ChunkScan chunkscan, boolean doReplace){
		if (biome instanceof SubBiome){
			return ((SubBiome)biome).getTree(chunkscan, doReplace, random);
		}
		return biome.genBigTreeChance(random);
	}

	public boolean customTree(Random random, BlockPos pos){
		double noise = (simplex2.noise(pos.getX()/32, pos.getZ()/32)*0.5 + 0.5); 
		double rand = random.nextFloat(); 
		double noise2 = noise - (noise - OverworldGenConfig.treeReplacementRate)*OverworldGenConfig.simplexTreeScale;	
		return rand < noise2;
	}

	public static void addRoots(World world, BlockPos pos, Random random, ChunkScan scan){
		if (OverworldGenConfig.addRoots){
			IBlockState state = world.getBlockState(pos.up());
			Block block = state.getBlock();
			int blockhash = block.hashCode();
			if (block instanceof BlockLog){
				int size = 0; 
				while (world.getBlockState(pos.up(size)).hashCode() == blockhash){
					size+=3;
				}
				GenTree.genRootsOnly(new TreePos(world, random, scan, pos, new RootsOnly(world, state, size)));
			}
		}
	}

	public static void addRoots(World world, BlockPos pos, Random random, ChunkScan scan, int size){
		if (OverworldGenConfig.addRoots){
			IBlockState state = world.getBlockState(pos.up());
			Block block = state.getBlock();
			if (block instanceof BlockLog){
				GenTree.genRootsOnly(new TreePos(world, random, scan, pos, new RootsOnly(world, state, size)));
			}
		}
	}	

	public void doGen(WorldGenerator tree, World world, ChunkScan chunkscan, BlockPos pos, Random random, ChunkScan scan) throws Exception{
		if (chunkscan.checkGenerated(pos, 2) && chunkscan.canGrowOnCheck(pos, 1) && tree != null){
			world.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
			tree.generate(world, world.rand, pos);
			chunkscan.setGenerated(pos, 4);
			if (OverworldGenConfig.addRoots){
				addRoots(world, pos, random, chunkscan);
			}
		}
	}



}
