package wtf.worldgen.dungeoncaves.mob;

import java.util.Random;

import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.GeneratorMethods;

public class DungeonSimpleStray extends DungeonSimpleSkeleton{

	public DungeonSimpleStray(String name) {
		super(name);
	}
	
	@Override
	public void generateCenter(GeneratorMethods gen, Random rand, CavePosition pos, float depth) {
		gen.spawnVanillaSpawner(pos.getFloorPos().up(), "wtfcore.Stray", 5);	
	}

}
