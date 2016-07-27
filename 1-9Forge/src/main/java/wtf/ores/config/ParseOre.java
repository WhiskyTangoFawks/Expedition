package wtf.ores.config;

import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import wtf.api.OreGenAbstract;
import wtf.core.Core;
import wtf.core.blocks.BlockDenseOre;
import wtf.core.config.GameplayConfig;
import wtf.core.init.BlockSets;
import wtf.core.init.WTFBlocks;
import wtf.core.utilities.wrappers.StoneAndOre;
import wtf.core.worldgen.oregenerators.OreGenCaveFloor;
import wtf.core.worldgen.oregenerators.OreGenCloud;
import wtf.core.worldgen.oregenerators.OreGenCluster;
import wtf.core.worldgen.oregenerators.OreGenSingle;
import wtf.core.worldgen.oregenerators.OreGenVanilla;
import wtf.core.worldgen.oregenerators.OreGenVein;
import wtf.ores.OreGenerator;

public class ParseOre {

	public static enum GENTYPE{
		vanilla,
		vein,
		cloud,
		cluster,
		single,
		cave;
	}

	public static enum ARGUMENT{
		oreperchunk, genheightpercentsurface, size, veindimensions, pitch, densitypercent, dimension, genpercentinbiometype, stone;
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

		GENTYPE gentype;

		try {
			gentype = GENTYPE.valueOf(stringArray[0].toLowerCase());
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
		String blockName = stringArray[1].split(":")[1].split("@")[0];
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

		IBlockState stone = Blocks.STONE.getDefaultState();
		
		float pitch = -99F; 

		HashSet<Integer> dimensions = new HashSet<Integer>();
		HashMap<BiomeDictionary.Type, Float> biomeMap = new HashMap<BiomeDictionary.Type, Float>();
		float density = 1F;

		for (int loop = 2; loop < stringArray.length; loop++){
			String[] subStringArray = stringArray[loop].toLowerCase().split("=");

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
		
		switch (gentype){
		case cave:
			oregenerator = new OreGenCaveFloor(blockstate, spawnmin, spawnmax, maxperchunk, minperchunk);
			break;
		case cloud:
			if (size == -1){
				throw new Exception("Ore Config Parsing Exception while trying to parse : " + orestring + " ***** "  + "missing Size argument");
			}
			oregenerator = new OreGenCloud(blockstate, spawnmax, spawnmin, maxperchunk, minperchunk, size);
			break;
		case cluster:
			oregenerator = new OreGenCluster(blockstate, spawnmax, spawnmin, maxperchunk, minperchunk);
			break;
		case single:
			oregenerator = new OreGenSingle(blockstate, spawnmax, spawnmin, maxperchunk, minperchunk);
			break;
		case vanilla:
			if (size == -1){
				throw new Exception("Ore Config Parsing Exception while trying to parse : " + orestring + " ***** "  + "missing Size argument");
			}
			oregenerator = new OreGenVanilla(blockstate, spawnmax, spawnmin, maxperchunk, minperchunk, size);
			break;
		case vein:
			if (length == -1){
				throw new Exception("Ore Config Parsing Exception while trying to parse : " + orestring + " ***** "  + "missing VeinDimensions argument");
			}
			if (pitch == -99F){
				throw new Exception("Ore Config Parsing Exception while trying to parse : " + orestring + " ***** "  + "missing Pitch argument");
			}
			oregenerator = new OreGenVein(blockstate, spawnmax, spawnmin, maxperchunk, minperchunk, length, width, thickness, pitch);
			break;
		default:
			break;
		}
		
		//set up to only do stone background currently, add more here later
		
		Block block = WTFBlocks.registerBlock(new BlockDenseOre(stone, blockstate), "dense_"+blockName);
		if (!GameplayConfig.denseOres){
			BlockSets.stoneAndOre.put(new StoneAndOre(stone, blockstate), block.getDefaultState());
		}
		
		//Optional arguments
		oregenerator.biomeModifier = biomeMap; 
		oregenerator.setVeinDensity(density);
		if (!dimensions.isEmpty()){
			oregenerator.dimension = dimensions;
		}
		
		OreGenerator.oreGenRegister.add(oregenerator);
	}


	@SuppressWarnings("deprecation")
	public static IBlockState getBlockState(String string){
		String[] stringArray = string.split("@");
		Block block = Block.getBlockFromName(stringArray[0]);
		//this is written this way, so it will return null, and can throw the exception back in the main method, which allows it to identify which string throws the exception
		return block == null ? null : block.getStateFromMeta(Integer.parseInt(stringArray[1]));
	}

}
