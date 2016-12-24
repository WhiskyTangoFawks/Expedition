package wtf.blocks;

import java.util.HashSet;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CustomOldLeaves extends BlockOldLeaf{

	 public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	    {
	        if (!worldIn.isRemote)
	        {
	            if (((Boolean)state.getValue(CHECK_DECAY)).booleanValue() && ((Boolean)state.getValue(DECAYABLE)).booleanValue())
	            {
	               //Basic structure
	            	//checks all adjacent blocks, by calling a check method
	            	//check method:
	            		//if block, return true
	            		//if leaf, return false, and add all adjacent pos to check (checked against a hashset of already done positions)
	            		//if air, just return false
	            	System.out.println("checking leaf can stay");

	            	alreadyChecked = new HashSet<BlockPos>();
	            	
	                if (findLog(worldIn, pos, pos))
	                {
	                    worldIn.setBlockState(pos, state.withProperty(CHECK_DECAY, Boolean.valueOf(false)), 4);
	                }
	                else
	                {
	                    this.dropBlockAsItem(worldIn, pos,state, 0);
	                    worldIn.setBlockToAir(pos);
	                }
	            }
	        }
	    }
	 private HashSet<BlockPos> alreadyChecked;
	 //could use a hashset of found log blocks maybe?
	 
	 private boolean findLog(World world, BlockPos origin, BlockPos pos){
		int x = pos.getX();
     	int y = pos.getY();
     	int z = pos.getZ();

     	for (int loopx = -1; loopx < 2; loopx++){
     		for (int loopy = -1; loopy < 2; loopy++){
     			for (int loopz = -1; loopz < 2; loopz++){
     				BlockPos toCheck = new BlockPos(x+loopx, y + loopy, z + loopz);
     				//checks that the distance is < 5 blocks
     				if (!alreadyChecked.contains(toCheck) && toCheck.distanceSq(x, y, z) < 25){
     					
     					
     					alreadyChecked.add(toCheck);
     					
     					int result = checkPos(world, toCheck);

     					switch (result){
     					case 2: 
     						return true;
     					case 1 : 
     						if (!alreadyChecked.contains(toCheck)){
     							findLog(world, origin, toCheck);
     						}
     						break;
     					case 0:
     						break;
     					}
     				}
     			}
     		}
     	}
     	return false;
	 }
	
	 private int checkPos(World world, BlockPos pos){
		 Block block = world.getBlockState(pos).getBlock();
		 if (block instanceof BlockLog){
			 return 2;
		 }
		 //It would be good to ensure that the blockstate matches - can just do this with metadata
		 else if (block instanceof BlockLeaves){
			 return 1;
		 }
		 else {
			 return 0;
		 }
	 }
	 
	 
}
