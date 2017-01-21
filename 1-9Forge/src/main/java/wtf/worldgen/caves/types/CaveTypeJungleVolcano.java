package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import wtf.config.CaveBiomesConfig;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.AdjPos;
import wtf.worldgen.AbstractCaveType;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class CaveTypeJungleVolcano extends AbstractCaveType{

	public CaveTypeJungleVolcano(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
	}

	@Override
	public void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub

	}

	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		double noise = getNoise(gen.chunk.getWorld(), pos, 6, 0.4F);
		if (noise < depth*3)
		{
			gen.replaceBlock(pos, Blocks.GRASS.getDefaultState());
		}
		else if (noise > depth*6 && noise > 4){
			gen.transformBlock(pos, Modifier.LAVA_CRUST);

			if (CaveBiomesConfig.enableLavaPools && noise > depth*7 && noise > 5){
				gen.setLavaPatch(pos);
			}
		}
		else if (noise < 3){
			gen.transformBlock(pos, Modifier.COBBLE);
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
		else if (!gen.genStalagmite(pos, depth, false)){
			gen.setFloorAddon(pos, Modifier.COBBLE);
		}
	}

	@Override
	public void generateFloorAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		double noise = getNoise(gen.chunk.getWorld(), pos.down(), 6, 0.2F);
		if (noise < depth*4)
		{
			gen.replaceBlock(pos, Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.FERN));
		}
		else 
		{
			if (random.nextFloat() > depth && !gen.genStalagmite(pos, depth, false)){
				gen.setFloorAddon(pos, Modifier.COBBLE);
			}
		}

	}

	@Override
	public void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height) {
		double mossNoise = getNoise(gen.chunk.getWorld(), pos, 5, 2F);
		if (mossNoise < 2*depth && random.nextBoolean()){
			gen.transformBlock(pos, Modifier.COBBLE);
		}
		else if (random.nextFloat() < 0.1){
			gen.transformBlock(pos, Modifier.LAVA_CRUST);
		}
	}

	@Override
	public void generateAdjacentWall(CaveBiomeGenMethods gen, Random random, AdjPos pos, float depth, int height){
		if (getNoise(gen.chunk.getWorld(), pos, 5, 1) < depth*1.5){	
			gen.GenVines(pos, pos.getFace(random));
		}
	}

}
