package wtf.worldgen.caves;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import wtf.worldgen.caves.types.CaveTypeDefault;
import wtf.worldgen.caves.types.CaveTypeDirtWater;
import wtf.worldgen.caves.types.CaveTypeFungal;
import wtf.worldgen.caves.types.CaveTypeHell;
import wtf.worldgen.caves.types.CaveTypeIce;
import wtf.worldgen.caves.types.CaveTypeIceRocky;
import wtf.worldgen.caves.types.CaveTypeJungleVolcano;
import wtf.worldgen.caves.types.CaveTypeLush;
import wtf.worldgen.caves.types.CaveTypeMossy;
import wtf.worldgen.caves.types.CaveTypeMossyRocky;
import wtf.worldgen.caves.types.CaveTypePaintedDesert;
import wtf.worldgen.caves.types.CaveTypePodzol;
import wtf.worldgen.caves.types.CaveTypeRocky;
import wtf.worldgen.caves.types.CaveTypeRockySandy;
import wtf.worldgen.caves.types.CaveTypeSandy;
import wtf.worldgen.caves.types.CaveTypeSandyVolcanic;
import wtf.worldgen.caves.types.CaveTypeSwamp;
import wtf.worldgen.caves.types.CaveTypeVolcanic;
import wtf.worldgen.caves.types.CaveTypeWet;
import wtf.worldgen.dungeoncaves.AbstractDungeonType;
import wtf.worldgen.dungeoncaves.DungeonTypeRegister;


public class CaveTypeRegister {

	public static HashMap<Biome, CaveProfile> cavebiomemap = new HashMap <Biome, CaveProfile>();


	//jacko lantern
	//mineshaft, nether portal, mushroom, cave in,
	//carved stone, carved sandstone

	private static int floorChance = 2;
	private static int ceilingChance = 3;

	//shallow caves
	public static final AbstractCaveType simple = new CaveTypeDefault("default", floorChance, ceilingChance);

	public static final AbstractCaveType wet =new CaveTypeWet ("wet", floorChance, ceilingChance+1);
	
	public static final AbstractCaveType swamp = new CaveTypeSwamp ("swamp", floorChance, ceilingChance+2);
	
	public static final AbstractCaveType sandy = new CaveTypeSandy ("sandy", floorChance, ceilingChance, false);
	public static final AbstractCaveType redSandy = new CaveTypeSandy ("redSand", floorChance, ceilingChance, true);
	public static final AbstractCaveType sandyRocky = new CaveTypeRockySandy ("sandRocky", floorChance, ceilingChance+3, false);
	public static final AbstractCaveType redSandyRocky = new CaveTypeRockySandy ("redsandRocky", floorChance, ceilingChance+3, true);
	public static final AbstractCaveType paintedDesert = new CaveTypePaintedDesert("paintedDesert", 0, ceilingChance);
	
	public static final AbstractCaveType lush = new CaveTypeLush ("lush", floorChance+3, ceilingChance+12);
	public static final AbstractCaveType lushVolcanic = new CaveTypeJungleVolcano ("jungleVolcano", floorChance+3, ceilingChance+12);
	
	public static final AbstractCaveType rocky = new CaveTypeRocky ("rocky", floorChance, ceilingChance+3);
	
	public static final AbstractCaveType ice = new CaveTypeIce ("ice", floorChance, ceilingChance+3);
	public static final AbstractCaveType iceRocky = new CaveTypeIceRocky ("iceRocky", floorChance, ceilingChance+3);
	
	public static final AbstractCaveType fungal = new CaveTypeFungal ("fungal", floorChance, ceilingChance);
	
	public static final AbstractCaveType plains = new CaveTypeDirtWater ("dirtWater", floorChance, ceilingChance);
	
	public static final AbstractCaveType podzol = new CaveTypePodzol("podzol", floorChance, ceilingChance);
	public static final AbstractCaveType mossy = new CaveTypeMossy("mossy", floorChance, ceilingChance);
	public static final AbstractCaveType mossyRocky = new CaveTypeMossyRocky("mossyRocky", floorChance, ceilingChance);
	
	public static final AbstractCaveType volcanic = new CaveTypeVolcanic ("volcanic", floorChance, ceilingChance+4);
	public static final AbstractCaveType sandyVolcanic = new CaveTypeSandyVolcanic("redSandyVolcanic", floorChance, ceilingChance+4);
	
	public static final CaveTypeHell nether = new CaveTypeHell ("nether", floorChance, ceilingChance);


	public static CaveProfile getCaveProfile(Biome biome){
		//System.out.println("Getting cave profile for " + biome.getBiomeName());
		return cavebiomemap.get(biome) != null ? cavebiomemap.get(biome) : getNewProfile(biome); 
	}


	public static CaveProfile getNewProfile(Biome biome){

		//System.out.println("No profile found, generating new profile for  " + biome.getBiomeName());

		ArrayList<AbstractDungeonType> dungeonShallow = new ArrayList<AbstractDungeonType>(DungeonTypeRegister.defaultList());
		AbstractCaveType shallow = simple;
		
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.MESA) || BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SAVANNA)){
			shallow = paintedDesert;
			dungeonShallow.addAll(DungeonTypeRegister.desertList());
		}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.MOUNTAIN) || BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.HILLS)) {

			if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.SNOWY)){
				shallow = iceRocky;
				dungeonShallow.addAll(DungeonTypeRegister.coldList());		
			}
			else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.SAVANNA)){
				shallow = redSandyRocky;
				dungeonShallow.addAll(DungeonTypeRegister.desertList());
			}
			else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.SANDY)){
				shallow = sandyRocky;
				dungeonShallow.addAll(DungeonTypeRegister.desertList());
			}
			else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.JUNGLE)){
				shallow = lushVolcanic;
				dungeonShallow.addAll(DungeonTypeRegister.lushList());
			}
			else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.FOREST)){
				shallow = mossyRocky;
				dungeonShallow.addAll(DungeonTypeRegister.forestList());
			}
			else {
				shallow = rocky;
			}
			dungeonShallow.addAll(DungeonTypeRegister.mountainList());
		}
		else if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SNOWY)){
			shallow = ice;
			dungeonShallow.addAll(DungeonTypeRegister.coldList());
		}
		else if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.OCEAN)|| BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.RIVER)){
			shallow = wet;
			dungeonShallow.addAll(DungeonTypeRegister.wetList());
		}
		else if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SWAMP)){
			shallow = swamp;
			dungeonShallow.addAll(DungeonTypeRegister.wetList());
			dungeonShallow.addAll(DungeonTypeRegister.forestList());
		}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.SANDY)){
			shallow = sandy;
			dungeonShallow.addAll(DungeonTypeRegister.desertList());
		}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.JUNGLE)){
			shallow = lush;
			dungeonShallow.addAll(DungeonTypeRegister.lushList());
		}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.MUSHROOM)){
			shallow = fungal;
		}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.PLAINS)){
			shallow = plains;
			dungeonShallow.addAll(DungeonTypeRegister.wetList());
		}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.CONIFEROUS)){
			shallow =podzol;
			dungeonShallow.addAll(DungeonTypeRegister.forestList());
			dungeonShallow.addAll(DungeonTypeRegister.coldList());
		}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.FOREST)){
			if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.LUSH)){
				shallow =swamp;
				dungeonShallow.addAll(DungeonTypeRegister.forestList());
				dungeonShallow.addAll(DungeonTypeRegister.wetList());
			}
			else{
				shallow = mossy;
			}
			dungeonShallow.addAll(DungeonTypeRegister.forestList());
		}

		//MID
		ArrayList<AbstractDungeonType> dungeonMid = new ArrayList<AbstractDungeonType>(DungeonTypeRegister.defaultList());
		AbstractCaveType mid = rocky;
		
		if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.SNOWY)){
			mid = iceRocky;
			dungeonMid.addAll(DungeonTypeRegister.coldList());
		}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.OCEAN) || BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.RIVER)){
			mid = wet;
			dungeonMid.addAll(DungeonTypeRegister.wetList());
		}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.SAVANNA) || BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.MESA)){
			mid = redSandyRocky;
			dungeonMid.addAll(DungeonTypeRegister.desertList());	
		}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.SANDY)){
			mid = sandyRocky;
			dungeonMid.addAll(DungeonTypeRegister.desertList());	
		}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.JUNGLE)){
			mid = lushVolcanic;
			dungeonMid.addAll(DungeonTypeRegister.volcanicList());
		}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.FOREST)){
			mid = mossyRocky;
			dungeonMid.addAll(DungeonTypeRegister.forestList());
		}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.WET)){
			mid = wet;
			dungeonMid.addAll(DungeonTypeRegister.wetList());
		}

		//DEEP
		AbstractCaveType deep = volcanic;
		ArrayList<AbstractDungeonType> dungeonDeep = new ArrayList<AbstractDungeonType>(DungeonTypeRegister.netherList());
		if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.SNOWY)){
			deep = iceRocky;
			dungeonShallow.addAll(DungeonTypeRegister.coldList());	
		}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.SANDY) || BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.SAVANNA)){
			deep = sandyVolcanic;
			dungeonShallow.addAll(DungeonTypeRegister.desertList());
		}

		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.NETHER)){
			shallow = nether;
			mid = nether;
			deep = nether;
		}

		CaveProfile profile = new CaveProfile(deep, mid, shallow);
		System.out.println("Setting up cave biomes for " + biome.getBiomeName() + " SHALLOW: " + shallow.name + " MID: " + mid.name + " DEEP: " + deep.name);
		profile.dungeonDeep = dungeonDeep;
		profile.dungeonMid = dungeonMid;
		profile.dungeonShallow = dungeonShallow;

		cavebiomemap.put(biome, profile);
		return profile;
	}


}
