package wtf.core.entities;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNodeType;

public class EntityDresser {

	static Random random = ThreadLocalRandom.current();
	
	public static EntityZombie setMiner(EntityZombie mob){
		mob.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack((random.nextBoolean() ? Items.STONE_PICKAXE : Items.WOODEN_PICKAXE), 1, random.nextInt(60)));
		mob.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
		return mob;
	}
	
	public static EntitySkeleton setKnight(EntitySkeleton mob){
		mob.tasks.addTask(5, new EntityAIMoveTowardsRestriction(mob, 1.0D));
		mob.targetTasks.addTask(2, new EntityAINearestAttackableTarget(mob, EntityPlayer.class, true));
		mob.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
		mob.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack((random.nextBoolean() ? Items.STONE_SWORD : Items.IRON_SWORD), 1, random.nextInt(60)));
		return mob;
	}
	
	public static EntityIronGolem setDerangedGolem(EntityIronGolem mob){ 
		mob.tasks.taskEntries.clear();
		mob.targetTasks.taskEntries.clear();
		mob.setPathPriority(PathNodeType.WATER, -1.0F);
		mob.tasks.addTask(1, new EntityAIAttackMelee(mob, 1.0D, true));
		mob.tasks.addTask(5, new EntityAIMoveTowardsRestriction(mob, 1.0D));
		//this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		mob.tasks.addTask(8, new EntityAIWatchClosest(mob, EntityPlayer.class, 8.0F));
		mob.tasks.addTask(8, new EntityAILookIdle(mob));
		mob.targetTasks.addTask(1, new EntityAIHurtByTarget(mob, true));
		mob.targetTasks.addTask(2, new EntityAINearestAttackableTarget(mob, EntityPlayer.class, true));
		mob.targetTasks.addTask(3, new EntityAINearestAttackableTarget(mob, EntityLiving.class, true));
		mob.enablePersistence();
		return mob;
	}
}
