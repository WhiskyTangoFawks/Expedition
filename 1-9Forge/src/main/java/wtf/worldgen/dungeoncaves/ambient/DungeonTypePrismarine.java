package wtf.worldgen.dungeoncaves.ambient;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.dungeoncaves.AbstractDungeonType;

public class DungeonTypePrismarine extends AbstractDungeonType {



	public DungeonTypePrismarine(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canGenerateAt(GeneratorMethods gen, CaveListWrapper cave) {
		return cave.getAvgFloor()<48;
	}

	@Override
	public void generateCenter(GeneratorMethods gen, Random rand, CavePosition pos, float depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		gen.replaceBlock(pos, Blocks.PRISMARINE.getDefaultState());
		
	}

	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		gen.replaceBlock(pos, Blocks.PRISMARINE.getDefaultState());
		
	}

	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {
		if (height == 3){
			gen.replaceBlock(pos, Blocks.SEA_LANTERN.getDefaultState());
		}
		else {
			gen.replaceBlock(pos, Blocks.PRISMARINE.getDefaultState());
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
