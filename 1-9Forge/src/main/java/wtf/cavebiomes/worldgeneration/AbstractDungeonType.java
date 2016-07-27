package wtf.cavebiomes.worldgeneration;

import java.util.Random;

import wtf.core.utilities.wrappers.CaveListWrapper;
import wtf.core.utilities.wrappers.CavePosition;

public abstract class AbstractDungeonType extends AbstractCaveType{

	public AbstractDungeonType(String name, int ceilingAddonPercentChance, int floorAddonPercentChance){
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
	}
	
	

	public abstract boolean canGenerateAt(CaveBiomeGenMethods gen, CaveListWrapper cave);
	

	
	/**
	 * called once, at the center of the dungeon, used to generate mob spawners and the like
	 */
	public abstract void generateCenter(CaveBiomeGenMethods gen, Random rand, CavePosition pos, float depth);

	/**
	 * generates fill blocks- used by cave in and frozen dungeons, to replace all air blocks within the dungeon with whatever is supplied.
	 **/

	
}
