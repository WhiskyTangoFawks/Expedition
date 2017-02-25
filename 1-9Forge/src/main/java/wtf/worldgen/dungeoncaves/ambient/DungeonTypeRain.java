package wtf.worldgen.dungeoncaves.ambient;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.blocks.BlockDecoAnim;
import wtf.init.BlockSets;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.dungeoncaves.AbstractDungeonType;

public class DungeonTypeRain extends AbstractDungeonType{


	public DungeonTypeRain(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canGenerateAt(GeneratorMethods gen, CaveListWrapper cave) {
		return true;
	}

	@Override
	public void generateCeilingAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {

	}


	@Override
	public void generateCenter(GeneratorMethods gen, Random rand, CavePosition pos, float depth) {
		// TODO Auto-generated method stub

	}

	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		IBlockState state = BlockSets.getTransformedState(gen.getWorld().getBlockState(pos), Modifier.WATER_DRIP);
		
		if (state != null && random.nextBoolean()){
			gen.replaceBlock(pos, state.withProperty(BlockDecoAnim.FAST, true));
		}
		else {
			int var = random.nextInt(3);
			switch (var){
			case 0:
				gen.transformBlock(pos, Modifier.COBBLE);
				break;
			case 1:
				gen.transformBlock(pos, Modifier.MOSSY);
				break;
			case 2:
				gen.transformBlock(pos, Modifier.COBBLE);
				gen.transformBlock(pos, Modifier.MOSSY);
				break;
			}
		}

	}

	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		int var = random.nextInt(3);
		boolean water = random.nextBoolean();
		switch (var){
		case 0:
			if (water){
				gen.setWaterPatch(pos);
				gen.transformBlock(pos, Modifier.COBBLE);
			}
			else {
				gen.transformBlock(pos, Modifier.COBBLE);
			}
			break;
		case 1:
			if (water){
				gen.setWaterPatch(pos);
				gen.transformBlock(pos, Modifier.MOSSY);
			}
			else {
				gen.transformBlock(pos, Modifier.MOSSY);	
			}
			break;
		case 2:
			if (water){
				gen.setWaterPatch(pos);
				gen.transformBlock(pos, Modifier.COBBLE);
				gen.transformBlock(pos, Modifier.MOSSY);
			}
			else {
				gen.transformBlock(pos, Modifier.COBBLE);
				gen.transformBlock(pos.down(), Modifier.MOSSY);
			}
			break;
		}

	}

	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {
		int var = random.nextInt(3);
		switch (var){
		case 0:
			gen.transformBlock(pos, Modifier.COBBLE);
			break;
		case 1:
			gen.transformBlock(pos, Modifier.MOSSY);
			break;
		case 2:
			gen.transformBlock(pos, Modifier.COBBLE);
			gen.transformBlock(pos, Modifier.MOSSY);
			break;
		}

	}

	@Override
	public void generateFloorAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}


}
