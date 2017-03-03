package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.block.BlockSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.init.WTFBlocks;
import wtf.worldgen.GeneratorMethods;

public class CaveTypeRockySandy extends CaveTypeRocky{

	private final IBlockState sand;
	private final IBlockState sandstone;
	private final IBlockState slab;
	
	public CaveTypeRockySandy(String name, int ceilingAddonPercentChance, int floorAddonPercentChance, boolean redSand) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		this.sand = redSand ?  Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND) : Blocks.SAND.getDefaultState();
		this.sandstone = redSand? WTFBlocks.natRedSandStone.getDefaultState() : WTFBlocks.natSandStone.getDefaultState();
		this.slab = redSand? WTFBlocks.redSandSlab.getDefaultState() : WTFBlocks.sandSlab.getDefaultState();
	}


	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		super.generateFloor(gen, random, pos, depth);		
		if (simplex.get3DNoiseScaled(gen.getWorld(),pos, 0.2) < 0.5 ){
			gen.setPatch(pos, slab);
		}
	}
	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {
		if (height < 3 && simplex.get3DNoiseScaled(gen.getWorld(),pos, 0.2) < 0.5 ){
			gen.replaceBlock(pos, sandstone);
		}
		super.generateWall(gen, random, pos, depth, height);
	}


}
