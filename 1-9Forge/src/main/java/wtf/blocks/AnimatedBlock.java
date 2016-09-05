package wtf.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wtf.config.GameplayConfig;
import wtf.gameplay.GravityMethods;
import wtf.init.BlockSets;
import wtf.init.WTFBlocks;
import wtf.utilities.wrappers.StateAndModifier;

public class AnimatedBlock extends AbstractBlockDerivative{

	public static final IProperty<ANIMTYPE> TYPE = PropertyEnum.create("type", ANIMTYPE.class);
	public static final PropertyBool FAST = PropertyBool.create("fast");
	
	public AnimatedBlock(IBlockState state) {
		super(state, state);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, ANIMTYPE.LAVA_CRUST).withProperty(FAST, false));
		
		BlockSets.blockTransformer.put(new StateAndModifier(state, BlockSets.Modifier.LAVA_CRUST), this.getDefaultState().withProperty(TYPE, ANIMTYPE.LAVA_CRUST));
		BlockSets.blockTransformer.put(new StateAndModifier(state, BlockSets.Modifier.WATER_DRIP), this.getDefaultState().withProperty(TYPE, ANIMTYPE.DRIP_WATER));
		BlockSets.blockTransformer.put(new StateAndModifier(state, BlockSets.Modifier.LAVA_DRIP), this.getDefaultState().withProperty(TYPE, ANIMTYPE.DRIP_LAVA));
		
	
		BlockSpeleothem block = WTFBlocks.speleothemMap.get(state);
		WTFBlocks.speleothemMap.put(this.getDefaultState().withProperty(TYPE, ANIMTYPE.DRIP_WATER), block);
		IBlockState fractured =  BlockSets.blockTransformer.get(new StateAndModifier(state, BlockSets.Modifier.COBBLE));
		BlockSets.blockTransformer.put(new StateAndModifier(this.getDefaultState().withProperty(TYPE, ANIMTYPE.DRIP_WATER), BlockSets.Modifier.COBBLE), fractured);
		BlockSets.blockTransformer.put(new StateAndModifier(this.getDefaultState().withProperty(TYPE, ANIMTYPE.DRIP_LAVA), BlockSets.Modifier.COBBLE), fractured);
		//BlockSets.blockTransformer.put(new StateAndModifier(this.getDefaultState().withProperty(TYPE, ANIMTYPE.LAVA_CRUST), BlockSets.Modifier.COBBLE), Blocks.LAVA.getDefaultState());
		
		//BlockSets.blockMiningSpeed
		
	}
	
	public IBlockState getBlockState(ANIMTYPE type){
		return this.getDefaultState().withProperty(TYPE, type);
	}
	
	public void setFast(World world, BlockPos pos){
		IBlockState blockstate = world.getBlockState(pos); 
		if (blockstate.getBlock() instanceof AnimatedBlock ){
			world.setBlockState(pos, getStateFromMeta(this.getMetaFromState(blockstate)+8));
		}
	}
	
	@Override
	public int getLightValue(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		
		IBlockState other = worldIn.getBlockState(pos);
		if (other.getBlock() != this)
		{
			return other.getLightValue(worldIn, pos);
		}
		//Update lit value for this position
		if (state.getValue(TYPE).ID==0){
			return 12;
		}
		return 0;
	}
	
    @Override
	public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state)
    {
    	if (state.getValue(TYPE).ID==0){
    		EntityFallingBlock entityfallingblock = new EntityFallingBlock(world, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, state);
			entityfallingblock.setHurtEntities(true);
			world.spawnEntityInWorld(entityfallingblock);
		}
    }

	
	public enum ANIMTYPE implements IStringSerializable {
		LAVA_CRUST(0, "lava_crust", BlockSets.Modifier.LAVA_CRUST),
		DRIP_WATER(1, "drip_water", BlockSets.Modifier.WATER_DRIP),
		DRIP_LAVA(2, "drip_lava", BlockSets.Modifier.LAVA_DRIP);
		
		private final int ID;
		private final String name;
		public final BlockSets.Modifier modifier;

		private ANIMTYPE(int ID, String name, BlockSets.Modifier mod) {
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
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random)
	{
		int chance = state.getValue(FAST) ? 5 : 20;
		if (random.nextInt(chance) == 0){

			//world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, pos.getX() + random.nextFloat(), pos.getY() + 0.5F, pos.getZ() + random.nextFloat(), 0.0D, 0.0D, 0.0D);

			//Default values for dripping
			double x = pos.getX() + random.nextFloat();
			double y = pos.getY() - 0.05D;
			double z = pos.getZ() + random.nextFloat();


			switch (state.getValue(TYPE)){
			case DRIP_LAVA:
				if (!world.isBlockNormalCube(pos.down(), false)){
					world.spawnParticle(EnumParticleTypes.DRIP_LAVA, x, y, z, 0.0D, 0.0D, 0.0D);
				}
				break;
			case DRIP_WATER:
				if (!world.isBlockNormalCube(pos.down(), false)){
					world.spawnParticle(EnumParticleTypes.DRIP_WATER, x, y, z, 0.0D, 0.0D, 0.0D);
				}
				break;
			case LAVA_CRUST:
				if (!world.isBlockNormalCube(pos.down(), false)){
					world.spawnParticle(EnumParticleTypes.DRIP_LAVA, x, y, z, 0.0D, 0.0D, 0.0D);
				}
				if (!world.isBlockNormalCube(pos.up(), false)){
					world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y+1, z, 0.0D, 0.0D, 0.0D);
				}
				break;
			default:
				break;

			}
		}
	}
	
    @Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
    {
    	if (state.getValue(TYPE).getID() == 0){
    		return layer == BlockRenderLayer.CUTOUT_MIPPED;
    	}
    	return super.canRenderInLayer(state, layer);
    }
    

    
	
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		for (int loop = 0; loop < ANIMTYPE.values().length; loop++){
			list.add(new ItemStack(itemIn, 1, loop));
		}
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		boolean fast = false;
		if (meta > 7){
			meta -=7;
			fast = true;
		}
		
		//temp fix because there are only 3 values
		if (meta > 2){
			meta = 0;
		}
		
		IBlockState state = this.getDefaultState().withProperty(TYPE, ANIMTYPE.values()[meta]).withProperty(FAST, fast);
		return state;
	}


	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;
		i = state.getValue(TYPE).getID();
		if (state.getValue(FAST)){
			i+=8;
		}
		return i;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {TYPE, FAST});
	}
}
