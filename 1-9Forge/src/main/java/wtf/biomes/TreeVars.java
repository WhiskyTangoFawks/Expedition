package wtf.biomes;

import java.util.HashSet;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import net.minecraft.world.World;
import wtf.core.utilities.Simplex;

public abstract class TreeVars {
	
	protected final Random random;
	protected double PId2 = Math.PI/2;
	
	//Constants
	public final IBlockState wood;// = Blocks.LOG.getDefaultState();
	public final IBlockState branch;// = Blocks.LOG.getDefaultState();
	public final IBlockState leaf;// = Blocks.LEAVES.getDefaultState();

	public enum LeafStyle{BASIC, SPRUCE};

	public final Simplex simplex;
	
	public TreeVars(World world, IBlockState wood, IBlockState branch, IBlockState leaf){
		random = new Random(world.getSeed());
		simplex = new Simplex((int) world.getSeed());
		
		canGrowOn.add(Blocks.DIRT); canGrowOn.add(Blocks.GRASS); 
		this.wood = wood;
		this.branch = branch;
		this.leaf = leaf;
	}
	
	public HashSet<Block> canGrowOn = new HashSet<Block>();

	public boolean waterGenerate = false;
	public int vines = 0;
	
	public LeafStyle leaftype = LeafStyle.BASIC;
	public double leafRad = 2.5;
	public double leafYMin = -1;
	public double leafYMax = 2;
	public double leafTaper = 1;
	
	
	public boolean airGenerate = false;
	public double airGenHeight = 0;
	
	public double  rootAirDivisor = 2.5;

	public double  rootInitialAngle = 1.01;
	public double  rootFinalAngle = 0;
	public double  rootIncrementAngle = 1;
	
	//public double trunkTaper = 1;
	

	public boolean rootWall = false;
	public boolean growDense = false;
	public boolean cocoa = false;
	
	//set topLimitUp to 0 to have a branch that goes straight up 
	public double  topLimitUp = 0F;
	public double  topLimitDown = 1;
	public double topLimitIncrement = Math.PI/4;

	//public boolean leafNoise = true;
	
	public TreeVars setWaterGen(){
		waterGenerate = true;
		canGrowOn.add(Blocks.WATER);
		canGrowOn.add(Blocks.SAND);
		canGrowOn.add(Blocks.GRAVEL);
		return this;
	}

	public abstract int getBranchesPerNode(double scale);
	public abstract double getBranchRotation(double scale, double numBranches);
	public abstract double getBranchSeperation(double scale);
	public abstract double getBranchPitch(double scale);
	

	//public double  lowestBranchRatio = 0.7F;

	public abstract double getLowestBranchRatio();
	
	public abstract double getBranchLength(double scale, double trunkHeight, double nodeHeight);
	
	//public double  trunkTaper = 0.8F;
	public abstract double getTrunkHeight(double scale);
	
	public abstract double getRootLength(double trunkHeight);
	
	public abstract double getTrunkDiameter(double scale);
	
	public abstract int getTrunkColumnHeight(double trunkHeight, double currentRadius, double maxRadius);
	
	public abstract int getNumRoots(double trunkDiameter);
}
