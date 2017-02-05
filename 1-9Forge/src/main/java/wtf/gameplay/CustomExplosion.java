package wtf.gameplay;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import wtf.config.GameplayConfig;
import wtf.utilities.wrappers.ExpVec;

public class CustomExplosion extends Explosion{

	private final World world;

	Random random = new Random();
	public Entity sourceEntity;
	boolean isSmoking = false;
	public boolean flaming = false;

	public HashMap<BlockPos, Block> fluids = new HashMap<BlockPos, Block>(); 

	int counterMod;

	//create a list of explosion vectors
	HashSet<ExpVec> vecList = new HashSet<ExpVec>();

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

	}
	
	public CustomExplosion(Entity entity, World world, Vec3d vec3d, float str, boolean fire) {
		super(world, entity, vec3d.xCoord, vec3d.yCoord, vec3d.zCoord, str, false, false);
		flaming = fire;
		isSmoking = fire;
		this.world = world;
		BlockPos origin = new BlockPos(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);
		this.sourceEntity = entity;
		this.counterMod = 0;
		populateVectorList(new BlockPos(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord), str);
		doExplosionB(origin, str);
		
		
		
	}

	/**
	 * Populates the affectedBlocksList with any blocks that should be affected
	 * by this explosion
	 */
	protected void populateVectorList(BlockPos origin, float baseStr) {

		float xpos = getModifier(origin.east());
		float xneg = getModifier(origin.west());
		float ypos = getModifier(origin.up());
		float yneg = this.sourceEntity instanceof EntityCreeper ? (float) (getModifier(origin.down())/GameplayConfig.creeperUpConstant) :  getModifier(origin.down()); 
		float zpos = getModifier(origin.north());
		float zneg = getModifier(origin.south());

		float ftotal = xpos + xneg + ypos + yneg + zpos + zneg;

		//System.out.println(" y neg = " + yneg);

		xpos = setModifier(xpos, ftotal) * baseStr;
		xneg = setModifier(xneg, ftotal) * baseStr;
		ypos = setModifier(ypos, ftotal) * baseStr;
		yneg = setModifier(yneg, ftotal) * baseStr;
		zpos = setModifier(zpos, ftotal) * baseStr;
		zneg = setModifier(zneg, ftotal) * baseStr;

		//System.out.println("north = " + zpos + " south " + zneg);
		//System.out.println("east = " + xpos + " west " + xneg);

		//System.out.println("y+ " + ypos + " y- " + yneg);

		int xMin = MathHelper.clamp_int(MathHelper.floor_float(-4 * xneg), -12, -4);
		int xMax = MathHelper.clamp_int(MathHelper.floor_float(4 * xpos), 4, 12);
		int yMin = MathHelper.clamp_int(MathHelper.floor_float(-4 * yneg),-12, -4);
		int yMax = MathHelper.clamp_int(MathHelper.floor_float(4 * ypos), 4, 12);
		int zMin = MathHelper.clamp_int(MathHelper.floor_float(-4 * zneg),-12, -4);
		int zMax = MathHelper.clamp_int(MathHelper.floor_float(4 * zpos), 4, 12);

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

	public void incrementVectorList() {
		
			//System.out.println("List shuffled");
			Iterator<ExpVec> iterator = vecList.iterator();
			while (iterator.hasNext()){
				ExpVec vec = iterator.next();
				
				if (vec.increment()){
					this.getAffectedBlockPositions().add(vec.pos());
					this.getAffectedBlockPositions().add(vec.pos().up());
					this.getAffectedBlockPositions().add(vec.pos().down());
					this.getAffectedBlockPositions().add(vec.pos().east());
					this.getAffectedBlockPositions().add(vec.pos().west());
					this.getAffectedBlockPositions().add(vec.pos().north());
					this.getAffectedBlockPositions().add(vec.pos().south());
					BlockPos end = vec.pos();
					List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(end));

					float damage = (float)vec.getStr()*7.5F;
					if (list.size() > 0 && vec.getStr() > 0){

						for (Entity entity : list){
							double d11 = 1;
							if (entity instanceof EntityLivingBase){
								d11 = EnchantmentProtection.getBlastDamageReduction((EntityLivingBase)entity, damage);
							}
							if (vec.getStr() > 0.5){
								entity.attackEntityFrom(DamageSource.causeExplosionDamage(this), (float) (d11*damage*GameplayConfig.explosionDamageMod));
							}							
							entity.addVelocity(vec.strX()/10*GameplayConfig.explosionForceMod, vec.strY()/10*GameplayConfig.explosionForceMod, vec.strZ()/10*GameplayConfig.explosionForceMod);
							entity.velocityChanged=true;

						}

					}
				}
				else {
					iterator.remove();
				}
			}
				
	}
	
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


	private float getModifier(BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		float mod = MathHelper.sqrt_float(MathHelper.sqrt_float(1 / (1 + state.getBlock().getExplosionResistance(this.sourceEntity) * 5F * state.getBlockHardness(world, pos))));
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

	int airHash = Blocks.AIR.hashCode();
	
	
	void update(){
		for (BlockPos pos: this.getAffectedBlockPositions()){
			//System.out.println("neightbor changed");
			IBlockState iblockstate = world.getBlockState(pos);
			iblockstate.neighborChanged(world, pos, iblockstate.getBlock());
			if (GameplayConfig.gravity){
				GravityMethods.checkPos(world, pos);
			}
		}
		this.clearAffectedBlockPositions();
	}



}
