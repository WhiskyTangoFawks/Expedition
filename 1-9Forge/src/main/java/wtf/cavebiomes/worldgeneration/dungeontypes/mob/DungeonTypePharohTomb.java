package wtf.cavebiomes.worldgeneration.dungeontypes.mob;

import java.util.Random;

import net.minecraft.block.BlockRedSandstone;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.cavebiomes.worldgeneration.AbstractDungeonType;
import wtf.cavebiomes.worldgeneration.CaveBiomeGenMethods;
import wtf.core.Core;
import wtf.core.entities.zombie.EntityMummy;
import wtf.core.utilities.wrappers.CaveListWrapper;
import wtf.core.utilities.wrappers.CavePosition;

public class DungeonTypePharohTomb extends AbstractDungeonType{

	public DungeonTypePharohTomb(String name) {
		super(name, 0, 0);
	}
	
	@Override
	public boolean canGenerateAt(CaveBiomeGenMethods gen, CaveListWrapper cave) {
		return cave.getSizeX() > 6 && cave.getSizeZ() > 6 && cave.getAvgCeiling() - cave.getAvgFloor() > 4;
	}
	
	@Override
	public void generateCenter(CaveBiomeGenMethods gen, Random rand, CavePosition pos, float depth) {
		
		gen.spawnVanillaSpawner(pos.getFloorPos(), Core.coreID+".ZombieMummy", 3);
		gen.replaceBlock(pos.getFloorPos().up(), Blocks.GOLD_BLOCK.getDefaultState());
		EntityMummy pharaoh = new EntityMummy(gen.chunk.getWorld(), true);
		pharaoh.setLocationAndAngles(pos.getFloorPos().getX(), pos.getFloorPos().up(2).getY(), pos.getFloorPos().getZ(), 0, 0);
		gen.chunk.getWorld().spawnEntityInWorld(pharaoh);

		//mummy spawner, with a block of solid gold on top
		
	}

	@Override
	public void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		gen.replaceBlock(pos, Blocks.RED_SANDSTONE.getDefaultState());
		
	}

	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		gen.replaceBlock(pos, Blocks.RED_SANDSTONE.getDefaultState());
		
	}

	@Override
	public void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height) {
		if (height == 3){
			gen.replaceBlock(pos, Blocks.RED_SANDSTONE.getDefaultState().withProperty(BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.CHISELED));
		}
		else {
			gen.replaceBlock(pos, Blocks.RED_SANDSTONE.getDefaultState());
		} 	
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
