package wtf.core.gameplay;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import wtf.core.blocks.AnimatedBlock;
import wtf.core.blocks.BlockDenseOre;
import wtf.core.init.BlockSets;
import wtf.core.init.BlockSets.Modifier;
import wtf.core.utilities.Simplex;
import wtf.core.utilities.wrappers.Vec;

public class StoneFractureMethods {

	static Random random = new Random();
	
	public static void tryFrac(World world, BlockPos pos, Block block, int tool){
		
		if (block instanceof BlockDenseOre){ 
			denseFrac(world, pos, tool);
		}
		else {
			frac(world, pos, tool);
		}
	}
	
	public static void frac(World world, BlockPos pos, int tool) {
		HashSet<BlockPos> hashset = new HashSet<BlockPos>();
		hashset.add(pos.up());
		hashset.add(pos.down());
		hashset.add(pos.east());
		hashset.add(pos.west());
		hashset.add(pos.north());
		hashset.add(pos.south());
		FracIterator(world, hashset);
	}

	
	static int coal = Blocks.COAL_ORE.getHarvestLevel(Blocks.COAL_ORE.getDefaultState());
	static int iron = Blocks.IRON_ORE.getHarvestLevel(Blocks.IRON_ORE.getDefaultState());
	static float pi = (float) Math.PI;
	
	/**
	 * tool level determines number of frac attempts
	 * block level determines strength of frac attempt
	 * @param world
	 * @param pos
	 * @param tool
	 */
	public static void denseFrac(World world, BlockPos pos, int tool) {
		HashSet<BlockPos> hashset= new HashSet<BlockPos>();

		//each one fracs n number of times
		//at the end, if number to frac < minimum number, it adds a few more rounds of iteration
		
		int blockLevel = world.getBlockState(pos).getBlock().getHarvestLevel(world.getBlockState(pos)); 
		
		if (blockLevel <= coal){
			//System.out.println("frac low found");
			hashset.addAll(fracLow(world, pos, (tool+1)*5));
		}
		else if (blockLevel <= iron){
			//System.out.println("frac edium found");
			hashset.addAll(fracStandard(world, pos, (tool+1)*4));
		}
		else{
			//System.out.println("frac high found");
			hashset.addAll(fracCrack(world, pos, (tool+1)*4));
		}

		FracIterator(world, hashset);
	}

	
	public static void hammerFrac(World world, int x, int y, int z, int harvestLevel) {
		HashSet<BlockPos> hashset= new HashSet<BlockPos>();
		if (fracStone(world, new BlockPos(x, y, z))){
		for (int loop = 0; loop < 12*harvestLevel; loop++){
			
			int xmod = random.nextInt(3)-1;
			int ymod = random.nextInt(3)-1;
			int zmod = random.nextInt(3)-1;

			hashset.add(new BlockPos(x, y, z));
			BlockPos pos = new BlockPos(x+xmod, y+ymod, z+zmod);
			Block blockToFracture = world.getBlockState(pos).getBlock();
			int count = 1;
			
			while (BlockSets.hasCobble(world.getBlockState(pos)) && hashset.contains(pos)  && count < 1+harvestLevel*1.5){
				count++;
				pos = new BlockPos(x+xmod*count, y+ymod*count, z+zmod*count);
				blockToFracture = (world.getBlockState(pos).getBlock());
			}
			
			hashset.add(pos);
			fracStone(world, pos);
			for (BlockPos drop : hashset){
				//if there isn't a block fractured below this one, drop it
				if (!hashset.contains(pos.down())){
					GravityMethods.dropBlock(world, drop.getX(), drop.getY(), drop.getZ(), false);
				}
			}
		}
		}

		//FracIterator(world, hashset);
	}

	public static ArrayList<BlockPos> getAdjPos(BlockPos pos){
		ArrayList<BlockPos> list = new ArrayList<BlockPos>();
		for (int xloop = -1; xloop < 2; xloop++){
			for (int yloop = -1; yloop < 2; yloop++){
				for (int zloop = -1; zloop < 2; zloop++){
					if (xloop != 0 && yloop != 0 && zloop != 0){
						list.add(new BlockPos(pos.getX()+xloop, pos.getY()+yloop, pos.getZ()+zloop));
					}
				}	
			}	
		}
		return list;
	}
	/**
	 * Returns a hashset with n number of adjacent blocks
	 * @param world
	 * @param pos
	 * @return
	 */
	public static HashSet<BlockPos> fracLow(World world, BlockPos pos, int n){
		HashSet<BlockPos> hashset = new HashSet<BlockPos>();
		ArrayList<BlockPos> adjPos = getAdjPos(pos);
		
		for (int loop = 0; loop < n; loop++){
			BlockPos randPos = adjPos.get(random.nextInt(adjPos.size()));
			if  (BlockSets.hasCobble(world.getBlockState(randPos))){
				hashset.add(randPos);
			}
		}
		return hashset;
	}
	
	/**
	 * Returns a hashset with n number of adjacent blocks
	 * if a block is already frac'd, then it goes out one further
	 * Vec based
	 * @param world
	 * @param pos
	 * @return
	 */
	public static HashSet<BlockPos> fracStandard(World world, BlockPos pos, int n){
		HashSet<BlockPos> hashset = new HashSet<BlockPos>();
		for (int loop = 0; loop < n; loop++){
			Vec frac = new Vec(pos, random);
			BlockPos fracPos = frac.next();

			while (BlockSets.isFractured(world.getBlockState(fracPos)) || hashset.contains(fracPos)){
				fracPos = frac.next();
			}

			if  (BlockSets.hasCobble(world.getBlockState(fracPos))){
				hashset.add(fracPos);
			}
		}
		return hashset;
	}


	public static HashSet<BlockPos> fracCrack(World world, BlockPos pos, int n){
		HashSet<BlockPos> hashset = new HashSet<BlockPos>();
		System.out.println("FracCrack called with " + n);
		Random posRandom = new Random(pos.hashCode());
		for (int loop = 0; loop < n; loop++){
			System.out.println("frac high iterating " + loop );
			//setup a frac vector
			Vec fracVec = new Vec(pos, posRandom);
			BlockPos fracPos = fracVec.next();

			//while the block already frac'd get next
			while (BlockSets.isFractured(world.getBlockState(fracPos)) || hashset.contains(fracPos)){
				fracPos = fracVec.next();
			}

			//if pos can be frac'd, add it to the hashset
			if  (BlockSets.hasCobble(world.getBlockState(fracPos))){
				hashset.add(fracPos);
				//iterate out up to an additional 2 positions
				for (int loop2 = random.nextInt(3); loop2 > 0; loop2--){
					while (BlockSets.isFractured(world.getBlockState(fracPos)) || hashset.contains(fracPos)){
						fracPos = fracVec.next();
						if  (BlockSets.hasCobble(world.getBlockState(fracPos))){
							hashset.add(fracPos);
						}
						else {
							break;
						}
					}
				}
			}
		}
		return hashset;
	}



	public static void FracIterator(World world, HashSet<BlockPos> hashset){
		BlockPos chunkposition;
		Iterator<BlockPos> iterator = hashset.iterator();
		while (iterator.hasNext()){
			chunkposition = iterator.next();
			fracStone(world, chunkposition);
		}
	}

	public static boolean fracStone(World world, BlockPos pos){
		IBlockState state = world.getBlockState(pos);
		IBlockState stateToSet = BlockSets.getTransformedState(state, BlockSets.Modifier.COBBLE);
		
		if (stateToSet != null) { 
			world.setBlockState(pos, stateToSet);
			GravityMethods.dropBlock(world, pos.getX(), pos.getY(), pos.getZ(), false);
			return true;
		}
		else if (world.getBlockState(pos) instanceof AnimatedBlock && state.getValue(AnimatedBlock.TYPE) == AnimatedBlock.ANIMTYPE.LAVA_CRUST) {
			world.setBlockState(pos, Blocks.LAVA.getDefaultState());
		}
		return false;
	}

}
