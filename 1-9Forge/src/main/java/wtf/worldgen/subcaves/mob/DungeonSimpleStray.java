package wtf.worldgen.subcaves.mob;

import java.util.Random;

import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class DungeonSimpleStray extends DungeonSimpleSkeleton{

	public DungeonSimpleStray(String name) {
		super(name);
	}
	
	@Override
	public void generateCenter(CaveBiomeGenMethods gen, Random rand, CavePosition pos, float depth) {
		gen.spawnVanillaSpawner(pos.getFloorPos().up(), "wtfcore.Stray", 5);	
	}

}
