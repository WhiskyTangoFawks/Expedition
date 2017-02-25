package wtf.config.ore;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.lang3.text.WordUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import wtf.Core;
import wtf.blocks.BlockDenseOre;
import wtf.blocks.BlockDenseOreFalling;
import wtf.blocks.redstone.DenseRedstoneOre;
import wtf.config.AbstractConfig;
import wtf.config.WTFStoneRegistry;
import wtf.init.BlockSets;
import wtf.init.BlockSets.Modifier;
import wtf.init.WTFBlocks;
import wtf.ores.OreGenAbstract;
import wtf.ores.OreReplacer;
import wtf.ores.VanillOreGenCatcher;
import wtf.ores.oregenerators.OreGenCaveFloor;
import wtf.ores.oregenerators.OreGenCloud;
import wtf.ores.oregenerators.OreGenCluster;
import wtf.ores.oregenerators.OreGenSingle;
import wtf.ores.oregenerators.OreGenUnderWater;
import wtf.ores.oregenerators.OreGenVanilla;
import wtf.ores.oregenerators.OreGenVein;
import wtf.utilities.UBC.UBCOreReplacer;
import wtf.utilities.wrappers.StoneAndOre;
import wtf.worldgen.generators.OreGenerator;

public class WTFOresNewConfig extends AbstractConfig{


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

		Configuration config = new Configuration(new File(configPath+"WTFOresConfigV2.cfg"));

		config.load();

		simplexGen = config.get("0 General Options", "Use simplex noise instead of random for ore generation", true).getBoolean();


		String[] defOres = {"minecraft:coal_ore@0 #vein", "minecraft:iron_ore@0 #vein", "minecraft:gold_ore@0 #cloud", "minecraft:lapis_ore@0 #cluster", "minecraft:redstone_ore@0 #vein", "minecraft:emerald_ore@0 #single", "minecraft:diamond_ore@0 #single",
				"minecraft:quartz_ore@0 #cave&cluster", "wtfcore:nitre_ore@0 #cave&single", "wtfcore:oreSandGold@0 #underwater&single", "WTFBlockType:cracked #vein"};

		if (Loader.isModLoaded("tconstruct")){
			defOres = appendArray(defOres, "tconstruct:ore@0 #vein" );
			defOres = appendArray(defOres, "tconstruct:ore@1 #cloud" );
			
		}
		
		String[] oreSet = config.get("1 Master Ore List", "Master list of all ores generated, add ore blocks to this list, then run minecraft, in order to generate their config options", defOres).getStringList();

		HashMap<String, OreDefReg> defPresets = OreConfigHelper.getPresets();

		for (String oreGenString : oreSet){

			if (oreGenString.length() > 1){

				String category = "Config for " +oreGenString;
				
				if (!oreGenString.contains("#")){
					config.get(category, "Generation type missing", "please delete this entry, and include a generation type in the master config string for "+oreGenString);
					continue;
				}
				
				String oreString = oreGenString.split("#")[0].replaceAll("\\s","");
				String genString = oreGenString.split("#")[1].replaceAll("\\s","");
			
				
				//Piece of code for backwards compatibility or previously generated ore strings
				if (oreString == " wtfcore:stone0DecoStatic@2"){
					oreString = "WTFBlockType:cracked #vein";
				}
				
				IBlockState oreState = null;
				Modifier modifier = null;
				
				if (oreString.toLowerCase().contains("wtfblocktype")){
					try {
						modifier = Modifier.valueOf(oreString.split(":")[1].toUpperCase());
						oreState = BlockSets.getTransformedState(Blocks.STONE.getDefaultState(), modifier);
					}
					catch (IllegalArgumentException e){
						Modifier[] types = Modifier.values();
						String gentypestring = "";
						for (Modifier type : types){
							gentypestring += "WTFBlockType:"+type.toString() + ", " ;
						}
						throw new Exception("Ore Config Parsing Exception while trying to parse config for " + oreString  + " ***** "  + oreString +" is not a recognised block modifier type.  Accepted Block modifier types are: " + gentypestring );
					}
				}
				else {
					oreState = getBlockState(oreString);
				}

				if (oreState == null){
					config.get(category, "Block not found", "unable to find block for "+oreGenString);
					break;
				}
				
				

				cancelOres.add(oreState);
				OreDefReg preset = defPresets.get(oreString);

				if (preset == null){
					preset = defPresets.get("minecraft:iron_ore@0");
				}

				
				//Background stones
				String stoneStringList = config.get(category, "0 List of background stones", preset.stoneList).getString().replaceAll("\\s","");

				ArrayList<IBlockState> stoneArray = getBlockStateArray(stoneStringList);
	
				int[] genRange = config.get(category, "2 Generation height range (min % surface height, max % surface height)", preset.genRange).getIntList();
				try {
					if (genRange[1]-genRange[0] < 1){
						throw new Exception();
					}
				}
				catch (Exception e){
					throw new Exception("Ore Config Parsing Exception while trying to parse  : " + oreString + " ***** Generation height range is not valid, max range must be greater than min range");
				}
				
				int[] orePerChunk = config.get(category, "1 Amount of ore to attempt to generate per chunk (min, max)", preset.orePerChunk).getIntList();
				boolean denseBlock = config.get(category, "4 Use dense versions of this ore block", modifier == null ? preset.denseBlock : false).getBoolean();

				String defRegName = modifier == null ? oreString.split(":")[1].split("@")[0] + oreString.split("@")[1] : oreString.split(":")[1];
				String oreBlockName = config.get(category, "0 Block Registry Name (changing this will break existing worlds)", defRegName).getString();
				
				
				OreGenAbstract generator = getGenerator(oreGenString, preset, config, genString, oreState, genRange, orePerChunk, denseBlock);

				String textureLoc = config.get(category, "5 Ore texture", preset.textureLoc == null ? oreState.getBlock().getRegistryName().toString().split(":")[1] : preset.textureLoc).getString();

				int[] overworld = {0};
				int[] dimensionIDs = config.get(category, "3 Dimensions to spawn in", preset.dimensionIDs==null ? overworld : preset.dimensionIDs).getIntList();
				for (int ID : dimensionIDs){
					generator.dimension.add(ID);
				}

				float density = config.get(category, "6 Vein percent density (chance each block will generate or not)", preset.density).getInt()/100F;
				generator.setVeinDensity(density);

				String[] biomeModTags = config.get(category, "7 Percent ore generation in biome type", preset.biomeTags).getString().replaceAll("\\s","").toLowerCase().split(",");
				//The block doesn't get added based on this- but maybe it should?
				//boolean preventPlayerPlacement = config.get(category, "8 Prevent player placement of the ore", true, "prevents infinite abuse of stone mining gameplay tweaks").getBoolean();
				generator.biomeModifier = new HashMap<BiomeDictionary.Type, Float>();

				if (biomeModTags[0] != "" && biomeModTags.length > 0){
					for (String biomestring : biomeModTags){
						if (biomestring.length() > 0){
							float f = Integer.parseInt(biomestring.split("@")[1])/100F;
							generator.biomeModifier.put(getBiomeTypeFromString(biomestring.split("@")[0]), f);
						}
					}
				}

				String[] reqBiomes = config.get(category, "8 Required Biome types", preset.reqBiomeTypes == null ? "" : preset.reqBiomeTypes).getString().replaceAll("\\s","").toLowerCase().split(",");
				if (reqBiomes[0] != "" && reqBiomes.length > 0){

					for (String biomestring : reqBiomes){
						if (biomestring.length() > 0){
							generator.reqBiomeTypes.add(getBiomeTypeFromString(biomestring));
						}
					}
				}

				for (IBlockState stone : stoneArray){
					
					if (modifier != null){
						BlockSets.stoneAndOre.put(new StoneAndOre(stone, oreState), BlockSets.getTransformedState(stone, modifier));
					}
					
					if (denseBlock){

						Block block;
						Block stoneblock = stone.getBlock();
						int meta = stoneblock.getMetaFromState(stone);
						String stoneName = stoneblock.getRegistryName().toString().split(":")[1]+meta;
						String regName = "dense_"+stoneName+ oreBlockName;
						
						String locStoneName =  WordUtils.capitalize(WTFStoneRegistry.stoneReg.get(stone).textureLocation.split("/")[1].replaceAll("_", " "));
						String locOreName = WordUtils.capitalize(textureLoc.replaceAll("_", " "));
						
						//if (Block.getBlockFromName(dense_"+blockName) == null){
						if (oreState.getBlock() != Blocks.REDSTONE_ORE){
							if (stone.getBlock() instanceof BlockFalling){
								block = WTFBlocks.registerBlock(new BlockDenseOreFalling(stone, oreState), regName);
								Core.proxy.writeDenseOreBlockstate(stone, regName, textureLoc, stoneName);
								Core.proxy.addName(regName,  "Dense " + locStoneName + " " + locOreName);
							}
							else {
								block = WTFBlocks.registerBlock(new BlockDenseOre(stone, oreState), regName);
								Core.proxy.writeDenseOreBlockstate(stone, regName, textureLoc, stoneName);
								Core.proxy.addName(regName,  "Dense " + locStoneName + " " + locOreName);
							}
						}
						else {

							block = WTFBlocks.registerBlock(new DenseRedstoneOre(false), regName);
							Core.proxy.writeDenseOreBlockstate(stone, regName, textureLoc, stoneName);
							Core.proxy.addName(regName,  "Dense " + locStoneName + " " + locOreName);
							
							DenseRedstoneOre.denseRedstone_off = block;
							DenseRedstoneOre.denseRedstone_on = WTFBlocks.registerBlock(new DenseRedstoneOre(true), regName+"_on");
							Core.proxy.writeDenseOreBlockstate(stone, regName+"_on", textureLoc, stoneName);
							Core.proxy.addName(regName+"_on",  "Dense " + locStoneName + " " + locOreName);
							
						}

						BlockSets.stoneAndOre.put(new StoneAndOre(stone, oreState), block.getDefaultState());
						//Ore ubification/densification of existing ores- removed because it was crashing
						//new OreReplacer(blockstate.getBlock());
					}
					else if (modifier == null){
						BlockSets.stoneAndOre.put(new StoneAndOre(stone, oreState), oreState);
						if (Core.UBC){
							new UBCOreReplacer(oreState.getBlock());
						}
						else {
							new OreReplacer(oreState.getBlock());
						}
					}
				}

				OreGenerator.oreGenRegister.add(generator);
				VanillOreGenCatcher.vanillaCanceler(oreState);
				Core.coreLog.info("Ore Generator Added for " + oreGenString);
			}

		}

		config.save();
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
			String[] surfacestrings = config.get("Config for " +oreString, "Surfaces in which to generates: floor, ceiling, wall, seperated by a &", preset.surfaces).getString().replaceAll("\\s","").split("&");
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

			int cloudDiameter = config.get("Config for " +oreString, "Cloud diameter", preset.cloudDiameter).getInt();
			return new OreGenCloud(oreState, genRange, orePerChunk, denseBlock, cloudDiameter);

		case cluster:
			return new OreGenCluster(oreState, genRange, orePerChunk, denseBlock);

		case single:
			return new OreGenSingle(oreState, genRange, orePerChunk, denseBlock);

		case underwater:
			secondaryGen =getGenerator("Config for " +oreString, preset, config, genTypeArray[1], oreState, genRange, orePerChunk, denseBlock); 
			return new OreGenUnderWater(secondaryGen, oreState, genRange, orePerChunk, denseBlock);

		case vanilla:
			int blocksPerCluster = config.get("Config for " +oreString, "Blocks per cluster", preset.vanillaBlocksPerCluster).getInt();
			return new OreGenVanilla(oreState, genRange, orePerChunk, denseBlock, blocksPerCluster);

		case vein:
			int[] dimensions = config.get("Config for " +oreString, "Vein dimensions (length,width,vertical thickness)", preset.veinDimensions).getIntList();
			float pitch = (float)config.get("Config for " +oreString, "Average vein pitch (o for horizontal, 1.5 for vertical)", preset.veinPitch).getDouble();
			return new OreGenVein(oreState, genRange, orePerChunk, dimensions, pitch, denseBlock);

		}
		return null;
	}




}
