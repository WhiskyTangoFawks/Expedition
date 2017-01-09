package wtf.utilities.UBC;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import wtf.blocks.BlockDenseOre;
import wtf.blocks.BlockDenseOreFalling;
import wtf.init.BlockSets;
import wtf.ores.ChunkDividedOreMap;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.OrePos;
import wtf.utilities.wrappers.StoneAndOre;

public class UBCChunkDividedOreMap extends ChunkDividedOreMap{

	public UBCChunkDividedOreMap(World world, ChunkCoords coords) {
		super(world, coords);
	}
	
	int stoneHash = Blocks.STONE.getDefaultState().hashCode();
	
	public void setBlockSet(){

		Iterator<Entry<ChunkCoords, HashMap<OrePos, IBlockState>>> master = hashmap.entrySet().iterator();
		
		while (master.hasNext()){
			Entry<ChunkCoords, HashMap<OrePos, IBlockState>> subMap = master.next();

			Chunk chunk = subMap.getKey().getChunk(world);

			Iterator<Entry<OrePos, IBlockState>> iterator = subMap.getValue().entrySet().iterator();
			while (iterator.hasNext()){
				Entry<OrePos, IBlockState> entry = iterator.next();

				
				
				if (entry.getKey().getY() > 1 && entry.getKey().getY() < world.getHeight()){
					//System.out.println("ypos = " + entry.getKey().getY());

					BlockPos pos = entry.getKey();
					ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[pos.getY() >> 4];
					if (extendedblockstorage != Chunk.NULL_BLOCK_STORAGE)
					{

						IBlockState oreType = entry.getValue();
						
						IBlockState stone = extendedblockstorage.get(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15);
						
						if (stone.hashCode() == stoneHash){
							stone = ReplacerUBCAbstract.getUBCStone(pos);
						}
						
						StoneAndOre stoneandore = new StoneAndOre(stone, oreType);
						
						IBlockState state = BlockSets.stoneAndOre.get(stoneandore);
						if (state != null){
							if (state.getBlock() instanceof BlockDenseOre){
								state = state.withProperty(BlockDenseOre.DENSITY, entry.getKey().density);
							}
							else if (state.getBlock() instanceof BlockDenseOreFalling){
								state = state.withProperty(BlockDenseOreFalling.DENSITY, entry.getKey().density);
								
							}
							if (state != null){
								extendedblockstorage.set(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15, state);
								//chunk.setBlockState(pos.up(100), state);
							}
						}
					}
					else {
						IBlockState oreType = entry.getValue();
						
						IBlockState stone = chunk.getBlockState(pos);
						
						if (stone.hashCode() == stoneHash){
							stone = ReplacerUBCAbstract.getUBCStone(pos);
						}
						
						StoneAndOre stoneandore = new StoneAndOre(stone, oreType);
						IBlockState state = BlockSets.stoneAndOre.get(stoneandore);
						if (state != null){
							
							if (state.getBlock() instanceof BlockDenseOre){
								state = state.withProperty(BlockDenseOre.DENSITY, entry.getKey().density);								
							}
							else if (state.getBlock() instanceof BlockDenseOreFalling){
								state = state.withProperty(BlockDenseOreFalling.DENSITY, entry.getKey().density);
								
							}
							if (state != null){
								
								chunk.setBlockState(pos, state);
							}
						}
					}
				}
			}
		}
	}

}
