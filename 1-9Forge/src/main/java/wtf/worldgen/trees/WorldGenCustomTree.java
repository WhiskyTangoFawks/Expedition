package wtf.worldgen.trees;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import wtf.utilities.wrappers.ChunkScan;

public class WorldGenCustomTree extends WorldGenerator {

	public final ChunkScan chunkscan;
	public final TreeVars type;
	
	public WorldGenCustomTree(ChunkScan chunkscan, TreeVars type){
		this.chunkscan = chunkscan;
		this.type = type;
	}
	
	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		 try {
			GenTree.tryGenerate(new TreePos(world, world.rand, chunkscan, pos, type));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
