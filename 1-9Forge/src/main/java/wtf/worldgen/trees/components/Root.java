package wtf.worldgen.trees.components;

import net.minecraft.util.math.BlockPos;
import wtf.worldgen.trees.TreeInstance;

public class Root extends Branch{

	public Root(TreeInstance tree, double oriX, double oriY, double oriZ, double x, double y, double z, double rootLength) {
		super(tree, oriX, oriY, oriZ, x, y, z, rootLength);

	}

	public void growDown(TreeInstance tree){
		y -= vecY;
		x += vecX/tree.type.rootAirDivisor;
		z += vecZ/tree.type.rootAirDivisor;
		if (tree.type.airGenerate){
			count+=0.5; //if it's growing through air on an air generated tree, only subtract half a length
		}
		else {
			count++;
		}
		
	}
	
	public void growLateral(){
		this.lateralNext();
		count++;
	}
	
}
