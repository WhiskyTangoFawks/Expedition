package wtf.entities.customentities;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityDerangedGolem extends EntityIronGolem{

	public EntityDerangedGolem(World worldIn) {
		super(worldIn);
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15D);

	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		EntityPlayer player = this.worldObj.getClosestPlayerToEntity(this, 5);
		if (player != null){
			this.setRevengeTarget(player);
		}

	}

}
