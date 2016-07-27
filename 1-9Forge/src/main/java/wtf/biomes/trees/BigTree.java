package wtf.biomes.trees;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import wtf.biomes.TreeVars;

public class BigTree extends TreeVars{

	public BigTree(World world, IBlockState wood, IBlockState branch, IBlockState leaf) {
		super(world, wood, branch, leaf);
		topLimitDown = Math.PI/2;
	}
	@Override
	public double getBranchLength(double scale, double trunkHeight, double nodeHeight) {
		double taper = 1-nodeHeight/trunkHeight;
		double halfLength = trunkHeight/4;
		return halfLength*taper+halfLength;
	}

	@Override
	public double getTrunkHeight(double scale) {
		return 8 + random.nextInt(8) + 8*scale;
	}


	@Override
	public double getTrunkDiameter(double scale) {
		return 2;//MathHelper.ceiling_double_int(2.05 + scale);
	}
	@Override
	public int getBranchesPerNode(double scale) {
		return random.nextInt(3)+3;
	}

	@Override
	public double getBranchRotation(double scale, double numBranches) {
		return Math.PI/(numBranches+1); 
	}

	@Override
	public double getBranchSeperation(double scale) {
		return 2;
	}

	@Override
	public double getBranchPitch(double scale) {
		return 0.5;
	}


	@Override
	public double getRootLength(double trunkHeight) {
		return trunkHeight/4;
	}

	@Override
	public int getTrunkColumnHeight(double trunkHeight, double currentRadius, double maxRadius) {
		if (currentRadius > 1){
			double halfHeight = trunkHeight/2;
			return (int) (halfHeight + halfHeight*currentRadius/maxRadius + random.nextInt(2)-1);
		}
		else {
			return MathHelper.ceiling_double_int(trunkHeight);
		}
	}
	@Override
	public double getLowestBranchRatio() {
		return random.nextFloat()/2+0.25;
	}
	@Override
	public int getNumRoots(double trunkDiameter) {
		return random.nextInt(3)+3;
	}

}
