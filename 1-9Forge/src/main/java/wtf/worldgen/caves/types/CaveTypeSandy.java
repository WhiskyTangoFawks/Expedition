package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.block.BlockSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.init.WTFBlocks;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.caves.AbstractCaveType;

public class CaveTypeSandy extends AbstractCaveType{

	private final IBlockState sand;
	private final IBlockState sandstone;
	private final IBlockState slab;
	
	public CaveTypeSandy(String name, int ceilingAddonPercentChance, int floorAddonPercentChance, boolean redSand) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		this.sand = redSand ?  Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND) : Blocks.SAND.getDefaultState();
		this.sandstone = redSand? WTFBlocks.natRedSandStone.getDefaultState() : WTFBlocks.natSandStone.getDefaultState();
		this.slab = redSand? WTFBlocks.redSandSlab.getDefaultState() : WTFBlocks.sandSlab.getDefaultState();
	}

	

	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		
	}

	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		
		if (simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.2) < 0.3){
			gen.setPatch(pos, slab);
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
		if (height < 3 && simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.2) < 0.3){
			gen.replaceBlock(pos, sandstone);
		}
	}




}
