package wtf.worldgen.trees.types;

import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import wtf.worldgen.trees.TreeVars;

public class JungleTree extends TreeVars{

	public JungleTree(World world) {
		super(world, Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE), Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE),
				Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE));
		
		leafYMin = 0;
		leafYMax = 2;
		leafRad = 3.5;
		airGenerate = true;
		airGenHeight = 3;
		
		growDense = false;
		cocoa = true;
		rootFinalAngle = 0.5;
		vines = 3;
		rootInitialAngle = 1F;
		rootIncrementAngle = 0.3F;
		this.genBuffer = -6;
		
	}

	@Override
	public int getBranchesPerNode(double scale) {
		return (int) MathHelper.clamp_double(random.nextInt(5-3), 1, 2);
	}

	@Override
	public double getBranchRotation(double scale, double numBranches) {
		return numBranches;
	}

	@Override
	public double getBranchSeperation(double scale) {
		return random.nextInt(4)+4;
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
		return 16 + random.nextInt(16) + 16*scale;
	}

	@Override
	public double getRootLength(double trunkHeight) {
		return trunkHeight/4;
	}

	@Override
	public double getTrunkDiameter(double scale) {
		return 2;
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
		return MathHelper.floor_double(PId2*(trunkDiameter+1));
	}


	
}
