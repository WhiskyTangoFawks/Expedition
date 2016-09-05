package wtf;

import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
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
import wtf.api.WTFWorldGen;
import wtf.blocks.BlockWTFTorch;
import wtf.config.CaveBiomesConfig;
import wtf.config.CoreConfig;
import wtf.config.GameplayConfig;
import wtf.config.OverworldGenConfig;
import wtf.crafting.GuiHandler;
import wtf.crafting.RecipeParser;
import wtf.entities.EntitySpawnListener;
import wtf.gameplay.AppleCoreEvents;
import wtf.gameplay.GamePlayEventListener;
import wtf.init.BlockSets;
import wtf.init.LootEventListener;
import wtf.init.WTFArmor;
import wtf.init.WTFBlocks;
import wtf.init.WTFEntities;
import wtf.init.WTFItems;
import wtf.init.WTFRecipes;
import wtf.ores.OreGenerator;
import wtf.ores.VanillOreGenCatcher;
import wtf.ores.config.WTFOreConfig;
import wtf.proxy.CommonProxy;
import wtf.worldgen.DungeonPopulator;
import wtf.worldgen.PopulationDecorator;
import wtf.worldgen.trees.WorldGenTreeCancel;
import wtf.worldscan.CoreWorldGenListener;

@Mod (modid = Core.coreID, name = Core.coreID, version = Core.version)

public class Core {
	public static  final String coreID = "wtfcore";
	public static final String version = "1.10_BetaX";

	@SidedProxy(clientSide="wtf.proxy.ClientProxy", serverSide="wtf.proxy.CommonProxy")
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
		OverworldGenConfig.loadConfig();
		CaveBiomesConfig.customConfig();


		BlockSets.initBlockSets();
		WTFBlocks.initBlocks(preEvent);
		WTFItems.initItems();
		WTFArmor.initArmor();
		WTFEntities.initEntites();
		WTFRecipes.initRecipes();
		

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		
		
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

		if (CoreConfig.dungeonGeneration){
			WTFWorldGen.addGen(new DungeonPopulator());
		}
		
		WTFWorldGen.addGen(new PopulationDecorator());
		
		if (CoreConfig.mobReplacement){
			MinecraftForge.EVENT_BUS.register(new EntitySpawnListener());
		}

		if (CoreConfig.gameplaytweaks){
			MinecraftForge.EVENT_BUS.register(new GamePlayEventListener());
		}

		if (CoreConfig.enableOreGen){
			MinecraftForge.ORE_GEN_BUS.register(new VanillOreGenCatcher());
			WTFWorldGen.addGen(new OreGenerator());
		}
		
		if (CoreConfig.gameplaytweaks){
			MinecraftForge.EVENT_BUS.register(new LootEventListener());
		}

		if (CoreConfig.enableOverworldGeneration){
			if (OverworldGenConfig.genTrees){
				MinecraftForge.TERRAIN_GEN_BUS.register(new WorldGenTreeCancel());
				//WTFWorldGen.addGen(new TreePopulator());
				MinecraftForge.EVENT_BUS.register(new WorldGenTreeCancel());
			}

		}
		
		if (Loader.isModLoaded("AppleCore")){
			coreLog.info("AppleCore detected, registering integration");
			MinecraftForge.EVENT_BUS.register(new AppleCoreEvents());
		}

	}
	@EventHandler
	public void PostInit(FMLPostInitializationEvent postEvent) throws Exception{

		//Blocks.LEAVES.setLightOpacity(0);
		//Blocks.LEAVES2.setLightOpacity(0);
		System.out.println("Torch class is now " + Blocks.TORCH.getClass());
		
		RecipeParser.init();
		
	}	





}
