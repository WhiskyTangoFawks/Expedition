package wtf.utilities.wrappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import wtf.init.WTFBlocks;
import wtf.worldgen.trees.TreePos;
import wtf.worldgen.trees.TreeVars;
import wtf.worldscan.CoreWorldGenListener;
import wtf.worldscan.WorldScanner;




public class ChunkScan {

	public final SurfacePos[][] surface;
	public final World world;
	public final int chunkX;
	public final int chunkZ;
	public final double surfaceAvg;


	public final ArrayList<CaveListWrapper> caveset;

	private ArrayList<BlockPos> underwater = null;

	public ChunkScan(World world, SurfacePos[][] var, int x, int z, int avg, ArrayList<CaveListWrapper> caveareas){
		this.surface = var;
		this.chunkX = x;
		this.chunkZ = z;
		this.surfaceAvg = avg;
		this.world = world;
		this.caveset = caveareas;
	}


	public Block getBlockInChunk(int x, int z){

		return world.getBlockState(surface[x][z]).getBlock();
	}
	public SurfacePos getSurfacePosFromWorldCoords(int worldx, int worldz){
		int x = worldx-chunkX;
		int z = worldz-chunkZ;
		if (x > -1 && x < 16 && z > -1 && z < 16){
			return surface[x][z];
		}
		return null;

	}
	public Block getBlockInWorld(int x, int y, int z){

		return world.getBlockState(new BlockPos(chunkX+x, y, chunkZ+z)).getBlock();
	}

	int waterHash = Material.WATER.hashCode();
	int lavaHash = Material.LAVA.hashCode();
	public boolean checkPositionForTreeGen(World world, TreePos tree) throws Exception{
		int x = tree.pos.getX()-chunkX;
		int z = tree.pos.getZ()-chunkZ;
		IBlockState up1 = world.getBlockState(new BlockPos(tree.pos.getX(), tree.pos.getY()+1, tree.pos.getZ()));
		int tradceil = MathHelper.ceiling_double_int(tree.trunkRadius);
		int trunkRadius = tradceil*tradceil;
		int addRad = MathHelper.ceiling_double_int(tree.type.genBuffer + tree.type.getBranchLength(tree.scale, tree.trunkHeight, tree.trunkHeight/2) + tree.type.leafRad/2);
		int fullRadius = addRad > 0 ? tradceil + addRad : tradceil;
		int fullrad2 = fullRadius*fullRadius;
		int materialHash = up1.getMaterial().hashCode();

		if (!tree.type.waterGenerate){
			if(materialHash == waterHash){
				return false;	
			}	
		}
		else if (materialHash != waterHash){ //else if it is a water tree
			return false;
		}
		if (materialHash == lavaHash){
			return false;
		}


		int canGrow = 0;
		for (int loopX = -fullRadius; loopX < fullRadius; loopX++){
			for (int loopZ = -fullRadius;loopZ  < fullRadius; loopZ++){	
				int scanx = loopX + x;
				int scanz = loopZ + z;
				int rad = loopX*loopX + loopZ*loopZ;
				if (rad < trunkRadius){
					if (scanx < 16 && scanx > -1 && scanz < 16 && scanz > -1){
						if (surface[scanx][scanz].generated || MathHelper.abs_int(surface[scanx][scanz].getY()-tree.pos.getY()) > 4){
							//System.out.println("Already generated");
							return false;
						}
						if (tree.type.canGrowOn.contains(getBlockInChunk(scanx,scanz))){
							canGrow++;
						}
						else {
							canGrow--;
						}

					}
					else { //position is outside the chunk
						ChunkScan adjacentScan = CoreWorldGenListener.getChunkScan(world, new ChunkCoords((scanx+chunkX)>>4, (scanz+chunkZ)>>4));
						if (adjacentScan != null){
							SurfacePos pos = adjacentScan.getSurfacePosFromWorldCoords(scanx+chunkX, scanz+chunkZ);
							if (pos.generated){
								return false;
							}
							if (MathHelper.abs_int(pos.getY()-tree.pos.getY()) > 4){
								//System.out.println("Height difference failed " + y + " " + tree.pos.y);
								return false;
							}
						}
						else {
							//part of the trunk is being set in an unloaded chunk- don't generate that
							return false;
						}
					}
				}
				else if (rad < fullrad2){
					if (scanx < 16 && scanx > -1 && scanz < 16 && scanz > -1){
						if (surface[scanx][scanz].generated){
							//System.out.println("Already generated");
							return false;
						}
					}
					else { //position is outside the chunk
						//System.out.println("outside chunk");
						ChunkScan adjacentScan = CoreWorldGenListener.getChunkScan(world, new ChunkCoords((scanx+chunkX)>>4, (scanz+chunkZ)>>4));
						if (adjacentScan != null){
							SurfacePos pos = adjacentScan.getSurfacePosFromWorldCoords(scanx+chunkX, scanz+chunkZ);
							if (pos.generated){
								return false;
							}
						}
						else {
							return false; //adjacent scan is null
						}
					}
				}
			}
		}

		return canGrow > 0;
	}
	Block[] base = {Blocks.DIRT, Blocks.GRASS, WTFBlocks.mossyDirt};
	HashSet<Block> canGrowOn = new HashSet(Arrays.asList(base));
	public boolean canGrowOnCheck(BlockPos treepos, int checkRad) throws Exception{
		int x = treepos.getX()-chunkX;
		int z = treepos.getZ()-chunkZ;
		IBlockState up1 = world.getBlockState(treepos.up());
		int tradceil = 2;
		int trunkRadius = tradceil*tradceil;
		int fullRadius = checkRad;
		int fullrad2 = fullRadius*fullRadius;
		int materialHash = up1.getMaterial().hashCode();

		if (materialHash == waterHash){ //else if it is a water tree
			return false;
		}
		if (materialHash == lavaHash){
			return false;
		}


		int canGrow = 0;
		for (int loopX = -fullRadius; loopX < fullRadius; loopX++){
			for (int loopZ = -fullRadius;loopZ  < fullRadius; loopZ++){	
				int scanx = loopX + x;
				int scanz = loopZ + z;
				int rad = loopX*loopX + loopZ*loopZ;
				if (rad < trunkRadius){
					if (scanx < 16 && scanx > -1 && scanz < 16 && scanz > -1){
						if (surface[scanx][scanz].generated || MathHelper.abs_int(surface[scanx][scanz].getY()-treepos.getY()) > 4){
							//System.out.println("Already generated");
							return false;
						}
						if (canGrowOn.contains(getBlockInChunk(scanx,scanz))){
							canGrow++;
						}
						else {
							canGrow--;
						}

					}
					else { //position is outside the chunk
						ChunkScan adjacentScan = CoreWorldGenListener.getChunkScan(world, new ChunkCoords((scanx+chunkX)>>4, (scanz+chunkZ)>>4));
						if (adjacentScan != null){
							SurfacePos pos = adjacentScan.getSurfacePosFromWorldCoords(scanx+chunkX, scanz+chunkZ);
							if (pos.generated){
								return false;
							}
							if (MathHelper.abs_int(pos.getY()-treepos.getY()) > 4){
								//System.out.println("Height difference failed " + y + " " + tree.pos.y);
								return false;
							}
						}
						else {
							//part of the trunk is being set in an unloaded chunk- don't generate that
							return false;
						}
					}
				}
				else if (rad < fullrad2){
					if (scanx < 16 && scanx > -1 && scanz < 16 && scanz > -1){
						if (surface[scanx][scanz].generated){
							//System.out.println("Already generated");
							return false;
						}
					}
					else { //position is outside the chunk
						//System.out.println("outside chunk");
						ChunkScan adjacentScan = CoreWorldGenListener.getChunkScan(world, new ChunkCoords((scanx+chunkX)>>4, (scanz+chunkZ)>>4));
						if (adjacentScan != null){
							SurfacePos pos = adjacentScan.getSurfacePosFromWorldCoords(scanx+chunkX, scanz+chunkZ);
							if (pos.generated){
								return false;
							}
						}
						else {
							return false; //adjacent scan is null
						}
					}
				}
			}
		}

		return canGrow > 0;
		
	}

	public SurfacePos getRandomNotGenerated(Random random){
		for (int tryloop = 0; tryloop < 5; tryloop++){
			int x = random.nextInt(16);
			int z = random.nextInt(16);
			if (!surface[x][z].generated){
				IBlockState up1 = world.getBlockState(surface[x][z].up());

				if (up1.getMaterial().hashCode() != lavaHash || up1.getMaterial().hashCode() != waterHash){
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

	public ArrayList<BlockPos> getWaterList(){
		if (underwater == null){
			underwater = new ArrayList<BlockPos>();
			Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
			for (int xloop = 0; xloop < 16; xloop++){
				for (int zloop = 0; zloop < 16; zloop++){
					if (chunk.getBlockState(surface[xloop][zloop].up()).getMaterial().hashCode() == waterHash){
						underwater.add(surface[xloop][zloop]);
					}
				}
			}
		}
		return underwater;
	}

	public void setGenerated(BlockPos pos, int radius) throws Exception{
		int x = pos.getX()-chunkX;
		int z = pos.getZ()-chunkZ;
		int radius2 = radius*radius;

		for (int loopX = -radius; loopX < radius; loopX++){
			for (int loopZ = -radius;loopZ  < radius; loopZ++){	
				int scanx = loopX + x;
				int scanz = loopZ + z;
				int rad = loopX*loopX + loopZ*loopZ;


				if (rad < radius2){
					if (scanx < 16 && scanx > -1 && scanz < 16 && scanz > -1){
						surface[scanx][scanz].generated = true;
					}
					else {
						ChunkScan adjacentScan = CoreWorldGenListener.getChunkScan(world, new ChunkCoords((scanx+chunkX)>>4, (scanz+chunkZ)>>4));
						if (adjacentScan != null){
							adjacentScan.getSurfacePosFromWorldCoords(scanx+chunkX, scanz+chunkZ).generated = true;
						}

					}
				}
			}
		}
	}
	

	public boolean checkGenerated(BlockPos pos, int radius) throws Exception{
		int x = pos.getX()-chunkX;
		int z = pos.getZ()-chunkZ;
		int radius2 = radius*radius;

		for (int loopX = -radius; loopX < radius; loopX++){
			for (int loopZ = -radius;loopZ  < radius; loopZ++){	
				int scanx = loopX + x;
				int scanz = loopZ + z;
				int rad = loopX*loopX + loopZ*loopZ;


				if (rad < radius2){
					if (scanx < 16 && scanx > -1 && scanz < 16 && scanz > -1){
						if (surface[scanx][scanz].generated ==true){
							return false;
						}
					}
					else {
						ChunkScan adjacentScan = CoreWorldGenListener.getChunkScan(world, new ChunkCoords((scanx+chunkX)>>4, (scanz+chunkZ)>>4));
						if (adjacentScan != null && adjacentScan.getSurfacePosFromWorldCoords(scanx+chunkX, scanz+chunkZ).generated == true) {
							return false;
						}

					}
				}
			}
		}
		return true;
	}



}
