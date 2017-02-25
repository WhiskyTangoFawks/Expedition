package wtf.worldgen.replacers;


import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import wtf.init.BlockSets;
import wtf.worldgen.GeneratorMethods;


public abstract class Replacer {

	public Replacer(Block block){
		BlockSets.isNonSolidAndCheckReplacement.put(block,  this);
	}
	
	public abstract boolean isNonSolidAndReplacement(Chunk chunk, BlockPos pos, GeneratorMethods gen, IBlockState oldState);
	


}
