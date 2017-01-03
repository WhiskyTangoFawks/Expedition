package wtf.worldgen.caves.types.nether;

import java.util.Random;

import net.minecraft.block.BlockSand;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.init.BlockSets.Modifier;
import wtf.init.WTFBlocks;
import wtf.worldgen.AbstractCaveType;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class NetherSoulDesert extends AbstractCaveType{

	//Soulsand, mixed with something else to make it traversable 
	//Stone or bone cacti
	
	public NetherSoulDesert() {
		super("NetherSoulDesert", 0, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		//Some sort of use of magma blocks, with obsidian stalactites?
		
	}

	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {

		if (getNoise(gen.chunk.getWorld(), pos, 1, 0.5F) < 0.5){
			gen.replaceBlock(pos, Blocks.SOUL_SAND.getDefaultState());
		}
		gen.transformBlock(pos, Modifier.COBBLE);
		double noise = getNoise(gen.chunk.getWorld(), pos, 6, 0.2F);
		if (noise < 2){
			gen.setLavaPatch(pos);
		}

	}

	@Override
	public void generateCeilingAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateFloorAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		for (int rand = random.nextInt(3); rand > -1; rand--){
			gen.replaceBlock(pos.up(rand), WTFBlocks.red_cactus.getDefaultState());
		}
		
	}

	@Override
	public void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height) {
		
	}

}
