package wtf.blocks;

import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import wtf.Core;

public class BlockMycorrack extends BlockNetherrack{

	public BlockMycorrack(){
		this.setHardness(0.4F);
		this.setSoundType(SoundType.STONE);
		this.setCreativeTab(Core.wtfTab);
	}
	
	
	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
    {
        switch (layer){
		case CUTOUT:
			return false;
		case CUTOUT_MIPPED:
			return false;
		case SOLID:
			return true;
		case TRANSLUCENT:
			return true;
		default:
			break;
        
        }
        return false;
    }
	
}
