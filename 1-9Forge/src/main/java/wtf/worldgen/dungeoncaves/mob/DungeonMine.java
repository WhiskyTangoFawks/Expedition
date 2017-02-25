package wtf.worldgen.dungeoncaves.mob;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.Core;
import wtf.utilities.wrappers.AdjPos;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.dungeoncaves.AbstractDungeonType;

public class DungeonMine extends AbstractDungeonType{

	public DungeonMine(String name) {
		super(name, 0, 0);
	}
	

	@Override
	public boolean canGenerateAt(GeneratorMethods gen, CaveListWrapper cave) {
		return this.isHeight(cave, 5);
	}

	@Override
	public void generateCenter(GeneratorMethods gen, Random rand, CavePosition pos, float depth) {
		gen.spawnVanillaSpawner(pos.getFloorPos().up(), Core.coreID+".ZombieMiner", 5);
		gen.replaceBlock(pos.getFloorPos().up(2), Blocks.TNT.getDefaultState());
	}

	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		int x = pos.getX()%10;
		int z = pos.getZ()%10;
		
		if (x == 1 || x == 5 || x == 8 ||z == 1 || z == 5 ||z == 8){
			gen.replaceBlock(pos.down(), Blocks.SPRUCE_FENCE.getDefaultState());
		}
	}

	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {

	}

	@Override
	public void generateCeilingAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateFloorAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateAdjacentWall(GeneratorMethods gen, Random random, AdjPos pos, float depth, int height){
		int x = pos.getX()%10;
		int z = pos.getZ()%10;
		if (x == 1 || x == 5 || x == 8 ||z == 1 || z == 5 ||z == 8){
			gen.replaceBlock(pos.down(), Blocks.SPRUCE_FENCE.getDefaultState());
		}
		
	}
	
}
