package wtf.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class CaveBiomesConfig extends AbstractConfig{

	public static float dungeonChance;
	public static boolean logDungeons;
	public static boolean enableMobDungeons;
	public static boolean enableAmbientDungeons;

	public static boolean enablePuddles;
	
	public static boolean replaceSandstone;
	public static boolean updateSandstone;
	
	public static void customConfig() {

		Configuration config = new Configuration(new File(configPath+"WTFCaveBiomesConfig.cfg"));

		config.load();
		String section1 = "Cave Subtype and Dungeon Generation";
		
		dungeonChance = config.get(section1, "Cave Subtype and Dungeon percent generation chance", 50).getInt()/100F;
		logDungeons=config.get(section1, "Log subtypes in chat", false).getBoolean();
		enableMobDungeons = config.get(section1, "Allow generation of custom mob based dungeons", true).getBoolean();
		enableAmbientDungeons = config.get(section1, "Allow generation of cave subtypes", true).getBoolean();
		
		enablePuddles = config.get("Cave Options", "Allow generation of puddles in wet cave biomes", true).getBoolean();
	
		replaceSandstone = config.get("Block Replacement", "Replace sandstone during world gen- prevents stalactites forming in pyramid ruins", true).getBoolean();
		updateSandstone = config.get("Block Replacement", "Update sandstone after replacement- required only if you've changed the texture of naturalised sandstone", true).getBoolean();
		
		config.save();
		
	}
}
