package wtf.ores.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;

public class WTFOreConfig {

	public static boolean cancelVanillaCoal;
	public static boolean cancelVanillaIron;
	public static boolean cancelVanillaGold;
	public static boolean cancelVanillaLapis;
	public static boolean cancelVanillaRedstone;
	public static boolean cancelVanillaQuartz;
	public static boolean cancelVanillaEmerald;
	public static boolean cancelVanillaDiamond;
	public static boolean genNitreOre;
	public static boolean genSandGold;
	public static boolean genCrackedStone;
	public static boolean rotate180only;
	
	
	public static boolean simplexGen;
	
	public static void loadConfig() throws Exception {

		Configuration config = new Configuration(new File("config/WTFOresConfig.cfg"));

		config.load();
		
		simplexGen = config.get("Gen Options", "Use simplex noise instead of random for ore generation", true).getBoolean();
		rotate180only = config.get("Ore Blockstate Generation", "When generating new blockstates, create variants with 180 rotation only, setting to false enables 90 adn 270 degree rotations.  Requires generating new blockstates, and placing them in a resource pack", true).getBoolean();
		
		
		
		String vanilla = "Generation of Vanilla Ore";
		String cancel = "Enable Ore Gen: Vanilla";
		
		//I need to add another tag: texture- which gets used when a blocks name doesn't match the texture overlay it needs to use
		
		cancelVanillaCoal = config.get(cancel, "Coal", true).getBoolean();
		String coal = config.get(vanilla, "Vanilla Coal Generation", "vein, minecraft:coal_ore@0, stone=minecraft:stone@0, stone=minecraft:gravel@0, denseOre=true, orePerChunk=60 & 220, GenHeightPercentSurface=20 & 120,"
				+ " VeinDimensions=8 & 8 & 1, pitch=1.5, DensityPercent=75, genPercentInBiomeType=swamp@150, genPercentInBiomeType=hot@50").getString();
		if (cancelVanillaCoal){
			ParseOre.parse(coal);
		}
		
		cancelVanillaIron = config.get(cancel, "Iron", true).getBoolean();
		String iron = config.get(vanilla, "Vanilla Iron Generation", "vein, minecraft:iron_ore@0, denseOre=true, orePerChunk=30 & 120, GenHeightPercentSurface=10 & 105, VeinDimensions=12 & 2 & 2,"
				+ " DensityPercent=50, pitch=1.5, genPercentInBiomeType=mountain@150, genPercentInBiomeType=savanna@50 ").getString();
		if (cancelVanillaIron){
			ParseOre.parse(iron);
		}
		
		cancelVanillaGold = config.get(cancel, "Gold", true).getBoolean();
		String gold = config.get(vanilla, "Vanilla Gold Generation", "cloud, minecraft:gold_ore@0, denseOre=true, orePerChunk=-12 & 20, GenHeightPercentSurface=5 & 45, size=14, DensityPercent=10,"
				+ " genPercentInBiomeType=river@150, genPercentInBiomeType=forest@50").getString();
		if (cancelVanillaGold){
			ParseOre.parse(gold);
		}
		
		cancelVanillaLapis = config.get(cancel, "Lapis", true).getBoolean();
		String lapis = config.get(vanilla, "Vanilla Lapis Generation", "cluster, minecraft:lapis_ore@0, denseOre=true, orePerChunk=1 & 5, GenHeightPercentSurface=1 & 50, "
				+ "genPercentInBiomeType=ocean@150, genPercentInBiomeType=beach@150, genPercentInBiomeType=dry@50").getString();
		if (cancelVanillaLapis){
			ParseOre.parse(lapis);
		}
		
		cancelVanillaRedstone = config.get(cancel, "Redstone", true).getBoolean();
		String redstone = config.get(vanilla, "Vanilla Redstone Generation", "vein, minecraft:redstone_ore@0, denseOre=true, orePerChunk=10 & 38, GenHeightPercentSurface=5 & 60, VeinDimensions=16 & 1 & 1,"
				+ " pitch=0, genPercentInBiomeType=sandy@150, genPercentInBiomeType=wet@50").getString();
		if (cancelVanillaRedstone){			
			ParseOre.parse(redstone);
		}
	
		
		cancelVanillaEmerald = config.get(cancel, "Emerald", true).getBoolean();
		String emerald = config.get(vanilla, "Vanilla Emerald Generation", "single, minecraft:emerald_ore@0, denseOre=true, orePerChunk=-10 & 10, GenHeightPercentSurface=1 & 33,"
				+ " genPercentInBiomeType=hills@150, genPercentInBiomeType=wet@50").getString();
		if (cancelVanillaEmerald){
			ParseOre.parse(emerald);
		}
		
		cancelVanillaDiamond = config.get(cancel, "Diamond", true).getBoolean();
		String diamond = config.get(vanilla, "Vanilla Diamond Generation", "cluster, minecraft:diamond_ore@0, stone=minecraft:stone@0, stone=minecraft:obsidian@0, denseOre=true, orePerChunk=-17 & 23, GenHeightPercentSurface=1 & 25,"
				+ " DensityPercent=50, genPercentInBiomeType=jungle@150, genPercentInBiomeType=swamp@50").getString();
		if (cancelVanillaDiamond){
			ParseOre.parse(diamond);
		}
		
		cancelVanillaQuartz = config.get(cancel, "Quartz", true).getBoolean();
		String quartz = config.get(vanilla, "Vanilla Quartz Generation", "cave@cluster, minecraft:quartz_ore@0, surfaces=floor&wall&ceiling, stone=minecraft:netherrack@0, denseOre=true, orePerChunk=-60 & 120,"
				+ " GenHeightPercentSurface=5 & 95, size=16, dimension=-1").getString();
		if (cancelVanillaQuartz){
			ParseOre.parse(quartz);
		}
		
		String defModOre = "Default Mod Added Ores Generation Config strings";
		String defEnableMod = "Enable Ore Gen : Default Mod Added Ores";
		
		genNitreOre = config.get(defEnableMod, "Mod Added Ore : WTF's Nitre", true).getBoolean();
		String nitre = config.get(defModOre, "WTF's Nitre Ore", "cave@single, wtfcore:nitre_ore@0, surfaces=floor, orePerChunk=-10 & 10, denseOre=true, GenHeightPercentSurface=15 & 95").getString();
		if (genNitreOre){
			ParseOre.parse(nitre);
		}
		genSandGold = config.get(defEnableMod, "Mod Added Ore: WTF's Gold in Sand", true).getBoolean();
		String sandgold = config.get(defModOre, "WTF's Gold in Sand", "underwater@single, wtfcore:oreSandGold@0, orePerChunk=-10 & 10, stone=minecraft:sand@0, reqBiomeType=river, denseOre=true, GenHeightPercentSurface=90 & 110, texture=gold_ore").getString();
		if (genSandGold){
			ParseOre.parse(sandgold);
		}
		genCrackedStone = config.get(defEnableMod, "Mod Added Ore: WTF's Cracked Stone", true).getBoolean();
		String crackedStone = config.get(defModOre, "WTFs Cracked Stone", "cave@vein, wtfcore:stone0DecoStatic@2, surfaces=ceiling, orePerChunk=-25 & 85, GenHeightPercentSurface=10 & 110, VeinDimensions=10 & 4 & 1, pitch=1.5, DensityPercent=50").getString();
		if (genCrackedStone){
			ParseOre.parse(crackedStone);
		}
		
		
		
		if (Loader.isModLoaded("tconstruct")){
			boolean tconOres = config.get(defEnableMod, "Mod Added Ore : Tinker's Construct Cobalt and Ardite", true).getBoolean();
			String cobalt = config.get(defModOre, "Tinker's Construct Cobalt Generation", "vein, tconstruct:ore@0, stone=minecraft:netherrack@0, denseOre=true, orePerChunk=-30 & 60, VeinDimensions=16 & 1 & 1, pitch = 0.45,"
					+ " GenHeightPercentSurface=5 & 95, dimension=-1").getString();
			if (tconOres){
				ParseOre.parse(cobalt);
			}
			String ardite = config.get(defModOre, "Tinker's Construct Ardite Generation", "cloud, tconstruct:ore@1, stone=minecraft:netherrack@0, denseOre=true, orePerChunk=-30 & 60, "
					+ " GenHeightPercentSurface=5 & 95, size=16, DensityPercent=15, dimension=-1").getString();
			if (tconOres){
				ParseOre.parse(ardite);
			}
			
			
			
		}
		
		
		int extralines = config.get("Mod Added Ores", "Generate extra lines to add custom ores, requires restarting minecraft to generate ", 2).getInt()-2;
		
		for (int loop = 1; loop < extralines+3; loop++){
			Boolean gen = config.get("Enable Ore Gen : Custom User Added Ores", "Mod Added Ore # " + loop, false).getBoolean();
			String ore = config.get("User added ore ", "# " + loop, "GenerationType, ModID:RegistryName@metadata, orePerChunk= min & max, GenHeightPercentSurface= min & max").getString();
			if (gen) {
				ParseOre.parse(ore);
			}
		}
		
		config.save();
	}
}
