package wtf.worldgen.dungeoncaves.ambient;

import java.util.Random;

import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.AdjPos;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.dungeoncaves.AbstractDungeonType;

public class DungeonJungleTemple extends AbstractDungeonType{

	public DungeonJungleTemple(String name) {
		super(name, 5, 0);
	}
	
	@Override
	public boolean canGenerateAt(GeneratorMethods gen, CaveListWrapper cave) {
		return isHeight(cave, 4) && cave.getAvgFloor() > 36;
	}
	
	@Override
	public void generateCenter(GeneratorMethods gen, Random rand, CavePosition pos, float depth) {
		gen.replaceBlock(pos.getFloorPos(), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED));
		gen.replaceBlock(pos.getFloorPos().down(), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED));
		gen.replaceBlock(pos.getFloorPos().up(), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED));
		gen.replaceBlock(pos.getFloorPos().up(2), Blocks.TORCH.getDefaultState());
		
	}

	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		if (simplex.get3DNoise(gen.getWorld(), pos) < 0.25){
			gen.replaceBlock(pos, Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED));
		}
		else {
			gen.replaceBlock(pos, Blocks.STONEBRICK.getDefaultState());
		} 	
	
		
	}

	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		if (simplex.get3DNoise(gen.getWorld(), pos) < 0.25){
			gen.replaceBlock(pos, Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED));
		}
		else {
			gen.replaceBlock(pos, Blocks.STONEBRICK.getDefaultState());
		} 	
	
		
	}

	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {
		if (simplex.get3DNoise(gen.getWorld(), pos) < 0.25){
			gen.replaceBlock(pos, Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED));
		}
		else {
			gen.replaceBlock(pos, Blocks.STONEBRICK.getDefaultState());
		} 	
	}

	@Override
	public void generateCeilingAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		if (random.nextFloat() < depth && gen.setCeilingAddon(pos, Modifier.COBBLE)){
			for (int loop = random.nextInt(3)+1; loop > -1; loop--){
				gen.GenVines(pos.east().down(loop), EnumFacing.WEST);
			}
			for (int loop = random.nextInt(3)+1; loop > -1; loop--){
				gen.GenVines(pos.west().down(loop), EnumFacing.EAST);
			}
			for (int loop = random.nextInt(3)+1; loop > -1; loop--){
				gen.GenVines(pos.north().down(loop), EnumFacing.SOUTH);
			}
			for (int loop = random.nextInt(3)+1; loop > -1; loop--){
				gen.GenVines(pos.south().down(loop), EnumFacing.NORTH);
			}
		}
		
	}

	@Override
	public void generateFloorAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void generateAdjacentWall(GeneratorMethods gen, Random random, AdjPos pos, float depth, int height){
		if (simplex.get3DNoise(gen.getWorld(), pos) < 0.15){
			gen.GenVines(pos, pos.getFace(random));
		}
	}


}
