package wtf.worldgen.generators.queuedgen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class QMobSpawner implements QueuedGenerator {

	private final World world;
	private final BlockPos pos;
	private final String entity;
	private final int count;
	
	public QMobSpawner(World world, BlockPos pos, String entity, int count){
		this.world = world;
		this.pos = pos;
		this.entity = entity;
		this.count = count;
	}
	
	@Override
	public IBlockState getBlockState(IBlockState oldstate) {
		world.setBlockState(pos, Blocks.MOB_SPAWNER.getDefaultState());
		TileEntityMobSpawner spawner = (TileEntityMobSpawner)world.getTileEntity(pos);
		if (spawner != null){
			spawner.getSpawnerBaseLogic().setEntityName(entity);
			NBTTagCompound nbt = new NBTTagCompound();
			spawner.writeToNBT(nbt);
			nbt.setShort("SpawnCount",(short)count);
			nbt.setShort("SpawnRange", (short)2);
			spawner.readFromNBT(nbt);
		}
		else{
			System.out.println("VanillaGen: SpawnVanillaSpawner- failed to set spawner entity");
		}
		return null;
	}

}
