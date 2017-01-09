package wtf.init;

import net.minecraftforge.common.MinecraftForge;
import wtf.api.WTFWorldGen;
import wtf.config.CoreConfig;
import wtf.config.GameplayConfig;
import wtf.config.OverworldGenConfig;
import wtf.entities.EntitySpawnListener;
import wtf.gameplay.eventlisteners.ListenerBlockNameGetter;
import wtf.gameplay.eventlisteners.ListenerChickenDrops;
import wtf.gameplay.eventlisteners.ListenerCustomExplosion;
import wtf.gameplay.eventlisteners.ListenerGravity;
import wtf.gameplay.eventlisteners.ListenerLeafDrops;
import wtf.gameplay.eventlisteners.ListenerMiningSpeed;
import wtf.gameplay.eventlisteners.ListenerOreFrac;
import wtf.gameplay.eventlisteners.ListenerPlantGrowth;
import wtf.gameplay.eventlisteners.ListenerStoneFrac;
import wtf.gameplay.eventlisteners.ListenerWaterSpawn;
import wtf.ores.OreGenerator;
import wtf.ores.VanillOreGenCatcher;
import wtf.worldgen.DungeonPopulator;
import wtf.worldgen.OverworldGen;
import wtf.worldgen.PopulationDecorator;
import wtf.worldgen.trees.WorldGenTreeCancel;
import wtf.worldscan.CoreWorldGenListener;

public class EventListenerRegistry {

	public static void initListeners(){

		MinecraftForge.EVENT_BUS.register(new CoreWorldGenListener());
		MinecraftForge.TERRAIN_GEN_BUS.register(new CoreWorldGenListener());
		WTFWorldGen.addGen(new PopulationDecorator());
		
		if (CoreConfig.gameplaytweaks){
			if (GameplayConfig.miningSpeedEnabled){
				MinecraftForge.EVENT_BUS.register(new ListenerMiningSpeed());
			}
			if (GameplayConfig.miningStoneFractures){
				MinecraftForge.EVENT_BUS.register(new ListenerStoneFrac());
			}
			if (GameplayConfig.miningOreFractures){
				MinecraftForge.EVENT_BUS.register(new ListenerOreFrac());
			}
			if (GameplayConfig.customExplosion){
				MinecraftForge.EVENT_BUS.register(new ListenerCustomExplosion());
			}
			if (GameplayConfig.gravity){
				MinecraftForge.EVENT_BUS.register(new ListenerGravity());
			}
			if (GameplayConfig.stickDrop > 0){
				MinecraftForge.EVENT_BUS.register(new ListenerLeafDrops());
			}
			if (GameplayConfig.featherDrop > 0){
				MinecraftForge.EVENT_BUS.register(new ListenerChickenDrops());
			}
			if (GameplayConfig.waterControl){
				MinecraftForge.EVENT_BUS.register(new ListenerWaterSpawn());
			}
			if (GameplayConfig.plantGrowthMod){
				MinecraftForge.EVENT_BUS.register(new ListenerPlantGrowth());
			}
			
			MinecraftForge.EVENT_BUS.register(new LootEventListener());
		}
		
		if (CoreConfig.dungeonGeneration){
			WTFWorldGen.addGen(new DungeonPopulator());
		}
	
		if (CoreConfig.mobReplacement){
			MinecraftForge.EVENT_BUS.register(new EntitySpawnListener());
		}

		if (CoreConfig.enableOreGen){
			MinecraftForge.ORE_GEN_BUS.register(new VanillOreGenCatcher());
			WTFWorldGen.addGen(new OreGenerator());
		}
		
		if (CoreConfig.enableOverworldGeneration){
			if (OverworldGenConfig.genTrees){
				MinecraftForge.TERRAIN_GEN_BUS.register(new WorldGenTreeCancel());
				//MinecraftForge.EVENT_BUS.register(new WorldGenTreeCancel());
			}
		}

		
		if (CoreConfig.enableOverworldGeneration){
				WTFWorldGen.addGen(new OverworldGen());
		}

		if(CoreConfig.enableNameGetter){
			MinecraftForge.EVENT_BUS.register(new ListenerBlockNameGetter());
		}

	}

}
