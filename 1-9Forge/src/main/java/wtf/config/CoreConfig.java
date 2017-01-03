package wtf.config;

import java.io.File;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockDirt.DirtType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;


public class CoreConfig extends ConfigMaster{
	
	public static boolean enableNameGetter;
	
	public static boolean gameplaytweaks;
	public static boolean caveGeneration;
	public static boolean dungeonGeneration;
	public static boolean mobReplacement;
	public static boolean enableOreGen;
	public static boolean enableOverworldGeneration;
	
	public static boolean rotate180only;
	public static boolean fancyBlockStates;
	public static boolean doResourcePack;
	
	public static Configuration config = new Configuration(new File(configPath+"MasterConfig.cfg"));

	public static void loadConfig() throws Exception{
	config.load();
		

		enableNameGetter = config.get("Name Getter", "When a player places a block, print out the blocks name", false).getBoolean();
	
		rotate180only = config.get("Blockstate Options", "When generating blockstates, create variants with 180 rotation only, setting to false enables 90 and 270 degree rotations.  Requires generating new blockstates, and placing them in a resource pack", true).getBoolean();
		rotate180only = config.get("Blockstate Options", "Use fancy blockstates", false).getBoolean();
		doResourcePack = config.get("Blockstate Options", "Automatically load the WTF-Expedition blockstate resource pack- Try disabling if you're getting a crash on load, and manually load the resource pack when in game", true).getBoolean();

		
		String section = "Feature Override";
		gameplaytweaks = config.get(section, "Set to false to disable all gameplay tweaks for a vanilla gameplay experience.  This override ALL options set inside WTFGameplay.cfg", true).getBoolean();
		caveGeneration = config.get(section, "Set to false to disable all cave decoration", true).getBoolean();
		dungeonGeneration = config.get(section, "Set to false to disable all dungeon and cave subtype generation", true).getBoolean();
		mobReplacement = config.get(section, "Set to false to spawning of all custom mobs in the world.  Does not disable spawning within custom dungeons", true).getBoolean();
		enableOreGen = config.get(section, "Set to false to disable WTF-Ore generation completely", true).getBoolean();
		enableOverworldGeneration = config.get(section, "Set to false to disable all Overworld generation", true).getBoolean();
			

	config.save();
	}
	
	public static IBlockState getBlockState(String string){
		String[] stringArray = string.split("@");
		Block block = Block.getBlockFromName(stringArray[0]);
		//this is written this way, so it will return null, and can throw the exception back in the main method, which allows it to identify which string throws the exception
		return block == null ? null : block.getStateFromMeta(Integer.parseInt(stringArray[1]));
	}


	
}
