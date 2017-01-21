package wtf.blocks.substitution;

import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.config.OverworldGenConfig;

public class LeafUpdateHelper {

	 static HashSet<BlockPos> alreadyChecked;
	 //could use a hashset of found log blocks maybe?
	 
	 
	 
	 static boolean findLog(World world, BlockPos origin, BlockPos pos){
		int x = pos.getX();
    	int y = pos.getY();
    	int z = pos.getZ();

    	for (int loopx = -1; loopx < 2; loopx++){
    		for (int loopy = -1; loopy < 2; loopy++){
    			for (int loopz = -1; loopz < 2; loopz++){
    				BlockPos toCheck = new BlockPos(x+loopx, y + loopy, z + loopz);
    				//checks that the distance is < 5 blocks
    				if (!alreadyChecked.contains(toCheck)){// && toCheck.distanceSq(x, y, z) < OverworldGenConfig.subLeafDistance){
    				
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
	
	 private static int checkPos(World world, BlockPos pos){
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
