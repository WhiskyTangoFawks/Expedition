package wtf.gameplay.eventlisteners;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.blocks.AbstractBlockDerivative;
import wtf.config.GameplayConfig;
import wtf.init.BlockSets;

public class ListenerMiningSpeed {

	@SubscribeEvent
	public void StoneBreakSpeed (BreakSpeed event)
	{
		IBlockState state = event.getState();
		if (!event.getEntityPlayer().capabilities.isCreativeMode && BlockSets.blockMiningSpeed.containsKey(state)){
			//Tinkers Construct hammer speed changer
			//if (isHammer(event.getEntityPlayer().getHeldItemMainhand())){
			//		event.setNewSpeed(BlockSets.blockMiningSpeed.get(state) * event.getOriginalSpeed());
			//}
			//else{
			event.setNewSpeed(BlockSets.blockMiningSpeed.get(state) * event.getOriginalSpeed());
			//}
			
			//else if (state.getBlock() instanceof AbstractBlockDerivative){
			//	if (!(state.getBlock().canHarvestBlock(event.getEntityPlayer().worldObj, event.getPos(), event.getEntityPlayer()))){
			//		event.setNewSpeed(0.2F * event.getOriginalSpeed());
			//	}
			//}
		}
	}
	
	public static boolean isHammer(ItemStack stack){
		if (stack == null){
			return false;
		}
		return stack.getItem().getItemStackDisplayName(stack).contains("ammer");
		
	}
	
}
