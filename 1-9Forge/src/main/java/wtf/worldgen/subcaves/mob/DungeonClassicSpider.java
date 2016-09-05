package wtf.worldgen.subcaves.mob;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.AbstractDungeonType;
import wtf.worldgen.CaveBiomeGenMethods;

public class DungeonClassicSpider extends AbstractDungeonType{


	public DungeonClassicSpider(String name) {
		super(name, 0, 0, false);

	}
	
	@Override
	public boolean canGenerateAt(CaveBiomeGenMethods gen, CaveListWrapper cave) {
		return isSize(cave, 7) && isHeight(cave, 5);
	}

	@Override
	public void generateCenter(CaveBiomeGenMethods gen, Random rand, CavePosition pos, float depth) {

		int center = pos.getFloorPos().getY() + (pos.getCeilingPos().getY() - pos.getFloorPos().getY())/2;
		
		for (int xloop = -1; xloop < 2; xloop++){
			for (int yloop = -1; yloop < 2; yloop++){
				for (int zloop = -1; zloop < 2; zloop++){
					if (rand.nextBoolean()){
						int xweb = pos.x + xloop;
						int yweb = center + yloop;
						int zweb = pos.z + zloop;
						BlockPos web = new BlockPos(xweb, yweb, zweb);
						int count = 0;
						while (gen.getBlockState(web).getBlock().hashCode() == gen.airHash && count < 8){
							count++;
							gen.replaceBlock(web, Blocks.WEB.getDefaultState());
							xweb += xloop;
							yweb += yloop;
							zweb += pos.z + zloop;
							web = new BlockPos(xweb, yweb, zweb);
							
						}
					}
				}
			}	
		}
		gen.spawnVanillaSpawner(new BlockPos(pos.x, center, pos.z), "Spider", 3);
		
	}


	@Override
	public void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		gen.replaceBlock(pos, Blocks.MOSSY_COBBLESTONE.getDefaultState());
		
	}


	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		gen.replaceBlock(pos, Blocks.MOSSY_COBBLESTONE.getDefaultState());
		
	}

	@Override
	public void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height) {
		gen.replaceBlock(pos, Blocks.MOSSY_COBBLESTONE.getDefaultState());
		
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

