package wtf.worldgen.trees.components;

import net.minecraft.util.math.BlockPos;

public class ColumnTrunk {

	public final double x;
	public final double z;
	public final double startY;
	
	
	public double currentY;
	
	public ColumnTrunk(double d, double e, double startHeight){
		this.x=d;
		this.z=e;
		this.startY=startHeight;
		this.currentY=startHeight;
	}
	
	public ColumnTrunk(double x, double z, int height) {
		this.x=(int) x;
		this.z=(int) z;
		this.startY=height;
		this.currentY=height;
	}

	public BlockPos nextPos(){
		currentY--;
		return new BlockPos(x, currentY, z);
	}
	
	public BlockPos currentPos(){
		return new BlockPos(x, currentY, z);
	}
	
}
