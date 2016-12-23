package wtf.worldgen.subcaves.mob;

import java.util.Random;

import net.minecraft.block.BlockStoneBrick;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.AbstractDungeonType;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class DungeonTypeDerangedGolem extends AbstractDungeonType {


	public DungeonTypeDerangedGolem(String name) {
		super(name, 0, 0, false);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canGenerateAt(CaveBiomeGenMethods gen, CaveListWrapper cave) {
		return isSize(cave, 8) && isHeight(cave, 6);
	}
	
	@Override
	public void generateCenter(CaveBiomeGenMethods gen, Random rand, CavePosition pos, float depth) {
		//anvil in center, with golem spawner underneath
		//gen.spawnVanillaSpawner(pos.getFloorPos(), Core.coreID+".ZombieMummy", 3);
		gen.replaceBlock(pos.getFloorPos().up(), Blocks.ANVIL.getDefaultState());
		
		EntityIronGolem mob = new EntityIronGolem(gen.chunk.getWorld());
		mob.tasks.taskEntries.clear();
		mob.targetTasks.taskEntries.clear();
		
		mob.setHomePosAndDistance(pos.getFloorPos().up(), 12);
		mob.tasks.addTask(1, new EntityAIAttackMelee(mob, 1.0D, true));
        mob.tasks.addTask(2, new EntityAIMoveTowardsTarget(mob, 0.9D, 32.0F));
		mob.targetTasks.addTask(3, new EntityAINearestAttackableTarget(mob, EntityPlayer.class, true));
		mob.tasks.addTask(4, new EntityAIMoveTowardsRestriction(mob, 1.0D));
		mob.tasks.addTask(5, new EntityAIWander(mob, 1.0D));
		mob.tasks.addTask(6, new EntityAIWatchClosest(mob, EntityPlayer.class, 16F));
		mob.tasks.addTask(7, new EntityAILookIdle(mob));

		mob.setLocationAndAngles(pos.getFloorPos().getX()+1, pos.getFloorPos().up(1).getY(), pos.getFloorPos().getZ(), 0, 0);
		gen.chunk.getWorld().spawnEntityInWorld(mob);

	}

	@Override
	public void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		gen.replaceBlock(pos, Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED));
		
	}

	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		gen.replaceBlock(pos, Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED));
		
	}

	@Override
	public void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height) {
		gen.replaceBlock(pos, Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED));
		
	}

	@Override
	public void generateCeilingAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateFloorAddons(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		// TODO Auto-generated method stub
		
	}
}
