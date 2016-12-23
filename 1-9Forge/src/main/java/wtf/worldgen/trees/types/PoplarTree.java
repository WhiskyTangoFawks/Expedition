package wtf.worldgen.trees.types;

import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import wtf.worldgen.trees.TreeVars;
import wtf.worldgen.trees.TreeVars.LeafStyle;



public class PoplarTree extends TreeVars{

	public PoplarTree(World world) {
		super(world, Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.BIRCH), 
				Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.BIRCH), 
				Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.BIRCH));
		leaftype = LeafStyle.SPRUCE;
	}

	@Override
	public int getBranchesPerNode(double scale) {
		return 7;
	}

	@Override
	public double getBranchRotation(double scale, double numBranches) {
		return Math.PI/7;
	}

	@Override
	public double getBranchSeperation(double scale) {
		return 1;
	}

	@Override
	public double getBranchPitch(double scale) {
		return 0;
	}

	@Override
	public double getBranchLength(double scale, double trunkHeight, double nodeHeight) {
		return random.nextInt(2)+0.5;
	}



	@Override
	public double getTrunkHeight(double scale) {
		
		return 12 + random.nextInt(12) + 12*scale;
	}

	@Override
	public double getRootLength(double trunkHeight) {
		return trunkHeight/5;
	}

	@Override
	public double getTrunkDiameter(double scale) {
		return 1;
	}

	@Override
	public int getTrunkColumnHeight(double trunkHeight, double currentRadius, double maxRadius) {
		return MathHelper.ceiling_double_int(trunkHeight);
	}

	@Override
	public double getLowestBranchRatio() {
		return 0.5;
	}

	@Override
	public int getNumRoots(double trunkDiameter) {
		return random.nextInt(3)+2;
	}

}
