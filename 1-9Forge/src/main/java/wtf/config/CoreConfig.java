package wtf.config;

import java.io.File;

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
	
		
	public static Configuration config = new Configuration(new File("config/WTFCore.cfg"));

	public static void loadConfig(){
	config.load();
		

		enableNameGetter = config.get("Name Getter", "When a player places a block, print out the blocks name", false).getBoolean();
	
		String section = "Feature Override";
		gameplaytweaks = config.get(section, "Set to false to disable all gameplay tweaks for a vanilla gameplay experience.  This override ALL options set inside WTFGameplay.cfg", true).getBoolean();
		caveGeneration = config.get(section, "Set to false to disable all cave decoration", true).getBoolean();
		dungeonGeneration = config.get(section, "Set to false to disable all dungeon and cave subtype generation", true).getBoolean();
		mobReplacement = config.get(section, "Set to false to spawning of all custom mobs in the world.  Does not disable spawning within custom dungeons", true).getBoolean();
		enableOreGen = config.get(section, "Set to false to disable WTF-Ore generation completely", true).getBoolean();
		enableOverworldGeneration = config.get(section, "Set to false to disable all Overworld generation", true).getBoolean();
		
		
	config.save();
	}
}
