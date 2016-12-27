package wtf.ores.oregenerators;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import wtf.ores.ChunkDividedOreMap;
import wtf.ores.OreGenAbstract;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;
import wtf.utilities.wrappers.OrePos;

public class OreGenVein extends OreGenAbstract {

	public final int veinLength;
	public final int veinWidth;
	public final int veinHeight;
	public final float veinPitch;
	public static final float pi8 = (float) (Math.PI/8);
	public static final float pi2 = (float) (Math.PI/2);
	public static final float pi4 = (float) (Math.PI/4);
	public static final float pi = (float) (Math.PI);
	
	public OreGenVein(IBlockState blockstate, int[] genRange, int[] minmaxPerChunk, int[] dimensions, float pitch, boolean genDense) {
		super(blockstate, genRange, minmaxPerChunk, genDense);
		this.veinLength = dimensions[0];
		this.veinWidth = dimensions[2];
		this.veinHeight = dimensions[1];
		this.veinPitch = pitch;
	}

	@Override
	public void doOreGen(World world, ChunkDividedOreMap map, Random random, ChunkCoords coords, ChunkScan chunkscan) throws Exception {

	
		int blocksPerChunk = this.getBlocksPerChunk(world, coords, random, chunkscan.surfaceAvg);
		int blocksReq = this.blocksReq();
		
		
		//this is working like it should
		while (blocksPerChunk > blocksReq || (blocksPerChunk > 0 && random.nextInt(blocksReq) < blocksPerChunk)){


			int x = coords.getWorldX() +random.nextInt(16);
			int z = coords.getWorldZ() +random.nextInt(16);
			int y = this.getGenStartHeight(chunkscan.surfaceAvg, random);

			blocksPerChunk -= genVein(world, map, random, chunkscan, new BlockPos(x, y, z));
			
			//The core issue, is that using the density function makes exponentially dense veins
		}


	}

	@Override
	public int genVein(World world, ChunkDividedOreMap map, Random random, ChunkScan scan, BlockPos pos)
			throws Exception {
		
		int length = veinLength *  + random.nextInt(5)-2;
		int blocksSet = 0;
		
		float pitchY = veinPitch + (random.nextFloat()*pi4)-pi8;
		float pitchX = random.nextFloat()*pi;
		float vecY = MathHelper.cos(pitchY);
		float vecX = MathHelper.cos(pitchX) * MathHelper.sin(pitchY);
		float vecZ = MathHelper.sin(pitchX) * MathHelper.sin(pitchY);

		
		
		float vecWidthY = MathHelper.cos(pitchY+pi4);
		float vecWidthX = MathHelper.cos(pitchX+pi4);// * MathHelper.sin(pitchY+pi4);
		float vecWidthZ = MathHelper.sin(pitchX+pi4);// * MathHelper.sin(pitchY+pi4);
		
		 
		double x = pos.getX() - vecWidthX*veinWidth/2 - vecX*length/2;
		double y = pos.getY() - vecWidthY*veinWidth/2 - vecY*length/2;
		double z = pos.getZ() - vecWidthZ*veinWidth/2 - vecZ*length/2;
		
		pos = new BlockPos(x, y, z);
		
		for (int widthLoop = 0; widthLoop < veinWidth; widthLoop++){
			for (int heightLoop = 0; heightLoop < veinHeight; heightLoop++){
				double xpos = pos.getX()+widthLoop*vecWidthX;
				double zpos = pos.getZ()+widthLoop*vecWidthZ;
				double ypos = pos.getY()+heightLoop*vecWidthY;
				
				for (int lengthLoop = 0; lengthLoop < length; lengthLoop++){
					
					
					int densityToSet = genDenseOres ? densityToSet = getDensityToSet(random, (int) ypos, scan.surfaceAvg) : 0;
					
					if (random.nextFloat() < this.veinDensity){
						blocksSet+= densityToSet+1;
						map.put(new OrePos(xpos, ypos, zpos, densityToSet), this.oreBlock);
					}
						
					xpos+=vecX;
					zpos+=vecZ;
					ypos+=vecY;
				}
			}
			
		}
		return blocksSet;
	}

	@Override
	public int blocksReq() {
		return (int) (this.veinLength*this.veinWidth*this.veinHeight*this.veinDensity)*2;

	}
	

}

