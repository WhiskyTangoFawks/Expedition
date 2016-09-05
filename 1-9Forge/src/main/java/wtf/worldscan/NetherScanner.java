package wtf.worldscan;


import java.util.Arrays;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import wtf.api.Replacer;
import wtf.init.BlockSets;

public class NetherScanner extends WorldScanner{
	
	@Override
	public int scanForSurface(Chunk chunk, int x, int y, int z) {
		return 128;
	}
	

	public boolean isSurfaceAndCheck(Chunk chunk, int x, int y, int z){
		//BlockPos pos = ;
		Block block = chunk.getBlockState(new BlockPos(x & 15, y, z & 15)).getBlock();

		if (BlockSets.isNonSolidAndCheckReplacement.containsKey(block)){
			//System.out.println("genReplace contained " + block.getLocalizedName());
			Replacer replacer = BlockSets.isNonSolidAndCheckReplacement.get(block);
			if (replacer!= null){
				replacer.isNonSolidAndReplacement(chunk, new BlockPos(x & 15, y, z & 15), block);
				//WTFCore.log.info("Replaced");
			}
		}
		return netherSurfaceBlocks.contains(block);
	}

	private static Block[] listSurfaceBlocks = {Blocks.NETHERRACK, Blocks.SOUL_SAND, Blocks.GRAVEL};
	public static HashSet<Block> netherSurfaceBlocks = new HashSet<Block>(Arrays.asList(listSurfaceBlocks));

}
