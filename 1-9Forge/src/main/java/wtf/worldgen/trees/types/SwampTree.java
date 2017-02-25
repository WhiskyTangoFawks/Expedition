package wtf.worldgen.trees.types;

import net.minecraft.block.BlockLeaves;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import wtf.worldgen.trees.TreeGenMethods;
import wtf.worldgen.trees.TreeInstance;
import wtf.worldgen.trees.components.Branch;

public class SwampTree extends AbstractTreeType {

	public SwampTree(World world) {
		super(world, Blocks.LOG.getDefaultState(), Blocks.LOG.getDefaultState(), Blocks.LEAVES.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false));
	
		leafRad = 3;
		leafYMax=2;
		leafYMin = -1;

		vines = 6;
		leafYMax=3;
		topLimitDown = Math.PI/2;
		this.setWaterGen(3);
		this.genBuffer = -10;
		
		}

	@Override
	public int getBranchesPerNode(double nodeHeight, double scale) {
		return random.nextInt(3)+4;
	}

	@Override
	public double getBranchRotation(double scale, double numBranches) {
		return numBranches*Math.PI*2+1;
	}

	@Override
	public double getBranchSeperation(double scale) {
		return random.nextInt(4)+2;
	}

	@Override
	public double getBranchPitch(double scale) {
		return 0.25+random.nextFloat()/4;
	}

	@Override
	public double getBranchLength(double scale, double trunkHeight, double nodeHeight) {
		double bottom = this.getLowestBranchRatio()*trunkHeight;
		double distFromBottom = nodeHeight - bottom;
		double branchSectionLength = trunkHeight-bottom;
		double taper = 1 - MathHelper.clamp_double(distFromBottom/branchSectionLength, 0.1, 0.9);
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
		return 0.15;
	}

	@Override
	public int getNumRoots(double trunkDiameter) {
		return MathHelper.floor_double(PId2*(trunkDiameter+1));
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
