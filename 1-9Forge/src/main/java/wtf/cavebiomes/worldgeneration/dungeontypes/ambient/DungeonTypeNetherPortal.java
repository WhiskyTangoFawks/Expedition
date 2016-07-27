package wtf.cavebiomes.worldgeneration.dungeontypes.ambient;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.cavebiomes.worldgeneration.AbstractDungeonType;
import wtf.cavebiomes.worldgeneration.CaveBiomeGenMethods;
import wtf.core.utilities.wrappers.CaveListWrapper;
import wtf.core.utilities.wrappers.CavePosition;

public class DungeonTypeNetherPortal extends AbstractDungeonType{



	public DungeonTypeNetherPortal(String name) {
		super(name, 0, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canGenerateAt(CaveBiomeGenMethods gen, CaveListWrapper cave) {
		return cave.getSizeX() > 6 && cave.getSizeZ() > 6 && cave.getAvgCeiling() - cave.getAvgFloor() > 7;
	}
	
	@Override
	public void generateCenter(CaveBiomeGenMethods gen, Random rand, CavePosition pos, float depth) {
		
		
		gen.replaceBlock(pos.getFloorPos().up(), Blocks.OBSIDIAN.getDefaultState());
		gen.replaceBlock(pos.getFloorPos().up().east(), Blocks.OBSIDIAN.getDefaultState());
		gen.replaceBlock(pos.getFloorPos().west(), Blocks.OBSIDIAN.getDefaultState());
		for (int loop = 1; loop < 7;loop++){
			gen.replaceBlock(pos.getFloorPos().up(loop).east(2), Blocks.OBSIDIAN.getDefaultState());
			gen.replaceBlock(pos.getFloorPos().up(loop).west(2), Blocks.OBSIDIAN.getDefaultState());
		}
		gen.replaceBlock(pos.getFloorPos().up(6), Blocks.OBSIDIAN.getDefaultState());
		gen.replaceBlock(pos.getFloorPos().up(6).east(), Blocks.OBSIDIAN.getDefaultState());
		gen.replaceBlock(pos.getFloorPos().up(6).west(), Blocks.OBSIDIAN.getDefaultState());
		for (int xloop = -1; xloop < 2; xloop++){
			for (int yloop = 2; yloop <6; yloop++){
				gen.blocksToSet.put(pos.getFloorPos().up(yloop).east(xloop), Blocks.PORTAL.getDefaultState());
			}
		}
		
	}

	@Override
	public void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		gen.replaceBlock(pos, Blocks.NETHERRACK.getDefaultState());
		
	}

	@Override
	public void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateCeilingAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateFloorAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}
}
