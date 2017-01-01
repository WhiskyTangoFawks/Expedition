
package wtf.init;

import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wtf.Core;
import wtf.blocks.BlockDecoAnim;
import wtf.blocks.BlockFoxfire;
import wtf.blocks.BlockIcicle;
import wtf.blocks.BlockDecoStatic;
import wtf.blocks.BlockPatchFluid;
import wtf.blocks.BlockPatchIce;
import wtf.blocks.BlockRedCactus;
import wtf.blocks.BlockRoots;
import wtf.blocks.BlockSpeleothem;
import wtf.blocks.BlockMycorrack;
import wtf.blocks.OreNitre;
import wtf.blocks.BlockDecoStatic.DecoType;
import wtf.blocks.redstone.RedstoneStalactite;
import wtf.config.CoreConfig;
import wtf.config.StoneRegEntry;
import wtf.config.WTFStoneRegistry;
import wtf.crafting.WCICTable;
import wtf.crafting.render.WCICTileEntity;
import wtf.gameplay.OreSandGoldNugget;
import wtf.items.ItemBlockState;
import wtf.utilities.blockstatewriters.BlockstateWriter;
import wtf.worldgen.replacers.NetherrackReplacer;

public class WTFBlocks{


	public static HashMap<IBlockState, BlockSpeleothem> speleothemMap = new HashMap<IBlockState, BlockSpeleothem>();
	
	public static Block oreSandGold;
	public static Block oreNitre;
	
	public static Block icePatch;
	public static BlockIcicle icicle;
	public static Block foxfire;
	public static Block mossyDirt;
	public static BlockRoots roots;
	public static IBlockState crackedStone;
	public static Block red_cactus;
	
	public static BlockPatchFluid waterPatch;
	public static BlockPatchFluid lavaPatch;
	public static BlockPatchFluid waterPatchStatic;
	public static BlockPatchFluid lavaPatchStatic;
	public static Block wcicTable;
	public static Block mycorrack;
	public static Block fireLeaves;
	public static Block ubcSand;
	
	public static void initBlocks(){	
		/*
		 * Replacers
		 */
		
		
		
		new NetherrackReplacer();
		
		oreNitre =  registerBlock(new OreNitre(), "nitre_ore");
		icePatch =  registerBlock(new BlockPatchIce(), "patchIce");
		icicle = (BlockIcicle) registerBlockItemSubblocks(new BlockIcicle(Blocks.ICE.getDefaultState()), 2, "icicle");
		foxfire = registerBlock(new BlockFoxfire(), "foxfire");
		roots = (BlockRoots) registerBlockItemSubblocks(new BlockRoots(), 4, "roots");
		oreSandGold = registerBlock(new OreSandGoldNugget(), "oreSandGold");
		
		mossyDirt = Block.getBlockFromName("dirt0decoStatic");
		//BlockstateWriter.writeDecoStaticBlockstate(Blocks.DIRT.getDefaultState(), "dirt0DecoStatic");
		
		red_cactus =  registerBlock(new BlockRedCactus(), "red_cactus");
		mycorrack = registerBlock(new BlockMycorrack().setHardness(0.4F), "mycorrack");
		//fireLeaves = registerBlock(new BlockFireLeaves(), "fireLeaves");
		
		
		waterPatch = (BlockPatchFluid) registerBlock(new BlockPatchFluid(Material.WATER), "patchWater");
		lavaPatch = (BlockPatchFluid) registerBlock(new BlockPatchFluid(Material.LAVA), "patchLava");
		waterPatchStatic = (BlockPatchFluid) registerBlock(new BlockPatchFluid(Material.WATER), "patchWaterStatic");
		lavaPatchStatic = (BlockPatchFluid) registerBlock(new BlockPatchFluid(Material.LAVA), "patchLavaStatic");
		
		waterPatch.otherState = waterPatchStatic.getDefaultState();
		lavaPatch.otherState = lavaPatchStatic.getDefaultState();
		waterPatchStatic.otherState = waterPatch.getDefaultState();
		lavaPatchStatic.otherState = lavaPatch.getDefaultState();
		
		
		//this is done so that the game "sees" base.json, which is used by all the other auto generated jsons
		registerBlock(new Block(Material.AIR), "base");
		
		
		for(Entry<IBlockState, StoneRegEntry> entry : WTFStoneRegistry.stoneReg.entrySet()){
			
			String stoneName = entry.getKey().getBlock().getRegistryName().toString().split(":")[1] + entry.getKey().getBlock().getMetaFromState(entry.getKey());
			//String cobbleName = entry.getValue().cobble.getBlock().getRegistryName().toString().split(":")[1] + entry.getValue().cobble.getBlock().getMetaFromState(entry.getValue().cobble);
			
			if (entry.getValue().speleothem){
				registerBlockItemSubblocks(new BlockSpeleothem(entry.getKey()).setFrozen(stoneName + "Speleothem"), 6, stoneName + "Speleothem");// .setFrozen("stoneSpeleothem");
				BlockstateWriter.writeSpeleothemBlockstate(entry.getKey(), stoneName + "Speleothem");
			}
			
			if (entry.getValue().decoAnim){
				registerBlockItemSubblocks(new BlockDecoAnim(entry.getKey()), BlockDecoAnim.ANIMTYPE.values().length-1, stoneName+"DecoAnim");
				BlockstateWriter.writeDecoAnimBlockstate(entry.getKey(), stoneName+"DecoAnim");
			}
			
			if (entry.getValue().decoStatic){
				registerBlockItemSubblocks(new BlockDecoStatic(entry.getKey()), BlockDecoStatic.DecoType.values().length-1, stoneName+"DecoStatic");
				BlockstateWriter.writeDecoStaticBlockstate(entry.getKey(), stoneName+"DecoStatic");
			}
				
		}
		
		//registerBlockItemSubblocks(new RedstoneStalactite(false).setFrozen("redstoneSpeleothem"), 6, "redstoneSpeleothem");// .setFrozen("stoneSpeleothem");
		//BlockstateWriter.writeSpeleothemBlockstate(Blocks.REDSTONE_ORE.getDefaultState(), "redstoneSpeleothem");
		
		//registerBlockItemSubblocks(new RedstoneStalactite(false).setFrozen("redstoneSpeleothem_on"), 6, "redstoneSpeleothem_on");// .setFrozen("stoneSpeleothem");
		//BlockstateWriter.writeSpeleothemBlockstate(Blocks.LIT_REDSTONE_ORE.getDefaultState(), "redstoneSpeleothem_on");
		
		wcicTable = registerBlock(new WCICTable(), "wcic_table");
		GameRegistry.registerTileEntity(WCICTileEntity.class, "WCICTable");

		//BlockWTFTorch.torch_on = registerBlock(new BlockWTFTorch(true), "torch_on");
		/*
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
		*/
		
		//Alias for Leaves
		//System.out.println("Attempting leaf replacement");

		


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
