package wtf.gameplay;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityWTFSlidingBlock extends EntityWTFFallingBlock{

	public final int oriY;
	
	public EntityWTFSlidingBlock(World worldIn, BlockPos pos, BlockPos targetpos, IBlockState fallingBlockState) {
		super(worldIn, pos, fallingBlockState);
		double motx = pos.getX() - targetpos.getX();
		double motz = pos.getZ() - targetpos.getZ();
		addVelocity(0.05D * motx, -0.1D, 0.05D * motz);
		this.oriY = pos.getY();
		
		worldIn.spawnEntityInWorld(this);
		worldIn.setBlockToAir(pos);
		System.out.println("New sliding block created");
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return true;
	}

    
	@Override
	public void onUpdate()
    {
		if (this.fallTime++ > 18){
    		super.onUpdate();
    	}
    	else {
    		this.moveEntity(this.motionX, this.motionY, this.motionZ);                    		
    	}
    }
	
	
	
}
