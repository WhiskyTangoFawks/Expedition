package wtf.core.gameplay;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
import wtf.core.config.GameplayConfig;
import wtf.core.init.BlockSets;

public class CustomExplosion extends Explosion{

	World world;
	double originX;
	double originY;
	double originZ;
	float baseStr;
	Random random = new Random();
	Entity sourceEntity;
	boolean isSmoking = false;

	int top;
	int bottom;
	int north;
	int south;
	int east;
	int west;

	int counterMod;

	float motionFactor = 2F;
	protected Map<Entity, Vec3d> affectedPlayers = new HashMap<Entity, Vec3d>();
	public HashSet<BlockPos> allBlocks = new HashSet<BlockPos>();

	public CustomExplosion(Entity entity, World world, Vec3d vec3d, float str) {
		super(world, entity, vec3d.xCoord, vec3d.yCoord, vec3d.zCoord, str, false, false);
		this.world = world;
		this.originX = vec3d.xCoord;
		this.originY = vec3d.yCoord;
		this.originZ = vec3d.zCoord;
		this.baseStr = str;
		this.sourceEntity = entity;
		this.counterMod = 0;

		populateAffectedBlocksList();
		affectEntitiesWithin();
		doExplosionB();
		this.notifyClients();
		doPropagate(world);
	}

	HashMap<BlockPos, Float> toExplode = new HashMap<BlockPos, Float>();
	HashSet<BlockPos> toAtomize = new HashSet<BlockPos>();
	HashSet<BlockPos> toDrop = new HashSet<BlockPos>();
	HashSet<BlockPos> toFracture = new HashSet<BlockPos>();

	/**
	 * Populates the affectedBlocksList with any blocks that should be affected
	 * by this explosion
	 */
	protected void populateAffectedBlocksList() {

		// These define the boundaries of the cube that the explosion happens
		// within- and are set based on the actual explosion
		top = 0;
		bottom = 0;
		north = 0;
		south = 0;
		east = 0;
		west = 0;

		// I need to set these based on the maximum dimensions of hte explosion-
		// which is the distance it would travel through air

		float xpos = getModifier(world.getBlockState(new BlockPos(originX + 1, originY, originZ)).getBlock());
		float xneg = getModifier(world.getBlockState(new BlockPos(originX - 1, originY, originZ)).getBlock());
		float ypos = getModifier(world.getBlockState(new BlockPos(originX, originY + 1, originZ)).getBlock());
		float yneg = getModifier(world.getBlockState(new BlockPos(originX, originY - 1, originZ)).getBlock());
		float zpos = getModifier(world.getBlockState(new BlockPos(originX, originY, originZ + 1)).getBlock());
		float zneg = getModifier(world.getBlockState(new BlockPos(originX, originY, originZ - 1)).getBlock());

		float ftotal = xpos + xneg + ypos + yneg + zpos + zneg;

		xpos = setModifier(xpos, ftotal) * baseStr;
		xneg = setModifier(xneg, ftotal) * baseStr;
		ypos = setModifier(ypos, ftotal) * baseStr;
		yneg = setModifier(yneg, ftotal) * baseStr;
		zpos = setModifier(zpos, ftotal) * baseStr;
		zneg = setModifier(zneg, ftotal) * baseStr;

		if (sourceEntity instanceof EntityCreeper) {
			yneg = yneg / 2;
		}

		int xMin = MathHelper.clamp_int(MathHelper.floor_float(-4 * xneg), -8, -4);
		int xMax = MathHelper.clamp_int(MathHelper.floor_float(4 * xpos), 4, 8);
		int yMin = MathHelper.clamp_int(MathHelper.floor_float(-4 * yneg),-8, -4);
		int yMax = MathHelper.clamp_int(MathHelper.floor_float(4 * ypos), 4, 8);
		int zMin = MathHelper.clamp_int(MathHelper.floor_float(-4 * zneg),-8, -4);
		int zMax = MathHelper.clamp_int(MathHelper.floor_float(4 * zpos), 4, 8);
		;

		// check adjacent blocks, and modify directional strength
		// add here

		for (int xloop = xMin; xloop < xMax + 1; xloop++) {
			for (int yloop = yMin; yloop < yMax + 1; yloop++) {
				for (int zloop = zMin; zloop < zMax + 1; zloop++) {

					// This checks if it's an edge of the cube
					if (xloop == xMin || xloop == xMax || yloop == yMin || yloop == yMax || zloop == zMin
							|| zloop == zMax) {
						// the values to increment along the ray each loop
						double incX = (0 + xloop);
						double incY = (0 + yloop);
						double incZ = (0 + zloop);

						// length of the vector
						double vectorLength = Math.sqrt(incX * incX + incY * incY + incZ * incZ);

						// setting the values
						incX /= (vectorLength);
						incY /= (vectorLength);
						incZ /= (vectorLength);

						// explosion origin- 0.5 makes it so that it starts in
						// the center of a block
						double currentX = originX + 0.5;
						double currentY = originY + 0.5;
						double currentZ = originZ + 0.5;

						float xcomp;
						float ycomp;
						float zcomp;

						// selecting between pos and neg vector strength
						// components
						if (xloop > 0) {
							xcomp = xpos;
						} else if (xloop < 0) {
							xcomp = xneg;
						} else {
							xcomp = 0F;
						}

						if (zloop > 0) {
							zcomp = zpos;
						} else if (zloop < 0) {
							zcomp = zneg;
						} else {
							zcomp = 0F;
						}

						if (yloop > 0) {
							ycomp = ypos;
						} else if (yloop < 0) {
							ycomp = yneg;
						} else {
							ycomp = 0F;
						}

						float absIncX = MathHelper.abs((float) incX);
						float absIncY = MathHelper.abs((float) incY);
						float absIncZ = MathHelper.abs((float) incZ);

						double total = absIncX + absIncY + absIncZ;

						// setting the vector strength, as a sum of each
						// component times the corresponding increment,

						double vector = (xcomp * (absIncX / total) + ycomp * (absIncY / total)
								+ zcomp * (absIncZ / total)) * (0.7 + random.nextFloat() * 0.6);

						//
						float attenuation = 0.75F;

						for (double loop = 0.3; vector > 0; vector -= loop * attenuation) {
							int x = MathHelper.floor_double(currentX);
							int y = MathHelper.floor_double(currentY);
							int z = MathHelper.floor_double(currentZ);

							Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
							BlockPos chunkposition = new BlockPos(x, y, z);
							float resistance = block.getExplosionResistance(sourceEntity);


							if (checkPropagate(world, chunkposition)){
								toAtomize.add(chunkposition);
							}
							else if (vector > resistance * 7) {
								toAtomize.add(chunkposition);
								vector = vector - 2 * resistance; // again
							} else if (vector > resistance) {
								toDrop.add(chunkposition);
								vector = vector - resistance;
							} else if (GameplayConfig.explosionFractures) {
								toFracture.add(chunkposition);
								vector = vector - resistance / 3;
							}
							
							currentX += incX;
							currentY += incY;
							currentZ += incZ;

						}
						// this set checks where this ray ends, against the
						// current max value in that direction
						// this is used later to set the bounding box in which
						// to check for entities
						north = (currentX > north) ? MathHelper.ceiling_double_int(currentX) : north;
						south = (currentX < south) ? MathHelper.floor_double(currentX) : south;

						top = (currentY > top) ? MathHelper.ceiling_double_int(currentY) : top;
						bottom = (currentY < top) ? MathHelper.floor_double(currentY) : bottom;

						east = (currentZ > east) ? MathHelper.ceiling_double_int(currentZ) : east;
						west = (currentZ < west) ? MathHelper.floor_double(currentZ) : west;

					}
				}
			}
		}
		this.getAffectedBlockPositions().addAll(allBlocks);
	}

	/**
	 * Affects all entities within the explosion, causing damage if flagged to
	 * do so
	 */
	protected void affectEntitiesWithin() {

		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(sourceEntity, new AxisAlignedBB(north, top, east, south, bottom, west));
		if (sourceEntity instanceof EntityPlayer){
			list.add(sourceEntity);
		}

		//I have a variable box for explosin strength... but not one for damage???  or do I???

		Vec3d vec3d = new Vec3d(originX, originY, originZ);


		int diameter = (MathHelper.abs_int(top - bottom) + MathHelper.abs_int(north - south)
		+ MathHelper.abs_int(east - west)) / 3;

		for (int k2 = 0; k2 < list.size(); ++k2) {
			Entity entity = list.get(k2);
			double d7 = entity.getDistance(originX, originY, originZ) / diameter;

			if (d7 <= 1.0D) {
				double d0 = entity.posX - originX;
				double d1 = entity.posY + entity.getEyeHeight() - originY;
				double d2 = entity.posZ - originZ;
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

	public void doExplosionB() {
		this.world.playSound((EntityPlayer)null, originX, originY, originZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
		if (baseStr >= 2.0F && isSmoking) {
			this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, originX, originY, originZ, 1.0D, 0.0D, 0.0D, new int[0]);

		} else {
			this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, originX, originY, originZ, 1.0D, 0.0D, 0.0D, new int[0]);
		}

		Iterator<BlockPos> iterator;
		BlockPos pos = null;

		Block block;
		iterator = toAtomize.iterator();
		while (iterator.hasNext()) {
			pos = iterator.next();
			block = world.getBlockState(pos).getBlock();
			world.setBlockToAir(pos);
			spawnExtraParticles(pos.getX(),pos.getY(), pos.getZ());

		}
		iterator = toDrop.iterator();
		while (iterator.hasNext()) {
			pos = iterator.next();
			block = world.getBlockState(pos).getBlock();
			if (block.canDropFromExplosion(this)) {
				block.dropBlockAsItemWithChance(world, pos, world.getBlockState(pos), 1.0F / baseStr, 0);
			}
			world.setBlockToAir(pos);
		}
		iterator = toFracture.iterator();
		while (iterator.hasNext()) {
			BlockPos fracpos = iterator.next();
			StoneFractureMethods.fracStone(world, fracpos);
			GravityMethods.dropBlock(world, fracpos.getX(), fracpos.getY(), fracpos.getZ(), false);
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

	private HashMap<BlockPos, Float> newExplosions = new HashMap<BlockPos, Float>();

	private boolean checkPropagate(World world, BlockPos pos){
		Block block = world.getBlockState(pos).getBlock();
		if (BlockSets.explosiveBlocks.containsKey(block)) {
			newExplosions.put(pos, BlockSets.explosiveBlocks.get(block));
			return true;
		}
		return false;
	}

	private void doPropagate(World world){
		Iterator<BlockPos> iterator = newExplosions.keySet().iterator();
		while (iterator.hasNext()){
			BlockPos pos = iterator.next();
			world.setBlockToAir(pos);
			new CustomExplosion(sourceEntity, world, new Vec3d(pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5), newExplosions.get(pos));	
		}
		newExplosions = null;
	}

	/**
	 * Actually explodes the block and spawns particles if allowed
	 */
	private void spawnExtraParticles(int i, int j, int k) {
		double d0 = i + world.rand.nextFloat();
		double d1 = j + world.rand.nextFloat();
		double d2 = k + world.rand.nextFloat();
		double d3 = d0 - originX;
		double d4 = d1 - originY;
		double d5 = d2 - originZ;
		double d6 = MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
		d3 /= d6;
		d4 /= d6;
		d5 /= d6;
		double d7 = 0.5D / (d6 / baseStr + 0.1D);
		d7 *= world.rand.nextFloat() * world.rand.nextFloat() + 0.3F;
		d3 *= d7;
		d4 *= d7;
		d5 *= d7;
		this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (d0 + originX * 1.0D) / 2.0D, (d1 + originY * 1.0D) / 2.0D, (d2 + originZ * 1.0D) / 2.0D, d3, d4, d5);

		world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, d3, d4, d5);

	}



	protected void notifyClients() {
		
		
		if (!world.isRemote) {
			Iterator<EntityPlayer> iterator = world.playerEntities.iterator();
			while (iterator.hasNext()) {
				EntityPlayer player = iterator.next();
				if (player.getDistanceSq(originX, originY, originZ) < 4096.0D) {
					SPacketExplosion exp = new SPacketExplosion(originX, originY, originZ, this.baseStr, this.getAffectedBlockPositions(), this.getPlayerKnockbackMap().get(player));
					//exp.processPacket(handler);
					//((EntityPlayer)player)playerNetServerHandler.sendPacket();
				}
			}
		}
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
