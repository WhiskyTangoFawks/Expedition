package wtf.core.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRoots extends AbstractBlockDerivative{

	public static final IProperty<RootType> TYPE = PropertyEnum.create("type", RootType.class);
	
	public BlockRoots() {
		super(Blocks.LOG.getDefaultState(), Blocks.LOG.getDefaultState());
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, RootType.oak));
	}


	
    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots) // do not drop items while restoring blockstates, prevents item dupe
        {
            java.util.List<ItemStack> items = this.getDrops(worldIn, pos, state, fortune);
            //chance = net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, this.parentForeground, fortune, chance, false, harvesters.get());

            for (ItemStack item : items)
            {
                if (worldIn.rand.nextFloat() <= chance)
                {
                    spawnAsEntity(worldIn, pos, item);
                }
            }
        }
    }
    
    @Override
	@Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Items.STICK;
    }
    
    @Override
	public int quantityDropped(Random random)
    {
        return 1+random.nextInt(2);
    }
    
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE);
	}


	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, RootType.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		RootType type = state.getValue(TYPE);
		return type.getID();
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		for (int loop = 0; loop < RootType.values().length; loop++){
			list.add(new ItemStack(itemIn, 1, loop));
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
    
	@Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
	
	public enum RootType implements IStringSerializable {
		oak(0, "oak"),
		spruce(1, "spruce"),
		birch(2, "birch"),
		jungle(3, "jungle"),
		big_oak(4, "big_oak"),
		acacia(5, "acacia");
		

		private final int ID;
		private final String name;


		private RootType(int ID, String name) {
			this.ID = ID;
			this.name = name;

		}

		@Override
		public String getName() {
			return name;
		}

		public int getID() {
			return ID;
		}
		@Override
		public String toString() {
			return getName();
		}
		
	}

}
