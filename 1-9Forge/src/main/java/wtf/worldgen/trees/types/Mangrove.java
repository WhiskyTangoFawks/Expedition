package wtf.worldgen.trees.types;

import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import wtf.worldgen.trees.TreeVars;

public class Mangrove extends TreeVars {
	public Mangrove(World world) {
		super(world, Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.BIRCH), Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.BIRCH),
				Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE));
		
		leafYMin = 0;
		leafYMax = 2;
		leafRad = 3;
		airGenerate = true;
		airGenHeight = 2;
		growDense = false;
		rootFinalAngle = 0.5;
		vines = 3;
		rootInitialAngle = 1F;
		rootIncrementAngle = 99F;
		waterGenerate = true;
	}

	@Override
	public int getBranchesPerNode(double scale) {
		return (int) MathHelper.clamp_double(random.nextInt(5-3), 1, 2);
	}

	@Override
	public double getBranchRotation(double scale, double numBranches) {
		return Math.PI/numBranches;
	}

	@Override
	public double getBranchSeperation(double scale) {
		return random.nextInt(2)+2;
	}

	@Override
	public double getBranchPitch(double scale) {
		return 0.5;
	}

	@Override
	public double getBranchLength(double scale, double trunkHeight, double nodeHeight) {
		double taper = 1-nodeHeight/trunkHeight;
		return trunkHeight/2*taper;
	}

	@Override
	public double getTrunkHeight(double scale) {
		return 7 + random.nextInt(7) + 7*scale;
	}

	@Override
	public double getRootLength(double trunkHeight) {
		return trunkHeight/2;
	}

	@Override
	public double getTrunkDiameter(double scale) {
		return 1;
	}

	@Override
	public int getTrunkColumnHeight(double trunkHeight, double currentRadius, double maxRadius) {
		return (int) trunkHeight;
	}

	@Override
	public double getLowestBranchRatio() {
		return random.nextFloat()/2+0.25;
	}


	@Override
	public int getNumRoots(double trunkDiameter) {
		return random.nextInt(2)+3;
	}

}
