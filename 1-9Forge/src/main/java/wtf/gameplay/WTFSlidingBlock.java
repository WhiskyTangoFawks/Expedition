package wtf.gameplay;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import wtf.config.GameplayConfig;

public class WTFSlidingBlock extends EntityFallingBlock
{
	private IBlockState fallTile;
	private int fallHurtMax = 40;
	private float fallHurtAmount = 2.0F;
	private final BlockPos origin;
	boolean fallen = false;

	public WTFSlidingBlock(World worldIn, BlockPos pos, BlockPos targetpos)
	{
		super(worldIn, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, worldIn.getBlockState(pos));
		this.fallTile = worldIn.getBlockState(pos);

		this.origin = pos;

		double motx = pos.getX() - targetpos.getX();
		double motz = pos.getZ() - targetpos.getZ();

		addVelocity(0.1D * motx, -0.1D, 0.1D * motz);
		worldIn.spawnEntityInWorld(this);
	}

	public boolean canBeCollidedWith()
	{
		return true;
	}

	public void onUpdate()
	{
		Block block = this.fallTile.getBlock();

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		if (this.fallTime++ == 0)
		{
			BlockPos blockpos = new BlockPos(this);
			if (this.worldObj.getBlockState(blockpos).getBlock() == block)
			{
				this.worldObj.setBlockToAir(blockpos);
			}
			else if (!this.worldObj.isRemote)
			{
				setDead();
				return;
			}
		}
		BlockPos blockpos1 = new BlockPos(this);
		if ((!this.fallen) && (this.posY < this.origin.getY()))
		{
			this.fallen = true;
			GravityMethods.checkPos(this.worldObj, this.origin.down());
		}
		if (!func_189652_ae()) {
			this.motionY -= 0.03999999910593033D;
		}
		moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9800000190734863D;
		this.motionY *= 0.9800000190734863D;
		this.motionZ *= 0.9800000190734863D;
		if (this.fallen)
		{
			this.motionX *= 0.2D;
			this.motionZ *= 0.2D;
		}
		if ((this.onGround) && (this.fallen))
		{
			if (BlockFalling.canFallThrough(this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.009999999776482582D, this.posZ))))
			{
				this.onGround = false;
				return;
			}
			setBlock();
		}
		else if (((this.fallTime > 100) && (!this.worldObj.isRemote) && ((blockpos1.getY() < 1) || (blockpos1.getY() > 256))) || (this.fallTime > 600))
		{
			if ((this.shouldDropItem) && (this.worldObj.getGameRules().getBoolean("doEntityDrops"))) {
				entityDropItem(new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0F);
			}
			setDead();
		}
		else if (Math.abs(this.motionX) + Math.abs(this.motionZ) + Math.abs(this.motionY) < 0.1D)
		{
			setBlock();
		}
	}

	private void setBlock()
	{
		Block block = this.fallTile.getBlock();
		BlockPos blockpos1 = new BlockPos(this);
		if ((this.worldObj.canBlockBePlaced(block, blockpos1, true, EnumFacing.UP, (Entity)null, (ItemStack)null)) && 
				(!BlockFalling.canFallThrough(this.worldObj.getBlockState(blockpos1.down()))) && 
				(this.worldObj.setBlockState(blockpos1, this.fallTile, 3)))
		{
			if ((block instanceof BlockFalling)) {
				((BlockFalling)block).onEndFalling(this.worldObj, blockpos1);
			}
			if ((this.tileEntityData != null) && ((block instanceof ITileEntityProvider)))
			{
				TileEntity tileentity = this.worldObj.getTileEntity(blockpos1);
				if (tileentity != null)
				{
					   NBTTagCompound nbttagcompound = tileentity.writeToNBT(new NBTTagCompound());
					for (String s : this.tileEntityData.getKeySet())
					{
						NBTBase nbtbase = this.tileEntityData.getTag(s);
						if ((!"x".equals(s)) && (!"y".equals(s)) && (!"z".equals(s))) {
							nbttagcompound.setTag(s, nbtbase.copy());
						}
					}
					tileentity.readFromNBT(nbttagcompound);
					tileentity.markDirty();
				}
			}
		}
		else if ((this.shouldDropItem) && (this.worldObj.getGameRules().getBoolean("doEntityDrops")))
		{
			entityDropItem(new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0F);
		}
		this.worldObj.removeEntity(this);
		GravityMethods.checkPos(this.worldObj, blockpos1);
	}

	public void fall(float distance, float damageMultiplier)
	{
		Block block = this.fallTile.getBlock();
		if (GameplayConfig.fallingBlocksDamage)
		{
			int i = MathHelper.ceiling_float_int(distance - 1.0F);
			if (i > 0)
			{
				List<Entity> list = Lists.newArrayList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox()));
				boolean flag = block == Blocks.ANVIL;
				DamageSource damagesource = flag ? DamageSource.anvil : DamageSource.fallingBlock;
				for (Entity entity : list) {
					entity.attackEntityFrom(damagesource, Math.min(MathHelper.floor_float(i * this.fallHurtAmount), this.fallHurtMax));
				}
				if ((flag) && (this.rand.nextFloat() < 0.05000000074505806D + i * 0.05D))
				{
					int j = ((Integer)this.fallTile.getValue(BlockAnvil.DAMAGE)).intValue();
					j++;
					if (j <= 2) {
						this.fallTile = this.fallTile.withProperty(BlockAnvil.DAMAGE, Integer.valueOf(j));
					}
				}
			}
		}
	}
}
