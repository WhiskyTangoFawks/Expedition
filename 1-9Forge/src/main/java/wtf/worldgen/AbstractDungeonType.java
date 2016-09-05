package wtf.worldgen;

import java.util.Random;

import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;

public abstract class AbstractDungeonType extends AbstractCaveType{

	public final boolean genOver;
	
	public AbstractDungeonType(String name, int ceilingAddonPercentChance, int floorAddonPercentChance, Boolean genOver){
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		this.genOver = genOver;
	}
	
	

	public abstract boolean canGenerateAt(CaveBiomeGenMethods gen, CaveListWrapper cave);
	
	protected boolean isSize(CaveListWrapper cave, int n){
		return cave.getSizeX() > n && cave.getSizeZ() > n && cave.cave.size() > n*(n-1);
	}
	
	protected boolean isHeight(CaveListWrapper cave, int n){
		return cave.getAvgCeiling()-cave.getAvgFloor() > n;
	}
	
	
	/**
	 * called once, at the center of the dungeon, used to generate mob spawners and the like
	 */
	public abstract void generateCenter(CaveBiomeGenMethods gen, Random rand, CavePosition pos, float depth);

	/**
	 * generates fill blocks- used by cave in and frozen dungeons, to replace all air blocks within the dungeon with whatever is supplied.
	 **/

	
}
