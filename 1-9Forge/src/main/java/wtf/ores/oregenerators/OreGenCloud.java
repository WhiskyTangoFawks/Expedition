package wtf.ores.oregenerators;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import wtf.core.utilities.wrappers.ChunkCoords;
import wtf.core.utilities.wrappers.ChunkScan;
import wtf.core.utilities.wrappers.OrePos;
import wtf.ores.ChunkDividedOreMap;
import wtf.ores.OreGenAbstract;

public class OreGenCloud extends OreGenAbstract {
public final double radius;

	public OreGenCloud(IBlockState blockstate, float maxGenRangeHeight, float minGenRangeHeight, int maxPerChunk,
			int minPerChunk, int diameter, boolean genDense) {
		super(blockstate, maxGenRangeHeight, minGenRangeHeight, maxPerChunk, minPerChunk, genDense);
		this.radius = diameter / 2F;
	}

	
	@Override
	public void doOreGen(World world, ChunkDividedOreMap map, Random random, ChunkCoords coords, ChunkScan chunkscan) throws Exception {

		int blocksPerChunk = this.getBlocksPerChunk(world, coords, random, chunkscan.surfaceAvg);

		int blocksReq = this.blocksReq();
		
		while (blocksPerChunk > blocksReq || (blocksPerChunk > 0 && random.nextInt(blocksReq) < blocksPerChunk)){	
			int oriX = coords.getWorldX() + random.nextInt(16);
			int oriZ = coords.getWorldZ() + random.nextInt(16);
			int oriY = this.getGenStartHeight(chunkscan.surfaceAvg, random);
			
			blocksPerChunk -= genVein(world, map, random, chunkscan, new BlockPos(oriX, oriY, oriZ));

		}

	}


	@Override
	public int genVein(World world, ChunkDividedOreMap map, Random random, ChunkScan scan, BlockPos pos)
			throws Exception {

		int blocksSet=0;
		
		for (int xloop = (int) -radius; xloop < radius+1; xloop++) {
			for (int yloop = (int) -radius; yloop < radius+1; yloop++) {
				for (int zloop = (int) -radius; zloop < radius+1; zloop++) {
					double distance = MathHelper.sqrt_double(xloop*xloop + yloop*yloop + zloop*zloop);
					if (distance < radius) {

						int densityToSet;
						if (genDenseOres) {
							densityToSet = getDensityToSet(random, (pos.getY() + yloop)/scan.surfaceAvg);
						} else {
							densityToSet = 0;
						}

						if (random.nextFloat() < this.veinDensity){
							blocksSet+=densityToSet+1;
							map.put(new OrePos(pos.getX() + xloop, pos.getY() + yloop, pos.getZ() + zloop, densityToSet), this.oreBlock);
						}
					}
				}
			}
		}
		return blocksSet;
		
	}


	@Override
	public int blocksReq() {
		return (int) (1.3333*Math.PI*radius*radius*radius*this.veinDensity);
	}

}
