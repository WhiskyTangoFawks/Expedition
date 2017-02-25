package wtf.worldgen.dungeoncaves.ambient;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.dungeoncaves.AbstractDungeonType;

public class DungeonTypeCaveIn extends AbstractDungeonType {



	public DungeonTypeCaveIn(String name) {
		super(name, 0, 0);
		this.genAir = true;
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

	}

	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub

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

	@Override
	public void generateAir(GeneratorMethods gen, Random random, BlockPos floating, float depth){
		double noise = simplex.get3DNoise(gen.getWorld(), floating.getX(), floating.getY(), floating.getZ());

		if (noise < 0.5){
			gen.genFloatingStone(floating);
			gen.transformBlock(floating, Modifier.COBBLE);
		}
	}



}
