package wtf.cavebiomes.worldgeneration.dungeontypes.ambient;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.cavebiomes.worldgeneration.AbstractDungeonType;
import wtf.cavebiomes.worldgeneration.CaveBiomeGenMethods;
import wtf.core.blocks.BlockFoxfire;
import wtf.core.init.WTFBlocks;
import wtf.core.utilities.wrappers.CaveListWrapper;
import wtf.core.utilities.wrappers.CavePosition;

public class DungeonTypeFoxfire extends AbstractDungeonType {


	public DungeonTypeFoxfire(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canGenerateAt(CaveBiomeGenMethods gen, CaveListWrapper cave) {
		return cave.getSizeX() > 4 && cave.getSizeZ() > 4 && cave.getAvgCeiling() - cave.getAvgFloor() > 4;
	}

	@Override
	public void generateCeilingAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		gen.replaceBlock(pos.up(2), Blocks.LOG.getDefaultState());
		gen.replaceBlock(pos.up(), Blocks.LOG.getDefaultState());
		gen.replaceBlock(pos, Blocks.LOG.getDefaultState());
		gen.replaceBlock(pos.down(), Blocks.LOG.getDefaultState());
		gen.replaceBlock(pos.down(2), WTFBlocks.foxfire.getDefaultState().withProperty(BlockFoxfire.HANGING, true));
	}
	@Override
	public void generateFloorAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		gen.replaceBlock(pos.down(2), Blocks.LOG.getDefaultState());
		gen.replaceBlock(pos.down(), Blocks.LOG.getDefaultState());
		gen.replaceBlock(pos, Blocks.LOG.getDefaultState());
		gen.replaceBlock(pos.up(), Blocks.LOG.getDefaultState());
		gen.replaceBlock(pos.up(2), WTFBlocks.foxfire.getDefaultState());
	}
	
	@Override
	public void generateCenter(CaveBiomeGenMethods gen, Random rand, CavePosition pos, float depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height) {
		// TODO Auto-generated method stub
		
	}


}