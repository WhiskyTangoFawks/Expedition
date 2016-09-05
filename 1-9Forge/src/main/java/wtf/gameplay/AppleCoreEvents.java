package wtf.gameplay;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import squeek.applecore.api.plants.PlantGrowthEvent;
import wtf.config.GameplayConfig;

public class AppleCoreEvents {

	//Task: Biome specific growth modifiers
	
	public static final int beetroot = Blocks.BEETROOTS.hashCode();
	public static final int cactus = Blocks.CACTUS.hashCode();
	public static final int carrot = Blocks.CARROTS.hashCode();
	public static final int cocoa = Blocks.COCOA.hashCode();
	public static final int melon = Blocks.MELON_STEM.hashCode();
	public static final int mushroomBrown = Blocks.BROWN_MUSHROOM.hashCode();
	public static final int mushroomRed = Blocks.RED_MUSHROOM.hashCode();
	public static final int netherWart = Blocks.NETHER_WART.hashCode();
	public static final int potato = Blocks.POTATOES.hashCode();
	public static final int pumpkin = Blocks.PUMPKIN_STEM.hashCode();
	public static final int reed = Blocks.REEDS.hashCode();
	public static final int wheat = Blocks.WHEAT.hashCode();
	
	@SubscribeEvent
	public static void GrowthTick(PlantGrowthEvent.AllowGrowthTick event){

		int hash = event.block.hashCode();
		int growthPercent = GameplayConfig.appleCoreConstant;
		Biome biome = event.world.getBiomeForCoordsBody(event.pos);
		
		if (hash == beetroot){
			if (BiomeDictionary.isBiomeOfType(biome, Type.COLD)){
				growthPercent*=2;
			}
		}
		else if (hash == cactus){
			if (BiomeDictionary.isBiomeOfType(biome, Type.HOT) && BiomeDictionary.isBiomeOfType(biome, Type.DRY)){
				growthPercent*=2;
			}
		}
		else if (hash == carrot){
			if (BiomeDictionary.isBiomeOfType(biome, Type.HILLS)){
				growthPercent*=2;
			}
		}
		else if (hash == cocoa){
			if (BiomeDictionary.isBiomeOfType(biome, Type.JUNGLE)){
				growthPercent*=2;
			}
		}
		else if (hash == melon){
			if (BiomeDictionary.isBiomeOfType(biome, Type.HOT) && BiomeDictionary.isBiomeOfType(biome, Type.WET)){
				growthPercent*=2;
			}
		}
		else if (hash == mushroomBrown){
			if (BiomeDictionary.isBiomeOfType(biome, Type.SWAMP)){
				growthPercent*=2;
			}
		}
		else if (hash == mushroomRed){
			if (BiomeDictionary.isBiomeOfType(biome, Type.CONIFEROUS)){
				growthPercent*=2;
			}
		}
		else if (hash == netherWart){
			if (BiomeDictionary.isBiomeOfType(biome, Type.NETHER)){
				growthPercent*=2;
			}
		}
		else if (hash == potato){
			if (BiomeDictionary.isBiomeOfType(biome, Type.PLAINS)){
				growthPercent*=2;
			}
		}
		else if (hash == pumpkin){
			if (BiomeDictionary.isBiomeOfType(biome, Type.FOREST)){
				growthPercent*=2;
			}
		}
		else if (hash == reed){
			if (BiomeDictionary.isBiomeOfType(biome, Type.RIVER)){
				growthPercent*=2;
			}
		}
		else if (hash == wheat){
			if (BiomeDictionary.isBiomeOfType(biome, Type.SAVANNA)){
				growthPercent*=2;
			}
		}

		else {
			System.out.println("Unrecognised crop type " + event.block.getLocalizedName());
		}
		
		if (event.random.nextInt(100) > growthPercent){
			event.setResult(Result.DENY);
		}
		
	}
	
}
