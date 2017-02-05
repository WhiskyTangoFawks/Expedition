package wtf.entities.simpleentities;

import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.SkeletonType;
import net.minecraft.world.World;

public class Stray extends EntitySkeleton{
	public Stray(World worldIn) {
		super(worldIn);
		this.setSkeletonType(SkeletonType.STRAY);
	}
}
