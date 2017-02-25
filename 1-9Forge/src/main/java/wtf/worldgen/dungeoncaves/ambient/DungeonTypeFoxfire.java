package wtf.worldgen.dungeoncaves.ambient;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.blocks.BlockFoxfire;
import wtf.init.WTFBlocks;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.dungeoncaves.AbstractDungeonType;

public class DungeonTypeFoxfire extends AbstractDungeonType {


	public DungeonTypeFoxfire(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canGenerateAt(GeneratorMethods gen, CaveListWrapper cave) {
		return isHeight(cave, 4);
	}

	@Override
	public void generateCeilingAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		gen.replaceBlock(pos.up(2), Blocks.LOG.getDefaultState());
		gen.replaceBlock(pos.up(), Blocks.LOG.getDefaultState());
		gen.replaceBlock(pos, Blocks.LOG.getDefaultState());
		gen.replaceBlock(pos.down(), Blocks.LOG.getDefaultState());
		gen.replaceBlock(pos.down(2), WTFBlocks.foxfire.getDefaultState().withProperty(BlockFoxfire.HANGING, true));
	}
	@Override
	public void generateFloorAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		gen.replaceBlock(pos.down(2), Blocks.LOG.getDefaultState());
		gen.replaceBlock(pos.down(), Blocks.LOG.getDefaultState());
		gen.replaceBlock(pos, Blocks.LOG.getDefaultState());
		gen.replaceBlock(pos.up(), Blocks.LOG.getDefaultState());
		gen.replaceBlock(pos.up(2), WTFBlocks.foxfire.getDefaultState());
	}
	
	@Override
	public void generateCenter(GeneratorMethods gen, Random rand, CavePosition pos, float depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {
		// TODO Auto-generated method stub
		
	}


}