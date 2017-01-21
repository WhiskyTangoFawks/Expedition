package wtf.gameplay;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ExploderEntity extends Entity{

	private static final DataParameter<Integer> FUSE = EntityDataManager.<Integer>createKey(EntityTNTPrimed.class, DataSerializers.VARINT);

	public ExploderEntity(World worldIn, BlockPos pos, float str) {
		super(worldIn);
		fuse = 2;
		this.str = str;
		this.posX=pos.getX();
		this.posY=pos.getY();
		this.posZ=pos.getZ();
	}

	public ExploderEntity(World worldIn, Vec3d pos, float str, int fuse) {
		super(worldIn);
		this.fuse = fuse;
		this.str = str;
		this.posX=pos.xCoord;
		this.posY=pos.yCoord;
		this.posZ=pos.zCoord;
	}

	//I should be able to modify this to actually do the explosion as well-
	//which allows me to modify the time between increments, and overall slow the explosion down

	private float str;
	private int fuse;

	CustomExplosion explosion;

	@Override
	public void onUpdate()
	{

		if (!this.worldObj.isRemote){
			if (fuse == 0){
				explosion = new CustomExplosion(null, this.worldObj, new Vec3d(this.posX, this.posY, this.posZ), str);
				explosion.incrementVectorList();
				explosion.update();
			}
			else if (fuse < 0){
				if (explosion.vecList.size() > 0){
					explosion.incrementVectorList();
					explosion.update();
				}
				else {
					this.setDead();
					explosion.update();
				}

			}
			fuse--;
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
