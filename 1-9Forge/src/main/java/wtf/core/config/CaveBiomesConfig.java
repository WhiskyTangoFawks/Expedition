package wtf.core.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class CaveBiomesConfig{


	//public static int ceilingAddonChance;
	//public static int floorAddonChance;
	//public static boolean enableMossyStone;
	public static float dungeonChance;

	//public static int magmaCrustGenRate;

	public static boolean logDungeons;
	public static boolean enableMobDungeons;
	public static boolean enableAmbientDungeons;
	
	public static void customConfig() {

		Configuration config = new Configuration(new File("config/WTFCaveBiomesConfig.cfg"));

		config.load();
		String section1 = "Cave Subtype and Dungeon Generation";
		
		dungeonChance = config.get(section1, "Cave Subtype and Dungeon percent generation chance", 10).getInt()/100F;
		logDungeons=config.get(section1, "Log subtypes in chat", false).getBoolean();

		String section2 = "Cave Generation Options";
		//ceilingAddonChance = config.get(section2, "base frequency of stalactites, and other ceiling addons", 5).getInt();
		//floorAddonChance = config.get(section2, "base frequency of stalagmite, and other floor addons", 5).getInt();


		String section3 = "Cave Subtype and Dungeon Generation";
		enableMobDungeons = config.get(section3, "Allow generation of custom mob based dungeons", true).getBoolean();
		enableAmbientDungeons = config.get(section3, "Allow generation of cave subtypes", true).getBoolean();
		
		config.save();
		
	}
}
