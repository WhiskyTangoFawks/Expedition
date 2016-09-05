package wtf.ores.oregenerators;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.ores.ChunkDividedOreMap;
import wtf.ores.OreGenAbstract;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;


public class OreGenUnderWater extends OreGenAbstract{

	private final OreGenAbstract veinType;
	
	public OreGenUnderWater(OreGenAbstract vein, IBlockState blockstate, float maxGenRangeHeight, float minGenRangeHeight, int maxPerChunk,
			int minPerChunk, boolean denseGen) {
		super(blockstate, maxGenRangeHeight, minGenRangeHeight, maxPerChunk, minPerChunk, denseGen);
		veinType = vein;
	}

	@Override
	public void doOreGen(World world, ChunkDividedOreMap map, Random random, ChunkCoords coords, ChunkScan chunkscan)
			throws Exception {
		
	
		ArrayList<BlockPos> list = chunkscan.getWaterList();
		if (list.size() > 0){
			
			int numToGenerate = this.getBlocksPerChunk(world, coords, random, chunkscan.surfaceAvg);
			int blocksReq = this.blocksReq();
		
			while (numToGenerate > blocksReq || (numToGenerate > 0 && random.nextInt(blocksReq) < numToGenerate)){
				
				BlockPos pos = list.get(random.nextInt(list.size()));
				//System.out.println("water generating " + pos.getX() + " " + pos.getY());
				//CavePosition pos = chunkscan.caveset.get(random.nextInt(chunkscan.caveset.size())).getRandomPosition(random);

				numToGenerate-=veinType.genVein(world, map, random, chunkscan, pos);
				//map.put(new OrePos(pos.x, pos.floor, pos.z, densityToSet), this.oreBlock);
			}
			
		}
		
	}

	@Override
	public int genVein(World world, ChunkDividedOreMap map, Random random, ChunkScan scan, BlockPos pos)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int blocksReq() {
		return veinType.blocksReq();
	}
}
