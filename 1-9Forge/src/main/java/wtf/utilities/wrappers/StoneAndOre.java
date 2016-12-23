package wtf.utilities.wrappers;

import net.minecraft.block.state.IBlockState;

public class StoneAndOre {

	public final IBlockState stone;
	public final IBlockState ore;
	
	public StoneAndOre(IBlockState blockStone, IBlockState blockOre){
		stone = blockStone;
		ore = blockOre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ore == null) ? 0 : ore.hashCode());
		result = prime * result + ((stone == null) ? 0 : stone.hashCode());
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
		StoneAndOre other = (StoneAndOre) obj;
		if (ore == null) {
			if (other.ore != null)
				return false;
		} else if (!ore.equals(other.ore))
			return false;
		if (stone == null) {
			if (other.stone != null)
				return false;
		} else if (!stone.equals(other.stone))
			return false;
		return true;
	}
}
