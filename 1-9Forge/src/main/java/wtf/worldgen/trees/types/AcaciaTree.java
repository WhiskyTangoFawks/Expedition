package wtf.worldgen.trees.types;

import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import wtf.worldgen.trees.TreeVars;

public class AcaciaTree extends TreeVars{

	public AcaciaTree(World world) {
		super(world, Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.ACACIA), 
				Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.ACACIA),
				Blocks.LEAVES2.getDefaultState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.ACACIA));
		
		leafYMin = -1;
		leafYMax = 1;
		leafRad = 3;
		this.topLimitUp = 0.4;
		this.topLimitDown = 1;
		this.topLimitIncrement = 0.5;
		this.genBuffer = 6;
	
	
	
	}

	@Override
	public int getBranchesPerNode(double scale) {
		return random.nextInt(2)+1;
	}

	@Override
	public double getBranchRotation(double scale, double numBranches) {
		return 2;
	}

	@Override
	public double getBranchSeperation(double scale) {
		return random.nextInt(2)+2;
	}

	@Override
	public double getBranchPitch(double scale) {
		return random.nextFloat()/3+0.4;
	}

	@Override
	public double getLowestBranchRatio() {
		return random.nextFloat()/2+0.5;
	}

	@Override
	public double getBranchLength(double scale, double trunkHeight, double nodeHeight) {
		return trunkHeight*2;
	}

	@Override
	public double getTrunkHeight(double scale) {
		return random.nextInt(3)+2 + 2*scale;
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
	public int getNumRoots(double trunkDiameter) {
		return random.nextInt(2)+2;
	}
	
}
