package wtf.worldgen.trees.types;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import wtf.blocks.BlockFoxfire;
import wtf.init.WTFBlocks;
import wtf.worldgen.trees.GenTree;
import wtf.worldgen.trees.TreePos;
import wtf.worldgen.trees.TreeVars;
import wtf.worldgen.trees.components.Branch;

public class DarkOakTree extends TreeVars{

	public DarkOakTree(World world) {
		super(world,  Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.DARK_OAK),
				 Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.DARK_OAK),
				 Blocks.LEAVES2.getDefaultState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.DARK_OAK).withProperty(BlockLeaves.CHECK_DECAY, false));
		
		this.leafRad = 3;
		this.leafYMax= 3;
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
	public int getBranchesPerNode(double nodeHeight, double scale) {
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
