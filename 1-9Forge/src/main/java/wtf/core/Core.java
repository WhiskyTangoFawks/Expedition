package wtf.core;

import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import wtf.api.WTFWorldGen;
import wtf.biomes.TreePopulator;
import wtf.biomes.WorldGenEventListener;
import wtf.core.config.CoreConfig;
import wtf.core.config.GameplayConfig;
import wtf.core.entities.EntitySpawnListener;
import wtf.core.init.BlockSets;
import wtf.core.init.WTFArmor;
import wtf.core.init.WTFBlocks;
import wtf.core.init.WTFEntities;
import wtf.core.init.WTFItems;
import wtf.core.proxy.CommonProxy;
import wtf.core.worldgen.CoreWorldGenListener;
import wtf.gameplay.GamePlayEventListener;
import wtf.ores.OreGenerator;
import wtf.ores.VanillOreGenCatcher;
import wtf.ores.config.WTFOreConfig;

@Mod (modid = Core.coreID, name = Core.coreID, version = Core.version)

public class Core {
	public static  final String coreID = "wtfcore";
	public static final String version = "1.9v0.1";
	
	@SidedProxy(clientSide="wtf.core.proxy.ClientProxy", serverSide="wtf.core.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static Logger coreLog;
	
	@Instance(coreID)
	public static Core instance;
	
	public static CreativeTabs wtfTab = new CreativeTabs("WTFBlocks")
	{

		@Override
		public Item getTabIconItem()
		{
			return Item.getItemFromBlock(Blocks.COBBLESTONE);
		}

	};
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent preEvent) throws Exception
	{
		coreLog = preEvent.getModLog();

		CoreConfig.loadConfig();
		GameplayConfig.loadConfig();
		
		
		BlockSets.initBlockSets();
		WTFBlocks.initBlocks();
		WTFItems.initItems();
		WTFArmor.initArmor();
		WTFEntities.initEntites();
		
		if (CoreConfig.enableOreGen){
			WTFOreConfig.loadConfig();
		}
	}
	@EventHandler public void load(FMLInitializationEvent event) throws Exception
	{
		//this was where the texture overlay stuff got called
		//proxy.clientRegister();
		MinecraftForge.EVENT_BUS.register(new CoreWorldGenListener());
		MinecraftForge.TERRAIN_GEN_BUS.register(new CoreWorldGenListener());
		
		if (CoreConfig.mobReplacement){
			MinecraftForge.EVENT_BUS.register(new EntitySpawnListener());
		}
		
		if (CoreConfig.gameplaytweaks && GameplayConfig.customExplosion){
			MinecraftForge.EVENT_BUS.register(new GamePlayEventListener());
		}
		
		if (CoreConfig.enableOreGen){
			MinecraftForge.ORE_GEN_BUS.register(new VanillOreGenCatcher());
			WTFWorldGen.addGen(new OreGenerator());
		}
		
		if (CoreConfig.enableBigTrees){
			MinecraftForge.TERRAIN_GEN_BUS.register(new WorldGenEventListener());
			WTFWorldGen.addGen(new TreePopulator());
			MinecraftForge.EVENT_BUS.register(new WorldGenEventListener());
		}

	}
	@EventHandler
	public void PostInit(FMLPostInitializationEvent postEvent) throws Exception{

		//Blocks.LEAVES.setLightOpacity(0);
		//Blocks.LEAVES2.setLightOpacity(0);
		
	}	
	
	
	
	
	
}
