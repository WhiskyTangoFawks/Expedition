package wtf.config;

import net.minecraft.block.state.IBlockState;

public class StoneRegEntry {

	public final IBlockState stone;
	public final IBlockState cobble;
	public final String textureLocation;
	public final String blockstateLocation;
	
	public final boolean decoStatic;
	public final boolean decoAnim;
	public final boolean speleothem;
	public final boolean cracked;
	
	public StoneRegEntry(IBlockState stoneState, IBlockState cobbleState, String textureLoc, String blockstateLoc, boolean stat, boolean anim, boolean spel, boolean crac){
		this.stone = stoneState;
		this.cobble = cobbleState;
		this.textureLocation = textureLoc;
		this.blockstateLocation = blockstateLoc;
		this.decoAnim = anim;
		this.decoStatic = stat;
		this.speleothem = spel;
		this.cracked = crac;
	}
	
	
	
}
