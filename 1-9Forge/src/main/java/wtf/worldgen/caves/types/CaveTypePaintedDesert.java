package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import wtf.init.WTFBlocks;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.caves.AbstractCaveType;

public class CaveTypePaintedDesert extends AbstractCaveType{

	IBlockState sand = Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND);
	IBlockState sandstone = Blocks.RED_SANDSTONE.getDefaultState();

	//Red, orange, yellow, brown, white, light gray and unstained hardened c

	IBlockState[] slabs = { 
			WTFBlocks.claySlab.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BLUE),
			WTFBlocks.claySlab.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.CYAN),
			WTFBlocks.claySlab.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.LIGHT_BLUE),
			WTFBlocks.claySlab.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.SILVER),
			WTFBlocks.claySlab.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.WHITE),
			WTFBlocks.claySlab.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.GRAY),
			WTFBlocks.claySlab.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.YELLOW),
			WTFBlocks.claySlab.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BLACK),
			WTFBlocks.claySlab.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BROWN),
			WTFBlocks.claySlab.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED),
			WTFBlocks.claySlab.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.PINK),
			WTFBlocks.claySlab.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.MAGENTA),
			
	};
	
	IBlockState[] clay = {
			Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BLUE),
			Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.CYAN),
			Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.LIGHT_BLUE),
			Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.SILVER),
			Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.WHITE),
			Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.GRAY),
			Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.YELLOW),
			Blocks.HARDENED_CLAY.getDefaultState(),
			Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BROWN),
			Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED),
			Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.PINK),
			Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.MAGENTA),
	};
	


	public CaveTypePaintedDesert(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);

	}



	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {

	}

	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {

		double noise = simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.2);
		if (noise < 0.3){
			gen.replaceBlock(pos.up(), slabs[(int)simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.33)*slabs.length]);
		}
		else if (noise > 0.66){
			if (random.nextBoolean()){
				gen.replaceBlock(pos, Blocks.GRASS_PATH.getDefaultState());
			}
			else {
				gen.replaceBlock(pos.up(), WTFBlocks.redSandSlab.getDefaultState());
			}
		}

	}

	@Override
	public void generateCeilingAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		gen.genSpeleothem(pos, getSpelSize(random, depth), depth, false);
	}

	@Override
	public void generateFloorAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		gen.genSpeleothem(pos, getSpelSize(random, depth), depth, false);
	}

	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {
		if (height < 3){
		double noise = simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.2);
		if (noise < 0.3){
			gen.replaceBlock(pos.up(), clay[(int)simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.33)*clay.length]);
		}
		}
	}

}
