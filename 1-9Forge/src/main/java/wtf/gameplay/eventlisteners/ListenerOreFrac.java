package wtf.gameplay.eventlisteners;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.config.GameplayConfig;
import wtf.config.StoneRegEntry;
import wtf.config.WTFStoneRegistry;
import wtf.gameplay.StoneFractureMethods;
import wtf.init.BlockSets;

public class ListenerOreFrac {


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

		boolean silk = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, tool) > 0;

		if (!event.getPlayer().capabilities.isCreativeMode && !silk){
			Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
			if (BlockSets.oreAndFractures.contains(block))
			{
				int toolLevel = tool == null ? 0 : tool.getItem().getHarvestLevel(tool, "pickaxe");	
				StoneFractureMethods.tryFrac(event.getWorld(), event.getPos(), block, toolLevel);
			}
		}
	}


}
