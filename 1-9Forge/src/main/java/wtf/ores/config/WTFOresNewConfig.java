package wtf.ores.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import wtf.ores.OreGenAbstract;
import wtf.ores.config.ParseOre.GENTYPE;
import wtf.ores.oregenerators.OreGenCaveFloor;
import wtf.ores.oregenerators.OreGenCloud;
import wtf.ores.oregenerators.OreGenCluster;
import wtf.ores.oregenerators.OreGenSingle;
import wtf.ores.oregenerators.OreGenUnderWater;
import wtf.ores.oregenerators.OreGenVanilla;
import wtf.ores.oregenerators.OreGenVein;

public class WTFOresNewConfig {
public static boolean rotate180only;
	
	
	public static boolean simplexGen;
	
	public static HashMap<String, String> defOres = new HashMap<String, String>();
	
	public static void loadDef(){
		
		
	}
	
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

		Configuration config = new Configuration(new File("config/WTFOresConfig.cfg"));

		config.load();
		
		simplexGen = config.get("Gen Options", "Use simplex noise instead of random for ore generation", true).getBoolean();
		rotate180only = config.get("Ore Blockstate Generation", "When generating new blockstates, create variants with 180 rotation only, setting to false enables 90 adn 270 degree rotations.  Requires generating new blockstates, and placing them in a resource pack", true).getBoolean();
		
		String defOres = "minecraft:coal_ore@0#vein, minecraft:iron_ore@0#vein, minecraft:gold_ore@0#cloud, minecraft:lapis_ore@0#cluster, minecraft:redstone_ore@0#vein, minecraft:emerald_ore@0#single, minecraft:diamond_ore@0#single,"
				+ " wtfcore:nitre_ore@0#cave&single, wtfcore:oreSandGold@0#underwater&single, wtfcore:stone0DecoStatic@2#vein";
		String oreList = config.get("Master Ore List", "Master list of all ores generated, add ore blocks to this list, then run minecraft, in order to generate their config options", defOres).getString().replaceAll("\\s","");
		
		HashMap<IBlockState, OreDefReg> defOreSetups = new HashMap<IBlockState, OreDefReg>(); 
		
		
		
		//mandatory for all: genStyle, gen range, dense, orePerChunk, texture, biome tags, background stone, texture
		//size, veindimensions, pitch, density, dimension
		//cave: surfaces
		//req biome type
		
		String[] oreSet = oreList.split(",");

		for (String oreString : oreSet){
			
			IBlockState oreState = getBlockState(oreString.split("#")[0]);
			
			if (oreState == null){
				config.get(oreString, "Block not found", "unable to find block for "+oreString);
				break;
			}
			OreDefReg preset = defOreSetups.get(oreState);
			
			//vein type
			//vein sub options
			String genType = oreString.split("#")[1];
					
			String[] genTypeArray = genType.split("&");
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
				throw new Exception("Ore Config Parsing Exception while trying to parse config for " + oreString  + " ***** "  + genType +" is not a recognised generation type.  Accepted generation types are: " + gentypestring );
			}

			switch (gentype){
			case cave:
				//First, get the secondary generation type
				try {
					gentype = GENTYPE.valueOf(genTypeArray[1].toLowerCase());
				}
				catch (IllegalArgumentException e){
					GENTYPE[] types = GENTYPE.values();
					String gentypestring = "";
					for (GENTYPE type : types){
						gentypestring += type.toString() + ", " ;
					}
					throw new Exception("Ore Config Parsing Exception while trying to parse secondary gen type for " + oreString  + " ***** "  + genType +" is not a recognised generation type.  Accepted generation types are: " + gentypestring );
				}
				//next- generate options based on that secondary gen type- probably eaasiest to do if the options are called for from outer methods
				
				break;
			case cloud:
				break;
			case cluster:
				break;
			case single:
				break;
			case underwater:
				break;
			case vanilla:
				break;
			case vein:
				break;
						
			}
			
			//TODO
			//Add a random getter if == null
				
				//background stones
				String stoneStringList = config.get(oreString, "List of background stones", preset.stoneList).getString();
				ArrayList<IBlockState> stoneArray = getBlockStateArray(stoneStringList);
				
							

				
				
			//height percent range
			//ore per chunk
			//texture
			//dimension
			//boolean density
			
				//biome tags?
				//required biomes?
				
				
				
				//config.get(string, ", defaultValue)
			}
	
		
		
		config.save();
	}
		
		public static IBlockState getBlockState(String string){
			String[] stringArray = string.split("@");
			Block block = Block.getBlockFromName(stringArray[0]);
			//this is written this way, so it will return null, and can throw the exception back in the main method, which allows it to identify which string throws the exception
			return block == null ? null : block.getStateFromMeta(Integer.parseInt(stringArray[1]));
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
		
		public OreGenAbstract getGenerator (String genString, IBlockState oreState){
			
		}
		
		
}
