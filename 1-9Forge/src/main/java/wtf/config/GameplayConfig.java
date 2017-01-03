package wtf.config;

import java.io.File;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import wtf.Core;
import wtf.init.BlockSets;
import wtf.init.WTFBlocks;
import wtf.utilities.UBCCompat;


	

public class GameplayConfig extends ConfigMaster {
	
	public static int torchLifespan;
	public static int torchRange;
	public static boolean relightTorchByHand;

	public static boolean modifyHammer;

	public static boolean fallingBlocksDamage;
	public static boolean antiNerdPole;
	
	public static boolean customExplosion;
	public static boolean explosionFractures;
	public static double expLvlAatomize;
	public static double expLvlDrop;
	public static double creeperUpConstant;
	
	public static boolean homescroll;
	
	public static boolean miningSpeedEnabled;

	//public static boolean enableQuickCrafting;

	public static int featherDrop;
	public static int stickDrop;
	
	public static double appleCoreConstant;
	
	public static boolean wcictable;
	
	public static boolean removeVanillaTools;
	
	public static Configuration config = new Configuration(new File(configPath+"WTFGameplay.cfg"));
	public static boolean waterControl;
	
	public static boolean childZombie;
	
	public static double explosionDamageMod;
	public static double explosionForceMod;

	public static void loadConfig() throws Exception{
	config.load();
	
	/**
	 * Mining Options
	 */

	//MOVE MINING SPEED INTO STONE REGISTRY
	
	modifyHammer = config.get("Mining", "Modify hammer behaviour", true).getBoolean();
	miningSpeedEnabled = config.get("Mining", "Enable mining speed modification (values are set in the Stone Registry)", true).getBoolean();
	


	String[] defaultOres = {Blocks.COAL_ORE.getRegistryName().toString(), Blocks.IRON_ORE.getRegistryName().toString(), Blocks.GOLD_ORE.getRegistryName().toString(),
			Blocks.LAPIS_ORE.getRegistryName().toString(), Blocks.DIAMOND_ORE.getRegistryName().toString(), Blocks.EMERALD_ORE.getRegistryName().toString()};
	
	String[] oresFrac = config.get("Mining", "Blocks which fracture adjacent stone when mined", defaultOres).getStringList();
	for (String string : oresFrac){
		BlockSets.oreAndFractures.add(getBlockFromString(string));
	}

	/**
	 * Explosions Options
	 */
	
	customExplosion = config.get("Explosives", "Override explosions with custom explosions", true).getBoolean();
	
	explosionFractures = config.get("Explosives", "Explosions fracture stone", true).getBoolean();
	
	expLvlAatomize = config.get("Explosives", "Explosion level above which blocks atomize, and below which they drop", 5).getDouble();
	expLvlDrop = config.get("Explosives", "Explosion level above which blocks drop, and below which they fracture (if fracturing on- if off nothing happens below)", 1).getDouble();
	creeperUpConstant = config.get("Explosives", "Creeper upward modifier, increase to have creeper explosions explode more upwards", 1.5).getDouble()*100;
	
	explosionDamageMod = config.get("Explosives", "Explosion entity damage percentage modifier", 100).getDouble()/1000;
	explosionForceMod = config.get("Explosives", "Explosion entity force percentage modifier", 100).getDouble()/500;

	/**
	 * Gravity Options
	 */
	
	String[] deffall = {"minecraft:dirt@50", "minecraft:cobblestone@75", "minecraft:mossy_cobblestone@90","minecraft:sand@10", "minecraft:soul_sand@10","minecraft:gravel@20", "minecraft:snow@40"};
	
	if (Core.UBC){
		String[] UBCdeffall = {"minecraft:dirt@50", "minecraft:cobblestone@75", "minecraft:mossy_cobblestone@90","minecraft:sand@10", "minecraft:soul_sand@10","minecraft:gravel@20", "minecraft:snow@40", 
				UBCCompat.IgneousCobblestone[0].getBlock().getRegistryName()+"@90", UBCCompat.MetamorphicCobblestone[0].getBlock().getRegistryName()+"@85", WTFBlocks.ubcSand.getRegistryName()+"@10"};
		
		deffall = UBCdeffall;
	}
	
	String[] fallingBlocks = config.get("Gravity", "blockName@percentStability (lower means less stable)", deffall).getStringList();
	
	for (String blockAndStability :fallingBlocks){
		Block block = getBlockFromString(blockAndStability.split("@")[0]);
		float stability = Integer.parseInt(blockAndStability.split("@")[1]);
		BlockSets.fallingBlocks.put(block, stability/100F);
	}
	

	fallingBlocksDamage = config.get("Gravity", "Enable damage from blocks", true).getBoolean();
	antiNerdPole = config.get("Gravity", "NerdPole Prevention: Prevent indefinite stacking of non-stable blocks (causes them to slide off)", true).getBoolean();
	
	/**
	 * Torches Options
	 */
	/*
	String torch = "Finite Torches";
	torchLifespan = config.get(torch, "Chance in 100 per block tick (avg every 45 seconds) that an unattended torch will go out, 0 disables torches going out, -1 prevents torch replacement entirely", 20).getInt();
	torchRange = config.get(torch, "Number of blocks a player must be within to prevent a torch from going out", 20).getInt();
	relightTorchByHand = config.get(torch, "Torches can be relit by hand (true), or require flint and steel (false)", true).getBoolean();
	*/
	
	/**
	 * Drops Options
	 */
	featherDrop = config.get("Drops", "Average number of minutes between chickens dropping feathers", 30).getInt()*20*60;
	stickDrop = config.get("Drops", "Percentage of leaf blocks that drop sticks", 50).getInt();

	/**
	 * AppleCore options
	 */
	
	appleCoreConstant = config.get("Crop Growth Rate", "Growth rate percent modifier for crops", 10).getDouble()/100;
	
	/*
	 * Loot & Crafting
	 */
	homescroll = config.get("Loot and Crafting", "Enable home scrolls", true).getBoolean();
	wcictable = config.get("Loot and Crafting", "Enable Crafting Recipe Table", true).getBoolean();
	
	removeVanillaTools = config.get("Loot and Crafting", "Remove recipes for vanilla tools (Requires Tinkers Construct to be installed)", false).getBoolean(); 
	
	waterControl = config.get("Other", "Prevent infinite water source blocks outside biomes with the WET type", false).getBoolean();
	childZombie = config.get("Other", "Prevent spawning of baby zombies", false).getBoolean();
	
	config.save();
	}
}
