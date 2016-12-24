package wtf.worldgen.subcaves.ambient;

import java.util.Random;

import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.AdjPos;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.AbstractDungeonType;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class DungeonJungleTemple extends AbstractDungeonType{

	public DungeonJungleTemple(String name) {
		super(name, 5, 0, false);
	}
	
	@Override
	public boolean canGenerateAt(CaveBiomeGenMethods gen, CaveListWrapper cave) {
		return isSize(cave, 6) && isHeight(cave, 4) && cave.getAvgFloor() > 48;
	}
	
	@Override
	public void generateCenter(CaveBiomeGenMethods gen, Random rand, CavePosition pos, float depth) {
		gen.replaceBlock(pos.getFloorPos(), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED));
		gen.replaceBlock(pos.getFloorPos().down(), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED));
		gen.replaceBlock(pos.getFloorPos().up(), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED));
		gen.replaceBlock(pos.getFloorPos().up(2), Blocks.TORCH.getDefaultState());
		
	}

	@Override
	public void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		if (getNoise(pos, 5, 1) < 1.5){
			gen.replaceBlock(pos, Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED));
		}
		else {
			gen.replaceBlock(pos, Blocks.STONEBRICK.getDefaultState());
		} 	
	
		
	}

	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		if (getNoise(pos, 5, 1) < 1.5){
			gen.replaceBlock(pos, Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED));
		}
		else {
			gen.replaceBlock(pos, Blocks.STONEBRICK.getDefaultState());
		} 	
	
		
	}

	@Override
	public void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height) {
		if (getNoise(pos, 5, 1) < 1.5){
			gen.replaceBlock(pos, Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED));
		}
		else {
			gen.replaceBlock(pos, Blocks.STONEBRICK.getDefaultState());
		} 	
	}

	@Override
	public void generateCeilingAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		if (random.nextFloat() < depth && !gen.isChunkEdge(pos) && gen.setCeilingAddon(pos, Modifier.COBBLE)){
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
	public void generateFloorAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void generateAdjacentWall(CaveBiomeGenMethods gen, Random random, AdjPos pos, float depth, int height){
		if (getNoise(pos, 5, 1) < depth*1.5){	
			gen.GenVines(pos, pos.getFace(random));
		}
	}

}
