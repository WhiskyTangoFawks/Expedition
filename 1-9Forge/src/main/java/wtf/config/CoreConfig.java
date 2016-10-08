package wtf.config;

import java.io.File;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.common.config.Configuration;


public class CoreConfig {
	
	public static boolean enableNameGetter;
	
	public static boolean gameplaytweaks;
	public static boolean caveGeneration;
	public static boolean dungeonGeneration;
	public static boolean mobReplacement;
	public static boolean enableOreGen;
	public static boolean enableOverworldGeneration;
	public static boolean appleCore;
	
	public static HashMap<IBlockState, IBlockState> StoneCobble = new HashMap<IBlockState, IBlockState>();
		
	public static Configuration config = new Configuration(new File("config/WTFCore.cfg"));

	public static void loadConfig() throws Exception{
	config.load();
		

		enableNameGetter = config.get("Name Getter", "When a player places a block, print out the blocks name", false).getBoolean();
	
		String section = "Feature Override";
		gameplaytweaks = config.get(section, "Set to false to disable all gameplay tweaks for a vanilla gameplay experience.  This override ALL options set inside WTFGameplay.cfg", true).getBoolean();
		caveGeneration = config.get(section, "Set to false to disable all cave decoration", true).getBoolean();
		dungeonGeneration = config.get(section, "Set to false to disable all dungeon and cave subtype generation", true).getBoolean();
		mobReplacement = config.get(section, "Set to false to spawning of all custom mobs in the world.  Does not disable spawning within custom dungeons", true).getBoolean();
		enableOreGen = config.get(section, "Set to false to disable WTF-Ore generation completely", true).getBoolean();
		enableOverworldGeneration = config.get(section, "Set to false to disable all Overworld generation", true).getBoolean();
		
		String defstone = "minecraft:stone@0 & minecraft:cobblestone@0, minecraft:stone@1 & minecraft:stone@1,"
				+ "minecraft:stone@3 & minecraft:stone@3, minecraft:stone@5 & minecraft:stone@5, "
				+ "minecraft:sandstone@0 & minecraft:sand@0, minecraft:red_sandstone@0 & minecraft:sand@1, minecraft:obsidian@0 & minecraft:obsidian@0";
		String stones = config.get("Stone Sets", "Stone and cobblestone pairings, used for world gen, fracturing, and decorative blocks", defstone).getString();
		
		stones = stones.replaceAll("\\s","");
		
		String[] stoneset = stones.split(",");
		for (String stoneAndCobble : stoneset){
			String[] pair = stoneAndCobble.split("&");
			
			IBlockState stone = getBlockState(pair[0]);
			if (stone == null){
				throw new Exception("Error trying to parse stone types in the core config : no block found for " + pair[0] + "  Turn on the block name getter in the core config, and place the block whose name you want in the world to get the blocks registry name");
			}
			IBlockState cobble = getBlockState(pair[1]);
			if (cobble == null){
				throw new Exception("Error trying to parse stone types in the core config : no block found for " + pair[1] + "  Turn on the block name getter in the core config, and place the block whose name you want in the world to get the blocks registry name");
			}
			
			StoneCobble.put(stone, cobble);
			
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
