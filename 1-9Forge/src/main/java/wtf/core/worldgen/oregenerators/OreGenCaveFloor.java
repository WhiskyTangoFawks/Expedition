package wtf.core.worldgen.oregenerators;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import wtf.api.OreGenAbstract;
import wtf.core.config.GameplayConfig;
import wtf.core.utilities.wrappers.CavePosition;
import wtf.core.utilities.wrappers.ChunkCoords;
import wtf.core.utilities.wrappers.ChunkDividedOreMap;
import wtf.core.utilities.wrappers.ChunkScan;
import wtf.core.utilities.wrappers.OrePos;


public class OreGenCaveFloor extends OreGenAbstract{

	public OreGenCaveFloor(IBlockState blockstate, float maxGenRangeHeight, float minGenRangeHeight, int maxPerChunk,
			int minPerChunk) {
		super(blockstate, maxGenRangeHeight, minGenRangeHeight, maxPerChunk, minPerChunk);
	}

	@Override
	public void doOreGen(World world, ChunkDividedOreMap map, Random random, ChunkCoords coords, ChunkScan chunkscan) throws Exception {
		
		if (chunkscan.caveset.size() > 0){
		
			float numToGenerate = (float)this.getBlocksPerChunk(world, coords, random, chunkscan.surfaceAvg)/16384 * chunkscan.caveset.size() * (random.nextFloat()+0.5F);
		
			while (numToGenerate > 1F || random.nextFloat() < numToGenerate){
				CavePosition pos = chunkscan.caveset.get(random.nextInt(chunkscan.caveset.size())).getRandomPosition(random);

				int densityToSet;
				if (GameplayConfig.denseOres){
					densityToSet = getDensityToSet(random, (pos.floor/chunkscan.surfaceAvg));
				}
				else {
					densityToSet = 0;
				}

				numToGenerate-=densityToSet+1;
				map.put(new OrePos(pos.x, pos.floor, pos.z, densityToSet), this.oreBlock);
			}
			
		}
		
	}


}
