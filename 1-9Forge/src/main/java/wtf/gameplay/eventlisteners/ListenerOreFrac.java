package wtf.gameplay.eventlisteners;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.config.GameplayConfig;
import wtf.gameplay.fracturing.EntityStoneCrack;
import wtf.init.BlockSets;

public class ListenerOreFrac {


	@SubscribeEvent
	public void rightClick(RightClickBlock event)
	{
		if (!event.getEntityPlayer().capabilities.isCreativeMode && event.getItemStack() != null){
			Block block = Block.getBlockFromItem(event.getItemStack().getItem());
			if (BlockSets.oreAndFractures.contains(block)){
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void BlockBreakEvent(BreakEvent event)
	{
		ItemStack tool = event.getPlayer().getHeldItemMainhand();
		boolean silk = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, tool) > 0;

		//REENABLE THIS LATER
		
		if (!event.getPlayer().capabilities.isCreativeMode && !silk){
			Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
			if (BlockSets.oreAndFractures.contains(block))
			{
				
				if (GameplayConfig.fracSimple){
					EntityStoneCrack.FracOreSmple(event.getWorld(), event.getPos());
				}
				else {
					int toolLevel = tool == null ? 0 : tool.getItem().getHarvestLevel(tool, "pickaxe", event.getPlayer(), event.getState());
					EntityStoneCrack.FracOre(event.getWorld(), event.getPos(), toolLevel);
				}
			}
		}
	}


}
