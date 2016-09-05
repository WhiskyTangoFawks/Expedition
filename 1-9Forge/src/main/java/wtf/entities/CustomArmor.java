package wtf.entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import wtf.Core;

public class CustomArmor extends ItemArmor{

	public String texture;
	
	public CustomArmor(ArmorMaterial material, EntityEquipmentSlot armorType, String textureName) {
		super(material, 0, armorType);
		this.texture = textureName;	
		this.setCreativeTab(Core.wtfTab);
	}


    @Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {

    }

}