package wtf.biomes;


import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import wtf.biomes.TreeVars.LeafStyle;
import wtf.biomes.trees.SimpleTree;
import wtf.biomes.trees.SwampTree;
import wtf.biomes.trees.Taiga1Tree;
import wtf.biomes.trees.Taiga2Tree;
import wtf.core.utilities.wrappers.ChunkCoords;
import wtf.core.utilities.wrappers.ChunkScan;
import wtf.core.worldgen.CoreWorldGenListener;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldGenEventListener {

	@SubscribeEvent
	public void decorate(DecorateBiomeEvent.Decorate event){
		if (event.getType() == Decorate.EventType.TREE){	
			event.setResult(Result.DENY);
		}
	}
	/*

	@SubscribeEvent
	public void PlayerPlaceBlock (PlaceEvent event) throws Exception
	{
		System.out.println("event caught");
	
		event.getWorld().setBlockState(event.getPos(), Blocks.AIR.getDefaultState());
	
		TreeVars newTree = new SwampTree(event.getWorld());
				
		
		ChunkCoords coords = new ChunkCoords(event.getPos()); 
		ChunkScan chunkscan = CoreWorldGenListener.getChunkScan(event.getWorld(), coords);
		
		
		
		TreePos tree = new TreePos(event.getWorld(), event.getWorld().rand, chunkscan, chunkscan.surface[event.getPos().getX()&15][event.getPos().getZ()&15], newTree);
		
		GenTree.generate(tree);

	}
*/
}
