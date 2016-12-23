package wtf.blocks;

import net.minecraft.block.BlockBush;
import net.minecraft.block.properties.PropertyBool;

public abstract class AbstractActiveablePlant extends BlockBush{

	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	
	public AbstractActiveablePlant(){
		
	}
	
	
}
