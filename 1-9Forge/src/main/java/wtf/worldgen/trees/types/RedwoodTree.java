package wtf.worldgen.trees.types;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import wtf.worldgen.trees.TreePos;
import wtf.worldgen.trees.TreeVars;
import wtf.worldgen.trees.TreeVars.LeafStyle;
import wtf.worldgen.trees.components.Branch;

public class RedwoodTree extends TreeVars{
	public RedwoodTree(World world) {
		super(world, Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE), 
				Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE), 
				Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.SPRUCE).withProperty(BlockLeaves.CHECK_DECAY, false));
		leaftype = LeafStyle.SPRUCE;
		this.topLimitDown = 2;
		this.topLimitUp = 0;
		this.topLimitIncrement = Math.PI/8;
		this.genBuffer = -25;
		this.leafRad = 2;
	}

	//I need to be able to do fewer branches as the height increases, so add node height as a variable to the getbranchespernode
	//and I can delete most of the leaf varaibles that I don't need for the
	
	@Override
	public int getBranchesPerNode(double nodeHeight, double scale) {
	//I should theoretically be able to calculate the number of spokes required to join up branches of a given distance
		double taper = 1 - nodeHeight;
		double branches = 7+5*scale;
		return (int) (branches + branches*taper);
	}

	@Override
	public double getBranchRotation(double scale, double numBranches) {
		return Math.PI/(numBranches+1);
	}

	@Override
	public double getBranchSeperation(double scale) {
		return 3+random.nextInt(2);
	}

	@Override
	public double getBranchPitch(double scale) {
		return -0.35;
	}

	@Override
	public double getBranchLength(double scale, double trunkHeight, double nodeHeight) {
		double taper = 1 - MathHelper.clamp_double(nodeHeight/(trunkHeight), 0.05, 1);
		return  trunkHeight/5+(trunkHeight/5)*taper;
	}

	@Override
	public double getTrunkHeight(double scale) {
		return 33 + random.nextInt(33) + 33*scale;
	}

	@Override
	public double getRootLength(double trunkHeight) {
		return trunkHeight/4;
	}

	@Override
	public double getTrunkDiameter(double scale) {
		return random.nextInt(4)+4+scale*4;
	}



	@Override
	public int getTrunkColumnHeight(double trunkHeight, double currentRadius, double maxRadius) {
		if (currentRadius > 1){
			double thirdHeight = trunkHeight/3;
			double rad = 1-(currentRadius/maxRadius);
			return (int) (thirdHeight + 2*(thirdHeight*rad) + random.nextInt(5)-2);
		}
		else {
			return MathHelper.ceiling_double_int(trunkHeight);
		}
	}
	@Override
	public double getLowestBranchRatio() {
		return 0.8;
	}

	@Override
	public int getNumRoots(double trunkDiameter) {
		return random.nextInt(4)+4;
	}

	@Override
	public void doLeafNode(TreePos tree, Branch branch, BlockPos pos) {
		
		if (pos.getY() < tree.trunkHeight+tree.y){
			tree.setBranch(pos, branch.axis);
		}
		
		for (int loop = 0; loop < 5; loop++){
			double vecX = branch.vecX + random.nextFloat()-0.5;
			double vecY = branch.vecY + random.nextFloat()-0.25;
			double vecZ = branch.vecZ + random.nextFloat()-0.5;
			
			Branch twig = new Branch(pos.getX(), pos.getY(), pos.getZ(), vecX, vecY, vecZ, 2);
			
			while (twig.hasNext()){
				tree.setLeaf(twig.next());
			}
			
		}
		
	}

}
