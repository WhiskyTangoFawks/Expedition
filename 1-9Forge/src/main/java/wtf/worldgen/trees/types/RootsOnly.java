package wtf.worldgen.trees.types;

import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import wtf.blocks.BlockFoxfire;
import wtf.init.WTFBlocks;
import wtf.worldgen.trees.TreeVars;

public class RootsOnly extends TreeVars{

	public final int size;
	public RootsOnly(World world, IBlockState wood, int size) {
		super(world, wood, wood, null);
		this.size=size;
		if (wood.hashCode() ==  Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.DARK_OAK).hashCode()){
			this.rootDecoRate = 0.05F;
			this.decoDown = WTFBlocks.foxfire.getDefaultState().withProperty(BlockFoxfire.HANGING, true);
			this.decoUp = WTFBlocks.foxfire.getDefaultState();
		}
	}

	@Override
	public int getBranchesPerNode(double scale) {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public double getBranchRotation(double scale, double numBranches) {
		// TODO Auto-generated method stub
		return Math.PI/numBranches;
	}

	@Override
	public double getBranchSeperation(double scale) {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public double getBranchPitch(double scale) {
		// TODO Auto-generated method stub
		return 0.5;
	}

	@Override
	public double getLowestBranchRatio() {
		// TODO Auto-generated method stub
		return 0.5;
	}

	@Override
	public double getBranchLength(double scale, double trunkHeight, double nodeHeight) {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public double getTrunkHeight(double scale) {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public double getRootLength(double trunkHeight) {
		// TODO Auto-generated method stub
		int length = (int) (trunkHeight/4);
		return length > 1 ? length : 2;
	}

	@Override
	public double getTrunkDiameter(double scale) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getTrunkColumnHeight(double trunkHeight, double currentRadius, double maxRadius) {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public int getNumRoots(double trunkDiameter) {
		// TODO Auto-generated method stub
		return random.nextInt(2)+3;
	}

}
