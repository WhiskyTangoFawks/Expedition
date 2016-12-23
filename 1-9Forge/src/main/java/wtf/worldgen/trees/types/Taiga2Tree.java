package wtf.worldgen.trees.types;

import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import wtf.worldgen.trees.TreeVars;

public class Taiga2Tree extends TreeVars {

	public Taiga2Tree(World world) {
		super(world, Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE), 
				Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE), 
				Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.SPRUCE));
		leaftype = LeafStyle.SPRUCE;
		this.genBuffer = 4;
	}

	@Override
	public int getBranchesPerNode(double scale) {
		return random.nextInt(4)+4;
	}

	@Override
	public double getBranchRotation(double scale, double numBranches) {
		return Math.PI/numBranches;
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
		return 0;
	}



	@Override
	public double getTrunkHeight(double scale) {
		return 10 + random.nextInt(10) + 10*scale;
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
