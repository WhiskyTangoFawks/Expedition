package wtf.gameplay;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.core.blocks.BlockDenseOre;
import wtf.core.config.CoreConfig;
import wtf.core.config.GameplayConfig;
import wtf.core.init.BlockSets;


public class GamePlayEventListener {

	Random random = new Random();

	//slows mining of stone
	@SubscribeEvent
	public void StoneBreakSpeed (BreakSpeed event)
	{
		Block block = event.getState().getBlock();
		if (!event.getEntityPlayer().capabilities.isCreativeMode){
			/*//Tinkers Construct hammer speed changer
			if (event.getEntityPlayer().getHeldItem() != null && Loader.isModLoaded("TConstruct") &&  event.getEntityPlayer().getHeldItem().getItem() instanceof Hammer){
				if (BlockSets.stoneAndCobble.containsKey(event.block) && speedMod.containsKey(event.block)){
					event.newSpeed = (speedMod.get(event.block) + ((1-speedMod.get(event.block))/2)) * event.originalSpeed;
				}
				else {
					event.newSpeed =  WTFTweaksConfig.hammerBreakSpeed* event.originalSpeed;
				}
			}

			else*/ if (BlockSets.blockMiningSpeed.containsKey(block)){
				event.setNewSpeed(BlockSets.blockMiningSpeed.get(block) * event.getOriginalSpeed());
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
		if (!event.getPlayer().capabilities.isCreativeMode && BlockSets.fallingBlocks.containsKey(block)){

			GravityMethods.dropBlock(event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), true);
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


		if (!event.getPlayer().capabilities.isCreativeMode && BlockSets.hasCobble(event.getState()) && GameplayConfig.stoneFracturesBeforeBreaking)	{


			/*if (Loader.isModLoaded("TConstruct") && event.getPlayer().getHeldItem().getItem() instanceof Hammer){
	//Just have the hammer treat all blocks like they're ores
				//Hammer tool = (Hammer)event.getPlayer().getHeldItem().getItem();
				event.setCanceled(true);
				if (BlockSets.stoneAndCobble.containsKey(world.getBlock(x, y, z))){
					FracMethods.hammerFrac(world, x, y, z, event.getPlayer().getHeldItem().getItem().getHarvestLevel(event.getPlayer().getHeldItem(), "pickaxe"));
					event.getPlayer().getHeldItem().attemptDamageItem(1, random);
				}

			}
			else*/ if (StoneFractureMethods.fracStone(event.getWorld(), event.getPos(), event.getState())){
				event.setCanceled(true);
				if (tool != null){
					tool.attemptDamageItem(1, random);
					event.getPlayer().addStat(StatList.getBlockStats(block));
					event.getPlayer().addExhaustion(0.025F);
				}
			}


		}
		//This checks if the block is an ore block, then calls the fracture method associated with it in the ore blocks hashmap if it is
		if (BlockSets.oreAndFractures.contains(block) && GameplayConfig.oreFractures)
		{
			StoneFractureMethods.tryFrac(event.getWorld(), event.getPos(), block, toolLevel);
		}

		//checks for fall of the block above the block thats been broken
		GravityMethods.disturbBlock(event.getWorld(), event.getPos().getX(), event.getPos().getY()+1, event.getPos().getZ());

	}

	@SubscribeEvent
	public void BlockHarvestEvent(HarvestDropsEvent event){
		if (event.getState().getBlock() instanceof BlockLeaves){
			if (random.nextInt(100) < GameplayConfig.stickDrop){

				event.getDrops().add(new ItemStack(Items.STICK, 1));
			}
		}

	}


	/*//QuickCrafting Events
	 * 	@SubscribeEvent
	public void PlayerCrafting(PlayerInteractEvent event){
		//metadata changer
		//event.world.setBlockMetadataWithNotify(event.x, event.y, event.z, event.world.getBlockMetadata(event.x,event.y,event.z)+1, 2);
		if (!event.world.isRemote && WTFTweaksConfig.enableQuickCrafting &&
				event.action == net.minecraftforge.event.entity.player.PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK &&
				event.world.getBlock(event.x,  event.y,  event.z) == Blocks.crafting_table) {
			event.setCanceled (true);
			event.entityPlayer.openGui(WTFtweaks.instance, 0, event.world, 0, 0, 0);
		}
	}*/

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
		Explosion explosion = event.getExplosion();
		float size = ObfuscationReflectionHelper.getPrivateValue(Explosion.class, explosion, 8);
		new CustomExplosion(explosion.getExplosivePlacedBy(), event.getWorld(), explosion.getPosition(), size);
		event.setCanceled(true);

	}


}
