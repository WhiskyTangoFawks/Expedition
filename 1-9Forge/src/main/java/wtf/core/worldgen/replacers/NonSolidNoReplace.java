package wtf.core.worldgen.replacers;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import wtf.api.Replacer;

public class NonSolidNoReplace extends Replacer {

	public NonSolidNoReplace(Block block) {
		super(block);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isNonSolidAndReplacement(Chunk chunk, BlockPos pos, Block oldBlock) {
		// TODO Auto-generated method stub
		return true;
	}

}
