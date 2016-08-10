package wtf.core.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import wtf.api.Replacer;
import wtf.cavebiomes.worldgeneration.replacer.LavaReplacer;
import wtf.core.config.GameplayConfig;
import wtf.core.utilities.wrappers.StoneAndOre;
import wtf.core.worldgen.replacers.NonSolidNoReplace;
import wtf.core.utilities.wrappers.StateAndModifier;


public class BlockSets {

	public enum Modifier {
		COBBLE, LAVA_CRUST, MOSSY, WATER_DRIP, LAVA_DRIP, FROZEN
	}

	private static String[] deffall = {"minecraft:dirt@2", "minecraft:cobblestone@1", "minecraft:mossy_cobble@1"};
	public static ArrayList<String> defaultFallingBlocks = new ArrayList<String>(Arrays.asList(deffall));
	public static HashMap<Block, Integer> fallingBlocks = new HashMap<Block, Integer>();

	private static Block[] defOres = {Blocks.IRON_ORE, Blocks.DIAMOND_ORE, Blocks.LAPIS_ORE, Blocks.GOLD_ORE, Blocks.EMERALD_ORE, Blocks.REDSTONE_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.COAL_ORE};
	public static HashSet<Block> oreAndFractures = new HashSet<Block>(Arrays.asList(defOres));

	private static IBlockState[] defOreStates = {Blocks.IRON_ORE.getDefaultState(), Blocks.DIAMOND_ORE.getDefaultState(), Blocks.LAPIS_ORE.getDefaultState(), Blocks.GOLD_ORE.getDefaultState(),
			Blocks.EMERALD_ORE.getDefaultState(), Blocks.REDSTONE_ORE.getDefaultState(), Blocks.LIT_REDSTONE_ORE.getDefaultState(), Blocks.COAL_ORE.getDefaultState()};
	public static HashMap<StoneAndOre, IBlockState> stoneAndOre = new HashMap<StoneAndOre, IBlockState>();

	public static HashMap<Block, Float> explosiveBlocks = new HashMap<Block, Float>();

	public static HashMap<Item, Item> itemReplacer = new HashMap<Item, Item>();

	public static HashMap <Block,Float> explosiveModBlock = new HashMap<Block, Float>();



	//WorldGenHashSets

	private static Block[] listReplaceBlocks = {Blocks.STONE, Blocks.SANDSTONE, Blocks.DIRT, Blocks.GRAVEL, Blocks.SAND, Blocks.AIR, Blocks.LAVA, Blocks.COBBLESTONE, Blocks.FLOWING_LAVA, Blocks.OBSIDIAN, Blocks.WATER, Blocks.FLOWING_WATER, WTFBlocks.icePatch};
	public static HashSet<Block> ReplaceHashset = new HashSet<Block>(Arrays.asList(listReplaceBlocks));

	private static Block[] listSurfaceBlocks = {Blocks.DIRT, Blocks.SAND, Blocks.GRASS, Blocks.STONE, Blocks.GRAVEL, Blocks.CLAY, Blocks.HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY};
	public static HashSet<Block> surfaceBlocks = new HashSet<Block>(Arrays.asList(listSurfaceBlocks));
	
	public static HashSet<Block> treeReplaceableBlocks = new HashSet<Block>();

	
	public static HashSet<Block> nonSolidBlockSet = new HashSet<Block>();


	private static Block[] defMelt = {Blocks.LAVA, Blocks.FLOWING_LAVA, Blocks.FLOWING_WATER, Blocks.FIRE};
	public static HashSet<Block> meltBlocks = new HashSet<Block>(Arrays.asList(defMelt));	

	public static HashMap<Block, Replacer> isNonSolidAndCheckReplacement = new HashMap <Block, Replacer>();
	public static HashMap<Block, Float> blockMiningSpeed = new HashMap <Block, Float>();


	public static HashMap<StateAndModifier, IBlockState> blockTransformer = new HashMap<StateAndModifier, IBlockState>();

	public static IBlockState getTransformedState(IBlockState oldstate, Modifier mod){
		return blockTransformer.get(new StateAndModifier(oldstate, mod));
	}

	public static boolean hasCobble(IBlockState state){
		return blockTransformer.containsKey(new StateAndModifier(state, Modifier.COBBLE));
	}

	private static HashSet<Block> cobble = new HashSet<Block>();
	public static boolean isFractured(IBlockState state){
		return cobble.contains(state.getBlock());
	}

	public static void initBlockSets(){

		
		Iterator<Block> blockIterator = Block.REGISTRY.iterator();
		while (blockIterator.hasNext()){
			Block block = blockIterator.next();
			if (!block.getDefaultState().isBlockNormalCube()){
				nonSolidBlockSet.add(block);
				if (!isNonSolidAndCheckReplacement.containsKey(block)){
					new NonSolidNoReplace(block);
				}
			}
			if (block.getDefaultState().getMaterial() == Material.AIR){
				nonSolidBlockSet.add(block);
				if (!isNonSolidAndCheckReplacement.containsKey(block)){
					new NonSolidNoReplace(block);
				}
			}
			if (block.getDefaultState().getMaterial() == Material.WATER){
				nonSolidBlockSet.add(block);
				if (!isNonSolidAndCheckReplacement.containsKey(block)){
					new NonSolidNoReplace(block);
				}
			}
			if (block.getDefaultState().getMaterial() == Material.LAVA){
				nonSolidBlockSet.add(block);
				if (!isNonSolidAndCheckReplacement.containsKey(block)){
					new NonSolidNoReplace(block);
				}
			}
			if (block.getDefaultState().getMaterial() == Material.SNOW){
				nonSolidBlockSet.add(block);
				if (!isNonSolidAndCheckReplacement.containsKey(block)){
					new NonSolidNoReplace(block);
				}
			}
			
			try{
				if (block.isReplaceable(null, null)){
					treeReplaceableBlocks.add(block);
				}
			}
			catch (Exception e){
				treeReplaceableBlocks.add(block);
			}
			if (block.getDefaultState().getMaterial() == Material.PLANTS){
				treeReplaceableBlocks.add(block);
			}
		}


		explosiveBlocks.put(Blocks.TNT, 4F);
		explosiveBlocks.put(Blocks.REDSTONE_ORE, 2F);
		explosiveBlocks.put(Blocks.LIT_REDSTONE_ORE, 3F);
		explosiveBlocks.put(Blocks.REDSTONE_TORCH, 1F);
		explosiveBlocks.put(Blocks.REDSTONE_BLOCK, 8F);
		//differentiate between lit and unlit redstone wire
		explosiveBlocks.put(Blocks.REDSTONE_WIRE, 0.9F);
		//

		blockTransformer.put(new StateAndModifier(Blocks.STONE.getDefaultState(), Modifier.COBBLE), Blocks.COBBLESTONE.getDefaultState());
		blockTransformer.put(new StateAndModifier(Blocks.SANDSTONE.getDefaultState(), Modifier.COBBLE), Blocks.SAND.getDefaultState());
		blockTransformer.put(new StateAndModifier(Blocks.COBBLESTONE.getDefaultState(), Modifier.MOSSY), Blocks.MOSSY_COBBLESTONE.getDefaultState());
		

		for (Entry<StateAndModifier, IBlockState> entry : blockTransformer.entrySet()){
			if (entry.getKey().modifier == Modifier.COBBLE){
				cobble.add(entry.getValue().getBlock());
			}
		}

		new LavaReplacer(Blocks.LAVA); //replaces lava below y=11 in a biome specific manner

	}
}
