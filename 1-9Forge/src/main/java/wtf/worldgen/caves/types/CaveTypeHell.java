package wtf.worldgen.caves.types;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import wtf.utilities.wrappers.AdjPos;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.caves.AbstractCaveType;
import wtf.worldgen.caves.types.nether.NetherDeadForest;
import wtf.worldgen.caves.types.nether.NetherFireForest;
import wtf.worldgen.caves.types.nether.NetherFrozen;
import wtf.worldgen.caves.types.nether.NetherMushroom;
import wtf.worldgen.caves.types.nether.NetherNormal;
import wtf.worldgen.caves.types.nether.NetherSoulDesert;

public class CaveTypeHell extends AbstractCaveType{

	public CaveTypeHell(String name, int ceilingAddonPercentChance, int floorAddonPercentChance) {
		super(name, 100, 100);
	}

	@Override
	public void generateCeiling(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		getSubType(pos).cavetype.generateCeiling(gen, random, pos, depth);
	}

	@Override
	public void generateFloor(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		getSubType(pos).cavetype.generateFloor(gen, random, pos, depth);	
	}

	@Override
	public void generateCeilingAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		hellBiome type = getSubType(pos);
		if (random.nextInt(100) < type.cavetype.ceilingaddonchance){
			type.cavetype.generateCeilingAddons(gen, random, pos, depth);
		}
		
	}

	@Override
	public void generateFloorAddons(GeneratorMethods gen, Random random, BlockPos pos, float depth) {
		hellBiome type = getSubType(pos);
		if (random.nextInt(100) < type.cavetype.flooraddonchance){
			type.cavetype.generateFloorAddons(gen, random, pos, depth);
		}
	}

	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {
		getSubType(pos).cavetype.generateWall(gen, random, pos, depth, height);
		
	}
	
	@Override
	public void generateAdjacentWall(GeneratorMethods gen, Random random, AdjPos pos, float depth, int height){
		getSubType(pos).cavetype.generateAdjacentWall(gen, random, pos, depth, height);
	}
	
	public enum hellBiome{
		NORMAL(new NetherNormal()), 
		SOULDESERT(new NetherSoulDesert()), 
		MUSHROOM(new NetherMushroom()), 
		DEADFOREST(new NetherDeadForest()), 
		FIREFOREST(new NetherFireForest()), 
		FROZEN(new NetherFrozen());
		
		
		private final AbstractCaveType cavetype;
		
		hellBiome(AbstractCaveType type){
			this.cavetype = type;
		}
	};


	public hellBiome getSubType(BlockPos pos){
		
		return hellBiome.NORMAL;
		
		/*
		int temp = (int)getNoise(pos, 10, 0.05F);
		switch (temp){
		case 0 :
			return hellBiome.FROZEN;
		case 1 :
			return hellBiome.FROZEN;
		case 2 :
			return hellBiome.MUSHROOM;
		case 3 :
			return hellBiome.DEADFOREST;
		case 4 :
			return hellBiome.NORMAL;
		case 5 :
			return hellBiome.NORMAL;
		case 6 :
			return hellBiome.FIREFOREST;
		case 7 :
			return hellBiome.NORMAL;
		case 8 :
			return hellBiome.NORMAL;
		case 9 :
			return hellBiome.SOULDESERT;
			
		
		}
		
		return null;
		*/
	}
	
	//MOVE LOCAL TYPE into a seperate routine, with it's own variable to be called, rather than recalculating it constantly
	//- This won't work, because the wall positions iterate seperately
	//And given the great distance between floor and ceiling, if I want to do localities in 3 dimensions, I need division within the position anyway
	
	//Glowstone stalactites
	
	//New Blocks - ashstone, Dragonglass, mycellium infested netherrack (maybe put netherrack overlay on mycellium?
	//ore: liquid ores, 
	//Trees: bone trees, fire trees, New Block: netherrwood : some sort of netherrack overlay on a wooden type texture
	//upside down trees
	//lava vines
	//giant mushrooms (poplar variants)
	
	//Netherrack replacer ? 
	//Lava replacer: needs to swap out in cold variants
	
	//Cold lava variants : lava replaced with Tar, ice trees with some sort of glowing center
	//Ice overlay over glowstone

	//desert variants- soul sand, mixed with something else to make the area traversable, bone trees
	
	//Mushroom- infested netherrack, big mushrooms
	
	//full size columns of stone, obsidian, and maybe other stuff
	
	//Dragon glass- black glass with some sort of property, maybe overlaid on stone or obsidian
	//Ashstone- stone with a speckled black overlay
	
	//Stone cactus?  Some sort of modified cactus in deserts
	//Fire-grass- grass that looks like fire, but doesn't burn you
	
}
