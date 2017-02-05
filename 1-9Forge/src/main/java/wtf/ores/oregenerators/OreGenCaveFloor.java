package wtf.ores.oregenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.ores.ChunkDividedOreMap;
import wtf.ores.OreGenAbstract;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;


public class OreGenCaveFloor extends OreGenAbstract{



	private final OreGenAbstract veinType;
	private final List<surface> surfaceList;
	
	public enum surface{
		floor, wall, ceiling
	}
	
	public OreGenCaveFloor(OreGenAbstract vein, IBlockState blockstate, int[] genRange, int[] minmaxPerChunk, boolean denseGen, ArrayList<surface> list) {
		super(blockstate, genRange, minmaxPerChunk, denseGen);
		veinType = vein;
		surfaceList = list;
	}

	//Not sure exactly why it's crashing...
	//something in the get random sets is returning a null
	//I mean, get wall is definitely going to be problematic, but that doesn't explain why the floow is causing the crash

	
	@Override
	public void doOreGen(World world, ChunkDividedOreMap map, Random random, ChunkCoords coords, ChunkScan chunkscan) throws Exception {
		
		if (chunkscan.caveset.size() > 0){
			
			//double chunkModifier = (double)chunkscan.caveset.size()/((double)chunkscan.surfaceAvg*256D);
			int blocksPerChunk = this.getBlocksPerChunk(world, coords, random, chunkscan.surfaceAvg);

			//I will need to access the size of the vein to do partial generation chances like I am for the other veins- which means I would need to seperate out the veinsize method, and throw it in
			//the abstract
			//System.out.println("gen " + numToGenerate);
			int blockReqs = this.blocksReq();
			while (blocksPerChunk > blockReqs || (blocksPerChunk > 0 && random.nextInt(blockReqs) < blocksPerChunk)){
				
				surface gen = surfaceList.get(random.nextInt(surfaceList.size()));
				BlockPos pos = null;
				CaveListWrapper cave = chunkscan.caveset.getRandomCave(random);
				CavePosition cavepos = cave.getRandomPosition(random);
				
				if (cavepos != null) {
					switch (gen){
					case ceiling:
						//System.out.println("ceiling");
						cavepos.getCeilingPos();
						break;
					case floor:
						//System.out.println("floor");
						pos = cavepos.getFloorPos();
						break;
					case wall:
						//System.out.println("wall");
						pos = cave.getRandomWall(random);

						break;
					default:
						break;

					}

					if (pos != null){
						blocksPerChunk-=veinType.genVein(world, map, random, chunkscan, pos);
					}
				}
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
