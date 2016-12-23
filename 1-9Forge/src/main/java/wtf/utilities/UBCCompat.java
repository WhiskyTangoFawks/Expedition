package wtf.utilities;

import exterminatorjeff.undergroundbiomes.api.API;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wtf.Core;
import wtf.blocks.UBC.UBCSand;
import wtf.config.CoreConfig;
import wtf.config.WTFStoneRegistry;
import wtf.init.BlockSets;
import wtf.init.WTFBlocks;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.StateAndModifier;

public class UBCCompat {

	public static final String[] IgneousStoneList = new String[]{"red_granite", "black_granite", "rhyolite", "andesite", "gabbro", "basalt", "komatiite", "dacite"};
	public static final String[] MetamorphicStoneList = new String[]{"gneiss", "eclogite", "marble", "quartzite", "blueschist", "greenschist", "soapstone", "migmatite"};
	public static final String[] SedimentaryStoneList = new String[]{"limestone", "chalk", "shale", "siltstone", "lignite", "dolomite", "greywacke", "chert"};

	public static final String[] IgneousCobblestoneList = new String[]{"redGraniteCobble", "blackGraniteCobble", "rhyoliteCobble", "andesiteCobble", "gabbroCobble", "basaltCobble", "komatiiteCobble", "daciteCobble"};
	public static final String[] MetamorphicCobblestoneList = new String[]{"gneissCobble", "eclogiteCobble", "marbleCobble", "quartziteCobble", "blueschistCobble", "greenschistCobble", "soapstoneCobble", "migmatiteCobble"};


	public static IBlockState[] IgneousStone;
	public static IBlockState[] IgneousCobblestone;
	public static IBlockState[] MetamorphicStone;
	public static IBlockState[] MetamorphicCobblestone;
	public static IBlockState[] SedimentaryStone;
	
	public static String UBCStoneList = "";

	public static void loadUBCStone(){
		
		Core.coreLog.info("Getting UBC stones");
		
		WTFBlocks.ubcSand = WTFBlocks.registerBlockItemSubblocks(new UBCSand(), 7, "ubcSand");
		
		BlockSets.defaultFallingBlocks.add("undergroundbiomes:igneous_cobblestone@80");
		BlockSets.defaultFallingBlocks.add("undergroundbiomes:metamorphic_cobblestone@70");
		BlockSets.defaultFallingBlocks.add("wtfcore:ubcSand@10");
		
		
		
		IgneousStone = new IBlockState[8];
		for (int loop = 0; loop < 8; loop++){
			IgneousStone[loop] = API.IGNEOUS_STONE.getBlock().getStateFromMeta(loop);
			UBCStoneList += IgneousStone[loop].getBlock().getRegistryName()+"@"+loop+", ";
			WTFStoneRegistry.defTextureLocations.put(IgneousStone[loop], "undergroundbiomes:blocks/"+IgneousStoneList[loop]);
			WTFStoneRegistry.defBlockStateLocations.put(IgneousStone[loop], "undergroundbiomes:igneous_stone#type="+IgneousStoneList[loop]);

		}
		
		IgneousCobblestone = new IBlockState[8];
		for (int loop = 0; loop < 8; loop++){
			IgneousCobblestone[loop] = API.IGNEOUS_COBBLE.getBlock().getStateFromMeta(loop);
			//CoreConfig.StoneCobble.put(IgneousStone[loop], IgneousCobblestone[loop]);
		}
		
		MetamorphicStone = new IBlockState[8];
		for (int loop = 0; loop < 8; loop++){
			MetamorphicStone[loop] = API.METAMORPHIC_STONE.getBlock().getStateFromMeta(loop);
			UBCStoneList += MetamorphicStone[loop].getBlock().getRegistryName()+"@"+loop+", ";
			WTFStoneRegistry.defTextureLocations.put(MetamorphicStone[loop], "undergroundbiomes:blocks/"+MetamorphicStoneList[loop]);
			WTFStoneRegistry.defBlockStateLocations.put(MetamorphicStone[loop], "undergroundbiomes:metamorphic_stone#type="+MetamorphicStoneList[loop]);
		}
		
		MetamorphicCobblestone = new IBlockState[8];
		for (int loop = 0; loop < 8; loop++){
			MetamorphicCobblestone[loop] = API.METAMORPHIC_COBBLE.getBlock().getStateFromMeta(loop);
			//CoreConfig.StoneCobble.put(MetamorphicStone[loop], MetamorphicCobblestone[loop]);
		}
		
		SedimentaryStone = new IBlockState[8];
		for (int loop = 0; loop < 8; loop++){
			SedimentaryStone[loop] = API.SEDIMENTARY_STONE.getBlock().getStateFromMeta(loop);
			WTFStoneRegistry.defCobble.put(SedimentaryStone[loop], "wtfcore:ubcSand@"+loop);
			UBCStoneList += SedimentaryStone[loop].getBlock().getRegistryName()+"@"+loop+", ";
			WTFStoneRegistry.defTextureLocations.put(SedimentaryStone[loop], "undergroundbiomes:blocks/"+SedimentaryStoneList[loop]);
			WTFStoneRegistry.defBlockStateLocations.put(SedimentaryStone[loop], "undergroundbiomes:sedimentary_stone#type="+SedimentaryStoneList[loop]);
		}
		
		System.out.println("Stones: " + UBCStoneList);
		
		for (int loop = 0; loop < 8; loop++){
			GameRegistry.addShapelessRecipe(new ItemStack(API.SEDIMENTARY_STONE.getBlock(), 4, loop), new Object[] {new ItemStack(WTFBlocks.ubcSand, 1, loop), new ItemStack(WTFBlocks.ubcSand, 1, loop), new ItemStack(WTFBlocks.ubcSand, 1, loop), new ItemStack(WTFBlocks.ubcSand, 1, loop)});
		}
		
		//StainedGlass
		//item size meta
		GameRegistry.addSmelting(new ItemStack(WTFBlocks.ubcSand, 1, 0), new ItemStack(Blocks.STAINED_GLASS, 1, 9), 0F);
		GameRegistry.addSmelting(new ItemStack(WTFBlocks.ubcSand, 1, 1), new ItemStack(Blocks.STAINED_GLASS, 1, 0), 0F);
		GameRegistry.addSmelting(new ItemStack(WTFBlocks.ubcSand, 1, 2), new ItemStack(Blocks.STAINED_GLASS, 1, 8), 0F);
		GameRegistry.addSmelting(new ItemStack(WTFBlocks.ubcSand, 1, 3), new ItemStack(Blocks.STAINED_GLASS, 1, 1), 0F);
		GameRegistry.addSmelting(new ItemStack(WTFBlocks.ubcSand, 1, 4), new ItemStack(Blocks.STAINED_GLASS, 1, 15), 0F);
		GameRegistry.addSmelting(new ItemStack(WTFBlocks.ubcSand, 1, 5), new ItemStack(Blocks.STAINED_GLASS, 1, 4), 0F);
		GameRegistry.addSmelting(new ItemStack(WTFBlocks.ubcSand, 1, 6), new ItemStack(Blocks.STAINED_GLASS, 1, 7), 0F);
		GameRegistry.addSmelting(new ItemStack(WTFBlocks.ubcSand, 1, 7), new ItemStack(Blocks.STAINED_GLASS, 1, 12), 0F);
		GameRegistry.addSmelting(new ItemStack(Blocks.SAND, 1, 1), new ItemStack(Blocks.STAINED_GLASS, 1, 14), 0F);

	}

	

	
}
