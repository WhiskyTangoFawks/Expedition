package wtf.worldscan;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import wtf.api.Replacer;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class NonSolidNoReplace extends Replacer {

	public NonSolidNoReplace(Block block) {
		super(block);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isNonSolidAndReplacement(Chunk chunk, BlockPos pos, CaveBiomeGenMethods gen,IBlockState oldState) {
		// TODO Auto-generated method stub
		return true;
	}

}
