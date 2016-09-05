package wtf.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class OverworldGenConfig{

	public static boolean genTrees;
	public static boolean modifySurface;
	
	public static double mountainFracChunkPercent;
	public static double mountainFracFreq;

	public static double forestMossChunkPercent;
	public static double ForestMossFreq;
	
	public static double mangroveChunkPercent;
	
	public static double treeReplacementRate;

	public static void loadConfig() {

		Configuration config = new Configuration(new File("config/WTFOverworldGenConfig.cfg"));

		genTrees = config.get("Trees", "Use custom big trees instead of default trees", true).getBoolean();
		
		String surface = "Surface Modifications";
		modifySurface = config.get(surface, "Enable all surface modification", true).getBoolean();
		
		mountainFracChunkPercent = config.get(surface, "In mountainous, percentage of chunks which will have cobblestone fractured surfaces", 50).getDouble()/100;
		mountainFracFreq = config.get(surface, "In mountainous, percentage of blocks within the chunk which will be fractured", 20).getDouble()/100;
		
		forestMossChunkPercent = config.get(surface, "In forests, percentage of chunks which will have mossy surfaces", 50).getDouble()/100;
		ForestMossFreq = config.get(surface, "In mossy forests, percentage of blocks within the chunk which will be mossified", 50).getDouble()/100;
		
		mangroveChunkPercent = config.get(surface, "Percentage of swamps that will have mangrove trees instead of swamp trees", 15).getDouble()/100;
		
		treeReplacementRate = config.get(surface, "Percentage of trees generated that this mod will attempt to replace with bit trees", 100).getDouble()/100;
		
		config.save();
		
	}
}
