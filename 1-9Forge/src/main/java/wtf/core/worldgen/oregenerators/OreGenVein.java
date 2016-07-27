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

public class OreGenVein extends OreGenAbstract {

	public final int veinLength;
	public final int veinWidth;
	public final int veinHeight;
	public final float veinPitch;
	public static final float pi2 = (float) (Math.PI/2);
	public static final float pi4 = (float) (Math.PI/4);
	public static final float pi = (float) (Math.PI);
	
	public OreGenVein(IBlockState blockstate, float maxGenRangeHeight, float minGenRangeHeight, int maxPerChunk, int minPerChunk, int length, int width, int height, float pitch) {
		super(blockstate, maxGenRangeHeight, minGenRangeHeight, maxPerChunk, minPerChunk);
		this.veinLength = length;
		this.veinWidth = width;
		this.veinHeight = height;
		this.veinPitch = pitch;
	}

	@Override
	public void doOreGen(World world, ChunkDividedOreMap map, Random random, ChunkCoords coords, ChunkScan surface) throws Exception {

	
		double blocksPerChunk = this.getBlocksPerChunk(world, coords, random, surface.surfaceAvg);
		
		
		double blocksReq = this.veinLength*this.veinWidth*this.veinHeight*this.veinDensity;
		
		while (blocksPerChunk > blocksReq || random.nextFloat() < (blocksPerChunk/blocksReq)){

			int oriX = coords.getWorldX() +random.nextInt(16);
			int oriZ = coords.getWorldZ() +random.nextInt(16);
			int oriY = this.getGenStartHeight(surface.surfaceAvg, random);

			int length = veinLength ;//* (surface.surfaceAvg/chunk.getWorld().getSeaLevel() + random.nextInt(5)-2);
			
			
			float pitchY = veinPitch + (random.nextFloat()*pi2)-pi4;
			float pitchX = random.nextFloat()*pi;
			float vecY = MathHelper.cos(pitchY);
			float vecX = MathHelper.cos(pitchX) * MathHelper.sin(pitchY);
			float vecZ = MathHelper.sin(pitchX) * MathHelper.sin(pitchY);

			
			
			float vecWidthY = MathHelper.cos(pitchY+pi4);
			float vecWidthX = MathHelper.cos(pitchX+pi4);// * MathHelper.sin(pitchY+pi4);
			float vecWidthZ =  MathHelper.sin(pitchX+pi4);// * MathHelper.sin(pitchY+pi4);
			
			
			
			for (int widthLoop = 0; widthLoop < veinWidth; widthLoop++){
				for (int heightLoop = 0; heightLoop < veinHeight; heightLoop++){
					double xpos = oriX+widthLoop*vecWidthX;
					double zpos = oriZ+widthLoop*vecWidthZ;
					double ypos = oriY+heightLoop*vecWidthY;
					
					for (int lengthLoop = 0; lengthLoop < length; lengthLoop++){
						
						
						int densityToSet;
						if (GameplayConfig.denseOres){
							densityToSet = getDensityToSet(random, (ypos/surface.surfaceAvg));
							
						}
						else {
							densityToSet = 0;
						}
					
						if (random.nextFloat() < this.veinDensity){
							blocksPerChunk-= densityToSet+1;
							map.put(new OrePos(xpos, ypos, zpos, densityToSet), this.oreBlock);
							
						}
							
						xpos+=vecX;
						zpos+=vecZ;
						ypos+=vecY;
					}
				}
				
			}
		}


	}
}


