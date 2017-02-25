package wtf.worldgen.dungeoncaves.mob;

import java.util.Random;

import net.minecraft.block.BlockSandStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.dungeoncaves.DungeonAbstractSimple;

public class DungeonSimpleHusk extends DungeonAbstractSimple{

	public DungeonSimpleHusk(String name) {
		super(name);
	}

	@Override
	public void generateCenter(GeneratorMethods gen, Random rand, CavePosition pos, float depth) {
		gen.spawnVanillaSpawner(pos.getFloorPos().up(), "wtfcore.Husk", 5);	
	}

	IBlockState sandstone = Blocks.SANDSTONE.getDefaultState().withProperty(BlockSandStone.TYPE, BlockSandStone.EnumType.SMOOTH);
	
	public void generate(GeneratorMethods gen, Random random, BlockPos pos) {
			gen.replaceBlock(pos, sandstone);
	}
	

}
