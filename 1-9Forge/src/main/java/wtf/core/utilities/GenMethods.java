package wtf.core.utilities;

import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;

public class GenMethods {

	public static HashSet<Block> getAreaHashSet(World world, BlockPos pos, int xmin, int xmax, int ymin, int ymax, int zmin, int zmax){
		HashSet<Block> blocks = new HashSet<Block>();
		for (int xloop = xmin; xloop < xmax+1; xloop++){
			for (int yloop = ymin; yloop < ymax+1; yloop++){
				for (int zloop = zmin; zloop < zmax+1; zloop++){
					blocks.add(world.getBlockState(new BlockPos(pos.getX()+xloop, pos.getY()+yloop, pos.getZ()+zloop)).getBlock());
				}
			}
		}
		return blocks;
	}

	public static boolean setBlockState(World world, BlockPos pos, IBlockState newState)
	{
		int flags = 0;
		if (!world.isRemote && world.getWorldInfo().getTerrainType() == WorldType.DEBUG_WORLD)
		{
			return false;
		}
		else
		{
			Chunk chunk = world.getChunkFromBlockCoords(pos);
			//Block block = newState.getBlock();

			net.minecraftforge.common.util.BlockSnapshot blockSnapshot = null;
			if (world.captureBlockSnapshots && !world.isRemote)
			{
				blockSnapshot = net.minecraftforge.common.util.BlockSnapshot.getBlockSnapshot(world, pos, flags);
				world.capturedBlockSnapshots.add(blockSnapshot);
			}
			IBlockState oldState = world.getBlockState(pos);
			int oldLight = oldState.getLightValue(world, pos);
			int oldOpacity = oldState.getLightOpacity(world, pos);

			IBlockState iblockstate = chunk.setBlockState(pos, newState);

			if (iblockstate == null)
			{
				if (blockSnapshot != null) world.capturedBlockSnapshots.remove(blockSnapshot);
				return false;
			}
			else
			{
				if (newState.getLightOpacity(world, pos) != oldOpacity || newState.getLightValue(world, pos) != oldLight)
				{
					world.theProfiler.startSection("checkLight");
					world.checkLight(pos);
					world.theProfiler.endSection();
				}

				if (blockSnapshot == null) // Don't notify clients or update physics while capturing blockstates
				{
					world.markAndNotifyBlock(pos, chunk, iblockstate, newState, flags);
				}
				return true;
			}
		}
	}


}
