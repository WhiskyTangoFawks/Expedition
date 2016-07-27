package wtf.core.utilities.wrappers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class AdjPos extends BlockPos {

	public final ArrayList<EnumFacing> faces = new ArrayList<EnumFacing>();

	
	public AdjPos(BlockPos pos, EnumFacing facing) {
		super(pos);
		faces.add(facing);
	}

	public void addFace(EnumFacing face){
		faces.add(face);
	}

	public EnumFacing getFace(Random random) {
		
		return faces.size() > 1 ? faces.get(random.nextInt(faces.size())) : faces.get(0);
	}
	
	
}
