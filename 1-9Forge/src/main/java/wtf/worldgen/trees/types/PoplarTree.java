package wtf.worldgen.trees.types;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import wtf.worldgen.trees.TreeGenMethods;
import wtf.worldgen.trees.TreeInstance;
import wtf.worldgen.trees.components.Branch;
import wtf.worldgen.trees.types.AbstractTreeType.LeafStyle;



public class PoplarTree extends AbstractTreeType{

	public PoplarTree(World world) {
		super(world, Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.BIRCH), 
				Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.BIRCH), 
				Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.BIRCH).withProperty(BlockLeaves.CHECK_DECAY, false));
		leaftype = LeafStyle.SPRUCE;
	}

	@Override
	public int getBranchesPerNode(double nodeHeight, double scale) {
		return 7;
	}

	@Override
	public double getBranchRotation(double scale, double numBranches) {
		return Math.PI/7;
	}

	@Override
	public double getBranchSeperation(double scale) {
		return 1;
	}

	@Override
	public double getBranchPitch(double scale) {
		return 0;
	}

	@Override
	public double getBranchLength(double scale, double trunkHeight, double nodeHeight) {
		return random.nextInt(2)+0.5;
	}



	@Override
	public double getTrunkHeight(double scale) {
		
		return 12 + random.nextInt(12) + 12*scale;
	}

	@Override
	public double getRootLength(double trunkHeight) {
		return trunkHeight/5;
	}

	@Override
	public double getTrunkDiameter(double scale) {
		return 1;
	}

	@Override
	public int getTrunkColumnHeight(double trunkHeight, double currentRadius, double maxRadius) {
		return MathHelper.ceiling_double_int(trunkHeight);
	}

	@Override
	public double getLowestBranchRatio() {
		return 0.5;
	}

	@Override
	public int getNumRoots(double trunkDiameter) {
		return random.nextInt(3)+2;
	}

	@Override
	public void doLeafNode(TreeInstance tree, Branch branch, BlockPos pos) {
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
									TreeGenMethods.genVine(tree, leafPos, xloop, zloop);
								}
							}
						}
					}
				}
			}
		}
	}

}
