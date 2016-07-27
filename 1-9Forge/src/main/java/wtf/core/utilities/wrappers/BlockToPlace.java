package wtf.core.utilities.wrappers;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockToPlace {

	
	public final IBlockState state;
	public final int x;
	public final int y;
	public final int z;
	public final BlockPos blockpos;
	
	/**
	 * A holder for a block to be placed, with a hashcode that does not take into account the block or metadata
	 * Designed to be used to place blocks into a hashcode, to prevent routines from spawning blocks in the same place repeatedly
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param block
	 * @param meta
	 */
	
	public BlockToPlace(int x, int y, int z, IBlockState state){
		this.state = state;
		this.x = x;
		this.y = y;
		this.z = z;
		blockpos = new BlockPos(x, y, z);
	}

	public BlockToPlace(BlockPos pos, IBlockState blockstate) {
		this.blockpos = pos;
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.state = blockstate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
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
		BlockToPlace other = (BlockToPlace) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}


}
