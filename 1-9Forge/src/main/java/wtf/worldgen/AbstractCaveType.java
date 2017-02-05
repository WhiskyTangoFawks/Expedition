package wtf.worldgen;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.utilities.simplex.SimplexHelper;
import wtf.utilities.wrappers.AdjPos;
import wtf.utilities.wrappers.CavePosition;
import wtf.utilities.wrappers.SurfacePos;
import wtf.worldgen.caves.CaveBiomeGenMethods;

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

	public abstract void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth);

	public abstract void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth);
	
	public abstract void generateCeilingAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth);
	
	public abstract void generateFloorAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth);
	
	//public void generateFill(CaveBiomeGenMethods gen, Random random, float depth);
	
	public abstract void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height);
	
	public void generateAdjacentWall(CaveBiomeGenMethods gen, Random random, AdjPos pos, float depth, int height){
		
	}
	
	protected boolean genAir = false;
	public void generateAir(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth){
		
	}
	

	
	/**
	 * 
	 * @param pos
	 * @param returnSize
	 * @param scale : smaller for larger, less random, more chunky looking areas
	 * @return
	 */
			
	protected final double getNoise(World world, BlockPos pos, double returnSize, float scale){
		double x = (double)pos.getX() * scale;
		double y = (double)pos.getY() * scale;
		double z = (double)pos.getZ() * scale;
		//System.out.println(" simplex " + x + " " + y + " " +z);
		double noise = simplex.get3DNoise(world, x, y, z)*returnSize; 
		return noise;
	}
	
	public void setTopBlock(CaveBiomeGenMethods gen, Random random, SurfacePos pos){
		
	}

}
