package wtf.cavebiomes.worldgeneration.dungeontypes.mob;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.cavebiomes.worldgeneration.AbstractDungeonType;
import wtf.cavebiomes.worldgeneration.CaveBiomeGenMethods;
import wtf.core.init.BlockSets.Modifier;
import wtf.core.utilities.wrappers.CaveListWrapper;
import wtf.core.utilities.wrappers.CavePosition;

public class DungeonClassicMob extends AbstractDungeonType{

	private IBlockState block = null;
	private Modifier modifier = null;
	private final String mob;
	private int spawnRate = 4;
	private IBlockState stripe = null;

	public DungeonClassicMob(String name, IBlockState state, String mobspawner) {
		super(name, 0, 0);
		block = state;
		mob = mobspawner;
	}
	public DungeonClassicMob(String name, Modifier modifier, String mobspawner) {
		super(name, 0, 0);
		this.modifier = modifier;
		mob = mobspawner;
	}
	public DungeonClassicMob(String name, int ceilingAddonPercentChance, int floorAddonPercentChance, IBlockState state, String mobspawner) {
		super(name, ceilingAddonPercentChance, floorAddonPercentChance);
		block = state;
		mob = mobspawner;
	}

	@Override
	public boolean canGenerateAt(CaveBiomeGenMethods gen, CaveListWrapper cave) {
		return cave.getSizeX() > 6 && cave.getSizeZ() > 6 && cave.getAvgCeiling() - cave.getAvgFloor() > 3;
	}

	@Override
	public void generateCenter(CaveBiomeGenMethods gen, Random rand, CavePosition pos, float depth) {
		gen.spawnVanillaSpawner(pos.getFloorPos().up(), mob, spawnRate);
		
	}


	@Override
	public void generateCeiling(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		if (block != null){
			gen.replaceBlock(pos, block);
		}
		else if (modifier != null){
			gen.transformBlock(pos, modifier);
		}
		
	}

	@Override
	public void generateFloor(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth) {
		if (block != null){
			gen.replaceBlock(pos, block);
		}
		else if (modifier != null){
			gen.transformBlock(pos, modifier);
		}
		
	}


	@Override
	public void generateWall(CaveBiomeGenMethods gen, Random random, BlockPos pos, float depth, int height) {

		if (stripe != null && height == 3){
			gen.replaceBlock(pos, stripe);
		}
		else {

			if (block != null){
				gen.replaceBlock(pos, block);
			}
			else if (modifier != null){
				gen.transformBlock(pos, modifier);
			}
		}
		
	}
	
	public DungeonClassicMob setSpawnRate(int rate){
		this.spawnRate = rate;
		return this;
	}

	
	public DungeonClassicMob setStripe(IBlockState state){
		this.stripe = state;
		return this;
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
