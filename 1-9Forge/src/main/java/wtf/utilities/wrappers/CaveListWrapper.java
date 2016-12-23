package wtf.utilities.wrappers;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;


public class CaveListWrapper {

	public final ArrayList<CavePosition> cave;
	public final ArrayList<BlockPos> wall;
	public final ArrayList<AdjPos> adjacentWall;

	private int totalFloor = 0;
	private int totalCeiling = 0;
	private int totalX = 0;
	private int totalZ = 0;

	int minFloor;
	int minCeiling;
	int minX;
	int minZ;

	int maxFloor;
	int maxCeiling;
	int maxX;
	int maxZ;
	
	CavePosition center = null;

	public CaveListWrapper(CavePosition firstposition, ArrayList<BlockPos> wallpos, ArrayList<AdjPos> adjacent){
		cave = new ArrayList<CavePosition>();
		wall = new ArrayList<BlockPos>(wallpos);
		adjacentWall = new ArrayList<AdjPos>(adjacent);

		cave.add(firstposition);
		totalFloor += firstposition.floor;
		totalCeiling += firstposition.ceiling;
		totalX += firstposition.x;
		totalZ += firstposition.z;

		minFloor = firstposition.floor;
		minCeiling = firstposition.ceiling;
		minX = firstposition.x;
		minZ = firstposition.z;

		maxFloor = firstposition.floor;
		maxCeiling = firstposition.ceiling;
		maxX = firstposition.x;
		maxZ = firstposition.z;
	}
	


	public CaveListWrapper addPos(CavePosition pos, ArrayList<BlockPos> wallpos, ArrayList<AdjPos> adjacent){

		int listFloor = cave.get(cave.size()-1).floor;
		int listCeiling = cave.get(cave.size()-1).ceiling;
		int lastX = cave.get(cave.size()-1).x;
		int lastZ = cave.get(cave.size()-1).z;

		//Maybe want to add a check for adjacency to the cave area?
		if 	(MathHelper.abs(listFloor - pos.floor) < 3 && MathHelper.abs(listCeiling - pos.ceiling) < 3 && MathHelper.abs(lastX - pos.x) < 4 && MathHelper.abs(lastZ - pos.z) < 4)
		{
			cave.add(pos);
			wall.addAll(wallpos);
			adjacentWall.addAll(adjacent);
			
			totalFloor += pos.floor;
			totalCeiling += pos.ceiling;
			totalX += pos.x;
			totalZ += pos.z;

			minFloor = minFloor < pos.floor ? minFloor : pos.floor; 
			minCeiling = minCeiling < pos.ceiling ? minCeiling : pos.ceiling;
			minX = minX < pos.x ? minX : pos.x;
			minZ = pos.z < pos.z ? minZ : pos.z;

			maxFloor = maxFloor > pos.floor ? maxFloor : pos.floor;
			maxCeiling = maxCeiling > pos.ceiling ? maxCeiling : pos.ceiling;
			maxX = maxX > pos.x ? maxX : pos.x;
			maxZ = maxZ > pos.z ? maxZ : pos.z;

			return this;
		}
		return null;
	}
	
	public void addWall(BlockPos wallpos, AdjPos adjpos){
		wall.add(wallpos);
		adjacentWall.add(adjpos);
	}
	
	public double getAvgFloor(){
		return totalFloor/cave.size();
	}

	public double getAvgCeiling(){
		return totalCeiling/cave.size();
	}

	public double getAvgX(){
		return totalX/cave.size();
	}
	public double getAvgZ(){
		return totalZ/cave.size();
	}

	public Biome getBiome(World world){
		return world.getBiomeForCoordsBody(new BlockPos(this.getAvgX(), this.getAvgFloor(), this.getAvgZ()));
	}

	public int getSizeX(){
		return MathHelper.abs_int(maxX-minX);
	}
	public int getSizeZ(){
		return MathHelper.abs_int(maxZ-minZ);
	}

	
	public CavePosition getCenter(){
	//This is very much a brute force method- it should be possible to index the set in a 2d array and simply call a given position
		if (center != null){
			return center;
		}
		int x = MathHelper.floor_double(this.getAvgX());
		int z = MathHelper.floor_double(this.getAvgZ());
		
		for (CavePosition pos : cave){
			if (pos.x == x && pos.z == z){
				center = pos;
				return pos;
			}
		}
			return null;
		
	}
	
	public ArrayList<CavePosition> getCircle(CavePosition center, float radius){
		ArrayList<CavePosition> newlist = new ArrayList<CavePosition>();
		for (CavePosition pos : cave){
			int distance = (pos.x - center.x)*(pos.x - center.x)+(pos.z - center.z)*(pos.z - center.z);
			if (distance < radius*radius){
				newlist.add(pos);
			}
		}
		return newlist;
	}
	
	public CavePosition getRandomPosition(Random random){
		return cave.get(random.nextInt(cave.size()));
	}

}
