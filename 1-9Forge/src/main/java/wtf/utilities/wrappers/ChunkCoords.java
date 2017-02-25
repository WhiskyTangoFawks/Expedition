package wtf.utilities.wrappers;

import java.util.ArrayList;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import wtf.blocks.GenPlaceHolder;

public class ChunkCoords {

	private final int x;
	private final int z;
	
	
	
	public ChunkCoords(int chunkX, int chunkZ){
		x=chunkX;
		z=chunkZ;
	}
	public ChunkCoords(BlockPos pos){
		x=pos.getX()>>4;
		z=pos.getZ()>>4;
	}
	
	public int getChunkX(){
		return x;
	}
	
	public int getChunkZ(){
		return z;
	}
	
	public int getWorldX(){
		return x<<4;
	}
	
	public int getWorldZ(){
		return z<<4;
	}
	
	public Chunk getChunk(World world){
		return world.getChunkFromChunkCoords(x, z);
	}

	public BlockPos getGenMarkerPos() {
		return new BlockPos(this.getWorldX() + 8, 0, this.getWorldZ() + 8);
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + z;
		return result;
	}

	public ArrayList<ChunkCoords> getChunksInRadius(int radius){
		ArrayList<ChunkCoords> arraylist = new ArrayList<ChunkCoords>();
		for (int xloop = -radius; xloop < radius+1; xloop++){
			for (int zloop = -radius; zloop < radius+1; zloop++){
				if (xloop == 0 && zloop == 0){
					//dont add the center position
				}
				else {
					arraylist.add(new ChunkCoords(x+xloop, z+zloop));
				}
			}
		}
		return arraylist;
	}
		
	public boolean exists(World world){
		return ((ChunkProviderServer)world.getChunkProvider()).chunkExists(x, z);
	}
	public boolean isPopulated(World world){
		return ((ChunkProviderServer)world.getChunkProvider()).chunkExists(x, z) && getChunk(world).isTerrainPopulated();
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChunkCoords other = (ChunkCoords) obj;
		if (x != other.x)
			return false;
		if (z != other.z)
			return false;
		return true;
	}
	public boolean isWTFGenerated(World world) {
		if (world.getBlockState(this.getGenMarkerPos()).getBlock() instanceof GenPlaceHolder){
			return false;
		}
		return true;
	}

}
