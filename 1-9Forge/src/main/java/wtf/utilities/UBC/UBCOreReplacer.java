package wtf.utilities.UBC;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class UBCOreReplacer extends ReplacerUBCAbstract{

	public UBCOreReplacer(Block block) {
		super(block);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isNonSolidAndReplacement(Chunk chunk, BlockPos pos, CaveBiomeGenMethods gen, IBlockState oldState) {
		IBlockState state = getUBCStone(pos);
		//IBlockState newBlock = BlockSets.stoneAndOre.get(new StoneAndOre(state, oldState));
		gen.overrideBlock(pos, state);
		return false;
	}

}
