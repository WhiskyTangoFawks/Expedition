package wtf.worldgen.subcaves.mob;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.init.BlockSets;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class DungeonSimpleMagma  extends DungeonAbstractSimple{

	public DungeonSimpleMagma(String name) {
		super(name);
	}

	@Override
	public void generateCenter(CaveBiomeGenMethods gen, Random rand, CavePosition pos, float depth) {
		gen.spawnVanillaSpawner(pos.getFloorPos().up(), "LavaSlime", 3);	
	}
	
	IBlockState block = null;
	
	@Override
	public boolean canGenerateAt(CaveBiomeGenMethods gen, CaveListWrapper cave) {
		block = BlockSets.getTransformedState(gen.getBlockState(cave.centerpos.getFloorPos()), Modifier.LAVA_CRUST);
		if (block==null){
			block = BlockSets.getTransformedState(Blocks.OBSIDIAN.getDefaultState(), Modifier.LAVA_CRUST);
			
		}
		return true;
	}

	public void generate(CaveBiomeGenMethods gen, Random random, BlockPos pos) {
		if (simplex.get3DNoise(gen.getWorld(), pos) < 0.8){
			gen.replaceBlock(pos, block);
		}	
	}
	
}