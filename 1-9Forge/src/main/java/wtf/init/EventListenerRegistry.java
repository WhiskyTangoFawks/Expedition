package wtf.init;

import net.minecraftforge.common.MinecraftForge;
import wtf.Core;
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
				Core.coreLog.info("mining speed listener registered");
			}
			if (GameplayConfig.miningOreFractures){
				MinecraftForge.EVENT_BUS.register(new ListenerOreFrac());
				Core.coreLog.info("ore fracturing listener registered");
			}
			if (GameplayConfig.miningStoneFractures){
				MinecraftForge.EVENT_BUS.register(new ListenerStoneFrac());
				Core.coreLog.info("stone fracturing listener registered");
			}

			if (GameplayConfig.customExplosion){
				MinecraftForge.EVENT_BUS.register(new ListenerCustomExplosion());
				Core.coreLog.info("custom explosion listener registered");
			}
			if (GameplayConfig.gravity){
				MinecraftForge.EVENT_BUS.register(new ListenerGravity());
				Core.coreLog.info("block gravity listener registered");
			}
			if (GameplayConfig.stickDrop > 0){
				MinecraftForge.EVENT_BUS.register(new ListenerLeafDrops());
				Core.coreLog.info("Leaves drop sticks listener registered");
			}
			if (GameplayConfig.featherDrop > 0){
				MinecraftForge.EVENT_BUS.register(new ListenerChickenDrops());
				Core.coreLog.info("Chickens drop feathers listener registered");
			}
			if (GameplayConfig.waterControl){
				MinecraftForge.EVENT_BUS.register(new ListenerWaterSpawn());
				Core.coreLog.info("Water spawn controller listener registered");
			}
			if (GameplayConfig.plantGrowthMod){
				MinecraftForge.EVENT_BUS.register(new ListenerPlantGrowth());
				Core.coreLog.info("Plang growth speed modifier listener registered");
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
