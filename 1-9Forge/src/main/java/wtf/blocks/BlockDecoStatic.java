package wtf.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.config.WTFStoneRegistry;
import wtf.gameplay.StoneCrack;
import wtf.init.BlockSets;
import wtf.init.WTFBlocks;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.blockstatewriters.BlockstateWriter;
import wtf.utilities.wrappers.StateAndModifier;
import wtf.utilities.wrappers.StoneAndOre;

public class BlockDecoStatic extends AbstractBlockDerivative{

	public static final IProperty<DecoType> TYPE = PropertyEnum.create("type", DecoType.class);
	
	public BlockDecoStatic(IBlockState state) {
		super(state, state);
		
		if (WTFBlocks.crackedStone == null){
			//If cracked stone hasn't been defined, the define it for later use
			WTFBlocks.crackedStone = this.getDefaultState().withProperty(TYPE, DecoType.CRACKED);
		}
		
		BlockSets.blockTransformer.put(new StateAndModifier(state, BlockSets.Modifier.MOSSY), this.getDefaultState());
		BlockSets.blockTransformer.put(new StateAndModifier(state, BlockSets.Modifier.SOUL), this.getDefaultState().withProperty(TYPE, DecoType.SOUL));
		if (BlockSets.fallingBlocks.containsKey(this.parentBackground)){
			BlockSets.fallingBlocks.put(this, BlockSets.fallingBlocks.get(this.parentBackground));
		}
		if (WTFStoneRegistry.stoneReg.get(state).cracked){ //If NOT sand, then register a cracked version of the ore register
			BlockSets.stoneAndOre.put(new StoneAndOre(state, WTFBlocks.crackedStone), this.getDefaultState().withProperty(TYPE, DecoType.CRACKED));
		}
		
		
		
		
			
		//BlockSets.blockMiningSpeed
		if (BlockSets.fallingBlocks.containsKey(state.getBlock())){
			BlockSets.fallingBlocks.put(this, BlockSets.fallingBlocks.get(state.getBlock()));
		}
		BlockSets.ReplaceHashset.add(this);
	}
	
    @Override
	public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state)
    {
    	if (state.getValue(TYPE)==DecoType.CRACKED){
    		Entity crack = new StoneCrack(world, pos);
			world.spawnEntityInWorld(crack);
    	}
	}
	
	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
    {
        switch (layer){
		case CUTOUT:
			return false;
		case CUTOUT_MIPPED:
			return false;
		case SOLID:
			return true;
		case TRANSLUCENT:
			return true;
		default:
			break;
        
        }
        return false;
    }

	public enum DecoType implements IStringSerializable {
		MOSS(0, "moss", BlockSets.Modifier.MOSSY),
		SOUL(1, "soul", BlockSets.Modifier.SOUL),
		CRACKED(2, "cracked", BlockSets.Modifier.CRACKED);
	
		
		private final int ID;
		private final String name;
		public final BlockSets.Modifier modifier;

		private DecoType(int ID, String name, BlockSets.Modifier mod) {
			this.ID = ID;
			this.name = name;
			this.modifier = mod;
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
	
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		for (int loop = 0; loop < DecoType.values().length; loop++){
			list.add(new ItemStack(itemIn, 1, loop));
		}
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{

		return this.getDefaultState().withProperty(TYPE, DecoType.values()[meta]);
	}


	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(TYPE).ID;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {TYPE});
	}
	
}
