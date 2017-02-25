package wtf.utilities.wrappers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import wtf.init.BlockSets;
import wtf.worldgen.CoreWorldGenListener;
import wtf.worldgen.generators.queuedgen.QReplaceNoCheck;
import wtf.worldgen.generators.queuedgen.QueuedGenerator;

public class BlockMap {

	private final HashMap<ChunkStorCoords, HashMap<StoragePos, Queue<QueuedGenerator>>> map;
	public final ChunkCoords coords;
	private final World world;

	public BlockMap(World world, ChunkCoords coords){
		this.world = world;
		this.coords = coords;
		this.map = new HashMap<ChunkStorCoords, HashMap<StoragePos, Queue<QueuedGenerator>>>();
	}

	public boolean posQueued(BlockPos pos){
		return map.get(new ChunkStorCoords(pos)) != null ? map.get(new ChunkStorCoords(pos)).get(new StoragePos(pos)) != null : false;
	}


	public boolean add(BlockPos pos, QueuedGenerator qg){
		if (qg != null){
			if (pos.getY() < 0 || pos.getY() >= world.getHeight()){
				return false;
			}
			ChunkStorCoords storcoords = new ChunkStorCoords(pos);
			HashMap<StoragePos, Queue<QueuedGenerator>> exstor = map.get(storcoords);
			if (exstor == null){
				exstor = new HashMap<StoragePos, Queue<QueuedGenerator>>();
				map.put(storcoords, exstor);
			}
			Queue<QueuedGenerator> q = exstor.get(pos);
			if (q == null){
				q = new LinkedList<QueuedGenerator>();
				exstor.put(new StoragePos(pos), q);
			}
			return q.add(qg);
		}
		
			try {
				throw new Exception("Trying to add a null QueuedGenerator");
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return false;
	}

	public void setBlocks(){

		HashSet<Chunk> chunks = new HashSet<Chunk>();
		for (Entry<ChunkStorCoords, HashMap<StoragePos, Queue<QueuedGenerator>>> mapentry : map.entrySet()){

			ExtendedBlockStorage extendedblockstorage = mapentry.getKey().getStorage(world);
			//chunks.add(world.getChunkFromChunkCoords(mapentry.getKey().chunkX, mapentry.getKey().chunkZ));
			
			for (Entry<StoragePos, Queue<QueuedGenerator>> entry : mapentry.getValue().entrySet()){

				IBlockState oldstate = extendedblockstorage.get(entry.getKey().x, entry.getKey().y, entry.getKey().z);
				while (!entry.getValue().isEmpty()){
					QueuedGenerator qg = entry.getValue().poll();
					IBlockState newstate = qg.getBlockState(oldstate);

					if (newstate != null && (qg instanceof QReplaceNoCheck || BlockSets.ReplaceHashset.contains(oldstate.getBlock()))){
						extendedblockstorage.set(entry.getKey().x, entry.getKey().y, entry.getKey().z, newstate);
						oldstate = newstate;
						
					}
				}
				//notify after the queue has finished polling, not during
				world.notifyBlockUpdate(entry.getKey().pos, oldstate, oldstate, 3);
				//world.markAndNotifyBlock(entry.getKey().pos, world.getChunkFromChunkCoords(mapentry.getKey().chunkX, mapentry.getKey().chunkZ), oldstate, oldstate, 3);
				
				
			}
		}
		CoreWorldGenListener.deRegGenerator(world, coords);
		
		for (Chunk chunk :chunks){
			chunk.checkLight();;
		}
		
	}



	private class ChunkStorCoords{

		@Override
		public int hashCode() {
			int relX = chunkX - coords.getChunkX();
			int relZ = chunkZ - coords.getChunkZ();
			int xz = relX+100 + (relZ+10)*1000;
			return 16*xz + storY;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ChunkStorCoords other = (ChunkStorCoords) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (chunkX != other.chunkX)
				return false;
			if (chunkZ != other.chunkZ)
				return false;
			if (storY != other.storY)
				return false;
			return true;
		}

		private final int chunkX;
		private final int chunkZ;
		private final int storY;

		public ChunkStorCoords(BlockPos pos){
			chunkX = pos.getX() >> 4; 
			chunkZ = pos.getZ() >> 4;
			storY = pos.getY() >> 4;

		}

		private BlockMap getOuterType() {
			return BlockMap.this;
		}

		
		
		private ExtendedBlockStorage getStorage(World world) {
			Chunk chunk =  world.getChunkFromChunkCoords(chunkX, chunkZ);

			if (!coords.exists(world)){// && MasterConfig.genWarning){
				//try {
				//throw new Exception("The generator is attempting to get an ungenerated chunk. "+ this.chunkX + " " + this.chunkZ);
				System.out.println("The generator is attempting to get an ungenerated chunk. "+ this.chunkX + " " + this.chunkZ);
				//} catch (Exception e) {
				//e.printStackTrace();
				//}
			}

			ExtendedBlockStorage storage = chunk.getBlockStorageArray()[storY];
			if (storage == Chunk.NULL_BLOCK_STORAGE){
				storage = new ExtendedBlockStorage(storY << 4, !world.provider.getHasNoSky());
				chunk.getBlockStorageArray()[storY] = storage;
			}
			return storage; 
		}	
	}

	private class StoragePos{
		@Override
		public int hashCode() {
			return x + y*16 + z*256;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			StoragePos other = (StoragePos) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			if (z != other.z)
				return false;
			return true;
		}

		final int x;
		final int y;
		final int z;
		final BlockPos pos;

		public StoragePos(BlockPos pos){
			this.x = pos.getX() & 15;
			this.y = pos.getY() & 15;
			this.z = pos.getZ() & 15;
			this.pos = pos;
		}

		private BlockMap getOuterType() {
			return BlockMap.this;
		}

	}

}
