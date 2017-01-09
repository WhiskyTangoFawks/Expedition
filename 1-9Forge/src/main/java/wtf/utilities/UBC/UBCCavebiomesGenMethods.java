package wtf.utilities.UBC;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class UBCCavebiomesGenMethods extends CaveBiomeGenMethods{

	final int stoneHash = Blocks.STONE.getDefaultState().hashCode();
	public UBCCavebiomesGenMethods(World world, ChunkCoords coords, Random random) {
		super(world, coords, random);
	}
	
	public IBlockState getBlockState(BlockPos pos){
		IBlockState state = super.getBlockState(pos);
		if (state.hashCode() == stoneHash){
			return ReplacerUBCAbstract.getUBCStone(pos);
		}
		return state;
	}

}
