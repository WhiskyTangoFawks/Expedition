package wtf.blocks;

import net.minecraft.block.state.IBlockState;
import wtf.init.BlockSets;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.wrappers.StateAndModifier;

public class BlockNaturalSandstone extends AbstractBlockDerivative{

	public BlockNaturalSandstone(IBlockState state){
		 super(state, state);
		 BlockSets.blockTransformer.put(new StateAndModifier(this.getDefaultState(), Modifier.BRICK), state);
	        
	}
	
}

