package wtf.worldgen.subcaves.ambient;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.core.utilities.wrappers.CaveListWrapper;
import wtf.core.utilities.wrappers.CavePosition;
import wtf.worldgen.AbstractDungeonType;
import wtf.worldgen.CaveBiomeGenMethods;

public class DungeonTypeFrozenSolid extends AbstractDungeonType{


	public DungeonTypeFrozenSolid(String name) {
		super(name, 0, 0, false);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canGenerateAt(CaveBiomeGenMethods gen, CaveListWrapper cave) {
		return isSize(cave, 3);
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
		for (int loop = 1; gen.getBlockState(pos.up(loop)).hashCode() == Blocks.AIR.hashCode(); loop++){
			if (random.nextBoolean()){
				gen.replaceBlock(pos, Blocks.ICE.getDefaultState());
			}
			else {
				gen.replaceBlock(pos, Blocks.PACKED_ICE.getDefaultState());
			}
		}
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
