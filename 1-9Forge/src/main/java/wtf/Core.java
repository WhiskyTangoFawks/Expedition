package wtf;

import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
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
import net.minecraftforge.fml.common.registry.ExistingSubstitutionException;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wtf.blocks.substitution.CustomOldLeaves;
import wtf.config.CaveBiomesConfig;
import wtf.config.CoreConfig;
import wtf.config.GameplayConfig;
import wtf.config.OverworldGenConfig;
import wtf.config.WTFStoneRegistry;
import wtf.config.ore.WTFOresNewConfig;
import wtf.crafting.GuiHandler;
import wtf.crafting.RecipeParser;
import wtf.init.BlockSets;
import wtf.init.EventListenerRegistry;
import wtf.init.WTFArmor;
import wtf.init.WTFBiomes;
import wtf.init.WTFBlocks;
import wtf.init.WTFEntities;
import wtf.init.WTFItems;
import wtf.init.WTFRecipes;
import wtf.init.WTFSubstitutions;
import wtf.proxy.CommonProxy;
import wtf.utilities.UBC.UBCCompat;
import wtf.utilities.blockstatewriters.BlockstateWriter;

@Mod (modid = Core.coreID, name = Core.coreID, version = Core.version, dependencies = "after:undergroundbiomes")

public class Core {
	public static  final String coreID = "wtfcore";
	public static final String version = "1.10.2_v1.3";

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

		BlockstateWriter.writeResourcePack();
		UBC = Loader.isModLoaded("undergroundbiomes");
	
		CoreConfig.loadConfig();
		
		OverworldGenConfig.loadConfig();
		CaveBiomesConfig.customConfig();

		if (UBC){
			UBCCompat.loadUBCStone();
		}
		else {
			coreLog.info("Underground Biomes Construct not detected");
		}
		
		GameplayConfig.loadConfig();
		WTFStoneRegistry.loadStoneReg();
		BlockSets.initBlockSets();
		WTFBlocks.initBlocks();
		proxy.initWCICRender();
		WTFItems.initItems();
		WTFArmor.initArmor();
		WTFEntities.initEntites();
		WTFRecipes.initRecipes();
		if (CoreConfig.enableOverworldGeneration){
			WTFBiomes.init();
		}
		
		if (CoreConfig.enableOreGen){
			WTFOresNewConfig.loadConfig();
		}

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		
		WTFSubstitutions.init();

		
	}
	
	@EventHandler public void load(FMLInitializationEvent event) throws Exception
	{
		EventListenerRegistry.initListeners();
		
	}
	
	@EventHandler
	public void PostInit(FMLPostInitializationEvent postEvent) throws Exception{

		if (CoreConfig.doResourcePack){
			proxy.enableBlockstateTexturePack();
		}

		if (GameplayConfig.wcictable){
			RecipeParser.init();
		}

	}	





}
