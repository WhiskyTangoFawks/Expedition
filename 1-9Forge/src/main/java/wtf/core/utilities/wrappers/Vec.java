package wtf.core.utilities.wrappers;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import wtf.core.utilities.Simplex;

public class Vec {
	
	public final BlockPos ori;
	
	public final double vecX;
	public final double vecZ;
	public final double vecY;
	public int n = 1;
	
	
	private BlockPos currentpos;
	
	public Vec(BlockPos pos, double pitchX, double pitchY){
		currentpos = pos;
		ori = pos;
		float sinY =  MathHelper.sin((float) pitchY);
		vecY = MathHelper.cos((float) pitchY);
		vecX = MathHelper.cos((float) pitchX) * sinY;
		vecZ = MathHelper.sin((float) pitchX) * sinY;
	}
	float pi = (float)Math.PI*2;
	
	
	public Vec(BlockPos pos, Random random){
		ori = new BlockPos(pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5);
		currentpos = ori;
		float pitchX = random.nextFloat()*pi;
		float pitchY = random.nextFloat()*pi;
		float sinY = MathHelper.sin(pitchY);
		vecY = MathHelper.cos(pitchY);
		vecX = MathHelper.cos(pitchX) * sinY;
		vecZ = MathHelper.sin(pitchX) * sinY;
		
	}
	
	
	
	public Vec(BlockPos pos, double x, double y, double z){
		ori = new BlockPos(pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5);
		currentpos = ori;
		vecX = x;
		vecY = y;
		vecZ = z;
	}
	
	public BlockPos next(){
		currentpos = new BlockPos(currentpos.getX()+vecX, currentpos.getY()+vecY, currentpos.getZ() + vecZ);
		return currentpos;
	}


	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentpos == null) ? 0 : currentpos.hashCode());
		result = prime * result + n;
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
		if (currentpos == null) {
			if (other.currentpos != null)
				return false;
		} else if (!currentpos.equals(other.currentpos))
			return false;
		if (n != other.n)
			return false;
		return true;
	}
}
