package wtf.entities.customentities;

import javax.annotation.Nullable;

import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityZombieGhost extends EntityBlockHead implements IRangedAttackMob{

	public EntityZombieGhost(World worldIn) {
		super(worldIn);
	}


	 
	    @Override
		@Nullable
	    protected ResourceLocation getLootTable()
	    {
	     return null;
	    }
}
