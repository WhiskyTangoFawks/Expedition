
package wtf.init;

import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import wtf.Core;
import wtf.blocks.BlockDecoAnim;
import wtf.blocks.BlockFoxfire;
import wtf.blocks.BlockIcicle;
import wtf.blocks.BlockDecoStatic;
import wtf.blocks.BlockClaySlab;
import wtf.blocks.BlockCustomSlab;
import wtf.blocks.BlockPatchIce;
import wtf.blocks.BlockPuddle;
import wtf.blocks.BlockRedCactus;
import wtf.blocks.BlockRoots;
import wtf.blocks.BlockSpeleothem;
import wtf.blocks.GenPlaceHolder;
import wtf.blocks.BlockMycorrack;
import wtf.blocks.OreNitre;
import wtf.blocks.substitution.BlockWTFTorch;
import wtf.config.StoneRegEntry;
import wtf.config.WTFStoneRegistry;
import wtf.crafting.WCICTable;
import wtf.crafting.render.WCICTileEntity;
import wtf.gameplay.OreSandGoldNugget;
import wtf.items.ItemBlockState;
import wtf.worldgen.replacers.NetherrackReplacer;

public class WTFBlocks{


	public static HashMap<IBlockState, BlockSpeleothem> speleothemMap = new HashMap<IBlockState, BlockSpeleothem>();
	
	public static Block oreSandGold;
	public static Block oreNitre;
	
	public static Block icePatch;
	public static BlockIcicle icicle;
	public static Block foxfire;
	public static BlockRoots roots;
	public static Block red_cactus;
	

	public static Block wcicTable;
	public static Block mycorrack;
	public static Block fireLeaves;
	public static Block ubcSand;
	public static Block puddle;
	public static Block dirtSlab;
	public static Block sandSlab;
	public static Block redSandSlab;
	public static Block gravelSlab;
	public static Block mossyDirtSlab;
	public static Block claySlab;
	public static Block podzolSlab;
	
	public static Block genMarker;
	
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
		
		
		//BlockstateWriter.writeDecoStaticBlockstate(Blocks.DIRT.getDefaultState(), "dirt0DecoStatic");
		
		red_cactus =  registerBlock(new BlockRedCactus(), "red_cactus");
		mycorrack = registerBlock(new BlockMycorrack().setHardness(0.4F), "mycorrack");
		//fireLeaves = registerBlock(new BlockFireLeaves(), "fireLeaves");
		
		//this is done so that the game "sees" base.json, which is used by all the other auto generated jsons
		registerBlock(new Block(Material.AIR), "base");
		
		puddle = registerBlock(new BlockPuddle(), "patchWater");
		dirtSlab = registerBlock(new BlockCustomSlab(Blocks.DIRT.getDefaultState()), "slabDirt");
		sandSlab = registerBlock(new BlockCustomSlab(Blocks.SAND.getDefaultState()), "slabSand");
		redSandSlab = registerBlock(new BlockCustomSlab(Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND)), "slabRedsand");
		gravelSlab = registerBlock(new BlockCustomSlab(Blocks.GRAVEL.getDefaultState()), "slabGravel");
		mossyDirtSlab = registerBlock(new BlockCustomSlab(Blocks.DIRT.getDefaultState()), "slabMossyDirt");
		podzolSlab = registerBlock(new BlockCustomSlab(Blocks.DIRT.getDefaultState()), "slabPodzol");
		claySlab = registerBlock(new BlockClaySlab(), "slabClay");
		
		genMarker = registerBlock(new GenPlaceHolder(), "genMarker");
		
		for(Entry<IBlockState, StoneRegEntry> entry : WTFStoneRegistry.stoneReg.entrySet()){
			
			String stoneName = entry.getKey().getBlock().getRegistryName().toString().split(":")[1] + entry.getKey().getBlock().getMetaFromState(entry.getKey());
			//String cobbleName = entry.getValue().cobble.getBlock().getRegistryName().toString().split(":")[1] + entry.getValue().cobble.getBlock().getMetaFromState(entry.getValue().cobble);
			
			
			String localisedName = StringUtils.capitalize(entry.getValue().textureLocation.split("/")[1].replaceAll("_", " "));
			
			if (entry.getValue().speleothem){
				registerBlockItemSubblocks(new BlockSpeleothem(entry.getKey()).setFrozen(stoneName + "Speleothem"), 6, stoneName + "Speleothem");// .setFrozen("stoneSpeleothem");
				Core.proxy.writeSpeleothemBlockstate(entry.getKey(), stoneName + "Speleothem");
				
				Core.proxy.addName(stoneName+"Speleothem.0", localisedName + " Small Stalactite");
				Core.proxy.addName(stoneName+"Speleothem.1", localisedName + " Stalactite Base");
				Core.proxy.addName(stoneName+"Speleothem.2", localisedName + " Stalactite Tip");
				Core.proxy.addName(stoneName+"Speleothem.3", localisedName + " Column");
				Core.proxy.addName(stoneName+"Speleothem.4", localisedName + " Small Stalagmite");
				Core.proxy.addName(stoneName+"Speleothem.5", localisedName + " Stalagmite Base");
				Core.proxy.addName(stoneName+"Speleothem.6", localisedName + " Stalagmite Tip");
				
				Core.proxy.addName(stoneName+"SpeleothemFrozen.0", localisedName + " Icy Small Stalactite");
				Core.proxy.addName(stoneName+"SpeleothemFrozen.1", localisedName + " Icy Stalactite Base");
				Core.proxy.addName(stoneName+"SpeleothemFrozen.2", localisedName + " Icy Stalactite Tip");
				Core.proxy.addName(stoneName+"SpeleothemFrozen.3", localisedName + " Icy Column");
				Core.proxy.addName(stoneName+"SpeleothemFrozen.4", localisedName + " Icy Small Stalagmite");
				Core.proxy.addName(stoneName+"SpeleothemFrozen.5", localisedName + " Icy Stalagmite Base");
				Core.proxy.addName(stoneName+"SpeleothemFrozen.6", localisedName + " Icy Stalagmite Tip");
			}
			
			if (entry.getValue().decoAnim){
				registerBlockItemSubblocks(new BlockDecoAnim(entry.getKey()), BlockDecoAnim.ANIMTYPE.values().length-1, stoneName+"DecoAnim");
				Core.proxy.writeDecoAnimBlockstate(entry.getKey(), stoneName+"DecoAnim");
				
				Core.proxy.addName(stoneName+"DecoAnim.0", localisedName+ " Lava Crust");
				Core.proxy.addName(stoneName+"DecoAnim.1", "Wet " + localisedName);
				Core.proxy.addName(stoneName+"DecoAnim.2", "Dripping Lava " + localisedName);
			}
			
			if (entry.getValue().decoStatic){
				registerBlockItemSubblocks(new BlockDecoStatic(entry.getKey()), BlockDecoStatic.DecoType.values().length-1, stoneName+"DecoStatic");
				Core.proxy.writeDecoStaticBlockstate(entry.getKey(), stoneName+"DecoStatic");
				
				Core.proxy.addName(stoneName+"DecoStatic.0", "Mossy " + localisedName);
				Core.proxy.addName(stoneName+"DecoStatic.1", "Soul "+localisedName);
				Core.proxy.addName(stoneName+"DecoStatic.2", "Cracked "+localisedName);
			}
		}
		
		//registerBlockItemSubblocks(new RedstoneStalactite(false).setFrozen("redstoneSpeleothem"), 6, "redstoneSpeleothem");// .setFrozen("stoneSpeleothem");
		//BlockstateWriter.writeSpeleothemBlockstate(Blocks.REDSTONE_ORE.getDefaultState(), "redstoneSpeleothem");
		
		//registerBlockItemSubblocks(new RedstoneStalactite(false).setFrozen("redstoneSpeleothem_on"), 6, "redstoneSpeleothem_on");// .setFrozen("stoneSpeleothem");
		//BlockstateWriter.writeSpeleothemBlockstate(Blocks.LIT_REDSTONE_ORE.getDefaultState(), "redstoneSpeleothem_on");
		
		wcicTable = registerBlock(new WCICTable(), "wcic_table");
		GameRegistry.registerTileEntity(WCICTileEntity.class, "WCICTable");

		BlockWTFTorch.torch_off = new BlockWTFTorch(false);
		
				
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
