package wtf.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import wtf.init.BlockSets;


	

public class GameplayConfig {

	
	
	public static boolean oreFractures;
	public static boolean stoneFracturesBeforeBreaking;
	
	
	public static int torchLifespan;
	public static int torchRange;
	public static boolean relightTorchByHand;

	public static boolean modifyHammer;
	
	

	public static String[] oreList;
	public static boolean fallingBlocksDamage;
	public static boolean antiNerdPole;
	
	public static boolean customExplosion;
	public static boolean explosionFractures;
	public static double expLvlAatomize;
	public static double expLvlDrop;
	public static double creeperUpConstant;
	
	public static boolean homescroll;

	//public static boolean enableQuickCrafting;

	public static int featherDrop;
	public static int stickDrop;
	
	public static int appleCoreConstant;
	
	public static boolean wcictable;
	
	public static boolean removeVanillaTools;
	
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
	modifyHammer = config.get("Mining", "Modify hammer behaviour", true).getBoolean();
	

	String oreString = config.get("Mining", "Ores to add for fracturing- modname:blockname", "minecraft:emerald_ore").getString();
	ConfigUtils.parseOreFrac(oreString);

	/**
	 * Explosions Options
	 */
	
	customExplosion = config.get("Explosives", "Override explosions with custom explosions", true).getBoolean();
	
	explosionFractures = config.get("Explosives", "Explosions fracture stone", true).getBoolean();
	
	expLvlAatomize = config.get("Explosives", "Explosion level above which blocks atomize, and below which they drop", 5).getDouble();
	expLvlDrop = config.get("Explosives", "Explosion level above which blocks drop, and below which they fracture (if fracturing on- if off nothing happens below)", 1).getDouble();
	creeperUpConstant = config.get("Explosives", "Creeper upward modifier, increase to have creeper explosions explode more upwards", 1.5).getDouble()*100;
	

	/**
	 * Gravity Options
	 */
	
	String defaultFall = ConfigUtils.getStringFromArrayList(BlockSets.defaultFallingBlocks);
	String fallingBlockString = config.get("Gravity", "Block name and number of identical blocks above requried to prevent falling if disturbed by player", defaultFall).getString();
	ConfigUtils.parseFallingBlocks(fallingBlockString);
		
	fallingBlocksDamage = config.get("Gravity", "Enable damage from blocks", true).getBoolean();
	antiNerdPole = config.get("Gravity", "NerdPole Prevention: Prevent indefinite stacking of non-stable blocks (causes them to slide off)", true).getBoolean();
	
	/**
	 * Torches Options
	 */
	String torch = "Finite Torches";
	torchLifespan = config.get(torch, "Chance in 100 per block tick (avg every 45 seconds) that an unattended torch will go out, 0 disables torches going out, -1 prevents torch replacement entirely", 20).getInt();
	torchRange = config.get(torch, "Number of blocks a player must be within to prevent a torch from going out", 20).getInt();
	relightTorchByHand = config.get(torch, "Torches can be relit by hand (true), or require flint and steel (false)", true).getBoolean();
	
	
	/**
	 * Drops Options
	 */
	featherDrop = config.get("Drops", "Average number of minutes between chickens dropping feathers", 30).getInt()*20*60;
	stickDrop = config.get("Drops", "Percentage of leaf blocks that drop sticks", 50).getInt();

	/**
	 * AppleCore options
	 */
	
	appleCoreConstant = config.get("AppleCore", "Growth rate percent for crops (requires AppleCore)", 10).getInt();
	
	/*
	 * Loot & Crafting
	 */
	homescroll = config.get("Loot and Crafting", "Enable home scrolls", true).getBoolean();
	wcictable = config.get("Loot and Crafting", "Enable Crafting Recipe Table", true).getBoolean();
	
	removeVanillaTools = config.get("Loot and Crafting", "Remove recipes for vanilla tools (Requires Tinkers Construct to be installed)", true).getBoolean(); 
	
	
	config.save();
	}
}
