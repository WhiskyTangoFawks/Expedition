package wtf.crafting;



import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
		
	@Override
		public Object getServerGuiElement(int ID, EntityPlayer player, World world,
				int x, int y, int z) {
			return null;//new QuickCon(player, player.inventory, new QuickInv(player));

		}

		@Override
		public Object getClientGuiElement(int ID, EntityPlayer player, World world,
				int x, int y, int z) {
			System.out.println("Opening new CraftGuideGUI");
			return new CraftGuideGUI(player.inventory, world);
		}
	}
