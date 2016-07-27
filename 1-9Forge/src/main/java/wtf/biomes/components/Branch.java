package wtf.biomes.components;

import net.minecraft.block.BlockLog;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class Branch {

	public final double length;
	public final double oriX;
	public final double oriY;
	public final double oriZ;
	
	public final double vecX;
	public final double vecZ;
	public final double vecY;
	
	protected double x;
	protected double y;
	protected double z;
	
	public int count = 0;
	
	public final BlockLog.EnumAxis axis;
	
	public Branch(double oriX, double oriY, double oriZ, double x, double y, double z, double rootLength) {
		this.oriX = oriX;
		this.oriY = oriY;
		this.oriZ = oriZ;
		
		vecX = x;
		vecY = y;
		vecZ = z;
		this.length=rootLength+1;
		//System.out.println("Branch block pos = " + pos.getX() + " " + pos.getY() + " " + pos.getZ());
		this.x = oriX;
		this.y = oriY;
		this.z = oriZ;

		
		 axis = Math.abs(vecY) < MathHelper.abs_max(vecX, vecZ) ? (Math.abs(vecX) > Math.abs(vecZ) ? BlockLog.EnumAxis.X : BlockLog.EnumAxis.Z) : BlockLog.EnumAxis.Y;
	}
	 
	public boolean hasNext(){
		double xlength = x - oriX;
		double ylength = y - oriY;
		double zlength = z - oriZ;
		return (xlength * xlength + ylength*ylength + zlength*zlength) < length*length;
		//return count<length;
	}
	
	//swap over to a calculated length, intead of simply number of blocks placed
	
	public BlockPos next(){
		/*
		if (length > 6 && MathHelper.abs_max(vecX,  vecY) > vecY){
			if ((int)(y+vecY) > (int)y){
				y += vecY;
				return new BlockPos(x, y, z);
			}
			else if ((int)(y+vecY) < (int)y){
				y += vecY;
				return new BlockPos(x, y, z);
			}
		}*/
		
		x += vecX;
		y += vecY;
		z += vecZ;
		count++;
		return new BlockPos(x, y, z);
	}
	
	public BlockPos pos(){
		return new BlockPos(x, y, z);
	}
	

	public BlockPos lateralNext(){
		//System.out.println("vec " + vecX + " " + vecZ);
		if (x==0 && z==0){
			y = y>0 ? y+1 : y-1;
			return new BlockPos(x, y, z); 
		}

		x += vecX;
		z += vecZ;
		return new BlockPos(x, y, z);
	}
	
	public BlockPos getAdjacent1(){
		return vecX > vecZ ? pos().north() : pos().east();
	}

	public BlockPos getAdjacent2(){
		return vecX > vecZ ? pos().south() : pos().west();
	}
	
	public int getCount(){
		return count;
	}
}
