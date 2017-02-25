package wtf.worldgen.dungeoncaves.mob;

import java.util.Random;

import net.minecraft.block.BlockSandStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.Core;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.dungeoncaves.AbstractDungeonType;

public class DungeonTypeMummy extends AbstractDungeonType{

	public DungeonTypeMummy(String name) {
		super(name, 0, 0);
	}
	
	@Override
	public boolean canGenerateAt(GeneratorMethods gen, CaveListWrapper cave) {
		return isHeight(cave, 4);
	}
	
	IBlockState sandstone = Blocks.SANDSTONE.getDefaultState().withProperty(BlockSandStone.TYPE, BlockSandStone.EnumType.SMOOTH);
	IBlockState chiseled = Blocks.SANDSTONE.getDefaultState().withProperty(BlockSandStone.TYPE, BlockSandStone.EnumType.CHISELED);
	
	@Override
	public void generateCenter(GeneratorMethods gen, Random rand, CavePosition pos, float depth) {
		
		gen.spawnVanillaSpawner(pos.getFloorPos().up(), Core.coreID+".Mummy", 5);
		
		gen.replaceBlock(pos.getFloorPos().up(2), Blocks.GOLD_BLOCK.getDefaultState());
		
	}


	
	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		
		
	}

	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		gen.replaceBlock(pos, sandstone);
		
	}

	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {
		if (height == 3){
			gen.replaceBlock(pos, chiseled);
		}
		else {
			gen.replaceBlock(pos, sandstone);
		} 	
	}

	@Override
	public void generateCeilingAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateFloorAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}


}
