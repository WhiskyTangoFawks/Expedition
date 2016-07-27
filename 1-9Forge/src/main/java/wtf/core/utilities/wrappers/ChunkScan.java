package wtf.core.utilities.wrappers;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import wtf.biomes.TreeVars;
import wtf.core.worldgen.CoreWorldGenListener;
import wtf.core.worldgen.WorldScanner;




public class ChunkScan {

	public final SurfacePos[][] surface;
	public final World world;
	public final int chunkX;
	public final int chunkZ;
	public final double surfaceAvg;
	
	
	public final ArrayList<CaveListWrapper> caveset;
	
	public ChunkScan(World world, SurfacePos[][] var, int x, int z, int avg, ArrayList<CaveListWrapper> caveareas){
		this.surface = var;
		this.chunkX = x;
		this.chunkZ = z;
		this.surfaceAvg = avg;
		this.world = world;
		this.caveset = caveareas;
	}
	

	public SurfacePos getPosForTreeGeneration(World world, TreeVars tree) throws Exception{
		
		Random r = world.rand;
		SurfacePos[] randoms = {surface[r.nextInt(8)][r.nextInt(8)], surface[r.nextInt(8)+8][r.nextInt(8)], surface[r.nextInt(8)+4][r.nextInt(8)+4], 
				surface[r.nextInt(8)][r.nextInt(8)+8], surface[r.nextInt(8)+8][r.nextInt(8)+8]};
		
		//int x = world.rand.nextInt(16);
		//int z = world.rand.nextInt(16);
		
		//search algorithm for trees- instead of doing a full random, do a random in each quandrant, and if that quadrand fails, throw it out.
		//if all four quadrants fail, stop trying to generate stuff
		//pick the starting quadrand at random
		//and keep track of which quadrants have attempted generation
		//I could in theory split it up into 4x4 = 16 bits per chunk, but that;s excessive, given the size of my trees
		
		int posRand = world.rand.nextInt(randoms.length);
		
		for (int loop = 0; loop < randoms.length; loop++){
		
			if (checkPositionForTreeGen(world, tree, randoms[posRand], MathHelper.ceiling_double_int(tree.getTrunkDiameter(1)/2+2))){
			return randoms[posRand];	
			}
			posRand++;
			if (posRand>randoms.length-1){
				posRand=0;
			}
		}
		return null;
		
	}

	
	public Block getBlockInChunk(int x, int z){
		
		return world.getBlockState(new BlockPos(surface[x][z].getX(), surface[x][z].getY(), surface[x][z].getZ())).getBlock();
	}
	public Block getBlockInWorld(int x, int y, int z){
		
		return world.getBlockState(new BlockPos(chunkX+x, y, chunkZ+z)).getBlock();
	}
	
	private boolean checkPositionForTreeGen(World world, TreeVars tree,SurfacePos surfacepos, int radius) throws Exception{
		int x = surfacepos.getX()-chunkX;
		int z = surfacepos.getZ()-chunkZ;
		IBlockState up1 = world.getBlockState(new BlockPos(surfacepos.getX(), surfacepos.getY()+1, surfacepos.getZ()));
		
		if (!tree.waterGenerate && up1.getMaterial() == Material.WATER){
			
			return false;
		}
		if (up1.getMaterial() == Material.LAVA){
			return false;
		}

		
		int canGrow = 0;
		for (int loopX = -radius; loopX < radius; loopX++){
			for (int loopZ = -radius;loopZ  < radius; loopZ++){	
				float currentRadius = MathHelper.sqrt_float((loopX*loopX + loopZ*loopZ));
				if (currentRadius < radius){
					if (posInChunk(surfacepos.getX()+loopX, surfacepos.getZ()+loopZ)){
						if (surface[x][z].generated || MathHelper.abs_int(surface[x][z].getY()-surfacepos.getY()) > 4){
							//System.out.println("Already generated");
							return false;
						}
						if (tree.canGrowOn.contains(getBlockInChunk(x,z))){
							canGrow++;
						}
						else {
							canGrow--;
						}

					}
					else { //position is outside the chunk
						//System.out.println("outside chunk");

						ChunkScan adjacentScan = CoreWorldGenListener.getChunkScan(world, new ChunkCoords(x>>4, z>>4));
						int y;
						if (adjacentScan != null){
							y = adjacentScan.surface[x][z].getY();
						}
						else {
							//part of the trunk is being set in an unloaded chunk- don't generate that
							return false;
						}
						

							if (MathHelper.abs_int(y-surfacepos.getY()) > 4){
								//System.out.println("Height difference failed " + y + " " + surfacepos.y);
								return false;
							}
							//System.out.println("Height difference passed " + y + " " + surfacepos.y);
							if (tree.canGrowOn.contains(getBlockInWorld(x, y, z))){
								canGrow++;
							}
							else {
								canGrow--;
							}
						
						
					}
				}
			}
		}

		return canGrow > 0;
	}

	public SurfacePos getRandomNotGenerated(Random random){
		for (int tryloop = 0; tryloop < 10; tryloop++){
			int x = random.nextInt(16);
			int z = random.nextInt(16);
			if (!surface[x][z].generated){
				IBlockState up1 = world.getBlockState(surface[x][z].up());
				if (up1.getMaterial() != Material.WATER && up1.getMaterial() != Material.LAVA){
					return surface[x][z];
				}
			}
		}
		return null;
	}
	
	public void setGenerated(int x, int z){
		int setX = x-chunkX;
		int setZ = z-chunkZ;
		if (setX < 16 && setX > -1 && setZ < 16 && setZ > -1){
			surface[setX][setZ].generated=true;
		}
	}
	
	public boolean posInChunk(int x, int z){
		return (x-chunkX > -1 && z-chunkZ > -1);
	}
	
}
