package wtf.gameplay;

import java.util.Random;

import akka.routing.Pool;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.CreateFluidSourceEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import squeek.applecore.api.plants.PlantGrowthEvent;
import wtf.Core;
import wtf.blocks.AbstractBlockDerivative;
import wtf.blocks.BlockDenseOre;
import wtf.config.CoreConfig;
import wtf.config.GameplayConfig;
import wtf.init.BlockSets;
import wtf.init.WTFItems;


public class GamePlayEventListener {

	Random random = new Random();

	//slows mining of stone
	@SubscribeEvent
	public void StoneBreakSpeed (BreakSpeed event)
	{
		Block block = event.getState().getBlock();
		if (!event.getEntityPlayer().capabilities.isCreativeMode){
			//Tinkers Construct hammer speed changer
			if (isHammer(event.getEntityPlayer().getHeldItemMainhand())){
				if (BlockSets.blockMiningSpeed.containsKey(block)){
					event.setNewSpeed(BlockSets.blockMiningSpeed.get(block) * event.getOriginalSpeed());
				}
			}
			else if (BlockSets.blockMiningSpeed.containsKey(block)){
				event.setNewSpeed(BlockSets.blockMiningSpeed.get(block) * event.getOriginalSpeed());
			}
			else if (block instanceof AbstractBlockDerivative){
				if (!(block.canHarvestBlock(event.getEntityPlayer().worldObj, event.getPos(), event.getEntityPlayer()))){
					event.setNewSpeed(0.2F * event.getOriginalSpeed());
				}
			}
		}
	}


	//Deals with whenever a player places a block
	@SubscribeEvent
	public void PlayerPlaceBlock (PlaceEvent event)
	{

		if(CoreConfig.enableNameGetter){
			event.getPlayer().addChatComponentMessage(new TextComponentString("The block name is : " + event.getState().getBlock().getRegistryName()));
			event.getPlayer().addChatComponentMessage(new TextComponentString("The block metadata is : " + event.getState().getBlock().getMetaFromState(event.getState())));
		}

		Block block = event.getState().getBlock();
		//if (!event.getPlayer().capabilities.isCreativeMode && BlockSets.fallingBlocks.containsKey(block)){
		if (BlockSets.fallingBlocks.containsKey(block)){
			GravityMethods.checkPos(event.getWorld(), event.getPos());
		}

	}

	
	@SubscribeEvent
	public void rightClick(RightClickBlock event)
	{
		if (!event.getEntityPlayer().capabilities.isCreativeMode && event.getEntityPlayer().getHeldItemMainhand() != null){
			Block block = Block.getBlockFromItem(event.getEntityPlayer().getHeldItemMainhand().getItem());
			if (BlockSets.oreAndFractures.contains(block)){
				event.setCanceled(true);

			}
		}

	}
	
	@SubscribeEvent
	public void BlockBreakEvent(BreakEvent event)
	{
		ItemStack tool = event.getPlayer().getHeldItemMainhand();
		int toolLevel = tool == null ? 0 : tool.getItem().getHarvestLevel(tool, "pickaxe");
		Block block = event.getWorld().getBlockState(event.getPos()).getBlock();


		//Check if the block we're trying to break should fracture
		if (!event.getPlayer().capabilities.isCreativeMode){
			if (BlockSets.hasCobble(event.getState()) && GameplayConfig.stoneFracturesBeforeBreaking)	{
				if (isHammer(event.getPlayer().getHeldItemMainhand()) && GameplayConfig.modifyHammer){
					event.setCanceled(true);
					StoneFractureMethods.fracStone(event.getWorld(), event.getPos(), event.getState());
					StoneFractureMethods.hammerFrac(event.getWorld(), event.getPos(), toolLevel);
					event.setCanceled(true);
					if (tool != null){
						tool.attemptDamageItem(1, random);
						event.getPlayer().addStat(StatList.getBlockStats(block));
						event.getPlayer().addExhaustion(0.025F);

					}

				}

				//if it's a stone to be fractured, cancel the event, damage tools, and frac the stone
				else if (StoneFractureMethods.fracStone(event.getWorld(), event.getPos(), event.getState())){
					event.setCanceled(true);
					if (tool != null){
						tool.attemptDamageItem(1, random);
						event.getPlayer().addStat(StatList.getBlockStats(block));
						event.getPlayer().addExhaustion(0.025F);
					}

				}
			}
			else if (BlockSets.oreAndFractures.contains(block) && GameplayConfig.oreFractures)
			{
				StoneFractureMethods.tryFrac(event.getWorld(), event.getPos(), block, toolLevel);
			}
		}
	}
	

	
	@SubscribeEvent
	public void BlockHarvestEvent(HarvestDropsEvent event){
		if (event.getState().getBlock() instanceof BlockLeaves){
			if (random.nextInt(100) < GameplayConfig.stickDrop){
				event.getDrops().add(new ItemStack(Items.STICK, 1));
			}
		}
		else {
			//instead of using the block break event, which fires, before the block is replaced, I use the harvest, which is fired afterwards
			GravityMethods.checkPos(event.getWorld(), event.getPos().up());
		}

	}


	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		if (GameplayConfig.featherDrop != 0 && !event.getEntity().worldObj.isRemote && (event.getEntity() instanceof EntityChicken)){
			EntityChicken chicken = (EntityChicken) event.getEntity();
			if (!chicken.isChild()){

				if(random.nextInt(GameplayConfig.featherDrop) == 1){
					chicken.dropItem(Items.FEATHER, 1);
				}
			}
		}
	}

	@SubscribeEvent
	public void explosion(ExplosionEvent.Start event){
		if (GameplayConfig.customExplosion){
			Explosion explosion = event.getExplosion();
			float size = ObfuscationReflectionHelper.getPrivateValue(Explosion.class, explosion, 8);
			//System.out.println(explosion.getExplosivePlacedBy() + " " + explosion.getPosition() + " " + size);
			new CustomExplosion(explosion.getExplosivePlacedBy(), event.getWorld(), explosion.getPosition(), size);
		event.setCanceled(true);
		}

	}
	
	@SubscribeEvent
	public void watergen(CreateFluidSourceEvent event){
		if (GameplayConfig.waterControl && event.getWorld().getBlockState(event.getPos()).getMaterial() ==Material.WATER && !BiomeDictionary.isBiomeOfType(event.getWorld().getBiome(event.getPos()), Type.WET)){
			event.setResult(Result.DENY);
		}
	}
	
/*
	@SubscribeEvent
	public void notify(NeighborNotifyEvent event){
		System.out.println("Notify event");
		//GravityMethods.checkPos(event.getWorld(), event.getPos());
	}
	*/
	

	public static boolean isHammer(ItemStack stack){
		if (stack == null){
			return false;
		}
		return stack.getItem().getItemStackDisplayName(stack).contains("ammer");
		
	}
	

	


}
