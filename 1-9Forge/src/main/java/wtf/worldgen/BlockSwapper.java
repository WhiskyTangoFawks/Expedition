package wtf.worldgen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockID;
import net.minecraftforge.event.terraingen.ChunkGeneratorEvent;
import net.minecraftforge.event.terraingen.ChunkGeneratorEvent.ReplaceBiomeBlocks;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockSwapper {

	

	
	@SubscribeEvent
	public void filler(ChunkGeneratorEvent.ReplaceBiomeBlocks event) {
		
		
	}
	
	@SubscribeEvent
	public void onVillageSelectBlock(GetVillageBlockID event) {
		IBlockState originalState = event.getOriginal();

		
        if (event.getOriginal().getBlock() == Blocks.LOG) {
            return;
        } 
        if (event.getOriginal().getBlock() == Blocks.PLANKS) {
            return;
        } 
        if (event.getOriginal().getBlock() == Blocks.STONE_SLAB) {
            return;
        }   
        if (event.getOriginal().getBlock() == Blocks.DOUBLE_STONE_SLAB) {
            return;
        } 
        if (event.getOriginal().getBlock() == Blocks.GLASS_PANE) {
            return;
        }   
        if (event.getOriginal().getBlock() == Blocks.AIR) {
            return;
        }
        if (event.getOriginal().getBlock() == Blocks.TORCH) {
            return;
        }
        if (event.getOriginal().getBlock() == Blocks.OBSIDIAN) {
            return;
        }
        if (event.getOriginal().getBlock() == Blocks.WOODEN_PRESSURE_PLATE) {
            return;
        }
        if (event.getOriginal().getBlock() == Blocks.BOOKSHELF) {
            return;
        }
        if (event.getOriginal().getBlock() == Blocks.CHEST) {
            return;
        }
        if (event.getOriginal().getBlock() == Blocks.CRAFTING_TABLE) {
            return;
        }
        if (event.getOriginal().getBlock() == Blocks.FURNACE) {
            return;
        }
        if (event.getOriginal().getBlock() == Blocks.IRON_BARS) {
            return;
        }
        if (event.getOriginal().getBlock() == Blocks.LAVA) {
            return;
        }
        if (event.getOriginal().getBlock() == Blocks.OAK_FENCE) {
            return;
        }
        if (event.getOriginal().getBlock() instanceof BlockDoor) {
            return;
        }
        if (event.getOriginal().getBlock() == Blocks.OAK_STAIRS) {
            return;
        }
        if (event.getOriginal().getBlock() == Blocks.DIRT) {
            return;
        }
        if (event.getOriginal().getBlock() == Blocks.WHEAT) {
            return;
        }
        if (event.getOriginal().getBlock() == Blocks.CARROTS) {
            return;
        }
        if (event.getOriginal().getBlock() == Blocks.POTATOES) {
            return;
        }
        if (event.getOriginal().getBlock() == Blocks.FARMLAND) {
            return;
        }
        if (event.getOriginal().getBlock() == Blocks.WATER) {
            return;
        }
        if (event.getOriginal().getBlock() == Blocks.LADDER) {
            return;
        }
        if (event.getOriginal().getBlock() instanceof BlockColored) {
            return;
        }
        if (event.getOriginal().getBlock() instanceof BlockDynamicLiquid) {
            return;
        }
        if (event.getOriginal().getBlock() == Blocks.GRASS_PATH) {
            return;
        }
        
        if (event.getOriginal().getBlock() == Blocks.GRAVEL) {
            return;
        }
        
       
	}

	
	
	
	
}

