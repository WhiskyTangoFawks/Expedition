package wtf.ores;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import scala.util.Random;
import wtf.api.Replacer;
import wtf.blocks.BlockDenseOre;
import wtf.init.BlockSets;
import wtf.utilities.wrappers.StoneAndOre;

public class OreReplacer extends Replacer{

	public OreReplacer(Block block) {
		super(block);
	}
	
	Random random = new Random();

	@Override
	public boolean isNonSolidAndReplacement(Chunk chunk, BlockPos pos, IBlockState oldState){
		IBlockState state = BlockSets.stoneAndOre.get(new StoneAndOre(Blocks.STONE.getDefaultState(), oldState));
		if (state != null && state.getBlock() instanceof BlockDenseOre){
			setBlock(chunk.getWorld(), pos, state.withProperty(BlockDenseOre.DENSITY, random.nextInt(3)));
		}
		return false;
	}
	
}
