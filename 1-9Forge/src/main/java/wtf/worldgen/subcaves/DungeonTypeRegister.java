package wtf.worldgen.subcaves;

import java.util.ArrayList;

import net.minecraft.block.BlockSandStone;
import net.minecraft.init.Blocks;
import wtf.config.CaveBiomesConfig;
import wtf.core.Core;
import wtf.init.BlockSets.Modifier;
import wtf.worldgen.AbstractDungeonType;
import wtf.worldgen.subcaves.ambient.DungeonSpeleothemGrotto;
import wtf.worldgen.subcaves.ambient.DungeonTypeBatCave;
import wtf.worldgen.subcaves.ambient.DungeonTypeCaveIn;
import wtf.worldgen.subcaves.ambient.DungeonTypeFoxfire;
import wtf.worldgen.subcaves.ambient.DungeonTypeFrozenSolid;
import wtf.worldgen.subcaves.ambient.DungeonTypeNetherPortal;
import wtf.worldgen.subcaves.ambient.DungeonTypePrismarine;
import wtf.worldgen.subcaves.ambient.DungeonTypeRain;
import wtf.worldgen.subcaves.ambient.DungeonTypeSoulsand;
import wtf.worldgen.subcaves.mob.DungeonClassicMob;
import wtf.worldgen.subcaves.mob.DungeonClassicSpider;
import wtf.worldgen.subcaves.mob.DungeonSlime;
import wtf.worldgen.subcaves.mob.DungeonTypeDerangedGolem;
import wtf.worldgen.subcaves.mob.DungeonTypePharohTomb;
import wtf.worldgen.subcaves.mob.DungeonTypeSkeletonMage;

public class DungeonTypeRegister {
	
	private static AbstractDungeonType Skeleton = new DungeonClassicMob("SkeletonClassic", Blocks.MOSSY_COBBLESTONE.getDefaultState(), "Skeleton");
	private static AbstractDungeonType Zombie = new DungeonClassicMob("ZombieClassic", Blocks.MOSSY_COBBLESTONE.getDefaultState(), "Zombie");
	private static AbstractDungeonType MagmaSlime = new DungeonClassicMob("MagmaSlime", Modifier.LAVA_CRUST, "LavaSlime");
	private static AbstractDungeonType Blaze = new DungeonClassicMob("Blaze", Blocks.NETHER_BRICK.getDefaultState(), "Blaze").setSpawnRate(2);
	private static AbstractDungeonType Pigman = new DungeonClassicMob("ZombiePigman", Blocks.NETHERRACK.getDefaultState(), "PigZombie").setSpawnRate(6);
	private static AbstractDungeonType Mummy = new DungeonClassicMob("Mummy", Blocks.SANDSTONE.getDefaultState(), Core.coreID+".ZombieMummy").setStripe(Blocks.SANDSTONE.getDefaultState().withProperty(BlockSandStone.TYPE, BlockSandStone.EnumType.CHISELED));	
	private static AbstractDungeonType SkeletonKnight = new DungeonClassicMob("SkeletonKnight", Blocks.STONEBRICK.getDefaultState(), "Skeleton");
	private static AbstractDungeonType Spider = new DungeonClassicSpider("ClassicSpider");
	private static AbstractDungeonType Slime = new DungeonSlime("Slime");
	public static AbstractDungeonType Pharaoh = new DungeonTypePharohTomb("PharohsTomb");
	private static AbstractDungeonType Golem = new DungeonTypeDerangedGolem("DerangedGolem");
	public static AbstractDungeonType Bat = new DungeonTypeBatCave("BatCave", 10, 0);
	
	public static AbstractDungeonType CaveIn = new DungeonTypeCaveIn("Cavein");
	public static AbstractDungeonType Grotto = new DungeonSpeleothemGrotto("SpeleothemGrotto", 50, 50);

	private static AbstractDungeonType Foxfire = new DungeonTypeFoxfire("Foxfire", 10, 10);
	private static AbstractDungeonType Frozen = new DungeonTypeFrozenSolid("FrozenSolid");
	private static AbstractDungeonType Rainstone = new DungeonTypeRain("Rain", 5, 5);
	

	private static AbstractDungeonType Soulsand = new DungeonTypeSoulsand("SoulSand", 5, 5);
	
	private static AbstractDungeonType SkeletonMage = new DungeonTypeSkeletonMage();
	private static AbstractDungeonType Prismarine = new DungeonTypePrismarine("Prismarine", 5, 5);

	private static AbstractDungeonType NetherPortal = new DungeonTypeNetherPortal("NetherPortal");
	//
	public static ArrayList<AbstractDungeonType> defaultlist(){
		ArrayList<AbstractDungeonType> list = new ArrayList<AbstractDungeonType>();
		if (CaveBiomesConfig.enableAmbientDungeons){
			list.add(CaveIn);
			list.add(Grotto);
			list.add(Bat);
		}
		if (CaveBiomesConfig.enableMobDungeons){
			list.add(Skeleton);
			list.add(Zombie);
		}
		return list;
	}
	
	public static ArrayList<AbstractDungeonType> forestlist(){
		ArrayList<AbstractDungeonType> list = new ArrayList<AbstractDungeonType>();
		if (CaveBiomesConfig.enableAmbientDungeons){
			
			list.add(Foxfire);
		}
		if (CaveBiomesConfig.enableMobDungeons){
			list.add(Spider);
			//list.add(Witch);
		}
		return list;
	}
	
	public static ArrayList<AbstractDungeonType> coldList(){
		ArrayList<AbstractDungeonType> list = new ArrayList<AbstractDungeonType>();
		if (CaveBiomesConfig.enableAmbientDungeons){
			list.add(Frozen);
		}
		if (CaveBiomesConfig.enableMobDungeons){
			//none available currently
		}
		return list;
	}
	
	public static ArrayList<AbstractDungeonType> wetList(){
		ArrayList<AbstractDungeonType> list = new ArrayList<AbstractDungeonType>();
		if (CaveBiomesConfig.enableAmbientDungeons){
			list.add(Rainstone);
			list.add(Prismarine);
		}
		if (CaveBiomesConfig.enableMobDungeons){
			list.add(Slime);
		}
		return list;
	}
	
	public static ArrayList<AbstractDungeonType> volcanicList(){
		ArrayList<AbstractDungeonType> list = new ArrayList<AbstractDungeonType>();
		if (CaveBiomesConfig.enableAmbientDungeons){
			
		}
		if (CaveBiomesConfig.enableMobDungeons){
			list.add(MagmaSlime);
		}
		return list;
	}
	
	public static ArrayList<AbstractDungeonType> desertList(){
		ArrayList<AbstractDungeonType> list = new ArrayList<AbstractDungeonType>();
		if (CaveBiomesConfig.enableAmbientDungeons){
			list.add(Soulsand);
		}
		if (CaveBiomesConfig.enableMobDungeons){
			list.add(Mummy);
			list.add(Pharaoh);
		}
		return list;
	}
	
	public static ArrayList<AbstractDungeonType> netherList(){
		ArrayList<AbstractDungeonType> list = new ArrayList<AbstractDungeonType>();
		if (CaveBiomesConfig.enableAmbientDungeons){
			list.add(NetherPortal);
			list.add(Soulsand);
		}
		if (CaveBiomesConfig.enableMobDungeons){
			list.add(Pigman);
			list.add(Blaze);
			list.add(SkeletonKnight);
			list.add(SkeletonMage);
		}
		return list;
	}
	
	public static ArrayList<AbstractDungeonType> mountainList(){
		ArrayList<AbstractDungeonType> list = new ArrayList<AbstractDungeonType>();
		if (CaveBiomesConfig.enableAmbientDungeons){

		}
		if (CaveBiomesConfig.enableMobDungeons){
			list.add(Golem);
		}
		return list;
	}
	
}
