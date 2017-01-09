package wtf.gameplay.eventlisteners;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.config.GameplayConfig;
import wtf.config.StoneRegEntry;
import wtf.config.WTFStoneRegistry;
import wtf.gameplay.StoneFractureMethods;

public class ListenerStoneFrac {

	@SubscribeEvent
	public void BlockBreakEvent(BreakEvent event)
	{
		ItemStack tool = event.getPlayer().getHeldItemMainhand();
		boolean silk = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, tool) > 0;
		
		if (!event.getPlayer().capabilities.isCreativeMode && !silk){
			
			int toolLevel = tool == null ? 0 : tool.getItem().getHarvestLevel(tool, "pickaxe");
			
			StoneRegEntry entry = WTFStoneRegistry.stoneReg.get(event.getState());
			if (entry != null && entry.fractures)	{

				if (ListenerHelper.isHammer(event.getPlayer().getHeldItemMainhand()) && GameplayConfig.modifyHammer){
					event.setCanceled(true);
					StoneFractureMethods.fracStone(event.getWorld(), event.getPos(), event.getState());
					StoneFractureMethods.hammerFrac(event.getWorld(), event.getPos(), toolLevel);
					event.setCanceled(true);
					if (tool != null){
						tool.attemptDamageItem(1, event.getWorld().rand);
						event.getPlayer().addStat(StatList.getBlockStats(event.getState().getBlock()));
						event.getPlayer().addExhaustion(0.025F);
					}
				}

				else {
					StoneFractureMethods.fracStone(event.getWorld(), event.getPos(), event.getState());

					event.setCanceled(true);
					if (tool != null){
						tool.attemptDamageItem(1, event.getWorld().rand);
						event.getPlayer().addStat(StatList.getBlockStats(event.getState().getBlock()));
						event.getPlayer().addExhaustion(0.025F);
					}

				}
			}
		}
	}
	
}
