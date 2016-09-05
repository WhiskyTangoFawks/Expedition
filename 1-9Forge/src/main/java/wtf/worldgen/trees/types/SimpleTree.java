package wtf.worldgen.trees.types;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import wtf.worldgen.trees.TreeVars;

public class SimpleTree extends TreeVars{

	public SimpleTree(World world, IBlockState wood, IBlockState branch, IBlockState leaf, boolean vines) {
		super(world, wood, branch, leaf);
		if (vines){
			this.vines = random.nextInt(3)+1;
		}
	}

	

	@Override
	public int getBranchesPerNode(double scale) {
		return random.nextInt(2)+3;
	}

	@Override
	public double getBranchRotation(double scale, double numBranches) {
		return Math.PI/numBranches; 
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
	public double getBranchLength(double scale, double trunkHeight, double nodeHeight) {
		double taper = nodeHeight/trunkHeight;
		return 1 + (trunkHeight/4)*taper;
	}


	@Override
	public double getTrunkHeight(double scale) {
		return 4 + random.nextInt(3)+1 + 3*scale;
	}

	@Override
	public double getRootLength(double trunkHeight) {
		return 2;
	}

	@Override
	public double getTrunkDiameter(double scale) {
		return 1;//MathHelper.ceiling_double_int(0.4 + scale);
	}



	@Override
	public int getTrunkColumnHeight(double trunkHeight, double currentRadius, double maxRadius) {
		return (int) trunkHeight;
	}


	@Override
	public double getLowestBranchRatio() {
		return 0.8+random.nextFloat();
	}



	@Override
	public int getNumRoots(double trunkDiameter) {
		return 4;
	}






}
