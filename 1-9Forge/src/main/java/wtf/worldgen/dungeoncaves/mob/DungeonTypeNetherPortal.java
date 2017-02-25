package wtf.worldgen.dungeoncaves.mob;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.dungeoncaves.AbstractDungeonType;

public class DungeonTypeNetherPortal extends AbstractDungeonType{



	public DungeonTypeNetherPortal(String name) {
		super(name, 0, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canGenerateAt(GeneratorMethods gen, CaveListWrapper cave) {
		return isHeight(cave, 6);
	}
	
	@Override
	public void generateCenter(GeneratorMethods gen, Random rand, CavePosition pos, float depth) {
		gen.spawnVanillaSpawner(pos.getFloorPos(), "PigZombie", 5);
		
		gen.replaceBlock(pos.getFloorPos().up(), Blocks.OBSIDIAN.getDefaultState());
		gen.replaceBlock(pos.getFloorPos().up().east(), Blocks.OBSIDIAN.getDefaultState());
		gen.replaceBlock(pos.getFloorPos().west(), Blocks.OBSIDIAN.getDefaultState());
		for (int loop = 1; loop < 7;loop++){
			gen.replaceBlock(pos.getFloorPos().up(loop).east(2), Blocks.OBSIDIAN.getDefaultState());
			gen.replaceBlock(pos.getFloorPos().up(loop).west(2), Blocks.OBSIDIAN.getDefaultState());
		}
		gen.replaceBlock(pos.getFloorPos().up(6), Blocks.OBSIDIAN.getDefaultState());
		gen.replaceBlock(pos.getFloorPos().up(6).east(), Blocks.OBSIDIAN.getDefaultState());
		gen.replaceBlock(pos.getFloorPos().up(6).west(), Blocks.OBSIDIAN.getDefaultState());
		for (int xloop = -1; xloop < 2; xloop++){
			for (int yloop = 2; yloop <6; yloop++){
				gen.replaceBlock(pos.getFloorPos().up(yloop).east(xloop), Blocks.PORTAL.getDefaultState());
			}
		}
		
	}

	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		gen.replaceBlock(pos, Blocks.NETHERRACK.getDefaultState());
		
	}

	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {
		// TODO Auto-generated method stub
		
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
