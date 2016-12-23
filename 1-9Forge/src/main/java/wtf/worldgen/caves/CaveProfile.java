package wtf.worldgen.caves;

import java.util.ArrayList;
import java.util.Random;

import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.AbstractCaveType;
import wtf.worldgen.AbstractDungeonType;

public class CaveProfile {

	public final AbstractCaveType caveDeep;
	public final AbstractCaveType caveMid;
	public final AbstractCaveType caveShallow;
	
	public ArrayList<AbstractDungeonType> dungeonShallow;
	public ArrayList<AbstractDungeonType> dungeonMid;
	public ArrayList<AbstractDungeonType> dungeonDeep;
	
	public CaveProfile (AbstractCaveType deepType, AbstractCaveType midType, AbstractCaveType shallowType){
		caveDeep = deepType;
		caveMid = midType;
		caveShallow = shallowType;
	}
	
	public AbstractCaveType getCave(CavePosition cave, int surface){

		float height = ((float)cave.floor)/((float)surface);
		if (height < 0.33){
			return caveDeep;
		}
		else if (height < 0.66){
			return 
					caveMid;
		}
		else {
			return caveShallow;
		}

	}
	
	/**
	 * Checks the height to determine what sort of dungeon it should generate, and the dungeon weight to determine if any dungeon should be generated at all based on
	 * the weight given in the cave type.  Returns null if no dungeon should generate. 
	 * 
	 * @param random
	 * @param y
	 * @param surface
	 * @return
	 */
	
	public AbstractDungeonType getDungeon(Random random, int y, int surface){
		float height = ((float)y)/((float)surface);
		if (height < 0.33){
			if (random.nextInt(100) < caveDeep.dungeonPercentChance){
				return dungeonDeep.get(random.nextInt(dungeonDeep.size()));
			}
		}
		else if (height < 0.66){
			if (random.nextInt(100) == caveMid.dungeonPercentChance){
				return dungeonMid.get(random.nextInt(dungeonMid.size()));
			}
		}
		else {
			if (random.nextInt(100) < caveShallow.dungeonPercentChance){
				return dungeonShallow.get(random.nextInt(dungeonShallow.size()));
			}
		}
		return null;
	}

	public AbstractDungeonType getDungeonForCave(CaveBiomeGenMethods gen, Random random, CaveListWrapper cave, int surface) {

		for (int loop = 0; loop < 5; loop++){
		float height = (float)cave.getAvgFloor()/(surface);
		if (height < 0.33){
			int chance = random.nextInt(dungeonDeep.size());
			if (dungeonDeep.get(chance).canGenerateAt(gen, cave)){
				return dungeonDeep.get(chance);
			}
			
		}
		else if (height < 0.66){
			int chance = random.nextInt(dungeonMid.size());
			if (dungeonMid.get(chance).canGenerateAt(gen, cave)){
				return dungeonMid.get(chance);
			}
		}
		else {
			int chance = random.nextInt(dungeonShallow.size());
			if (dungeonShallow.get(chance).canGenerateAt(gen, cave)){
				return dungeonShallow.get(chance);
			}
		}
		}
		return null;
	}
	
}
