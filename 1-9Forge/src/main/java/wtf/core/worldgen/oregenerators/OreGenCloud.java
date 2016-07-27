package wtf.core.worldgen.oregenerators;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import wtf.api.OreGenAbstract;
import wtf.core.config.GameplayConfig;
import wtf.core.utilities.wrappers.ChunkCoords;
import wtf.core.utilities.wrappers.ChunkDividedOreMap;
import wtf.core.utilities.wrappers.ChunkScan;
import wtf.core.utilities.wrappers.OrePos;

public class OreGenCloud extends OreGenAbstract {
public final double radius;

	public OreGenCloud(IBlockState blockstate, float maxGenRangeHeight, float minGenRangeHeight, int maxPerChunk,
			int minPerChunk, int diameter) {
		super(blockstate, maxGenRangeHeight, minGenRangeHeight, maxPerChunk, minPerChunk);
		this.radius = diameter / 2F;
	}

	
	@Override
	public void doOreGen(World world, ChunkDividedOreMap map, Random random, ChunkCoords coords, ChunkScan chunkscan) throws Exception {

		int blocksPerChunk = this.getBlocksPerChunk(world, coords, random, chunkscan.surfaceAvg);

		double blocksReq = 1.3333*Math.PI*radius*radius*radius*this.veinDensity;
		
		for (int fail = 0; (blocksPerChunk > blocksReq || random.nextFloat()<(blocksPerChunk/(float)blocksReq)) && fail < 100; fail++){
			
			int oriX = coords.getWorldX() + random.nextInt(16);
			int oriZ = coords.getWorldZ() + random.nextInt(16);
			int oriY = this.getGenStartHeight(chunkscan.surfaceAvg, random);

			for (int xloop = (int) -radius; xloop < radius+1; xloop++) {
				for (int yloop = (int) -radius; yloop < radius+1; yloop++) {
					for (int zloop = (int) -radius; zloop < radius+1; zloop++) {
						double distance = MathHelper.sqrt_double(xloop*xloop + yloop*yloop + zloop*zloop);
						if (distance < radius) {

							int densityToSet;
							if (GameplayConfig.denseOres) {
								densityToSet = getDensityToSet(random, (oriY + yloop)/chunkscan.surfaceAvg);
							} else {
								densityToSet = 0;
							}

							if (random.nextFloat() < this.veinDensity){
								blocksPerChunk-=densityToSet+1;
								map.put(new OrePos(oriX + xloop, oriY + yloop, oriZ + zloop, densityToSet), this.oreBlock);
							}
						}
					}
				}
			}
		}

	}

}
