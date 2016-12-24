package wtf.utilities.wrappers;



import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import wtf.config.GameplayConfig;
import wtf.gameplay.CustomExplosion;
import wtf.gameplay.ExploderEntity;
import wtf.gameplay.GravityMethods;
import wtf.gameplay.StoneFractureMethods;
import wtf.init.BlockSets;

public class ExpVec extends Vec{

	private final World world;
	private double str;
	float attenuation = 0.75F;



	private final CustomExplosion explosion;
	int waterHash = Material.WATER.hashCode();
	int lavaHash = Material.LAVA.hashCode();

	public ExpVec(World world, BlockPos pos, double x, double y, double z, double vecStr, CustomExplosion explosion) {
		super(pos, x, y, z);
		this.world = world;
		this.str = vecStr;
		this.explosion = explosion;

	}

	IBlockState air = Blocks.AIR.getDefaultState();
	public boolean increment(){

		if (!hasNext()){
			return false;
		}
		BlockPos pos = this.next();

		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		float resistance = block.getExplosionResistance(explosion.sourceEntity);
		int hash = state.getMaterial().hashCode();

		if (hash == waterHash || hash == lavaHash){
			world.setBlockState(pos, air, 2);
		}
		else {

			if (BlockSets.explosiveBlocks.containsKey(block)) {
				world.setBlockState(pos, air, 2);
				ExploderEntity entity = new ExploderEntity(world, pos, BlockSets.explosiveBlocks.get(block));
				world.spawnEntityInWorld(entity);
			}


			double atomize = resistance * GameplayConfig.expLvlAatomize;

			float chance = (float) ((atomize-str)/atomize);
			if (chance > 1F) {
				str -= resistance;
				block.onBlockDestroyedByExplosion(world, pos, explosion);
				world.setBlockState(pos, air, 2);
				explosion.spawnExtraParticles(pos, 2F, pos.getX(),pos.getY(), pos.getZ());
			} 
			else if (str > resistance*GameplayConfig.expLvlDrop) {
				str -= resistance;
				block.onBlockDestroyedByExplosion(world, pos, explosion);
				if (block.canDropFromExplosion(explosion)) {
					block.dropBlockAsItemWithChance(world, pos, state, chance, 0);
				}
				else {
					block.onBlockDestroyedByExplosion(world, pos, explosion);
				}
				world.setBlockState(pos, air, 2);
			} 
			else if (GameplayConfig.explosionFractures){
				str -= resistance/3;
				StoneFractureMethods.fracStone(world, pos, world.getBlockState(pos));
			}
		}
		str -= attenuation;
		return true;
	}

	public double getStr(){
		return str;
	}

	public double strX(){
		return str*vecX;
	}
	public double strY(){
		return str*vecY;
	}

	public double strZ(){
		return str*vecZ;
	}
	public boolean hasNext(){
		return str > 0;
	}

}
