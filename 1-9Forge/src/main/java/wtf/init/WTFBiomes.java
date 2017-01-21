package wtf.init;

import java.util.Collections;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeBeach;
import net.minecraft.world.biome.BiomeDesert;
import net.minecraft.world.biome.BiomeEnd;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomeForestMutated;
import net.minecraft.world.biome.BiomeHell;
import net.minecraft.world.biome.BiomeHills;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomeMesa;
import net.minecraft.world.biome.BiomeMushroomIsland;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.biome.BiomeRiver;
import net.minecraft.world.biome.BiomeSavanna;
import net.minecraft.world.biome.BiomeSavannaMutated;
import net.minecraft.world.biome.BiomeSnow;
import net.minecraft.world.biome.BiomeStoneBeach;
import net.minecraft.world.biome.BiomeSwamp;
import net.minecraft.world.biome.BiomeTaiga;
import net.minecraft.world.biome.BiomeVoid;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.biome.BiomeForest.Type;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import wtf.config.OverworldGenConfig;
import wtf.worldgen.OverworldGen;
import wtf.worldgen.subbiomes.BiomeAutumnForest;
import wtf.worldgen.subbiomes.SubBiome;
import wtf.worldgen.subbiomes.SubBiomeMangroveSwamp;

public class WTFBiomes {

	public static BiomeAutumnForest autumnForest;
	public static BiomeAutumnForest autumnHills;
	//public static BiomeAutumnForest autumnBirchForest;
	//public static BiomeAutumnForest autumnBirchHills;
	
	//public static SubBiomeMangroveSwamp mangrove;
	
	//public static SubBiomeDenseJungle densejungle;
	//public static SubBiomeDenseJungle denseJungleHills;
	
	//public static SubBiomeMeadow meadow;
	
	public static void init(){
		
		if (OverworldGenConfig.autumnForestID > 0){
			autumnForest = new BiomeAutumnForest(Type.NORMAL, new BiomeProperties("Autumn Forest").setTemperature(0.4F).setRainfall(0.8F), Biomes.FOREST);
			registerSubBiome(autumnForest, "AutumnForest", OverworldGenConfig.autumnForestID, 4);
			BiomeDictionary.registerBiomeType(autumnForest, BiomeDictionary.Type.FOREST);

			autumnHills = new BiomeAutumnForest(Type.NORMAL, new BiomeProperties("Autumn Hills").setTemperature(0.3F).setRainfall(0.8F), Biomes.FOREST_HILLS);
			registerSubBiome(autumnHills, "AutumnHills", OverworldGenConfig.autumnHillsID, 18);
			BiomeDictionary.registerBiomeType(autumnHills, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.HILLS);
			
			/*autumnBirchForest = new BiomeAutumnForest(Type.NORMAL, new BiomeProperties("Autumn Birch Forest").setTemperature(0.4F).setRainfall(0.8F), Biomes.BIRCH_FOREST);
			registerSubBiome(autumnBirchForest, "AutumnBirchForest", OverworldGenConfig.autumnBirchForestID, 27);
			
			autumnBirchHills = new BiomeAutumnForest(Type.NORMAL, new BiomeProperties("Autumn Birch Hills").setTemperature(0.4F).setRainfall(0.8F), Biomes.BIRCH_FOREST_HILLS);
			registerSubBiome(autumnBirchHills, "AutumnBirchHills", OverworldGenConfig.autumnBirchHillsID, 28);
			*/
			//mangrove = new SubBiomeMangroveSwamp(new BiomeProperties("Mangrove Swamp").setTemperature(0.6F).setRainfall(0.9F), Biomes.SWAMPLAND);
			//registerSubBiome(mangrove, "MangroveSwamp", OverworldGenConfig.mangroveID, 6);
			
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
