package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.init.WTFBlocks;
import wtf.init.BlockSets.Modifier;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.caves.AbstractCaveType;

public class CaveTypePodzol extends AbstractCaveType{

	public CaveTypePodzol(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
	}
	
	IBlockState podzol = Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);

	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		if (simplex.get3DNoise(gen.getWorld(), pos) > 0.66){
			gen.transformBlock(pos, Modifier.MOSSY);
		}
	}

	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		if (simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.2) < 0.33){
			gen.replaceBlock(pos.up(), WTFBlocks.podzolSlab.getDefaultState());
		}
		else {
			if (simplex.get3DNoise(gen.getWorld(), pos) > 0.66){
				gen.transformBlock(pos, Modifier.MOSSY);
			}
		}
	}

	@Override
	public void generateCeilingAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		gen.genSpeleothem(pos, getSpelSize(random, depth), depth, false);

	}

	@Override
	public void generateFloorAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		Block mushroom = simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.2) > 0.5 ? Blocks.BROWN_MUSHROOM : Blocks.RED_MUSHROOM;
		gen.replaceBlock(pos, mushroom.getDefaultState());
	}

	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {

		if (height < 3 && simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.2) < 0.33){
			gen.replaceBlock(pos, podzol);
		}
		if (simplex.get3DNoise(gen.getWorld(), pos) > 0.66){
				gen.transformBlock(pos, Modifier.MOSSY);
		}
		
	}

}
