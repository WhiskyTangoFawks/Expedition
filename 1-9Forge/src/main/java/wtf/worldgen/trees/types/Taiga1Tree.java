package wtf.worldgen.trees.types;

import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import wtf.worldgen.trees.TreeVars;

public class Taiga1Tree extends TreeVars{

	public Taiga1Tree(World world) {
		super(world, Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE), 
				Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE), 
				Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.SPRUCE));
		leaftype = LeafStyle.SPRUCE;
		this.topLimitDown = 1.5;
		this.topLimitUp = 0;
	}

	@Override
	public int getBranchesPerNode(double scale) {
		return 4;
	}

	@Override
	public double getBranchRotation(double scale, double numBranches) {
		return Math.PI/8;
	}

	@Override
	public double getBranchSeperation(double scale) {
		return random.nextInt(2)+1;
	}

	@Override
	public double getBranchPitch(double scale) {
		return -0.33;
	}

	@Override
	public double getBranchLength(double scale, double trunkHeight, double nodeHeight) {
		double taper = 1-nodeHeight/trunkHeight;
		return  1+(trunkHeight/4)*taper;
	}

	@Override
	public double getTrunkHeight(double scale) {
		return 6 + random.nextInt(6) + 6*scale;
	}

	@Override
	public double getRootLength(double trunkHeight) {
		return trunkHeight/4;
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
		return 0.35;
	}

	@Override
	public int getNumRoots(double trunkDiameter) {
		return random.nextInt(2)+2;
	}


}
