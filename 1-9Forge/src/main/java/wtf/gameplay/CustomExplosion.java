package wtf.gameplay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import wtf.core.init.BlockSets;
import wtf.core.utilities.wrappers.ExpVec;

public class CustomExplosion extends Explosion{

	private final World world;

	Random random = new Random();
	public Entity sourceEntity;
	boolean isSmoking = false;

	int top;
	int bottom;
	int north;
	int south;
	int east;
	int west;

	int counterMod;

	//create a list of explosion vectors
	ArrayList<ExpVec> vecList = new ArrayList<ExpVec>();

	float motionFactor = 2F;
	protected Map<Entity, Vec3d> affectedPlayers = new HashMap<Entity, Vec3d>();

	public CustomExplosion(Entity entity, World world, Vec3d vec3d, float str) {
		super(world, entity, vec3d.xCoord, vec3d.yCoord, vec3d.zCoord, str, false, false);
		this.world = world;
		BlockPos origin = new BlockPos(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);
		
		this.sourceEntity = entity;
		this.counterMod = 0;

		
		populateVectorList(new BlockPos(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord), str);
		doExplosionB(origin, str);
		incrementVectorList();
		affectEntitiesWithin(origin);
		
		//this.notifyClients();

	}

	/**
	 * Populates the affectedBlocksList with any blocks that should be affected
	 * by this explosion
	 */
	protected void populateVectorList(BlockPos origin, float baseStr) {

		// These define the boundaries of the cube that the explosion happens
		// within- and are set based on the actual explosion
		top = 0;
		bottom = 0;
		north = 0;
		south = 0;
		east = 0;
		west = 0;

		float xpos = getModifier(world.getBlockState(origin.east()).getBlock());
		float xneg = getModifier(world.getBlockState(origin.west()).getBlock());
		float ypos = getModifier(world.getBlockState(origin.up()).getBlock());
		float yneg = getModifier(world.getBlockState(origin.down()).getBlock());
		float zpos = getModifier(world.getBlockState(origin.north()).getBlock());
		float zneg = getModifier(world.getBlockState(origin.south()).getBlock());

		float ftotal = xpos + xneg + ypos + yneg + zpos + zneg;

		xpos = setModifier(xpos, ftotal) * baseStr;
		xneg = setModifier(xneg, ftotal) * baseStr;
		ypos = setModifier(ypos, ftotal) * baseStr;
		yneg = setModifier(yneg, ftotal) * baseStr;
		zpos = setModifier(zpos, ftotal) * baseStr;
		zneg = setModifier(zneg, ftotal) * baseStr;


		int xMin = MathHelper.clamp_int(MathHelper.floor_float(-4 * xneg), -8, -4);
		int xMax = MathHelper.clamp_int(MathHelper.floor_float(4 * xpos), 4, 8);
		int yMin = MathHelper.clamp_int(MathHelper.floor_float(-4 * yneg),-8, -4);
		int yMax = MathHelper.clamp_int(MathHelper.floor_float(4 * ypos), 4, 8);
		int zMin = MathHelper.clamp_int(MathHelper.floor_float(-4 * zneg),-8, -4);
		int zMax = MathHelper.clamp_int(MathHelper.floor_float(4 * zpos), 4, 8);

		for (int xloop = xMin; xloop < xMax + 1; xloop++) {
			for (int yloop = yMin; yloop < yMax + 1; yloop++) {
				for (int zloop = zMin; zloop < zMax + 1; zloop++) {

					// This checks if it's an edge of the cube
					if (xloop == xMin || xloop == xMax || yloop == yMin || yloop == yMax || zloop == zMin
							|| zloop == zMax) {

						// the values to increment along the ray each loop


						// length of the vector
						double vectorLength = Math.sqrt(xloop * xloop + yloop * yloop + zloop * zloop);

						// setting the values
						double incX = xloop/vectorLength;
						double incY = yloop/vectorLength;
						double incZ = zloop/vectorLength;

						// selecting between pos and neg vector strength
						float xcomp = xloop == 0 ? 0 : xloop > 0 ? xpos: xneg;
						float ycomp = yloop == 0 ? 0 : yloop > 0 ? ypos: yneg;
						float zcomp = zloop == 0 ? 0 : zloop > 0 ? zpos: zneg;

						double absIncX = Math.abs(incX);
						double absIncY = Math.abs(incY);
						double absIncZ = Math.abs(incZ);

						double total = absIncX + absIncY + absIncZ;

						// setting the vector strength, as a sum of each
						// component times the corresponding increment, plus a random component
						double vector = (xcomp * (absIncX / total) + ycomp * (absIncY / total)
								+ zcomp * (absIncZ / total)) * (0.7 + random.nextFloat() * 0.6);

						//add the vector info to a vector list
						vecList.add(new ExpVec(world, origin, incX, incY, incZ, vector, this));
						//System.out.println("vector created");

					}
				}
			}
		}
	}
	
	private void incrementVectorList() {
		while (!vecList.isEmpty()){
			Collections.shuffle(vecList);
			//System.out.println("List shuffled");
			for (int loop = 0; loop < vecList.size(); loop++){
				
				ExpVec vec = vecList.get(loop);
				if (vec.increment()){
					this.getAffectedBlockPositions().add(vec.pos());
				}
				else {
					// this set checks where this ray ends, against the
					// current max value in that direction
					// this is used later to set the bounding box in which
					// to check for entities
					BlockPos end = vec.pos();
					north = (end.getZ() > north) ? end.getZ() : north;
					south = (end.getZ() < south) ? end.getZ() : south;

					top = (end.getY() > top) ? end.getY() : top;
					bottom = (end.getY() < top) ? end.getY() : bottom;

					east = (end.getX() > east) ? end.getX() : east;
					west = (end.getX() < west) ? end.getX() : west;
					vecList.remove(loop);
				}
			}
		}		
	}

	/**
	 * Affects all entities within the explosion, causing damage if flagged to
	 * do so
	 */
	
	protected void affectEntitiesWithin(BlockPos origin) {

		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(sourceEntity, new AxisAlignedBB(north, top, east, south, bottom, west));
		if (sourceEntity instanceof EntityPlayer){
			list.add(sourceEntity);
		}

		//I have a variable box for explosin strength... but not one for damage???  or do I???

		Vec3d vec3d = new Vec3d(origin.getX(), origin.getY(), origin.getZ());


		int diameter = (MathHelper.abs_int(top - bottom) + MathHelper.abs_int(north - south)
		+ MathHelper.abs_int(east - west)) / 3;

		for (int k2 = 0; k2 < list.size(); ++k2) {
			Entity entity = list.get(k2);
			double d7 = entity.getDistance(origin.getX(), origin.getY(), origin.getZ()) / diameter;

			if (d7 <= 1.0D) {
				double d0 = entity.posX - origin.getX();
				double d1 = entity.posY + entity.getEyeHeight() - origin.getY();
				double d2 = entity.posZ - origin.getZ();
				double d8 = MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);

				if (d8 != 0.0D) {
					d0 /= d8;
					d1 /= d8;
					d2 /= d8;
					double d9 = world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
					double d10 = (1.0D - d7) * d9;
					float damage = ((int) ((d10 * d10 + d10) / 2.0D * 8.0D * diameter + 1.0D));
					entity.attackEntityFrom(DamageSource.causeExplosionDamage(this), damage);

					if (entity instanceof EntityLivingBase){
						double d11 = EnchantmentProtection.getBlastDamageReduction((EntityLivingBase)entity, d10);
						entity.motionX += d0 * motionFactor*d11;
						entity.motionY += d1 * motionFactor*d11;
						entity.motionZ += d2 * motionFactor*d11;
					}
					if (entity instanceof EntityPlayer){
						EntityPlayer entityplayer = (EntityPlayer) entity;
						if (!entityplayer.isSpectator() && (!entityplayer.isCreative() || !entityplayer.capabilities.isFlying))
						{
							this.getPlayerKnockbackMap().put(entityplayer, new Vec3d(d0 * d10, d7 * d10, d9 * d10));
						}
					}
				}
			}
		}
	}

	/**
	 * Does the second part of the explosion (sound, particles, drop spawn)
	 */

	public void doExplosionB(BlockPos origin, float baseStr) {
		this.world.playSound((EntityPlayer)null, origin.getX(), origin.getY(), origin.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
		if (baseStr >= 2.0F && isSmoking) {
			this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, origin.getX(), origin.getY(), origin.getZ(), 1.0D, 0.0D, 0.0D, new int[0]);

		} else {
			this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, origin.getX(), origin.getY(), origin.getZ(), 1.0D, 0.0D, 0.0D, new int[0]);
		}
		/*
		 * if (isFlaming) { iterator = affectedBlockPositions.iterator(); while
		 * (iterator.hasNext()) { chunkposition =
		 * (BlockPos)iterator.next(); i = chunkposition.getX(); j =
		 * chunkposition.getY(); k = chunkposition.getZ(); block =
		 * worldObj.getBlockState(new BlockPos(i, j, k); Block block1 = worldObj.getBlockState(new BlockPos(i, j -
		 * 1, k); // func_149730_j() returns block.opaque if (block ==
		 * Blocks.air && block1.func_149730_j() && rand.nextInt(3) == 0) {
		 * worldObj.setBlock(i, j, k, Blocks.fire); } } }
		 */

	}


	/**
	 * Actually explodes the block and spawns particles if allowed
	 */
	public void spawnExtraParticles(BlockPos origin, float baseStr, int i, int j, int k) {
		double d0 = i + world.rand.nextFloat();
		double d1 = j + world.rand.nextFloat();
		double d2 = k + world.rand.nextFloat();
		double d3 = d0 - origin.getX();
		double d4 = d1 - origin.getY();
		double d5 = d2 - origin.getZ();
		double d6 = MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
		d3 /= d6;
		d4 /= d6;
		d5 /= d6;
		double d7 = 0.5D / (d6 / baseStr + 0.1D);
		d7 *= world.rand.nextFloat() * world.rand.nextFloat() + 0.3F;
		d3 *= d7;
		d4 *= d7;
		d5 *= d7;
		this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (d0 + origin.getX() * 1.0D) / 2.0D, (d1 + origin.getY() * 1.0D) / 2.0D, (d2 + origin.getZ() * 1.0D) / 2.0D, d3, d4, d5);

		world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, d3, d4, d5);

	}


	private float getModifier(Block block) {
		float mod = MathHelper.sqrt_float(MathHelper.sqrt_float(1 / (1 + block.getExplosionResistance(null) * 5F * block.getBlockHardness(null, world, null))));
		if (mod < 1) {
			counterMod++;
			return mod;
		} else {
			return 0.95F;
		}
	}

	private float setModifier(float dir, float totalAssigned) {
		float ret  = dir / (totalAssigned/6F);
		return ret;

	}

}
