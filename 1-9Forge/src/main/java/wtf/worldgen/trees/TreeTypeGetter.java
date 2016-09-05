package wtf.worldgen.trees;

import java.util.HashMap;

import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenBirchTree;

import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenSwamp;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTrees;
import wtf.worldgen.trees.types.AcaciaTree;
import wtf.worldgen.trees.types.BigTree;
import wtf.worldgen.trees.types.JungleTree;
import wtf.worldgen.trees.types.RedwoodTree;
import wtf.worldgen.trees.types.SimpleTree;
import wtf.worldgen.trees.types.SwampTree;
import wtf.worldgen.trees.types.Taiga1Tree;
import wtf.worldgen.trees.types.Taiga2Tree;

public class TreeTypeGetter {

	public static HashMap<WorldGenAbstractTree, TreeVars> treeTypes = new HashMap<WorldGenAbstractTree, TreeVars>();

	final static IBlockState woodOak = Blocks.LOG.getDefaultState();
	final static IBlockState woodSpruce = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE);
	final static IBlockState woodBirch = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.BIRCH);
	final static IBlockState woodJungle = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE);
	final static IBlockState woodAcacia = Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.ACACIA);
	final static IBlockState darkOakBlocks = Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.DARK_OAK);

	final static IBlockState leavesOak = Blocks.LEAVES.getDefaultState();
	final static IBlockState leavesSpruce = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.SPRUCE);
	final static IBlockState leavesBirch = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.BIRCH);
	final static IBlockState leavesJungle = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE);
	final static IBlockState leavesAcacia = Blocks.LEAVES2.getDefaultState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.ACACIA);
	final static IBlockState leavesDarkOak = Blocks.LEAVES2.getDefaultState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.DARK_OAK);

	public static TreeVars getTree(World world, WorldGenAbstractTree oldTree){

		if (treeTypes.containsKey(oldTree)){
			return treeTypes.get(oldTree);
		}

		if (oldTree instanceof WorldGenShrub){
			treeTypes.put(oldTree, null);
			return null;
		}

		if (oldTree instanceof WorldGenTrees){

			IBlockState wood = net.minecraftforge.fml.common.ObfuscationReflectionHelper.getPrivateValue(WorldGenTrees.class, (WorldGenTrees)oldTree, 4);
			IBlockState leaf = net.minecraftforge.fml.common.ObfuscationReflectionHelper.getPrivateValue(WorldGenTrees.class, (WorldGenTrees)oldTree, 5);
			Boolean vines = net.minecraftforge.fml.common.ObfuscationReflectionHelper.getPrivateValue(WorldGenTrees.class, (WorldGenTrees)oldTree, 3);
			
			if (wood == woodJungle){
				treeTypes.put(oldTree, new JungleTree(world));
				return new JungleTree(world);
			}
			else {
				treeTypes.put(oldTree, new SimpleTree(world, wood, wood, leaf, vines));
				return new SimpleTree(world, wood, wood, leaf, vines);
			}

		}

		else 
			if (oldTree instanceof WorldGenBigTree){
				treeTypes.put(oldTree, new BigTree(world, woodOak, woodOak, leavesOak));
				return new BigTree(world, woodOak, woodOak, leavesOak);

		}
		
		else if (oldTree instanceof WorldGenBirchTree){
			treeTypes.put(oldTree, new BigTree(world, woodBirch, woodBirch, leavesBirch));
			return new SimpleTree(world, woodBirch, woodBirch, leavesBirch, false);
		}
			
		else if (oldTree instanceof WorldGenMegaJungle){
			treeTypes.put(oldTree, new JungleTree(world));
			return new JungleTree(world);
		}

		
		else if (oldTree instanceof WorldGenMegaPineTree){
			treeTypes.put(oldTree, new RedwoodTree(world));
			return new RedwoodTree(world);
		}
		else if (oldTree instanceof WorldGenSavannaTree){
			treeTypes.put(oldTree, new AcaciaTree(world));
			return new AcaciaTree(world);
		}
		

		else if (oldTree instanceof WorldGenSwamp){
			treeTypes.put(oldTree, new SwampTree(world));
			return new SwampTree(world);
		}

		else if (oldTree instanceof WorldGenTaiga1){		
			treeTypes.put(oldTree, new Taiga1Tree(world));
			return new Taiga1Tree(world);
		}
		else if (oldTree instanceof WorldGenTaiga2){
			treeTypes.put(oldTree, new Taiga2Tree(world));
			return new Taiga2Tree(world);
		}


		//else {
		//	System.out.println("Unrecognised tree type " + oldTree.getClass().toString());
		//}

		//add it to the type hashmap
		return null;

	}
}

