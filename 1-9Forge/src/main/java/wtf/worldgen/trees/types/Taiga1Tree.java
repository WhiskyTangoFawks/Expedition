package wtf.worldgen.trees.types;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import wtf.worldgen.trees.TreeInstance;
import wtf.worldgen.trees.components.Branch;

public class Taiga1Tree extends AbstractTreeType{

	public Taiga1Tree(World world) {
		super(world, Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE), 
				Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE), 
				Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.SPRUCE).withProperty(BlockLeaves.CHECK_DECAY, false));
		leaftype = LeafStyle.SPRUCE;
		this.topLimitDown = 1.5;
		this.topLimitUp = 0;
		this.genBuffer = 3;
		this.leafRad = 2;
	}

	//based on the pine needles going off to the side, with a short branch
	//What is the core problem: I'm getty boxy trees
		//is the pine leaf generator just going in s traight line?
	
	
	@Override
	public int getBranchesPerNode(double nodeHeight, double scale) {
		return random.nextInt(2)+3;
	}

	@Override
	public double getBranchRotation(double scale, double numBranches) {
		return Math.PI/(numBranches+1);
	}

	@Override
	public double getBranchSeperation(double scale) {
		return 1+random.nextInt(2);
	}

	@Override
	public double getBranchPitch(double scale) {
		return -0.33;
	}

	@Override
	public double getBranchLength(double scale, double trunkHeight, double nodeHeight) {
		double bottom = this.getLowestBranchRatio()*trunkHeight;
		double distFromBottom = nodeHeight - bottom;
		double branchSectionLength = trunkHeight-bottom;
		double taper = 1 - MathHelper.clamp_double(distFromBottom/branchSectionLength, 0.1, 0.9);
		return  1+(trunkHeight/5)*taper;
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
	
	@Override
	public void doLeafNode(TreeInstance tree, Branch branch, BlockPos pos) {
		tree.setLeaf(pos.up());
		tree.setLeaf(pos.north());
		tree.setLeaf(pos.east());
		tree.setLeaf(pos.south());
		tree.setLeaf(pos.west());
	}


}
