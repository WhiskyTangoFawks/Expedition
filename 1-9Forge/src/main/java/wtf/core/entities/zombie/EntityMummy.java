package wtf.core.entities.zombie;

import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.core.init.WTFArmor;

public class EntityMummy extends EntityZombie{
	public EntityMummy(World worldIn) {
		super(worldIn);

		this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(WTFArmor.mummyHelmet));
		this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(WTFArmor.mummyChestplate));
		this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(WTFArmor.mummyLeggings));
		this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(WTFArmor.mummyBoots));	 

		this.setDropChance(EntityEquipmentSlot.HEAD, 0.1F);
		this.setDropChance(EntityEquipmentSlot.CHEST, 0.1F);
		this.setDropChance(EntityEquipmentSlot.LEGS, 0.1F);
		this.setDropChance(EntityEquipmentSlot.FEET, 0.1F);


	}

	public EntityMummy(World worldIn, boolean isPharaoh ) {
		super(worldIn);

		if (isPharaoh){
			this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(WTFArmor.pharaohHelmet));
			this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(WTFArmor.pharaohChestplate));
			this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(WTFArmor.pharaohLeggings));
			this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(WTFArmor.pharaohBoots));
			this.enablePersistence();
		}
		else {
			this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(WTFArmor.mummyHelmet));
			this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(WTFArmor.mummyChestplate));
			this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(WTFArmor.mummyLeggings));
			this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(WTFArmor.mummyBoots));	 
		}
		this.setDropChance(EntityEquipmentSlot.HEAD, 0.1F);
		this.setDropChance(EntityEquipmentSlot.CHEST, 0.1F);
		this.setDropChance(EntityEquipmentSlot.LEGS, 0.1F);
		this.setDropChance(EntityEquipmentSlot.FEET, 0.1F);

	}

	public void onLivingUpdate()
	{
		if (this.worldObj.isDaytime() && !this.worldObj.isRemote)
		{
			float f = this.getBrightness(1.0F);
			BlockPos blockpos = this.getRidingEntity() instanceof EntityBoat ? (new BlockPos(this.posX, (double)Math.round(this.posY), this.posZ)).up() : new BlockPos(this.posX, (double)Math.round(this.posY), this.posZ);

			if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.worldObj.canSeeSky(blockpos))
			{
				boolean flag = true;


				if (flag)
				{
					this.setFire(8);
				}
			}
		}

		super.onLivingUpdate();
	}
}
