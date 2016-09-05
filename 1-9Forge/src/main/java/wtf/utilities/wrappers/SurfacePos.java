package wtf.utilities.wrappers;

import net.minecraft.util.math.BlockPos;

public class SurfacePos extends BlockPos{

	private final double x;
	private final double y;
	private final double z;
	
	public SurfacePos(double x, double y, double z) {
		super(x, y, z);
		this.x=x;
		this.y=y;
		this.z=z;
	}

	public boolean generated = false;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SurfacePos other = (SurfacePos) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}
	

}

