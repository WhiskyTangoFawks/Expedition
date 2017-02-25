package wtf.worldgen.generators;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.utilities.wrappers.AdjPos;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.caves.AbstractCaveType;
import wtf.worldgen.caves.CaveProfile;
import wtf.worldgen.caves.CaveTypeRegister;

public class CaveGenerator {

	public void generate(World world, ChunkCoords coords, Random random, ChunkScan chunkscan, GeneratorMethods gen) {
		for (CaveListWrapper cave : chunkscan.caveset.getCaves()){

			CaveProfile profile = CaveTypeRegister.getCaveProfile(cave.getBiome(world));
			float depth =  (float) (1-cave.getAvgFloor()/chunkscan.surfaceAvg);
			AbstractCaveType cavetype = profile.getCave(cave.getAvgFloor()/chunkscan.surfaceAvg);
			
			//System.out.println("Testing purposes");
			//cavetype = CaveTypeRegister.paintedDesert;
			
			for (CavePosition position : cave.getCaveSet() ) {

				cavetype.generateFloor(gen, random, position.getFloorPos(), depth);
				cavetype.generateCeiling(gen, random, position.getCeilingPos(), depth);
				for (BlockPos wallpos : position.wall){
					cavetype.generateWall(gen, random, wallpos, depth, wallpos.getY() - position.floor);
				}
				for (AdjPos adjpos : position.adj){
					cavetype.generateAdjacentWall(gen, random, adjpos, depth, adjpos.getY() - position.floor);
				}


				if (random.nextInt(100) < cavetype.ceilingaddonchance+(depth)*5)
				{
					cavetype.generateCeilingAddons(gen, random, position.getCeilingPos().down(), depth);
				}

				if (random.nextInt(100) < cavetype.flooraddonchance+(depth)*5)
				{
					cavetype.generateFloorAddons(gen, random, position.getFloorPos().up(), depth);
				}

				if (cavetype.genAir){
					for (BlockPos airpos : position.getAirPos()){
						cavetype.generateAir(gen, random, airpos, depth);
					}

				}



			}
		}
	}	
}
