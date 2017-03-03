package wtf;

import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import wtf.config.CaveBiomesConfig;
import wtf.config.MasterConfig;
import wtf.config.GameplayConfig;
import wtf.config.OverworldGenConfig;
import wtf.config.WTFStoneRegistry;
import wtf.config.ore.WTFOresNewConfig;
import wtf.crafting.GuiHandler;
import wtf.crafting.RecipeParser;
import wtf.init.BlockSets;
import wtf.init.EventListenerRegistry;
import wtf.init.WTFBiomes;
import wtf.init.WTFBlocks;
import wtf.init.WTFEntities;
import wtf.init.WTFItems;
import wtf.init.WTFRecipes;
import wtf.init.WTFSubstitutions;
import wtf.proxy.CommonProxy;
import wtf.utilities.UBC.UBCCompat;


@Mod (modid = Core.coreID, name = "WTFs Expedition", version = Core.version, dependencies = "after:undergroundbiomes")

public class Core {
	public static  final String coreID = "wtfcore";
	public static final String version = "1.10.2_v1.5";

	@SidedProxy(clientSide="wtf.proxy.ClientProxy", serverSide="wtf.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static Logger coreLog;

	@Instance(coreID)
	public static Core instance;
	
	public static boolean UBC;

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

		proxy.writeResourcePack();
		proxy.loadLangFile();
		
		UBC = Loader.isModLoaded("undergroundbiomes");
	
		MasterConfig.loadConfig();
		

		CaveBiomesConfig.customConfig();

		if (UBC){
			UBCCompat.loadUBCStone();
		}
		else {
			coreLog.info("Underground Biomes Construct not detected");
		}
		OverworldGenConfig.loadConfig();
		GameplayConfig.loadConfig();
		WTFBlocks.initSimpleBlocks();
		WTFStoneRegistry.loadStoneReg();
		BlockSets.initBlockSets();
		WTFBlocks.initDependentBlocks();
		proxy.initWCICRender();
		WTFItems.initItems();
		WTFEntities.initEntites();
		WTFRecipes.initRecipes();
		
		if (MasterConfig.enableOverworldGeneration){
			WTFBiomes.init();
		}
		
		if (MasterConfig.enableOreGen){
			WTFOresNewConfig.loadConfig();
		}

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		
		WTFSubstitutions.init();

		proxy.finishLangFile();
	}
	
	@EventHandler public void load(FMLInitializationEvent event) throws Exception
	{
	
		
		EventListenerRegistry.initListeners();
		

		
	}
	
	@EventHandler
	public void PostInit(FMLPostInitializationEvent postEvent) throws Exception{

		if (MasterConfig.doResourcePack){
			proxy.enableBlockstateTexturePack();
		}

		if (GameplayConfig.wcictable){
			RecipeParser.init();
		}
		
		
		

	}	





}
