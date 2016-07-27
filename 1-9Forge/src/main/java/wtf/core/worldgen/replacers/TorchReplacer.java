package wtf.core.worldgen.replacers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import wtf.api.Replacer;
import wtf.core.init.WTFBlocks;

public class TorchReplacer extends Replacer{

	public TorchReplacer(Block block) {
		super(block);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isNonSolidAndReplacement(Chunk chunk, BlockPos pos, Block oldBlock) {
		IBlockState oldState = chunk.getBlockState(pos);
		IBlockState newState = WTFBlocks.litTorch.getDefaultState().withProperty(BlockTorch.FACING, oldState.getValue(BlockTorch.FACING));
		chunk.setBlockState(pos, newState);
		return true;
	}



}
