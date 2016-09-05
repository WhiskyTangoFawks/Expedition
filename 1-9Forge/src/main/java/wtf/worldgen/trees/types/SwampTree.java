package wtf.worldgen.trees.types;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import wtf.worldgen.trees.TreeVars;

public class SwampTree extends TreeVars {

	public SwampTree(World world) {
		super(world, Blocks.LOG.getDefaultState(), Blocks.LOG.getDefaultState(), Blocks.LEAVES.getDefaultState());
	
		leafRad = 3;
		leafYMax=2;
		leafYMin = -1;

		vines = 6;
		leafYMax=3;
		topLimitDown = Math.PI/2;
		}

	@Override
	public int getBranchesPerNode(double scale) {
		return random.nextInt(3)+4;
	}

	@Override
	public double getBranchRotation(double scale, double numBranches) {
		return Math.PI/numBranches;
	}

	@Override
	public double getBranchSeperation(double scale) {
		return random.nextInt(4)+2;
	}

	@Override
	public double getBranchPitch(double scale) {
	
		return 0.35;
	}

	@Override
	public double getBranchLength(double scale, double trunkHeight, double nodeHeight) {
		double taper = 1-nodeHeight/trunkHeight;
		
		return trunkHeight/2+trunkHeight/2*taper;
	}

	@Override
	public double getTrunkHeight(double scale) {
		return 8 + 8*scale + random.nextInt(8);
	}

	@Override
	public double getRootLength(double trunkHeight) {
		return trunkHeight/3;
	}

	@Override
	public double getTrunkDiameter(double scale) {
		return MathHelper.ceiling_double_int(1.1 + scale);
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
		return 0.15;
	}

	@Override
	public int getNumRoots(double trunkDiameter) {
		return MathHelper.floor_double(PId2*(trunkDiameter+1));
	}
	
}
