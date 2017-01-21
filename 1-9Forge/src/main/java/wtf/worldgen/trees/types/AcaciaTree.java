package wtf.worldgen.trees.types;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import wtf.worldgen.trees.GenTree;
import wtf.worldgen.trees.TreePos;
import wtf.worldgen.trees.TreeVars;
import wtf.worldgen.trees.components.Branch;

public class AcaciaTree extends TreeVars{

	public AcaciaTree(World world) {
		super(world, Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.ACACIA), 
				Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.ACACIA),
				Blocks.LEAVES2.getDefaultState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.ACACIA).withProperty(BlockLeaves.CHECK_DECAY, false));
		
		leafYMin = -1;
		leafYMax = 1;
		leafRad = 3;
		this.topLimitUp = 0.4;
		this.topLimitDown = 1;
		this.topLimitIncrement = 0.5;
		this.genBuffer = 6;
	
	
	
	}

	@Override
	public int getBranchesPerNode(double nodeHeight, double scale) {
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

	@Override
	public void doLeafNode(TreePos tree, Branch branch, BlockPos pos) {
		double height = pos.getY()-tree.y;
		double taper = MathHelper.clamp_double((tree.type.leafTaper) * (tree.trunkHeight-height)/tree.trunkHeight, tree.type.leafTaper, 1);

		double radius = MathHelper.clamp_double(tree.type.leafRad*taper, 1, tree.type.leafRad);
		double ymin = tree.type.leafYMin;
		double ymax = tree.type.leafYMax;


		for (double yloop = ymin; yloop < ymax; yloop++){

			double sliceRadSq = (radius+1) * (radius+1) - (yloop*yloop);
			double slicedRadSqSmall = radius * radius - (yloop * yloop);

			if (sliceRadSq > 0){

				for (double xloop = -radius; xloop < radius+1; xloop++){
					for (double zloop = -radius; zloop < radius+1; zloop++){

						double xzDistanceSq = xloop*xloop + zloop*zloop;

						BlockPos leafPos = new BlockPos(xloop+pos.getX(), yloop+pos.getY(), zloop+pos.getZ());

						if (xzDistanceSq < slicedRadSqSmall){
							tree.setLeaf(leafPos);
						}
						else if (xzDistanceSq < sliceRadSq){
							if (tree.random.nextBoolean()){
								tree.setLeaf(leafPos);


								if (tree.type.vines > 0 && MathHelper.abs_max(xloop, zloop) > yloop && tree.random.nextBoolean()){
									GenTree.genVine(tree, leafPos, xloop, zloop);
								}
							}
						}
					}
				}
			}
		}
	}
	
}
