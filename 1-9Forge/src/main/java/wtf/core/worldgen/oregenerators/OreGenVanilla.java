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


public class OreGenVanilla extends OreGenAbstract{

	protected final int blocksPerCluster;
	
	public OreGenVanilla(IBlockState blockstate, float maxGenRangeHeight, float minGenRangeHeight, int maxPerChunk, int minPerChunk, int blocksPerCluster) {
		super(blockstate, maxGenRangeHeight, maxGenRangeHeight, blocksPerCluster, blocksPerCluster);
		this.blocksPerCluster = blocksPerCluster;
	}

	@Override
	public void doOreGen(World world, ChunkDividedOreMap map, Random random, ChunkCoords coords, ChunkScan surface) throws Exception {
		
		int blocksPerChunk = getBlocksPerChunk(world, coords, random, surface.surfaceAvg);

		
		
		double blocksReq = (double)this.blocksPerCluster*this.veinDensity;
		
		for (int fail = 0; (blocksPerChunk > blocksReq || random.nextFloat()<(blocksPerChunk/(float)blocksReq)) && fail < 100; fail++){

			float f = random.nextFloat() * (float)Math.PI;
			double d0 = coords.getWorldX() + 8 + MathHelper.sin(f) * blocksPerCluster / 8.0F;
			double d1 = coords.getWorldX() + 8 - MathHelper.sin(f) * blocksPerCluster / 8.0F;
			double d2 = coords.getWorldZ() + 8 + MathHelper.cos(f) * blocksPerCluster / 8.0F;
			double d3 = coords.getWorldZ() + 8 - MathHelper.cos(f) * blocksPerCluster / 8.0F;
			int genHeight = getGenStartHeight(surface.surfaceAvg, random);
			double d4 = genHeight + random.nextInt(3) - 2;
			double d5 = genHeight + random.nextInt(3) - 2;

			for (int l = 0; l <= blocksPerCluster; ++l)
			{
				double d6 = d0 + (d1 - d0) * l / blocksPerCluster;
				double d7 = d4 + (d5 - d4) * l / blocksPerCluster;
				double d8 = d2 + (d3 - d2) * l / blocksPerCluster;
				double d9 = random.nextDouble() * blocksPerCluster / 16.0D;
				double d10 = (MathHelper.sin(l * (float)Math.PI / blocksPerCluster) + 1.0F) * d9 + 1.0D;
				double d11 = (MathHelper.sin(l * (float)Math.PI / blocksPerCluster) + 1.0F) * d9 + 1.0D;
				int i1 = MathHelper.floor_double(d6 - d10 / 2.0D);
				int j1 = MathHelper.floor_double(d7 - d11 / 2.0D);
				int k1 = MathHelper.floor_double(d8 - d10 / 2.0D);
				int l1 = MathHelper.floor_double(d6 + d10 / 2.0D);
				int i2 = MathHelper.floor_double(d7 + d11 / 2.0D);
				int j2 = MathHelper.floor_double(d8 + d10 / 2.0D);

				for (int k2 = i1; k2 <= l1; ++k2){
					double d12 = (k2 + 0.5D - d6) / (d10 / 2.0D);
					if (d12 * d12 < 1.0D){
						for (int l2 = j1; l2 <= i2; ++l2){
							double d13 = (l2 + 0.5D - d7) / (d11 / 2.0D);
							if (d12 * d12 + d13 * d13 < 1.0D){
								for (int i3 = k1; i3 <= j2; ++i3){
									
									int densityToSet;
									if (GameplayConfig.denseOres){
										densityToSet = getDensityToSet(random, (l2/surface.surfaceAvg));
									}
									else {
										densityToSet = 0;
									}
									if (random.nextFloat() < this.veinDensity){
										blocksPerChunk-=densityToSet+1;
										map.put(new OrePos(k2, l2, i3, densityToSet), this.oreBlock);
									}
									
								}
							}
						}
					}
				}
			}
		}
	}

}
