package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import wtf.blocks.BlockRoots;
import wtf.init.WTFBlocks;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.AdjPos;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.caves.AbstractCaveType;

public class CaveTypeLush extends AbstractCaveType{

	public CaveTypeLush(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
	}
	
	final IBlockState leaves = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE).withProperty(BlockLeaves.DECAYABLE, Boolean.valueOf(false));
	final IBlockState roots = WTFBlocks.roots.getDefaultState().withProperty(BlockRoots.TYPE, BlockRoots.RootType.jungle);
	final IBlockState fern = Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.FERN);
	final IBlockState sapling = Blocks.SAPLING.getDefaultState().withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.JUNGLE);


	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		double noise = simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.1);
		boolean mossy = simplex.get3DNoise(gen.getWorld(), pos) > 0.5;
		
		if (noise < 0.33){
			gen.replaceBlock(pos, Blocks.DIRT.getDefaultState());
		}
		if (mossy){	
			gen.transformBlock(pos, Modifier.MOSSY);
		}
	}

	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		
		double noise = simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.1);
		boolean mossy =  simplex.get3DNoise(gen.getWorld(), pos) > 0.5;
		
		if (noise < 0.33){
			gen.replaceBlock(pos, Blocks.DIRT.getDefaultState());
		}
		if (simplex.get3DNoiseShifted(gen.getWorld(), pos, -100) > 0.66){
			if (mossy){
				gen.setPatch(pos, WTFBlocks.mossyDirtSlab.getDefaultState());
			}
			else {
				gen.setPatch(pos, WTFBlocks.dirtSlab.getDefaultState());
			}
		}
		
		if (simplex.get3DNoise(gen.getWorld(), pos) > 0.75){
			gen.transformBlock(pos, Modifier.COBBLE);
		}
		if (mossy){	
			gen.transformBlock(pos, Modifier.MOSSY);
		}
		
	}


	@Override
	public void generateCeilingAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		double noise = simplex.get3DNoiseScaled(gen.getWorld(), pos.up(), 0.1);
		if (noise < 0.33){
			gen.replaceBlock(pos, roots);
		}
		else{
			gen.setCeilingAddon(pos, Modifier.COBBLE);
			if (random.nextBoolean()){
				for (int loop = random.nextInt(3)+1; loop > -1; loop--){
					gen.GenVines(pos.east().down(loop), EnumFacing.WEST);
				}
			}
			if (random.nextBoolean()){
				for (int loop = random.nextInt(3)+1; loop > -1; loop--){
					gen.GenVines(pos.west().down(loop), EnumFacing.EAST);
				}
			}
			if (random.nextBoolean()){
				for (int loop = random.nextInt(3)+1; loop > -1; loop--){
					gen.GenVines(pos.north().down(loop), EnumFacing.SOUTH);
				}
			}
			if (random.nextBoolean()){
				for (int loop = random.nextInt(3)+1; loop > -1; loop--){
					gen.GenVines(pos.south().down(loop), EnumFacing.NORTH);
				}
			}
		}
		
	}

	@Override
	public void generateFloorAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		double noise = simplex.get3DNoiseScaled(gen.getWorld(), pos.down(), 0.1);
		if (noise < 0.165){
			gen.replaceBlock(pos, fern );
		}
		else if (noise < 0.33){
			gen.replaceBlock(pos, sapling);
		}
		else if (random.nextBoolean()){
			gen.replaceBlock(pos, leaves);
			gen.replaceBlock(pos.north(), leaves);
			gen.replaceBlock(pos.south(), leaves);
			gen.replaceBlock(pos.east(), leaves);
			gen.replaceBlock(pos.west(), leaves);
			gen.replaceBlock(pos.up(), leaves);
		}

	}

	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {

		double noise = simplex.get3DNoiseScaled(gen.getWorld(), pos, 0.1);
		boolean mossy = simplex.get3DNoise(gen.getWorld(), pos) > 0.5;
		
		if (noise < 0.33){
			gen.replaceBlock(pos, Blocks.DIRT.getDefaultState());
		}
		
		if (simplex.get3DNoise(gen.getWorld(), pos) > 0.75){
			gen.transformBlock(pos, Modifier.COBBLE);
		}
		if (mossy){	
			gen.transformBlock(pos, Modifier.MOSSY);
		}
		
	}

	@Override
	public void generateAdjacentWall(GeneratorMethods gen, Random random, AdjPos pos, float depth, int height){
		boolean mossy = simplex.get3DNoise(gen.getWorld(), pos) > 0.5;
		if (mossy){
			gen.GenVines(pos, pos.getFace(random));
		}
	}
	


}
