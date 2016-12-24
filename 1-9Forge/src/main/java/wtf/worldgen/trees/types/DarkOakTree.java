package wtf.worldgen.trees.types;

import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import wtf.blocks.BlockFoxfire;
import wtf.init.WTFBlocks;
import wtf.worldgen.trees.TreeVars;

public class DarkOakTree extends TreeVars{

	public DarkOakTree(World world) {
		super(world,  Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.DARK_OAK),
				 Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.DARK_OAK),
				 Blocks.LEAVES2.getDefaultState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.DARK_OAK));
		
		this.leafRad = 4;
		this.leafYMax= 4;
		this.leafYMin = -2;
		this.rootDecoRate = 0.01F;
		this.decoDown = WTFBlocks.foxfire.getDefaultState().withProperty(BlockFoxfire.HANGING, true);
		this.decoUp = WTFBlocks.foxfire.getDefaultState();
		this.genBuffer = -10;
		this.rootInitialAngle=1.35;
		this.rootLevel = 2;
		this.rootWall = true;
	}

	@Override
	public int getBranchesPerNode(double scale) {
		return 10;
	}

	@Override
	public double getBranchRotation(double scale, double numBranches) {
		return Math.PI/numBranches; 
	}

	@Override
	public double getBranchSeperation(double scale) {
		return 3;
	}

	@Override
	public double getBranchPitch(double scale) {
		return 0;
	}

	@Override
	public double getBranchLength(double scale, double trunkHeight, double nodeHeight) {
		double taper = 1-nodeHeight/trunkHeight;
		return trunkHeight/2+trunkHeight/2*taper;
	}

	@Override
	public double getTrunkHeight(double scale) {
		return 6 + random.nextInt(8) + 8*scale;
	}

	@Override
	public double getRootLength(double trunkHeight) {
		return trunkHeight/3;
	}

	@Override
	public double getTrunkDiameter(double scale) {
		return 1.5+(2*scale);//MathHelper.ceiling_double_int(0.4 + scale);
	}

	@Override
	public int getTrunkColumnHeight(double trunkHeight, double currentRadius, double maxRadius) {
		if (currentRadius > 1){
			double thirdHeight = trunkHeight/3;
			double rad = 1-(currentRadius/maxRadius);
			return (int) (thirdHeight + (thirdHeight*rad) + random.nextInt(5)-2);
		}
		else {
			return MathHelper.ceiling_double_int(trunkHeight);
		}
	}


	@Override
	public double getLowestBranchRatio() {
		return 1;
	}



	@Override
	public int getNumRoots(double trunkDiameter) {
		return 4;
	}



}
