package wtf.ores.oregenerators;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.ores.ChunkDividedOreMap;
import wtf.ores.OreGenAbstract;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;
import wtf.utilities.wrappers.OrePos;

public class OreGenSingle extends OreGenAbstract{



	public OreGenSingle(IBlockState blockstate, int[] genRange, int[] minmaxPerChunk, boolean denseGen) {
		super(blockstate, genRange, minmaxPerChunk, denseGen);
		
	}

	@Override
	public void doOreGen(World world, ChunkDividedOreMap map, Random random, ChunkCoords coords, ChunkScan chunkscan) throws Exception {
		
		int blocksPerChunk = this.getBlocksPerChunk(world, coords, random, chunkscan.surfaceAvg);

		while (blocksPerChunk > 0){
			int x=coords.getWorldX()+random.nextInt(16);
			int y = this.getGenStartHeight(chunkscan.surfaceAvg, random);
			int z = coords.getWorldZ()+random.nextInt(16);
			
			
			blocksPerChunk -= genVein(world, map, random, chunkscan, new BlockPos(x, y, z));
			 
		}
		
	}

	@Override
	public int genVein(World world, ChunkDividedOreMap map, Random random, ChunkScan scan, BlockPos pos) {
		int densityToSet;
		if (genDenseOres){
			densityToSet = getDensityToSet(random, pos.getY(), scan.surfaceAvg);
		}
		else {
			densityToSet = 0;
		}

		
		map.put(new OrePos(pos, densityToSet), this.oreBlock);
		return densityToSet+1;
	}

	@Override
	public int blocksReq() {
		return 2;
	}


}
