package wtf.worldscan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import wtf.utilities.wrappers.AdjPos;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.XZ;

public class UnsortedChunkCaves {

	private HashMap<XZ, ArrayList<CavePosition>> unsortedCavePos = new HashMap<XZ, ArrayList<CavePosition>>();
	private ArrayList<CaveListWrapper> sortedCaveLists = new ArrayList<CaveListWrapper>();
	
	

	public final ChunkCoords coords;

	public UnsortedChunkCaves(ChunkCoords coords){
		this.coords = coords;
	}
	
	public boolean addWallPos(BlockPos wall, AdjPos adj){
		XZ xz = new XZ(adj.getX(), adj.getZ());
		ArrayList<CavePosition> list = unsortedCavePos.get(xz);

		//if (list == null){
//			return false;
			
	//	}
		//else {
		for (CavePosition pos : list){
			if (pos.floor < adj.getY() && pos.ceiling > adj.getY()){
				//System.out.println("cave pos found");
				pos.wall.add(wall);
				pos.adj.add(adj);
				return true;
			}
		}
		//}
		//System.out.println("Cave not found");
		return false;
	}



	public void add(CavePosition pos){

		XZ xz = pos.xz();
		//System.out.println("Adding CavePos at " + pos.x + " " + pos.z);
		
		if (unsortedCavePos.containsKey(xz)){
			unsortedCavePos.get(xz).add(pos);
		}
		else {
			ArrayList<CavePosition> list = new ArrayList<CavePosition>();
			list.add(pos);
			unsortedCavePos.put(xz, list);
		}

	}

	public void getSortedCaves(){
		
		//start at the first position, with an empty sorted-cave list
		//get any adjacent to that position, check that the Y value is reasonably close, and remove them from the unsorted list, and add them to the sorted
		//repeat that, until there are no more adjacent

		Iterator<Entry<XZ, ArrayList<CavePosition>>> iterator = unsortedCavePos.entrySet().iterator();

		//I need to add a check to verify that during addalladj I'm not rechecking columns- if I just use the same method for positions with the hash to store, that should actually work
		//If I rewrite cavepos to give a hashcode of hash- I can't put it in a hashset as a master- am I doing that at all?

		while (iterator.hasNext()){
			Entry<XZ, ArrayList<CavePosition>> entry = iterator.next();
			XZ xz = entry.getKey();
			ArrayList<CavePosition> list = entry.getValue();

			Iterator<CavePosition> listerator = list.iterator();
			
			while (listerator.hasNext()){
				CavePosition cavepos = listerator.next();
				CaveListWrapper newcavelist = new CaveListWrapper(cavepos, coords);
				listerator.remove();
				addAllAdj(newcavelist, cavepos, xz);
				sortedCaveLists.add(newcavelist);

			}


		}
		
		for (CaveListWrapper wrapper : sortedCaveLists){
			wrapper.setCaveArrayList();
		}
		

	}

	private void addAllAdj(CaveListWrapper newcavelist, CavePosition oldpos, XZ oldxz){


		
		XZ[] set = oldxz.getAdj();
		
		for (XZ newxz: set){

			//if there's not already a position in the caveset, but there is a position in the master set
			if (!newcavelist.contains(newxz) && unsortedCavePos.containsKey(newxz)){

				CavePosition usepos = null;

				Iterator<CavePosition> iterator = unsortedCavePos.get(newxz).iterator();
				while (iterator.hasNext()){
					CavePosition newpos = iterator.next();
					int floordiff = Math.abs(oldpos.floor - newpos.floor);
					int ceildiff = Math.abs(oldpos.ceiling - newpos.ceiling);
					if (floordiff < 3 && ceildiff < 4){
						usepos=newpos;
						iterator.remove();
						break; //not sure whether performance is going to be afffected- I shouldn't need to iterate through the rest of the list
					}
				}
				

				//if I've gone from air to stone, then the cave position for the air, by definition, alread exists
				//so, I can just get the list at that pos, based on it's x-z coordinates, and add based on that

				if (usepos != null){
					
					newcavelist.addPos(usepos); //add the new position
					addAllAdj(newcavelist, usepos, newxz);
				}



			}
		}

	}

	public void printReport(){
		System.out.println("Caves per chunk " + sortedCaveLists.size());
		for (CaveListWrapper list : sortedCaveLists){
			if (list.size() > 10){
			System.out.println("New Cave");
			System.out.println(list.size());
			list.printMetrics();
			}
		}
	}

	public int size(){
		return this.sortedCaveLists.size();
	}
	
	public CaveListWrapper getRandomCave(Random random){
		CaveListWrapper list = this.sortedCaveLists.get(random.nextInt(sortedCaveLists.size()));
		return list;
	}
	
	public ArrayList<CaveListWrapper> getCaves(){
		return this.sortedCaveLists;
	}
}
