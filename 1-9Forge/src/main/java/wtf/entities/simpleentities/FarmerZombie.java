package wtf.entities.simpleentities;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FarmerZombie extends EntityZombie{

	public FarmerZombie(World worldIn) {
		super(worldIn);
		setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.WOODEN_HOE, 1, rand.nextInt(30)));
		setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.POISONOUS_POTATO));
	}

}
