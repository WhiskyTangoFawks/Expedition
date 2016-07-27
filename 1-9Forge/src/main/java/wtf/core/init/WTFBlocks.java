
package wtf.core.init;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wtf.core.Core;
import wtf.core.blocks.BlockLitTorch;
import wtf.core.blocks.BlockMossy;
import wtf.core.blocks.AnimatedBlock;
import wtf.core.blocks.BlockDenseOre;
import wtf.core.blocks.BlockFoxfire;
import wtf.core.blocks.BlockPatchIce;
import wtf.core.blocks.BlockIcicle;
import wtf.core.blocks.BlockNitreOre;
import wtf.core.blocks.BlockRoots;
import wtf.core.blocks.BlockSpeleothem;
import wtf.core.blocks.BlockPatchFluid;
import wtf.core.config.CoreConfig;
import wtf.core.init.BlockSets.Modifier;
import wtf.core.items.ItemBlockState;
import wtf.core.utilities.wrappers.StateAndModifier;
import wtf.core.worldgen.replacers.TorchReplacer;

public class WTFBlocks {

	public static HashMap<IBlockState, BlockSpeleothem> speleothemMap = new HashMap<IBlockState, BlockSpeleothem>();
	
	public static Block oreNitre;
	public static Block litTorch;
	
	public static Block icePatch;
	public static BlockIcicle icicle;
	public static Block foxfire;
	public static Block mossyStone;
	public static Block decoStone;
	public static BlockRoots roots;
	
	public static BlockPatchFluid waterPatch;
	public static BlockPatchFluid lavaPatch;
	public static BlockPatchFluid waterPatchStatic;
	public static BlockPatchFluid lavaPatchStatic;
	
	public static void initBlocks(){	
		oreNitre =  registerBlock(new BlockNitreOre(), "oreNitre");
		icePatch =  registerBlock(new BlockPatchIce(), "patchIce");
		icicle = (BlockIcicle) registerBlockItemSubblocks(new BlockIcicle(Blocks.ICE.getDefaultState()), 2, "icicle");
		foxfire = registerBlock(new BlockFoxfire(), "foxfire");
		roots = (BlockRoots) registerBlockItemSubblocks(new BlockRoots(), 4, "roots");
		
		waterPatch = (BlockPatchFluid) registerBlock(new BlockPatchFluid(Material.WATER), "patchWater");
		lavaPatch = (BlockPatchFluid) registerBlock(new BlockPatchFluid(Material.LAVA), "patchLava");
		waterPatchStatic = (BlockPatchFluid) registerBlock(new BlockPatchFluid(Material.WATER), "patchWaterStatic");
		lavaPatchStatic = (BlockPatchFluid) registerBlock(new BlockPatchFluid(Material.LAVA), "patchLavaStatic");
		
		waterPatch.otherState = waterPatchStatic.getDefaultState();
		lavaPatch.otherState = lavaPatchStatic.getDefaultState();
		waterPatchStatic.otherState = waterPatch.getDefaultState();
		lavaPatchStatic.otherState = lavaPatch.getDefaultState();
		
		registerBlockItemSubblocks(new BlockSpeleothem(Blocks.STONE.getDefaultState()).setFrozen("stoneSpeleothem"), 6, "stoneSpeleothem");// .setFrozen("stoneSpeleothem");
		registerBlockItemSubblocks(new BlockSpeleothem(Blocks.SANDSTONE.getDefaultState()).setFrozen("sandstoneSpeleothem"), 6, "sandstoneSpeleothem");//);

		
		//All Speleothems must be registered prior to decorative blocks
		//switch this over to a block transformation placement map
		mossyStone = registerBlock(new BlockMossy(Blocks.STONE.getDefaultState()), "overlayStone");
		
		decoStone = registerBlockItemSubblocks(new AnimatedBlock(Blocks.STONE.getDefaultState()), 2, "animStone");

		registerBlock(new BlockMossy(Blocks.DIRT.getDefaultState()), "overlayDirt");
		
		if (CoreConfig.gameplaytweaks){
			litTorch = registerBlock(new BlockLitTorch(), "lit_torch");
			
			new TorchReplacer(Blocks.TORCH);
		}
		
	}

	public static Block registerBlock(Block block, String name){
		block.setRegistryName(name);
		block.setUnlocalizedName(name);
		GameRegistry.register(block);
		ItemBlock temp = (ItemBlock) new ItemBlock(block).setUnlocalizedName(name).setRegistryName(name).setHasSubtypes(true);
		GameRegistry.register(temp);
		Core.proxy.registerItemRenderer(block);
		return block;
	}
	
	/**
	 * Called to register blocks with subblocks that should appear in the inventory
	 * Requires a blockstate json, with values for both the block models and inventory models
	 * @param block
	 * @param meta - value of the maximus metadata
	 * @param name
	 * @return
	 */
	public static Block registerBlockItemSubblocks(Block block, int meta, String name){
		block.setRegistryName(name);
		block.setUnlocalizedName(name);
		GameRegistry.register(block);
		ItemBlock temp = (ItemBlock) new ItemBlockState(block).setUnlocalizedName(name).setRegistryName(name).setHasSubtypes(true);
		GameRegistry.register(temp);
		Core.proxy.registerItemSubblocksRenderer(block, meta);
		return block;
	}
}
