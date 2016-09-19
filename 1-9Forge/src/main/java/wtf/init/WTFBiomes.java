package wtf.init;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.biome.BiomeForest.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import wtf.config.OverworldGenConfig;
import wtf.worldgen.OverworldGen;
import wtf.worldgen.subbiomes.BiomeAutumnForest;
import wtf.worldgen.subbiomes.SubBiome;
import wtf.worldgen.subbiomes.SubBiomeDenseJungle;
import wtf.worldgen.subbiomes.SubBiomeMangroveSwamp;
import wtf.worldgen.subbiomes.SubBiomeMeadow;

public class WTFBiomes {

	public static BiomeAutumnForest autumnForest;
	public static BiomeAutumnForest autumnHills;
	public static BiomeAutumnForest autumnBirchForest;
	public static BiomeAutumnForest autumnBirchHills;
	
	public static SubBiomeMangroveSwamp mangrove;
	
	//public static SubBiomeDenseJungle densejungle;
	//public static SubBiomeDenseJungle denseJungleHills;
	
	//public static SubBiomeMeadow meadow;
	
	public static void init(){
		
		if (OverworldGenConfig.autumnForestID > 0){
			autumnForest = new BiomeAutumnForest(Type.NORMAL, new BiomeProperties("Autumn Forest").setTemperature(0.4F).setRainfall(0.8F), Biomes.FOREST);
			registerSubBiome(autumnForest, "AutumnForest", OverworldGenConfig.autumnForestID, 4);

			autumnHills = new BiomeAutumnForest(Type.NORMAL, new BiomeProperties("Autumn Hills").setTemperature(0.3F).setRainfall(0.8F), Biomes.FOREST_HILLS);
			registerSubBiome(autumnHills, "AutumnHills", OverworldGenConfig.autumnHillsID, 18);
			
			/*autumnBirchForest = new BiomeAutumnForest(Type.NORMAL, new BiomeProperties("Autumn Birch Forest").setTemperature(0.4F).setRainfall(0.8F), Biomes.BIRCH_FOREST);
			registerSubBiome(autumnBirchForest, "AutumnBirchForest", OverworldGenConfig.autumnBirchForestID, 27);
			
			autumnBirchHills = new BiomeAutumnForest(Type.NORMAL, new BiomeProperties("Autumn Birch Hills").setTemperature(0.4F).setRainfall(0.8F), Biomes.BIRCH_FOREST_HILLS);
			registerSubBiome(autumnBirchHills, "AutumnBirchHills", OverworldGenConfig.autumnBirchHillsID, 28);
			*/
			mangrove = new SubBiomeMangroveSwamp(new BiomeProperties("Mangrove Swamp").setTemperature(0.9F).setRainfall(0.9F), Biomes.SWAMPLAND);
			registerSubBiome(mangrove, "MangroveSwamp", OverworldGenConfig.mangroveID, 6);
			
			//densejungle = new SubBiomeDenseJungle(new BiomeProperties("Dense Jungle").setTemperature(0.9F).setRainfall(0.9F), Biomes.JUNGLE);
			//registerSubBiome(densejungle, "DenseJungle", OverworldGenConfig.denseJungleID, 21);
			
			//denseJungleHills = new SubBiomeDenseJungle(new BiomeProperties("Dense Jungle Hills").setTemperature(0.9F).setRainfall(0.9F), Biomes.JUNGLE);
			//registerSubBiome(denseJungleHills, "DenseJungleHills", OverworldGenConfig.denseJungleHillsID, 22);
			
			//meadow = new SubBiomeMeadow(new BiomeProperties("Meadow").setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(0.8F).setRainfall(0.4F));
			//registerSubBiome(meadow, "meadow", OverworldGenConfig.meadowID, 1);
			
			
			
		}


		


	}
	
	public static Biome registerSubBiome(SubBiome subbiome,String name, int id, int parentBiome){
		Biome biome = (Biome)subbiome;
		BiomeEntry entry = new BiomeEntry(biome, id);
		BiomeManager.addBiome(BiomeType.COOL, entry);
		BiomeManager.removeBiome(BiomeType.COOL, entry);
		biome.setRegistryName(name);
		Biome.registerBiome(id, name, biome);
		OverworldGen.subBiomeRegistry.put((byte)parentBiome, subbiome);
		return biome;
	}

}
