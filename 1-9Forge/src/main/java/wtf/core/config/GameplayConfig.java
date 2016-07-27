package wtf.core.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import wtf.core.init.BlockSets;


	

public class GameplayConfig {

	public static boolean customExplosion;
	
	public static boolean oreFractures;
	public static boolean stoneFracturesBeforeBreaking;
	public static boolean finiteTorches;
	
	public static boolean relightableTorches;
	public static int enableFiniteTorch;
	public static int torchLifespan;
	
	public static float hammerBreakSpeed;
	
	public static boolean denseOres;

	public static String[] oreList;
	public static boolean fallingBlocksDamage;
	public static boolean explosionFractures;
	
	public static boolean homescroll;

	//public static boolean enableQuickCrafting;

	public static int featherDrop;
	public static float stickDrop;

	public static Configuration config = new Configuration(new File("config/WTFGameplay.cfg"));

	public static void loadConfig(){
	config.load();
	
	/**
	 * Mining Options
	 */
	
	String defaultMiningSpeed = "minecraft:stone@0.15,minecraft:sandstone@0.3"; //,UndergroundBiomes:igneousStone@0.05,UndergroundBiomes:metamorphicStone@0.2,UndergroundBiomes:sedimentaryStone@0.3
	String readMiningSpeed = config.get("Mining", "Mining speed modifiers", defaultMiningSpeed).getString();
	ConfigUtils.parseMiningSpeeds(readMiningSpeed);
	
	oreFractures = config.get("Mining", "Ores fracture adjacent blocks when mined", true).getBoolean();
	stoneFracturesBeforeBreaking = config.get("Mining", "Stone fractures before breaking", true).getBoolean();
	hammerBreakSpeed = 0.01F * config.get("Mining", "TCon Hammer break speed % modifier on non-stone blocks", 10).getInt();
	
	denseOres = config.get("Mining", "Req. WTFOres: Use WTFOre's dense ores", true).getBoolean();

	String oreString = config.get("Mining", "Ores to add for fracturing- modname:blockname", "minecraft:emerald_ore").getString();
	ConfigUtils.parseOreFrac(oreString);

	/**
	 * Explosions Options
	 */
	
	customExplosion = config.get("Explosives", "TNT uses custom exploives", true).getBoolean();
	explosionFractures = config.get("Explosives", "Explosions fracture stone", true).getBoolean();

	/**
	 * Gravity Options
	 */
	
	String defaultFall = ConfigUtils.getStringFromArrayList(BlockSets.defaultFallingBlocks);
	String fallingBlockString = config.get("Gravity", "Block name and number of identical blocks above requried to prevent falling if disturbed by player", defaultFall).getString();
	ConfigUtils.parseFallingBlocks(fallingBlockString);
		
	fallingBlocksDamage = config.get("Gravity", "Enable damage from falling cobblestone and dirt", true).getBoolean();

	
	/**
	 * Torches Options
	 */
	enableFiniteTorch = config.get("Finite Torches", "0 = disable finite torches, 1 = Relight torches with a right click, 2= relight torches with a flint and steel only", 1).getInt();
	torchLifespan = config.get("Finite Torches", "Average Torch lifespan", 5).getInt();
	

	/**
	 * Item Options
	 */
	homescroll = config.get("Items", "Enable home scrolls", true).getBoolean();
	
	/**
	 * Crafting Options
	 */
	//enableQuickCrafting = config.get("Crafting", "Enable quick crafting", true).getBoolean();
	
	/**
	 * Drops Options
	 */
	featherDrop = config.get("Drops", "1 chance in X per tick that a chicken drops a feather, 0 disables", 3000).getInt();
	stickDrop = config.get("Drops", "Percentage of leaf blocks that drop sticks", 50).getInt()/100;

	
	config.save();
	}
}
