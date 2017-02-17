package wtf.worldscan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import wtf.Core;
import wtf.api.Replacer;
import wtf.init.BlockSets;
import wtf.utilities.UBC.UBCCavebiomesGenMethods;
import wtf.utilities.wrappers.AdjPos;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;
import wtf.utilities.wrappers.SurfacePos;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class WorldScanner {

	public WorldScanner(){

	}


	public HashSet<BlockPos> airBlocks = new HashSet<BlockPos>();

	public ChunkScan getChunkScan(World world, ChunkCoords coords)
	{		
		CaveBiomeGenMethods gen = Core.UBC ? new UBCCavebiomesGenMethods(world, coords, world.rand) : new CaveBiomeGenMethods(world, coords, world.rand);

		UnsortedChunkCaves unsortedcavepos = new UnsortedChunkCaves(coords);

		ArrayList<BlockPos> water = new ArrayList<BlockPos>();

		SurfacePos[][] surfacepositions = new SurfacePos[16][16];

		Chunk chunk = coords.getChunk(world);


		ArrayList<ExtendedBlockStorage> storageList = getStorageList(chunk);

		int surfaceaverage = 0;

		//hashmap position = x * 256 +y
		HashMap<Integer, Boolean> xArray = new HashMap<Integer, Boolean>();
		for (int xloop = 0; xloop < 16; xloop++)
		{

			//boolean [] zArray = new boolean[256];
			HashMap<Integer, Boolean> zArray = new HashMap<Integer, Boolean>();
			int worldx = coords.getWorldX() + xloop;
			for (int zloop1 = 0; zloop1 < 16; zloop1++){

				//zig zagging back and forth
				boolean zig;
				int zloop;
				if ((xloop & 1) == 0){ // if the last bit is a 0, zig forward
					zloop= zloop1;
					zig = true;
				}
				else { //zig back
					zloop = 15-zloop1;
					zig = false;
				}
				int worldz = coords.getWorldZ() + zloop;

				boolean generated = false;
				boolean liquid = false;


				ArrayList<IBlockState> column = getColumn(storageList, xloop, zloop);
				int y = column.size()-1;

				//chunk.getWorld().setBlockState(new BlockPos(worldx, y, worldz), Blocks.GLASS.getDefaultState());

				while (isAirAndCheck(chunk, gen, column.get(y), worldx, y, worldz)) //removed y>1- because if it's getting through in vanilla, there's a bug
				{
					y--;
				}

				while (BlockSets.liquidBlockSet.contains(column.get(y))){

					y--;
					liquid = true;
				}

				while (!isSurfaceAndCheck(chunk, gen, column.get(y), worldx, y, worldz) && y > 1){
					generated = true;
					y--;
				}


				surfacepositions[xloop][zloop] = new SurfacePos(worldx, y, worldz);

				if (generated){
					surfacepositions[xloop][zloop].setGenerated();
				}
				if (liquid){
					water.add(surfacepositions[xloop][zloop]);
				}

				surfaceaverage += y;

				int ceiling = -1;

				//setting the block arrays for the y column- needs to be done here
				//ArrayList<BlockPos> wallPos = new ArrayList<BlockPos>();
				//ArrayList<AdjPos> adjPos = new ArrayList<AdjPos>();

				//We could move down a position here, but that would then be skipping the replacer a position


				ArrayList<AdjPos> tempAdj = null;
				ArrayList<BlockPos> tempWall = null;

				//iterate downward

				//System.out.println("Scanning column " + worldx + " " + worldz + " relative to " + coords.getWorldX() + " " + coords.getWorldZ());

				for (y=y-1; y > -1; y--){
					BlockPos currentPos = new BlockPos(worldx, y, worldz);
					BlockPos prevZpos = zig ? new BlockPos(worldx, y, worldz-1) : new BlockPos(worldx, y, worldz+1);
					EnumFacing prevFaceZ = zig ? EnumFacing.NORTH : EnumFacing.SOUTH;
					EnumFacing nextFaceZ = zig ? EnumFacing.SOUTH : EnumFacing.NORTH;

					boolean isAir =isAirAndCheck(chunk, gen, column.get(y), worldx, y, worldz);


					if (isAir){ //if it's air
						airBlocks.add(currentPos);
						if (ceiling == -1){ //if the generator isn't set to cave yet
							ceiling = y + 1; //set the ceiling
							tempAdj = new ArrayList<AdjPos>();
							tempWall = new ArrayList<BlockPos>();
							//System.out.println("Cave start found");
						}
					}

					//if false, I might want to add to the air hash here, instead of doing a loop later

					/**
					 * X scanning
					 */

					if (!xArray.containsKey(zloop*256+y)){//If the row does not already have an assigned boolean
						xArray.put(zloop*256+y, isAir);

						//Overscanning initial entries: if this is air, and the prev block in the row is not, then check it for being a wall
						//backing up the thing, so overscan isn't needed

						if (isAir && !getIsAir(world, currentPos.west()) && isWall(world, currentPos.west())){  
							tempWall.add(currentPos.west());
							tempAdj.add(new AdjPos(currentPos, EnumFacing.WEST));
						}
					}

					//This is on an else if because if the current value is the first one, then it's just checking it against itself
					else if (isAir != xArray.get(zloop*256+y)){ // if the block isAir isn't the same as the previous one in the row

						xArray.put(zloop*256+y, isAir);
						if (isAir){ //gone from wall to air
							if (isWall(chunk, currentPos.west())){
								tempAdj.add(new AdjPos(currentPos, EnumFacing.WEST));
								tempWall.add(currentPos.west());


							}
						}
						else { 
							if (isWall(chunk, currentPos) && airBlocks.contains(currentPos.west())){
								unsortedcavepos.addWallPos(currentPos, new AdjPos(currentPos.west(), EnumFacing.EAST));
							}
						}
					}

					if (xloop == 15){ //if it's the last column of the chunk, overscan to see if any caves end on the first position of the next chunk
						if (isAir && !getIsAir(world, currentPos.east())){ //if this is air, and the prev block is not, then add 

							if (isWall(world, currentPos.east())){ //checking for floor/ceiling
								tempAdj.add(new AdjPos(currentPos, EnumFacing.EAST));
								tempWall.add(currentPos.east());
							}
						}
					}

					/**
					 * Z Scanning
					 */

					if (!zArray.containsKey(y)){ //set the initial z boolean, and check if the block just outside the chunk should be a wall
						zArray.put(y, getIsAir(world, prevZpos));
						if (isAir && !getIsAir(world, prevZpos) && isWall(world, prevZpos)){//if this is air, and the prev isn't and it's a wall
							tempWall.add(prevZpos);  //add it
							tempAdj.add(new AdjPos(currentPos, prevFaceZ));
						}
					}
					else { //if the last row has been scanned
						if (isAir != zArray.get(y)){ //if there is a change
							zArray.put(y, isAir); //update the array
							if (isAir){ //gone from wall to air
								if (isWall(chunk, prevZpos)){ //checking for floor/ceiling
									tempWall.add(prevZpos);
									tempAdj.add(new AdjPos(currentPos, prevFaceZ));
								}
							}
							else if (airBlocks.contains(prevZpos) && isWall(chunk, currentPos)){
								//System.out.println("Trying to add a wallpos on " + zloop);
							
								if (!unsortedcavepos.addWallPos(currentPos, new AdjPos(prevZpos, nextFaceZ))){
									//System.out.println(currentPos.toString() + " " + prevZpos.toString());

									//System.out.println("current block " + chunk.getBlockState(currentPos) + " " + chunk.getBlockState(prevZpos));
								}
							}
						}
					}

					if (zloop1 == 15){
						//on 15, if I am overscanning it is always going to be a z-1 situation
						//BlockPos nextZpos = new BlockPos(x, y, z-1);
						if (isAir && !getIsAir(world, currentPos.south())){
							if (isWall(world, currentPos.south())){ //checking for floor/ceiling
								tempWall.add(currentPos.south());
								tempAdj.add(new AdjPos(currentPos, EnumFacing.SOUTH));
							}
						}
					}
				

					/**
					 * Y Scanning
					 */


					if (ceiling != -1  && (!isAir || y==2)){ // if we have a ceiling, and this isn't air, then here's the floor OR, if there's an open ceiling that's just failed to get a floor

						//System.out.println("Cave floor found" );
						CavePosition pos = new CavePosition(worldx, ceiling, y, worldz);
						pos.adj.addAll(tempAdj);
						pos.wall.addAll(tempWall);
						unsortedcavepos.add(pos);

						tempWall = null;
						tempAdj = null;

						ceiling = -1;

					}
				}
			}
		}


		surfaceaverage /= 256;
		//System.out.println("surface average = " +surfaceaverage);
		gen.blocksToSet.setBlockSet();

		unsortedcavepos.getSortedCaves();
		//unsortedcavepos.printReport();

		return new ChunkScan(world, surfacepositions, coords.getWorldX(), coords.getWorldZ(), surfaceaverage, unsortedcavepos, water);
	}

	public boolean isAirAndCheck(Chunk chunk, CaveBiomeGenMethods gen, IBlockState state, int x, int y, int z){

		Block block = state.getBlock();

		Replacer replacer = BlockSets.isNonSolidAndCheckReplacement.get(block);
		if (replacer!= null){
			return replacer.isNonSolidAndReplacement(chunk, new BlockPos(x, y, z), gen, state);
		}


		return false;
	}

	public boolean isWall(Chunk chunk, BlockPos pos){
		return !airBlocks.contains(pos) && !getIsAir(chunk, pos.up()) && !getIsAir(chunk, pos.down());
	}

	public boolean isWall(World world, BlockPos pos){
		return !getIsAir(world, pos.up()) && !getIsAir(world, pos.down());
	}

	public boolean getIsAir(Chunk chunk, BlockPos pos){
		return BlockSets.nonSolidBlockSet.contains(chunk.getBlockState(pos).getBlock());
	}
	public boolean getIsAir(World world, BlockPos pos){
		return BlockSets.nonSolidBlockSet.contains(world.getBlockState(pos).getBlock());
	}

	public boolean isSurfaceAndCheck(Chunk chunk, CaveBiomeGenMethods gen, IBlockState state, int x, int y, int z){
		Block block = state.getBlock();

		if (BlockSets.isNonSolidAndCheckReplacement.containsKey(block)){
			Replacer replacer = BlockSets.isNonSolidAndCheckReplacement.get(block);
			if (replacer!= null){
				replacer.isNonSolidAndReplacement(chunk, new BlockPos(x, y, z), gen, state);
			}
		}
		return BlockSets.surfaceBlocks.contains(block);
	}


	protected ArrayList<ExtendedBlockStorage> getStorageList(Chunk chunk){
		ArrayList<ExtendedBlockStorage> list = new ArrayList<ExtendedBlockStorage>();
		for (int loop = 0; loop < chunk.getBlockStorageArray().length; loop++){
			if (chunk.getBlockStorageArray()[loop] == null)
				break;
			list.add(chunk.getBlockStorageArray()[loop]);
		}
		return list;
	}


	protected ArrayList<IBlockState> getColumn(ArrayList<ExtendedBlockStorage> storageList, int chunkX, int chunkZ){
		ArrayList<IBlockState> blockArray = new ArrayList<IBlockState>();

		for (ExtendedBlockStorage extendedblockstorage : storageList){
			for (int loop = 0; loop < 16; loop++){
				blockArray.add(extendedblockstorage.get(chunkX, loop, chunkZ));
			}	
		}
		return blockArray;
	}

}
