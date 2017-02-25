package wtf.worldgen.dungeoncaves.ambient;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.dungeoncaves.AbstractDungeonType;

public class DungeonTypeFrozenSolid extends AbstractDungeonType{


	public DungeonTypeFrozenSolid(String name) {
		super(name, 0, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canGenerateAt(GeneratorMethods gen, CaveListWrapper cave) {
		return true;
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
	public void generateAir(GeneratorMethods gen, Random random, BlockPos floating, float depth){
		if (random.nextBoolean()){
			gen.replaceBlock(floating, Blocks.ICE.getDefaultState());
		}
		else {
			gen.replaceBlock(floating, Blocks.PACKED_ICE.getDefaultState());
		}

	}
	
	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
	
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
