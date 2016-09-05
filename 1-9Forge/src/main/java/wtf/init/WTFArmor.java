package wtf.init;

import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wtf.core.Core;
import wtf.entities.CustomArmor;

public class WTFArmor {

	public static Item mummyHelmet;
	public static Item mummyChestplate;
	public static Item mummyLeggings;
	public static Item mummyBoots;
	
	public static Item pharaohHelmet;
	public static Item pharaohChestplate;
	public static Item pharaohLeggings;
	public static Item pharaohBoots;	


	public static ArmorMaterial mummyWrap = EnumHelper.addArmorMaterial("MUMMYCLOTH", Core.coreID+":mummy", 1, new int[] {0, 1, 1, 0}, 5, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
	public static ArmorMaterial pharaohWrap = EnumHelper.addArmorMaterial("MUMMYGOLD", Core.coreID+":pharaoh", 1, new int[] {0, 1, 1, 0}, 5, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0);
	
	public static void initArmor(){


		
		mummyHelmet = registerItem(new CustomArmor(mummyWrap, EntityEquipmentSlot.HEAD, "mummy"), "mummy_helmet");		
		mummyChestplate = registerItem(new CustomArmor(mummyWrap, EntityEquipmentSlot.CHEST, "mummy"), "mummy_chestplate");
		mummyLeggings = registerItem(new CustomArmor(mummyWrap, EntityEquipmentSlot.LEGS, "mummy"), "mummy_leggings");
		mummyBoots = registerItem(new CustomArmor(mummyWrap, EntityEquipmentSlot.FEET, "mummy"), "mummy_boots");
		
		GameRegistry.addShapelessRecipe(new ItemStack(Items.STRING, 1, 15), new Object[]{mummyHelmet});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.STRING, 3, 15), new Object[]{mummyChestplate});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.STRING, 2, 15), new Object[]{mummyLeggings});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.STRING, 1, 15), new Object[]{mummyBoots});
		
		pharaohHelmet = registerItem(new CustomArmor(pharaohWrap, EntityEquipmentSlot.HEAD, "pharaoh"), "pharaoh_helmet");
		pharaohChestplate = registerItem(new CustomArmor(pharaohWrap, EntityEquipmentSlot.CHEST, "pharaoh"), "pharaoh_chestplate");
		pharaohLeggings = registerItem(new CustomArmor(pharaohWrap, EntityEquipmentSlot.LEGS, "pharaoh"), "pharaoh_leggings");
		pharaohBoots = registerItem(new CustomArmor(pharaohWrap, EntityEquipmentSlot.FEET, "pharaoh"), "pharaoh_boots");
	}
	
	public static Item registerItem(Item item, String name){
		item.setUnlocalizedName(name);
		item.setRegistryName(name);
		GameRegistry.register(item);
		Core.proxy.registerItemRenderer(item);
		return item;
	}
	
}
