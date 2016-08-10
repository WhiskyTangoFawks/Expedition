package wtf.cavebiomes;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import wtf.api.WTFWorldGen;
import wtf.cavebiomes.proxy.CommonProxy;
import wtf.cavebiomes.worldgeneration.CavePopulator;
import wtf.cavebiomes.worldgeneration.DungeonPopulator;
import wtf.core.config.CaveBiomesConfig;
import wtf.core.config.CoreConfig;

@Mod(modid = CaveBiomes.modid, name = "WhiskyTangoFox's Cave Biomes", version = "2.0")


public class CaveBiomes {
	public static  final String modid = "cavebiomes";



	@SidedProxy(clientSide="wtf.cavebiomes.proxy.ClientProxy", serverSide="wtf.cavebiomes.proxy.CommonProxy")
	public static CommonProxy proxy;


	@EventHandler
	public void PreInit(FMLPreInitializationEvent preEvent)
	{
		
		CaveBiomesConfig.customConfig();
		if (CoreConfig.dungeonGeneration){
			WTFWorldGen.addGen(new DungeonPopulator());
		}
		
	}
	@EventHandler public void load(FMLInitializationEvent event)
	{

	}
	@EventHandler
	public void PostInit(FMLPostInitializationEvent postEvent){

	
		if (CoreConfig.dungeonGeneration){
			WTFWorldGen.addGen(new CavePopulator());
		}
		
		
	}
}
