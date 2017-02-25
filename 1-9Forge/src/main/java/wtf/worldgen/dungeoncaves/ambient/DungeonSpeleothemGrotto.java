package wtf.worldgen.dungeoncaves.ambient;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.dungeoncaves.AbstractDungeonType;

public class DungeonSpeleothemGrotto extends AbstractDungeonType{

	public DungeonSpeleothemGrotto(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean canGenerateAt(GeneratorMethods gen, CaveListWrapper cave) {
		return  isHeight(cave, 4);
	}


	@Override
	public void generateCeilingAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		if (simplex.get2DRandom(gen.getWorld(), pos) < 0.5){
			gen.genSpeleothem(pos, random.nextInt(5), depth, BiomeDictionary.isBiomeOfType(gen.getWorld().getBiome(pos), Type.SNOWY));
		}
	}
	@Override
	public void generateFloorAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		if (simplex.get2DRandom(gen.getWorld(), pos) < 0.5){
			gen.genSpeleothem(pos, random.nextInt(3), depth, BiomeDictionary.isBiomeOfType(gen.getWorld().getBiome(pos), Type.SNOWY));
		}
	}
	
	@Override
	public void generateCenter(GeneratorMethods gen, Random rand, CavePosition pos, float depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {
		// TODO Auto-generated method stub
		
	}


}
