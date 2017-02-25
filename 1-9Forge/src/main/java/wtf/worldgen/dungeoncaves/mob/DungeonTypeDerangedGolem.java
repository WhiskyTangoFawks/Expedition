package wtf.worldgen.dungeoncaves.mob;

import java.util.Random;

import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.Core;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.dungeoncaves.AbstractDungeonType;

public class DungeonTypeDerangedGolem extends AbstractDungeonType {


	public DungeonTypeDerangedGolem(String name) {
		super(name, 0, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canGenerateAt(GeneratorMethods gen, CaveListWrapper cave) {
		return isHeight(cave, 4);
	}
	
	@Override
	public void generateCenter(GeneratorMethods gen, Random rand, CavePosition pos, float depth) {
		//anvil in center, with golem spawner underneath
		//gen.spawnVanillaSpawner(pos.getFloorPos(), Core.coreID+".ZombieMummy", 3);
		gen.spawnVanillaSpawner(pos.getFloorPos().up(), Core.coreID+".DerangedIronGolem", 1);
		gen.replaceBlock(pos.getFloorPos().up(2), Blocks.ANVIL.getDefaultState());
		
	}

	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		gen.replaceBlock(pos, Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED));
		
	}

	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		gen.replaceBlock(pos, Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED));
		
	}

	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {
		gen.replaceBlock(pos, Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED));
		
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
