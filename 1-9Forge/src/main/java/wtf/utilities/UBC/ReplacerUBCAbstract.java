package wtf.utilities.UBC;

import exterminatorjeff.undergroundbiomes.api.API;
import exterminatorjeff.undergroundbiomes.api.UBStrataColumn;
import exterminatorjeff.undergroundbiomes.api.UBStrataColumnProvider;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import wtf.api.Replacer;

public abstract class ReplacerUBCAbstract extends Replacer{

	protected static int columnX;
	protected static int columnZ;
	
	protected static UBStrataColumn column;
	
	protected int sedHash;
	
	public ReplacerUBCAbstract(Block block) {
		super(block);
		sedHash = API.SEDIMENTARY_STONE.hashCode();
	}
	
	public static IBlockState getUBCStone(BlockPos pos){
		if (column != null && columnX == pos.getX() && columnZ == pos.getZ()){
			return column.stone(pos.getY());
		}
		else {
			UBStrataColumn column = API.STRATA_COLUMN_PROVIDER.ubStrataColumnProvider(0).strataColumn(pos.getX(), pos.getZ());
			columnX = pos.getX();
			columnZ = pos.getZ();
			return column.stone(pos.getY());
		}
	}

}
