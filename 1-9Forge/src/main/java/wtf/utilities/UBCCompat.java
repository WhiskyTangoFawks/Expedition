package wtf.utilities;

import exterminatorjeff.undergroundbiomes.api.API;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import wtf.Core;
import wtf.blocks.UBCSand;
import wtf.config.CoreConfig;
import wtf.init.BlockSets;
import wtf.init.WTFBlocks;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.StateAndModifier;

public class UBCCompat {

	public static final String[] IgneousStoneList = new String[]{"redGranite", "blackGranite", "rhyolite", "andesite", "gabbro", "basalt", "komatiite", "dacite"};
	public static final String[] MetamorphicStoneList = new String[]{"gneiss", "eclogite", "marble", "quartzite", "blueschist", "greenschist", "soapstone", "migmatite"};
	public static final String[] SedimentaryStoneList = new String[]{"limestone", "chalk", "shale", "siltstone", "ligniteBlock", "dolomite", "greywacke", "chert"};

	public static final String[] IgneousCobblestoneList = new String[]{"redGraniteCobble", "blackGraniteCobble", "rhyoliteCobble", "andesiteCobble", "gabbroCobble", "basaltCobble", "komatiiteCobble", "daciteCobble"};
	public static final String[] MetamorphicCobblestoneList = new String[]{"gneissCobble", "eclogiteCobble", "marbleCobble", "quartziteCobble", "blueschistCobble", "greenschistCobble", "soapstoneCobble", "migmatiteCobble"};


	public static IBlockState[] IgneousStone;
	public static IBlockState[] IgneousCobblestone;
	public static IBlockState[] MetamorphicStone;
	public static IBlockState[] MetamorphicCobblestone;
	public static IBlockState[] SedimentaryStone;

	public static void loadUBCStone(){
		
		Core.coreLog.info("Getting UBC stones");
		
		WTFBlocks.ubcSand = WTFBlocks.registerBlockItemSubblocks(new UBCSand(), 7, "ubcSand");
		
		BlockSets.defaultFallingBlocks.add("undergroundbiomes:igneous_cobblestone@80");
		BlockSets.defaultFallingBlocks.add("undergroundbiomes:metamorphic_cobblestone@70");
		BlockSets.defaultFallingBlocks.add("wtfcore:ubcSand@10");
		
		
		IgneousStone = new IBlockState[8];
		for (int loop = 0; loop < 8; loop++){
			IgneousStone[loop] = API.IGNEOUS_STONE.getBlock().getStateFromMeta(loop);

		}
		
		IgneousCobblestone = new IBlockState[8];
		for (int loop = 0; loop < 8; loop++){
			IgneousCobblestone[loop] = API.IGNEOUS_COBBLE.getBlock().getStateFromMeta(loop);
			CoreConfig.StoneCobble.put(IgneousStone[loop], IgneousCobblestone[loop]);
			
		}
		
		MetamorphicStone = new IBlockState[8];
		for (int loop = 0; loop < 8; loop++){
			MetamorphicStone[loop] = API.METAMORPHIC_STONE.getBlock().getStateFromMeta(loop);
		}
		
		MetamorphicCobblestone = new IBlockState[8];
		for (int loop = 0; loop < 8; loop++){
			MetamorphicCobblestone[loop] = API.METAMORPHIC_COBBLE.getBlock().getStateFromMeta(loop);
			CoreConfig.StoneCobble.put(MetamorphicStone[loop], MetamorphicCobblestone[loop]);
		}
		
		SedimentaryStone = new IBlockState[8];
		for (int loop = 0; loop < 8; loop++){
			SedimentaryStone[loop] = API.SEDIMENTARY_STONE.getBlock().getStateFromMeta(loop);
			CoreConfig.StoneCobble.put(SedimentaryStone[loop], WTFBlocks.ubcSand.getStateFromMeta(loop));
		}

	}

	

	
}
