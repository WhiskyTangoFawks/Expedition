package wtf.utilities.wrappers;

import net.minecraft.util.math.BlockPos;

public class CavePosition{

	public final int floor;
	public final int ceiling;
    public final int x;
    public final int z;

    public boolean alreadyGenerated = false;
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ceiling;
		result = prime * result + floor;
		result = prime * result + x;
		result = prime * result + z;
		return result;
	}

	public BlockPos getFloorPos(){
		return new BlockPos(x, floor, z);
	}
	
	public BlockPos getCeilingPos(){
		return new BlockPos(x, ceiling, z);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CavePosition other = (CavePosition) obj;
		if (ceiling != other.ceiling)
			return false;
		if (floor != other.floor)
			return false;
		if (x != other.x)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	public CavePosition(int p_i45363_1_, int p_i45363_2_, int p_i45363_3_,  int p_i45363_4_) {

		this.x = p_i45363_1_;
		this.ceiling = p_i45363_2_;
		this.floor = p_i45363_3_;
		this.z = p_i45363_4_;

	}

}
