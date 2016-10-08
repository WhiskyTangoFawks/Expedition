package wtf.ores.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import wtf.Core;
import wtf.blocks.BlockDenseOre;
import wtf.blocks.BlockDenseOreFalling;
import wtf.blocks.redstone.DenseRedstoneOre;
import wtf.config.GameplayConfig;
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
import wtf.utilities.wrappers.StoneAndOre;

public class ParseOre {

	public static enum GENTYPE{
		vanilla,
		vein,
		cloud,
		cluster,
		single,
		cave,
		underwater;
	}

	public static enum ARGUMENT{
		oreperchunk, genheightpercentsurface, size, veindimensions, pitch, densitypercent, dimension, genpercentinbiometype, stone, denseore, surfaces, reqbiometype;
	}


	/** string structure
	 *  first entry MUST be one of the vein types
	 *  second entry MUST be the block
	 * @throws Exception 
	 *
	 */
	public static void parse(String orestring) throws Exception{
		orestring = orestring.replaceAll("\\s","");
		Core.coreLog.info ("WTF-ores: loading from "+  orestring);

		String[] stringArray = orestring.split(",");
		String[] genTypeArray = stringArray[0].split("@");
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
			throw new Exception("Ore Config Parsing Exception while trying to parse : " + orestring + " ***** "  + stringArray[0]+" is not a recognised generation type.  Accepted generation types are: " + gentypestring );
		}


		IBlockState blockstate = getBlockState(stringArray[1]);
		if (!stringArray[1].contains(":")){
			throw new Exception("Ore Config Parsing Exception while trying to parse : " + orestring + " expected a block argument, got this instead " + stringArray[1] + " The second argument of each string MUST be the block and metadata, in the format modID:block@metadata");
		}
		String oreName = stringArray[1].split(":")[1].split("@")[0] + Integer.parseInt(stringArray[1].split(":")[1].split("@")[1]); 
		
		
		if (blockstate == null){
			throw new Exception("Ore Config Parsing Exception while trying to parse : " + orestring + " ***** cound not find block for " + stringArray[1] + " Turn on the block name getter in the core config, and place the block whose name you want in the world to get the blocks registry name");
		}

		float spawnmin = -1F;
		float spawnmax = -1F;

		int minperchunk = -1;
		int maxperchunk = -1;

		int thickness = -1;
		int length = -1;
		int width = -1;
		int size = -1;
		ArrayList<OreGenCaveFloor.surface> surfacelist = new ArrayList<OreGenCaveFloor.surface>();
		ArrayList<BiomeDictionary.Type> reqBiomeTypes = new ArrayList<BiomeDictionary.Type>();

		boolean denseOres = false;
		ArrayList<IBlockState> stones = new ArrayList<IBlockState>();
		
		float pitch = -99F; 

		HashSet<Integer> dimensions = new HashSet<Integer>();
		HashMap<BiomeDictionary.Type, Float> biomeMap = new HashMap<BiomeDictionary.Type, Float>();
		float density = 1F;

		for (int loop = 2; loop < stringArray.length; loop++){
			String[] subStringArray = stringArray[loop].toLowerCase().split("=");
			String[] uncleaned = stringArray[loop].split("=");
			
			ARGUMENT argument;

			try {
				argument = ARGUMENT.valueOf(subStringArray[0]);
			}
			catch (IllegalArgumentException e){
				ARGUMENT[] types = ARGUMENT.values();
				String argstring = "";
				for (ARGUMENT type : types){
					argstring += type.toString() + ", " ;
				}
				throw new Exception("Ore Config Parsing Exception while trying to parse : " + orestring + " ***** "  + subStringArray[0] +" is not a recognised argument.  Recognised arguments are : " + argstring );
			}

			switch (argument){
			case oreperchunk:
				String[] perchunkArray = subStringArray[1].split("&");
				if (perchunkArray.length != 2){ 
					throw new Exception("Ore Config Parsing Exception while trying to parse : " + orestring + " ***** "  + "OrePerChunk argument should only have 2 variable, and as written it has " + perchunkArray.length);
				}
				minperchunk = Integer.parseInt(perchunkArray[0]);
				maxperchunk = Integer.parseInt(perchunkArray[1]);
				break;
			case pitch:
				pitch = Float.parseFloat(subStringArray[1]);
				break;
			case size:
				size = Integer.parseInt(subStringArray[1]);
				break;
			case genheightpercentsurface:
				String[] heightrangeArray = subStringArray[1].split("&");

				if (heightrangeArray.length != 2){ 
					throw new Exception("Ore Config Parsing Exception while trying to parse : " + orestring + " ***** "  + "GenHeightPercentSurface argument should only have 2 variable, and as written it has " + heightrangeArray.length);
				}
				spawnmin = Integer.parseInt(heightrangeArray[0])/100F;
				spawnmax = Integer.parseInt(heightrangeArray[1])/100F;
				break;
			case veindimensions:
				String[] veinArray = subStringArray[1].split("&");

				if (veinArray.length != 3){ 
					throw new Exception("Ore Config Parsing Exception while trying to parse : " + orestring + " ***** "  + "VeinDimensions argument should only have 3 variable, and as written it has " + veinArray.length);
				}

				length = Integer.parseInt(veinArray[0]);
				width =  Integer.parseInt(veinArray[1]);
				thickness = Integer.parseInt(veinArray[2]);
				break;
			case densitypercent :
				density = Integer.parseInt(subStringArray[1])/100F;
				if (density < 0 || density > 1){
					throw new Exception("Ore Config Parsing Exception while trying to parse : " + orestring + " ***** "  + "DensityPercent cannot be set to less than 1, or more than 100, currently set to : " + subStringArray[1]);
				}
				break;
			case dimension:
				dimensions.add(Integer.parseInt(subStringArray[1]));
				break;
			case genpercentinbiometype:
				String[] biomeArray = subStringArray[1].split("@");
				BiomeDictionary.Type biometype;
				try {
					biometype = BiomeDictionary.Type.valueOf(biomeArray[0].toUpperCase());
				} catch (IllegalArgumentException e){
					Type[] types = BiomeDictionary.Type.values();
					String string = "";
					for (Type type : types){
						string += type.toString() + ", " ;
					}
					throw new Exception("Ore Config Parsing Exception while trying to parse : " + orestring + " ***** "  + "Unrecognised Forge BiomeDictionary BiomeType, the available biome types are : " + string);
				}
				float f = Integer.parseInt(biomeArray[1])/100F;
				biomeMap.put(biometype, f);
				break;
			case stone:
				IBlockState stoneBlockState = getBlockState(uncleaned[1]);
				if (blockstate == null){
					throw new Exception("Ore Config Parsing Exception while trying to parse : " + orestring + " ***** cound not find block for " + stringArray[1] + " int the stone section of the config.  Turn on the block name getter in the core config, and place the block whose name you want in the world to get the blocks registry name");
				}
				stones.add(stoneBlockState);
				break;
			case denseore:
				denseOres = Boolean.parseBoolean(subStringArray[1]);
				break;
			case surfaces:
				String[] surfacestrings = subStringArray[1].split("&");
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
						throw new Exception("Ore Config Parsing Exception while trying to parse  : " + orestring + " ***** "  + stringArray[0]+" is not a recognised surface type.  Accepted surface types are: " + gentypestring );
					}
				}
			break;
			case reqbiometype:
				try {
					biometype = BiomeDictionary.Type.valueOf(subStringArray[1].toUpperCase());
				} catch (IllegalArgumentException e){
					Type[] types = BiomeDictionary.Type.values();
					String string = "";
					for (Type type : types){
						string += type.toString() + ", " ;
					}
					throw new Exception("Ore Config Parsing Exception while trying to parse reqBiomeType : " + orestring + " ***** "  + "Unrecognised Forge BiomeDictionary BiomeType, the available biome types are : " + string);
				}
				reqBiomeTypes.add(biometype);
				
				break;
			default:
				ARGUMENT[] types = ARGUMENT.values();
				String string = "";
				for (ARGUMENT type : types){
					string += type.toString() + ", " ;
				}
				throw new Exception("Ore Config Parsing Exception while trying to parse : " + orestring + " ***** "  + "Unrecognised argument.  Valid arguments are : " + string);	
			
				
			}
		}

		if (spawnmin == -1){
			throw new Exception("Ore Config Parsing Exception while trying to parse : " + orestring + " ***** "  + "missing GenHeightPercentSurface argument");
		}
		if (minperchunk == -1){
			throw new Exception("Ore Config Parsing Exception while trying to parse : " + orestring + " ***** "  + "missing OrePerChunk argument");
		}

		OreGenAbstract oregenerator = null;
		
		
		//I need to add a secondary type- so add a split at the @, and then make a new method to call, which has a switch (because I'm gonig to use it on sea floor and riverbed too-
		//		actually-- I can use it size check, anythnig without a secondary specification I can set with the method
		
		if (genTypeArray.length > 1){
			GENTYPE secondaryGenType = null;
			try {
				secondaryGenType = GENTYPE.valueOf(genTypeArray[1].toLowerCase());
			}
			catch (IllegalArgumentException e){
				GENTYPE[] types = GENTYPE.values();
				String gentypestring = "";
				for (GENTYPE type : types){
					gentypestring += type.toString() + ", " ;
				}
				throw new Exception("Ore Config Parsing Exception while trying to parse secondary generation type for : " + orestring + " ***** "  + stringArray[0]+" is not a recognised generation type.  Accepted generation types are: " + gentypestring );
			}

			
			OreGenAbstract secondaryGen=null;
			switch (gentype){
			
			case cave:
				if (surfacelist.size() == 0){
					throw new Exception("Ore Config Parsing Exception while trying to parse string for " + blockstate.getBlock().getLocalizedName()  +  "No surfaces have been set- use the surface=ceiling & wall & floor argument to set a surface of the cave to generate on");
				}
				 secondaryGen = getGenerator(secondaryGenType, blockstate, spawnmax, spawnmin, maxperchunk, minperchunk, length, width, thickness, pitch, denseOres, size);
				oregenerator = new OreGenCaveFloor(secondaryGen, blockstate, spawnmax, spawnmin, maxperchunk, minperchunk, denseOres, surfacelist);
				break;
			case underwater:
				secondaryGen = getGenerator(secondaryGenType, blockstate, spawnmax, spawnmin, maxperchunk, minperchunk, length, width, thickness, pitch, denseOres, size);
				oregenerator = new OreGenUnderWater(secondaryGen, blockstate, spawnmax, spawnmin, maxperchunk, minperchunk, denseOres);
			default:
				break;
			
			}
		}
		else {
			oregenerator = getGenerator(gentype, blockstate, spawnmax, spawnmin, maxperchunk, minperchunk, length, width, thickness, pitch, denseOres, size);
		}
		
		if (stones.size() == 0){
			stones.add(Blocks.STONE.getDefaultState());
		}
		for (IBlockState stone : stones){
			if (denseOres){
				Block block = null;
				String stoneName = stone.getBlock().getRegistryName().toString().split(":")[1]+stone.getBlock().getMetaFromState(stone);
				String blockName = stoneName+oreName;
				if (blockstate.getBlock() != Blocks.REDSTONE_ORE){
					block = stone.getBlock() instanceof BlockFalling ? WTFBlocks.registerBlock(new BlockDenseOreFalling(stone, blockstate), "dense_"+blockName) : WTFBlocks.registerBlock(new BlockDenseOre(stone, blockstate), "dense_"+blockName);
				}
				else {
					
					block = WTFBlocks.registerBlock(new DenseRedstoneOre(false), "dense_"+blockName);
					DenseRedstoneOre.denseRedstone_off = block;
					DenseRedstoneOre.denseRedstone_on = WTFBlocks.registerBlock(new DenseRedstoneOre(true), "dense_"+blockName+"_on");
				}
				BlockSets.stoneAndOre.put(new StoneAndOre(stone, blockstate), block.getDefaultState());
				//Ore ubification/densification of existing ores- removed because it was crashing
				//new OreReplacer(blockstate.getBlock());
			}
			else {
				BlockSets.stoneAndOre.put(new StoneAndOre(stone, blockstate), blockstate);
				new OreReplacer(blockstate.getBlock());
			}
		}
		//Optional arguments
		oregenerator.biomeModifier = biomeMap; 
		oregenerator.setVeinDensity(density);
		if (!dimensions.isEmpty()){
			oregenerator.dimension = dimensions;
		}
		oregenerator.reqBiomeTypes.addAll(reqBiomeTypes);

		OreGenerator.oreGenRegister.add(oregenerator);
	}


	@SuppressWarnings("deprecation")
	public static IBlockState getBlockState(String string){
		String[] stringArray = string.split("@");
		Block block = Block.getBlockFromName(stringArray[0]);
		//this is written this way, so it will return null, and can throw the exception back in the main method, which allows it to identify which string throws the exception
		return block == null ? null : block.getStateFromMeta(Integer.parseInt(stringArray[1]));
	}

	public static OreGenAbstract getGenerator(GENTYPE gentype, IBlockState blockstate, float spawnmax, float spawnmin, int maxperchunk, int minperchunk, int length, 
			int width, int thickness, float pitch, boolean denseOres, int size) throws Exception{
		switch (gentype){
	case cloud:
		if (size == -1){
			throw new Exception("Ore Config Parsing Exception while trying to parse string for " + blockstate.getBlock().getLocalizedName()  + "missing Size argument");
		}
		return new OreGenCloud(blockstate, spawnmax, spawnmin, maxperchunk, minperchunk, size, denseOres);
		
	case cluster:
		return new OreGenCluster(blockstate, spawnmax, spawnmin, maxperchunk, minperchunk, denseOres);

	case single:
		return new OreGenSingle(blockstate, spawnmax, spawnmin, maxperchunk, minperchunk, denseOres);

	case vanilla:
		if (size == -1){
			throw new Exception("Ore Config Parsing Exception while trying to parse string for " + blockstate.getBlock().getLocalizedName()  +  "missing Size argument");
		}
		return new OreGenVanilla(blockstate, spawnmax, spawnmin, maxperchunk, minperchunk, size, denseOres);

	case vein:
		if (length == -1){
			throw new Exception("Ore Config Parsing Exception while trying to parse string for " + blockstate.getBlock().getLocalizedName()  +  "missing VeinDimensions argument");
		}
		if (pitch == -99F){
			throw new Exception("Ore Config Parsing Exception while trying to parse string for " + blockstate.getBlock().getLocalizedName()  +  "missing Pitch argument");
		}
		return new OreGenVein(blockstate, spawnmax, spawnmin, maxperchunk, minperchunk, length, width, thickness, pitch, denseOres);
	
	default:
		// the gen type error is thrown when attempting to parse it
		return null;
		
	}
	}
	
}
