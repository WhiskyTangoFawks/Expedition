package wtf.utilities.UBC;

import exterminatorjeff.undergroundbiomes.api.API;
import exterminatorjeff.undergroundbiomes.common.block.SedimentaryStone;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class ReplacerUBCSandstone extends ReplacerUBCAbstract{

	public ReplacerUBCSandstone(Block block) {
		super(block);

	}

	@Override
	public boolean isNonSolidAndReplacement(Chunk chunk, BlockPos pos, CaveBiomeGenMethods gen, IBlockState oldState) {
		IBlockState state = getUBCStone(pos);
		if (state.getBlock().hashCode() == sedHash){
			gen.replaceBlock(pos, state);
		}
		return false;
	}

}
