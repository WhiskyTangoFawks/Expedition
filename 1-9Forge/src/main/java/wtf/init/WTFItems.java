package wtf.init;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wtf.Core;
import wtf.config.CoreConfig;
import wtf.config.GameplayConfig;
import wtf.items.HomeScroll;
import wtf.items.SimpleItem;

public class WTFItems {

	public static Item sulfur;
	public static Item nitre;
	public static Item homescroll;
	
	public static void initItems(){
		sulfur = registerItem(new SimpleItem(), "itemSulfur");
		nitre = registerItem(new SimpleItem(), "itemNitre");
		if (CoreConfig.gameplaytweaks && GameplayConfig.homescroll){
			homescroll = registerItem(new HomeScroll(), "home_scroll");
		}
	}
	
	private static Item registerItem(Item item, String name){
		item.setUnlocalizedName(name);
		GameRegistry.register(item.setRegistryName(name));
		Core.proxy.registerItemRenderer(item);
		return item;
	}
	
}
