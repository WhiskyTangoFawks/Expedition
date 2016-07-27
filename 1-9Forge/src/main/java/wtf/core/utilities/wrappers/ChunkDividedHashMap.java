package wtf.core.utilities.wrappers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class ChunkDividedHashMap {

	//class with a master hashmap<chunkcoords, hashmap<BlockPos, IBlockState>

	private final HashMap<ChunkCoords, HashMap<BlockPos, IBlockState>> hashmap;
	private final World world;

	public ChunkDividedHashMap(World world, ChunkCoords coords){
		hashmap = new HashMap<ChunkCoords, HashMap<BlockPos, IBlockState>>();
		hashmap.put(coords, new HashMap<BlockPos, IBlockState>());
		this.world = world;
	}

	public void put(BlockPos pos, IBlockState state){
		ChunkCoords coords = new ChunkCoords(pos);
		HashMap<BlockPos, IBlockState> submap = hashmap.get(coords);
		if (submap != null){
			submap.put(pos, state);
		}
		else {
			HashMap<BlockPos, IBlockState> newMap = new HashMap<BlockPos, IBlockState>();
			newMap.put(pos, state);
			hashmap.put(coords, newMap);
		}
	}

	public void putAll(HashMap<BlockPos, IBlockState> mapIn){
		for (Entry<BlockPos, IBlockState> entry : mapIn.entrySet()){
			put(entry.getKey(), entry.getValue());
		}
	}

	public void setBlockSet(){

		Iterator<Entry<ChunkCoords, HashMap<BlockPos, IBlockState>>> master = hashmap.entrySet().iterator();
		//System.out.println("Iterating tree in chunks " + hashmap.size());
		//int count = 1;
		while (master.hasNext()){
			//System.out.println("Count = " + count);
			//count++;
			Entry<ChunkCoords, HashMap<BlockPos, IBlockState>> subMap = master.next();

			Chunk chunk = subMap.getKey().getChunk(world);
		
			Iterator<Entry<BlockPos, IBlockState>> iterator = subMap.getValue().entrySet().iterator();
			Entry<BlockPos, IBlockState> entry = null;
			while (iterator.hasNext()){
				entry = iterator.next();


				if (entry.getKey().getY() > 0 || entry.getKey().getY() < world.getHeight()){



					BlockPos pos = entry.getKey();
					IBlockState state = entry.getValue();

					ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[pos.getY() >> 4];


					if (extendedblockstorage != Chunk.NULL_BLOCK_STORAGE)
					{
						if (state != null){

							//System.out.println("setting " + entry.getKey().density);
							extendedblockstorage.set(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15, state);


						}

					}
					else {
						chunk.setBlockState(entry.getKey(), entry.getValue());
					}

				}
			}
			if (entry != null){
				world.setBlockState(entry.getKey(), entry.getValue());
			}
		}
	}
}
