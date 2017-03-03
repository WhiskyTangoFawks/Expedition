package wtf.init;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wtf.Core;
import wtf.config.MasterConfig;
import wtf.config.CaveBiomesConfig;
import wtf.config.GameplayConfig;
import wtf.config.OverworldGenConfig;
import wtf.gameplay.eventlisteners.ListenerBlockNameGetter;
import wtf.gameplay.eventlisteners.ListenerChickenDrops;
import wtf.gameplay.eventlisteners.ListenerCustomExplosion;
import wtf.gameplay.eventlisteners.ListenerEntityDrops;
import wtf.gameplay.eventlisteners.ListenerGravity;
import wtf.gameplay.eventlisteners.ListenerLeafDrops;
import wtf.gameplay.eventlisteners.ListenerMiningSpeed;
import wtf.gameplay.eventlisteners.ListenerOreFrac;
import wtf.gameplay.eventlisteners.ListenerPlantGrowth;
import wtf.gameplay.eventlisteners.ListenerStoneFrac;
import wtf.gameplay.eventlisteners.ListenerWaterSpawn;
import wtf.gameplay.eventlisteners.ZombieListener;
import wtf.ores.VanillOreGenCatcher;
import wtf.worldgen.CoreWorldGenListener;
import wtf.worldgen.SandstoneNaturaliser;
import wtf.worldgen.generators.TickGenBuffer;
import wtf.worldgen.trees.WorldGenTreeCancel;

public class EventListenerRegistry {

	public static void initListeners(){

		MinecraftForge.EVENT_BUS.register(new CoreWorldGenListener());
		MinecraftForge.TERRAIN_GEN_BUS.register(new CoreWorldGenListener());
		
		MinecraftForge.EVENT_BUS.register(new TickGenBuffer());
		
		if (MasterConfig.gameplaytweaks){
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
			if (GameplayConfig.mobDropsReqPlayer > 0){
				MinecraftForge.EVENT_BUS.register(new ListenerEntityDrops());
			}		
			
			MinecraftForge.EVENT_BUS.register(new LootEventListener());
		}
		
		if (MasterConfig.enableOreGen){
			MinecraftForge.ORE_GEN_BUS.register(new VanillOreGenCatcher());
		}

		if (GameplayConfig.childZombie){
			MinecraftForge.EVENT_BUS.register(new ZombieListener());
		}

	
		if (MasterConfig.enableOverworldGeneration){
			if (OverworldGenConfig.genTrees){
				MinecraftForge.TERRAIN_GEN_BUS.register(new WorldGenTreeCancel());
			}
		}
		
		if (CaveBiomesConfig.replaceSandstone){

			
			MinecraftForge.TERRAIN_GEN_BUS.register(new SandstoneNaturaliser());
			MinecraftForge.EVENT_BUS.register(new SandstoneNaturaliser());
			//GameRegistry.registerWorldGenerator(new SandstoneNaturaliser(), 0);
			//Core.coreLog.info("Sandstone Naturaliser Registered");
			
		}

		if(MasterConfig.enableNameGetter){
			MinecraftForge.EVENT_BUS.register(new ListenerBlockNameGetter());
			Core.coreLog.info("Registery name getter enabled");
		}
		


		
	}

}
