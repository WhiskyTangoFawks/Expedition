package wtf.worldgen.caves;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.utilities.simplex.SimplexHelper;
import wtf.utilities.wrappers.AdjPos;
import wtf.utilities.wrappers.CavePosition;
import wtf.utilities.wrappers.SurfacePos;
import wtf.worldgen.GeneratorMethods;

public abstract class AbstractCaveType
{

	public int dungeonPercentChance = 5;
	final public String name;
	protected static SimplexHelper simplex = new SimplexHelper("CaveGenerator");
	
	public final int ceilingaddonchance;
	public final int flooraddonchance;

	public AbstractCaveType(String name, int ceilingAddonPercentChance, int floorAddonPercentChance)
	{
		this.name = name;
		this.ceilingaddonchance = ceilingAddonPercentChance;
		this.flooraddonchance = floorAddonPercentChance;
	}

	public abstract void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth);

	public abstract void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth);
	
	public abstract void generateCeilingAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth);
	
	public abstract void generateFloorAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth);
	
	//public void generateFill(CaveBiomeGenMethods gen, Random random, float depth);
	
	public abstract void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height);
	
	public void generateAdjacentWall(GeneratorMethods gen, Random random, AdjPos pos, float depth, int height){
		
	}
	
	public boolean genAir = false;
	public void generateAir(GeneratorMethods gen, Random random, BlockPos pos, float depth){
		
	}
	

	
	/**
	 * 
	 * @param pos
	 * @param returnSize
	 * @param scale : smaller for larger, less random, more chunky looking areas
	 * @return
	 */
			
	
	protected int getSpelSize(Random random, float depth){
		
		return (int)(Math.sqrt(random.nextInt(9))*(1-depth))+random.nextInt(2)+random.nextInt(2);
	}

}
