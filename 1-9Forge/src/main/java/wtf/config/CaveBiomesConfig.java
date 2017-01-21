package wtf.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class CaveBiomesConfig extends ConfigMaster{

	public static float dungeonChance;
	public static boolean logDungeons;
	public static boolean enableMobDungeons;
	public static boolean enableAmbientDungeons;

	public static boolean enableLavaPools;
	
	public static void customConfig() {

		Configuration config = new Configuration(new File(configPath+"WTFCaveBiomesConfig.cfg"));

		config.load();
		String section1 = "Cave Subtype and Dungeon Generation";
		
		dungeonChance = config.get(section1, "Cave Subtype and Dungeon percent generation chance", 10).getInt()/100F;
		logDungeons=config.get(section1, "Log subtypes in chat", false).getBoolean();
		enableMobDungeons = config.get(section1, "Allow generation of custom mob based dungeons", true).getBoolean();
		enableAmbientDungeons = config.get(section1, "Allow generation of cave subtypes", true).getBoolean();
		
		enableLavaPools = config.get("Cave Options", "Allow generation of lava pools in deep volcanic and jungle volcanic cave biomes", true).getBoolean();
	
		
		config.save();
		
	}
}
