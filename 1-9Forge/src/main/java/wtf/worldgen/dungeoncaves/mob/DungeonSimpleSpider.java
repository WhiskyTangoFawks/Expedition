package wtf.worldgen.dungeoncaves.mob;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.init.BlockSets;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.dungeoncaves.DungeonAbstractSimple;

public class DungeonSimpleSpider extends DungeonAbstractSimple{


	public DungeonSimpleSpider(String name) {
		super(name);
		this.genAir = true;
	}

	@Override
	public boolean canGenerateAt(GeneratorMethods gen, CaveListWrapper cave) {
		IBlockState temp = BlockSets.getTransformedState(gen.getWorld().getBlockState(cave.centerpos.getFloorPos()), Modifier.COBBLE);
		block = BlockSets.getTransformedState(temp, Modifier.MOSSY);
		
		if (block==null){
			block = Blocks.MOSSY_COBBLESTONE.getDefaultState();
		}
		return isHeight(cave, 4);
	}

	IBlockState block = null;
	
	
	@Override
	public void generateCenter(GeneratorMethods gen, Random rand, CavePosition pos, float depth) {

		BlockPos midpos = pos.getMidPos();

		gen.spawnVanillaSpawner(midpos, "Spider", 3);
		midpos = midpos.down();
		while (midpos.getY() > pos.floor){
			gen.replaceBlock(midpos, Blocks.MOSSY_COBBLESTONE.getDefaultState());
			midpos = midpos.down();
		}

	}



	@Override
	public void generateAir(GeneratorMethods gen, Random random, BlockPos floating, float depth){
		double noise = simplex.get3DNoise(gen.getWorld(), floating.getX(), floating.getY(), floating.getZ());

		if (noise < 0.2){
			gen.replaceBlock(floating, Blocks.WEB.getDefaultState());
		}
	}

	@Override
	public void generate(GeneratorMethods gen, Random random, BlockPos pos) {
		gen.replaceBlock(pos, block);
}

}

