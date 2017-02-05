package wtf.entities.customentities;

import javax.annotation.Nullable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBlockHead extends EntityMob implements IRangedAttackMob{

	//get them to not attack villgers on sight
	//Set them non-flamable

	public double renderOffsetX = 0;
	public double renderOffsetY = 0;
	public double renderOffsetZ = 0;

	    private static final DataParameter<Boolean> SWINGING_ARMS = EntityDataManager.<Boolean>createKey(EntityBlockHead.class, DataSerializers.BOOLEAN);
	    private final AIBlockHeadRangedAttack aiArrowAttack = new AIBlockHeadRangedAttack(this, 1.0D, 20, 15.0F);
	    private final EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, 1.2D, false)
	    {
	        /**
	         * Resets the task
	         */
	        @Override
			public void resetTask()
	        {
	            super.resetTask();
	            EntityBlockHead.this.setSwingingArms(false);
	        }
	        /**
	         * Execute a one shot task or start executing a continuous task
	         */
	        @Override
			public void startExecuting()
	        {
	            super.startExecuting();
	            EntityBlockHead.this.setSwingingArms(true);
	        }
	    };

	    public EntityBlockHead(World worldIn)
	    {
	        super(worldIn);
	        this.setCombatTask();
	    }

	    @Override
		protected void initEntityAI()
	    {
	        this.tasks.addTask(1, new EntityAISwimming(this));
	        //this.tasks.addTask(2, new EntityAIRestrictSun(this));
	        //this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
	        this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityCreeper.class, 6.0F, 1.0D, 1.2D));
	       
	        this.tasks.addTask(4, new EntityAIWander(this, 1.0D));
	        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	        this.tasks.addTask(6, new EntityAILookIdle(this));
	        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
	        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	        //this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
	    }

	    @Override
		protected void applyEntityAttributes()
	    {
	        super.applyEntityAttributes();
	        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20D);
	    }

	    @Override
		protected void entityInit()
	    {
	        super.entityInit();
	        this.dataManager.register(SWINGING_ARMS, Boolean.valueOf(false));
	    }

	    @Override
		protected SoundEvent getAmbientSound()
	    {
	        return SoundEvents.ENTITY_PLAYER_BREATH;
	    }

	    @Override
		protected SoundEvent getHurtSound()
	    {
	        return SoundEvents.ENTITY_PLAYER_HURT;
	    }

	    @Override
		protected SoundEvent getDeathSound()
	    {
	        return SoundEvents.ENTITY_PLAYER_DEATH;
	    }

	    /**
	     * Get this Entity's EnumCreatureAttribute
	     */
	    @Override
		public EnumCreatureAttribute getCreatureAttribute()
	    {
	        return EnumCreatureAttribute.UNDEFINED;
	    }


	    /**
	     * Handles updating while being ridden by an entity
	     */
	    @Override
		public void updateRidden()
	    {
	        super.updateRidden();

	        if (this.getRidingEntity() instanceof EntityCreature)
	        {
	            EntityCreature entitycreature = (EntityCreature)this.getRidingEntity();
	            this.renderYawOffset = entitycreature.renderYawOffset;
	        }
	    }

	    /**
	     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
	     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
	     */
	    @Override
		@Nullable
	    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
	    {
	 
	    		this.setCombatTask();
	            this.setEquipmentBasedOnDifficulty(difficulty);
	            this.setEnchantmentBasedOnDifficulty(difficulty);

	        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * difficulty.getClampedAdditionalDifficulty());

	        return livingdata;
	    }

	    /**
	     * sets this entity's combat AI.
	     */
	    public void setCombatTask()
	    {
	        if (this.worldObj != null && !this.worldObj.isRemote)
	        {
	            this.tasks.removeTask(this.aiAttackOnCollide);
	            this.tasks.removeTask(this.aiArrowAttack);
	            ItemStack itemstack = this.getHeldItemMainhand();

	            if (itemstack != null && itemstack.getItem() == Items.BOW)
	            {
	            	int i = 20;

	                if (this.worldObj.getDifficulty() != EnumDifficulty.HARD)
	                {
	                    i = 40;
	                }

	                this.aiArrowAttack.setAttackCooldown(i);
	                this.tasks.addTask(3, this.aiArrowAttack);
	            }
	            else
	            {
	                this.tasks.addTask(3, this.aiAttackOnCollide);
	            }
	        }
	    }

	    /**
	     * Attack the specified entity using a ranged attack.
	     *  
	     * @param distanceFactor How far the target is, normalized and clamped between 0.1 and 1.0
	     */
	    @Override
		public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
	    {
	        EntityTippedArrow entitytippedarrow = new EntityTippedArrow(this.worldObj, this);
	        double d0 = target.posX - this.posX;
	        double d1 = target.getEntityBoundingBox().minY + target.height / 3.0F - entitytippedarrow.posY;
	        double d2 = target.posZ - this.posZ;
	        double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
	        entitytippedarrow.setThrowableHeading(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, 14 - this.worldObj.getDifficulty().getDifficultyId() * 4);
	        int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, this);
	        int j = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, this);
	        DifficultyInstance difficultyinstance = this.worldObj.getDifficultyForLocation(new BlockPos(this));
	        entitytippedarrow.setDamage(distanceFactor * 2.0F + this.rand.nextGaussian() * 0.25D + this.worldObj.getDifficulty().getDifficultyId() * 0.11F);

	        if (i > 0)
	        {
	            entitytippedarrow.setDamage(entitytippedarrow.getDamage() + i * 0.5D + 0.5D);
	        }

	        if (j > 0)
	        {
	            entitytippedarrow.setKnockbackStrength(j);
	        }

	        boolean flag = this.isBurning() && difficultyinstance.isHard() && this.rand.nextBoolean();
	        flag = flag || EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, this) > 0;

	        if (flag)
	        {
	            entitytippedarrow.setFire(100);
	        }

	        ItemStack itemstack = this.getHeldItem(EnumHand.OFF_HAND);

	        if (itemstack != null && itemstack.getItem() == Items.TIPPED_ARROW)
	        {
	            entitytippedarrow.setPotionEffect(itemstack);
	        }

	        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
	        this.worldObj.spawnEntityInWorld(entitytippedarrow);
	    }

	    /**
	     * (abstract) Protected helper method to read subclass entity data from NBT.
	     */
	    @Override
		public void readEntityFromNBT(NBTTagCompound compound)
	    {
	        super.readEntityFromNBT(compound);

	        this.setCombatTask();
	    }

	    /**
	     * (abstract) Protected helper method to write subclass entity data to NBT.
	     */
	    @Override
		public void writeEntityToNBT(NBTTagCompound compound)
	    {
	        super.writeEntityToNBT(compound);
	        
	    }

	    @Override
		public void setItemStackToSlot(EntityEquipmentSlot slotIn, @Nullable ItemStack stack)
	    {
	        super.setItemStackToSlot(slotIn, stack);

	        if (!this.worldObj.isRemote && slotIn == EntityEquipmentSlot.MAINHAND)
	        {
	            this.setCombatTask();
	        }
	    }

	    @Override
		public float getEyeHeight()
	    {
	        return 1.74F;
	    }

	    /**
	     * Returns the Y Offset of this entity.
	     */
	    @Override
		public double getYOffset()
	    {
	        return -0.35D;
	    }

	    @SideOnly(Side.CLIENT)
	    public boolean isSwingingArms()
	    {
	        return this.dataManager.get(SWINGING_ARMS).booleanValue();
	    }

	    public void setSwingingArms(boolean swingingArms)
	    {
	        this.dataManager.set(SWINGING_ARMS, Boolean.valueOf(swingingArms));
	    }

}
