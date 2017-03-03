package wtf.worldgen;

import java.util.HashSet;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.event.terraingen.ChunkGeneratorEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.config.CaveBiomesConfig;
import wtf.init.WTFBlocks;
import wtf.utilities.wrappers.ChunkCoords;

public class SandstoneNaturaliser {

	int sandstoneID = Blocks.SANDSTONE.getDefaultState().hashCode();
	int redsandstonestoneID = Blocks.RED_SANDSTONE.getDefaultState().hashCode();


	
	//this isn't world specific, and it should be
	ChunkCoords current = null;
	
	@SubscribeEvent
	public void chunkGen(InitMapGenEvent event){
		if (event.getType() == EventType.CAVE){
			event.setNewGen(new MapGenWTFCave());
		}
	}



}
