package wtf.utilities.wrappers;

import net.minecraft.util.math.BlockPos;

public class OrePos extends BlockPos{

	public final int density;
	
	public OrePos(double xpos, double ypos, double zpos, int density) {
		super(xpos, ypos, zpos);
		this.density = density;
	}
	
	public OrePos(BlockPos pos, int density){
		super(pos);
		this.density = density;
	}


}
