package wtf.worldgen.trees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.Loader;
import rtg.world.WorldTypeRTG;
import rtg.world.biome.realistic.RealisticBiomeBase;
import rtg.world.gen.feature.tree.rtg.TreeRTG;
import wtf.utilities.wrappers.ChunkScan;
import wtf.worldgen.OverworldGen;



public class RTGCompat {

	public static boolean RTG(World world){
		return Loader.isModLoaded("RTG") && world.getWorldInfo().getTerrainType() instanceof WorldTypeRTG;
	}

	public static TreeRTG getRTGTree(Random random, Biome biome){
		RealisticBiomeBase rb = RealisticBiomeBase.getBiome(Biome.getIdForBiome(biome));
		ArrayList<TreeRTG> biomeTrees = rb.rtgTrees;
		if (biomeTrees.size() > 0) {
			TreeRTG rtgtree = biomeTrees.get(random.nextInt(biomeTrees.size()));
			// Set the trunk size if min/max values have been set.

			int size = rtgtree.maxTrunkSize-rtgtree.minTrunkSize > 4 ? rtgtree.maxTrunkSize-rtgtree.minTrunkSize : 4;
			rtgtree.trunkSize = random.nextInt(size)+rtgtree.minTrunkSize;
			//System.out.println("Tree " + rtgtree.getClass().toString() + rtgtree.trunkSize);

			size = rtgtree.maxCrownSize-rtgtree.minCrownSize;
			rtgtree.crownSize = size > 0 ? random.nextInt(size)+rtgtree.minCrownSize : 2;

			if (rtgtree.noLeaves){
				rtgtree.noLeaves=false;
			}

			return rtgtree;
		}
		return null;
	}

	public static boolean tryRTGRoots(World world, BlockPos pos, Random random, ChunkScan chunkscan, WorldGenerator oldTree){
		if (OverworldGen.RTG && oldTree instanceof TreeRTG){
			OverworldGen.addRoots(world, pos, random, chunkscan, ((TreeRTG)oldTree).trunkSize);
			return true;
		}
		return false;
	}
	static ArrayList<TreeRTG> rtgtrees = new ArrayList<TreeRTG>();

	public static WorldGenerator getNonSpruceRTG(Biome parentBiome, Random random, Boolean doReplace ){
		if (rtgtrees.size() < 1){
			RealisticBiomeBase rb = RealisticBiomeBase.getBiome(Biome.getIdForBiome(parentBiome));
			ArrayList<TreeRTG> biomeTrees = rb.rtgTrees;

			IBlockState[] leaflist = {Blocks.LEAVES.getDefaultState(), Blocks.LEAVES.getStateFromMeta(2), Blocks.LEAVES2.getStateFromMeta(1)}; 
			HashSet<IBlockState> leaves = new HashSet<IBlockState>(Arrays.asList(leaflist));

			for (TreeRTG rtgtree : biomeTrees){
				if (leaves.contains(rtgtree.leavesBlock)){

					int size = rtgtree.maxTrunkSize-rtgtree.minTrunkSize > 4 ? rtgtree.maxTrunkSize-rtgtree.minTrunkSize : 4;
					rtgtree.trunkSize = random.nextInt(size)+rtgtree.minTrunkSize;
					//System.out.println("Tree " + rtgtree.getClass().toString() + rtgtree.trunkSize);

					size = rtgtree.maxCrownSize-rtgtree.minCrownSize;
					rtgtree.crownSize = size > 0 ? random.nextInt(size)+rtgtree.minCrownSize : 2;
				}
				if (rtgtree.noLeaves){
					rtgtree.noLeaves=false;
				}
				rtgtrees.add(rtgtree);
			}
		}

		return (rtgtrees.get(random.nextInt(rtgtrees.size())));
	}

}
