package wtf.utilities.wrappers;

import net.minecraft.block.state.IBlockState;
import wtf.init.BlockSets;

public class StateAndModifier {

	public final IBlockState state;
	public final BlockSets.Modifier modifier;
	
	public StateAndModifier(IBlockState state, BlockSets.Modifier modifier){
		this.state = state;
		this.modifier = modifier;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((modifier == null) ? 0 : modifier.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StateAndModifier other = (StateAndModifier) obj;
		if (modifier != other.modifier)
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
	
	
}
