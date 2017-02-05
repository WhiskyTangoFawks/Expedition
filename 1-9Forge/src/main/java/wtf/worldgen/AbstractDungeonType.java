package wtf.worldgen;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import wtf.utilities.simplex.SimplexHelper;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public abstract class AbstractDungeonType extends AbstractCaveType{


	
	protected SimplexHelper simplex;
	
	public AbstractDungeonType(String name, int ceilingAddonPercentChance, int floorAddonPercentChance){
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
	}
	
	//core idea- that the dungeon wrapper calls the cave method if it doens't choose to generate something
	//issue- the generators need to be seperate, so nevermind

	public abstract boolean canGenerateAt(CaveBiomeGenMethods gen, CaveListWrapper cave);

	
	protected boolean isHeight(CaveListWrapper cave, int n){
		return cave.getAvgCeiling()-cave.getAvgFloor() >= n;
	}
	

	private final double variable = 5;

	protected BlockPos midpos;
	protected double wallDist;
	protected CaveListWrapper cave = null;
	
	public void setupForGen(CaveListWrapper cave){
		simplex = new SimplexHelper(name+cave.centerpos.toString(), false);
		this.cave=cave;
		midpos = cave.centerpos.getMidPos();
		wallDist = cave.getWallDist(cave.centerpos);
		
	}
	
	protected boolean shouldPosGen(CaveBiomeGenMethods gen, BlockPos pos){
		
		
		double rad = cave.distFromCenter(pos);
		
		if (pos.getX()==midpos.getX() && pos.getZ()==midpos.getZ()){
			//for some reason, the center pos wasn't generating, so I've just stuck this check in
			return true;
		}
		
		double vecx = (midpos.getX()-pos.getX())/rad;
		double vecy = (midpos.getY()-pos.getY())/rad;
	
		double pitchY = Math.acos(vecy);
		double sinY = Math.sin(pitchY);

		
		double n = MathHelper.clamp_double(vecx/sinY, -1, 1);
		double pitchX = Math.acos(n);
		double pitchZ = Math.asin(n);
		 
		double noise = simplex.get3DNoise(gen.chunk.getWorld(), pitchX*2, pitchY*3, pitchZ*2);
		double maxDist = noise*variable + wallDist+1;
		

		
		//System.out.println("noise " + noise*variable);
		
		//y is left out in order to generate a cylinder, rather than a sphere
		int x = pos.getX()-midpos.getX();
		int z = pos.getZ()-midpos.getZ();
		return x*x+z*z < maxDist*maxDist;
	}
	
	/**
	 * called once, at the center of the dungeon, used to generate mob spawners and the like
	 */
	public abstract void generateCenter(CaveBiomeGenMethods gen, Random rand, CavePosition pos, float depth);

	/**
	 * generates fill blocks- used by cave in and frozen dungeons, to replace all air blocks within the dungeon with whatever is supplied.
	 **/
	

	
}
