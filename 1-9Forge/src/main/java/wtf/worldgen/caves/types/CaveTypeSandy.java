package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.block.BlockSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.worldgen.AbstractCaveType;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class CaveTypeSandy extends AbstractCaveType{

	private final IBlockState sand;
	private final IBlockState sandstone;
	
	public CaveTypeSandy(String name, int ceilingAddonPercentChance, int floorAddonPercentChance, boolean redSand) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		this.sand = redSand ?  Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND) : Blocks.SAND.getDefaultState();
		this.sandstone = redSand? Blocks.RED_SANDSTONE.getDefaultState() : Blocks.SANDSTONE.getDefaultState();
	}

	

	@Override
	public void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		double noise = getNoise(pos, 5, 0.2F);
		if (noise < depth*3 ){
			gen.replaceBlock(pos, sandstone);
		}
	}

	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		
		double noise = getNoise(pos, 5, 0.2F);
		if (noise < depth*3){
			gen.replaceBlock(pos, sandstone);
		}
		else if (noise < depth*6 ){
			gen.replaceBlock(pos, sand);
		}
	}

	@Override
	public void generateCeilingAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		gen.genStalactite(pos, depth, false);
	}

	@Override
	public void generateFloorAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		double noise = getNoise(pos, 5, 0.2F);
		if (noise < depth*3 ){
			gen.replaceBlock(pos, sand);
		}
		
		gen.genStalagmite(pos, depth, false);
		
	}

	@Override
	public void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height) {
		double noise = getNoise(pos, 5, 0.2F);
		if (noise < depth*3 ){
			gen.replaceBlock(pos, sandstone);
		}
		else if (noise < depth*6 ){
			gen.replaceBlock(pos, sand);
		}
	}




}
