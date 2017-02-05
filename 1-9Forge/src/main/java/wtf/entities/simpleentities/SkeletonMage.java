package wtf.entities.simpleentities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class SkeletonMage extends EntitySkeleton{
	public SkeletonMage(World worldIn){
		super(worldIn);
		this.tasks.addTask(4, new EntityAIAttackRanged(this, 1.0D, 20, 60, 15.0F));
		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BLAZE_ROD));
		this.tasks.removeTask(new EntityAIAttackMelee(this, 1.2D, false));
	}
	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty){}
	@Override
	public void setCombatTask(){}
	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float p_70785_2_)
	{
		double d0 = target.posX - this.posX;
		double d1 = target.getEntityBoundingBox().minY  + target.height / 2.0F - (this.posY + this.height / 2.0F);
		double d2 = target.posZ - this.posZ;

		float f1 = MathHelper.sqrt_float(p_70785_2_) * 0.5F;
		this.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		this.swingArm(EnumHand.MAIN_HAND);
		for (int i = 0; i < 1; ++i){
			EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.worldObj, this, d0 + this.rand.nextGaussian() * f1, d1, d2 + this.rand.nextGaussian() * f1);
			entitysmallfireball.setLocationAndAngles(this.posX, this.posY + this.getEyeHeight(), this.posZ, this.rotationYaw, this.rotationPitch);
			entitysmallfireball.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
			entitysmallfireball.posY -= 0.10000000149011612D;
			entitysmallfireball.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
			this.worldObj.spawnEntityInWorld(entitysmallfireball);
		}
	}
}
