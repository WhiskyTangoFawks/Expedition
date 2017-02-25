package wtf.init;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.config.GameplayConfig;
import wtf.config.MasterConfig;

public class LootEventListener {

	public class NameAndPool {
		public final ResourceLocation name;
		public final String pool;
		public NameAndPool(ResourceLocation name, String pool){
			this.name = name;
			this.pool = pool;
		}
		
		public boolean trySet(LootTableLoadEvent event, LootEntry entry){
			if (event.getName() == name && event.getTable().getPool(pool) != null){
				event.getTable().getPool(pool).addEntry(entry);
				return true;
			}
			return false;
		}
		
	}
	
	NameAndPool dungeonMain = new NameAndPool(LootTableList.CHESTS_SIMPLE_DUNGEON, "main");
	NameAndPool pyramid = new NameAndPool(LootTableList.CHESTS_DESERT_PYRAMID, "main");
	NameAndPool library = new NameAndPool(LootTableList.CHESTS_STRONGHOLD_LIBRARY, "main");
	NameAndPool blacksmith = new NameAndPool(LootTableList.CHESTS_VILLAGE_BLACKSMITH, "main");
	
	NameAndPool[] teleportScrollList = {dungeonMain, pyramid, library};
	LootEntry teleportScroll = new LootEntryItem(WTFItems.homescroll, 15, 0, new LootFunction[] {new SetCount(new LootCondition[0], 
			new RandomValueRange(1, 3))}, new LootCondition[0], "loottable:teleportscroll");
	
	LootEntry wcicTable = new LootEntryItem(Item.getItemFromBlock(WTFBlocks.wcicTable), 20, 0, new LootFunction[0], new LootCondition[0], "loottable:wcicTable");
	
	
	
	@SubscribeEvent
	public void lootTableLoad(LootTableLoadEvent event){
		
		if (GameplayConfig.homescroll && MasterConfig.gameplaytweaks){
			for (NameAndPool entry : teleportScrollList){
				entry.trySet(event, teleportScroll);
			}
		}
		
		if (GameplayConfig.wcictable && MasterConfig.gameplaytweaks){
			blacksmith.trySet(event, wcicTable);
		}
	}

	
	
}
