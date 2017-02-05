package wtf.entities.simpleentities;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.ZombieType;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Mummy extends EntityZombie{

	static int mummyCount = 0;
	
	public Mummy(World worldIn) {
		super(worldIn);
		this.setZombieType(ZombieType.HUSK);
		
		if (mummyCount > 4){
			mummyCount = 0;
			this.setCustomNameTag("Pharaoh");
			this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET, 1, rand.nextInt(60)));
			this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE, 1, rand.nextInt(60)));
			this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.GOLDEN_LEGGINGS, 1, rand.nextInt(60)));
			this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.GOLDEN_BOOTS, 1, rand.nextInt(60)));
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD, 1, rand.nextInt(60)));
			this.setAIMoveSpeed(0.2F);

		}
		else {
			mummyCount++;
			this.setCustomNameTag("Mummy");
			if (rand.nextFloat() < 0.25) {this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET, 1, rand.nextInt(30)));}
			this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE, 1, rand.nextInt(30)));
			if (rand.nextFloat() < 0.75) this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS, 1, rand.nextInt(30)));
			if (rand.nextFloat() < 0.50) this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.LEATHER_BOOTS, 1, rand.nextInt(30)));
		}
	}
}