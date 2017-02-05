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

public class DungeonSimpleZombie extends DungeonAbstractSimple{

	public DungeonSimpleZombie(String name) {
		super(name);
	}

	@Override
	public void generateCenter(CaveBiomeGenMethods gen, Random rand, CavePosition pos, float depth) {
		gen.spawnVanillaSpawner(pos.getFloorPos().up(), "Zombie", 5);	
	}
	
	IBlockState block = null;
	
	@Override
	public boolean canGenerateAt(CaveBiomeGenMethods gen, CaveListWrapper cave) {
		IBlockState temp = BlockSets.getTransformedState(gen.getBlockState(cave.centerpos.getFloorPos()), Modifier.COBBLE);
		block = BlockSets.getTransformedState(temp, Modifier.MOSSY);
		
		if (block==null){
			block = Blocks.MOSSY_COBBLESTONE.getDefaultState();
		}
		return true;
	}

	public void generate(CaveBiomeGenMethods gen, Random random, BlockPos pos) {
			gen.replaceBlock(pos, block);
	}
	

}
