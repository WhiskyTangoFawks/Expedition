package wtf.utilities.UBC;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import wtf.worldgen.GeneratorMethods;

public class ReplacerUBCSandstone extends ReplacerUBCAbstract{

	public ReplacerUBCSandstone(Block block) {
		super(block);

	}

	@Override
	public boolean isNonSolidAndReplacement(Chunk chunk, BlockPos pos, GeneratorMethods gen, IBlockState oldState) {
		/*
		IBlockState state = getUBCStone(pos);
		if (state.getBlock().hashCode() == sedHash){
			gen.replaceBlock(pos, state);
		}*/
		double noise =getSimplexSand(chunk.getWorld(), pos);
		if (noise < 8){
			gen.replaceBlock(pos, sands[(int)noise]);
		}
		return false;
	}

}
