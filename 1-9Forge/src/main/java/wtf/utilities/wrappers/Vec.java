package wtf.utilities.wrappers;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class Vec {
	
	public final BlockPos ori;
	
	public final double vecX;
	public final double vecZ;
	public final double vecY;
	public int n = 1;
	
	private double currentX;
	private double currentY;
	private double currentZ;
	
	public Vec(BlockPos pos, double pitchX, double pitchY){
		currentX = pos.getX();
		currentZ = pos.getZ();
		currentY = pos.getY();
		ori = pos;
		float sinY =  MathHelper.sin((float) pitchY);
		vecY = MathHelper.cos((float) pitchY);
		vecX = MathHelper.cos((float) pitchX) * sinY;
		vecZ = MathHelper.sin((float) pitchX) * sinY;
	}
	float pi = (float)Math.PI*2;
	
	
	public Vec(BlockPos pos, Random random){
		ori = new BlockPos(pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5);
		currentX = pos.getX();
		currentZ = pos.getZ();
		currentY = pos.getY();
		float pitchX = random.nextFloat()*pi;
		float pitchY = random.nextFloat()*pi;
		float sinY = MathHelper.sin(pitchY);
		vecY = MathHelper.cos(pitchY);
		vecX = MathHelper.cos(pitchX) * sinY;
		vecZ = MathHelper.sin(pitchX) * sinY;
		
	}
	
	
	
	public Vec(BlockPos pos, double x, double y, double z){
		ori = new BlockPos(pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5);
		currentX = pos.getX()+0.5;
		currentZ = pos.getZ()+0.5;
		currentY = pos.getY()+0.5;
		vecX = x;
		vecY = y;
		vecZ = z;
	}
	
	public BlockPos next(){
		currentX += vecX;
		currentY += vecY;
		currentZ += vecZ;
		return pos();
	}
	
	public BlockPos checkNext(){
		currentX += vecX;
		currentY += vecY;
		currentZ += vecZ;
		return new BlockPos(currentX + vecX, currentY + vecY, currentZ + vecZ);
	}

	public BlockPos pos() {
		// TODO Auto-generated method stub
		return new BlockPos(currentX, currentY, currentZ);
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(currentX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(currentY);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(currentZ);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((ori == null) ? 0 : ori.hashCode());
		temp = Double.doubleToLongBits(vecX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(vecY);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(vecZ);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vec other = (Vec) obj;
		if (Double.doubleToLongBits(currentX) != Double.doubleToLongBits(other.currentX))
			return false;
		if (Double.doubleToLongBits(currentY) != Double.doubleToLongBits(other.currentY))
			return false;
		if (Double.doubleToLongBits(currentZ) != Double.doubleToLongBits(other.currentZ))
			return false;
		if (ori == null) {
			if (other.ori != null)
				return false;
		} else if (!ori.equals(other.ori))
			return false;
		if (Double.doubleToLongBits(vecX) != Double.doubleToLongBits(other.vecX))
			return false;
		if (Double.doubleToLongBits(vecY) != Double.doubleToLongBits(other.vecY))
			return false;
		if (Double.doubleToLongBits(vecZ) != Double.doubleToLongBits(other.vecZ))
			return false;
		return true;
	}
	

}
