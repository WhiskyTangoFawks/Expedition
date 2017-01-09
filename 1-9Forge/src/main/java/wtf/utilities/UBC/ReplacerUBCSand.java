package wtf.utilities.UBC;

import exterminatorjeff.undergroundbiomes.api.API;
import exterminatorjeff.undergroundbiomes.common.block.SedimentaryStone;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import wtf.init.BlockSets;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.StateAndModifier;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class ReplacerUBCSand extends ReplacerUBCAbstract{

	public ReplacerUBCSand(Block block) {
		super(block);

	}

	@Override
	public boolean isNonSolidAndReplacement(Chunk chunk, BlockPos pos, CaveBiomeGenMethods gen, IBlockState oldState) {
		
		IBlockState state = getUBCStone(pos);
		//System.out.println(state.toString());
		if (state.getBlock().hashCode() == API.SEDIMENTARY_STONE.hashCode()){
			IBlockState sand = BlockSets.blockTransformer.get(new StateAndModifier(state, Modifier.COBBLE));
			gen.replaceBlock(pos, sand);
		}
		return false;
	}



	
}
