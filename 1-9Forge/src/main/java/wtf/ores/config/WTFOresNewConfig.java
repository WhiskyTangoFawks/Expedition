package wtf.ores.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import wtf.blocks.BlockDenseOre;
import wtf.blocks.BlockDenseOreFalling;
import wtf.blocks.redstone.DenseRedstoneOre;
import wtf.init.BlockSets;
import wtf.init.WTFBlocks;
import wtf.ores.OreGenAbstract;
import wtf.ores.OreGenerator;
import wtf.ores.OreReplacer;
import wtf.ores.oregenerators.OreGenCaveFloor;
import wtf.ores.oregenerators.OreGenCloud;
import wtf.ores.oregenerators.OreGenCluster;
import wtf.ores.oregenerators.OreGenSingle;
import wtf.ores.oregenerators.OreGenUnderWater;
import wtf.ores.oregenerators.OreGenVanilla;
import wtf.ores.oregenerators.OreGenVein;
import wtf.utilities.BlockstateWriter;
import wtf.utilities.wrappers.StoneAndOre;

public class WTFOresNewConfig {
	public static boolean rotate180only;

	public static HashSet<IBlockState> cancelOres = new HashSet<IBlockState>(); 
	
	public static boolean simplexGen;

	public static enum GENTYPE{
		vanilla,
		vein,
		cloud,
		cluster,
		single,
		cave,
		underwater;
	}

	public static void loadConfig() throws Exception {
		
		Configuration config = new Configuration(new File("config/WTFOresConfigV2.cfg"));
	
		config.load();

		simplexGen = config.get("0 General Options", "Use simplex noise instead of random for ore generation", true).getBoolean();
		rotate180only = config.get("0 Blockstate Options", "When generating new blockstates, create variants with 180 rotation only, setting to false enables 90 adn 270 degree rotations.  Requires generating new blockstates, and placing them in a resource pack", true).getBoolean();


		/*
		 * 		if (Loader.isModLoaded("tconstruct")){
			boolean tconOres = config.get(defEnableMod, "Mod Added Ore : Tinker's Construct Cobalt and Ardite", true).getBoolean();
			String cobalt = config.get(defModOre, "Tinker's Construct Cobalt Generation", "vein, tconstruct:ore@0, stone=minecraft:netherrack@0, denseOre=true, orePerChunk=-30 & 60, VeinDimensions=16 & 1 & 1, pitch = 0.45,"
					+ " GenHeightPercentSurface=5 & 95, dimension=-1").getString();
			if (tconOres){
				ParseOre.parse(cobalt);
			}
			String ardite = config.get(defModOre, "Tinker's Construct Ardite Generation", "cloud, tconstruct:ore@1, stone=minecraft:netherrack@0, denseOre=true, orePerChunk=-30 & 60, "
					+ " GenHeightPercentSurface=5 & 95, size=16, DensityPercent=15, dimension=-1").getString();
			if (tconOres){
				ParseOre.parse(ardite);
			}
			
			
		 */
		
		String defOres = "minecraft:coal_ore@0#vein, minecraft:iron_ore@0#vein, minecraft:gold_ore@0#cloud, minecraft:lapis_ore@0#cluster, minecraft:redstone_ore@0#vein, minecraft:emerald_ore@0#single, minecraft:diamond_ore@0#single,"
				+ " wtfcore:nitre_ore@0#cave&single, wtfcore:oreSandGold@0#underwater&single, wtfcore:stone0DecoStatic@2#vein";
		
		String oreList = config.get("1 Master Ore List", "Master list of all ores generated, add ore blocks to this list, then run minecraft, in order to generate their config options", defOres).getString().replaceAll("\\s","");

		HashMap<String, OreDefReg> defPresets = new HashMap<String, OreDefReg>(); 
		String defStone = "minecraft:stone@0";

		defPresets.put("minecraft:coal_ore@0", new OreDefReg(defStone+", minecraft:gravel@0", "vein", new int[]{20, 120}, new int[]{60, 220}, true).setDensity(75)
				.setVeinDimensions(new int[]{8,8,1}).setVeinPitch(1.5F).setBiomeTags("genPercentInBiomeType=swamp@150, genPercentInBiomeType=hot@50")); 

		defPresets.put("minecraft:iron_ore@0", new OreDefReg(defStone, "vein", new int[]{10, 105}, new int[]{30, 120}, true).setDensity(50)
				.setVeinDimensions(new int[]{12,2,2}).setVeinPitch(1.5F).setBiomeTags("genPercentInBiomeType=mountain@150, genPercentInBiomeType=savanna@50")); 
		
		defPresets.put("minecraft:gold_ore@0", new OreDefReg(defStone, "cloud", new int[]{5, 45}, new int[]{-12, 20}, true).setDensity(10).setCloudDiameter(14)
				.setBiomeTags("genPercentInBiomeType=river@150, genPercentInBiomeType=forest@50")); 

		defPresets.put("minecraft:lapis_ore@0", new OreDefReg(defStone, "cluster", new int[]{1, 50}, new int[]{1, 5}, true).setBiomeTags("genPercentInBiomeType=ocean@150, genPercentInBiomeType=dry@50")); 
		
		defPresets.put("minecraft:redstone_ore@0", new OreDefReg(defStone, "vein", new int[]{5, 60}, new int[]{10, 38}, true)
				.setVeinDimensions(new int[]{16,1,1}).setVeinPitch(0F).setBiomeTags("genPercentInBiomeType=sandy@150, genPercentInBiomeType=wet@50"));
		
		defPresets.put("minecraft:emerald_ore@0", new OreDefReg(defStone, "single", new int[]{1, 35}, new int[]{-10, 10}, true).setBiomeTags("genPercentInBiomeType=hills@150, genPercentInBiomeType=wet@50"));
		
		defPresets.put("minecraft:diamond_ore@0", new OreDefReg(defStone+", minecraft:obsidian@0", "cluster", new int[]{1, 25}, new int[]{-17, 23}, true).setDensity(50).setBiomeTags("genPercentInBiomeType=jungle@150, genPercentInBiomeType=swamp@50")); 
		
		defPresets.put("minecraft:quartz_ore@0", new OreDefReg("minecraft:netherrack@0", "cave&cluster", new int[]{5, 95}, new int[]{60, 120}, true).setDensity(75).setDimensionIDs(new int[]{-1}).setSurfaces("floor,ceiling,wall"));
		
		defPresets.put("wtfcore:nitre_ore@0", new OreDefReg(defStone, "cave@single", new int[]{15, 95}, new int[]{-10, 10}, true).setSurfaces("floor"));
		
		defPresets.put("wtfcore:oreSandGold@0", new OreDefReg("minecraft:sand@0", "underwater@single", new int[]{90, 110}, new int[]{-10, 10}, true).setTextureLoc("gold_ore").setBiomeTags("reqBiomeType=river"));
		
		defPresets.put("wtfcore:stone0DecoStatic@2", new OreDefReg(defStone, "cave@single", new int[]{10, 110}, new int[]{-25, 110}, false).setSurfaces("ceiling, wall").setVeinDimensions(new int[]{10,2,1}).setVeinPitch(0).setDensity(50));
		
		String[] oreSet = oreList.split(",");

		for (String oreGenString : oreSet){

			String oreString = oreGenString.split("#")[0];
			String genString = oreGenString.split("#")[1];
			
			IBlockState oreState = getBlockState(oreString);
			
			if (oreState == null){
				config.get("Config for " +oreGenString, "Block not found", "unable to find block for "+oreGenString);
				break;
			}
			
			cancelOres.add(oreState);
			OreDefReg preset = defPresets.get(oreString);

			if (preset == null){
				System.out.println("No preset found for " + oreGenString);
			}

			//TODO
			//Add a random getter if == null

			//Background stones
			String stoneStringList = config.get("Config for " +oreGenString, "0 List of background stones", preset.stoneList).getString().replaceAll("\\s","");
			ArrayList<IBlockState> stoneArray = getBlockStateArray(stoneStringList);

			int[] genRange = config.get("Config for " +oreGenString, "2 Generation height range (min % surface height, max % surface height)", preset.genRange).getIntList();
			int[] orePerChunk = config.get("Config for " +oreGenString, "1 Amount of ore to attempt to generate per chunk (min, max)", preset.orePerChunk).getIntList();
			boolean denseBlock = config.get("Config for " +oreGenString, "4 Use dense versions of this ore block", preset.denseBlock).getBoolean();

			OreGenAbstract generator = getGenerator(oreGenString, preset, config, genString, oreState, genRange, orePerChunk, denseBlock);
		
			String textureLoc = config.get("Config for " +oreGenString, "5 Ore texture", preset.textureLoc == null ? oreState.getBlock().getRegistryName().toString().split(":")[1] : preset.textureLoc).getString();
			
			int[] overworld = {0};
			int[] dimensionIDs = config.get("Config for " +oreGenString, "3Dimensions to spawn in", preset.dimensionIDs==null ? overworld : preset.dimensionIDs).getIntList();
			for (int ID : dimensionIDs){
				generator.dimension.add(ID);
			}
			
			float density = config.get("Config for " +oreGenString, "6 Vein percent density (chance each block will generate or not)", preset.density).getInt()/100F;
			generator.setVeinDensity(density);

			String[] biomeTags = config.get("Config for " +oreGenString, "7 Biome tags", preset.biomeTags).getString().replaceAll("\\s","").toLowerCase().split(",");

			//biome tags?
			//required biomes?

			for (IBlockState stone : stoneArray){
				if (denseBlock){
					
					Block block = null;
					Block stoneblock = stone.getBlock();
					int meta = stoneblock.getMetaFromState(stone);
					String stoneName = stoneblock.getRegistryName().toString().split(":")[1]+meta;
					
					String blockName = stoneName+ oreString.split(":")[1].split("@")[0] + Integer.parseInt(oreString.split(":")[1].split("@")[1]);
					
					if (oreState.getBlock() != Blocks.REDSTONE_ORE){
						block = stone.getBlock() instanceof BlockFalling ? WTFBlocks.registerBlock(new BlockDenseOreFalling(stone, oreState), "dense_"+blockName) : WTFBlocks.registerBlock(new BlockDenseOre(stone, oreState), "dense_"+blockName);
					}
					else {
						
						block = WTFBlocks.registerBlock(new DenseRedstoneOre(false), "dense_"+blockName);
						DenseRedstoneOre.denseRedstone_off = block;
						DenseRedstoneOre.denseRedstone_on = WTFBlocks.registerBlock(new DenseRedstoneOre(true), "dense_"+blockName+"_on");
						BlockstateWriter.writeDenseOreBlockstate(stone, "dense_"+blockName+"_on", textureLoc, stoneName);
					}
					
					BlockstateWriter.writeDenseOreBlockstate(stone, "dense_"+blockName, textureLoc, stoneName);
					
					BlockSets.stoneAndOre.put(new StoneAndOre(stone, oreState), block.getDefaultState());
					//Ore ubification/densification of existing ores- removed because it was crashing
					//new OreReplacer(blockstate.getBlock());
				}
				else {
					BlockSets.stoneAndOre.put(new StoneAndOre(stone, oreState), oreState);
					new OreReplacer(oreState.getBlock());
				}
			}

			OreGenerator.oreGenRegister.add(generator);
		}


		
		config.save();
	}

	public static IBlockState getBlockState(String string){
		String[] stringArray = string.split("@");
		Block block = Block.getBlockFromName(stringArray[0]);
		
		if (block == null){
			try {
				throw new Exception("Unable to find block for " + stringArray[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		IBlockState state = block.getStateFromMeta(Integer.parseInt(stringArray[1]));
		if (state == null){
			try {
				throw new Exception("Invalid blockstate for block and meta " + string);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return state; 
	}

	public static ArrayList<IBlockState> getBlockStateArray(String string){
		ArrayList<IBlockState> list = new ArrayList<IBlockState>();
		String[] stringArray = string.split(",");
		for (String substring :stringArray){
			//TODO
			//Add UBC block integration here for subtypes
			list.add(getBlockState(substring));
		}
		return list;
	}

	public static OreGenAbstract getGenerator (String oreString, OreDefReg preset, Configuration config, String genString, IBlockState oreState, int[] genRange, int[] orePerChunk, boolean denseBlock) throws Exception{

		//Parse the gen type string
		String[] genTypeArray = genString.split("&");
		GENTYPE gentype;

		try {
			gentype = GENTYPE.valueOf(genTypeArray[0].toLowerCase());
		}
		catch (IllegalArgumentException e){
			GENTYPE[] types = GENTYPE.values();
			String gentypestring = "";
			for (GENTYPE type : types){
				gentypestring += type.toString() + ", " ;
			}
			throw new Exception("Ore Config Parsing Exception while trying to parse config for " + oreString  + " ***** "  + genString +" is not a recognised generation type.  Accepted generation types are: " + gentypestring );
		}

		OreGenAbstract secondaryGen = null;

		//Handle the gen type string in a switch
		switch (gentype){
		case cave:
			//Get the surfaces for gen
			ArrayList<OreGenCaveFloor.surface> surfacelist = new ArrayList<OreGenCaveFloor.surface>();
			String[] surfacestrings = config.get(oreString, "Surfaces in which to generates: floor, ceiling, wall", preset.surfaces).getString().split("&");
			for (String string : surfacestrings){
				try {
					surfacelist.add(OreGenCaveFloor.surface.valueOf(string.toLowerCase()));
				}
				catch (IllegalArgumentException e){
					OreGenCaveFloor.surface[] types = OreGenCaveFloor.surface.values();
					String gentypestring = "";
					for (OreGenCaveFloor.surface type : types){
						gentypestring += type.toString() + ", " ;
					}
					throw new Exception("Ore Config Parsing Exception while trying to parse  : " + oreString + " ***** "  + string+" is not a recognised surface type.  Accepted surface types are: " + gentypestring );
				}
			}

			//Get the secondary generation type
			secondaryGen =getGenerator(oreString, preset, config, genTypeArray[1], oreState, genRange, orePerChunk, denseBlock); 
			return new OreGenCaveFloor(secondaryGen, oreState, genRange, orePerChunk, denseBlock, surfacelist);

		case cloud:

			int cloudDiameter = config.get(oreString, "Cloud diameter", preset.cloudDiameter).getInt();
			return new OreGenCloud(oreState, genRange, orePerChunk, denseBlock, cloudDiameter);

		case cluster:
			return new OreGenCluster(oreState, genRange, orePerChunk, denseBlock);

		case single:
			return new OreGenSingle(oreState, genRange, orePerChunk, denseBlock);

		case underwater:
			secondaryGen =getGenerator(oreString, preset, config, genTypeArray[1], oreState, genRange, orePerChunk, denseBlock); 
			return new OreGenUnderWater(secondaryGen, oreState, genRange, orePerChunk, denseBlock);

		case vanilla:
			int blocksPerCluster = config.get(oreString, "Blocks per cluster", preset.vanillaBlocksPerCluster).getInt();
			return new OreGenVanilla(oreState, genRange, orePerChunk, denseBlock, blocksPerCluster);

		case vein:
			int[] dimensions = config.get(oreString, "Vein dimensions (length,width,vertical thickness)", preset.veinDimensions).getIntList();
			float pitch = (float)config.get(oreString, "Average vein pitch (o for horizontal, 1.5 for vertical)", preset.veinPitch).getDouble();
			return new OreGenVein(oreState, genRange, orePerChunk, dimensions, pitch, denseBlock);

		}
		return null;
	}


}
