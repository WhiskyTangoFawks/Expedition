package wtf.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import wtf.config.OverworldGenConfig;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;
import wtf.worldgen.subbiomes.SubBiome;
import wtf.worldgen.trees.GenTree;
import wtf.worldgen.trees.RTGCompat;
import wtf.worldgen.trees.TreePos;
import wtf.worldgen.trees.types.RootsOnly;

public class RTGOverworldGen extends OverworldGen{

	@Override
	public void genTrees(World world, ChunkCoords chunkcoords, Random random, ChunkScan chunkscan) throws Exception {
		RTG = RTGCompat.RTG(world);
		super.genTrees(world, chunkcoords, random, chunkscan);
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

	@Override
	public WorldGenerator getTree(Random random, Biome biome, ChunkScan chunkscan, boolean doReplace){
		if (biome instanceof SubBiome){
			return ((SubBiome)biome).getTree(chunkscan, doReplace, random);
		}
		else if (!doReplace){
			return RTGCompat.getRTGTree(random, biome);
		}
		return biome.genBigTreeChance(random);
	}


	@Override
	public void doGen(WorldGenerator tree, World world, ChunkScan chunkscan, BlockPos pos, Random random, ChunkScan scan) throws Exception{
		if (chunkscan.checkGenerated(pos, 3) && chunkscan.canGrowOnCheck(pos, 1) && tree != null){

			tree.generate(world, world.rand, pos);
			chunkscan.setGenerated(pos, random.nextInt(2)+2);
			if (OverworldGenConfig.addRoots){
				if (RTGCompat.tryRTGRoots(world, pos, random, chunkscan, tree)){
					//try rtg roots- if that fails do basic roots								
				}
				else {
					addRoots(world, pos, random, chunkscan);
				}
			}
		}
	}

	public double getGenRate(Biome biome){
		return biome.theBiomeDecorator.treesPerChunk > -1 ? biome.theBiomeDecorator.treesPerChunk : 16;
	}
	
}
