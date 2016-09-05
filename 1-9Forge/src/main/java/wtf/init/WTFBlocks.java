
package wtf.init;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ExistingSubstitutionException;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wtf.blocks.AnimatedBlock;
import wtf.blocks.BlockCrackedStone;
import wtf.blocks.BlockFoxfire;
import wtf.blocks.BlockIcicle;
import wtf.blocks.BlockMossy;
import wtf.blocks.BlockPatchFluid;
import wtf.blocks.BlockPatchIce;
import wtf.blocks.BlockRoots;
import wtf.blocks.BlockSpeleothem;
import wtf.blocks.BlockWTFTorch;
import wtf.blocks.OreNitre;
import wtf.blocks.OreSandGoldNugget;
import wtf.blocks.redstone.RedstoneStalactite;
import wtf.config.CoreConfig;
import wtf.config.GameplayConfig;
import wtf.core.Core;
import wtf.crafting.WCICTable;
import wtf.items.ItemBlockState;

public class WTFBlocks {

	public static HashMap<IBlockState, BlockSpeleothem> speleothemMap = new HashMap<IBlockState, BlockSpeleothem>();
	
	public static Block oreSandGold;
	public static Block oreNitre;
	
	public static Block icePatch;
	public static BlockIcicle icicle;
	public static Block foxfire;
	public static Block mossyStone;
	public static Block decoStone;
	public static BlockRoots roots;
	public static Block crackedStone;
	
	public static BlockPatchFluid waterPatch;
	public static BlockPatchFluid lavaPatch;
	public static BlockPatchFluid waterPatchStatic;
	public static BlockPatchFluid lavaPatchStatic;
	public static Block wcicTable;
	
	public static void initBlocks(FMLPreInitializationEvent preEvent){	
		oreNitre =  registerBlock(new OreNitre(), "oreNitre");
		icePatch =  registerBlock(new BlockPatchIce(), "patchIce");
		icicle = (BlockIcicle) registerBlockItemSubblocks(new BlockIcicle(Blocks.ICE.getDefaultState()), 2, "icicle");
		foxfire = registerBlock(new BlockFoxfire(), "foxfire");
		roots = (BlockRoots) registerBlockItemSubblocks(new BlockRoots(), 4, "roots");
		oreSandGold = registerBlock(new OreSandGoldNugget(), "oreSandGold");
		crackedStone = registerBlock(new BlockCrackedStone(Blocks.STONE.getDefaultState()), "cracked_stone");
		
		waterPatch = (BlockPatchFluid) registerBlock(new BlockPatchFluid(Material.WATER), "patchWater");
		lavaPatch = (BlockPatchFluid) registerBlock(new BlockPatchFluid(Material.LAVA), "patchLava");
		waterPatchStatic = (BlockPatchFluid) registerBlock(new BlockPatchFluid(Material.WATER), "patchWaterStatic");
		lavaPatchStatic = (BlockPatchFluid) registerBlock(new BlockPatchFluid(Material.LAVA), "patchLavaStatic");
		
		waterPatch.otherState = waterPatchStatic.getDefaultState();
		lavaPatch.otherState = lavaPatchStatic.getDefaultState();
		waterPatchStatic.otherState = waterPatch.getDefaultState();
		lavaPatchStatic.otherState = lavaPatch.getDefaultState();
		
		
		registerBlockItemSubblocks(new RedstoneStalactite(false).setFrozen("redstoneSpeleothem"), 6, "redstoneSpeleothem");// .setFrozen("stoneSpeleothem");
		registerBlockItemSubblocks(new RedstoneStalactite(false).setFrozen("redstoneSpeleothem_on"), 6, "redstoneSpeleothem_on");// .setFrozen("stoneSpeleothem");
		
		registerBlockItemSubblocks(new BlockSpeleothem(Blocks.STONE.getDefaultState()).setFrozen("stoneSpeleothem"), 6, "stoneSpeleothem");// .setFrozen("stoneSpeleothem");
		registerBlockItemSubblocks(new BlockSpeleothem(Blocks.SANDSTONE.getDefaultState()).setFrozen("sandstoneSpeleothem"), 6, "sandstoneSpeleothem");//);

		
		//All Speleothems must be registered prior to decorative blocks
		//switch this over to a block transformation placement map
		mossyStone = registerBlock(new BlockMossy(Blocks.STONE.getDefaultState()), "overlayStone");
		
		decoStone = registerBlockItemSubblocks(new AnimatedBlock(Blocks.STONE.getDefaultState()), 2, "animStone");

		registerBlock(new BlockMossy(Blocks.DIRT.getDefaultState()), "overlayDirt");
		
		BlockWTFTorch.torch_on = registerBlock(new BlockWTFTorch(true), "torch_on");
		
		wcicTable = registerBlock(new WCICTable(), "wcic_table");
		
		if (CoreConfig.gameplaytweaks && GameplayConfig.torchLifespan > -1){
			
			BlockWTFTorch.torch_off = new BlockWTFTorch(false);
			BlockWTFTorch.torch_off.setRegistryName("torch");
			//BlockWTFTorch.torch_off.setUnlocalizedName("torch");

			System.out.println("Attempting torch replacement");
			try {
				GameRegistry.addSubstitutionAlias("minecraft:torch", GameRegistry.Type.BLOCK, BlockWTFTorch.torch_off);

			} catch (ExistingSubstitutionException e) {
				e.printStackTrace();
			}
			
		
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
