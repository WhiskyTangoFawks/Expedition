package wtf.worldgen.trees.types;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import wtf.worldgen.trees.GenTree;
import wtf.worldgen.trees.TreePos;
import wtf.worldgen.trees.TreeVars;
import wtf.worldgen.trees.components.Branch;

public class JungleTree extends TreeVars{

	public JungleTree(World world) {
		super(world, Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE), Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE),
				Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE).withProperty(BlockLeaves.CHECK_DECAY, false));
		
		leafYMin = 0;
		leafYMax = 2;
		leafRad = 3.5;
		airGenerate = true;
		airGenHeight = 3;
		
		growDense = false;
		cocoa = true;
		rootFinalAngle = 0.5;
		vines = 3;
		rootInitialAngle = 1F;
		rootIncrementAngle = 0.3F;
		this.genBuffer = -6;
		
	}

	@Override
	public int getBranchesPerNode(double nodeHeight, double scale) {
		return (int) MathHelper.clamp_double(random.nextInt(5-3), 1, 2);
	}

	@Override
	public double getBranchRotation(double scale, double numBranches) {
		return numBranches;
	}

	@Override
	public double getBranchSeperation(double scale) {
		return random.nextInt(4)+4;
	}

	@Override
	public double getBranchPitch(double scale) {
		return 0.5;
	}

	@Override
	public double getBranchLength(double scale, double trunkHeight, double nodeHeight) {
		double taper = 1-nodeHeight/trunkHeight;
		return trunkHeight/2*taper;
	}

	@Override
	public double getTrunkHeight(double scale) {
		return 16 + random.nextInt(16) + 16*scale;
	}

	@Override
	public double getRootLength(double trunkHeight) {
		return trunkHeight/4;
	}

	@Override
	public double getTrunkDiameter(double scale) {
		return 2;
	}

	@Override
	public int getTrunkColumnHeight(double trunkHeight, double currentRadius, double maxRadius) {
		return (int) trunkHeight;
	}

	@Override
	public double getLowestBranchRatio() {
		return random.nextFloat()/2+0.25;
	}


	@Override
	public int getNumRoots(double trunkDiameter) {
		return MathHelper.floor_double(PId2*(trunkDiameter+1));
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
