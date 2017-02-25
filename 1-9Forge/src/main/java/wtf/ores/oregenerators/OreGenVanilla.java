package wtf.ores.oregenerators;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import wtf.ores.OreGenAbstract;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;
import wtf.worldgen.GeneratorMethods;


public class OreGenVanilla extends OreGenAbstract{

	public OreGenVanilla(IBlockState blockstate, int[] genRange, int[] minmaxPerChunk, boolean denseGen, int blocks) {
		super(blockstate, genRange, minmaxPerChunk, denseGen);
		blocksPerCluster = blocks;
	}

	protected final int blocksPerCluster;
	


	@Override
	public void doOreGen(World world, GeneratorMethods gen, Random random, ChunkCoords coords, ChunkScan scan) throws Exception {
		
		int blocksPerChunk = getBlocksPerChunk(world, coords, random, scan.surfaceAvg);
		//System.out.println("vanilla gen");
		
		
		int blocksReq = this.blocksReq();
		
		while (blocksPerChunk > blocksReq || (blocksPerChunk > 0 && random.nextInt(blocksReq) < blocksPerChunk)){
			int genHeight = getGenStartHeight(scan.surfaceAvg, random);
			
			blocksPerChunk -= genVein(world, gen, random, scan, new BlockPos(coords.getWorldX() + 8, genHeight, coords.getWorldZ() + 8));
			
		}
	}

	@Override
	public int genVein(World world, GeneratorMethods gen, Random random, ChunkScan scan, BlockPos pos)
			throws Exception {
		float f = random.nextFloat() * (float)Math.PI;
		int blocksSet = 0;
		
		double d0 = pos.getX()+ MathHelper.sin(f) * blocksPerCluster / 8.0F;
		double d1 = pos.getX() - MathHelper.sin(f) * blocksPerCluster / 8.0F;
		
		double d2 = pos.getZ() + 8 + MathHelper.cos(f) * blocksPerCluster / 8.0F;
		double d3 = pos.getZ() + 8 - MathHelper.cos(f) * blocksPerCluster / 8.0F;
		
		
		
		double d4 = pos.getY() + random.nextInt(3) - 2;
		double d5 = pos.getY() + random.nextInt(3) - 2;

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
								if (genDenseOres){
									densityToSet = getDensityToSet(random, l2, scan.surfaceAvg);
								}
								else {
									densityToSet = 0;
								}
								if (random.nextFloat() < this.veinDensity){
									blocksSet +=densityToSet+1;
									gen.setOreBlock(new BlockPos(k2, l2, i3), this.oreBlock, densityToSet);
								}
								
							}
						}
					}
				}
			}
		}
		return blocksSet;
	}

	@Override
	public int blocksReq() {
		return (int) (blocksPerCluster*this.veinDensity)*2;
	}

}
