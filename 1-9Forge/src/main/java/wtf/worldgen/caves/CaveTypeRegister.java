package wtf.worldgen.caves;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.BlockDirt;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import wtf.worldgen.AbstractCaveType;
import wtf.worldgen.AbstractDungeonType;
import wtf.worldgen.caves.types.CaveTypeDefault;
import wtf.worldgen.caves.types.CaveTypeDirtWater;
import wtf.worldgen.caves.types.CaveTypeFungal;
import wtf.worldgen.caves.types.CaveTypeHell;
import wtf.worldgen.caves.types.CaveTypeIce;
import wtf.worldgen.caves.types.CaveTypeIceRocky;
import wtf.worldgen.caves.types.CaveTypeJungleVolcano;
import wtf.worldgen.caves.types.CaveTypeMossy;
import wtf.worldgen.caves.types.CaveTypeRocky;
import wtf.worldgen.caves.types.CaveTypeSandy;
import wtf.worldgen.caves.types.CaveTypeSwamp;
import wtf.worldgen.caves.types.CaveTypeVolcanic;
import wtf.worldgen.caves.types.CaveTypeWet;
import wtf.worldgen.subcaves.DungeonTypeRegister;


public class CaveTypeRegister {

	public static HashMap<Biome, CaveProfile> cavebiomemap = new HashMap <Biome, CaveProfile>();


	//jacko lantern
	//mineshaft, nether portal, mushroom, cave in,
	//carved stone, carved sandstone
	
	private static int floorChance = 5;
	private static int ceilingChance = 5;

	//shallow caves
	public static final AbstractCaveType simple = new CaveTypeDefault("default", floorChance, ceilingChance);
	
	public static final AbstractCaveType wet =new CaveTypeWet ("wet", floorChance, ceilingChance);
	public static final AbstractCaveType swamp = new CaveTypeSwamp ("swamp", floorChance, ceilingChance);
	public static final AbstractCaveType sandy = new CaveTypeSandy ("sandy", floorChance, ceilingChance, false);
	public static final AbstractCaveType redSandy = new CaveTypeSandy ("sandy", floorChance, ceilingChance, true);
	public static final AbstractCaveType jungleVolcano = new CaveTypeJungleVolcano ("jungleVolcanic", floorChance, ceilingChance+10);
	public static final AbstractCaveType rocky = new CaveTypeRocky ("rocky", floorChance*2, ceilingChance*2);
	public static final AbstractCaveType ice = new CaveTypeIce ("ice", floorChance, ceilingChance);
	public static final AbstractCaveType fungal = new CaveTypeFungal ("fungal", floorChance, ceilingChance);
	public static final AbstractCaveType plains = new CaveTypeDirtWater ("dirtWater", floorChance, ceilingChance);
	public static final AbstractCaveType podzol = new CaveTypeMossy("mossy", floorChance, ceilingChance, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL));
	public static final AbstractCaveType mossy = new CaveTypeMossy("mossy", floorChance, ceilingChance, Blocks.DIRT.getDefaultState());
	public static final AbstractCaveType iceRocky = new CaveTypeIceRocky ("iceRocky", floorChance*2, ceilingChance*2);
	public static final AbstractCaveType volcanic = new CaveTypeVolcanic ("volcanic", floorChance*2, ceilingChance*2);
	public static final AbstractCaveType nether = new CaveTypeHell ("nether", floorChance, ceilingChance);


	public static CaveProfile getCaveProfile(Biome biome){
		//System.out.println("Getting cave profile for " + biome.getBiomeName());
		return cavebiomemap.get(biome) != null ? cavebiomemap.get(biome) : getNewProfile(biome); 
	}


	public static CaveProfile getNewProfile(Biome biome){

		//System.out.println("No profile found, generating new profile for  " + biome.getBiomeName());
		
		ArrayList<AbstractDungeonType> dungeonShallow = new ArrayList<AbstractDungeonType>(DungeonTypeRegister.defaultlist());
		AbstractCaveType shallow = simple;
		
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SNOWY)){
			shallow = ice;
			dungeonShallow.addAll(DungeonTypeRegister.coldList());
		}
		else if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.OCEAN)){
			shallow = wet;
			dungeonShallow.addAll(DungeonTypeRegister.wetList());
		}
		else if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SWAMP)){
			shallow = swamp;
			dungeonShallow.addAll(DungeonTypeRegister.wetList());
			dungeonShallow.addAll(DungeonTypeRegister.forestlist());
		}
		else if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.MESA)){
			shallow = redSandy;
			dungeonShallow.addAll(DungeonTypeRegister.desertList());
		}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.SANDY)){
			shallow = sandy;
			dungeonShallow.addAll(DungeonTypeRegister.desertList());
		}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.JUNGLE)){
			shallow = jungleVolcano;
			dungeonShallow.addAll(DungeonTypeRegister.volcanicList());
		}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.MOUNTAIN)){
			shallow = rocky;
			dungeonShallow.addAll(DungeonTypeRegister.mountainList());
		}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.MOUNTAIN)&&BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.SNOWY)){
			shallow = iceRocky;
			dungeonShallow.addAll(DungeonTypeRegister.coldList());
			dungeonShallow.addAll(DungeonTypeRegister.mountainList());
		}

		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.MUSHROOM)){
			shallow = fungal;
		}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.PLAINS)){
			shallow = plains;
			}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.FOREST)){
			
			if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.CONIFEROUS)){
				shallow =podzol;
				dungeonShallow.addAll(DungeonTypeRegister.forestlist());	
			}
			else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.LUSH)){
				shallow =swamp;
			}
			else if (!BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.JUNGLE) && !BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.SAVANNA)){
				shallow = mossy;
				dungeonShallow.addAll(DungeonTypeRegister.forestlist());
			}
		}

		//MID
		ArrayList<AbstractDungeonType> dungeonMid = new ArrayList<AbstractDungeonType>(DungeonTypeRegister.defaultlist());
		AbstractCaveType mid = rocky;
		if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.OCEAN)){
			mid = wet;
			dungeonMid.addAll(DungeonTypeRegister.wetList());
		}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.SNOWY)){
			mid = iceRocky;
			dungeonMid.addAll(DungeonTypeRegister.coldList());
		}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.SANDY)){
			mid = sandy;
			dungeonMid.addAll(DungeonTypeRegister.desertList());	
		}

		
		//DEEP
		AbstractCaveType deep = volcanic;
		ArrayList<AbstractDungeonType> dungeonDeep = new ArrayList<AbstractDungeonType>(DungeonTypeRegister.netherList());
		if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.SNOWY)){
			deep = iceRocky;
			dungeonShallow.addAll(DungeonTypeRegister.coldList());	
		}
		else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.SANDY)){
			deep = redSandy;
			dungeonShallow.addAll(DungeonTypeRegister.desertList());
		}

		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.NETHER)){
			shallow = nether;
			mid = nether;
			deep = nether;
		}
		
		CaveProfile profile = new CaveProfile(deep, mid, shallow);
		//CaveProfile profile = new CaveProfile(plains, plains, plains);
		profile.dungeonDeep = dungeonDeep;
		profile.dungeonMid = dungeonMid;
		profile.dungeonShallow = dungeonShallow;
		
		cavebiomemap.put(biome, profile);
		return profile;
	}


}
