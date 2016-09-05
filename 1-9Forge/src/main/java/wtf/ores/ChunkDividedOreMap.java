package wtf.ores;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import wtf.blocks.BlockDenseOre;
import wtf.config.GameplayConfig;
import wtf.init.BlockSets;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.OrePos;
import wtf.utilities.wrappers.StoneAndOre;

public class ChunkDividedOreMap{

	private final HashMap<ChunkCoords, HashMap<OrePos, IBlockState>> hashmap;
	private final World world;

	public ChunkDividedOreMap(World world, ChunkCoords coords){
		hashmap = new HashMap<ChunkCoords, HashMap<OrePos, IBlockState>>();
		hashmap.put(coords, new HashMap<OrePos, IBlockState>());
		this.world = world;
	}

	public void put(OrePos pos, IBlockState state){
		ChunkCoords coords = new ChunkCoords(pos);
		HashMap<OrePos, IBlockState> submap = hashmap.get(coords);
		if (submap != null){
			submap.put(pos, state);
		}
		else {
			HashMap<OrePos, IBlockState> newMap = new HashMap<OrePos, IBlockState>();
			newMap.put(pos, state);
			hashmap.put(coords, newMap);
		}
	}

	public void putAll(HashMap<OrePos, IBlockState> mapIn){
		for (Entry<OrePos, IBlockState> entry : mapIn.entrySet()){
			put(entry.getKey(), entry.getValue());
		}
	}


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
						
						StoneAndOre stoneandore = new StoneAndOre(extendedblockstorage.get(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15), oreType);
						IBlockState state = BlockSets.stoneAndOre.get(stoneandore);
						if (state != null){
							if (state.getBlock() instanceof BlockDenseOre){
								state = state.withProperty(BlockDenseOre.DENSITY, entry.getKey().density);
							}


							if (state != null){
								extendedblockstorage.set(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15, state);
							}

						}

					}
					else {
						IBlockState oreType = entry.getValue();
						StoneAndOre stoneandore = new StoneAndOre(chunk.getBlockState(pos), oreType);
						IBlockState state = BlockSets.stoneAndOre.get(stoneandore);
						if (state != null){
							if (state.getBlock() instanceof BlockDenseOre){
								state = state.withProperty(BlockDenseOre.DENSITY, entry.getKey().density);
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
