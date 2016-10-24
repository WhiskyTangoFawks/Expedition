package wtf;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
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
import wtf.api.WTFWorldGen;
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
import wtf.init.WTFBiomes;
import wtf.init.WTFBlocks;
import wtf.init.WTFEntities;
import wtf.init.WTFItems;
import wtf.init.WTFRecipes;
import wtf.ores.OreGenerator;
import wtf.ores.VanillOreGenCatcher;
import wtf.ores.config.WTFOreConfig;
import wtf.proxy.CommonProxy;
import wtf.worldgen.DungeonPopulator;
import wtf.worldgen.OverworldGen;
import wtf.worldgen.PopulationDecorator;
import wtf.worldgen.RTGOverworldGen;
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
		proxy.initWCICRender();
		WTFItems.initItems();
		WTFArmor.initArmor();
		WTFEntities.initEntites();
		WTFRecipes.initRecipes();
		if (CoreConfig.enableOverworldGeneration){
			WTFBiomes.init();
		}
		

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		
		
		if (CoreConfig.enableOreGen){
			WTFOreConfig.loadConfig();
		}
	}
	@EventHandler public void load(FMLInitializationEvent event) throws Exception
	{
		OverworldGenConfig.postLoadConfig();

		MinecraftForge.EVENT_BUS.register(new CoreWorldGenListener());
		MinecraftForge.TERRAIN_GEN_BUS.register(new CoreWorldGenListener());

		if (CoreConfig.dungeonGeneration){
			WTFWorldGen.addGen(new DungeonPopulator());
		}
	
		if (CoreConfig.mobReplacement){
			MinecraftForge.EVENT_BUS.register(new EntitySpawnListener());
		}

		if (CoreConfig.gameplaytweaks){
			MinecraftForge.EVENT_BUS.register(new GamePlayEventListener());
			MinecraftForge.EVENT_BUS.register(new LootEventListener());
		}

		if (CoreConfig.enableOreGen){
			MinecraftForge.ORE_GEN_BUS.register(new VanillOreGenCatcher());
			WTFWorldGen.addGen(new OreGenerator());
		}
		
		if (CoreConfig.enableOverworldGeneration){
			if (OverworldGenConfig.genTrees){
			MinecraftForge.TERRAIN_GEN_BUS.register(new WorldGenTreeCancel());
				MinecraftForge.EVENT_BUS.register(new WorldGenTreeCancel());
			}
		}
		
		if (Loader.isModLoaded("AppleCore")){
			coreLog.info("AppleCore detected, registering integration");
			AppleCoreEvents.initGrowthMap();
			MinecraftForge.EVENT_BUS.register(new AppleCoreEvents());
		}
		
		if (CoreConfig.enableOverworldGeneration){
			if (Loader.isModLoaded("RTG")){
				coreLog.info(";RTG detected, enabling integration");
				WTFWorldGen.addGen(new RTGOverworldGen());
			}
			else {
				
				WTFWorldGen.addGen(new OverworldGen());
			}
		}
		
		WTFWorldGen.addGen(new PopulationDecorator());
		

	}
	@EventHandler
	public void PostInit(FMLPostInitializationEvent postEvent) throws Exception{

		//Blocks.LEAVES.setLightOpacity(0);
		//Blocks.LEAVES2.setLightOpacity(0);
		System.out.println("Torch class is now " + Blocks.TORCH.getClass());
		
		RecipeParser.init();
	
		/*
		if (CoreConfig.enableOverworldGeneration && OverworldGenConfig.fixBirchLeaves){
			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor()
			{
				@Override
				public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
				{
					BlockPlanks.EnumType blockplanks$enumtype = state.getValue(BlockOldLeaf.VARIANT);
					return blockplanks$enumtype == BlockPlanks.EnumType.SPRUCE ? ColorizerFoliage.getFoliageColorPine() :  (worldIn != null && pos != null ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos) : ColorizerFoliage.getFoliageColorBasic());
				}
			}, new Block[] {Blocks.LEAVES});
		coreLog.info("Birch leaves registered to the colour handler");
		}
		*/
		
	}	





}
