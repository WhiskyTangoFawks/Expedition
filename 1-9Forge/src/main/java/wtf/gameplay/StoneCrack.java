package wtf.gameplay;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StoneCrack extends Entity{
	
	 private static final DataParameter<Integer> FUSE = EntityDataManager.<Integer>createKey(EntityTNTPrimed.class, DataSerializers.VARINT);
	 private int fuse;
	 Random random = new Random();
	
	public StoneCrack(World worldIn, BlockPos pos) {
		super(worldIn);
		fuse = random.nextInt(20);
		this.posX=pos.getX();
		this.posY=pos.getY();
		this.posZ=pos.getZ();
	}
	
	

	@Override
	public void onUpdate()
    {

		if (fuse > 0){  
			fuse--;
		}

		if (fuse < 1)
		{
			this.setDead();
			
			if (!this.worldObj.isRemote)
			{
				StoneFractureMethods.frac(this.worldObj, new BlockPos(this.posX, this.posY, this.posZ), 5);	
				//this.worldObj.playSound(this.posX, this.posY, this.posZ, SoundCategory, getSoundCategory(), volume, pitch, distanceDelay);
			}
		}

    }


  @Override
protected void entityInit()
    {
        this.dataManager.register(FUSE, Integer.valueOf(10));
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    @Override
	protected boolean canTriggerWalking()
    {
        return false;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    @Override
	public boolean canBeCollidedWith()
    {
    	  return false;
    }


    @Override
	protected void writeEntityToNBT(NBTTagCompound compound)
    {
        compound.setShort("Fuse", (short)fuse);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
	protected void readEntityFromNBT(NBTTagCompound compound)
    {
        fuse=(compound.getShort("Fuse"));
    }

}
