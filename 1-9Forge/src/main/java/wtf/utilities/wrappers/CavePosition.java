package wtf.utilities.wrappers;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.util.math.BlockPos;

public class CavePosition{

	public final int floor;
	public final int ceiling;
    public final int x;
    public final int z;

    public ArrayList<AdjPos> adj = new ArrayList<AdjPos>();
    public ArrayList<BlockPos> wall = new ArrayList<BlockPos>();
    
    //public boolean alreadyGenerated = false;
    
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
	
	public XZ xz(){
		return new XZ(this.x, this.z);
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

	public BlockPos getRandomWall(Random random) {
		if (this.wall.size() > 1){
			return wall.get(random.nextInt(wall.size()));
		}
		return null;
	}
	
	public BlockPos getMidPos(){
		return new BlockPos(this.x, (ceiling-floor/2)+floor, this.z);
	}
	
	public ArrayList<BlockPos> getAirPos(){
		ArrayList<BlockPos> list= new ArrayList<BlockPos>();
		BlockPos pos = getFloorPos().up();
		while (pos.getY() < this.ceiling){
			list.add(pos);
			pos=pos.up();
		}
		return list;
	}

}
