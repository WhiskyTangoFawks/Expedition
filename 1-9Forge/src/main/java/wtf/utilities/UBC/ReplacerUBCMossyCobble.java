package wtf.utilities.UBC;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import wtf.init.BlockSets;
import wtf.init.BlockSets.Modifier;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class ReplacerUBCMossyCobble extends ReplacerUBCAbstract{

	public ReplacerUBCMossyCobble() {
		super(Blocks.MOSSY_COBBLESTONE);
	}

	@Override
	public boolean isNonSolidAndReplacement(Chunk chunk, BlockPos pos, CaveBiomeGenMethods gen, IBlockState oldState) {
		
		IBlockState cobble = BlockSets.getTransformedState(getUBCStone(pos), Modifier.COBBLE);
		IBlockState mossy = BlockSets.getTransformedState(cobble, Modifier.MOSSY);
		
		//Running it off simplex sand, because it's easy	
		//double noise =getSimplexSand(chunk.getWorld(), pos);
			//if (noise < 8){
		if (mossy != null){		
			gen.overrideBlock(pos, mossy);
		}
		//else spawn something striking to test
		

		//}
		return false;
	//}
	}

}
