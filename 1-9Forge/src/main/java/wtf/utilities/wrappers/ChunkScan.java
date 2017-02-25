package wtf.utilities.wrappers;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.worldgen.CoreWorldGenListener;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.UnsortedChunkCaves;

public class ChunkScan {

	public final SurfacePos[][] surface;
	public final World world;
	public final ChunkCoords coords;
	public final double surfaceAvg;
	public GeneratorMethods gen;


	public final UnsortedChunkCaves caveset;

	private final ArrayList<BlockPos> underwater;

	public ChunkScan(World world, ChunkCoords coords, GeneratorMethods gen, SurfacePos[][] var, int avg, UnsortedChunkCaves unsortedcavepos, ArrayList<BlockPos> water){
		this.surface = var;
		this.coords = coords;
		this.surfaceAvg = avg;
		this.world = world;
		this.caveset = unsortedcavepos;
		this.underwater = water;
		this.gen = gen;
	}


	public Block getBlockInChunk(int x, int z){

		return world.getBlockState(surface[x][z]).getBlock();
	}
	public SurfacePos getSurfacePosFromWorldCoords(int worldx, int worldz){
		int x = worldx-coords.getChunkX();
		int z = worldz-coords.getChunkZ();
		if (x > -1 && x < 16 && z > -1 && z < 16){
			return surface[x][z];
		}
		return null;

	}
	public SurfacePos getRandomNotGenerated(Random random){
		for (int tryloop = 0; tryloop < 5; tryloop++){
			int x = random.nextInt(16);
			int z = random.nextInt(16);
			if (!surface[x][z].generated){
				//IBlockState up1 = world.getBlockState(surface[x][z].up());

				//if (up1.getMaterial().hashCode() != lavaHash || up1.getMaterial().hashCode() != waterHash){
				return surface[x][z];
				//}

			}
		}
		return null;
	}


	public ArrayList<BlockPos> getWaterList(){
		return underwater;
	}

	public void setGenerated(BlockPos pos){
		int x = pos.getX()-coords.getWorldX();
		int z = pos.getZ()-coords.getWorldZ();

		if (x < 16 && x > -1 && z < 16 && z > -1){
			surface[x][z].generated = true;
		}
		else {
			ChunkCoords adj = new ChunkCoords(pos);
			ChunkScan scan = CoreWorldGenListener.getChunkScan(world, adj);
			//if (x > 16 || z > 16){
				//System.out.println("getting adjacent chunkscan " + x + " " + z);
			//}
			if (scan != null){
				scan.setGenerated(pos);
			}
		}

	}


	public boolean checkGenerated(BlockPos pos, double d){
		int x = pos.getX()-coords.getChunkX();
		int z = pos.getZ()-coords.getChunkZ();
		double radius2 = d*d;

		for (double loopX = -d; loopX < d; loopX++){
			for (double loopZ = -d;loopZ  < d; loopZ++){	
				int scanx = (int) (loopX + x);
				int scanz = (int) (loopZ + z);
				int rad = (int) (loopX*loopX + loopZ*loopZ);


				if (rad < radius2){
					if (scanx < 16 && scanx > -1 && scanz < 16 && scanz > -1){
						if (surface[scanx][scanz].generated ==true){
							return true;
						}
					}
					else {
						
						//ChunkScan adjacentScan = CoreWorldGenListener.getChunkScan(world, new ChunkCoords(scanx+coords.getWorldX(), scanz+coords.getWorldZ()));
						//if (adjacentScan != null && adjacentScan.getSurfacePosFromWorldCoords(scanx+coords.getWorldX(), scanz+coords.getWorldZ()).generated == true) {
						//	return true;
						//}

					}
				}
			}
		}
		return false;
	}



}
