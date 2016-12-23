package wtf.worldgen.trees;


import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;
import wtf.worldgen.trees.TreeVars.LeafStyle;
import wtf.worldgen.trees.types.AcaciaTree;
import wtf.worldgen.trees.types.Mangrove;
import wtf.worldgen.trees.types.PoplarTree;
import wtf.worldgen.trees.types.RedwoodTree;
import wtf.worldgen.trees.types.SimpleTree;
import wtf.worldgen.trees.types.SwampTree;
import wtf.worldgen.trees.types.Taiga1Tree;
import wtf.worldgen.trees.types.Taiga2Tree;
import wtf.worldscan.CoreWorldGenListener;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldGenTreeCancel {

	@SubscribeEvent
	public void decorate(DecorateBiomeEvent.Decorate event){
		if (event.getType() == Decorate.EventType.TREE){	
			event.setResult(Result.DENY);
		}
	}

	//tree testing
/*
	@SubscribeEvent
	public void PlayerPlaceBlock (PlaceEvent event) throws Exception
	{
		System.out.println("event caught");
	
		event.getWorld().setBlockState(event.getPos(), Blocks.AIR.getDefaultState());
	
		TreeVars newTree = new Mangrove(event.getWorld());
				
		
		ChunkCoords coords = new ChunkCoords(event.getPos()); 
		ChunkScan chunkscan = CoreWorldGenListener.getChunkScan(event.getWorld(), coords);
		
		TreePos tree = new TreePos(event.getWorld(), event.getWorld().rand, chunkscan, chunkscan.surface[event.getPos().getX()&15][event.getPos().getZ()&15], newTree);
		
		GenTree.generate(tree);

	}
*/
}
