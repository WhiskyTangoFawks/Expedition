package wtf.worldgen;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import wtf.core.utilities.Simplex;
import wtf.core.utilities.wrappers.AdjPos;
import wtf.core.utilities.wrappers.ChunkCoords;
import wtf.core.utilities.wrappers.ChunkScan;
import wtf.core.utilities.wrappers.SurfacePos;
import wtf.worldgen.trees.TreeTypeGetter;
import wtf.worldgen.trees.TreeVars;

public abstract class AbstractCaveType
{

	public int dungeonPercentChance = 5;
	final public String name;
	private static Simplex simplex = new Simplex(5000);
	
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
	
	/**
	 * 
	 * @param pos
	 * @param returnSize
	 * @param scale : smaller for larger, less random, more chunky looking areas
	 * @return
	 */
			
	protected final double getNoise(BlockPos pos, double returnSize, float scale){
		double x = pos.getX() * scale;
		double y = pos.getY() * scale;
		double z = pos.getZ() * scale;
		//System.out.println(" simplex " + x + " " + y + " " +z);
		returnSize /=2;
		double noise = simplex.noise(x, y, z)*returnSize + returnSize; 
		return noise;
	}
	
	public void setTopBlock(CaveBiomeGenMethods gen, Random random, SurfacePos pos){
		
	}
	
	public TreeVars getTreeType(World world, ChunkScan scan, WorldGenAbstractTree oldTree){
		return TreeTypeGetter.getTree(world, oldTree);
	}

}
