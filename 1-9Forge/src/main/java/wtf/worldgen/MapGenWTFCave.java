package wtf.worldgen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenCaves;
import wtf.config.CaveBiomesConfig;
import wtf.init.WTFBlocks;

public class MapGenWTFCave extends MapGenCaves{

    protected static final IBlockState BLK_SANDSTONE = WTFBlocks.natSandStone.getDefaultState();
    protected static final IBlockState BLK_RED_SANDSTONE = WTFBlocks.natRedSandStone.getDefaultState();
	
	int sandstoneID = Blocks.SANDSTONE.getDefaultState().hashCode();
	int redsandstonestoneID = Blocks.RED_SANDSTONE.getDefaultState().hashCode();
	
	@Override
    public void generate(World worldIn, int chunkX, int chunkZ, ChunkPrimer primer){
		super.generate(worldIn, chunkX, chunkZ, primer);
		
		//ChunkCoords coords = new ChunkCoords(chunkZ, chunkZ);
		
				for (int x = 0; x < 16; ++x) {
					for (int y = 0; y < 256; ++y) {
						for (int z = 0; z < 16; ++z) {
							IBlockState state = primer.getBlockState(x, y, z);
							int hash = state.hashCode();
							if (hash == sandstoneID){
								primer.setBlockState(x, y, z, BLK_SANDSTONE);
								//System.out.println("Replacing Sandstone");
								if (CaveBiomesConfig.updateSandstone){
									//world.notifyBlockUpdate(entry.getKey().pos, oldstate, oldstate, 3);
								}
							}
							else if (hash == redsandstonestoneID){
								primer.setBlockState(x, y, z, BLK_RED_SANDSTONE);
								//if (CaveBiomesConfig.updateSandstone){
									//world.notifyBlockUpdate(entry.getKey().pos, oldstate, oldstate, 3);
								//}
							}
						}
					}
				}

	
		
	}
	
}
