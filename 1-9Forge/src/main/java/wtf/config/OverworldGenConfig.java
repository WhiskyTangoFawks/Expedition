package wtf.config;

import java.io.File;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import wtf.Core;
import wtf.utilities.UBC.ReplacerUBCSand;
import wtf.utilities.UBC.ReplacerUBCSandstone;

public class OverworldGenConfig extends AbstractConfig{

	public static boolean genTrees;
	public static boolean modifySurface;
	
	public static double mountainFracChunkPercent;
	public static double mountainFracFreq;

	public static double riverFracChunkPercent;
	public static double riverFracFreq;
	
	public static double forestMossChunkPercent;
	public static double ForestMossFreq;
	
	public static double treeReplacementRate;
	public static double simplexTreeScale;
	
	public static int autumnForestID;
	public static int autumnHillsID;

	public static int autumnForestPercent;
	public static int autumnForestSize;
	public static int autumnForestColorScale;

	public static boolean UBCSandReplacer;
	
	//public static boolean subLeaves;
	//public static int subLeafDistance;
	
	public static void loadConfig() {

		Configuration config = new Configuration(new File(configPath + "WTFOverworldGenConfig.cfg"));

		genTrees = config.get("Trees", "Allow this mod to bypass normal tree generation, and do custom tree generation (required for the rest of the tree configs to have effect)", true).getBoolean();
		treeReplacementRate = config.get("Trees", "Percentage of trees generated that this mod will attempt to replace with custom big trees", 50).getDouble()/100;
		simplexTreeScale = config.get("Trees", "Scale for the simplex tree replacement- smaller values allow more mixing of tree types, larger values seperate them out more", 3.0).getDouble();
		
		//subLeaves = config.get("Trees", "Leaf Sub: Enable registry alias substitution of vanilla leaves (WARNING: This breaks vanilla trees, ONLY use in RTG world types)", false).getBoolean();
		//subLeafDistance = config.get("Trees", "Leaf Sub: Distance that substituted leaf blocks should look for a log block", 5).getInt();
		//subLeafDistance*= subLeafDistance;
		
		String surface = "Surface Modifications";
		modifySurface = config.get(surface, "Enable all surface modification", true).getBoolean();
		
		mountainFracChunkPercent = config.get(surface, "In mountainous, percentage of chunks which will have cobblestone fractured surfaces", 50).getDouble()/100;
		mountainFracFreq = config.get(surface, "In mountainous, percentage of blocks within the chunk which will be fractured", 20).getDouble()/100;
		
		riverFracChunkPercent = config.get(surface, "In rivers, percentage of chunks which will have cobblestone fractured river beds", 50).getDouble()/100;
		riverFracFreq = config.get(surface, "In rivers, percentage of blocks within the chunk which will be fractured", 20).getDouble()/100;
		
		forestMossChunkPercent = config.get(surface, "In forests, percentage of chunks which will have mossy surfaces", 50).getDouble()/100;
		ForestMossFreq = config.get(surface, "In mossy forests, percentage of blocks within the chunk which will be mossified", 50).getDouble()/100;
		

		String af = "Sub-Biome : Autumn Forest";
		autumnForestID = config.get(af, "Autumn Forest ID, set to -1 to disable", 40).getInt();
		autumnHillsID = config.get(af, "Autumn Hills ID, set to -1 to disable", 41).getInt();
	
		autumnForestPercent = config.get(af, "Percentage frequency of Autumn Forest sub biomes within their parent biomes", 15).getInt();
		autumnForestSize = config.get(af, "Autumn forest size- setting smaller will give isolated patches, larger gives large swathes", 30).getInt()*32;
		autumnForestColorScale = config.get(af, "Autumn Color Scaling- setting smaller will give faster changes in colour", 10).getInt();
		

		if (Core.UBC){
			UBCSandReplacer = config.get("UBC Options", "Replace some of the world's sand and sandstone with UBC sedimentary sand", true).getBoolean();
			if (UBCSandReplacer){
				new ReplacerUBCSand(Blocks.SAND);
				//new ReplacerUBCSand(Blocks.GRAVEL);
				//new ReplacerUBCSandstone(Blocks.SANDSTONE);
				//new ReplacerUBCSandstone(Blocks.RED_SANDSTONE);
			}
		}
				
		config.save();

	}
}
