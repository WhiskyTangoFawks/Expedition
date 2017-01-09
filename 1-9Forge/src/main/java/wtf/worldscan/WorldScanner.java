package wtf.worldscan;

import java.util.ArrayList;
import java.util.HashMap;
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
	
	public HashMap<BlockPos, CaveListWrapper> airBlocks = new HashMap<BlockPos, CaveListWrapper>();
	
	public ChunkScan getChunkScan(World world, ChunkCoords coords)
	{		
		CaveBiomeGenMethods gen = Core.UBC ? new UBCCavebiomesGenMethods(world, coords, world.rand) : new CaveBiomeGenMethods(world, coords, world.rand);
		
		ArrayList<BlockPos> water = new ArrayList<BlockPos>();
		ArrayList<CaveListWrapper> caveareas = new ArrayList<CaveListWrapper>();

		SurfacePos[][] surfacepositions = new SurfacePos[16][16];

		Chunk chunk = coords.getChunk(world);
		
		int lastY = 70;

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
				
				//First, we find the surface for the block position
				boolean generated = false;
				boolean liquid = false;
				
				int y = lastY+3;
				
				while (!chunk.canSeeSky(new BlockPos(xloop, y, zloop)) && y<256){
					y+=10;
				}	

				IBlockState[] column = getColumn(chunk, xloop, y, zloop);
				
				//initial scan to find the first non-air block
				
				while (isAirAndCheck(chunk, gen, column[y], worldx,y,worldz) && y > 1)
				{
					y--;
				}

				while (BlockSets.liquidBlockSet.contains(column[y])){
					y--;
					liquid = true;
				}
				
				while (!isSurfaceAndCheck(chunk, gen, column[y], worldx, y, worldz) && y > 1){
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
					
				lastY = y;
				surfaceaverage += y;

				int ceiling = -1;
				boolean caveStartFound = false;

				//setting the block arrays for the y column- needs to be done here
				ArrayList<BlockPos> wallPos = new ArrayList<BlockPos>();
				ArrayList<AdjPos> adjPos = new ArrayList<AdjPos>();

				//We could move down a position here, but that would then be skipping the replacer a position
				
				//iterate downward
				for (y=y-1; y > 1; y--){
					BlockPos currentPos = new BlockPos(worldx, y, worldz);
					BlockPos prevZpos = zig ? new BlockPos(worldx, y, worldz-1) : new BlockPos(worldx, y, worldz+1);
					EnumFacing prevFaceZ = zig ? EnumFacing.NORTH : EnumFacing.SOUTH;
					EnumFacing nextFaceZ = zig ? EnumFacing.SOUTH : EnumFacing.NORTH;

					boolean isAir =isAirAndCheck(chunk, gen, column[y], worldx, y, worldz);
					
					//if false, I might want to add to the air hash here, instead of doing a loop later

					/**
					 * X scanning
					 */

					if (!xArray.containsKey(zloop*256+y)){//If the row does not already have an assigned boolean
						xArray.put(zloop*256+y, isAir);
			
						//Overscanning initial entries: if this is air, and the prev block in the row is not, then check it for being a wall
						//backing up the thing, so overscan isn't needed
						if (isAir && !getIsAir(world, currentPos.west()) && isWall(world, currentPos.west())){  
							wallPos.add(currentPos.west());
							adjPos.add(new AdjPos(currentPos, EnumFacing.WEST));
						}
					}
					//This is on an else if because if the current value is the first one, then it's just checking it against itself
					else if (isAir != xArray.get(zloop*256+y)){ // if the block isAir isn't the same as the previous one in the row
						
						xArray.put(zloop*256+y, isAir);
						if (isAir){ //gone from wall to air
							if (isWall(chunk, currentPos.west())){
								wallPos.add(currentPos.west());
								adjPos.add(new AdjPos(currentPos, EnumFacing.WEST));
							}
						}
						else { 
							if (isWall(chunk, currentPos)){

								CaveListWrapper cavewrapper = airBlocks.get(currentPos.west());
								if (cavewrapper != null){
									cavewrapper.addWall(currentPos, new AdjPos(currentPos.west(), EnumFacing.EAST));
								}
								else {
									//not a known position, likely outside the cave
								}
							}
						}
					}

					if (xloop == 15){ //if it's the last column of the chunk, overscan to see if any caves end on the first position of the next chunk
						if (isAir && !getIsAir(world, currentPos.east())){ //if this is air, and the prev block is not, then add 

							if (isWall(world, currentPos.east())){ //checking for floor/ceiling
								wallPos.add(currentPos.east());
								adjPos.add(new AdjPos(currentPos, EnumFacing.EAST));
							}
						}
					}

					/**
					 * Z Scanning
					 */
			
					if (!zArray.containsKey(y)){ //set the initial z boolean, and check if the block just outside the chunk should be a wall
						zArray.put(y, getIsAir(world, prevZpos));
						if (isAir && !getIsAir(world, prevZpos) && isWall(world, prevZpos)){//if this is air, and the prev isn't and it's a wall
							wallPos.add(prevZpos);  //add it
							adjPos.add(new AdjPos(currentPos, prevFaceZ));
						}
					}
					
					else if (isAir != zArray.get(y)){
							zArray.put(y, isAir);
							if (isAir){ //gone from wall to air
								if (isWall(chunk, prevZpos)){ //checking for floor/ceiling
									wallPos.add(prevZpos);
									adjPos.add(new AdjPos(currentPos, prevFaceZ));
								}
							}
							else { // gone from air to wall
								if (isWall(chunk, currentPos)){
								
									CaveListWrapper cavewrapper = airBlocks.get(prevZpos);
									if (cavewrapper != null){
										cavewrapper.addWall(currentPos, new AdjPos(prevZpos, nextFaceZ));
									}
									else {
										//airblock not found
									}
								}
							}
						}
					
					if (zloop1 == 15){
						//on 15, if I am overscanning it is always going to be a z-1 situation
						//BlockPos nextZpos = new BlockPos(x, y, z-1);
						if (isAir && !getIsAir(world, currentPos.south())){
							if (isWall(world, currentPos.south())){ //checking for floor/ceiling
								wallPos.add(currentPos.south());
								adjPos.add(new AdjPos(currentPos, EnumFacing.SOUTH));
							}
						}
					}
					
					/**
					 * Y Scanning
					 */
					
					if (isAir){ //if it's air
						if (!caveStartFound && ceiling == -1){ //and if the generator isn't set to cave yet
							ceiling = y + 1; //set the ceiling
							caveStartFound = true; //set the generator to cave
						}
					}
					else{ //if it's not air
						if (ceiling != -1){ // if the ceiling has already been found- then that means we've found a cave floor

							CaveListWrapper cave =  null;
							CavePosition pos = new CavePosition(worldx, ceiling, y, worldz);

							//figure out which cave this is closest too
							for (CaveListWrapper cavewrapper : caveareas){
								cave = cavewrapper.addPos(pos, wallPos, adjPos);
								break;
							}	
							//if there isn't one suitable, create a new one
							if (cave == null){
								cave = new CaveListWrapper(pos, wallPos, adjPos); 
								caveareas.add(cave);
							}
							
							//add all the air blocks for this cave position to the air hash
							for (int loop = y+1; loop < ceiling; loop ++){
								airBlocks.put(new BlockPos(worldx, loop, worldz), cave);
							}
													
							wallPos = new ArrayList<BlockPos>();
							adjPos = new ArrayList<AdjPos>();

							ceiling = -1;
						}
						caveStartFound = false;
					}
				}
			}
		}

		surfaceaverage /= 256;
		gen.blocksToSet.setBlockSet();
		return new ChunkScan(world, surfacepositions, coords.getWorldX(), coords.getWorldZ(), surfaceaverage, caveareas, water);
	}

	public boolean isAirAndCheck(Chunk chunk, CaveBiomeGenMethods gen, IBlockState state, int x, int y, int z){

		Block block = state.getBlock();

		Replacer replacer = BlockSets.isNonSolidAndCheckReplacement.get(block);
		if (replacer!= null){
			return replacer.isNonSolidAndReplacement(chunk, new BlockPos(x, y, z), gen, state);
			//WTFCore.log.info("Replaced");
		}
		return false;
	}

	public boolean isWall(Chunk chunk, BlockPos pos){
		return !getIsAir(chunk, pos.up()) && !getIsAir(chunk, pos.down());
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
		//IBlockState state = chunk.getBlockState(x & 15, y, z & 15);
		Block block = state.getBlock();

		if (BlockSets.isNonSolidAndCheckReplacement.containsKey(block)){
			Replacer replacer = BlockSets.isNonSolidAndCheckReplacement.get(block);
			if (replacer!= null){
				replacer.isNonSolidAndReplacement(chunk, new BlockPos(x & 15, y, z & 15), gen, state);
			}
		}
		return BlockSets.surfaceBlocks.contains(block);
	}

	protected IBlockState[] getColumn(Chunk chunk, int chunkX, int maxY, int chunkZ){
	
		maxY++; //Because I want it to be inclusive of the top block
		IBlockState[] blockArray = new IBlockState[maxY];
		
		for (int loop = 0; loop < maxY;){
			//blockArray[loop] = chunk.getBlockState(chunkX, loop, chunkZ);
			//get extended storage
			//while y < length
			//get blockstate

			int y2 = loop >> 4;

		//loop max = either 16, or the number of blocks remaining
			int remaining = maxY-loop;
			int loopMax = remaining > 16 ? 16 : remaining;

			ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[y2];
			if (extendedblockstorage == null){
				for (int loop2 = 0; loop2 < loopMax; loop2++){
					blockArray[loop] = Blocks.AIR.getDefaultState();
					loop++;
				}	
			}
			else {
				for (int loop2 = 0; loop2 < loopMax; loop2++){
					blockArray[loop] = extendedblockstorage.get(chunkX, loop2, chunkZ);
					loop++;
				}	
			}
		}
		return blockArray;
	}

}
