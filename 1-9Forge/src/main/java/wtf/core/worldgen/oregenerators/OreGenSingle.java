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

public class OreGenSingle extends OreGenAbstract{

	public OreGenSingle(IBlockState blockstate, float maxGenRangeHeight, float minGenRangeHeight, int maxPerChunk, int minPerChunk) {
		super(blockstate, maxGenRangeHeight, minGenRangeHeight, maxPerChunk, minPerChunk);

	}

	@Override
	public void doOreGen(World world, ChunkDividedOreMap map, Random random, ChunkCoords coords, ChunkScan chunkscan) throws Exception {
		
		int blocksPerChunk = this.getBlocksPerChunk(world, coords, random, chunkscan.surfaceAvg);

		while (blocksPerChunk > 0){
			int x=coords.getWorldX()+random.nextInt(16);
			int y = this.getGenStartHeight(chunkscan.surfaceAvg, random);
			int z = coords.getWorldZ()+random.nextInt(16);

			int densityToSet;
			if (GameplayConfig.denseOres){
				densityToSet = getDensityToSet(random, (y/chunkscan.surfaceAvg));
			}
			else {
				densityToSet = 0;
			}

			blocksPerChunk-=densityToSet+1;
			map.put(new OrePos(x, y, z, densityToSet), this.oreBlock);
			 
		}
		
	}

}
