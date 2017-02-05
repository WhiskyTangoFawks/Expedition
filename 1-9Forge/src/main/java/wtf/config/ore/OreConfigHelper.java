package wtf.config.ore;

import java.util.HashMap;

import wtf.Core;

public class OreConfigHelper {

	public static HashMap<String, OreDefReg> getPresets(){
		
		HashMap<String, OreDefReg> defPresets = new HashMap<String, OreDefReg>();
		
			defPresets.put("tconstruct:ore@0", new OreDefReg("minecraft:netherrack@0", "vein", new int[]{5, 95}, new int[]{-30, 60}, true).setDensity(75)
					.setVeinDimensions(new int[]{16,1,1}).setVeinPitch(0.45F).setDimensionIDs(-1).setTextureLoc("ore_cobalt").setSurfaces("floor & ceiling & wall")); 
			defPresets.put("tconstruct:ore@1", new OreDefReg("minecraft:netherrack@0", "cloud", new int[]{5, 95}, new int[]{-30, 60}, true).setDensity(15).setCloudDiameter(14).setTextureLoc("ore_ardite").setDimensionIDs(-1)); 
	
		String defStone = "minecraft:stone@0";
		if (Core.UBC){
			defStone += ", igneous, metamorphic, sedimentary";
		}
		
		defPresets.put("minecraft:coal_ore@0", new OreDefReg(defStone+", minecraft:gravel@0", "vein", new int[]{20, 120}, new int[]{60, 220}, true).setDensity(75)
				.setVeinDimensions(new int[]{8,8,1}).setVeinPitch(1.5F).setBiomeTags("swamp@150, hot@50")); 

		defPresets.put("minecraft:iron_ore@0", new OreDefReg(defStone, "vein", new int[]{10, 105}, new int[]{30, 120}, true).setDensity(50)
				.setVeinDimensions(new int[]{12,2,2}).setVeinPitch(1.5F).setBiomeTags("mountain@150, savanna@50")); 
		
		defPresets.put("minecraft:gold_ore@0", new OreDefReg(defStone, "cloud", new int[]{5, 45}, new int[]{-12, 20}, true).setDensity(10).setCloudDiameter(14)
				.setBiomeTags("river@150, forest@50")); 

		defPresets.put("minecraft:lapis_ore@0", new OreDefReg(defStone, "cluster", new int[]{1, 50}, new int[]{1, 5}, true).setBiomeTags("ocean@150, dry@50")); 
		
		defPresets.put("minecraft:redstone_ore@0", new OreDefReg(defStone, "vein", new int[]{5, 60}, new int[]{10, 38}, true)
				.setVeinDimensions(new int[]{16,1,1}).setVeinPitch(0F).setBiomeTags("sandy@150, wet@50"));
		
		defPresets.put("minecraft:emerald_ore@0", new OreDefReg(defStone, "single", new int[]{1, 35}, new int[]{-10, 10}, true).setBiomeTags("hills@150, wet@50"));
		
		defPresets.put("minecraft:diamond_ore@0", new OreDefReg(defStone+", minecraft:obsidian@0", "cluster", new int[]{1, 25}, new int[]{-17, 23}, true).setDensity(50).setBiomeTags("jungle@150, swamp@50")); 
		
		defPresets.put("minecraft:quartz_ore@0", new OreDefReg("minecraft:netherrack@0", "cave&cluster", new int[]{5, 95}, new int[]{60, 120}, true).setDensity(75).setDimensionIDs(new int[]{-1}).setSurfaces("floor & ceiling & wall"));
		
		defPresets.put("wtfcore:nitre_ore@0", new OreDefReg(defStone, "cave@single", new int[]{15, 95}, new int[]{-10, 10}, true).setSurfaces("floor"));
		
		defPresets.put("wtfcore:oreSandGold@0", new OreDefReg("minecraft:sand@0", "underwater@single", new int[]{90, 110}, new int[]{-10, 10}, true).setTextureLoc("gold_ore").setReqBiomes("river"));
		
		defPresets.put("WTFBlockType:cracked", new OreDefReg(defStone, "cave@single", new int[]{10, 110}, new int[]{-25, 110}, false).setSurfaces("ceiling & wall").setVeinDimensions(new int[]{10,2,1}).setVeinPitch(0).setDensity(50));
	
		return defPresets;
		
	}
	
}
