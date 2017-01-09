package wtf.config;

import java.io.File;
import java.util.HashMap;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockDirt.DirtType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import wtf.Core;
import wtf.init.BlockSets;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.UBC.UBCCompat;
import wtf.utilities.wrappers.StateAndModifier;

public class WTFStoneRegistry extends ConfigMaster {

	static Random rand = new Random();
	public static Configuration config = new Configuration(new File(configPath+"WTFStoneRegistry.cfg"));

	public static HashMap<IBlockState, String> defBlockStateLocations = new HashMap<IBlockState, String>();
	public static HashMap<IBlockState, String> defTextureLocations = new HashMap<IBlockState, String>();
	public static HashMap<IBlockState, String> defCobble = new HashMap<IBlockState, String>();
	public static HashMap<Block, Integer> defSpeed = new HashMap<Block, Integer>();

	public static HashMap<IBlockState, StoneRegEntry> stoneReg = new HashMap<IBlockState, StoneRegEntry>();

	public static void loadOverrideMap(){
		defBlockStateLocations.put(Blocks.STONE.getDefaultState(), "stone#normal");
		defBlockStateLocations.put(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), "andesite#normal");
		defBlockStateLocations.put(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), "diorite#normal");
		defBlockStateLocations.put(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), "granite#normal");
		defBlockStateLocations.put(Blocks.SAND.getDefaultState(), "sand#normal");
		defBlockStateLocations.put(Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND), "red_sand#normal");
		defBlockStateLocations.put(Blocks.SANDSTONE.getDefaultState(), "sandstone#normal");
		defBlockStateLocations.put(Blocks.RED_SANDSTONE.getDefaultState(), "red_sandstone#normal");
		defBlockStateLocations.put(Blocks.DIRT.getDefaultState(), "dirt#normal");
		defBlockStateLocations.put(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.COARSE_DIRT), "coarse_dirt#normal");
		defBlockStateLocations.put(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.PODZOL), "podzol#normal");
		defBlockStateLocations.put(Blocks.OBSIDIAN.getDefaultState(), "obsidian#normal");
		defBlockStateLocations.put(Blocks.GRAVEL.getDefaultState(), "gravel#normal");
		defBlockStateLocations.put(Blocks.NETHERRACK.getDefaultState(), "netherrack#normal");

		defTextureLocations.put(Blocks.STONE.getDefaultState(), "minecraft:blocks/stone");
		defTextureLocations.put(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), "minecraft:blocks/stone_andesite");
		defTextureLocations.put(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), "minecraft:blocks/stone_diorite");
		defTextureLocations.put(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), "minecraft:blocks/stone_granite");
		defTextureLocations.put(Blocks.SAND.getDefaultState(), "minecraft:blocks/sand");
		defTextureLocations.put(Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND), "minecraft:blocks/red_sand");
		defTextureLocations.put(Blocks.SANDSTONE.getDefaultState(), "minecraft:blocks/sandstone_top");
		defTextureLocations.put(Blocks.RED_SANDSTONE.getDefaultState(), "minecraft:blocks/red_sandstone_top");
		defTextureLocations.put(Blocks.DIRT.getDefaultState(), "minecraft:blocks/dirt");
		defTextureLocations.put(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.COARSE_DIRT), "minecraft:blocks/coarse_dirt");
		defTextureLocations.put(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.PODZOL), "minecraft:blocks/podzol");
		defTextureLocations.put(Blocks.OBSIDIAN.getDefaultState(), "minecraft:blocks/obsidian");
		defTextureLocations.put(Blocks.GRAVEL.getDefaultState(), "minecraft:blocks/gravel");
		defTextureLocations.put(Blocks.NETHERRACK.getDefaultState(), "minecraft:blocks/netherrack");
		
		defCobble.put(Blocks.SANDSTONE.getDefaultState(), "minecraft:sand@0");
		defCobble.put(Blocks.RED_SANDSTONE.getDefaultState(), "minecraft:sand@1");
		
		defSpeed.put(Blocks.STONE, 15);
		defSpeed.put(Blocks.SANDSTONE, 30);
		defSpeed.put(Blocks.RED_SANDSTONE, 30);
		if (Core.UBC){
			defSpeed.put(UBCCompat.IgneousStone[0].getBlock(), 5);
			defSpeed.put(UBCCompat.MetamorphicStone[0].getBlock(), 20);
			defSpeed.put(UBCCompat.SedimentaryStone[0].getBlock(), 30);
		}
	}

	public static void loadStoneReg() throws Exception{
		loadOverrideMap();
		config.load();

		String[] defstone = {"minecraft:stone@0", "minecraft:stone@1", "minecraft:stone@3", "minecraft:stone@5","minecraft:sandstone@0", "minecraft:red_sandstone@0", "minecraft:obsidian@0", "minecraft:dirt@0", 
				"minecraft:sand@0", "minecraft:sand@1", "minecraft:gravel@0", "minecraft:netherrack@0"};
				
		if (Core.UBC){
			Core.coreLog.info("Adding UBC stone to the registry- you may need to delete the configuration file to allow it to generate correctly if the config has already generated");
			String[] newDef = new String[defstone.length+UBCCompat.UBCStoneList.length];
			int count = 0;
			for (String string : defstone){
				newDef[count] = string;
				count++;
			}
			for (String string : UBCCompat.UBCStoneList){
				newDef[count] = string;
				count++;
			}
			defstone = newDef;
		}

		
		String[] stoneset = config.get("Master Stone List", "Master list of stone blockstates", defstone).getStringList();

		//String[] stoneset = stoneList.split(",");

		for (String stateString : stoneset){

			IBlockState state = getBlockState(stateString);

			if (state != null){
				String locBlockstate = config.get(stateString, "BlockState resource location", defBlockStateLocations.get(state) == null ? state.getBlock().getRegistryName().toString() : defBlockStateLocations.get(state).toString()).getString();
				String locTexture = config.get(stateString, "Stone texture resource location", defTextureLocations.get(state) == null ? "null" : defTextureLocations.get(state).toString()).getString();


				//Igneous and Metamorphic aren't automatically finding their cobblestone

				String cobblestring = defCobble.get(state);

				if (cobblestring == null){
					Block drop = Block.getBlockFromItem(state.getBlock().getItemDropped(state, rand, 0));
					int meta = state.getBlock().damageDropped(state);

					if (drop != null && drop != state.getBlock()){
						cobblestring = drop.getRegistryName()+"@"+meta;
					}
					else {
						cobblestring = "";
					}
					
				}
				
				cobblestring = config.get (stateString, "Cobblestone version of block", cobblestring).getString();
				
				IBlockState cobblestone = getBlockState(cobblestring);
				
				boolean speleothems =  config.get(stateString, "Generate stalactite and stalagmites", state.getMaterial() ==Material.ROCK).getBoolean();
				boolean staticDeco =  config.get(stateString, "Generate static deco blocks (moss, cracked, ect)", true).getBoolean();
				boolean animDeco =  config.get(stateString, "Generate animated deco blocks (Lava crust, dripping, ect)", state.getMaterial() ==Material.ROCK).getBoolean();
				boolean cracked = config.get(stateString, "Allow cracked version to spawn in world (requires static deco blocks, and cracked stone ore generation)", state.getMaterial() == Material.ROCK).getBoolean();
				boolean frac = false;
				if (CoreConfig.gameplaytweaks){
					int speed = config.get(stateString, "Gameplay: Percentage speed modifier to mine (100% disables)", defSpeed.get(state.getBlock())!= null ? defSpeed.get(state.getBlock()) : 100 ).getInt();
					BlockSets.blockMiningSpeed.put(state, speed/100F);
				
					if (cobblestone != null){
						frac = config.get(stateString, "Gameplay: Fractures into cobblestone variant before breaking", true).getBoolean();
						BlockSets.blockTransformer.put(new StateAndModifier(state, Modifier.COBBLE), cobblestone);
					}
					
				}
				
				stoneReg.put(state, new StoneRegEntry(state, cobblestone, locTexture, locBlockstate, staticDeco, animDeco, speleothems, cracked, frac));
				
				
			}
			else {
				config.get(stateString, "unable to find Block", "").getString();	
			}

		}
		
		//I need a simple way to add blocks to the registry as a default, that default to having their shit off
		//use a check on the def boolean values for the material


		config.save();
	}


}
