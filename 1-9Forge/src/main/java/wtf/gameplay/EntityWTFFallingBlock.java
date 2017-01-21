package wtf.gameplay;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityWTFFallingBlock extends EntityFallingBlock {

	public EntityWTFFallingBlock(World worldIn, BlockPos pos, IBlockState fallingBlockState) {
		super(worldIn, pos.getX()+0.5, pos.getY(), pos.getZ()+0.5, fallingBlockState);
		
	}

	  @Override
	public void onUpdate(){
		  if (this.fallTime > 20 && Math.abs(this.motionX) + Math.abs(this.motionY) + Math.abs(this.motionZ) < 0.1){
			  setDead();
		  }
		  else {
			  super.onUpdate();
		  }
		  
	    }

    @Override
	public void setDead()
    {
    	if (!this.isDead){
    		GravityMethods.checkPos(this.worldObj, new BlockPos(this.posX, this.posY, this.posZ));
    		this.isDead = true;
    	}
    	
    }
	

    
}
