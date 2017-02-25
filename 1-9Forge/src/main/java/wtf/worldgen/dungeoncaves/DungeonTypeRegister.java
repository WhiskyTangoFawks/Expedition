package wtf.worldgen.dungeoncaves;

import java.util.ArrayList;

import net.minecraft.block.BlockSandStone;
import net.minecraft.init.Blocks;
import wtf.Core;
import wtf.config.CaveBiomesConfig;
import wtf.init.BlockSets.Modifier;
import wtf.worldgen.dungeoncaves.ambient.DungeonJungleTemple;
import wtf.worldgen.dungeoncaves.ambient.DungeonSpeleothemGrotto;
import wtf.worldgen.dungeoncaves.ambient.DungeonTypeBatCave;
import wtf.worldgen.dungeoncaves.ambient.DungeonTypeCaveIn;
import wtf.worldgen.dungeoncaves.ambient.DungeonTypeFoxfire;
import wtf.worldgen.dungeoncaves.ambient.DungeonTypeFrozenSolid;
import wtf.worldgen.dungeoncaves.ambient.DungeonTypePrismarine;
import wtf.worldgen.dungeoncaves.ambient.DungeonTypeRain;
import wtf.worldgen.dungeoncaves.ambient.DungeonTypeSoulsand;
import wtf.worldgen.dungeoncaves.mob.DungeonBlaze;
import wtf.worldgen.dungeoncaves.mob.DungeonBogLantern;
import wtf.worldgen.dungeoncaves.mob.DungeonFireElemental;
import wtf.worldgen.dungeoncaves.mob.DungeonMine;
import wtf.worldgen.dungeoncaves.mob.DungeonSimpleHusk;
import wtf.worldgen.dungeoncaves.mob.DungeonSimpleMagma;
import wtf.worldgen.dungeoncaves.mob.DungeonSimpleSkeleton;
import wtf.worldgen.dungeoncaves.mob.DungeonSimpleSkeletonKnight;
import wtf.worldgen.dungeoncaves.mob.DungeonSimpleSpider;
import wtf.worldgen.dungeoncaves.mob.DungeonSimpleStray;
import wtf.worldgen.dungeoncaves.mob.DungeonSimpleZombie;
import wtf.worldgen.dungeoncaves.mob.DungeonSlime;
import wtf.worldgen.dungeoncaves.mob.DungeonTypeDerangedGolem;
import wtf.worldgen.dungeoncaves.mob.DungeonTypeMummy;
import wtf.worldgen.dungeoncaves.mob.DungeonTypeNetherPortal;
import wtf.worldgen.dungeoncaves.mob.DungeonTypeSkeletonMage;

public class DungeonTypeRegister {
	
	private static AbstractDungeonType Skeleton = new DungeonSimpleSkeleton("SkeletonClassic");
	private static AbstractDungeonType Zombie = new DungeonSimpleZombie("ZombieClassic");
	private static AbstractDungeonType MagmaSlime = new DungeonSimpleMagma("MagmaSlime");
	private static AbstractDungeonType Blaze = new DungeonBlaze("Blaze");
	private static AbstractDungeonType Stray = new DungeonSimpleStray("Stray");
	private static AbstractDungeonType Husk = new DungeonSimpleHusk("Husk");
	
	private static AbstractDungeonType SkeletonKnight = new DungeonSimpleSkeletonKnight("SkeletonKnight");
	private static AbstractDungeonType Spider = new DungeonSimpleSpider("ClassicSpider");
	private static AbstractDungeonType Slime = new DungeonSlime("Slime");
	public static AbstractDungeonType Mummy = new DungeonTypeMummy("Mummy");
	private static AbstractDungeonType Golem = new DungeonTypeDerangedGolem("DerangedGolem");
	public static AbstractDungeonType Bat = new DungeonTypeBatCave("BatCave", 10, 0);
	
	public static AbstractDungeonType CaveIn = new DungeonTypeCaveIn("Cavein");
	public static AbstractDungeonType Grotto = new DungeonSpeleothemGrotto("SpeleothemGrotto", 50, 50);
	public static AbstractDungeonType Mine = new DungeonMine("Mine");
	public static AbstractDungeonType BogLantern = new DungeonBogLantern("BogLangern");
	public static AbstractDungeonType ZombieFarm = new DungeonZombieFarmer("ZombieFarm");

	private static AbstractDungeonType Foxfire = new DungeonTypeFoxfire("Foxfire", 10, 10);
	private static AbstractDungeonType Frozen = new DungeonTypeFrozenSolid("FrozenSolid");
	private static AbstractDungeonType Rainstone = new DungeonTypeRain("Rain", 5, 5);
	private static AbstractDungeonType JungleTemple = new DungeonJungleTemple("JungleTemple");
	

	private static AbstractDungeonType Soulsand = new DungeonTypeSoulsand("SoulSand", 5, 5);
	
	private static AbstractDungeonType SkeletonMage = new DungeonTypeSkeletonMage();
	private static AbstractDungeonType Prismarine = new DungeonTypePrismarine("Prismarine", 5, 5);

	private static AbstractDungeonType NetherPortal = new DungeonTypeNetherPortal("NetherPortal");
	private static AbstractDungeonType FireElemental = new DungeonFireElemental("FireElemental");
	
	public static ArrayList<AbstractDungeonType> defaultList(){
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
	
	public static ArrayList<AbstractDungeonType> forestList(){
		ArrayList<AbstractDungeonType> list = new ArrayList<AbstractDungeonType>();
		if (CaveBiomesConfig.enableAmbientDungeons){
			
			list.add(Foxfire);
		}
		if (CaveBiomesConfig.enableMobDungeons){
			list.add(Spider);
			list.add(ZombieFarm);
		}
		return list;
	}
	
	public static ArrayList<AbstractDungeonType> lushList(){
		ArrayList<AbstractDungeonType> list = new ArrayList<AbstractDungeonType>();
		if (CaveBiomesConfig.enableAmbientDungeons){
			
			list.add(Foxfire);
			list.add(JungleTemple);
			list.add(Rainstone);
		}
		if (CaveBiomesConfig.enableMobDungeons){
			list.add(Spider);
			
		}
		return list;
	}
	
	public static ArrayList<AbstractDungeonType> coldList(){
		ArrayList<AbstractDungeonType> list = new ArrayList<AbstractDungeonType>();
		if (CaveBiomesConfig.enableAmbientDungeons){
			list.add(Frozen);
		}
		if (CaveBiomesConfig.enableMobDungeons){
			list.add(Stray);
		}
		return list;
	}
	
	public static ArrayList<AbstractDungeonType> wetList(){
		ArrayList<AbstractDungeonType> list = new ArrayList<AbstractDungeonType>();
		if (CaveBiomesConfig.enableAmbientDungeons){
			list.add(Rainstone);
			list.add(Prismarine);
			list.add(BogLantern);
		}
		if (CaveBiomesConfig.enableMobDungeons){
			list.add(Slime);
		}
		return list;
	}
	
	public static ArrayList<AbstractDungeonType> volcanicList(){
		ArrayList<AbstractDungeonType> list = new ArrayList<AbstractDungeonType>();
		if (CaveBiomesConfig.enableAmbientDungeons){
			list.add(JungleTemple);
		}
		if (CaveBiomesConfig.enableMobDungeons){
			list.add(MagmaSlime);
			list.add(FireElemental);
		}
		return list;
	}
	
	public static ArrayList<AbstractDungeonType> desertList(){
		ArrayList<AbstractDungeonType> list = new ArrayList<AbstractDungeonType>();
		if (CaveBiomesConfig.enableAmbientDungeons){
			list.add(Soulsand);
		}
		if (CaveBiomesConfig.enableMobDungeons){
			list.add(Husk);
			list.add(Mummy);
		}
		return list;
	}
	
	public static ArrayList<AbstractDungeonType> netherList(){
		ArrayList<AbstractDungeonType> list = new ArrayList<AbstractDungeonType>();
		if (CaveBiomesConfig.enableAmbientDungeons){
			list.add(Soulsand);
		}
		if (CaveBiomesConfig.enableMobDungeons){
			list.add(NetherPortal);
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
			list.add(Mine);			
		}
		return list;
	}
	
}
