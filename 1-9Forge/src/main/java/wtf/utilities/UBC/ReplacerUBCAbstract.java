package wtf.utilities.UBC;

import exterminatorjeff.undergroundbiomes.api.API;
import exterminatorjeff.undergroundbiomes.api.UBStrataColumn;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.init.WTFBlocks;
import wtf.utilities.simplex.SimplexHelper;
import wtf.worldgen.replacers.Replacer;

public abstract class ReplacerUBCAbstract extends Replacer{

	protected static int columnX;
	protected static int columnZ;
	
	protected static UBStrataColumn column;
	
	protected int sedHash;
	
	protected IBlockState[] sands = {
			WTFBlocks.ubcSand.getStateFromMeta(1),
			WTFBlocks.ubcSand.getStateFromMeta(0),
			WTFBlocks.ubcSand.getStateFromMeta(2),
			WTFBlocks.ubcSand.getStateFromMeta(5),
			WTFBlocks.ubcSand.getStateFromMeta(6),
			WTFBlocks.ubcSand.getStateFromMeta(3),
			WTFBlocks.ubcSand.getStateFromMeta(7),
			WTFBlocks.ubcSand.getStateFromMeta(4)
	};
	protected IBlockState[] sandsstones = {
			UBCCompat.SedimentaryStone[1],
			UBCCompat.SedimentaryStone[0],
			UBCCompat.SedimentaryStone[2],
			UBCCompat.SedimentaryStone[5],
			UBCCompat.SedimentaryStone[6],
			UBCCompat.SedimentaryStone[3],
			UBCCompat.SedimentaryStone[7],
			UBCCompat.SedimentaryStone[4],
	};
	
	public static SimplexHelper simplex = new SimplexHelper("UBCSedimentaryOverride");
	
	
	public ReplacerUBCAbstract(Block block) {
		super(block);
		sedHash = API.SEDIMENTARY_STONE.getBlock().hashCode();
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
	
	public static double getSimplexSand(World world, BlockPos pos){
		int nonReplace = 8;
		return simplex.get3DNoise(world, (double)pos.getX()/3200, (double)pos.getY()/800, (double)pos.getZ()/3200)*(8+nonReplace);
	}

}
