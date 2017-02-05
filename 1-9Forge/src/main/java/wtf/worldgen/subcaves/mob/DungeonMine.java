package wtf.worldgen.subcaves.mob;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.Core;
import wtf.utilities.wrappers.AdjPos;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.AbstractDungeonType;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class DungeonMine extends AbstractDungeonType{

	public DungeonMine(String name) {
		super(name, 0, 0);
	}
	

	@Override
	public boolean canGenerateAt(CaveBiomeGenMethods gen, CaveListWrapper cave) {
		return this.isHeight(cave, 5);
	}

	@Override
	public void generateCenter(CaveBiomeGenMethods gen, Random rand, CavePosition pos, float depth) {
		gen.spawnVanillaSpawner(pos.getFloorPos().up(), Core.coreID+".ZombieMiner", 5);
		gen.replaceBlock(pos.getFloorPos().up(2), Blocks.TNT.getDefaultState());
	}

	@Override
	public void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		int x = pos.getX()%10;
		int z = pos.getZ()%10;
		
		if (x == 1 || x == 5 || x == 8 ||z == 1 || z == 5 ||z == 8){
			gen.replaceBlock(pos.down(), Blocks.SPRUCE_FENCE.getDefaultState());
		}
	}

	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {

	}

	@Override
	public void generateCeilingAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateFloorAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateAdjacentWall(CaveBiomeGenMethods gen, Random random, AdjPos pos, float depth, int height){
		int x = pos.getX()%10;
		int z = pos.getZ()%10;
		if (x == 1 || x == 5 || x == 8 ||z == 1 || z == 5 ||z == 8){
			gen.replaceBlock(pos.down(), Blocks.SPRUCE_FENCE.getDefaultState());
		}
		
	}
	
}
