package wtf.worldgen.dungeoncaves.mob;

import java.util.Random;

import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.init.BlockSets;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.dungeoncaves.DungeonAbstractSimple;

public class DungeonSlime extends DungeonAbstractSimple{

	public DungeonSlime(String name) {
		super(name);
	}
	
	@Override
	public void generateCenter(GeneratorMethods gen, Random rand, CavePosition pos, float depth) {
		gen.spawnVanillaSpawner(pos.getFloorPos().up(), "Slime", 2);	
	}
	
	IBlockState block = null;
	
	@Override
	public boolean canGenerateAt(GeneratorMethods gen, CaveListWrapper cave) {
		IBlockState temp = BlockSets.getTransformedState(gen.getWorld().getBlockState(cave.centerpos.getFloorPos()), Modifier.COBBLE);
		block = BlockSets.getTransformedState(temp, Modifier.MOSSY);
		if (block==null){
			block = Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.MOSSY);
		}
		return true;
	}

	public void generate(GeneratorMethods gen, Random random, BlockPos pos) {
			gen.replaceBlock(pos, block);
	}

	@Override
	public void generateCeilingAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		gen.replaceBlock(pos, Blocks.SLIME_BLOCK.getDefaultState());
		
	}

}
