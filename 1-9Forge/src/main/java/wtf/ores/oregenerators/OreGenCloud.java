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

public class OreGenCloud extends OreGenAbstract {

public final double radius;

/*	
public OreGenCloud(IBlockState blockstate, float maxGenRangeHeight, float minGenRangeHeight, int maxPerChunk,
			int minPerChunk, int diameter, boolean genDense) {
		super(blockstate, maxGenRangeHeight, minGenRangeHeight, maxPerChunk, minPerChunk, genDense);
		this.radius = diameter / 2F;
	}
*/
public OreGenCloud(IBlockState blockstate, int[] genRange, int[] minmaxPerChunk, boolean denseGen, int diameter) {
	super(blockstate, genRange, minmaxPerChunk, denseGen);
	this.radius = diameter / 2F;
}

	
	@Override
	public void doOreGen(World world, GeneratorMethods gen, Random random, ChunkCoords coords, ChunkScan chunkscan) throws Exception {

		int blocksPerChunk = this.getBlocksPerChunk(world, coords, random, chunkscan.surfaceAvg);

		int blocksReq = this.blocksReq();
		
		while (blocksPerChunk > blocksReq || (blocksPerChunk > 0 && random.nextInt(blocksReq) < blocksPerChunk)){	
			int oriX = coords.getWorldX() + random.nextInt(16);
			int oriZ = coords.getWorldZ() + random.nextInt(16);
			int oriY = this.getGenStartHeight(chunkscan.surfaceAvg, random);
			
			blocksPerChunk -= genVein(world, gen, random, chunkscan, new BlockPos(oriX, oriY, oriZ));

		}

	}


	@Override
	public int genVein(World world, GeneratorMethods gen, Random random, ChunkScan scan, BlockPos pos)
			throws Exception {

		int blocksSet=0;
		
		for (int xloop = (int) -radius; xloop < radius+1; xloop++) {
			for (int yloop = (int) -radius; yloop < radius+1; yloop++) {
				for (int zloop = (int) -radius; zloop < radius+1; zloop++) {
					double distance = MathHelper.sqrt_double(xloop*xloop + yloop*yloop + zloop*zloop);
					if (distance < radius) {

						int densityToSet;
						if (genDenseOres) {
							densityToSet = getDensityToSet(random, pos.getY() + yloop, scan.surfaceAvg, distance, radius);
						} else {
							densityToSet = 0;
						}

						if (random.nextFloat() < this.veinDensity/2 + (1-distance/radius)/2){
							blocksSet+=densityToSet+1;
							gen.setOreBlock(new BlockPos(pos.getX() + xloop, pos.getY() + yloop, pos.getZ() + zloop), this.oreBlock, densityToSet);
						}
					}
				}
			}
		}
		return blocksSet;
		
	}
	
	public int getDensityToSet(Random random, double height, double surfaceAvg, double radius, double maxRadius){
		
		//0 is a full ore, and 2 is a light ore
		double depth = height/(surfaceAvg*maxGenRangeHeight);
		double rand = random.nextFloat()+random.nextFloat()-1;
		double radRatio = radius/maxRadius;
		
		double density = depth*1.5+radRatio*1.5 + rand;
		
		if (density < 1){ return 0;}
		else if (density > 2){ return 2;}
		return 1;
	}

	@Override
	public int blocksReq() {
		return (int) (1.3333*Math.PI*radius*radius*radius*this.veinDensity)*2;
	}

}
