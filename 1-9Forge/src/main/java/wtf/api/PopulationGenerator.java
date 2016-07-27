package wtf.api;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import wtf.core.utilities.GenMethods;
import wtf.core.utilities.wrappers.BlockToPlace;
import wtf.core.utilities.wrappers.ChunkCoords;
import wtf.core.utilities.wrappers.ChunkScan;


public abstract class PopulationGenerator {

	public abstract void generate(World world, ChunkCoords chunkcoords, Random random, ChunkScan surface) throws Exception;

	protected HashSet<BlockToPlace> blocksToPlace = new HashSet<BlockToPlace>();
	
	protected boolean addBlock(World world, BlockPos pos, IBlockState blockstate){
		return GenMethods.setBlockState(world, pos, blockstate);
				//blocksToPlace.add(new BlockToPlace(blockpos, blockstate));
	}
	protected boolean addBlock(World world, int x, int y, int z, IBlockState blockstate){
		
		return GenMethods.setBlockState(world, new BlockPos(x, y, z), blockstate);
		//return blocksToPlace.add(new BlockToPlace(new BlockPos(x,y,z), blockstate));
	}
	

	protected void setBlockSet(World world, HashSet<BlockToPlace> blockset){

		if ((!world.isRemote && world.getWorldInfo().getTerrainType() == WorldType.DEBUG_WORLD) || blockset.isEmpty())
		{
			return;
		}
		else
		{

			Iterator<BlockToPlace> iterator = blockset.iterator();

			BlockToPlace blockpos = iterator.next();


			while (iterator.hasNext()){

				int chunkX = blockpos.x >> 4;
				int chunkZ = blockpos.z >> 4;
				Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);


				//Block block = blockpos.state.getBlock();

				net.minecraftforge.common.util.BlockSnapshot blockSnapshot = null;
				if (world.captureBlockSnapshots && !world.isRemote)
				{
					blockSnapshot = net.minecraftforge.common.util.BlockSnapshot.getBlockSnapshot(world, blockpos.blockpos, 0);
					world.capturedBlockSnapshots.add(blockSnapshot);
				}

				while (chunkX == blockpos.x >> 4 && chunkZ == blockpos.z >> 4){
					chunk.setBlockState(blockpos.blockpos, blockpos.state);

					if (!iterator.hasNext()){break;}
					blockpos = iterator.next();
				}

				//IBlockState oldState = world.getBlockState(pos);
				//int oldLight = oldState.getLightValue(world, pos);
				//int oldOpacity = oldState.getLightOpacity(world, pos);
				//if (newState.getLightOpacity(world, pos) != oldOpacity || newState.getLightValue(world, pos) != oldLight)
				//{
				//   world.theProfiler.startSection("checkLight");
				//  world.checkLight(pos);
				// world.theProfiler.endSection();
				// }

				//if (blockSnapshot == null) // Don't notify clients or update physics while capturing blockstates
				//{
				//    world.markAndNotifyBlock(pos, chunk, iblockstate, newState, flags);
				//}
				//return true;

			}
		}
	}

}