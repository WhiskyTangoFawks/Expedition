package wtf.entities.simpleentities;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.ZombieType;
import net.minecraft.world.World;


	public class Husk extends EntityZombie{
		public Husk(World worldIn) {
			super(worldIn);
			this.setZombieType(ZombieType.HUSK);
		}
	}
