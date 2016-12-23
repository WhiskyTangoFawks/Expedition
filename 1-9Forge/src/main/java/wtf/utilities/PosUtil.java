package wtf.utilities;

import java.util.ArrayList;

import net.minecraft.util.math.BlockPos;

public class PosUtil {

	public static ArrayList<BlockPos> getAdj(BlockPos ori){
		ArrayList<BlockPos> list = new ArrayList<BlockPos>();
		
		for (int xloop = -1; xloop < 2; xloop++){
			for (int yloop = -1; yloop < 2; yloop++){
				for (int zloop = -1; zloop < 2; zloop++){
					BlockPos pos = new BlockPos(ori.getX() + xloop, ori.getY() + yloop, ori.getZ()+zloop);
						if (pos != ori){
							list.add(pos);
					}
				}	
			}	
		}
		return list;
	}
	
}
