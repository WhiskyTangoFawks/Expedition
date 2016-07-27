package wtf.core.worldgen.oregenerators;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import wtf.api.OreGenAbstract;
import wtf.core.config.GameplayConfig;
import wtf.core.utilities.wrappers.ChunkCoords;
import wtf.core.utilities.wrappers.ChunkDividedOreMap;
import wtf.core.utilities.wrappers.ChunkScan;
import wtf.core.utilities.wrappers.OrePos;

public class OreGenCluster extends OreGenAbstract {

	public OreGenCluster(IBlockState blockstate, float maxGenRangeHeight, float minGenRangeHeight, int maxPerChunk,	int minPerChunk) {
		super(blockstate, maxGenRangeHeight, minGenRangeHeight, maxPerChunk, minPerChunk);

	}

	
	@Override
	public void doOreGen(World world, ChunkDividedOreMap map, Random random, ChunkCoords coords, ChunkScan chunkscan) throws Exception {

		int blocksPerChunk = this.getBlocksPerChunk(world, coords, random, chunkscan.surfaceAvg);

		double blocksReq = 6F*this.veinDensity;

		while (blocksPerChunk > blocksReq || random.nextFloat()< blocksPerChunk){
			int x=coords.getWorldX()+random.nextInt(12)+2;
			int y = this.getGenStartHeight(chunkscan.surfaceAvg, random);
			if (y > 1 && y < 255){
				int z = coords.getWorldZ()+random.nextInt(16);

				double depth = y/chunkscan.surfaceAvg;

				if (random.nextFloat() < this.veinDensity){
					blocksPerChunk -= genStarPosition(map, world, random, x, y, z, depth);
				}
				if (random.nextFloat() < this.veinDensity){
					blocksPerChunk -= genStarPosition(map, world, random, x+1, y, z, depth);
				}
				blocksPerChunk -= genStarPosition(map, world, random, x-1, y, z, depth);
				if (random.nextFloat() < this.veinDensity){
					blocksPerChunk -= genStarPosition(map, world, random, x, y+1, z, depth);
				}
				if (random.nextFloat() < this.veinDensity){
					blocksPerChunk -= genStarPosition(map, world, random, x, y-1, z, depth);
				}
				if (random.nextFloat() < this.veinDensity){
					blocksPerChunk -= genStarPosition(map, world, random, x, y, z+1, depth);
				}
				if (random.nextFloat() < this.veinDensity){
					blocksPerChunk -= genStarPosition(map, world, random, x, y, z-1, depth);
				}
			}
		}
	}



	private int genStarPosition(ChunkDividedOreMap map, World world, Random random, int x, int y, int z, double depth) throws Exception {
		int densityToSet;
		if (GameplayConfig.denseOres){
			densityToSet = getDensityToSet(random, depth);
		}
		else {
			densityToSet = 0;
		}

		map.put(new OrePos(x, y, z, densityToSet), this.oreBlock);
		return densityToSet+1;
	}


}
