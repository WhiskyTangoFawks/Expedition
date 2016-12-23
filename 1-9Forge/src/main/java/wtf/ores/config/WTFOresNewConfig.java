package wtf.ores.config;

import java.io.File;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;

public class WTFOresNewConfig {
public static boolean rotate180only;
	
	
	public static boolean simplexGen;
	
	public static HashMap<String, String> defOres = new HashMap<String, String>();
	
	public static void loadDef(){
		
		
	}
	
	public static void loadConfig() throws Exception {

		Configuration config = new Configuration(new File("config/WTFOresConfig.cfg"));

		config.load();
		
		simplexGen = config.get("Gen Options", "Use simplex noise instead of random for ore generation", true).getBoolean();
		rotate180only = config.get("Ore Blockstate Generation", "When generating new blockstates, create variants with 180 rotation only, setting to false enables 90 adn 270 degree rotations.  Requires generating new blockstates, and placing them in a resource pack", true).getBoolean();
		
		String defOres = "minecraft:coal_ore@0, minecraft:iron_ore@0, minecraft:iron_ore@0, minecraft:gold_ore@0, minecraft:lapis_ore@0, minecraft:redstone_ore@0, minecraft:emerald_ore@0, minecraft:diamond_ore@0, wtfcore:nitre_ore@0, wtfcore:oreSandGold@0, wtfcore:stone0DecoStatic@2";
		String oreList = config.get("Master Ore List", "Master list of all ores generated, add ore blocks to this list, then run minecraft, in order to generate their config options", defOres).getString().replaceAll("\\s","");
		

		
		String[] oreSet = oreList.split(",");

		for (String string : oreSet){
			IBlockState oreState = getBlockState(string);
			if (oreState != null){
				//config.get(string, ", defaultValue)
			}
			else {
				//config.get(string, key, defaultValue)
			}
		
		
		
		}
		config.save();
	}
		
		public static IBlockState getBlockState(String string){
			String[] stringArray = string.split("@");
			Block block = Block.getBlockFromName(stringArray[0]);
			//this is written this way, so it will return null, and can throw the exception back in the main method, which allows it to identify which string throws the exception
			return block == null ? null : block.getStateFromMeta(Integer.parseInt(stringArray[1]));
		}
}
