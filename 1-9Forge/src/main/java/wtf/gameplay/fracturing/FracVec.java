package wtf.gameplay.fracturing;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class FracVec{

	public final int maxDist;
	public final int blocksToFrac;
	
	public final double oriX;
	public final double oriY;
	public final double oriZ;
	
	public final double vecX;
	public final double vecY;
	public final double vecZ;
	
	float pi = (float)Math.PI*2;
	
	public FracVec(BlockPos pos, int str, int maxDist, double x, double y, double z) {
		this.oriX = pos.getX() + 0.5;
		this.oriY = pos.getY() + 0.5;
		this.oriZ = pos.getZ() + 0.5;
		this.vecX = x;
		this.vecY = y;
		this.vecZ = z;
		this.maxDist=maxDist;
		this.blocksToFrac=str;
	}
	
	public FracVec(BlockPos pos, int str, int maxDist, Random random){
		this.oriX = pos.getX() + 0.5;
		this.oriY = pos.getY() + 0.5;
		this.oriZ = pos.getZ() + 0.5;
		
		float pitchX = random.nextFloat()*pi;
		float pitchY = random.nextFloat()*pi;
		float sinY = MathHelper.sin(pitchY);
		vecY = MathHelper.cos(pitchY);
		vecX = MathHelper.cos(pitchX) * sinY;
		vecZ = MathHelper.sin(pitchX) * sinY;
		
		this.maxDist=maxDist;
		this.blocksToFrac=str;
	}
	
	public BlockPos getPos(int dist){
		return new BlockPos(oriX+vecX*dist, oriY+vecY*dist, oriZ+vecZ*dist);
	}

}
