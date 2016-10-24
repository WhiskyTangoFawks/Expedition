package wtf.config;

import java.io.File;

import com.google.common.collect.ImmutableMap;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.config.Configuration;

public class OverworldGenConfig{

	public static boolean genTrees;
	public static boolean modifySurface;
	
	public static double mountainFracChunkPercent;
	public static double mountainFracFreq;

	public static double forestMossChunkPercent;
	public static double ForestMossFreq;
	
	public static double treeReplacementRate;
	public static ImmutableMap<Biome, Double> treeReplacementRateBiomeOverride;
	public static double simplexTreeScale;
	public static boolean addRoots;
	
	public static int autumnForestID;
	public static int autumnHillsID;
	//public static int autumnBirchForestID;
	//public static int autumnBirchHillsID;
	
	public static int autumnForestPercent;
	public static int autumnForestSize;
	public static int autumnForestColorScale;
	
	public static boolean fixBirchLeaves;
	public static boolean replaceRTGBirches;

	
	public static int mangroveID;
	public static int mangrovePercent;
	public static int mangroveSize;
	
	public static int denseJungleID;
	public static int denseJungleHillsID;	
	public static int denseJunglePercent;
	public static int denseJungleSize;
	
	public static int meadowID;
	public static int meadowPercent;
	public static int meadowSize;
	
	private static Configuration config;
	
	public static void loadConfig() {

		config = new Configuration(new File("config/WTFOverworldGenConfig.cfg"));

		genTrees = config.get("Trees", "Allow this mod to bypass normal tree generation, and do custom tree generation (required for the rest of the tree configs to have effect)", true).getBoolean();
		treeReplacementRate = config.get("Trees", "Percentage of trees generated that this mod will attempt to replace with custom big trees", 50).getDouble()/100;
		simplexTreeScale = config.get("Trees", "Scale for the simplex tree replacement- smaller values allow more mixing of tree types, larger values seperate them out more", 3.0).getDouble();
		addRoots = config.get("Trees", "Generate roots for vanilla, and mod added trees placed by this mods generator", true).getBoolean();
		replaceRTGBirches = config.get("Trees", "Replace RTG Birch trees with custom large poplar trees", true).getBoolean();
		
		String surface = "Surface Modifications";
		modifySurface = config.get(surface, "Enable all surface modification", true).getBoolean();
		
		mountainFracChunkPercent = config.get(surface, "In mountainous, percentage of chunks which will have cobblestone fractured surfaces", 50).getDouble()/100;
		mountainFracFreq = config.get(surface, "In mountainous, percentage of blocks within the chunk which will be fractured", 20).getDouble()/100;
		
		forestMossChunkPercent = config.get(surface, "In forests, percentage of chunks which will have mossy surfaces", 50).getDouble()/100;
		ForestMossFreq = config.get(surface, "In mossy forests, percentage of blocks within the chunk which will be mossified", 50).getDouble()/100;
		

		String af = "Sub-Biome : Autumn Forest";
		autumnForestID = config.get(af, "Autumn Forest ID, set to -1 to disable", 40).getInt();
		autumnHillsID = config.get(af, "Autumn Hills ID, set to -1 to disable", 41).getInt();
		//autumnBirchForestID = config.get(af, "Autumn Birch Forest ID, set to -1 to disable", 42).getInt();
		//autumnBirchHillsID = config.get(af, "Autumn Birch Hills ID, set to -1 to disable", 43).getInt();
		
		autumnForestPercent = config.get(af, "Percentage frequency of Autumn Forest sub biomes within their parent biomes", 15).getInt();
		autumnForestSize = config.get(af, "Autumn forest size- setting smaller will give isolated patches, larger gives large swathes", 30).getInt()*32;
		autumnForestColorScale = config.get(af, "Autumn Color Scaling- setting smaller will give faster changes in colour", 10).getInt();
		

		fixBirchLeaves = config.get(af, "Register Birch leaves to the color manager- allows them to function in autumn sub-biomes", true).getBoolean();
		
		String m = "Sub-Biome : Mangrove Swamp";
		mangroveID = config.get(m, "Mangrove swamp ID, set to -1 to disable", 44).getInt();
		mangrovePercent = config.get(m, "Percentage frequency of mangrove sub biomes within their parent biomes", 15).getInt();
		mangroveSize = config.get(m, "Mangrove sub-biome size- setting smaller will give isolated patches, larger gives large swathes", 1).getInt()*32;

		//String j = "Sub-Biome : Dense Jungle";
		//denseJungleID = config.get(j, "Dense Jungle ID, set to -1 to disable", 45).getInt();
		//denseJungleHillsID = config.get(j, "Dense Jungle ID, set to -1 to disable", 46).getInt();
		//denseJunglePercent = config.get(j, "Percentage frequency of Dense Jungle sub biomes within their parent biomes", 25).getInt();
		//denseJungleSize = config.get(j, "Dense Jungle sub-biome size- setting smaller will give isolated patches, larger gives large swathes", 4).getInt()*32;
		
		//String meadow = "Sub-Biome : Meadow";
		//meadowID = config.get(meadow, "Meadow ID, set to -1 to disable", 47).getInt();
		//meadowPercent = config.get(meadow, "Percentage frequency of meadow sub biomes within their parent biomes", 25).getInt();
		//meadowSize = config.get(meadow, "Meadow sub-biome size- setting smaller will give isolated patches, larger gives large swathes", 1).getInt()*32;
		
	}
	
	public static void postLoadConfig() {
		
		ImmutableMap.Builder<Biome, Double> treeReplacementRateBiomeOverrideBuilder = ImmutableMap.builder();
		
		String[] raw = config.get("Trees", "Biome specific percentage of trees generated that this mod will attempt to replace with custom big trees", new String[0], "Format - modid:biome=%").getStringList();
		for(String s : raw){
			String[] bv = s.split("=");
			if(bv.length == 2){
				Biome biome = Biome.REGISTRY.getObject(new ResourceLocation(bv[0]));
				if(biome != null){
					double p;
					try {
						p = Double.parseDouble(bv[2]) / 100;
					} catch(NumberFormatException e){
						continue;
					}
					treeReplacementRateBiomeOverrideBuilder.put(biome, p);
				}
			}
		}
		
		treeReplacementRateBiomeOverride = treeReplacementRateBiomeOverrideBuilder.build();
		
		config.save();
		
	}

	public static double getTreeReplacementRate(Biome biome){
		
		if(treeReplacementRateBiomeOverride.containsKey(biome)){
			return treeReplacementRateBiomeOverride.get(biome);
		} else {
			return treeReplacementRate;
		}
		
	}
}
